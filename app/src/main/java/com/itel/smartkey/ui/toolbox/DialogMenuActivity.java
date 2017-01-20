package com.itel.smartkey.ui.toolbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.itel.smartkey.R;
import com.itel.smartkey.adapter.DialogMenuFuncAdapter;
import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.bean.FuncActiveBean;
import com.itel.smartkey.contants.MyContants;
import com.itel.smartkey.ui.phone.SelectContactsActivity;

import java.util.ArrayList;

/**
 * 弹窗菜单，单击背部的smartkey，会弹出该activity，根据用户是否设置功能而提供两种布局
 * Created by huorong.liang on 2017/1/17.
 */

public class DialogMenuActivity extends BaseActivity implements View.OnClickListener {

    private Button bt_addNow;
    private RecyclerView mRecyclerView;
    private DialogMenuFuncAdapter menuFuncAdapter;
    private Context mContext;
    private ArrayList<FuncActiveBean> mRvDatas;

    @Override
    protected int getResultId() {
        return R.layout.activity_dialogmenuactivity_contains_function;
    }

    @Override
    protected void initView() {
        mContext = this;
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_dialogmenu);
        mRvDatas = new ArrayList<>();
        menuFuncAdapter = new DialogMenuFuncAdapter(mContext,mRvDatas);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(menuFuncAdapter);
        //recycleview的点击事件
        menuFuncAdapter.setOnItemClickListener(new DialogMenuFuncAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View view, int position) {
                Log.d("LHRTAG", "点击了 " + position + "项" );
                Intent intent = new Intent(mContext, SelectContactsActivity.class);
                startActivity(intent);
            }
        });
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
        menuFuncAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LHRTAG", "onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LHRTAG", "onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LHRTAG", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LHRTAG", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LHRTAG", "onDestroy()");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }
}
