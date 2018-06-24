package com.example.shenjiaqi.hoverdemo;

/**
 *  悬浮窗window控制器
 *
 *  @author shenjiaqi
 *  @since 2017/5/28
 */
public abstract class BaseHoverViewState implements HoverState {

    /** 悬浮窗视图view */
    protected HoverLayout mHoverLayout;

    @Override
    public void takeControl(HoverLayout hoverLayout) {
        mHoverLayout = hoverLayout;
    }

    @Override
    public void addToWindow(int dx, int dy) {
        mHoverLayout.windowViewController.setHoverView(mHoverLayout);
        if (!mHoverLayout.isAddedToWindow) {
            mHoverLayout.windowViewController.addView(
                    mHoverLayout.sTouchDiameter,
                    mHoverLayout.sTouchDiameter,
                    false,
                    mHoverLayout,
                    dx,
                    dy
            );

            mHoverLayout.isAddedToWindow = true;

            if (mHoverLayout.isTouchableInWindow) {
                mHoverLayout.makeTouchableInWindow();
            } else {
                mHoverLayout.makeUntouchableInWindow();
            }
        }
    }

    @Override
    public void removeFromWindow() {
        if (mHoverLayout.isAddedToWindow) {
            mHoverLayout.windowViewController.removeView(mHoverLayout);
            mHoverLayout.isAddedToWindow = false;
        }
    }

    @Override
    public void makeTouchableInWindow() {
        mHoverLayout.isTouchableInWindow = true;
        if (mHoverLayout.isAddedToWindow) {
            mHoverLayout.windowViewController.makeTouchable(mHoverLayout);
        }
    }

    @Override
    public void makeUntouchableInWindow() {
        mHoverLayout.isTouchableInWindow = false;
        if (mHoverLayout.isAddedToWindow) {
            mHoverLayout.windowViewController.makeUntouchable(mHoverLayout);
        }
    }
}
