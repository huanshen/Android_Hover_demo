package com.example.shenjiaqi.hoverdemo;

/**
 * 悬浮窗移动状态管理器
 *
 * @author shenjiaqi
 * @since 2017/5/28
 */
public class HoverViewStateMoved extends BaseHoverViewState {

    /** 拖拽控制监听 */
    private HoverDragger.DragListener mDragListener;

    @Override
    public void takeControl(HoverLayout hoverLayout) {
        super.takeControl(hoverLayout);

        mHoverLayout.mState = this;
        mHoverLayout.clearFocus();
        mHoverLayout.makeUntouchableInWindow();
        mHoverLayout.windowViewController.showView(mHoverLayout);
        activateDragger();
    }

    @Override
    public void close() {
        changeState(mHoverLayout.closed);
    }

    @Override
    public void moved() {
        changeState(mHoverLayout.moveState);
    }

    /**
     * 更改状态
     *
     * @param nextState 待改变的状态
     */
    private void changeState(HoverState nextState) {
        mHoverLayout.setState(nextState);
        mHoverLayout = null;
    }

    /**
     * 激活拖拽控制器
     */
    private void activateDragger() {
        mHoverLayout.dragger.activate(mDragListener, mHoverLayout.getStartPosition(), true);
    }

    /**
     * 反激活拖拽控制器
     */
    private void deactivateDragger() {
        mHoverLayout.dragger.deactivate();
    }

    /**
     * 设置拖拽控制器监听
     */
    public void setDragListener(HoverDragger.DragListener dragListener) {
        mDragListener = dragListener;
    }
}

