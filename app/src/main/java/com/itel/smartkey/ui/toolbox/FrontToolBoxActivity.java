package com.itel.smartkey.ui.toolbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.itel.smartkey.R;
import com.itel.smartkey.adapter.FrontToolboxFuncAdapter;
import com.itel.smartkey.adapter.SimpleItemTouchHelperCallback;
import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.bean.Function;
import com.itel.smartkey.bean.Settings;
import com.itel.smartkey.service.DBService;
import com.itel.smartkey.ui.function.ChooseFunctionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 工具箱activity
 * Created by huorong.liang on 2017/1/16.
 */

public class FrontToolBoxActivity extends BaseActivity implements View.OnClickListener {

    public static final String ITEM_REAL_POSITION = "itemPositon";
    public static final String FUNCTION_FUNCID = "funcid";
    public static final String FUNCTION_BEAN = "functinBean";
    public static final String SETTINGS_BEAN = "settingsBean";

    public static final int REQUEST_CHOOSE_FUNCTION = 1;

    private RelativeLayout relative_layout;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private FrontToolboxFuncAdapter mAdapter;
    private Context mContext;
    private List<Settings> mRvDatas = new ArrayList<>();;
    private ImageView ivBackground;

    private DBService mDBService;





    @Override
    protected int getResultId() {
        return R.layout.activity_fronttoolbox;
    }

    @Override
    protected void initView() {
        mContext = this;
        mDBService = new DBService(mContext);




        ivBackground = (ImageView) findViewById(R.id.background);
        startBackgroundAnimtion(mContext, ivBackground);

        //初始化toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(mToolbar, R.mipmap.pic_back_bar_white, "工具箱", false, null, null);

        //初始化recycleview
        initRecyclerView();

    }

    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_fronttoolbox);

        mAdapter = new FrontToolboxFuncAdapter(mContext,mRvDatas);
        mAdapter.setOnItemClickListener(new FrontToolboxFuncAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View view, int position) {//进去选择功能界面，获取对应的funcId并返回Functin，
                                                                // 如果有额外参数需要配置（如联系人，打开网址，打开app），则同时返回对应的参数
                Log.d("LHRTAG", "点击了 " + position + "项" );

                Intent intent = new Intent(mContext, ChooseFunctionActivity.class);
                intent.putExtra(ITEM_REAL_POSITION, position);
                startActivityForResult(intent, REQUEST_CHOOSE_FUNCTION);
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }


    @Override
    protected void initListener() {
    }

    /**
     * 初始化数据源
     */
    @Override
    protected void initData() {
        mRvDatas.addAll(mDBService.findAllSettings());
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_CHOOSE_FUNCTION){//获取得到的intent，获取其中的funcid
                int itemPosition = data.getIntExtra(ITEM_REAL_POSITION, 0);
                int funcId = data.getIntExtra(FUNCTION_FUNCID, -1);
                Log.d("LHRTAG", "FrontToolBoxActivity funcId " + funcId);
                Function functionBean = (Function) data.getSerializableExtra(FUNCTION_BEAN);
                String data5 = data.getStringExtra("data5");
                Log.d("LHRTAG", "FrontToolBoxActivity data5" + data5);
                Settings settingsBean = (Settings) data.getSerializableExtra(SETTINGS_BEAN);
                if (settingsBean == null){
                    settingsBean = new Settings();
                    settingsBean.setFuncId(funcId);
                    if (data5 != null){
                        settingsBean.setData5(data5);
                    }
                    settingsBean.setFuncAcId(itemPosition);
                    mDBService.updateSettings(settingsBean);
                    mRvDatas.set(itemPosition, settingsBean);
                    mAdapter.notifyDataSetChanged();

                }
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
