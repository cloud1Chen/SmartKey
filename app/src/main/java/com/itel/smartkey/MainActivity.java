package com.itel.smartkey;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.bean.Function;
import com.itel.smartkey.bean.Settings;
import com.itel.smartkey.service.DBService;
import com.itel.smartkey.ui.toolbox.FrontToolBoxActivity;
import com.itel.smartkey.utils.Execute;
import com.itel.smartkey.utils.Utils;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final int KEY_REQUEST_CHOOSE_FUNCTION_DOUBLECLICK = 1;
    public static final int KEY_REQUEST_CHOOSE_FUNCTION_LONGCLICK = 2;
    public static final String POSITION_DOUBLECLICK = "1";
    public static final String POSITION_LONGCLICK = "2";

    private Toolbar toolbar;
    private static final String TAG = "BaseActivityTAG";
    private Context mContext;
    private RelativeLayout relativelayout_click;
    private RelativeLayout relativelayout_double_click;
    private RelativeLayout relativelayout_long_click;

    //需要执行动画的控件
    private ImageView ivBackground;
    private TextView tv_main_smartkey;
    private TextView tv_main_smartkey_subscribe;
    private ImageView iv_main_phone;
    private LinearLayout ll_main_button_group;

    private TextView tv_click;//单击的主标题
    private TextView tv_click_sub;//单击的副标题
    private TextView tv_double_click;//双击的主标题
    private TextView tv_double_click_sub;//双击的副标题
    private TextView tv_long_click;//长按的主标题
    private TextView tv_long_click_sub;//长按的副标题

    private HomeKeyEventBroadCastReceiver receiver;
    private DBService mDBService;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LHRTAG", "onDestroy()");
        unregisterReceiver(receiver);
    }


    @Override
    protected int getResultId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mContext = this;
        mDBService = new DBService(mContext);

        receiver = new HomeKeyEventBroadCastReceiver();// 注册监听HOME键的广播
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

        relativelayout_click = (RelativeLayout) findViewById(R.id.relativelayout_click);
        relativelayout_double_click = (RelativeLayout) findViewById(R.id.relativelayout_double_click);
        relativelayout_long_click = (RelativeLayout) findViewById(R.id.relativelayout_long_click);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //初始化动画相关的控件
        ivBackground = (ImageView) findViewById(R.id.background);
        tv_main_smartkey = (TextView) findViewById(R.id.tv_main_smartkey);
        tv_main_smartkey_subscribe = (TextView) findViewById(R.id.tv_main_smartkey_subscribe);
        iv_main_phone = (ImageView) findViewById(R.id.iv_main_phone);
        ll_main_button_group = (LinearLayout) findViewById(R.id.ll_main_button_group);

        //初始化标题相关的控件
        tv_click = (TextView) findViewById(R.id.tv_click);
        tv_click_sub = (TextView) findViewById(R.id.tv_click_sub);
        tv_double_click = (TextView) findViewById(R.id.tv_double_click);
        tv_double_click_sub = (TextView) findViewById(R.id.tv_double_click_sub);
        tv_long_click = (TextView) findViewById(R.id.tv_long_click);
        tv_long_click_sub = (TextView) findViewById(R.id.tv_long_click_sub);

        String tvClickSubText = this.getString(R.string.openmenu);
        tv_click_sub.setText(tvClickSubText);
        setDoubleClickSubText();
        setLongClickSubText();


        startEnterAnim();//开始进场动画
        startBackgroundAnimtion(mContext, ivBackground);//开启背景动画
        initToolbar(toolbar, "", true);//初始化toolbar

    }

    private void setDoubleClickSubText() {
        Settings settingsBean = mDBService.findSettingsById(POSITION_DOUBLECLICK);
        String itemName = settingsBean.getFuncAcName();
        Log.d("LHRTAG", "is itemName null" + (itemName != null ? "false" : "true"));
        if (itemName != null){
            tv_double_click_sub.setText(itemName);
        }else {
            Function functionBean = mDBService.findFunction(settingsBean.getFuncId() + "");
            Log.d("LHRTAG", "functionBean Id " + functionBean.getId() );
            String name = functionBean.getName();
            Log.d("LHRTAG", "name " + name);
            tv_double_click_sub.setText(Utils.getStringById(mContext, name));
        }
    }
    private void setLongClickSubText() {
        Settings settingsBean = mDBService.findSettingsById(POSITION_LONGCLICK);
        String itemName = settingsBean.getFuncAcName();
        Log.d("LHRTAG", "is itemName null" + (itemName != null ? "false" : "true"));
        if (itemName != null){
            tv_long_click_sub.setText(itemName);
        }else {
            Function functionBean = mDBService.findFunction(settingsBean.getFuncId() + "");
            Log.d("LHRTAG", "functionBean Id " + functionBean.getId() );
            String name = functionBean.getName();
            Log.d("LHRTAG", "name " + name);
            tv_long_click_sub.setText(Utils.getStringById(mContext, name));
        }
    }

    private void startEnterAnim() {
        final Animation animTvSmartKey = AnimationUtils.loadAnimation(mContext, R.anim.anim_main_tv_smartkey_in);
        final Animation animIvPhone = AnimationUtils.loadAnimation(mContext, R.anim.anim_main_iv_phone_in);
        tv_main_smartkey.startAnimation(animTvSmartKey);
        new Handler().postDelayed(//动画开始60ms后，执行副标题的动画
                new Runnable() {
                    @Override
                    public void run() {
                        tv_main_smartkey_subscribe.startAnimation(animTvSmartKey);
                    }
                }
                , 60);
        new Handler().postDelayed(//动画开始20ms后，执行手机图片的动画
                new Runnable() {
                    @Override
                    public void run() {
                        iv_main_phone.startAnimation(animIvPhone);
                    }
                }
                , 20);
    }

    private void startOutAnim() {
        final Animation animTvSmartKeyOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_main_tv_smartkey_out);

        final Animation animIvPhoneOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_main_iv_phone_out);
        final Animation animButtonGroupOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_main_buttongroup_out);
        ll_main_button_group.startAnimation(animButtonGroupOut);//底部菜单的动画
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_main_phone.startAnimation(animIvPhoneOut);
            }
        }, 60);
        new Handler().postDelayed(new Runnable() {//文字的动画
            @Override
            public void run() {
                tv_main_smartkey.startAnimation(animTvSmartKeyOut);
                tv_main_smartkey_subscribe.startAnimation(animTvSmartKeyOut);
            }
        }, 160);

        animTvSmartKeyOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(mContext, FrontToolBoxActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_activity_fade_in, R.anim.anim_activity_fade_out);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animIvPhoneOut.setFillAfter(false);
                        animButtonGroupOut.setFillAfter(false);
                        animTvSmartKeyOut.setFillAfter(false);
                    }
                },500);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected void initListener() {
        relativelayout_click.setOnClickListener(this);
        relativelayout_double_click.setOnClickListener(this);
        relativelayout_long_click.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        Log.d("LHRTAG", "onClick()");
        switch (view.getId()) {
            case R.id.relativelayout_click:
                startOutAnim();
                break;
            case R.id.relativelayout_double_click:
                Log.d("LHRTAG", "");
                Intent intent1 = new Intent();
                intent1.setAction("com.itel.smartkey.action.ChooseFunctionActivity");
                startActivityForResult(intent1, KEY_REQUEST_CHOOSE_FUNCTION_DOUBLECLICK);
//                startActivity(intent1);
                break;
            case R.id.relativelayout_long_click:
                Log.d("LHRTAG", "");
                Intent intent2 = new Intent();
                intent2.setAction("com.itel.smartkey.action.ChooseFunctionActivity");
                startActivityForResult(intent2, KEY_REQUEST_CHOOSE_FUNCTION_LONGCLICK);
//                startActivity(intent2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == KEY_REQUEST_CHOOSE_FUNCTION_DOUBLECLICK) {//获取得到的intent，获取其中的funcid
                updateDoubleClickInfo(data);
            }else if (requestCode == KEY_REQUEST_CHOOSE_FUNCTION_LONGCLICK){
                updateLongClickInfo(data);
            }
        }
    }

    private void updateDoubleClickInfo(Intent data) {
        Settings mSettingsBean = mDBService.findSettingsById(POSITION_DOUBLECLICK);
        setTemSettingsTomSettings(data, mSettingsBean);
        mDBService.updateSettings(mSettingsBean);
        setDoubleClickSubText();
    }



    private void updateLongClickInfo(Intent data) {
        Settings mSettingsBean = mDBService.findSettingsById(POSITION_LONGCLICK);
        setTemSettingsTomSettings(data, mSettingsBean);
        mDBService.updateSettings(mSettingsBean);

        int a = android.provider.Settings.System.getInt(MainActivity.this.getContentResolver(),"smart_quick_states",0);
        Log.d("LHRTAG", "smart_quick_states " +System.currentTimeMillis()+ a);

        if (mSettingsBean.getFuncId() == Execute.FUNCID_STANDBY_CAMERA){
            android.provider.Settings.System.putInt(MainActivity.this.getContentResolver(),"smart_quick_states",1);
        }else {
            android.provider.Settings.System.putInt(MainActivity.this.getContentResolver(),"smart_quick_states",0);
        }
        setLongClickSubText();
    }

    private void setTemSettingsTomSettings(Intent data, Settings mSettingsBean) {
        int funcId = data.getIntExtra(FrontToolBoxActivity.FUNCTION_FUNCID, -1);
        mSettingsBean.setFuncId(funcId);
        Log.d("LHRTAG", "FrontToolBoxActivity funcId " + funcId);
        Log.d("LHRTAG", "FrontToolBoxActivity funcAcId " + mSettingsBean.getFuncAcId());


        Settings mTempSettingsBean = (Settings) data.getSerializableExtra(FrontToolBoxActivity.SETTINGS_BEAN);
        String url = mTempSettingsBean.getData1();
        String packageName = mTempSettingsBean.getData2();
        String activityClass = mTempSettingsBean.getData3();
        String phonenumber = mTempSettingsBean.getData4();
        String methodType = mTempSettingsBean.getData5();
        String data6 = mTempSettingsBean.getData6();
        String data7 = mTempSettingsBean.getData7();
        byte[] funcAcIconBytes = mTempSettingsBean.getFuncAcIconBytes();
        String name = mTempSettingsBean.getFuncAcName();
        String funcAcIconPath = mTempSettingsBean.getFuncAcIconPath();
        int funcAcIconId = mTempSettingsBean.getFuncAcIconId();
        int funcAcNameId = mTempSettingsBean.getFuncAcNameId();


        if (mTempSettingsBean != null){
            mSettingsBean.setFuncAcName(name);
            mSettingsBean.setFuncAcIconBytes(funcAcIconBytes);
            mSettingsBean.setData1(url);
            mSettingsBean.setData2(packageName);
            mSettingsBean.setData3(activityClass);
            mSettingsBean.setData4(phonenumber);
            mSettingsBean.setData5(methodType);
            mSettingsBean.setData6(data6);
            mSettingsBean.setData7(data7);
            mSettingsBean.setFuncAcIconPath(funcAcIconPath);
            mSettingsBean.setFuncAcIconId(funcAcIconId);
            mSettingsBean.setFuncAcNameId(funcAcNameId);
        }
    }

    /**
     * 监听home键广播，如果用户点击了按键，则finsh当前的activity
     */
    class HomeKeyEventBroadCastReceiver extends BroadcastReceiver {
        static final String SYSTEM_REASON = "reason";
        static final String SYSTEM_HOME_KEY = "homekey";
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                Log.d("LHRTAG", "onReceive()");
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (reason != null) {
                    if (reason.equals(SYSTEM_HOME_KEY)) {
                        MainActivity.this.finish();//这里是你监听到HOME键要做什么，我这里是销毁Activity
                    }
                }
            }
        }
    }
}
