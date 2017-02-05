package com.itel.smartkey.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.itel.smartkey.bean.Function;
import com.itel.smartkey.bean.Settings;
import com.itel.smartkey.database.DBHelper;
import com.itel.smartkey.database.DBInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 数据库操作类，这个类主要的功能是：存放数据库操作的一些方法 这里有一些例子：包含数据库的增删改查，分别有两种方法的操作，各有优缺点，都在解释中
 *
 * @author loonggg
 *
 */
public class DBService {
    private DBHelper dbHelper = null;
    public DBService(Context context) {
        dbHelper = new DBHelper(context);
    }
    /**
     * 添加一条记录到数据库
     *
     * @param id
     * @param name
     */
    public void add(String id, String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 不好之处：无返回值，无法判断是否插入成功
        db.execSQL("insert into user_table (userId,userName) values (?,?)",
                new Object[] { id, name });
        db.close();
    }
    public long addAndroid(String id, String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", id);
        values.put("userName", name);
        // 好处：有返回值
        long result = db.insert(DBInfo.Table.USER_INFO_TB_NAME, null, values);// 返回值是插入的是第几行，大于0代表添加成功
        db.close();
        return result;
    }

    //id name icon function function_type single_click_enable double_click_enable long_click_enable
    public long addAndroid(Function fun) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", fun.getName());
        values.put("icon", fun.getIcon());
        values.put("function", fun.getFunction());
        values.put("function_type", fun.getFunction_type());
        values.put("function_app_type", fun.getFunction_app_type());
        values.put("parameter", fun.getParameter());
        values.put("single_click_enable", fun.getSingleCick());
        values.put("double_click_enable", fun.getDoubleClick());
        values.put("long_click_enable", fun.getLongClick());
        values.put("close_enable", fun.getCloseEnable());
        values.put("lock_enable", fun.getLockEnable());
        values.put("data", fun.getParameter_extra());
        // 好处：有返回值
        long result = db.insert(DBInfo.Table.USER_INFO_TB_NAME, null, values);// 返回值是插入的是第几行，大于0代表添加成功
        Log.d("jlog", "add:" + result);
        db.close();
        return result;
    }

    public long addFunction(Function fun) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", fun.getId());
        values.put("name", fun.getName());
        values.put("icon", fun.getIcon());
        values.put("function", fun.getFunction());
        values.put("function_type", fun.getFunction_type());
        values.put("function_app_type", fun.getFunction_app_type());
        values.put("parameter", fun.getParameter());
        values.put("single_click_enable", fun.getSingleCick());
        values.put("double_click_enable", fun.getDoubleClick());
        values.put("long_click_enable", fun.getLongClick());
        values.put("close_enable", fun.getCloseEnable());
        values.put("lock_enable", fun.getLockEnable());
        values.put("data", fun.getParameter_extra());
        // 好处：有返回值
        long result = db.insert(DBInfo.Table.USER_INFO_TB_NAME, null, values);// 返回值是插入的是第几行，大于0代表添加成功
        Log.d("jlog", "add:" + result);
        db.close();
        return result;
    }
