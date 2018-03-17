package com.demo.longzongjia.customwidget.adaper;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.longzongjia.customwidget.R;
import com.demo.longzongjia.customwidget.entity.Person;

import java.util.ArrayList;

/**
 * Created by longzongjia on 2018/3/13.
 */

public class PersonAdapter extends BaseAdapter {
    private ArrayList<Person> persons = new ArrayList<Person>();
    private final Context context;


    public PersonAdapter(ArrayList<Person> persons, Context context) {
        this.persons = persons;
        this.context = context;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        if (convertView == null){
            view = View.inflate(viewGroup.getContext(), R.layout.item_person,null);
        }else {
            view = convertView;
        }
        TextView tv_index = (TextView) view.findViewById(R.id.tv_index);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);

        Person person = persons.get(i);
        // 当前首字母
        String currentStr = person.getPinyin().charAt(0) + "";

        String indexStr = null;
        // 如果是第一个, 直接显示
        if(i == 0){
            indexStr = currentStr;
        }else {
            // 判断当前首字母和上一个条目的首字母是否一致, 不一致时候显示.
            String lastStr = persons.get(i - 1).getPinyin().charAt(0) + "";
            if(!TextUtils.equals(lastStr, currentStr)){
                // 不一致时候赋值indexStr
                indexStr = currentStr;
            }

        }

        tv_index.setVisibility(indexStr != null ?  View.VISIBLE : View.GONE);
        tv_index.setText(currentStr);
        tv_name.setText(person.getName());

        return view;
    }
}
