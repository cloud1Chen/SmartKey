package com.itel.smartkey.ui.toolbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itel.smartkey.R;
import com.itel.smartkey.adapter.DialogMenuFuncAdapter;
import com.itel.smartkey.bean.Settings;
import com.itel.smartkey.contants.MyContants;
import com.itel.smartkey.contants.SmartKeyAction;
import com.itel.smartkey.service.DBService;
import com.itel.smartkey.utils.Execute;

import java.util.ArrayList;
import java.util.List;

/**
 * 弹窗菜单，单击背部的smartkey，会弹出该activity，根据用户是否设置功能而提供两种布局
 * Created by huorong.liang on 2017/1/20.
 */

public class DialogMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private RecyclerView mRecyclerView;
    private DialogMenuFuncAdapter menuFuncAdapter;
    private List<Settings> mRvDatas = new ArrayList<>();

    private Button bt_addnow;

    public static final int LAYOUT_MODE_FUNCTION = 1;
    public static final int LAYOUT_MODE_NO_FUNCTION = 2;
    private int layoutMode = 2;//布局mode

    private HomeKeyEventBroadCastReceiver receiver;
    private DBService mDBService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mDBService = new DBService(mContext);
        receiver = new HomeKeyEventBroadCastReceiver();// 注册监听HOME键的广播
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

        initDatas();
        initView();
    }

    private void initView() {//根据表2的实体类是否具有功能而展示不同的界面，只要（0 —— 9）中有一个具有功能（即funcId != -1），则展示有功能的列表
        for (int i = 0; i < 9; i++){
            int funcId = mRvDatas.get(i).getFuncId();
            if (funcId != MyContants.NOT_FUNCTION){
                layoutMode = 1;
                break;
            }
        }
        Log.d("LHRTAG", "layoutMode  " + layoutMode);
        if (layoutMode == LAYOUT_MODE_FUNCTION) {
            setContentView(R.layout.activity_dialogmenuactivity_contains_function);
            initFunctinView();
        } else {
            setContentView(R.layout.activity_dialogmenuactivity_no_function);
            initNoFunctinView();
        }
    }

    private void initFunctinView() {
        initRecyclerView();
    }

    private void initNoFunctinView() {
        bt_addnow = (Button) findViewById(R.id.bt_addnow);
        bt_addnow.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_dialogmenu);
        menuFuncAdapter = new DialogMenuFuncAdapter(mContext, mRvDatas);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(menuFuncAdapter);
        //recycleview的点击事件
        menuFuncAdapter.setOnItemClickListener(new DialogMenuFuncAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
            @Override
            public void itemClick(View view, int position) {
                Log.d("LHRTAG", "点击了 " + position + "项");
                Settings settingsBean = mRvDatas.get(position);
                int funcAcId = settingsBean.getFuncAcId();
                int funcId = settingsBean.getFuncId();
                Log.d("LHRTAG", "点击了 " + position + "项" + "funcAcId为 " + funcAcId + "funcId为 " + funcId);
                if (funcId == MyContants.NOT_FUNCTION){
                    Toast.makeText(mContext, "还没有设置功能，请设置噢", Toast.LENGTH_SHORT).show();
                }else{
                    Execute.run(mContext, funcAcId, -1, null);
                    DialogMenuActivity.this.finish();
                }
            }
        });
    }

    /**
     * 读取数据库表2中的数据，保存到mDatas中
     */
    private void initDatas() {
        //add for fix load icon lhr 2017/2/4 {
        List<Settings> mFindedRvDatas = mDBService.findAllSettings();
        mRvDatas.clear();
        for (int i=0; i<mFindedRvDatas.size()-2; i++){
            mRvDatas.add(mFindedRvDatas.get(i));
            Log.d("LHRTAG", "DialogMenuActivity mRvDatas.funcAcId " + mRvDatas.get(i).getFuncId());
        }
        Log.d("LHRTAG", "Dialog mRvDatas size " + mRvDatas.size());
        //fix end }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LHRTAG", "onDestroy()");
        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View view) {
        Log.d("LHRTAG", "onClick()");
        switch (view.getId()) {
            case R.id.bt_addnow:
                Intent intent = new Intent();
                intent.setAction(SmartKeyAction.ACTION_OPEN_FRONTTOOLBOX_ACTIVITY);
                startActivity(intent);
                this.finish();
                break;
        }
    }

    /**
     * 监听home键广播，如果用户点击了按键，则finsh当前的activity
     */
    class HomeKeyEventBroadCastReceiver extends BroadcastReceiver {
        static final String SYSTEM_REASON = "reason";
        static final String SYSTEM_HOME_KEY = "homekey";
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                Log.d("LHRTAG", "onReceive()");
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (reason != null) {
                    if (reason.equals(SYSTEM_HOME_KEY)) {
                        DialogMenuActivity.this.finish();//这里是你监听到HOME键要做什么，我这里是销毁Activity
                    }
                }
            }
        }
    }


}