/*
funcAcId funcAcName funcAcNameId funcAcIconId funcAcIconPath funcAcIconBytes data1 funcId
 */
    public long addSetting(Settings set) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("funcAcId", set.getFuncAcId());
        values.put("funcAcName", set.getFuncAcName());
        values.put("funcAcNameId", set.getFuncAcNameId());
        values.put("funcAcIconId", set.getFuncAcIconId());
        values.put("funcAcIconPath", set.getFuncAcIconPath());
        values.put("funcAcIconBytes", set.getFuncAcIconBytes());
        values.put("data1", set.getData1());
        values.put("data2", set.getData2());
        values.put("data3", set.getData3());
        values.put("data4", set.getData4());
        values.put("data5", set.getData5());
        values.put("data6", set.getData6());
        values.put("data7", set.getData7());
        values.put("funcId", set.getFuncId());
        // 好处：有返回值
        long result = db.insert(DBInfo.Table.USER_SET_TB_NAME, null, values);// 返回值是插入的是第几行，大于0代表添加成功
        Log.d("jlog", "add:" + result);
        db.close();
        return result;
    }
    /**
     * 查询某条记录是否存在
     *
     * @param name
     * @return
     */
    public boolean find(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from user_table where userName = ?",
                new String[] { name });
        boolean result = cursor.moveToNext();
        db.close();
        return result;
    }


    /**
     * 查询表是否存在数据
     * @param
     * @return
     */
    public boolean isTableNoRecord(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from function_table", null);
        boolean isNoRecord = !cursor.moveToFirst();
        cursor.close();
        db.close();
        return isNoRecord;
    }




    public Function findAndroid(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.USER_INFO_TB_NAME, null, "name = ?",
                new String[] { name }, null, null, null);

        boolean result = cursor.moveToNext();// true代表查找到了
        Function fun = null;
        Log.d("jlog", "find");
        if(result == true)
        {
            String str = cursor.getString(cursor.getColumnIndex("name"));
            Log.d("jlog", "name:" + str);
            fun = new Function();
            fun.setId(cursor.getInt(cursor.getColumnIndex("id")));
            fun.setName(cursor.getString(cursor.getColumnIndex("name")));
            fun.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
            fun.setFunction(cursor.getString(cursor.getColumnIndex("function")));
            fun.setFunction_type(cursor.getInt(cursor.getColumnIndex("function_type")));
            fun.setFunction_app_type(cursor.getInt(cursor.getColumnIndex("function_app_type")));
            fun.setParameter(cursor.getString(cursor.getColumnIndex("parameter")));
            fun.setSingleClick(cursor.getInt(cursor.getColumnIndex("single_click_enable"))>0);
            fun.setDoubleClick(cursor.getInt(cursor.getColumnIndex("double_click_enable"))>0);
            fun.setLongClick(cursor.getInt(cursor.getColumnIndex("long_click_enable"))>0);
            fun.setCloseEnable(cursor.getInt(cursor.getColumnIndex("close_enable"))>0);
            fun.setLockEnable(cursor.getInt(cursor.getColumnIndex("lock_enable"))>0);
            fun.setParameter_extra(cursor.getString(cursor.getColumnIndex("data")));
        }
        db.close();
        return fun;
    }

    /**
     * 通过 id 查找settings表中的某一行
     * @param id
     * @return
     */
    public Settings findSettingsById(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.USER_SET_TB_NAME, null, "funcAcId = ?",
                new String[] { id }, null, null, null);
        //funcAcId funcAcName funcAcNameId funcAcIconId funcAcIconPath funcAcIconBytes data1 funcId
        boolean result = cursor.moveToNext();// true代表查找到了
        Settings set = null;
        Log.d("jlog", "find");
        if(result == true)
        {
//            String str = cursor.getString(cursor.getColumnIndex("id"));
//            Log.d("jlog", "id:" + str);
            set = new Settings();
            set.setFuncAcId(cursor.getInt(cursor.getColumnIndex("funcAcId")));
            set.setFuncAcName(cursor.getString(cursor.getColumnIndex("funcAcName")));
            set.setFuncAcNameId(cursor.getInt(cursor.getColumnIndex("funcAcNameId")));
            set.setFuncAcIconId(cursor.getInt(cursor.getColumnIndex("funcAcIconId")));
            set.setFuncAcIconPath(cursor.getString(cursor.getColumnIndex("funcAcIconPath")));
            //set.setFuncAcIconBytes(cursor.gcursor.getColumnIndex("funcAcIconBytes")));
            set.setFuncAcIconBytes(cursor.getBlob(cursor.getColumnIndex("funcAcIconBytes")));
            set.setData1(cursor.getString(cursor.getColumnIndex("data1")));
            set.setData2(cursor.getString(cursor.getColumnIndex("data2")));
            set.setData3(cursor.getString(cursor.getColumnIndex("data3")));
            set.setData4(cursor.getString(cursor.getColumnIndex("data4")));
            set.setData5(cursor.getString(cursor.getColumnIndex("data5")));
            set.setData6(cursor.getString(cursor.getColumnIndex("data6")));
            set.setData7(cursor.getString(cursor.getColumnIndex("data7")));
            set.setFuncId(cursor.getInt(cursor.getColumnIndex("funcId")));

        }
        cursor.close();
        db.close();
        return set;
    }

    public Function findFunction(String id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.USER_INFO_TB_NAME, null, "id = ?",
                new String[] { id }, null, null, null);

        boolean result = cursor.moveToNext();// true代表查找到了
        Log.d("jlog" , "result" + result);
        Function fun = null;
        Log.d("jlog", "find " + id);
        if(result == true)
        {
            int i = cursor.getInt(cursor.getColumnIndex("id"));
            Log.d("jlog", "name:" + i);
            fun = new Function();
            fun.setId(cursor.getInt(cursor.getColumnIndex("id")));
            fun.setName(cursor.getString(cursor.getColumnIndex("name")));
            fun.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
            fun.setFunction(cursor.getString(cursor.getColumnIndex("function")));
            fun.setFunction_type(cursor.getInt(cursor.getColumnIndex("function_type")));
            fun.setFunction_app_type(cursor.getInt(cursor.getColumnIndex("function_app_type")));
            fun.setParameter(cursor.getString(cursor.getColumnIndex("parameter")));
            fun.setSingleClick(cursor.getInt(cursor.getColumnIndex("single_click_enable"))>0);
            fun.setDoubleClick(cursor.getInt(cursor.getColumnIndex("double_click_enable"))>0);
            fun.setLongClick(cursor.getInt(cursor.getColumnIndex("long_click_enable"))>0);
            fun.setCloseEnable(cursor.getInt(cursor.getColumnIndex("close_enable"))>0);
            fun.setLockEnable(cursor.getInt(cursor.getColumnIndex("lock_enable"))>0);
            fun.setParameter_extra(cursor.getString(cursor.getColumnIndex("data")));
        }
        db.close();
        return fun;
    }
    /**
     * 修改一条记录
     *
     * @param id
     * @param name
     */
    public void update(String id, String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 缺点无返回值
        db.execSQL("update user_table set userName = ? where userId = ?",
                new Object[] { name, id });
        db.close();
    }
    public int updateAndroid(String id, String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", name);
        // 返回值大于0代表修改更新成功
        int result = db.update(DBInfo.Table.USER_INFO_TB_NAME, values, "userId = ?",
                new String[] { id });
        db.close();
        return result;
    }

    public int updateFunction(Function fun) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("funcId", fun.getId());
        values.put("name", fun.getName());
        values.put("icon", fun.getIcon());
        values.put("function", fun.getFunction());
        values.put("function_type", fun.getFunction_type());
        values.put("function_app_type", fun.getFunction_app_type());
        values.put("parameter", fun.getParameter());
        values.put("single_click_enable", fun.getSingleCick());
        values.put("double_click_enable", fun.getDoubleClick());
        values.put("long_click_enable", fun.getLongClick());
        values.put("close_enable", fun.getCloseEnable());
        values.put("lock_enable", fun.getLockEnable());
        values.put("data", fun.getParameter_extra());
        // 返回值大于0代表修改更新成功
        int result = db.update(DBInfo.Table.USER_INFO_TB_NAME, values, "id = ?",
                new String[] { fun.getId() + "" });
        db.close();
        return result;
    }
    //add for test
    public int updateSettingsById(Settings set, int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("funcAcId", set.getFuncAcId());
        Log.d("LHRTAG", "updateSettingsById funcAcId " + set.getFuncAcId());
        values.put("funcAcName", set.getFuncAcName());
        values.put("funcAcNameId", set.getFuncAcNameId());
        values.put("funcAcIconId", set.getFuncAcIconId());
        values.put("funcAcIconPath", set.getFuncAcIconPath());
        values.put("funcAcIconBytes", set.getFuncAcIconBytes());
        values.put("data1", set.getData1());
        values.put("data2", set.getData2());
        values.put("data3", set.getData3());
        values.put("data4", set.getData4());
        values.put("data5", set.getData5());
        values.put("data6", set.getData6());
        values.put("data7", set.getData7());
        values.put("funcId", set.getFuncId());
        // 返回值大于0代表修改更新成功
        int result = db.update(DBInfo.Table.USER_SET_TB_NAME, values, "_id = ?",
                new String[] { id + "" });
        db.close();
        return result;
    }
    //add end

    public int updateSettings(Settings set) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("funcAcId", set.getFuncAcId());
        values.put("funcAcName", set.getFuncAcName());
        values.put("funcAcNameId", set.getFuncAcNameId());
        values.put("funcAcIconId", set.getFuncAcIconId());
        values.put("funcAcIconPath", set.getFuncAcIconPath());
        values.put("funcAcIconBytes", set.getFuncAcIconBytes());
        values.put("data1", set.getData1());
        values.put("data2", set.getData2());
        values.put("data3", set.getData3());
        values.put("data4", set.getData4());
        values.put("data5", set.getData5());
        values.put("data6", set.getData6());
        values.put("data7", set.getData7());
        values.put("funcId", set.getFuncId());
        // 返回值大于0代表修改更新成功
        int result = db.update(DBInfo.Table.USER_SET_TB_NAME, values, "funcAcId = ?",
                new String[] { set.getFuncAcId() + "" });
        db.close();
        return result;
    }
    /**
     * 删除一条记录
     *
     * @param name
     */
    public void delete(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from user_table where name = ?",
                new String[] { name });
        db.close();
    }
    public int deleteAndroid(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(DBInfo.Table.USER_INFO_TB_NAME, "name = ?",
                new String[] { name });// 返回值为受影响的行数，大于0代表成功
        db.close();
        return result;
    }
    /**
     * 返回所有的数据库信息
     *
     * @return
     */
    public List<HashMap<String, String>> findAll() {
        List<HashMap<String, String>> list = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user_table", null);
        if (cursor.getCount() > 0) {
            list = new ArrayList<HashMap<String, String>>();
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor
                        .getColumnIndex("id"));
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", id);
                map.put("name", name);
                list.add(map);
            }
        }
        cursor.close();
        db.close();
        return list;
    }
    public List<HashMap<String, String>> findAllAndroid() {
        List<HashMap<String, String>> list = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.USER_INFO_TB_NAME, new String[] {
                "userId", "userName" }, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            list = new ArrayList<HashMap<String, String>>();
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor
                        .getColumnIndex("id"));
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", id);
                map.put("name", name);
                list.add(map);
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Function> findAllFunction() {
        List<Function> list = new ArrayList<Function>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.USER_INFO_TB_NAME, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Function fun = new Function();
                fun.setId(cursor.getInt(cursor.getColumnIndex("id")));
                fun.setName(cursor.getString(cursor.getColumnIndex("name")));
                fun.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                fun.setFunction(cursor.getString(cursor.getColumnIndex("function")));
                fun.setFunction_type(cursor.getInt(cursor.getColumnIndex("function_type")));
                fun.setFunction_app_type(cursor.getInt(cursor.getColumnIndex("function_app_type")));
                fun.setParameter(cursor.getString(cursor.getColumnIndex("parameter")));
                fun.setSingleClick(cursor.getInt(cursor.getColumnIndex("single_click_enable"))>0);
                fun.setDoubleClick(cursor.getInt(cursor.getColumnIndex("double_click_enable"))>0);
                fun.setLongClick(cursor.getInt(cursor.getColumnIndex("long_click_enable"))>0);
                fun.setCloseEnable(cursor.getInt(cursor.getColumnIndex("close_enable"))>0);
                fun.setLockEnable(cursor.getInt(cursor.getColumnIndex("lock_enable"))>0);
                fun.setParameter_extra(cursor.getString(cursor.getColumnIndex("data")));
                list.add(fun);
            }
        }
        cursor.close();
        db.close();
        return list;
    }


    /**
     * 根据funcacId查询set_table中的记录
     * @return
     */
    public List<Settings> findSettngsByfuncAcId(int funcAcId){
        List<Settings> mSettingsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.USER_SET_TB_NAME, null, "funcAcId = ?", new String[]{ funcAcId + "" }, null, null, null);
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                Settings settingsBean = new Settings();
                settingsBean.setFuncId(cursor.getInt(cursor.getColumnIndex("funcId")));
                settingsBean.setFuncAcName(cursor.getString(cursor.getColumnIndex("funcAcName")));
                settingsBean.setFuncAcNameId(cursor.getInt(cursor.getColumnIndex("funcAcNameId")));
                settingsBean.setFuncAcIconId(cursor.getInt(cursor.getColumnIndex("funcAcIconId")));
                settingsBean.setFuncAcIconPath(cursor.getString(cursor.getColumnIndex("funcAcIconPath")));
                settingsBean.setFuncAcIconBytes(cursor.getBlob(cursor.getColumnIndex("funcAcIconBytes")));
                settingsBean.setData1(cursor.getString(cursor.getColumnIndex("data1")));
                settingsBean.setData2(cursor.getString(cursor.getColumnIndex("data2")));
                settingsBean.setData3(cursor.getString(cursor.getColumnIndex("data3")));
                settingsBean.setData4(cursor.getString(cursor.getColumnIndex("data4")));
                settingsBean.setData5(cursor.getString(cursor.getColumnIndex("data5")));
                settingsBean.setData6(cursor.getString(cursor.getColumnIndex("data6")));
                settingsBean.setData7(cursor.getString(cursor.getColumnIndex("data7")));
                settingsBean.setFuncAcId(cursor.getInt(cursor.getColumnIndex("funcAcId")));
                mSettingsList.add(settingsBean);
            }
        }
        return mSettingsList;
    }






    public List<Settings> findAllSettings() {
        List<Settings> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.USER_SET_TB_NAME, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Settings set = new Settings();
                set.setFuncAcId(cursor.getInt(cursor.getColumnIndex("funcAcId")));
                set.setFuncAcName(cursor.getString(cursor.getColumnIndex("funcAcName")));
                set.setFuncAcNameId(cursor.getInt(cursor.getColumnIndex("funcAcNameId")));
                set.setFuncAcIconId(cursor.getInt(cursor.getColumnIndex("funcAcIconId")));
                set.setFuncAcIconPath(cursor.getString(cursor.getColumnIndex("funcAcIconPath")));
                //set.setFuncAcIconBytes(cursor.gcursor.getColumnIndex("funcAcIconBytes")));
                set.setFuncAcIconBytes(cursor.getBlob(cursor.getColumnIndex("funcAcIconBytes")));
                set.setData1(cursor.getString(cursor.getColumnIndex("data1")));
                set.setData2(cursor.getString(cursor.getColumnIndex("data2")));
                set.setData3(cursor.getString(cursor.getColumnIndex("data3")));
                set.setData4(cursor.getString(cursor.getColumnIndex("data4")));
                set.setData5(cursor.getString(cursor.getColumnIndex("data5")));
                set.setData6(cursor.getString(cursor.getColumnIndex("data6")));
                set.setData7(cursor.getString(cursor.getColumnIndex("data7")));
                set.setFuncId(cursor.getInt(cursor.getColumnIndex("funcId")));
                list.add(set);
            }
        }
        cursor.close();
        db.close();
        return list;
    }
}