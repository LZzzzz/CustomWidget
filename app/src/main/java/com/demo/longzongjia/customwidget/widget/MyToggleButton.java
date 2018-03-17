package com.demo.longzongjia.customwidget.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.demo.longzongjia.customwidget.R;

/**
 * Created by longzongjia on 2018/3/15.
 */

public class MyToggleButton extends View {
    private static final int DEFAULT_WIDTH = dp2pxInt(58);
    private static final int DEFAULT_HEIGHT = dp2pxInt(36);

    private boolean state;
    private RectF rect = new RectF();
    private Paint paint;
    private Paint buttonPaint;
    /**
     * 边框宽度px
     */
    private int borderWidth;
    private int background;
    private int shadowRadius;
    private int shadowOffset;
    /**
     * 边框内按钮宽高
     */
    private float height;
    private float width;
    /**
     * 背景位置
     */
    private float left;
    private float top;
    private float right;
    private float bottom;
    /**
     * 背景半径
     */
    private float viewRadius;
    private int uncheckColor;
    private int checkedColor;

    public MyToggleButton(Context context) {
        super(context);
        init(context, null);
    }

    public MyToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint("Recycle")
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = null;
        if (attrs != null) {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyToggleButton);
        }
        boolean isShadow = optBoolean(typedArray, R.styleable.MyToggleButton_is_shadow, true);
        int buttonColor = optColor(typedArray, R.styleable.MyToggleButton_button_color, Color.WHITE);
        background = optColor(typedArray, R.styleable.MyToggleButton_bt_background, Color.WHITE);//Color.WHITE;
        shadowRadius = optPixelSize(typedArray, R.styleable.MyToggleButton_shadow_radius, dp2pxInt(2.5f));
        shadowOffset = optPixelSize(typedArray, R.styleable.MyToggleButton_shadow_offset, dp2pxInt(1.5f));
        int shadowColor = optColor(typedArray, R.styleable.MyToggleButton_shadow_color, 0X33000000);
        borderWidth = optPixelSize(typedArray, R.styleable.MyToggleButton_border_width, dp2pxInt(1));
        uncheckColor = optColor(typedArray, R.styleable.MyToggleButton_uncheck_color, 0XffDDDDDD);//0XffDDDDDD;
        checkedColor = optColor(typedArray, R.styleable.MyToggleButton_checked_color, 0Xff51d367);//0Xff51d367;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        buttonPaint.setColor(buttonColor);
        if (isShadow) {
            buttonPaint.setShadowLayer(shadowRadius, 0, shadowOffset, shadowColor);
        }
    }

    private int optPixelSize(TypedArray typedArray, int index, int def) {
        if (typedArray == null) {
            return def;
        }
        return typedArray.getInt(index, def);
    }

    private boolean optBoolean(TypedArray typedArray, int index, boolean def) {
        if (typedArray == null) {
            return def;
        }
        return typedArray.getBoolean(index, def);
    }

    private int optColor(TypedArray typedArray, int index, int def) {
        if (typedArray == null) {
            return def;
        }
        return typedArray.getColor(index, def);
    }


    private static float dp2px(float dp) {
        Resources r = Resources.getSystem();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    private static int dp2pxInt(float dp) {
        return (int) dp2px(dp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.UNSPECIFIED
                || widthMode == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_WIDTH, MeasureSpec.EXACTLY);
        }
        if (heightMode == MeasureSpec.UNSPECIFIED
                || heightMode == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_HEIGHT, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float viewPadding = Math.max(shadowRadius + shadowOffset, borderWidth);

        height = h - viewPadding - viewPadding;
        width = w - viewPadding - viewPadding;

        viewRadius = height * .5f;

        left = viewPadding;
        top = viewPadding;
        right = w - viewPadding;
        bottom = h - viewPadding;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(borderWidth);
        paint.setStyle(Paint.Style.FILL);
        //绘制按钮白色背景
        paint.setColor(background);
        //绘制背景
        drawRoundRect(canvas,
                left, top, right, bottom,
                viewRadius, paint);
        //绘制关闭状态的边框
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(uncheckColor);
        drawRoundRect(canvas,
                left, top, right, bottom,
                viewRadius, paint);
        //绘制小圆圈
//        if(showIndicator){
//            drawUncheckIndicator(canvas);
//        }
    }

    private void drawRoundRect(Canvas canvas, float left,
                               float top, float right,
                               float bottom, float backgroundRadius,
                               Paint paint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(left, top, right, bottom,
                    backgroundRadius, backgroundRadius, paint);
        }else{
            rect.set(left, top, right, bottom);
            canvas.drawRoundRect(rect,
                    backgroundRadius, backgroundRadius, paint);
        }
    }


}
