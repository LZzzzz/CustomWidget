package com.demo.longzongjia.customwidget.adaper;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.longzongjia.customwidget.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by longzongjia on 2018/3/16.
 */

public class MyRecyclerCardviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> data;
    private Context context;
    private List<Integer> imglist;

    public static enum TYPE {
        ITEM_TYPE_IMAG,
        ITEM_TYPE_TEXT_IMAG
    }

    public MyRecyclerCardviewAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE.ITEM_TYPE_IMAG.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager, parent, false);
            return new ViewPagerHolder(view);
        } else if (viewType == TYPE.ITEM_TYPE_TEXT_IMAG.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_text, parent, false);
            return new ImgtextHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewPagerHolder) {
            imglist = new ArrayList<>();
            imglist.add(R.drawable.timg01);
            imglist.add(R.drawable.timg02);
            imglist.add(R.drawable.timg03);
            imglist.add(R.drawable.timg04);
            ViewPager viewPager = ((ViewPagerHolder) holder).viewPager;
            viewPager.setAdapter(new ImageAdapter(context, imglist));
            viewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    28, context.getResources().getDisplayMetrics()));
            viewPager.setPageTransformer(false, new ScaleTransformer(context));
        } else if (holder instanceof ImgtextHolder) {
            float elevation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    10, context.getResources().getDisplayMetrics());
            ((ImgtextHolder) holder).cd.setElevation(elevation);
            ((ImgtextHolder) holder).tv.setText(data.get(position));
//            Random random = new Random();
//            int pick = random.nextInt(4);
//            if (imglist != null && pick <= 3) {
//                ((ImgtextHolder) holder).img.setImageResource(imglist.get(pick));
//            }

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position % 7 == 0) ? TYPE.ITEM_TYPE_IMAG.ordinal() : TYPE.ITEM_TYPE_TEXT_IMAG.ordinal();
    }


    public class ViewPagerHolder extends RecyclerView.ViewHolder {
        private ViewPager viewPager;

        public ViewPagerHolder(View itemView) {
            super(itemView);
            viewPager = (ViewPager) itemView.findViewById(R.id.viewpager);
        }
    }

    public class ImgtextHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private CardView cd;
        private ImageView img;

        public ImgtextHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_img);
            cd = (CardView) itemView.findViewById(R.id.cd);
            img = (ImageView) itemView.findViewById(R.id.img_item);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE.ITEM_TYPE_IMAG.ordinal()
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }
}
