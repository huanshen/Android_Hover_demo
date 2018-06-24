package com.example.shenjiaqi.hoverdemo;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *  悬浮窗基本布局
 *
 *  @author shenjiaqi
 *  @since 2017/5/28
 */
public class HoverLayout extends FrameLayout {
    /** 悬浮窗控制区域 */
    public static int sTouchDiameter;
    /** 悬浮窗状态改变事件监听 */
    final Set<HoverStateListener> mListeners = new CopyOnWriteArraySet<>();
    /** 悬浮窗点击滑动事件处理view */
    public final HoverDragger dragger;
    /** 悬浮窗控制器 */
    public final HoverWindowController windowViewController;
    /** 悬浮窗关闭状态 */
    public final HoverViewStateClosed closed = new HoverViewStateClosed();
    /** 悬浮窗处于可移动状态 */
    public final HoverViewStateMoved moveState = new HoverViewStateMoved();

    /** 标记是否已添加至window */
    public boolean isAddedToWindow;
    /** 标记是否可触发touch事件 */
    public boolean isTouchableInWindow;
    /** 悬浮窗当前状态 */
    public HoverState mState;
    /** windowManager布局参数 */
    protected WindowManager.LayoutParams mWmParams;
    /** 悬浮窗初始位置 */
    private int mStartX;
    private int mStartY;
    /** 标记悬浮窗是否已经显示，避免重复添加 */
    private boolean mIsShow = false;
    
    public HoverLayout(Context context,
                       HoverDragger dragger,
                       HoverWindowController hoverWindowController) {
        super(context);
        this.dragger = dragger;
       // dragger.activate();
        windowViewController = hoverWindowController;
        setFocusableInTouchMode(true);
        setState(new HoverViewStateClosed());
    }

    public void moveTo(@NonNull Point floatPosition) {
        Point cornerPosition = convertCenterToCorner(floatPosition);
        setX(cornerPosition.x);
        setY(cornerPosition.y);
    }

    private Point convertCenterToCorner(@NonNull Point centerPosition) {
        return new Point(
                centerPosition.x - (150 / 2),
                centerPosition.y - (150 / 2)
        );
    }

    /**
     * 通知切换到close状态中
     */
    public void notifyListenersClosing() {
        for (HoverStateListener listener : mListeners) {
            listener.onClosing();
        }
    }

    /**
     * 通知已切换到close状态
     */
    public void notifyListenersClosed() {
        for (HoverStateListener listener : mListeners) {
            listener.onClosed();
        }
    }

    /**
     * 通知切换到fixed状态中
     */
    public void notifyListenersFixing() {
        for (HoverStateListener listener : mListeners) {
            listener.onFixing();
        }
    }

    /**
     * 通知已切换到fixed状态
     */
    public void notifyListenersFixed() {
        for (HoverStateListener listener : mListeners) {
            listener.onFixed();
        }
    }

    /**
     * 添加至window
     *
     * @param dx 待添加的x轴位置
     * @param dy 待添加的y轴位置
     */
    public void addToWindow(int dx, int dy) {
        mState.addToWindow(dx, dy);
        mStartX = dx;
        mStartY = dy;
        mIsShow = true;
    }

    /**
     * 从window中移除对应的悬浮窗
     */
    public void removeFromWindow() {
        mState.removeFromWindow();
    }

    /**
     * 使对应悬浮窗可响应touch事件
     */
    public void makeTouchableInWindow() {
        mState.makeTouchableInWindow();
    }

    /**
     * 使对应悬浮窗不响应touch事件
     */
    public void makeUntouchableInWindow() {
        mState.makeUntouchableInWindow();
    }
    
    /**
     * 设置状态
     *
     * @param state 当前悬浮窗状态
     */
    public void setState(HoverState state) {
        if (state != null) {
            mState = state;
            mState.takeControl(this);
        }
    }

    /**
     * 切换为close状态
     */
    public void close() {
        mState.close();
    }

    /**
     * 切换为moved状态
     */
    public void moved() {
        mState.moved();

    }

    /**
     * 获取悬浮窗初始位置
     */
    public Point getStartPosition() {
        return new Point(
                 (mStartX + sTouchDiameter / 2),
                 (mStartY + sTouchDiameter / 2)
        );
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
     * @param params 小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mWmParams = params;
    }

    /**
     * 隐藏悬浮窗
     */
    public void hide() {
        if (!mIsShow) {
            return;
        }
        windowViewController.hideView(this);
        if (dragger instanceof HoverWindowDragger) {
            View view = ((HoverWindowDragger) dragger).mDragView;
            if (view != null) {
                windowViewController.hideView(view);
            }
        }
        mIsShow = false;
    }

    /**
     * 显示悬浮窗
     */
    public void show() {
        if (mIsShow) {
            return;
        }
        windowViewController.showView(this);
        if (dragger instanceof HoverWindowDragger) {
            View view = ((HoverWindowDragger) dragger).mDragView;
            if (view != null) {
                windowViewController.showView(view);
            }
        }
        mIsShow = true;
    }
    
    public boolean isShow() {
        return mIsShow;
    }

    public void setVisibility(boolean visible) {
        mIsShow = false;
    }

    /**
     * 悬浮窗状态迁移监听
     */
    public interface HoverStateListener {
        /** 迁移至fixed状态中 */
        void onFixing();
        /** 已迁移至fixed状态 */
        void onFixed();
        /** 迁移至close状态中 */
        void onClosing();
        /** 已迁移至close状态 */
        void onClosed();
    }
    
}
