package com.example.shenjiaqi.hoverdemo;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

/**
 *  悬浮拖动IMPL
 *
 *  @author shenjiaqi
 *  @since 2017/5/28
 */
public class HoverWindowDragger implements HoverDragger {
    /** 上下文 */
    private final Context mContext;
    /** 悬浮窗控制类 */
    private final HoverWindowController mHoverWindowController;
    /** 悬浮窗点击区域直径 */
    private final int mTouchAreaDiameter;
    /** 视为点击状态范围 */
    private final float mTapTouchSlop;
    /** 拖拽监听 */
    private HoverDragger.DragListener mDragListener;
    /** 标记是否 */
    private boolean mIsActivated;
    /** 用于进行拖拽的view */
    public View mDragView;
    /** 是否可以进行拖拽 */
    private boolean mIsDraggable = true;
    /** 标记位置 */
    private PointF mOriginalViewPosition = new PointF();
    private PointF mCurrentViewPosition = new PointF();
    private PointF mOriginalTouchPosition = new PointF();


    /** 拖拽事件监听 */
    private View.OnTouchListener mDragTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    mOriginalViewPosition = getDragViewCenterPosition();
                    mCurrentViewPosition = new PointF(mOriginalViewPosition.x, mOriginalViewPosition.y);
                    mOriginalTouchPosition.set(event.getRawX(), event.getRawY());
                    if (mDragListener != null) {
                        mDragListener.onPress(mCurrentViewPosition.x, mCurrentViewPosition.y);
                    }

                    return true;
                case MotionEvent.ACTION_MOVE:
                    float dragDeltaX = event.getRawX() - mOriginalTouchPosition.x;
                    float dragDeltaY = event.getRawY() - mOriginalTouchPosition.y;
                    mCurrentViewPosition = new PointF(
                            mOriginalViewPosition.x + dragDeltaX,
                            mOriginalViewPosition.y + dragDeltaY
                    );
                    if (mIsDraggable && !isTouchWithinSlopOfOriginalTouch(dragDeltaX, dragDeltaY)) {
                        moveDragViewTo(mCurrentViewPosition);
                        if (mDragListener != null) {
                            mDragListener.onDragTo(mCurrentViewPosition.x, mCurrentViewPosition.y);
                        }
                        mHoverWindowController.moveHoverViewTo((int) mCurrentViewPosition.x, (int) mCurrentViewPosition.y);
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    if (!mIsDraggable) {
                        if (mDragListener != null) {
                            mDragListener.onTap();
                        }
                    } else {
                        if (mDragListener != null) {
                            mDragListener.onReleasedAt(mCurrentViewPosition.x, mCurrentViewPosition.y);
                        }
                    }

                    return true;
                default:
                    return false;
            }
        }
    };

    public HoverWindowDragger(Context context,
                              HoverWindowController hoverWindowController,
                         int touchAreaDiameter,
                         float tapTouchSlop) {
        mContext = context;
        mHoverWindowController = hoverWindowController;
        mTouchAreaDiameter = touchAreaDiameter;
        mTapTouchSlop = tapTouchSlop;
    }

    
    @Override
    public void activate(DragListener dragListener, Point dragStartCenterPosition,
                         boolean isDraggable) {
        if (!mIsActivated) {
            createTouchControlView(dragStartCenterPosition);
            mDragListener = dragListener;
            mDragView.setOnTouchListener(mDragTouchListener);
            mIsActivated = true;
            mIsDraggable = isDraggable;
        }

    }

    @Override
    public void deactivate() {
        if (mIsActivated) {
            mDragView.setOnTouchListener(null);
            destroyTouchControlView();
            mIsActivated = false;
        }
    }

    /**
     * 创建拖拽控制view
     *
     * @param dragStartCenterPosition 拖拽事件起始位置
     */
    private void createTouchControlView(final Point dragStartCenterPosition) {
        mDragView = new View(mContext);
        mHoverWindowController.addView(mTouchAreaDiameter, mTouchAreaDiameter, true, mDragView, 0, 0);
        // 移动到位置
        mHoverWindowController.moveViewTo(mDragView,
                dragStartCenterPosition.x - (mTouchAreaDiameter / 2),
                dragStartCenterPosition.y - (mTouchAreaDiameter / 2));
        mDragView.setOnTouchListener(mDragTouchListener);
        updateTouchControlViewAppearance();

    }

    private void updateTouchControlViewAppearance() {
        if (null != mDragView) {
            if (mIsActivated) {
                mDragView.setBackgroundColor(0x44FF0000);
            } else {
                mDragView.setBackgroundColor(0x00000000);
            }
        }
    }
    
    /**
     * 注销触摸控制区域view
     */
    private void destroyTouchControlView() {
        mHoverWindowController.removeView(mDragView);
        mDragView = null;
    }

    /**
     * 获取触摸控制区域view位置
     * 
     * @return 触摸控制区域view位置
     */
    private PointF getDragViewCenterPosition() {
        Point cornerPosition =  mHoverWindowController.getViewPosition(mDragView);
        return new PointF(cornerPosition.x, cornerPosition.y);
    }

    /**
     * 判断该次touch事件的距离是否属于tap事件
     * 
     * @param dx x轴移动距离
     * @param dy y轴移动距离
     * @return 是否属于tap事件 true为是 false为否
     */
    private boolean isTouchWithinSlopOfOriginalTouch(float dx, float dy) {
        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return distance < mTapTouchSlop;
    }

    /**
     * 移动触摸控制区域view
     * 
     * @param centerPosition 待移动的位置
     */
    private void moveDragViewTo(PointF centerPosition) {
        mHoverWindowController.moveViewTo(mDragView, (int) centerPosition.x, (int) centerPosition.y);
    }
}
