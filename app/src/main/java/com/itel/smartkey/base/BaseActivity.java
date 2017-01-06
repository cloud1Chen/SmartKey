package com.itel.smartkey.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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
    public void initToolbar(Toolbar toolbar, boolean homeAsUpEnabled, String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (homeAsUpEnabled){
            toolbar.setNavigationIcon(R.drawable.ab_android);
        }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LHRTAG", "navigation onClick");
                finish();
            }
        });
    };
}
