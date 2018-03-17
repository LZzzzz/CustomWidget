package com.demo.longzongjia.customwidget.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.demo.longzongjia.customwidget.MainActivity;
import com.demo.longzongjia.customwidget.R;
import com.demo.longzongjia.customwidget.adaper.MyListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by longzongjia on 2018/3/16.
 */

public class FourFragment extends Fragment implements MainActivity.MyTouchListener {

    @BindView(R.id.lv_01)
    ListView lv01;
    private Unbinder unbinder;
    private MyListAdapter adapter;
    private MainActivity activity;

    public static FourFragment getInstance(int type) {
        Bundle args = new Bundle();
        FourFragment fragment = new FourFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        adapter = new MyListAdapter();
        lv01.setAdapter(adapter);
        lv01.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    adapter.closeAllItem();
                }

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        activity = (MainActivity) getActivity();
        activity.registerTouchListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDown(MotionEvent e) {
        if (adapter != null) {
            adapter.closeAllItem();
        }
    }
}
