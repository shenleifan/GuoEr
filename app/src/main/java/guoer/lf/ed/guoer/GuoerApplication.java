package guoer.lf.ed.guoer;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

import guoer.lf.ed.guoer.logUtils.LogUtils;

public class GuoerApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        LogUtils.setLevel(LogUtils.VERBOSE);

        LitePal.initialize(mContext);
    }
}
