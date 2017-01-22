package com.itel.smartkey.ui.settings;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.itel.smartkey.R;
import com.itel.smartkey.adapter.SlideAdapter;
import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.contants.MyContants;
import com.itel.smartkey.utils.PreferenceUtils;

import ch.ielse.view.SwitchView;
import so.orion.slidebar.GBSlideBar;
import so.orion.slidebar.GBSlideBarListener;

/**
 * Created by huorong.liang on 2017/1/6.
 */

public class MenuActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Context mContext;
    private SwitchView switch_vibration;
    private SwitchView switch_canuse_in_black;
    private SwitchView switch_click;
    private SwitchView switch_camera;

    boolean isvibrateOpened;
    boolean isCanusedInBlack;
    boolean isClickEffect;
    boolean isCameraEffect;

    private GBSlideBar gbSlideBar;
    private SlideAdapter mGBSlideBarAdapter;
    private int gbSlideBarPosition;
    private String[] doubleSpeedValues;




    @Override
    protected int getResultId() {
        return R.layout.activity_menu;
    }

    @Override
    protected void initView() {
        mContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        String toolbarBackgroundColor = mContext.getString(R.string.toolbar_title_text_color_choose_function);
        initToolbar(toolbar, R.mipmap.pic_back_bar_black, "设置", false, toolbarBackgroundColor, null);
        initSwitch();
        initGBSlideBar();
    }

    /**
     * 初始化三目选择按钮
     */
    private void initGBSlideBar() {
        gbSlideBar = (GBSlideBar) findViewById(R.id.gbslidebar);
        Resources resources = getResources();
        mGBSlideBarAdapter = new SlideAdapter(resources, new int[]{
                R.drawable.aaa,
                R.drawable.aaa,
                R.drawable.aaa});
        mGBSlideBarAdapter.setTextColor(new int[]{
                Color.BLUE,
                Color.BLUE,
                Color.BLUE
        });
        gbSlideBar.setAdapter(mGBSlideBarAdapter);
        gbSlideBarPosition = PreferenceUtils.getInt(mContext, MyContants.KEY_DOUBLE_SPEED_POSION, 0);
        gbSlideBar.setPosition(gbSlideBarPosition);
        gbSlideBar.setOnGbSlideBarListener(new GBSlideBarListener() {
            @Override
            public void onPositionSelected(int position) {//状态改变时，把position保存到本地；把double_speed_value保存到本地。
                Log.d("LHRTAG","selected " + position);
                PreferenceUtils.putInt(mContext, MyContants.KEY_DOUBLE_SPEED_POSION, position);
                doubleSpeedValues = mContext.getResources().getStringArray(R.array.double_speed_values);
                PreferenceUtils.putString(mContext, MyContants.KEY_DOUBLE_SPEED, doubleSpeedValues[position]);
                Log.d("LHRTAG","value " + PreferenceUtils.getString(mContext, MyContants.KEY_DOUBLE_SPEED));
            }
        });
    }

    private void initSwitch() {

        //初始化switch控件
        switch_vibration = (SwitchView) findViewById(R.id.switch_vibration);
        switch_canuse_in_black = (SwitchView) findViewById(R.id.switch_canuse_in_black);
        switch_click = (SwitchView) findViewById(R.id.switch_click);
        switch_camera = (SwitchView) findViewById(R.id.switch_camera);

        //获取保存的状态值
        isvibrateOpened = PreferenceUtils.getBoolean(mContext, MyContants.KEY_IS_VIBRATE_OPENED);
        isCanusedInBlack = PreferenceUtils.getBoolean(mContext, MyContants.KEY_IS_CANUSE_IN_BLACK);
        isClickEffect = PreferenceUtils.getBoolean(mContext, MyContants.KEY_IS_CLICK_EFFECT);
        isCameraEffect = PreferenceUtils.getBoolean(mContext, MyContants.KEY_IS_CAMARA_EFFECT);

        //初始化状态值
        switch_vibration.setOpened(isvibrateOpened);
        switch_canuse_in_black.setOpened(isCanusedInBlack);
        switch_click.setOpened(isClickEffect);
        switch_camera.setOpened(isCameraEffect);
    }

    @Override
    protected void initListener() {
        initSwitchListener();
    }

    private void initSwitchListener() {
        switch_vibration.setOnClickListener(this);
        switch_canuse_in_black.setOnClickListener(this);
        switch_click.setOnClickListener(this);
        switch_camera.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.switch_vibration://震动反馈
                setStateToSharepreference(mContext, switch_vibration, isvibrateOpened,
                        MyContants.KEY_IS_VIBRATE_OPENED);
                break;

            case R.id.switch_canuse_in_black://锁屏黑屏状态下可用
                setStateToSharepreference(mContext, switch_canuse_in_black, isCanusedInBlack,
                        MyContants.KEY_IS_CANUSE_IN_BLACK);
                break;

            case R.id.switch_click://单击键开关
                setStateToSharepreference(mContext, switch_click, isClickEffect,
                        MyContants.KEY_IS_CLICK_EFFECT);
                break;

            case R.id.switch_camera://相机
                setStateToSharepreference(mContext, switch_camera, isCameraEffect,
                        MyContants.KEY_IS_CAMARA_EFFECT);
                break;
        }

    }

    private void setStateToSharepreference(Context mContext, SwitchView switchView, boolean switchState, String key) {
        switchState = switchView.isOpened();
        PreferenceUtils.putBoolean(mContext, key, switchState);
        Log.d("LHRTAG", (switchState ? "true" : "false")) ;
    }
}
