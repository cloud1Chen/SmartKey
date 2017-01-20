package com.itel.smartkey.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;

/**
 * DBmanager
 * Created by huorong.liang on 2017/1/19.
 */

public class DBManager {
    private final static String dbName = "function_db";
    private static DBManager mInstance;
    private HMROpenHelper openHelper;
    private Context context;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public DBManager(Context context) {
        this.context = context;
        openHelper = new HMROpenHelper(context, dbName, null);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }


    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new HMROpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }


    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new HMROpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }


    public DaoMaster getDaomaster(){
        if (mDaoMaster == null){
            mDaoMaster = new DaoMaster(getWritableDatabase());
        }
        return mDaoMaster;
    }

    public DaoSession getDaoSession(){
        if (mDaoSession == null){
            mDaoSession = getDaomaster().newSession();
        }
        return mDaoSession;
    }


}
