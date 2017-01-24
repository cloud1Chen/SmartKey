package com.itel.smartkey.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.itel.smartkey.service.BroadcastService;

import static android.content.Context.ACTIVITY_SERVICE;

public class StaticBroadcastReceiver extends BroadcastReceiver {
    Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        mContext = context;
        Log.d("jlog", "onReceive");
        Intent it;
        it = new Intent(mContext, BroadcastService.class);
        if (intent.getAction().equals("com.itel.smartkey.action.ACTION_SMARTKEY_SINGLE_CLICK")){

            Log.d("jlog", "single_click");
            it.putExtra("data", "single_click");
            //if (isServiceRunning(BroadcastService.class.getName()) == false)
            {
                mContext.startService(it);
                Log.d("jlog", "start:" + BroadcastService.class.getName());
            }
        }else if(intent.getAction().equals("com.itel.smartkey.action.ACTION_SMARTKEY_LONG_CLICK")) {
            Log.d("jlog", "long_click");
            it.putExtra("data", "long_click");
            mContext.startService(it);
        }
    }
    private boolean isServiceRunning(String name) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (name.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}