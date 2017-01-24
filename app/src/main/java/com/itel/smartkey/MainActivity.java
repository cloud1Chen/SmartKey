package com.itel.smartkey;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.ui.toolbox.FrontToolBoxActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

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

    @Override
    protected int getResultId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mContext = this;
        //初始化动画相关的控件
        ivBackground = (ImageView) findViewById(R.id.background);
        tv_main_smartkey = (TextView) findViewById(R.id.tv_main_smartkey);
        tv_main_smartkey_subscribe = (TextView) findViewById(R.id.tv_main_smartkey_subscribe);
        iv_main_phone = (ImageView) findViewById(R.id.iv_main_phone);
        ll_main_button_group = (LinearLayout) findViewById(R.id.ll_main_button_group);


//        Animation animTvSmartKey = AnimationUtils.loadAnimation(mContext, R.anim.anim_main_tv_smartkey);
//        tv_main_smartkey.startAnimation(animTvSmartKey);














        startBackgroundAnimtion(mContext, ivBackground);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar, "", true);
        relativelayout_click = (RelativeLayout) findViewById(R.id.relativelayout_click);
        relativelayout_double_click = (RelativeLayout) findViewById(R.id.relativelayout_double_click);
        relativelayout_long_click = (RelativeLayout) findViewById(R.id.relativelayout_long_click);
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
        switch (view.getId()){
            case R.id.relativelayout_click:
                Intent intent = new Intent(this, FrontToolBoxActivity.class);
//                Intent intent = new Intent();
//                intent.setAction("com.itel.smartkey.action.SetupSurfNetUrlActivity");
//                Intent intent = new Intent(this, SelectContactsTestActivity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_double_click:
                Log.d("LHRTAG", "");
                Intent intent1 = new Intent();
                intent1.setAction("com.itel.smartkey.action.ChooseAppsActivity");
                startActivity(intent1);
                break;
            case R.id.relativelayout_long_click:
                Log.d("LHRTAG", "");
                Intent intent2 = new Intent();
                intent2.setAction("com.itel.smartkey.action.ChooseFunctionActivity");
                startActivity(intent2);
                break;
        }
    }

}
