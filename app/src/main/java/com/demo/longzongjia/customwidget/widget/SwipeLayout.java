package com.demo.longzongjia.customwidget.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by longzongjia on 2018/3/14.
 */

public class SwipeLayout extends FrameLayout {

    private ViewDragHelper helper;
    private View mContent;
    private View mBehind;
    private int mWidth;
    private int mHeight;
    private int mRange;
    boolean isOpen = false;
    private Status status = Status.CLOSE;
    private int downX;
    private int downY;

    public enum Status {
        OPEN,
        CLOSE,
        SWIPING
    }

    public SwipeLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        helper = ViewDragHelper.create(this, callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        /**
         * 是否能拖拽
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 空间位置改变时的操作
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mBehind) {
                mContent.offsetLeftAndRight(dx);
            } else if (changedView == mContent) {
                mBehind.offsetLeftAndRight(dx);
            }
            dispactEvent();
            invalidate();
        }

        /**
         * 松开手指时是执行的方法
         *
         * @param xvel x方向加速度
         * @param yvel y方向加速度
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (xvel == 0 && mContent.getLeft() < -mRange * 0.5f) {
                openItem();
            } else if (xvel < 0) {
                openItem();
            } else {
                closeItem();
            }
        }

        /**
         * 获取拖拽的范围
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mRange;
        }

        /**
         * 限制拖拽的范围
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mContent) {
                if (left < -mRange) {
                    left = -mRange;
                } else if (left > 0) {
                    left = 0;
                }
            }
            if (child == mBehind) {
                if (left < mWidth - mRange) {
                    left = mWidth - mRange;
                } else if (left > mWidth) {
                    left = mWidth;
                }
            }
            return left;
        }
    };

    /**
     * 关闭条目
     */
    private void closeItem() {
        close(true);
    }

    public void close(boolean isSmooth) {
        if (isSmooth) {
            if (helper.smoothSlideViewTo(mContent, 0, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutContent(false);
        }
    }

    /**
     * 打开条目
     */
    private void openItem() {
        open(true);
    }

    private void open(boolean isSmooth) {
        if (isSmooth) {
            if (helper.smoothSlideViewTo(mContent, -mRange, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutContent(true);
        }
    }

    @Override
    public void computeScroll() {
        if (helper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     *
     */
    private void dispactEvent() {
        Status lastStatus = status;
        status = updateStatus();
        if (status != lastStatus && swipingListener != null) {
            if (status == Status.OPEN) {
                System.out.println("打开状态");
                swipingListener.onOpened(this);
            } else if (status == Status.CLOSE) {
                System.out.println("关闭状态");
                swipingListener.onClosed(this);
            } else if (status == Status.SWIPING) {
                if (lastStatus == Status.CLOSE) {
                    System.out.println("正在打开状态");
                    swipingListener.onSwiping(this);
                }
            }
        }
    }

    private Status updateStatus() {
        int left = mContent.getLeft();
        if (left == -mRange) {
            return Status.OPEN;
        } else if (left == 0) {
            return Status.CLOSE;
        }
        return Status.SWIPING;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContent = getChildAt(1);
        mBehind = getChildAt(0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRange = mBehind.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutContent(isOpen);
    }

    private void layoutContent(boolean isOpen) {
        Rect contentRect = computeContentRect(isOpen);
        mContent.layout(contentRect.left, contentRect.top, contentRect.right, contentRect.bottom);
        Rect behindRect = computeBehindRect(contentRect);
        mBehind.layout(behindRect.left, behindRect.top, behindRect.right, behindRect.bottom);
    }

    private Rect computeBehindRect(Rect contentRect) {
        return new Rect(contentRect.right, 0, contentRect.right + mRange, mHeight);
    }

    private Rect computeContentRect(boolean isOpen) {
        int left = 0;
        if (isOpen) {
            left = -mRange;
        } else {
            left = 0;
        }
        return new Rect(left, 0, left + mWidth, mHeight);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                //按下时请求父控件不要拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                int diffX = moveX - downX;
                int diffY = moveY - downY;
                //当上下滑时请求父控件拦截
                if (Math.abs(diffX) < Math.abs(diffY)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    //其它情况不拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return helper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            helper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public OnSwipingListener swipingListener;

    public void setOnSwipingListener(OnSwipingListener swipingListener) {
        this.swipingListener = swipingListener;
    }

    public interface OnSwipingListener {
        void onOpened(SwipeLayout layout);

        void onClosed(SwipeLayout layout);

        void onSwiping(SwipeLayout layout);
    }

}
