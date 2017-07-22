package guoer.lf.ed.guoer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Shared preference Utils
 */

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils sPreferencesUtils;

    private SharedPreferences mSharedPreferences;

    public static final String CRASH_TAG = "crash_tag";

    public static final boolean DEFAULT_CRASH_TAG_VALUE = false;

    public static final String LOG_TAG = "log_level";

    public static final int DEFAULT_LOG_TAG_LEVEL = LogUtils.VERBOSE;

    private SharedPreferencesUtils(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferencesUtils getUtils(Context context) {
        if (null == sPreferencesUtils) {
            sPreferencesUtils = new SharedPreferencesUtils(context);
        }
        return sPreferencesUtils;
    }

    public void setCrashTag(boolean isOpen) {
        mSharedPreferences.edit().putBoolean(CRASH_TAG, isOpen).apply();
    }

    public void setLogUtils(int level) {
        mSharedPreferences.edit().putInt(LOG_TAG, level).apply();
    }


}
