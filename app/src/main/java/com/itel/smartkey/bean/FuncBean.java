package com.itel.smartkey.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by huorong.liang on 2017/1/19.
 */
@Entity
public class FuncBean {
    @Id
    private Long funcId;
    @Property
    private String funcName;
    @Property
    private int funcNameId;
    @Property
    private int funcType;
    @Property
    private String funcSubscribe1;
    @Property
    private String funcSubscribe2;
    @Property
    private String funcSubscribe3;
    @Property
    private String funcSubscribe4;
    @Property
    private String funcSubscribe5;
    @Property
    private int iconId;
    @Property
    private boolean actionClick;
    @Property
    private boolean actionDoubleClick;
    @Property
    private boolean actionLongClick;
    @Generated(hash = 346687992)
    public FuncBean(Long funcId, String funcName, int funcNameId, int funcType,
            String funcSubscribe1, String funcSubscribe2, String funcSubscribe3,
            String funcSubscribe4, String funcSubscribe5, int iconId,
            boolean actionClick, boolean actionDoubleClick,
            boolean actionLongClick) {
        this.funcId = funcId;
        this.funcName = funcName;
        this.funcNameId = funcNameId;
        this.funcType = funcType;
        this.funcSubscribe1 = funcSubscribe1;
        this.funcSubscribe2 = funcSubscribe2;
        this.funcSubscribe3 = funcSubscribe3;
        this.funcSubscribe4 = funcSubscribe4;
        this.funcSubscribe5 = funcSubscribe5;
        this.iconId = iconId;
        this.actionClick = actionClick;
        this.actionDoubleClick = actionDoubleClick;
        this.actionLongClick = actionLongClick;
    }
    @Generated(hash = 1753309699)
    public FuncBean() {
    }
    public Long getFuncId() {
        return this.funcId;
    }
    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }
    public String getFuncName() {
        return this.funcName;
    }
    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }
    public int getFuncNameId() {
        return this.funcNameId;
    }
    public void setFuncNameId(int funcNameId) {
        this.funcNameId = funcNameId;
    }
    public int getFuncType() {
        return this.funcType;
    }
    public void setFuncType(int funcType) {
        this.funcType = funcType;
    }
    public String getFuncSubscribe1() {
        return this.funcSubscribe1;
    }
    public void setFuncSubscribe1(String funcSubscribe1) {
        this.funcSubscribe1 = funcSubscribe1;
    }
    public String getFuncSubscribe2() {
        return this.funcSubscribe2;
    }
    public void setFuncSubscribe2(String funcSubscribe2) {
        this.funcSubscribe2 = funcSubscribe2;
    }
    public String getFuncSubscribe3() {
        return this.funcSubscribe3;
    }
    public void setFuncSubscribe3(String funcSubscribe3) {
        this.funcSubscribe3 = funcSubscribe3;
    }
    public String getFuncSubscribe4() {
        return this.funcSubscribe4;
    }
    public void setFuncSubscribe4(String funcSubscribe4) {
        this.funcSubscribe4 = funcSubscribe4;
    }
    public String getFuncSubscribe5() {
        return this.funcSubscribe5;
    }
    public void setFuncSubscribe5(String funcSubscribe5) {
        this.funcSubscribe5 = funcSubscribe5;
    }
    public int getIconId() {
        return this.iconId;
    }
    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
    public boolean getActionClick() {
        return this.actionClick;
    }
    public void setActionClick(boolean actionClick) {
        this.actionClick = actionClick;
    }
    public boolean getActionDoubleClick() {
        return this.actionDoubleClick;
    }
    public void setActionDoubleClick(boolean actionDoubleClick) {
        this.actionDoubleClick = actionDoubleClick;
    }
    public boolean getActionLongClick() {
        return this.actionLongClick;
    }
    public void setActionLongClick(boolean actionLongClick) {
        this.actionLongClick = actionLongClick;
    }
}
