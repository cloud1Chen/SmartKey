package com.itel.smartkey.utils;


import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.itel.smartkey.bean.Function;
import com.itel.smartkey.bean.Settings;
import com.itel.smartkey.service.BroadcastService;
import com.itel.smartkey.service.DBService;

import static com.itel.smartkey.utils.Flashlight.isFlashLight;


public class Execute {
    public static final int MODE_SINGLE_CLICK = 1;
    public static final int MODE_DOUBLE_CLICK = 2;
    public static final int MODE_LONG_CLICK = 3;
    private static Context mContext;
    private static DBService mDBService;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static void run(Context context, int funcAcId, int mode, String data){
        mDBService = new DBService(context);
        Settings settingsBean = mDBService.findSettingsById(funcAcId + "");
        int funcId = settingsBean.getFuncId();
        Function functionBean = mDBService.findFunction(funcId + "");
        if (functionBean.getFunction_type() == Function.FUN_TYPE_RUN_METHOD) {//如果是通过打开方法类型执行
            Execute.runMethod(context, functionBean, settingsBean, -1, null);
        } else if (functionBean.getFunction_type() == Function.FUN_TYPE_OPEN_APP) {//通过打开app类型执行,这里又分为两种：1、通过选择app设置的方式  2、通过我们预置的方式
            Execute.openApp(context, functionBean, settingsBean, -1, null);
        }
    }


