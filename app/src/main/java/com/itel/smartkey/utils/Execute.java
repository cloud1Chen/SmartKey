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
import com.itel.smartkey.contants.MyContants;
import com.itel.smartkey.service.DBService;


public class Execute {
    public static final int MODE_SINGLE_CLICK = 1;
    public static final int MODE_DOUBLE_CLICK = 2;
    public static final int MODE_LONG_CLICK = 3;
    private static Context mContext;
    private static DBService mDBService;
    //各功能的funcid
    public static final int FUNCID_OPEN_AN_APP = 1;
    public static final int FUNCID_CAMERA = 2;
    public static final int FUNCID_BEAUTY_SELFIE = 3;
    public static final int FUNCID_CLICK_ACCELERATE = 4;
    public static final int FUNCID_NOTIFICATION_BAR = 5;
    public static final int FUNCID_SCREENSHOT = 6;
    public static final int FUNCID_FLASH_LIGHT = 7;
    public static final int FUNCID_STANDBY_CAMERA = 8;
    public static final int FUNCID_OPEN_AN_URL = 9;
    public static final int FUNCID_CALL_PHONE = 10;
    public static final int FUNCID_LISTEN_TO_RADIO = 11;
    public static final int FUNCID_SHARE_PIC_TO_FACEBOOK = 12;
    public static final int FUNCID_SOS = 13;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static void run(Context context, int funcAcId, int mode, String data){
        mContext = context;
        mDBService = new DBService(context);

        boolean canUsedInLockScreen = PreferenceUtils.getBoolean(context, MyContants.KEY_IS_CANUSE_IN_BLACK, false);


        Log.d("LHRTAG", "canUsedInLockScreen " + canUsedInLockScreen + "System.currentTimeMillis() " + System.currentTimeMillis());
        Log.d("LHRTAG", "isLockScreen() " + isLockScreen() + "System.currentTimeMillis() " + System.currentTimeMillis());



        //锁屏下是否启用smartkey
        if (isLockScreen() && !canUsedInLockScreen){
            return;
        }

        Settings settingsBean = mDBService.findSettingsById(funcAcId + "");
        int funcId = settingsBean.getFuncId();
        Function functionBean = mDBService.findFunction(funcId + "");
        int a = android.provider.Settings.System.getInt(context.getContentResolver(),"smart_quick_states",0);
        Log.d("LHRTAG", "smart_quick_states " +System.currentTimeMillis()+ a);
//        if (mode == Execute.MODE_LONG_CLICK && funcId != Execute.FUNCID_STANDBY_CAMERA){
//            android.provider.Settings.System.putInt(context.getContentResolver(),"smart_quick_states",0);
//        }
//        int b = android.provider.Settings.System.getInt(context.getContentResolver(),"smart_quick_states",0);
//        Log.d("LHRTAG", "smart_quick_states " +System.currentTimeMillis()+ b);
        if (functionBean.getFunction_type() == Function.FUN_TYPE_RUN_METHOD) {//如果是通过打开方法（2）类型执行
            Execute.runMethod(context, functionBean, settingsBean, mode, null);
        } else if (functionBean.getFunction_type() == Function.FUN_TYPE_OPEN_APP) {//通过打开app类型（1）执行,这里又分为两种：1、通过选择app设置的方式  2、通过我们预置的方式
            Execute.openApp(context, functionBean, settingsBean, mode, null);
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
        if (mode == MODE_SINGLE_CLICK && !function.getSingleCick()) {
            return;
        } else if (mode == MODE_DOUBLE_CLICK && !function.getDoubleClick()) {
            return;
        } else if (mode == MODE_LONG_CLICK && !function.getLongClick()) {
            return;
        }
        //判断是否在锁屏、黑屏下执行
        if (!isScreenOn()) {
            if (!function.getCloseEnable())
                return;
        }
        if (isLockScreen()) {
            if (!function.getLockEnable())
                return;
        }
        //add for test function call phone lhr 20170207
        int funcId = function.getId();
        Log.d("LHRTAG", "Execute strUrl " + funcId);
        if (funcId == Execute.FUNCID_CALL_PHONE){//打电话功能
            Intent callIntent = new Intent();
            callIntent.setAction("android.intent.action.NEWCALL");
            Log.d("LHRTAG", "Execute numbers " + settings.getData4());
            callIntent.setData(Uri.parse("call://" + "_" + settings.getData4()));
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            callIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            mContext.startActivity(callIntent);
            return;
        }
//        if (funcId == Execute.FUNCID_STANDBY_CAMERA){
//            //Settings.System.putInt(getContentResolver(), "smart_flashligh_state", 0);
//            android.provider.Settings.System.putInt(mContext.getContentResolver(),"smart_quick_states", 1);
//        }
        if (Integer.parseInt(settings.getData5()) == Function.METHOD_TYPE_OPEN_FLASHLIGHT) {//根据setingsBean中保存的data5（方法type）来执行对应的方法
            //add for fix flashlight lhr 2017/2/4 {
             Flashlight.openOrCloseFlashLight(mContext, true);
//            boolean isFlashLight = PreferenceUtils.getBoolean(context, "isFlashLight", false);
//            Log.d("LHRTAG", "isFlashLight " + isFlashLight);
//            ManagerFlashlightUtils.openOrCloseFlashLight(context, isFlashLight);
//            PreferenceUtils.putBoolean(context, "isFlashLight", !isFlashLight);
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
        //add for test function open URL lhr 20170206
        int funcId = function.getId();
        if (funcId == Execute.FUNCID_OPEN_AN_URL){//如果是打开网址
            /*
            当调用系统Intent时，以调用以下2种Intent出现：android.content.ActivityNotFoundException: No Activity found to handle Intent
            { act=android.intent.action.VIEW dat=http flg=0x10000000 }出错提示时，主要的原因及解决办法：
            1、访问浏览器：
            Uri uri = uri.parse("www.google.com");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
            原因：网址前面未加http导致的，访问的网址必须是完整的网址，不能省略http。
             */
            String strUrl = settings.getData1();
            Log.d("LHRTAG", "Execute strUrl " + strUrl);
            Uri uri = Uri.parse(strUrl);
            Intent mOpenUrlIntent = new Intent();
            mOpenUrlIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mOpenUrlIntent.setAction(Intent.ACTION_VIEW);
            mOpenUrlIntent.setData(uri);
            mContext.startActivity(mOpenUrlIntent);
            return;
        }
        //add end
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private static boolean isLockScreen() {
        KeyguardManager km = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = km.inKeyguardRestrictedInputMode();
        if (isScreenOn())
            flag = false;
        return flag;
    }
}
