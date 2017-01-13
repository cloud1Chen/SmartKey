package com.itel.smartkey.utils;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * 打开下拉菜单的工具类
 * Created by huorong.liang on 2017/1/11.
 */

public class StatusBarUtils {
    public static void openStatusBar(Context mContext){
        Log.d("LHRTAG", "openStatusBar()");
        try{
            Object service = mContext.getSystemService("statusbar");
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod("expand");
            expand.invoke(service);
            Log.d("LHRTAG", "-------------2");
        }catch(NoSuchMethodException e)
        {
            try{
                Object obj = mContext.getSystemService("statusbar");
                Class.forName("android.app.StatusBarManager")
                        .getMethod("expandNotificationsPanel", new Class[0])//expandSettingsPanel  expandNotificationsPanel
                        .invoke(obj, (Object[]) null);
                Log.d("LHRTAG", "-------------3");

            }catch(Exception e2){
            }
        }catch(Exception e){
        }
    }
}
