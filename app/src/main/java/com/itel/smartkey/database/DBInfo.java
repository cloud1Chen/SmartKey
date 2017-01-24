package com.itel.smartkey.database;

/**
 * 数据库信息类，主要是保存一些数据库的版本，名字，及数据库表的创建语句和表的信息等，通过这个类记录，方便操作
 *
 * @author loonggg
 *
 */
public class DBInfo {
    /**
     * 数据库信息
     *
     * @author loonggg
     *
     */
    public static class DB {
        // 数据库名称
        public static final String DB_NAME = "user.db";
        // 数据库的版本号
        public static final int DB_VERSION = 1;
    }
    /**
     * 数据库表的信息
     *
     * @author loonggg
     *
     */
    public static class Table {
        public static final String USER_INFO_TB_NAME = "function_table";
        public static final String USER_SET_TB_NAME = "set_table";
        //id name icon function function_type single_click_enable double_click_enable long_click_enable close_enable lock_enable
        public static final String USER_INFO_CREATE = "CREATE TABLE IF NOT EXISTS "
                + USER_INFO_TB_NAME
                + " ( "
                + "_id INTEGER PRIMARY KEY" + ","
                + "id INTEGER" + ","
                + "name TEXT" + ","
                + "icon INTEGER" + ","
                + "function TEXT" + ","
                + "function_type INTEGER" + ","
                + "function_app_type INTEGER" + ","
                + "parameter TEXT" + ","
                + "single_click_enable BLOB" + ","
                + "double_click_enable BLOB" + ","
                + "long_click_enable BLOB" + ","
                + "close_enable BLOB" + ","
                + "lock_enable BLOB" + ","

                + "data TEXT"
                +")";
/*
funcAcId funcAcName funcAcNameId funcAcIconId funcAcIconPath funcAcIconBytes data1 funcId
 */
        public static final String USER_SET_CREATE = "CREATE TABLE IF NOT EXISTS "
                + USER_SET_TB_NAME
                + " ( "
                + "_id INTEGER PRIMARY KEY" + ","
                + "funcAcId INTEGER" + ","
                + "funcAcName TEXT" + ","
                + "funcAcNameId INTEGER" + ","
                + "funcAcIconId INTEGER" + ","
                + "funcAcIconPath TEXT" + ","
                + "funcAcIconBytes BLOB" + ","
                + "data1 TEXT" + ","
                + "data2 TEXT" + ","
                + "data3 TEXT" + ","
                + "data4 TEXT" + ","
                + "data5 TEXT" + ","
                + "data6 TEXT" + ","
                + "data7 TEXT" + ","
                + "funcId INTEGER" + ","

                + "data TEXT"
                +")";
        public static final String USER_INFO_DROP = "DROP TABLE"
                + USER_INFO_TB_NAME;
        public static final String USER_SET_DROP = "DROP TABLE"
                + USER_SET_TB_NAME;
    }
}