package com.itel.smartkey;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.ui.toolbox.FrontToolBoxActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private static final String TAG = "BaseActivityTAG";
    private Context mContext;
    private RelativeLayout relativelayout_click;

    @Override
    protected int getResultId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mContext = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar, "", true);
        relativelayout_click = (RelativeLayout) findViewById(R.id.relativelayout_click);

    }

    @Override
    protected void initListener() {
        relativelayout_click.setOnClickListener(this);
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
                startActivity(intent);
                break;
        }
    }

}
