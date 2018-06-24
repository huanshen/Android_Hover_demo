package com.example.shenjiaqi.hoverdemo;


/**
 *  默认关闭态
 *
 *  @author shenjiaqi
 *  @since 2017/5/28
 */
public class HoverViewStateClosed extends BaseHoverViewState {
    
    @Override
    public void takeControl(HoverLayout hoverLayout) {
        super.takeControl(hoverLayout);
        mHoverLayout.notifyListenersClosing();
        mHoverLayout.mState = this;
        mHoverLayout.clearFocus();
        mHoverLayout.makeUntouchableInWindow();
        
        mHoverLayout.windowViewController.hideView(mHoverLayout);
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

    @Override
    public void close() {

    }

    @Override
    public void moved() {
        changeState(mHoverLayout.moveState);

    }
}
