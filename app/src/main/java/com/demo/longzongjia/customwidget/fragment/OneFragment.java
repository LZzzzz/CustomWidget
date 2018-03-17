package com.demo.longzongjia.customwidget.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.longzongjia.customwidget.R;
import com.demo.longzongjia.customwidget.adaper.PersonAdapter;
import com.demo.longzongjia.customwidget.commons.Array;
import com.demo.longzongjia.customwidget.entity.Person;
import com.demo.longzongjia.customwidget.widget.QuickIndexBar;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by longzongjia on 2018/3/13.
 */

public class OneFragment extends Fragment implements QuickIndexBar.OnLetterUpdateListener {

    @BindView(R.id.lv_name)
    ListView lvName;
    Unbinder unbinder;
    @BindView(R.id.quick_bar)
    QuickIndexBar quickBar;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    private ArrayList<Person> persons;

    public static OneFragment getInstance(int type) {
        Bundle args = new Bundle();
        OneFragment fragment = new OneFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        unbinder = ButterKnife.bind(this, view);
        // Model
        persons = new ArrayList<>();
        fillAndSortData(persons);
        lvName.setAdapter(new PersonAdapter(persons, view.getContext()));
        init();
        return view;
    }

    private void fillAndSortData(ArrayList<Person> persons) {
        // 填充
        for (int i = 0; i < Array.NAMES_2.length; i++) {
            String s = Array.NAMES_2[i];
            persons.add(new Person(s));
        }

        // 排序
        Collections.sort(persons);
    }

    private void init() {
        quickBar.setmOnLetterUpdateListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onLetterUpdate(String letter) {

        showLetter(letter);
        for (int i = 0; i < persons.size(); i++) {
            String l = persons.get(i).getPinyin().charAt(0) + "";
            if (TextUtils.equals(letter, l)) {
                // 找到第一个首字母是letter条目.
                lvName.setSelection(i);
                break;
            }
        }
    }

    private Handler mHandler = new Handler();

    private void showLetter(String letter) {
        tvCenter.setText(letter);
        tvCenter.setVisibility(View.VISIBLE);
        // 移除之前的延时操作
        mHandler.removeCallbacksAndMessages(null);
        // 延时两秒消除
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                tvCenter.setVisibility(View.GONE);
            }

        }, 500);
    }
}
