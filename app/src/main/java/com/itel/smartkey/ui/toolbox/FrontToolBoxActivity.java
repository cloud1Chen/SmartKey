package com.itel.smartkey.ui.toolbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itel.smartkey.R;
import com.itel.smartkey.adapter.FrontToolboxFuncAdapter;
import com.itel.smartkey.adapter.SimpleItemTouchHelperCallback;
import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.bean.Settings;
import com.itel.smartkey.service.DBService;
import com.itel.smartkey.ui.function.ChooseFunctionActivity;
import com.itel.smartkey.utils.Execute;

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
    private List<Settings> mRvDatas = new ArrayList<>();
    private ImageView ivBackground;
    private CardView cardview_fronttoolbox;
    private TextView tv_drag_to_move_the_shortcuts;

    private DBService mDBService;//操作数据库的工具类
    private int itemPosition;//记录点击的item的位置


    @Override
    protected int getResultId() {
        return R.layout.activity_fronttoolbox;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //add for animotion lhr 2017/2/4
        //cardview的动画效果
        Animation animCardview = AnimationUtils.loadAnimation(mContext, R.anim.anim_main_cardview_fronttoolbox_scale);
        cardview_fronttoolbox.startAnimation(animCardview);
    }

    @Override
    protected void initView() {
        mContext = this;
        mDBService = new DBService(mContext);

        tv_drag_to_move_the_shortcuts = (TextView) findViewById(R.id.tv_drag_to_move_the_shortcuts);
        tv_drag_to_move_the_shortcuts.setText(R.string.drag_to_move_the_shortcuts);

        ivBackground = (ImageView) findViewById(R.id.background);
        startBackgroundAnimtion(mContext, ivBackground);
        cardview_fronttoolbox = (CardView) findViewById(R.id.cardview_fronttoolbox);

        //初始化toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        String toolbarTitle = mContext.getString(R.string.toolbox);
        initToolbar(mToolbar, R.mipmap.pic_back_bar_white, toolbarTitle, false, null, null);

        //初始化recycleview
        initRecyclerView();
    }

    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_fronttoolbox);

        mAdapter = new FrontToolboxFuncAdapter(mContext, mRvDatas);
        mAdapter.setOnItemClickListener(new FrontToolboxFuncAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View view, int position) {//进去选择功能界面，获取对应的funcId并返回Functin，
                // 如果有额外参数需要配置（如联系人，打开网址，打开app），则同时返回对应的参数
                Log.d("LHRTAG", "点击了 " + position + "项");

                Intent intent = new Intent(mContext, ChooseFunctionActivity.class);
                intent.putExtra(ChooseFunctionActivity.KEY_FUNCTIONMODE, Execute.MODE_SINGLE_CLICK);//设置模式
                itemPosition = position;
//                intent.putExtra(ITEM_REAL_POSITION, position);
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
        //add for fix load icon lhr 2017/2/4
        initRecyclerViewDatas();
    }

    /**
     * 初始化recyclerView的数据源
     */
    private void initRecyclerViewDatas() {
        List<Settings> mFindedRvDatas = mDBService.findAllSettings();
        Log.d("LHRTAG", "FrontToolBoxActivity mFindedRvDatas.size " + mFindedRvDatas.size());
        mRvDatas.clear();
        for (int i = 2; i < mFindedRvDatas.size(); i++){
            mRvDatas.add(mFindedRvDatas.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_FUNCTION) {//获取得到的intent，获取其中的funcid
//                int itemPosition = data.getIntExtra(ITEM_REAL_POSITION, 0);
                Settings mSettingsBean = mRvDatas.get(itemPosition);
                int funcId = data.getIntExtra(FUNCTION_FUNCID, -1);
                Log.d("LHRTAG", "FrontToolBoxActivity funcId " + funcId);
                Log.d("LHRTAG", "FrontToolBoxActivity funcAcId " + mSettingsBean.getFuncAcId());
                mSettingsBean.setFuncId(funcId);

                Settings mTempSettingsBean = (Settings) data.getSerializableExtra(SETTINGS_BEAN);
                String url = mTempSettingsBean.getData1();
                String packageName = mTempSettingsBean.getData2();
                String activityClass = mTempSettingsBean.getData3();
                String phonenumber = mTempSettingsBean.getData4();
                String methodType = mTempSettingsBean.getData5();
                String data6 = mTempSettingsBean.getData6();
                String data7 = mTempSettingsBean.getData7();
                byte[] funcAcIconBytes = mTempSettingsBean.getFuncAcIconBytes();
                String name = mTempSettingsBean.getFuncAcName();
                String funcAcIconPath = mTempSettingsBean.getFuncAcIconPath();
                int funcAcIconId = mTempSettingsBean.getFuncAcIconId();
                int funcAcNameId = mTempSettingsBean.getFuncAcNameId();


                Log.d("LHRTAG", "FrontToolBoxActivity tem packageName " + packageName);
                Log.d("LHRTAG", "FrontToolBoxActivity tem activityClassName " + activityClass);
                Log.d("LHRTAG", "FrontToolBoxActivity tem iconBytes " + funcAcIconBytes);
                Log.d("LHRTAG", "FrontToolBoxActivity data5 " + methodType);

                if (mTempSettingsBean != null){
                        mSettingsBean.setFuncAcName(name);
                        mSettingsBean.setFuncAcIconBytes(funcAcIconBytes);
                        mSettingsBean.setData1(url);
                        mSettingsBean.setData2(packageName);
                        mSettingsBean.setData3(activityClass);
                        mSettingsBean.setData4(phonenumber);
                        mSettingsBean.setData5(methodType);
                        mSettingsBean.setData6(data6);
                        mSettingsBean.setData7(data7);
                    mSettingsBean.setFuncAcIconPath(funcAcIconPath);
                    mSettingsBean.setFuncAcIconId(funcAcIconId);
                    mSettingsBean.setFuncAcNameId(funcAcNameId);
                }
                Log.d("LHRTAG", "FrontToolBoxActivity url " + mSettingsBean.getData1());
                Log.d("LHRTAG", "FrontToolBoxActivity packageName " + mSettingsBean.getData2());
                Log.d("LHRTAG", "FrontToolBoxActivity activityClassName " + mSettingsBean.getData3());
                Log.d("LHRTAG", "FrontToolBoxActivity iconBytes " + mSettingsBean.getFuncAcIconBytes());
                Log.d("LHRTAG", "FrontToolBoxActivity data5 " + mSettingsBean.getData5());
                mAdapter.notifyDataSetChanged();
                Log.d("LHRTAG", "FrontToolBoxActivity mSettingsBean.getFuncAcId() " + mSettingsBean.getFuncAcId());
                mDBService.updateSettings(mSettingsBean);
            }
        }

    }

    private void setSettingsBeanToDb() {
        for (int i = 0; i < mRvDatas.size(); i++ ){
            int idIncre = i+1;
            int reault = mDBService.updateSettingsById(mRvDatas.get(i), idIncre);
            Log.d("LHRTAG", "FrontToolBoxActivity reault " + reault);
            Log.d("LHRTAG", "FrontToolBoxActivity mDatas.funcId " + mRvDatas.get(i).getFuncId());
            Log.d("LHRTAG", "FrontToolBoxActivity mDatas.funcAcId " + mRvDatas.get(i).getFuncAcId());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
