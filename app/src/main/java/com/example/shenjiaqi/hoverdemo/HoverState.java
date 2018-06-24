package com.example.shenjiaqi.hoverdemo;


/**
 *  {@link HoverLayout}的具体响应拖拽点击等事件的状态类实现接口
 *  
 *  @author shenjiaqi
 *  @since 2017/5/28
 */
public interface HoverState {

    /** 
     * 激活该状态 
     * 
     * @param hoverLayout 悬浮窗布局view
     */
    void takeControl(HoverLayout hoverLayout);

    /**
     * 改变为移动状态
     */
    void moved();

    /**
     * 改变为关闭状态
     */
    void close();
    /**
     * 将对应的HoverLayout增添至window
     * 
     * @param dx 悬浮窗离屏幕左边缘距离
     * @param dy 悬浮窗离屏幕下边缘距离
     */
    void addToWindow(int dx, int dy);

    /**
     * 将对应的HoverLayout从window中移除
     */
    void removeFromWindow();

    /**
     * 使对应的view响应touch事件
     */
    void makeTouchableInWindow();

    /**
     * 使对应的view不响应touch事件
     */
    void makeUntouchableInWindow();
}
