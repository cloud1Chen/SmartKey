package com.itel.smartkey.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.itel.smartkey.R;

/**
 * Created by huorong.liang on 2017/1/6.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResultId());
        initView();
        initListener();
        initData();
    }
    protected abstract int getResultId();
    protected abstract void initView();
    protected abstract void initListener();
    protected abstract void initData();


    /**
     * 初始化toolbar
     * @param toolbar
     * @param title 菜单名字
     * @param isShowMenu 是否显示左边的菜单键
     */
    public void initToolbar(Toolbar toolbar, String title, boolean isShowMenu){
        initToolbar(toolbar, -1, title, isShowMenu, null, null);
    };

    /**
     * 初始化toolbar
     * @param toolbar
     * @param title 菜单名字
     * @param isShowMenu 是否显示左边的菜单键
     * @param color 菜单栏文字颜色
     * @param backGroundColor toolbar背景栏颜色
     */
    public void initToolbar(Toolbar toolbar, String title, boolean isShowMenu, String color, String backGroundColor){
        initToolbar(toolbar, -1, title, isShowMenu, color, backGroundColor);
    };

    /**
     * 初始化toolbar
     * @param toolbar
     * @param naviIcon 返回键图标
     * @param title 菜单栏名称
     * @param isShowMenu 是否显示右边的菜单键
     * @param titleTextColor 菜单栏文字颜色
     * @param backGroundColor 背景颜色
     */
    public void initToolbar(Toolbar toolbar, int naviIcon, String title, boolean isShowMenu, String titleTextColor, String backGroundColor){
        toolbar.setTitle(title);
        if (titleTextColor != null){//默认采用布局中的颜色
            toolbar.setTitleTextColor(Color.parseColor("#" + titleTextColor));
        }

        if (backGroundColor != null){//默认采用布局中的颜色
            toolbar.setBackgroundColor(Color.parseColor("#" + backGroundColor));
        }

        if (naviIcon != -1){
            toolbar.setNavigationIcon(naviIcon);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LHRTAG", "navigation onClick");
                finish();
//                overridePendingTransition(R.anim.anim_activity_fade_out, R.anim.anim_activity_fade_in);
            }
        });
        if (isShowMenu){
            toolbar.inflateMenu(R.menu.toolbar_menu);
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_tool:
//                        Toast.makeText(getApplicationContext(), "打开工具箱", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setAction("com.itel.smartkey.action.MenuActivity");
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    };

    /**
     * 开始主界面和工具箱界面的背景动画
     * @param mContext
     * @param view
     */
    protected void startBackgroundAnimtion(Context mContext, final View view) {

        final Animation anim_back_scale_1 = AnimationUtils.loadAnimation(mContext, R.anim.anim_main_background_scale_1);
        view.startAnimation(anim_back_scale_1);
        final Animation anim_back_scale_2 = AnimationUtils.loadAnimation(mContext, R.anim.anim_main_background_scale_2);
        anim_back_scale_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(anim_back_scale_1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim_back_scale_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(anim_back_scale_2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public <T extends View> T findViewByIds(int id){
        View mView = this.findViewById(id);
        return (T) mView;
    }
}
