package com.itel.smartkey.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by huorong.liang on 2017/1/19.
 */
@Entity
public class FuncActiveBean {
    @Id
    private Long funcAcId;//主键
    @Property
    private String funcAcName;//功能名称
    @Property
    private int funcAcNameId;//功能名称id
    @Property
    private int funcAcIconId;//功能id
    @Property
    private String funcAcIconPath;//功能图标的路径
    @Property
    private String data1;//url
    @Property
    private String data2;//packagename
    @Property
    private String data3;//activityclass
    @Property
    private String data4;//phonenumber
    @Property
    private String data5;//extra
    @Property
    private String data6;//extra
    @Property
    private String data7;//extra
    @Property
    private Long funcId;//对应表1的主键（事件TAG）
    @Generated(hash = 1804003016)
    public FuncActiveBean(Long funcAcId, String funcAcName, int funcAcNameId,
            int funcAcIconId, String funcAcIconPath, String data1, String data2,
            String data3, String data4, String data5, String data6, String data7,
            Long funcId) {
        this.funcAcId = funcAcId;
        this.funcAcName = funcAcName;
        this.funcAcNameId = funcAcNameId;
        this.funcAcIconId = funcAcIconId;
        this.funcAcIconPath = funcAcIconPath;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.data4 = data4;
        this.data5 = data5;
        this.data6 = data6;
        this.data7 = data7;
        this.funcId = funcId;
    }
    @Generated(hash = 1128188233)
    public FuncActiveBean() {
    }
    public Long getFuncAcId() {
        return this.funcAcId;
    }
    public void setFuncAcId(Long funcAcId) {
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
    public void setFuncAcIconId(int funcAcIconId) {
        this.funcAcIconId = funcAcIconId;
    }
    public String getFuncAcIconPath() {
        return this.funcAcIconPath;
    }
    public void setFuncAcIconPath(String funcAcIconPath) {
        this.funcAcIconPath = funcAcIconPath;
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
    public Long getFuncId() {
        return this.funcId;
    }
    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }

}
