package com.itel.smartkey.ui.toolbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.itel.smartkey.R;
import com.itel.smartkey.adapter.FrontToolboxFuncAdapter;
import com.itel.smartkey.adapter.SimpleItemTouchHelperCallback;
import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.bean.FuncActiveBean;
import com.itel.smartkey.contants.MyContants;
import com.itel.smartkey.ui.phone.SelectContactsActivity;

import java.util.ArrayList;

/**
 * 工具箱activity
 * Created by huorong.liang on 2017/1/16.
 */

public class FrontToolBoxActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    FrontToolboxFuncAdapter mAdapter;
    private Context mContext;
    private ArrayList<FuncActiveBean> mRvDatas;


    @Override
    protected int getResultId() {
        return R.layout.activity_fronttoolbox;
    }

    @Override
    protected void initView() {
        mContext = this;
        //初始化toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(mToolbar, R.mipmap.pic_back_bar_white, "工具箱", false, null, null);
        //初始化recycleview
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_fronttoolbox);
        mRvDatas = new ArrayList<>();
        mAdapter = new FrontToolboxFuncAdapter(mContext,mRvDatas);
        //recycleview的点击事件
        mAdapter.setOnItemClickListener(new FrontToolboxFuncAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View view, int position) {
                Log.d("LHRTAG", "点击了 " + position + "项" );
                Intent intent = new Intent(mContext, SelectContactsActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        //设置recycleview的item可长按拖拽
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

        FuncActiveBean bean = new FuncActiveBean();
        bean.setFuncAcId(1L);
        bean.setFuncAcIconId(R.mipmap.pic_list_apps);
        bean.setFuncAcName(mContext.getResources().getString(R.string.open_an_app));
        mRvDatas.add(bean);

        FuncActiveBean bean1 = new FuncActiveBean();
        bean1.setFuncAcId(2L);
        bean1.setFuncAcIconId(R.mipmap.pic_list_takephoto);
        bean1.setFuncAcName(mContext.getResources().getString(R.string.camera));
        mRvDatas.add(bean1);

        FuncActiveBean bean2 = new FuncActiveBean();
        bean2.setFuncAcId(3L);
        bean2.setFuncAcIconId(R.mipmap.pic_list_beautshot);
        bean2.setFuncAcName(mContext.getResources().getString(R.string.beauty_selfie));
        mRvDatas.add(bean2);

        FuncActiveBean bean3 = new FuncActiveBean();
        bean3.setFuncId(MyContants.NOT_FUNCTION);
        mRvDatas.add(bean3);

        for (int i=0; i<5; i++){
            FuncActiveBean bean4 = new FuncActiveBean();
            bean4.setFuncAcId(Long.parseLong(i + ""));
            bean4.setFuncAcIconId(R.mipmap.pic_list_pushtopass);
            bean4.setFuncAcName(mContext.getResources().getString(R.string.click_accelerate));
            mRvDatas.add(bean4);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
