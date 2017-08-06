package com.example.lifan.drawanddrag.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.lifan.drawanddrag.Box;
import com.example.lifan.drawanddrag.R;

import java.util.ArrayList;
import java.util.List;

public class BoxDrawingView extends View {
    private static final String TAG = "BoxDrawingView";

    private Box mCurrentBox;

    private List<Box> mBoxes = new ArrayList<>();

    private Paint mBoxPaint;

    private Paint mBackgroundPaint;

    public BoxDrawingView(Context context) {
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public BoxDrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //Paint the boxes a nice semitransparent red (ARGB)
        mBoxPaint = new Paint();
        mBackgroundPaint = new Paint();
        Resources rs = context.getResources();
        int colorDraw = rs.getColor(R.color.colorDraw, null);
        int colorBackground = rs.getColor(R.color.colorBackground, null);
        mBoxPaint.setColor(colorDraw);

        //Paint the background off-white
        mBackgroundPaint.setColor(colorBackground);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF curr = new PointF(event.getX(), event.getY());
//        Point curPoint = new Point();
        Log.i(TAG, "onTouchEvent: Received event at (" + curr.x + " , " + curr.y + ")");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent: Action down");
                //Reset drawing state
                mCurrentBox = new Box(curr);
                mBoxes.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent: Action up");
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.i(TAG, "onTouchEvent: Action move");
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrentPoint(curr);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "onTouchEvent: Action cancel");
                mCurrentBox = null;
                break;
            default:
                break;
        }

//        return super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Fill the background
        canvas.drawPaint(mBackgroundPaint);

        for (Box box : mBoxes) {
            if (box.getCurrentPoint() != null && box.getOrigin() != null) {

                float left = Math.min(box.getOrigin().x, box.getCurrentPoint().x);
                float right = Math.max(box.getOrigin().x, box.getCurrentPoint().x);
                float top = Math.min(box.getOrigin().y, box.getCurrentPoint().y);
                float bottom = Math.max(box.getOrigin().y, box.getCurrentPoint().y);
                canvas.drawRect(left, top, right, bottom, mBoxPaint);
            }

        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.i(TAG, "onSaveInstanceState: ");
        Parcelable parcelable = super.onSaveInstanceState();
        DrawSavedState drawSavedState = new DrawSavedState(parcelable);
        drawSavedState.boxes = mBoxes;
        return drawSavedState;
    }

    private static class DrawSavedState extends BaseSavedState {
        List<Box> boxes;

        public DrawSavedState(Parcel source) {
            super(source);
            this.boxes = (List<Box>) source.readValue(getClass().getClassLoader());
        }

        public DrawSavedState(Parcelable source) {
            super(source);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(boxes);
        }

        @Override
        public String toString() {
            return "BoxDrawingView.DrawSavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " box " + boxes + "}";
        }

        public static final Parcelable.Creator<DrawSavedState> CREATOR =
                new Parcelable.Creator<DrawSavedState>() {

                    @Override
                    public DrawSavedState createFromParcel(Parcel source) {
//                        return null;
                        return new DrawSavedState(source);
                    }

                    @Override
                    public DrawSavedState[] newArray(int size) {
                        return new DrawSavedState[size];
                    }
                };
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.i(TAG, "onRestoreInstanceState: ");
        DrawSavedState savedState = (DrawSavedState) state;

        super.onRestoreInstanceState(savedState.getSuperState());
        mBoxes = savedState.boxes;
    }
}
