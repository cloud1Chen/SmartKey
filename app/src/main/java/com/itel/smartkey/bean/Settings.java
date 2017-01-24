package com.itel.smartkey.bean;


import java.io.Serializable;

public class Settings implements Serializable {

    private int funcAcId;//主键
    private String funcAcName;//功能名称
    private int funcAcNameId;//功能名称id
    private int funcAcIconId;//功能id
    private String funcAcIconPath;//功能图标的路径
    private byte[] funcAcIconBytes;//功能图标的路径
    private String data1;//url
    private String data2;//packagename
    private String data3;//activityclass
    private String data4;//phonenumber
    private String data5;//方法的类型：如 4 代表手电筒  5 代表打开通知栏
    private String data6;
    private String data7;//extra
    private int funcId;//对应表1的主键（事件TAG）

    public int getFuncAcId() {
        return this.funcAcId;
    }

    public void setFuncAcId(int funcAcId) {
        this.funcAcId = funcAcId;
    }

    public String getFuncAcName() {
        return this.funcAcName;
    }

    public void setFuncAcName(String funcAcName) {
        this.funcAcName = funcAcName;
    }

    public int getFuncAcNameId() {
        return this.funcAcNameId;
    }

    public void setFuncAcNameId(int funcAcNameId) {
        this.funcAcNameId = funcAcNameId;
    }

    public int getFuncAcIconId() {
        return this.funcAcIconId;
    }

    public void setFuncAcIconId(int funcAcNameId) {
        this.funcAcIconId = funcAcIconId;
    }

    public String getFuncAcIconPath() {
        return this.funcAcIconPath;
    }

    public void setFuncAcIconPath(String funcAcIconPath) {
        this.funcAcIconPath = funcAcIconPath;
    }

    public byte[] getFuncAcIconBytes() {
        return this.funcAcIconBytes;
    }

    public void setFuncAcIconBytes(byte[] funcAcIconBytes) {
        this.funcAcIconBytes = funcAcIconBytes;
    }

    public String getData1() {
        return this.data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return this.data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return this.data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public String getData4() {
        return this.data4;
    }

    public void setData4(String data4) {
        this.data4 = data4;
    }

    public String getData5() {
        return this.data5;
    }

    public void setData5(String data5) {
        this.data5 = data5;
    }

    public String getData6() {
        return this.data6;
    }

    public void setData6(String data6) {
        this.data6 = data6;
    }

    public String getData7() {
        return this.data7;
    }

    public void setData7(String data7) {
        this.data7 = data7;
    }

    public int getFuncId() {
        return this.funcId;
    }

    public void setFuncId(int funcId) {
        this.funcId = funcId;
    }
}