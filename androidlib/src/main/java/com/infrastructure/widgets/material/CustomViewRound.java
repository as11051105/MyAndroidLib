package com.infrastructure.widgets.material;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CustomViewRound extends RelativeLayout {

    final static String MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto";
    final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";

    final int disabledBackgroundColor = Color.parseColor("#E2E2E2");
    // Indicate if user touched this view the last time
    public boolean isLastTouch = false;
    int beforeBackground;
    boolean animation = false;
    // 控件默认长、宽
    private int defaultWidth = 50;
    private int defaultHeight = 50;
    private int mBorderThickness = 1;
    private int mBorderInsideColor = 6;                                            // 图片的内边界

    public CustomViewRound(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            setBackgroundColor(beforeBackground);
        } else {
            setBackgroundColor(disabledBackgroundColor);
        }
        invalidate();
    }

    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
        animation = true;
    }

    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
        animation = false;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (defaultWidth == 0) {
            defaultWidth = getWidth();
        }
        if (defaultHeight == 0) {
            defaultHeight = getHeight();
        }

        int radius = 0;
        radius = (defaultWidth < defaultHeight ? defaultWidth : defaultHeight) / 2 - 2 * mBorderThickness;
        // 画内圆
        drawCircleBorder(canvas, radius + mBorderThickness / 2, mBorderInsideColor);
        if (animation) {
            invalidate();
        }
    }

    /**
     * 边缘画圆
     */
    private void drawCircleBorder(Canvas canvas, int radius, int color) {
        Paint paint = new Paint();
        /* 去锯齿 */
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);
        /* 设置paint的 style 为STROKE：空心 */
        paint.setStyle(Paint.Style.FILL);
		/* 设置paint的外框宽度 */
        paint.setStrokeWidth(mBorderThickness);
        canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);
    }
}
