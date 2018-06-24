package com.example.shenjiaqi.hoverdemo;

import android.graphics.Point;

/**
 * 拖动接口
 *
 * @author shenjiaqi
 * @since 2017/5/28
 */
public interface HoverDragger  {

    /**
     * 开始控制实现用户点击拖拽事件
     * 
     * @param dragListener 拖拽点击事件监听器
     * @param dragStartCenterPosition 拖拽点击事件开始坐标
     * @param isDraggable 是否支持滑动
     */
    void activate(DragListener dragListener, Point dragStartCenterPosition,
                  boolean isDraggable);

    /**
     * 结束操作用户点击拖拽事件
     */
    void deactivate();

    interface DragListener {

        /**
         * 按压事件
         *
         * @param x 事件触发的x坐标
         * @param y 事件触发的y坐标
         */
        void onPress(float x, float y);

        /**
         * 开始拖拽事件
         *
         * @param x 事件触发的x坐标
         * @param y 事件触发的y坐标
         */
        void onDragStart(float x, float y);

        /**
         * 拖拽事件
         *
         * @param x 事件触发的x坐标
         * @param y 事件触发的y坐标
         */
        void onDragTo(float x, float y);

        /**
         * 用户停止触摸拖拽区域
         *
         * @param x 事件触发的x坐标
         * @param y 事件触发的y坐标
         */
        void onReleasedAt(float x, float y);

        /**
         * 点击事件
         */
        void onTap();
    }
}
