package com.itel.smartkey.ui.surfnet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.itel.smartkey.R;
import com.itel.smartkey.bean.Settings;
import com.itel.smartkey.ui.toolbox.FrontToolBoxActivity;

/**
 * Created by huorong.liang on 2017/1/21.
 */

public class SetupSurfNetUrlActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private EditText et_setup_url;
    private TextView tv_setup_url_cancel;
    private TextView tv_setup_url_ok;
    private Intent mRequestIntent;
    private Settings settingsBean;
    private String urlStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setupsurfneturl);
        mContext = this;
        mRequestIntent = getIntent();
        initView();
        initListener();
    }

    private void initView() {
        et_setup_url = (EditText) findViewById(R.id.et_setup_url);
        tv_setup_url_ok = (TextView) findViewById(R.id.tv_setup_url_ok);
        tv_setup_url_cancel = (TextView) findViewById(R.id.tv_setup_url_cancel);
    }

    private void initListener() {
        tv_setup_url_ok.setOnClickListener(this);
        tv_setup_url_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_setup_url_ok://把网址保存到settingsBean的参数中
                Log.d("LHRTAG", "点击了确认");
                urlStr = et_setup_url.getText().toString().trim();
                Log.d("LHRTAG", "SetupSurfNetUrlActivity urlStr " + urlStr);
                if (mRequestIntent != null){
                    settingsBean = (Settings) mRequestIntent.getSerializableExtra(FrontToolBoxActivity.SETTINGS_BEAN);
                    if (settingsBean != null){
                        settingsBean.setData1(urlStr);
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(FrontToolBoxActivity.SETTINGS_BEAN, settingsBean);
                mRequestIntent.putExtras(bundle);
                setResult(RESULT_OK, mRequestIntent);
                finish();
                break;

            case R.id.tv_setup_url_cancel:
                Log.d("LHRTAG", "点击了取消");
                finish();
                break;
        }
    }
}
