package guoer.lf.ed.guoer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import guoer.lf.ed.guoer.utils.NetworkUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Splash activity.
 */

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    @BindView(R.id.image_splash)
    ImageView mImageSplash;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
//        String bingPic = PreferenceManager.getDefaultSharedPreferences(this).getString("bing_pic", null);
        if (NetworkUtils.isNetWorkAvailable(this)) {
//            if (bingPic != null) {
//                Glide.with(this).load(bingPic).into(mImageSplash);
//            } else {
//                loadBingPic();
//            }
            String requestBingPic = "http://cn.bing.com/az/hprichbg/rb/RainbowLorikeets_ZH-CN10796666125_1920x1080.jpg";
            Glide.with(this).load(requestBingPic).into(mImageSplash);
            mHandler.postDelayed(mRunnable = new Runnable() {
                @Override
                public void run() {
                    startMainScreen();
                }
            }, 3000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            startMainScreen();
        }
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
        return super.onTouchEvent(event);
    }

    private void startMainScreen() {
        Log.d(TAG, "startMainScreen: ");
        Intent intent = new Intent(this, GuoerActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadBingPic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String requestBingPic = "http://cn.bing.com/az/hprichbg/rb/RainbowLorikeets_ZH-CN10796666125_1920x1080.jpg";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(requestBingPic)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (BuildConfig.DEBUG) Log.d(TAG, "response:" + response);
                    if (response != null && response.isSuccessful()) {
                        final String bingPicUrl = response.body().string();
                        if (BuildConfig.DEBUG) Log.d("SplashActivity", bingPicUrl);
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                        editor.putString("bing_pic", bingPicUrl);
                        editor.apply();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(SplashActivity.this).load(bingPicUrl).into(mImageSplash);
                                startMainScreen();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
