package com.itel.smartkey;

import android.content.Context;
import android.content.Intent;
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

        startEnterAnim();//开始进场动画
        startBackgroundAnimtion(mContext, ivBackground);//开启背景动画
        initToolbar(toolbar, "", true);//初始化toolbar

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
