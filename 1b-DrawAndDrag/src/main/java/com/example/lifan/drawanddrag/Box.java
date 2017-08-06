package com.example.lifan.drawanddrag;

import android.graphics.PointF;

public class Box {
    private PointF mOrigin;
    private PointF mCurrentPoint;

    public Box(PointF origin) {
        mOrigin = origin;
    }

    public PointF getCurrentPoint() {
        return mCurrentPoint;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public void setCurrentPoint(PointF currentPoint) {
        mCurrentPoint = currentPoint;
    }
}
