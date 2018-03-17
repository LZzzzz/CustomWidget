package com.demo.longzongjia.customwidget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by longzongjia on 2018/3/13.
 */

public class QuickIndexBar extends View {
    private static final String[] LETTERS = new String[]{
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z"
    };
    private int mWidth;
    private int mCellHeight;
    private Paint paint;
    int currentIndex = -1;

    public QuickIndexBar(Context context) {
        super(context);
        init();
    }

    public QuickIndexBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickIndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(30);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        mCellHeight = (int) (mHeight * 1.0f / LETTERS.length);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getCurrentIndex(event);
                break;
            case MotionEvent.ACTION_MOVE:
                getCurrentIndex(event);
                break;
            case MotionEvent.ACTION_UP:
                currentIndex = -1;
                break;
        }
        return true;
    }

    private void getCurrentIndex(MotionEvent event) {
        int index = (int) (event.getY() / mCellHeight);
        if (index >= 0 && index < LETTERS.length) {
            if (index != currentIndex) {
                if (mOnLetterUpdateListener != null) {
                    mOnLetterUpdateListener.onLetterUpdate(LETTERS[index]);
                }
                currentIndex = index;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < LETTERS.length; i++) {
            String letter = LETTERS[i];
            float width = mWidth * 0.5f;
            Rect bounds = new Rect();
            paint.getTextBounds("0123456", 2, 5, bounds);
            int texthight = bounds.height();
            float height = mCellHeight * 0.5f + texthight * 0.5f + i * mCellHeight;
            canvas.drawText(letter, width, height, paint);
        }
    }

    OnLetterUpdateListener mOnLetterUpdateListener;

    public void setmOnLetterUpdateListener(OnLetterUpdateListener mOnLetterUpdateListener) {
        this.mOnLetterUpdateListener = mOnLetterUpdateListener;
    }

    public interface OnLetterUpdateListener {
        void onLetterUpdate(String letter);
    }
}
