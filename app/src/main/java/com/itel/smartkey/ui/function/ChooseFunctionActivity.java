package com.itel.smartkey.ui.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.itel.smartkey.R;
import com.itel.smartkey.adapter.ChooseFunctionAdapter;
import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.bean.Function;
import com.itel.smartkey.bean.Settings;
import com.itel.smartkey.contants.SmartKeyAction;
import com.itel.smartkey.service.DBService;
import com.itel.smartkey.ui.phone.SelectContactsItemDecoration;
import com.itel.smartkey.ui.toolbox.FrontToolBoxActivity;

import java.util.ArrayList;
import java.util.List;

import static com.itel.smartkey.ui.toolbox.FrontToolBoxActivity.ITEM_REAL_POSITION;


/**
 * 选择功能列表界面,查询数据库表1中的内容并解析展示，
 * 单击列表中的功能，会对应的把这些功能添加到表2中，同时返回bean到调起页面
 * Created by huorong.liang on 2017/1/19.
 */

public class ChooseFunctionActivity extends BaseActivity {



    public static String TAG = "ChooseFunctionActivity";
    public static final int REQUEST_OPEN_SETURL = 1;
    public static final int REQUEST_OPEN_CHOOSECONTACTS = 2;
    public static final int REQUEST_OPEN_CHOOSEAPPS = 3;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ChooseFunctionAdapter functionsAdapter;
    private List<Function> mDatas = new ArrayList<>();
    private Context mContext;

    //加载不同的类型
    private boolean isClick;
    private boolean isDoubleClick;
    private boolean isLongClick;

    private DBService service;

    //调起的intent以及相关参数
    private Intent mIntent;
    private int itemPosition;








    @Override
    protected int getResultId() {
        return R.layout.activity_choosefunction;
    }

    @Override
    protected void initView() {
        mContext = this;

        //获得调起的intent
        mIntent = getIntent();
        itemPosition = mIntent.getIntExtra(ITEM_REAL_POSITION, 0);

        //add 2017 1 23
        service = new DBService(this);
        boolean isTableNoRecord = service.isTableNoRecord();
        Log.d("LHRTAG", "isTableNoRecord" + isTableNoRecord );

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
                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                Function functionBean = mDatas.get(position);
                int funcTionId = functionBean.getId();
                Settings settingsBean = new Settings();
                settingsBean.setFuncId(funcTionId);
                bundle.putSerializable(FrontToolBoxActivity.FUNCTION_BEAN, functionBean);
                bundle.putSerializable(FrontToolBoxActivity.SETTINGS_BEAN, settingsBean);
                bundle.putInt(FrontToolBoxActivity.ITEM_REAL_POSITION, itemPosition);
                bundle.putInt(FrontToolBoxActivity.FUNCTION_FUNCID, funcTionId);
                intent.putExtras(bundle);
                Log.d("LHRTAG", "funcTionId " + funcTionId);
                if (funcTionId == 9){//打开网址
                    intent.setAction(SmartKeyAction.ACTION_OPEN_SETUPURL_ACTIVITY);
                    startActivityForResult(intent, REQUEST_OPEN_SETURL);
                }else if (funcTionId == 10){//打开电话
                    intent.setAction(SmartKeyAction.ACTION_OPEN_CHOOSECONTACTS_ACTIVITY);
                    startActivityForResult(intent, REQUEST_OPEN_CHOOSECONTACTS);
                }else if (funcTionId == 1){//打开apps
                    intent.setAction(SmartKeyAction.ACTION_OPEN_CHOOSEAPPS_ACTIVITY);
                    startActivityForResult(intent, REQUEST_OPEN_CHOOSEAPPS);
                }else {
                    String data5 = null;
                    if (funcTionId == 5){
                        data5 = Function.METHOD_TYPE_OPEN_NOTIFICATION + "";
                    }else if (funcTionId == 7){
                        data5 = Function.METHOD_TYPE_OPEN_FLASHLIGHT + "";
                    }
                    settingsBean.setData5(data5);
                    bundle.putSerializable(FrontToolBoxActivity.SETTINGS_BEAN, settingsBean);
                    setResult(RESULT_OK, intent);
                    finish();
                }
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
        List<Function> list = service.findAllFunction();
        Log.d("jlog", "list:" + list.size());
        for(int i=0;i<list.size();i++)
        {
            Function f = list.get(i);
            Log.d("jlog", "id:" + f.getId() + " name:" + f.getName());
        }
        mDatas.addAll(list);
        functionsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Settings setitngsBean;
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_OPEN_SETURL){

            }else if (requestCode == REQUEST_OPEN_CHOOSECONTACTS){

            }else if (requestCode == REQUEST_OPEN_CHOOSEAPPS){
                setResult(RESULT_OK, data);
                Settings settingsBean = (Settings) data.getSerializableExtra(FrontToolBoxActivity.SETTINGS_BEAN);
                String packageName = settingsBean.getData2();
                String activityClassName = settingsBean.getData3();
                byte[] iconBytes = settingsBean.getFuncAcIconBytes();
                String appsName = settingsBean.getFuncAcName();
                Log.d("LHRTAG", "ChooseFunctionActivity packageName " + packageName);
                Log.d("LHRTAG", "ChooseFunctionActivity activityClassName " + activityClassName);
                Log.d("LHRTAG", "ChooseFunctionActivity iconBytes " + iconBytes);
                Log.d("LHRTAG", "ChooseFunctionActivity appsName " + appsName);
                finish();
            }else {

            }
        }

    }
}
