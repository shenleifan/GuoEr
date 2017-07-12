package guoer.lf.ed.guoer.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import guoer.lf.ed.guoer.R;

public class ProgressWebView extends WebView {
    private ProgressBar mProgressBar;

    public ProgressWebView(Context context) {
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0, 0);
        mProgressBar.setLayoutParams(layoutParams);
        Drawable drawable = context.getDrawable(R.drawable.progress_bar_states);
        mProgressBar.setProgressDrawable(drawable);
        addView(mProgressBar);

        setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });

        setWebChromeClient(new ProgressWebChromeClient());

        this.getSettings().setSupportZoom(true);
        this.getSettings().setBuiltInZoomControls(true);

    }

    private class ProgressWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                if (mProgressBar.getVisibility() == View.GONE) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
