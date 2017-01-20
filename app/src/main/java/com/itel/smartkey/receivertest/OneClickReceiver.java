package com.itel.smartkey.receivertest;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.itel.smartkey.utils.ManagerFlashlightUtils;
import com.itel.smartkey.utils.PreferenceUtils;

/**
 * Created by huorong.liang on 2017/1/11.
 */

public class OneClickReceiver extends BroadcastReceiver {
    boolean isFlashLight;
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        //判断屏幕是否点亮，方式一
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了
        Log.d("LHRTAG", "isScreen on " + (isScreenOn?"yes":"no"));

        //判断屏幕是否点亮，方式二
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();//flag为true：1、目前黑屏，2、目前处于屏幕点亮待解锁状态；
                                                                         //flag为false：目前处于亮屏已解锁状态

        Log.d("LHRTAG", "isScreen on " + (flag?"false":"true"));

        if (intent.getAction().equals(SentBroadcastActivity.ACTION_SMARTKEY_SINGLE_CLICK)){
            Log.d("LHRTAG", "你接收到了单击发出的广播");
            Intent intent1 = new Intent();
            intent1.setAction("com.itel.smartkey.action.TestActivity");
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent1);
        }else if (intent.getAction().equals(SentBroadcastActivity.ACTION_SMARTKEY_DOUBLE_CLICK)){
//            StatusBarUtils.openStatusBar(context);
            Intent in2 = new Intent();
            ComponentName componentName = new ComponentName("com.itel.broadcastreceiverdemo", "com.itel.broadcastreceiverdemo.SentBroadcastActivity");
            in2.setComponent(componentName);
            in2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(in2);
            Log.d("LHRTAG", "你接收到了双击发出的广播");
        }else if (intent.getAction().equals(SentBroadcastActivity.ACTION_SMARTKEY_LONG_CLICK)){
            Log.d("LHRTAG", "你接收到了长按发出的广播");
            isFlashLight = PreferenceUtils.getBoolean(context, "isFlashLight", false);
            Log.d("LHRTAG", isFlashLight + "");
            ManagerFlashlightUtils.openOrCloseFlashLight(context, isFlashLight);
            PreferenceUtils.putBoolean(context, "isFlashLight", !isFlashLight);
            Log.d("LHRTAG", PreferenceUtils.getBoolean(context, "isFlashLight", false) + "");
        }
    }
}
