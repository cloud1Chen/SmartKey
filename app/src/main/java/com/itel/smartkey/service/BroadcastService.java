package com.itel.smartkey.service;


import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.itel.smartkey.MainActivity;
import com.itel.smartkey.bean.Function;
import com.itel.smartkey.contants.MyContants;
import com.itel.smartkey.contants.SmartKeyAction;
import com.itel.smartkey.utils.Execute;
import com.itel.smartkey.utils.Flashlight;
import com.itel.smartkey.utils.PreferenceUtils;


public class BroadcastService extends Service {

    private DBService mDBService;


    @Override
    public void onCreate() {
        Log.d("jlog", "BroadcastService");

        super.onCreate();
    }

    static int mClick = 0;
    long mTime = 0;
    private long doubleClickSpeed = 400;
    private boolean isSingleClickEffect = true;//单击是否弹出菜单功能
    boolean canVibrate;//是否允许震动
    int mStartMode;
    public static final int MODE_SINGLE_CLICK = 1;
    public static final int MODE_DOUBLE_CLICK = 2;
    public static final int MODE_LONG_CLICK = 3;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mDBService = new DBService(this);
        String strDoubleClickSpeed = PreferenceUtils.getString(this, MyContants.KEY_DOUBLE_SPEED, "400");
        doubleClickSpeed =  Long.parseLong(strDoubleClickSpeed);
        Log.d("LHRTAG", "doubleClickSpeed " + doubleClickSpeed);

        isSingleClickEffect = PreferenceUtils.getBoolean(this, MyContants.KEY_IS_CLICK_EFFECT, true);
        Log.d("LHRTAG", "isSingleClickEffect " + isSingleClickEffect + "System.currentTimeMillis()" + System.currentTimeMillis());

        canVibrate = PreferenceUtils.getBoolean(this, MyContants.KEY_IS_VIBRATE_OPENED, false);
        Log.d("LHRTAG", "canVibrate " + canVibrate + "System.currentTimeMillis() " + System.currentTimeMillis());

        // The service is starting, due to a call to startService()
        String data = intent.getStringExtra("data");
        Log.d("jlog", "BroadcastService onStartCommand");

