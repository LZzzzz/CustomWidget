package com.demo.longzongjia.customwidget.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.longzongjia.customwidget.R;
import com.demo.longzongjia.customwidget.adaper.MyRecyclerCardviewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by longzongjia on 2018/3/13.
 */

public class ThreeFragment extends Fragment {


    @BindView(R.id.re)
    RecyclerView re;
    Unbinder unbinder;

    public static ThreeFragment getInstance(int type) {
        Bundle args = new Bundle();
        ThreeFragment fragment = new ThreeFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        List<String> title = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            title.add("私は気になります！" + i);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2, GridLayoutManager.VERTICAL,false);
        re.setLayoutManager(layoutManager);
        re.setAdapter(new MyRecyclerCardviewAdapter(getContext(), title));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
