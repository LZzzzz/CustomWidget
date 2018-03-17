package com.demo.longzongjia.customwidget.entity;

import android.support.annotation.NonNull;

import com.demo.longzongjia.customwidget.utils.PinYinUtils;


/**
 * Created by longzongjia on 2018/3/13.
 */

public class Person implements Comparable<Person> {
    private String name;
    private String pinyin;

    public Person(String name) {
        this.name = name;
        this.pinyin = PinYinUtils.getPinYing(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public int compareTo(@NonNull Person person) {
        return this.pinyin.compareTo(person.pinyin);
    }
}
