package com.demo.longzongjia.customwidget.adaper;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by longzongjia on 2018/3/17.
 */

class ScaleTransformer implements ViewPager.PageTransformer {
    private Context context;
    private float elevation;

    public ScaleTransformer(Context context) {
        this.context = context;
        elevation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10, context.getResources().getDisplayMetrics());
    }

    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {

        } else {
            if (position < 0) {
                ((CardView) page).setCardElevation((1 + position) * elevation);
            } else {
                ((CardView) page).setCardElevation((1 - position) * elevation);
            }
        }
    }
}