    /**
     * 使用方法的方式调起功能
     *
     * @param context
     * @param function
     * @param settings
     * @param mode
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static void runMethod(Context context, Function function, Settings settings, int mode, String data) {
        mContext = context;
        //判断是否在单击、双击、长按下执行
        if (mode == MODE_SINGLE_CLICK && function.getSingleCick() == false) {
            return;
        } else if (mode == MODE_DOUBLE_CLICK && function.getDoubleClick() == false) {
            return;
        } else if (mode == MODE_LONG_CLICK && function.getLongClick() == false) {
            return;
        }
        //判断是否在锁屏、黑屏下执行
        if (isScreenOn() == false) {
            if (function.getCloseEnable() == false)
                return;
        }
        if (isLockScreen() == true) {
            if (function.getLockEnable() == false)
                return;
        }
        if (Integer.parseInt(settings.getData5()) == Function.METHOD_TYPE_OPEN_FLASHLIGHT) {//根据setingsBean中保存的data5（方法type）来执行对应的方法
            //add for fix flashlight lhr 2017/2/4 {
            // Flashlight.openOrCloseFlashLight(mContext, true);
            boolean isFlashLight = PreferenceUtils.getBoolean(context, "isFlashLight", false);
            Log.d("LHRTAG", "isFlashLight " + isFlashLight);
            ManagerFlashlightUtils.openOrCloseFlashLight(context, isFlashLight);
            PreferenceUtils.putBoolean(context, "isFlashLight", !isFlashLight);
            //add and }
        } else if (Integer.parseInt(settings.getData5()) == Function.METHOD_TYPE_OPEN_NOTIFICATION) {
            StatusBarUtils.openStatusBar(mContext);
        }
    }

    /**
     * 使用Intent的方式调起功能。
     *
     * @param context
     * @param function
     * @param settings
     * @param mode
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static void openApp(Context context, Function function, Settings settings, int mode, String data) {
        mContext = context;
        Log.d("jlog", "openApp");
        DBService service = new DBService(context);
//        Function fun = service.findFunction(String.valueOf(id));
        Log.d("jlog", "-->>" + function.getName() + " " + function.getFunction() + " " + function.getParameter() + " " + function.getFunction_type());
        if (mode == MODE_SINGLE_CLICK && function.getSingleCick() == false) {
            return;
        } else if (mode == MODE_DOUBLE_CLICK && function.getDoubleClick() == false) {
            return;
        } else if (mode == MODE_LONG_CLICK && function.getLongClick() == false) {
            return;
        }
        if (isScreenOn() == false) {
            if (function.getCloseEnable() == false)
                return;
        }
        if (isLockScreen() == true) {
            if (function.getLockEnable() == false)
                return;
        }
        if (function.getFunction_app_type() == Function.APP_TYPE_EXTERNAL_APP) {//打开第三方应用
            Intent intentOpenExternal = new Intent();
            ComponentName componentName = new ComponentName(settings.getData2(), settings.getData3());
            intentOpenExternal.setComponent(componentName);
            intentOpenExternal.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intentOpenExternal);
        } else if (function.getFunction_app_type() == Function.APP_TYPE_INTERNAL_APP) {//打开本app内置的应用
            if (function.getFunction_type() == Function.FUN_TYPE_OPEN_APP) {
                Intent it = new Intent();
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (function.getParameter() != null && function.getParameter().length() > 0) {
                    Uri uri = Uri.parse(function.getParameter());
                    Log.d("jlog", "uri:" + function.getParameter().length());
                    it.setData(uri);
                }
                if (function.getParameter_extra() != null && function.getParameter_extra().length() > 0){
                    String extra = function.getParameter_extra();
                    it.putExtra(extra, true);
                }

                if (function.getFunction() != null && function.getFunction().length() > 0) {
                    it.setAction(function.getFunction());
                    mContext.startActivity(it);
                }
            }
        }
    }


    public static void runMethod(Context context, String name, int mode, String data) {
        mContext = context;
        DBService service = new DBService(context);
        Function fun = service.findAndroid(name);

        if (mode == MODE_SINGLE_CLICK && fun.getSingleCick() == false) {
            return;
        } else if (mode == MODE_DOUBLE_CLICK && fun.getDoubleClick() == false) {
            return;
        } else if (mode == MODE_LONG_CLICK && fun.getLongClick() == false) {
            return;
        }
        if (isScreenOn() == false) {
            if (fun.getCloseEnable() == false)
                return;
        }
        if (isLockScreen() == true) {
            if (fun.getLockEnable() == false)
                return;
        }

        Log.d("jlog", "-->>" + fun.getName() + " " + fun.getFunction() + " " + fun.getParameter() + " " + fun.getFunction_type());
        if (fun.getFunction_type() == Function.FUN_TYPE_RUN_METHOD) {


            if (Flashlight.isMarshMallow())
                Flashlight.openOrCloseFlashLight(mContext, true);
        }
    }


    public static void openApp(Context context, int id, int mode, String data) {
        mContext = context;
        Log.d("jlog", "openApp");
        DBService service = new DBService(context);
        Function fun = service.findFunction(String.valueOf(id));
        Log.d("jlog", "-->>" + fun.getName() + " " + fun.getFunction() + " " + fun.getParameter() + " " + fun.getFunction_type());
        if (mode == MODE_SINGLE_CLICK && fun.getSingleCick() == false) {
            return;
        } else if (mode == MODE_DOUBLE_CLICK && fun.getDoubleClick() == false) {
            return;
        } else if (mode == MODE_LONG_CLICK && fun.getLongClick() == false) {
            return;
        }
        if (isScreenOn() == false) {
            if (fun.getCloseEnable() == false)
                return;
        }
        if (isLockScreen() == true) {
            if (fun.getLockEnable() == false)
                return;
        }
        if (fun.getFunction_type() == Function.FUN_TYPE_OPEN_APP) {
            Intent it = new Intent();
            //            String str = Intent.ACTION_VIEW;
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (fun.getParameter() != null && fun.getParameter().length() > 0) {
                Uri uri = Uri.parse(fun.getParameter());
                Log.d("jlog", "uri:" + fun.getParameter().length());
                it.setData(uri);
            }

            if (fun.getFunction() != null && fun.getFunction().length() > 0) {
                it.setAction(fun.getFunction());
                mContext.startActivity(it);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private static boolean isScreenOn() {
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        return pm.isInteractive();
    }

    private static boolean isLockScreen() {
        KeyguardManager km = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = km.inKeyguardRestrictedInputMode();
        if (isScreenOn())
            flag = false;
        return flag;
    }
}
