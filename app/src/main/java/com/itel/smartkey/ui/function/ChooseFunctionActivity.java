package com.itel.smartkey.ui.function;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.itel.smartkey.R;
import com.itel.smartkey.adapter.ChooseFunctionAdapter;
import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.bean.FuncBean;
import com.itel.smartkey.contants.MyContants;
import com.itel.smartkey.ui.phone.SelectContactsItemDecoration;

import java.util.ArrayList;

/**
 * 选择功能列表界面,查询数据库表1中的内容并解析展示，
 * 单击列表中的功能，会对应的把这些功能添加到表2中，同时返回bean到调起页面
 * Created by huorong.liang on 2017/1/19.
 */

public class ChooseFunctionActivity extends BaseActivity {

    public static String TAG = "ChooseFunctionActivity";

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ChooseFunctionAdapter functionsAdapter;
    private ArrayList<FuncBean> mDatas = new ArrayList<>();
    private Context mContext;

    //加载不同的类型
    private boolean isClick;
    private boolean isDoubleClick;
    private boolean isLongClick;



    @Override
    protected int getResultId() {
        return R.layout.activity_choosefunction;
    }

    @Override
    protected void initView() {
        mContext = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        String toolbarTitle = mContext.getString(R.string.toolbar_title_choose_function);
        String toolbarTextColor = mContext.getString(R.string.toolbar_title_text_color_choose_function);
        String toolbarBackgroundColor = mContext.getString(R.string.toolbar_background_color_choose_function);
        initToolbar(toolbar,R.mipmap.pic_back_bar_black,toolbarTitle,false, toolbarTextColor, toolbarBackgroundColor);

        initRecycleView();
    }

    private void initRecycleView() {
        //recycleview相关的初始化
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_choose_function);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));//设置垂直方向的线性布局
        recyclerView.addItemDecoration(new SelectContactsItemDecoration(mContext, SelectContactsItemDecoration.VERTICAL_LIST));//设置item的下划线
        functionsAdapter = new ChooseFunctionAdapter(mContext, mDatas);
        functionsAdapter.setOnItemClickListener(new ChooseFunctionAdapter.OnItemClickListener() {//设置item的点击事件
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(functionsAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        FuncBean bean = new FuncBean();
        bean.setFuncId(1L);
        bean.setIconId(R.mipmap.pic_list_apps);
        bean.setFuncName(mContext.getResources().getString(R.string.open_an_app));
        mDatas.add(bean);

        FuncBean bean1 = new FuncBean();
        bean1.setFuncId(2L);
        bean1.setIconId(R.mipmap.pic_list_takephoto);
        bean1.setFuncName(mContext.getResources().getString(R.string.camera));
        mDatas.add(bean1);

        FuncBean bean2 = new FuncBean();
        bean2.setFuncId(3L);
        bean2.setIconId(R.mipmap.pic_list_beautshot);
        bean2.setFuncName(mContext.getResources().getString(R.string.beauty_selfie));
        mDatas.add(bean2);

        FuncBean bean3 = new FuncBean();
        bean3.setFuncId(MyContants.NOT_FUNCTION);
//        bean3.setIconId(R.mipmap.pic_list_apps);
//        bean3.setFuncName(mContext.getResources().getString(R.string.open_an_app));
        mDatas.add(bean3);



        for (int i=0; i<5; i++){
            FuncBean bean4 = new FuncBean();
            bean4.setFuncId(Long.parseLong(i + ""));
            bean4.setIconId(R.mipmap.pic_list_pushtopass);
            bean4.setFuncName(mContext.getResources().getString(R.string.click_accelerate));
            mDatas.add(bean4);
        }
        functionsAdapter.notifyDataSetChanged();
    }
}
