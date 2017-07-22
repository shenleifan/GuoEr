package guoer.lf.ed.guoer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.litepal.LitePal;

import guoer.lf.ed.guoer.utils.CrashHandler;
import guoer.lf.ed.guoer.utils.LogUtils;
import guoer.lf.ed.guoer.utils.SharedPreferencesUtils;

public class GuoerApplication extends Application {
    private static Context mContext;

    private SharedPreferencesUtils mPreferencesUtils = null;

    private static GuoerApplication mApplication = new GuoerApplication();

    public GuoerApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        init();
    }

    private void init() {
        LitePal.initialize(mContext);

        CrashHandler.getInstance().init(this);

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        boolean isCrashHandled = mPreferences.getBoolean(SharedPreferencesUtils.CRASH_TAG, SharedPreferencesUtils.DEFAULT_CRASH_TAG_VALUE);

        int tagLevel = mPreferences.getInt(SharedPreferencesUtils.LOG_TAG, SharedPreferencesUtils.DEFAULT_LOG_TAG_LEVEL);

        LogUtils.setLevel(tagLevel);

    }

    public static Context getContext() {
        return mContext;
    }

    public static GuoerApplication getInstance() {
        return mApplication;
    }

    public SharedPreferencesUtils getSpUtils() {
        return mPreferencesUtils;
    }

}
