package com.itel.smartkey;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.ui.MenuActivity;
import com.itel.smartkey.ui.phone.SelectContactsActivity;
import com.itel.smartkey.ui.sos.SetupSOSContactsInfoActivity;
import com.itel.smartkey.utils.StatusBarUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private static final String TAG = "BaseActivityTAG";
    private Button openMenuActivity;
    private Button openSlectActivity;
    private Button openSetuososContactsInfoActivity;
    private Button openSystemUI;
    private Button btScreenShot;
    private Context mContext;

    @Override
    protected int getResultId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mContext = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar, false, "");
        openMenuActivity = (Button) findViewById(R.id.open_menuActivity);
        openSlectActivity = (Button) findViewById(R.id.open_selectActivity);
        openSetuososContactsInfoActivity = (Button) findViewById(R.id.open_setuososcontactsinfoactivity);
        openSystemUI = (Button) findViewById(R.id.open_systemUI);
        btScreenShot = (Button) findViewById(R.id.bt_screenShot);
        // Title
//        toolbar.setTitle("Title");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initListener() {
        openMenuActivity.setOnClickListener(this);
        openSlectActivity.setOnClickListener(this);
        openSetuososContactsInfoActivity.setOnClickListener(this);
        openSystemUI.setOnClickListener(this);
        btScreenShot.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }
    @Override
    public void onClick(View view) {
        Log.d("LHRTAG", "onClick()");
        switch (view.getId()){
            case R.id.open_menuActivity:
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                break;
            case R.id.open_selectActivity:
                Intent intent1 = new Intent(this, SelectContactsActivity.class);
                startActivity(intent1);
                break;
            case R.id.open_setuososcontactsinfoactivity:
                Intent intent2 = new Intent(this, SetupSOSContactsInfoActivity.class);
                startActivity(intent2);
                break;
            case R.id.open_systemUI:
                StatusBarUtils.openStatusBar(mContext);
                break;
            case R.id.bt_screenShot:
                takeScreenshot();
                break;
        }
    }

    final Object mScreenshotLock = new Object();
    ServiceConnection mScreenshotConnection = null;
    Handler mHandler = new Handler();
    final Runnable mScreenshotTimeout = new Runnable() {
        @Override public void run() {
            synchronized (mScreenshotLock) {
                if (mScreenshotConnection != null) {
                    mContext.unbindService(mScreenshotConnection);
                    mScreenshotConnection = null;
                }
            }
        }
    };
    private void takeScreenshot() {
        synchronized (mScreenshotLock) {
            if (mScreenshotConnection != null) {
                return;
            }
            ComponentName cn = new ComponentName("com.android.systemui",
                    "com.android.systemui.screenshot.TakeScreenshotService");
            Intent intent = new Intent();
            intent.setComponent(cn);
            ServiceConnection conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    synchronized (mScreenshotLock) {
                        if (mScreenshotConnection != this) {
                            return;
                        }
                        Messenger messenger = new Messenger(service);
                        Message msg = Message.obtain(null, 1);
                        final ServiceConnection myConn = this;
                        Handler h = new Handler(mHandler.getLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                synchronized (mScreenshotLock) {
                                    if (mScreenshotConnection == myConn) {
                                        mContext.unbindService(mScreenshotConnection);
                                        mScreenshotConnection = null;
                                        mHandler.removeCallbacks(mScreenshotTimeout);
                                    }
                                }
                            }
                        };
                        msg.replyTo = new Messenger(h);
                        msg.arg1 = msg.arg2 = 0;
//                        if (mStatusBar != null && mStatusBar.isVisibleLw())
//                            msg.arg1 = 1;
//                        if (mNavigationBar != null && mNavigationBar.isVisibleLw())
//                            msg.arg2 = 1;
                        try {
                            messenger.send(msg);
                        } catch (RemoteException e) {
                        }
                    }
                }
                @Override
                public void onServiceDisconnected(ComponentName name) {}
            };
            if (getApplicationContext().bindService(
                    intent, conn, Context.BIND_AUTO_CREATE)) {
                mScreenshotConnection = conn;
                mHandler.postDelayed(mScreenshotTimeout, 10000);
            }
        }
    }
}
