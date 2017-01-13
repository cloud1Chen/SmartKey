package com.itel.smartkey.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.itel.smartkey.R;
import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.ui.phone.SelectContactsActivity;

/**
 * Created by huorong.liang on 2017/1/6.
 */

public class MenuActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Button fun_select_contacts;

    @Override
    protected int getResultId() {
        return R.layout.menu_activity;
    }

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar, true, "工具箱");
        fun_select_contacts = (Button) findViewById(R.id.fun_select_cantacts);

    }

    @Override
    protected void initListener() {
        fun_select_contacts.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fun_select_cantacts:
                Intent in = new Intent(this, SelectContactsActivity.class);
                startActivity(in);
                break;
        }
    }
}
