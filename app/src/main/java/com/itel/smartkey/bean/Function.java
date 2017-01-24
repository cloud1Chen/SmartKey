package com.itel.smartkey.bean;


import java.io.Serializable;

public class Function implements Serializable{

    //id name icon function function_type parameter single_click_enable double_click_enable long_click_enable close_enable lock_enable
    public static final int FUN_TYPE_OPEN_APP = 1;
    public static final int FUN_TYPE_RUN_METHOD = 2;
    public static final int FUN_TYPE_RUN_EXTERNAL = 3;
    public static final int METHOD_TYPE_OPEN_FLASHLIGHT = 4;
    public static final int METHOD_TYPE_OPEN_NOTIFICATION = 5;
    public static final int APP_TYPE_INTERNAL_APP = 2;
    public static final int APP_TYPE_EXTERNAL_APP = 1;


    private int id = -1;
    private String name = null;
    private String icon = null;
    private String function = null;
    private int function_type = -1;//1： intent的方式 2：方法的方式
    private int function_app_type = -1;//1: 代表打开读取的手机上安装的intent  2：代表打开内置的intent

    public int getFunction_app_type() {
        return function_app_type;
    }

    public void setFunction_app_type(int function_app_type) {
        this.function_app_type = function_app_type;
    }

    private String parameter = null;
    private boolean single_click_enable = true;
    private boolean double_click_enable = true;
    private boolean long_click_enable = true;
    private boolean close_enable = false;
    private boolean lock_enable = false;
    private String parameter_extra = null;



    public String getParameter_extra() {
        return parameter_extra;
    }

    public void setParameter_extra(String parameter_extra) {
        this.parameter_extra = parameter_extra;
    }

    public int getId()
    {
        return this.id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getFunction()
    {
        return this.function;
    }
    public void setFunction(String function)
    {
        this.function = function;
    }

    public String getIcon()
    {
        return this.icon;
    }
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public int getFunction_type()
    {
        return this.function_type;
    }
    public void setFunction_type(int function_type)
    {
        this.function_type = function_type;
    }

    public String getParameter()
    {
        return this.parameter;
    }
    public void setParameter(String parameter)
    {
        this.parameter = parameter;
    }

    public boolean getSingleCick()
    {
        return this.single_click_enable;
    }
    public void setSingleClick(boolean singleClick)
    {
        this.single_click_enable = singleClick;
    }

    public boolean getDoubleClick()
    {
        return this.double_click_enable;
    }
    public void setDoubleClick(boolean doubleClick)
    {
        this.double_click_enable = doubleClick;
    }

    public boolean getLongClick()
    {
        return this.long_click_enable;
    }
    public void setLongClick(boolean longClick)
    {
        this.long_click_enable = longClick;
    }

    public boolean getCloseEnable()
    {
        return this.close_enable;
    }
    public void setCloseEnable(boolean closeEnable)
    {
        this.close_enable = closeEnable;
    }

    public boolean getLockEnable()
    {
        return this.lock_enable;
    }
    public void setLockEnable(boolean lockEnable)
    {
        this.lock_enable = lockEnable;
    }
}