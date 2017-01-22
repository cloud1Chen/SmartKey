package com.itel.smartkey.bean;

/**
 * 需要排序的bean类，需要继承于这个类
 * Created by huorong.liang on 2017/1/22.
 */

public abstract class ShortModel {
    String sortLetter;
    String name;

    public String getSortLetter() {
        return sortLetter;
    }

    public void setSortLetter(String sortLetter) {
        this.sortLetter = sortLetter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
