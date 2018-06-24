package com.example.shenjiaqi.hoverdemo;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 *  自己定义的悬浮窗布局
 *
 *  @author shenjiaqi
 *  @since 2017/5/28
 */
public class MyHoverView extends HoverLayout {

    /** 上下文环境 **/
    private Context mContext;

    public final HoverDragger mDragger;

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

    @NonNull
    public Point getPosition() {
        return new Point(
                (int) (getX() + (150 / 2)),
                (int) (getY() + (150 / 2))
        );
    }

    /**
     * 创建悬浮窗
     *
     * @param context               上下文环境
     * @param hoverWindowController 悬浮窗window控制器
     */
    public static MyHoverView createForWindow(Context context,
                                              HoverWindowController hoverWindowController) {
        HoverDragger mDragger = createWindowDragger(context, hoverWindowController);
        return new MyHoverView(context, mDragger, hoverWindowController);
    }

    /**
     * 创建悬浮窗拖拽控制视图
     *
     * @param context 上下文环境
     * @param hoverWindowController 悬浮窗window控制器
     */
    private static HoverDragger createWindowDragger(Context context,
                                                    HoverWindowController hoverWindowController) {
        sTouchDiameter = 150;
        int slop = ViewConfiguration.get(context).getScaledTouchSlop();
        return new HoverWindowDragger(
                context,
                hoverWindowController,
                sTouchDiameter,
                slop
        );
    }

    /**
     * 默认构造器
     *
     * @param context               上下文环境
     * @param dragger               悬浮窗拖拽视图
     * @param hoverWindowController 悬浮窗window控制器
     */
    private MyHoverView(Context context,
                        HoverDragger dragger,
                        HoverWindowController hoverWindowController) {
        super(context, dragger, hoverWindowController);
        mDragger = dragger;
        mContext = context;
        init();
    }

    public void init() {
        TextView textView = new TextView(mContext);
        textView.setText("拖动我啊");
        addView(textView);
        setFocusableInTouchMode(true);
    }

}