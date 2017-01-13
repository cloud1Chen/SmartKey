package com.itel.smartkey.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by huorong.liang on 2017/1/10.
 */

public class SmartKeyOneClickReceiver extends BroadcastReceiver {
    public static int KEY_ONECLICK_SENDSOS = 0xff01;
    public static int KEY_ONECLICK_CALLPHONE = 0xff02;
    public static int KEY_ONECLICK_EXPLORER = 0xff03;
    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
