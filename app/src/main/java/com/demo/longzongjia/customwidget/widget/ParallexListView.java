package com.demo.longzongjia.customwidget.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

import com.demo.longzongjia.customwidget.R;

/**
 * Created by longzongjia on 2018/3/13.
 */

public class ParallexListView extends ListView {

    private ImageView img;
    private int orginalHeigh;
    private volatile int intrinsicHeight;
    private int newHeight;

    public ParallexListView(Context context) {
        super(context);
        init(context);
    }

    public ParallexListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ParallexListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View inflate = View.inflate(context, R.layout.header, null);
        img = (ImageView) inflate.findViewById(R.id.header);
        intrinsicHeight = img.getDrawable().getIntrinsicHeight();
        addHeaderView(inflate);
        this.post(new Runnable() {
            @Override
            public void run() {
                orginalHeigh = img.getHeight();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                ValueAnimator animator = ValueAnimator.ofInt(img.getHeight(), orginalHeigh);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        newHeight = (int) valueAnimator.getAnimatedValue();
                        updateHeight(newHeight);
                    }
                });
                animator.setInterpolator(new OvershootInterpolator());
                animator.setDuration(500);
                animator.start();
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void updateHeight(int newHeight) {
        if (newHeight < intrinsicHeight) {
            img.getLayoutParams().height = newHeight;
            img.requestLayout();
        }
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (deltaY < 0 && isTouchEvent) {
            newHeight = (int) (img.getHeight() + Math.abs(deltaY) / 3.0f);
            updateHeight(newHeight);
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }
}
