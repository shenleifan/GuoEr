package guoer.lf.ed.guoer.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


public class MyImgView extends AppCompatImageView {
    private static final String TAG = "MyImgView";

    private static final int SCALE_REDUCE_INIT = 0;

    private static final int SCALING = 1;

    private static final int SCALE_ADD_INIT = 6;

    private int mWidth;

    private int mHeight;

    private int mCenterWidth;

    private int mCenterHeight;

    private float mMinScale = 0.85f;

    private boolean isFinished = true;

    private Handler mScaleHandler = new Handler() {
        private Matrix matrix = new Matrix();

        private int count = 0;

        private float s = 1.0f;

        private boolean isClicked;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            matrix.set(getImageMatrix());
            switch (msg.what) {
                case SCALE_REDUCE_INIT:
                    if (!isFinished) {
                        mScaleHandler.sendEmptyMessage(SCALE_REDUCE_INIT);
                    } else {
                        isFinished = false;
                        count = 0;
                        s = (float) Math.sqrt(Math.sqrt(mMinScale));
                        beginScale(matrix, s);
                        mScaleHandler.sendEmptyMessage(SCALING);
                    }
                    break;
                case SCALING:
                    beginScale(matrix, s);
                    if (count < 2) {
                        s = s * s;
                        mScaleHandler.sendEmptyMessage(SCALING);
                    } else {
                        isFinished = true;
                        if (MyImgView.this.mOnViewClickListener != null && !isClicked) {
                            isClicked = true;
                            MyImgView.this.mOnViewClickListener.onViewClicked(MyImgView.this);
                        } else {
                            isClicked = false;
                        }
                    }
                    count++;
                    break;
                case SCALE_ADD_INIT:
                    if (!isFinished) {
                        mScaleHandler.sendEmptyMessage(SCALE_ADD_INIT);
                    } else {
                        isFinished = true;
                        count = 0;
                        s = (float) Math.sqrt(Math.sqrt(1.0f / mMinScale));
                        beginScale(matrix, s);
                        mScaleHandler.sendEmptyMessage(SCALING);
                    }
                    break;
            }
        }
    };

    private synchronized void beginScale(Matrix matrix, float scale) {
        matrix.postScale(scale, scale, mCenterWidth, mCenterHeight);
        setImageMatrix(matrix);
    }

    //
//    private static class MyHandler extends Handler {
//        private Matrix matrix = new Matrix();
//
//        private int count = 0;
//
//        private float s;
//
//        private boolean isClicked = false;
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            matrix.set(getImageMatrix());
//        }
//    }
    private OnViewClickListener mOnViewClickListener;

    public interface OnViewClickListener {
        void onViewClicked(MyImgView view);
    }

    public void setOnViewClickListener(OnViewClickListener listener) {
        mOnViewClickListener = listener;
    }

    public MyImgView(Context context) {
        super(context);
        init(null, 0);
    }

    public MyImgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyImgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            mHeight = getHeight() - getPaddingTop() - getPaddingBottom();
            mCenterWidth = mWidth / 2;
            mCenterHeight = mHeight / 2;

            BitmapDrawable bg = (BitmapDrawable) getDrawable();
            bg.setAntiAlias(true);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                mScaleHandler.sendEmptyMessage(SCALE_REDUCE_INIT);
                break;
            case MotionEvent.ACTION_UP:
                mScaleHandler.sendEmptyMessage(SCALE_ADD_INIT);
                break;
        }
        return true;
    }
}
