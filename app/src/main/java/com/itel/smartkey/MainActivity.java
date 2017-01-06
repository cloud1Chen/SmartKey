package com.itel.smartkey;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.ui.MenuActivity;
import com.itel.smartkey.ui.phone.SelectActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private static final String TAG = "BaseActivityTAG";
    private Button openMenuActivity;
    private Button openSlectActivity;

    @Override
    protected int getResultId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar, false, "");
        openMenuActivity = (Button) findViewById(R.id.open_menuActivity);
        openSlectActivity = (Button) findViewById(R.id.open_selectActivity);
        // Title
//        toolbar.setTitle("Title");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initListener() {
        openMenuActivity.setOnClickListener(this);
        openSlectActivity.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.open_menuActivity:
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                break;
            case R.id.open_selectActivity:
                Intent intent1 = new Intent(this, SelectActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
