package com.demo.longzongjia.customwidget.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.demo.longzongjia.customwidget.R;
import com.demo.longzongjia.customwidget.commons.Array;
import com.demo.longzongjia.customwidget.widget.ParallexListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by longzongjia on 2018/3/13.
 */

public class TwoFragment extends Fragment {

    @BindView(R.id.lv)
    ParallexListView lv;
    Unbinder unbinder;

    public static TwoFragment getInstance(int type) {
        Bundle args = new Bundle();
        TwoFragment fragment = new TwoFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_two, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        lv.setAdapter(new ArrayAdapter<>(inflate.getContext(), android.R.layout.simple_list_item_1, Array.NAMES));
        lv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
