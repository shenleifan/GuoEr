package guoer.lf.ed.guoer.view;

import android.support.design.widget.AppBarLayout;

public abstract class AppBarStateChangedListener implements AppBarLayout.OnOffsetChangedListener {
    private State mCurrentState = State.IDLE;

    public enum State {
        EXPANDED,
        COLLAPSE,
        IDLE
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSE) {
                onStateChanged(appBarLayout, State.COLLAPSE);
            }
            mCurrentState = State.COLLAPSE;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE);
            }
            mCurrentState = State.IDLE;
        }
    }
    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
}
