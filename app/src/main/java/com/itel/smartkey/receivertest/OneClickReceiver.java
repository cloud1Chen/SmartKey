package com.itel.smartkey.receivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.itel.smartkey.utils.ManagerFlashlightUtils;
import com.itel.smartkey.utils.PreferenceUtils;
import com.itel.smartkey.utils.StatusBarUtils;

/**
 * Created by huorong.liang on 2017/1/11.
 */

public class OneClickReceiver extends BroadcastReceiver {
    boolean isFlashLight;
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SentBroadcastActivity.ACTION_SMARTKEY_SINGLE_CLICK)){
            Log.d("LHRTAG", "你接收到了单击发出的广播");
            Intent intent1 = new Intent();
            intent1.setAction("com.itel.smartkey.action.SelectContactsActivity");
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent1);
        }else if (intent.getAction().equals(SentBroadcastActivity.ACTION_SMARTKEY_DOUBLE_CLICK)){
            StatusBarUtils.openStatusBar(context);
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
