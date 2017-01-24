package com.itel.smartkey.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 数据库帮助类，继承android自带的SQLiteOpenHelper 主要用于数据库的创建与更新
 *
 * @author loonggg
 *
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, DBInfo.DB.DB_NAME, null, DBInfo.DB.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBInfo.Table.USER_INFO_CREATE);
        db.execSQL(DBInfo.Table.USER_SET_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBInfo.Table.USER_INFO_DROP);
        db.execSQL(DBInfo.Table.USER_SET_DROP);
        onCreate(db);
    }
}
