package com.itel.smartkey.receivertest;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.itel.smartkey.R;
import com.itel.smartkey.base.BaseActivity;

/**
 * Created by huorong.liang on 2017/1/11.
 */

public class SentBroadcastActivity extends BaseActivity implements View.OnClickListener {
    public static final String ACTION_SMARTKEY_SINGLE_CLICK = "com.itel.smartkey.action.ACTION_SMARTKEY_SINGLE_CLICK";
    public static final String ACTION_SMARTKEY_DOUBLE_CLICK = "com.itel.smartkey.action.ACTION_SMARTKEY_DOUBLE_CLICK";
    public static final String ACTION_SMARTKEY_LONG_CLICK = "com.itel.smartkey.action.ACTION_SMARTKEY_LONG_CLICK";


    private Button btSentOneClick;
    private Button btSentDoubleClick;
    private Button btSentLongClick;
    @Override
    protected int getResultId() {
        return R.layout.activity_sentbroadcast;
    }

    @Override
    protected void initView() {
        btSentOneClick = (Button) findViewById(R.id.sent_oneclick_broadcast);
        btSentDoubleClick = (Button) findViewById(R.id.sent_doubleclick_broadcast);
        btSentLongClick = (Button) findViewById(R.id.sent_longclick_broadcast);
    }

    @Override
    protected void initListener() {
        btSentOneClick.setOnClickListener(this);
        btSentDoubleClick.setOnClickListener(this);
        btSentLongClick.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        Log.d("LHRTAG",view.getId() + "onClick()");
        switch (view.getId()){
            case R.id.sent_oneclick_broadcast:
                Intent in1 = new Intent();
                in1.setAction(ACTION_SMARTKEY_SINGLE_CLICK);
                sendBroadcast(in1);
                break;
            case R.id.sent_doubleclick_broadcast:
                Intent in2 = new Intent();
                in2.setAction(ACTION_SMARTKEY_DOUBLE_CLICK);
                sendBroadcast(in2);
                break;
            case R.id.sent_longclick_broadcast:
                Intent in3 = new Intent();
                in3.setAction(ACTION_SMARTKEY_LONG_CLICK);
                sendBroadcast(in3);
                break;
        }
    }
}