        if (data.equals("single_click")) {
            mClick += 1;
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
                public void run() {
                    if (mClick > 1) {//双击事件

                        Log.d("jlog", "运行双击事件");
                        mTime = System.nanoTime() - mTime;
                        mTime = mTime / 1000000;
                        Log.d("jlog", "-------------mClick:" + mClick + " time:" + mTime);

//                        Settings settingsBean = mDBService.findSettingsById(MainActivity.POSITION_DOUBLECLICK);
//                        int funcId = settingsBean.getFuncId();
//                        Function functionBean = mDBService.findFunction(funcId + "");
//                        if (functionBean.getFunction_type() == Function.FUN_TYPE_RUN_METHOD) {//如果是通过打开方法类型执行
//                            Execute.runMethod(BroadcastService.this, functionBean, settingsBean, Execute.MODE_DOUBLE_CLICK, null);
//                        } else if (functionBean.getFunction_type() == Function.FUN_TYPE_OPEN_APP) {//通过打开app类型执行,这里又分为两种：1、通过选择app设置的方式  2、通过我们预置的方式
//                            Execute.openApp(BroadcastService.this, functionBean, settingsBean, Execute.MODE_DOUBLE_CLICK, null);
//                        }
                        //使用smartkey时是否允许震动提示
                        if (canVibrate){
                            Vibrator vibrator = (Vibrator) BroadcastService.this.getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(50);
                        }
                        Execute.run(BroadcastService.this, Integer.parseInt(MainActivity.POSITION_DOUBLECLICK), Execute.MODE_DOUBLE_CLICK, null);


//                        //openApp("camera", MODE_DOUBLE_CLICK, null);
//                        Execute.openApp(BroadcastService.this, 1, MODE_DOUBLE_CLICK, null);
                    } else if (mClick == 1) {//单击事件
                        Log.d("jlog", "运行单击事件");
                        isSingleClickEffect = PreferenceUtils.getBoolean(BroadcastService.this, MyContants.KEY_IS_CLICK_EFFECT, true);
                        Log.d("LHRTAG", "isSingleClickEffect " + isSingleClickEffect + "System.currentTimeMillis()" + System.currentTimeMillis());
                        //是否启用单击弹出菜单功能
                        if (!isSingleClickEffect){

                        }else{
                            //使用smartkey时是否允许震动提示
                            if (canVibrate){
                                Vibrator vibrator = (Vibrator) BroadcastService.this.getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(50);
                            }
                            Intent intentOpenDialogMenuActivity = new Intent();
                            intentOpenDialogMenuActivity.setAction(SmartKeyAction.ACTION_OPEN_DIALOGMENU_ACTIVITY);
                            intentOpenDialogMenuActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intentOpenDialogMenuActivity.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intentOpenDialogMenuActivity);
                        }
//                        //runMethod("flashlight", MODE_SINGLE_CLICK, null);
//                        Execute.runMethod(BroadcastService.this, "flashlight", MODE_SINGLE_CLICK, null);
                    }
                    mTime = System.nanoTime();
                    mClick = 0;
                }
            }, doubleClickSpeed);


        } else if (data.equals("long_click")) {//长按广播触发的事件，先去settings中查询获取相关的额外参数，然后根据funcId查询Function表中的相关数据

//            Settings settingsBean = mDBService.findSettingsById(MainActivity.POSITION_LONGCLICK);
//            int funcId = settingsBean.getFuncId();
//            Function functionBean = mDBService.findFunction(funcId + "");
//
//
//            if (functionBean.getFunction_type() == Function.FUN_TYPE_RUN_METHOD) {//如果是通过打开方法类型执行
//                Execute.runMethod(BroadcastService.this, functionBean, settingsBean, Execute.MODE_LONG_CLICK, null);
//            } else if (functionBean.getFunction_type() == Function.FUN_TYPE_OPEN_APP) {//通过打开app类型执行,这里又分为两种：1、通过选择app设置的方式  2、通过我们预置的方式
//                Execute.openApp(BroadcastService.this, functionBean, settingsBean, Execute.MODE_LONG_CLICK, null);
//            }
            //使用smartkey时是否允许震动提示
            if (canVibrate){
                Vibrator vibrator = (Vibrator) BroadcastService.this.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(50);
            }
            Execute.run(BroadcastService.this, Integer.parseInt(MainActivity.POSITION_LONGCLICK), Execute.MODE_LONG_CLICK, null);
        }
        return START_NOT_STICKY;
//        return mStartMode;
    }

    public static int run() {
        int ret = -1;

        return ret;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private void runMethod(String name, int mode, String data) {
        DBService service = new DBService(BroadcastService.this);
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
                Flashlight.openOrCloseFlashLight(BroadcastService.this, true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private void openApp(String name, int mode, String data) {
        DBService service = new DBService(BroadcastService.this);
        Function fun = service.findAndroid(name);
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
            String str = Intent.ACTION_VIEW;
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (fun.getParameter() != null && fun.getParameter().length() > 0) {
                Uri uri = Uri.parse(fun.getParameter());
                Log.d("jlog", "uri:" + fun.getParameter().length());
                it.setData(uri);
            }

            if (fun.getFunction() != null && fun.getFunction().length() > 0) {
                it.setAction(fun.getFunction());
                BroadcastService.this.startActivity(it);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        Log.d("jlog", "BroadcastService onBind");

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private boolean isScreenOn() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        return pm.isInteractive();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private boolean isLockScreen() {
        /*
        Settings.System.putInt(getContentResolver(), "smart_hand_up", 0);
        Settings.System.putInt(getContentResolver(), "smart_hand_up", 0);


//        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
//        //解锁
//        kl.disableKeyguard();
//        kl.reenableKeyguard();

        //获取电源管理器对象
        PowerManager pm=(PowerManager) getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
*/

        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = km.inKeyguardRestrictedInputMode();
        if (isScreenOn())
            flag = false;
        return flag;
    }
}
