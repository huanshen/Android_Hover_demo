package com.example.shenjiaqi.hoverdemo;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 *  悬浮窗window控制器
 *
 *  @author shenjiaqi
 *  @since 2017/5/28
 */

public class HoverWindowController {
    /** 用于管控悬浮窗的windowManager */
    private WindowManager mWindowManager;
    /** 当前需依附的activity */
    private Activity mContext;

    private HoverLayout mHoverLayout;

    public HoverWindowController(WindowManager windowManager, Activity context) {
        mWindowManager = windowManager;
        mContext = context;
    }
    
    /**
     * 创建一个悬浮视图。默认靠近左上
     *
     * @param width       视图宽度
     * @param height      视图高度
     * @param isTouchable 悬浮窗是否可触摸
     * @param view        添加为悬浮窗的视图view.
     * @param dx          悬浮窗离视图左边距
     * @param dy          悬浮窗离视图下边距
     */
    public void addView(int width, int height, boolean isTouchable, View view, int dx, int dy) {
        // 如果待添加的视图不可响应touch事件，则添加响应的flag标记
        int touchableFlag = isTouchable ? 0 : WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        int windowType = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
        IBinder windowToken = mContext.getWindow().getDecorView().getWindowToken();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                width,
                height,
                windowType,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE 
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS 
                        | touchableFlag,
                PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = dx;
        params.y = dy;
        params.token = windowToken;
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;

        mWindowManager.addView(view, params);
    }

    /**
     * 移除悬浮窗
     *
     * @param view  需移除的视图
     */
    public void removeView(View view) {
        if (mWindowManager == null) {
            return;
        }
        if (null != view && null != view.getParent()) {
            mWindowManager.removeViewImmediate(view);
        }
    }

    /**
     * 获取视图位置
     *
     * @param view 待获取位置的视图
     * @return 返回视图位置
     */
    public Point getViewPosition(View view) {
        if (view == null) {
            return new Point(0, 0);
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null || !(params instanceof WindowManager.LayoutParams)) {
            return new Point(0, 0);
        }
        WindowManager.LayoutParams windowParams = (WindowManager.LayoutParams) view.getLayoutParams();
        return new Point(windowParams.x, windowParams.y);
    }

    /**
     * 移动悬浮窗位置
     *
     * @param view 待移动的悬浮窗
     * @param x 悬浮窗x轴移动距离
     * @param y 悬浮窗y轴移动距离
     */
    public void moveViewTo(View view, int x, int y) {
        if (view == null) {
            return;
        }
        WindowManager.LayoutParams params;
        if (view.getLayoutParams() == null || !(view.getLayoutParams() instanceof WindowManager.LayoutParams)) {
            params = new WindowManager.LayoutParams();
        } else {
            params = (WindowManager.LayoutParams) view.getLayoutParams();

        }
        params.x = x;
        params.y = y;
        mWindowManager.updateViewLayout(view, params);
    }

    /**
     * 移动悬浮窗位置
     *
     * @param x 悬浮窗x轴移动距离
     * @param y 悬浮窗y轴移动距离
     */
    public void moveHoverViewTo(int x, int y) {
        moveViewTo(mHoverLayout, x, y);
    }

    public void setHoverView(HoverLayout view) {
        mHoverLayout = view;
    }

    /**
     * 显示悬浮窗
     *
     * @param view 需要显示的view
     */
    public void showView(View view) {
        try {
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) view.getLayoutParams();
            mWindowManager.addView(view, params);
        } catch (IllegalStateException e) {
            // The view is already visible.
        }
    }

    /**
     * 隐藏悬浮窗
     *
     * @param view 需要隐藏的view
     */
    public void hideView(View view) {
        try {
            mWindowManager.removeViewImmediate(view);
        } catch (IllegalArgumentException e) {
            // The View wasn't visible to begin with.
        }
    }
    
    /**
     * 将指定的view设定为接收touch事件
     *
     * @param view 待移动的悬浮窗
     */
    public void makeTouchable(View view) {
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) view.getLayoutParams();
        params.flags = params.flags 
                & ~WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE 
                & ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mWindowManager.updateViewLayout(view, params);
    }

    /**
     * 将指定的view设定为不接收touch事件
     *
     * @param view 待移动的悬浮窗
     */
    public void makeUntouchable(View view) {
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) view.getLayoutParams();
        params.flags = params.flags 
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE 
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mWindowManager.updateViewLayout(view, params);
    }
}