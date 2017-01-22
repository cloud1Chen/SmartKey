package com.itel.smartkey.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * 选择应用界面的  应用  实体类
 * Created by huorong.liang on 2017/1/20.
 */

public class AppsBean extends ShortModel{
//    private String appsName;
    private String appsNameId;
    private Drawable iconDraw;
    private int iconId;
    private byte[] iconBytes;
    private String iconUri;
    private String iconPath;
    private Bitmap iconBitmap;

    public Bitmap getIconBitmap() {
        return iconBitmap;
    }

    public void setIconBitmap(Bitmap iconBitmap) {
        this.iconBitmap = iconBitmap;
    }

    private String packageName;
    private String className;

//    public String getAppsName() {
//        return appsName;
//    }
//
//    public void setAppsName(String appsName) {
//        this.appsName = appsName;
//    }

    public String getAppsNameId() {
        return appsNameId;
    }

    public void setAppsNameId(String appsNameId) {
        this.appsNameId = appsNameId;
    }

    public Drawable getIconDraw() {
        return iconDraw;
    }

    public void setIconDraw(Drawable iconDraw) {
        this.iconDraw = iconDraw;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public byte[] getIconBytes() {
        return iconBytes;
    }

    public void setIconBytes(byte[] iconBytes) {
        this.iconBytes = iconBytes;
    }

    public String getIconUri() {
        return iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


}
