package com.itel.smartkey.bean;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * 选择联系人界面的联系人的实体类
 * Created by huorong.liang on 2017/1/6.
 */

public class ContactsBean {
    private String name;
    private ArrayList<String> phones;
    private Bitmap contactsBitmap;
    private byte[] contactsPhotoBytes;

    public Bitmap getContactsBitmap() {
        return contactsBitmap;
    }

    public void setContactsBitmap(Bitmap contactsBitmap) {
        this.contactsBitmap = contactsBitmap;
    }

    public byte[] getContactsPhotoBytes() {
        return contactsPhotoBytes;
    }

    public void setContactsPhotoBytes(byte[] contactsPhotoBytes) {
        this.contactsPhotoBytes = contactsPhotoBytes;
    }

    public String getContactsPhotoString() {
        return contactsPhotoString;
    }

    public void setContactsPhotoString(String contactsPhotoString) {
        this.contactsPhotoString = contactsPhotoString;
    }

    private String contactsPhotoString;

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
