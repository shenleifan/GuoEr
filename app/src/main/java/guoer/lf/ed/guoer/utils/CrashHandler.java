package guoer.lf.ed.guoer.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.UserManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import guoer.lf.ed.guoer.BuildConfig;
import guoer.lf.ed.guoer.GuoerApplication;

/**
 * Singleton instance of Crash handler
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private GuoerApplication mApplication;

    private static CrashHandler sCrashHandler = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //used for save crash infos
    private Map<String, String> infos = new HashMap<>();

    //format created info date
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault());
    private String mNameString = null;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sCrashHandler;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void init(Context context) {
        mContext = context;
        mApplication = GuoerApplication.getInstance();
        //Get system default UncaughtExceptionHandler
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        //Set this CrashHandler as default uncaughtExceptionHandler
        Thread.setDefaultUncaughtExceptionHandler(this);
//        UserManager userManager = (UserManager) mContext.getSystemService(Context.USER_SERVICE);
//        userManager.getUserName();
        PackageManager packageManager = mContext.getPackageManager();
        mNameString = (String) packageManager.getApplicationLabel(mContext.getApplicationInfo());
    }

    /**
     * Handle actions when uncaughtException happen
     *
     * @param t Current thread
     * @param e Throwable exception
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(e) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            //exit application
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * Custom uncaughtException handle
     *
     * @param e Throwable exception
     * @return true if handled else false
     */
    private boolean handleException(Throwable e) {
        if (null == e) {
            return false;
        }

        GuoerApplication.getInstance().getSpUtils().setCrashTag(true);

        //use toast to show exception collection
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "Sorry, exception collection, application will exit...", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();

        //Collection device info
        collectDeviceInfo(mContext);

        //Save log file
        String fileName = saveCrashLogToFile(e);

        return true;
    }

    /**
     * save crash log into local log file
     *
     * @param e throwable
     * @return file name, used for upload to server
     */
    private String saveCrashLogToFile(Throwable e) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(value);
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);

        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace();
            cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append(result);


        try {
            long currentTime = System.currentTimeMillis();
            String time = mDateFormat.format(new Date());
            StringBuilder logFileName = new StringBuilder();
            logFileName.append(mNameString).append("-").append(time).append(currentTime).append(".log");
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                        + mContext.getPackageName() + File.separator + "log";
                if (BuildConfig.DEBUG) Log.d("CrashHandler", path);
                File dir = new File(path);
                boolean mkOK = false;
                if (!dir.isDirectory()) {
                    mkOK = dir.mkdirs();
                    if (!mkOK) {
                        if (BuildConfig.DEBUG) Log.d("CrashHandler", "create dir failed.");
                    }

                }
                FileOutputStream fos = new FileOutputStream(path + logFileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return logFileName.toString();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    /**
     * Collect device info
     *
     * @param context context
     */
    private void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (null != pi) {
                String versionName = pi.versionName == null ? null : pi.versionName;
                String versionCode = "" + pi.versionCode;
                infos.put(versionName, versionName);
                infos.put(versionCode, versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                infos.put(field.getName(), field.get(null).toString());
                if (BuildConfig.DEBUG) Log.d("CrashHandler", "field.get(null):" + field.get(null));
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG)
                    Log.d("CrashHandler", "an error occured when collect crash info", e);
                e.printStackTrace();
            }
        }
    }
}
