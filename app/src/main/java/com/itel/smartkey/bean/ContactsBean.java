package com.itel.smartkey.bean;

import java.util.ArrayList;

/**
 * 选择联系人界面的联系人的实体类
 * Created by huorong.liang on 2017/1/6.
 */

public class ContactsBean {
    private String name;
    private ArrayList<String> phones;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }
}
