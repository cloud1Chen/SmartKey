package com.itel.smartkey.receivertest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.itel.smartkey.R;
import com.itel.smartkey.adapter.DialogMenuFuncAdapter;
import com.itel.smartkey.bean.FuncActiveBean;
import com.itel.smartkey.ui.phone.SelectContactsActivity;

import java.util.ArrayList;

/**
 * Created by huorong.liang on 2017/1/20.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<FuncActiveBean> mRvDatas;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private DialogMenuFuncAdapter menuFuncAdapter;
    public static int LAYOUT_MODE_FUNCTION = 1;
    public static int LAYOUT_MODE_NO_FUNCTION= 2;
    private int layoutMode;

    private Button bt_addnow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mRvDatas = new ArrayList<>();
        initDatas();
        if (mRvDatas.size() > 0){
            layoutMode = 1;
            setContentView(R.layout.activity_dialogmenuactivity_contains_function);
        }else {
            layoutMode = 2;
            setContentView(R.layout.activity_dialogmenuactivity_no_function);
        }

        if (layoutMode == LAYOUT_MODE_FUNCTION){
            initFunctinView();
        }else if (layoutMode == LAYOUT_MODE_NO_FUNCTION){
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

    private void initDatas() {
//        FuncActiveBean bean = new FuncActiveBean();
//        bean.setFuncAcId(1L);
//        bean.setFuncAcIconId(R.mipmap.pic_list_apps);
//        bean.setFuncAcName(mContext.getResources().getString(R.string.open_an_app));
//        mRvDatas.add(bean);
//
//        FuncActiveBean bean1 = new FuncActiveBean();
//        bean1.setFuncAcId(2L);
//        bean1.setFuncAcIconId(R.mipmap.pic_list_takephoto);
//        bean1.setFuncAcName(mContext.getResources().getString(R.string.camera));
//        mRvDatas.add(bean1);
//
//        FuncActiveBean bean2 = new FuncActiveBean();
//        bean2.setFuncAcId(3L);
//        bean2.setFuncAcIconId(R.mipmap.pic_list_beautshot);
//        bean2.setFuncAcName(mContext.getResources().getString(R.string.beauty_selfie));
//        mRvDatas.add(bean2);
//
//        FuncActiveBean bean3 = new FuncActiveBean();
//        bean3.setFuncId(MyContants.NOT_FUNCTION);
//        mRvDatas.add(bean3);
//
//        for (int i=0; i<5; i++){
//            FuncActiveBean bean4 = new FuncActiveBean();
//            bean4.setFuncAcId(Long.parseLong(i + ""));
//            bean4.setFuncAcIconId(R.mipmap.pic_list_pushtopass);
//            bean4.setFuncAcName(mContext.getResources().getString(R.string.click_accelerate));
//            mRvDatas.add(bean4);
//        }
    }

    @Override
    public void onClick(View view) {
        Log.d("LHRTAG", "onClick()");
    }
}
