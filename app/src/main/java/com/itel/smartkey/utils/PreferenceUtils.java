package com.itel.smartkey.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by huorong.liang on 2017/1/10.
 */

public class PreferenceUtils {

    private static final String SHAREPREFERENCE_NAME = "my_action_pre";

    /**
     * 保存int值到shareference中
     * @param context
     * @param name
     * @param value
     * @return
     */
    public static boolean putInt(Context context, String name, int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE | Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, value);
        return editor.commit();
    }

    /**
     * 从sharePreference中获取int值，若取不到，默认返回-1
     * @param context 上下文对象
     * @param name key
     * @return value
     */
    public static int getInt(Context context, String name){
       return getInt( context,  name, -1);
    }

    /**
     * 从sharePreference中获取int值，若取不到，默认返回defaultVale
     * @param context 上下文
     * @param name key
     * @param defaultVale defaultVale
     * @return value
     */
    public static int getInt(Context context, String name, int defaultVale){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE|Context.MODE_APPEND);
        int value = sharedPreferences.getInt(name, defaultVale);
        return value;
    }

    /**
     * 保存String类型的值到sharePreference中
     * @param context 上下文对象
     * @param name key
     * @param value value
     * @return 是否保存成功
     */
    public static boolean putString(Context context, String name, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_APPEND|Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        return editor.commit();
    }

    /**
     * 从sharePreference中获取String值，若取不到，默认返回null
     * @param context 上下文对象
     * @param name key
     * @return value
     */
    public static String getString(Context context, String name){
        return getString(context, name, null);
    }

    /**
     * 从sharePreference中获取String值，若取不到，默认返回defaultValue
     * @param context 上下文对象
     * @param name key
     * @param defaultValue defaultValue
     * @return value
     */
    public static String getString(Context context, String name, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE|Context.MODE_APPEND);
        return sharedPreferences.getString(name, defaultValue);
    }

    /**
     * 保存long类型的值到sharePreference中
     * @param context 上下文对象
     * @param key keyName
     * @param value value
     * @return 是否保存成功
     */
    public static boolean putLong(Context context, String key, long value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE|Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 从sharePreference中获取long类型的值，若取不到，默认返回-1
     * @param context 上下文对象
     * @param key key
     * @return value
     */
    public static long getLong(Context context, String key){
        return getLong(context, key, -1);
    }

    /**
     * 从sharePreference中获取long类型的值，若取不到，默认返回defaultValue
     * @param context 上下文对象
     * @param key key
     * @param defaultValue defaultValue
     * @return value
     */
    public static long getLong(Context context, String key, long defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE|Context.MODE_APPEND);
        return sharedPreferences.getLong(key,defaultValue);
    }


    /**
     * 保存boolean类型的值到sharePreference中
     * @param context 上下文对象
     * @param key keyName
     * @param value value
     * @return 是否保存成功
     */
    public static boolean putBoolean(Context context, String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE|Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 从sharePreference中获取boolean类型的值，若取不到，默认返回defaultValue
     * @param context 上下文对象
     * @param key key
     * @param defaultValue defaultValue
     * @return value
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE|Context.MODE_APPEND);
        return sharedPreferences.getBoolean(key, defaultValue);
    }


    /**
     * 从sharePreference中获取boolean类型的值，若取不到，默认返回false
     * @param context 上下文对象
     * @param key key
     * @return value
     */
    public static boolean getBoolean(Context context, String key){
        return getBoolean(context, key, false);
    }

    /**
     * 保存float类型的值到sharePreference中
     * @param context 上下文对象
     * @param key keyName
     * @param value value
     * @return 是否保存成功
     */
    public static boolean putFloat(Context context, String key, float value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE|Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 从sharePreference中获取float类型的值，若取不到，默认返回defaultValue
     * @param context 上下文对象
     * @param key key
     * @param defaultValue defaultValue
     * @return value
     */
    public static float getFloat(Context context, String key, float defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE|Context.MODE_APPEND);
        return sharedPreferences.getFloat(key, defaultValue);
    }


    /**
     * 从sharePreference中获取float类型的值，若取不到，默认返回-1
     * @param context 上下文对象
     * @param key key
     * @return value
     */
    public static float getFloat(Context context, String key){
        return getFloat(context, key, -1);
    }
}
