package com.itel.smartkey.ui.phone;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.itel.smartkey.R;
import com.itel.smartkey.adapter.SelectCantactsAdapter;
import com.itel.smartkey.adapter.SelectContactsNumberAdapter;
import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.bean.ContactsBean;
import com.itel.smartkey.utils.ContactsUtils;
import com.itel.smartkey.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SelectContactsActivity extends BaseActivity {

    //判断是否由SetupSOSContactsInfoActivity调起
    private boolean isSetSOSInfo;

    //获取调起的intent
    Intent intent;

    //主界面相关
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private Context mContext;
    private ArrayList<ContactsBean> mDatas = new ArrayList<>();
    private SelectCantactsAdapter mAdapter;

    //popupWindow相关
    private PopupWindow mPopupWindow;
    private RecyclerView recyclerViewSelectNum;
    private SelectContactsNumberAdapter mSelectContactsNumberAdapter;
    private List<String> mNumbers = new ArrayList<>();

    private View rootView;


    @Override
    protected int getResultId() {
        return R.layout.activity_select;
    }

    @Override
    protected void initView() {
        mContext = this;
        rootView = findViewById(R.id.activity_select);

        intent = getIntent();
        isSetSOSInfo = intent.getBooleanExtra("isFromSetupSOS", false);

        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_CONTACTS}, 1);
        } else {
            Log.d("LHRTAG", "已经授予权限");
        }

        //toobar初始化
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar, R.mipmap.pic_back_bar_black, "选择联系人", false, "505050", "f3f4f5");

        //recycleview相关的初始化
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_selectPhone);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));//设置垂直方向的线性布局
        recyclerView.addItemDecoration(new SelectContactsItemDecoration(mContext, SelectContactsItemDecoration.VERTICAL_LIST));//设置item的下划线
        mAdapter = new SelectCantactsAdapter(mContext, mDatas);
        mAdapter.setOnItemClickListener(new SelectCantactsAdapter.onItemClickListener() {//设置item的点击事件
            @Override
            public void onItemClick(View view, int position) {
                Log.d("LHRTAG", "setOnItemClickListener");
                if (mDatas.get(position).getPhones().size() > 1) {//如果该联系人的号码多于一个，则弹出框选择
                    Toast.makeText(mContext, "numbers" + mDatas.get(position).getPhones().get(0)
                            + " " + mDatas.get(position).getPhones().get(1), Toast.LENGTH_SHORT).show();
                    if (!mPopupWindow.isShowing() && mPopupWindow != null) {
                        mNumbers.clear();
                        mNumbers.addAll(mDatas.get(position).getPhones());
                        mSelectContactsNumberAdapter.setDatas(mNumbers);
                        showPopupWindow();
                    } else if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                } else if (mDatas.get(position).getPhones().size() == 1) {//如果该联系人的号码只有一个，则直接返回
                    Toast.makeText(mContext, "numbers" + mDatas.get(position).getPhones().get(0),
                            Toast.LENGTH_SHORT).show();
                    if (isSetSOSInfo){//由设置SOS启动
                        Intent intent = getIntent();
                        Bundle bundle = new Bundle();
                        bundle.putString("number", mDatas.get(position).getPhones().get(0));
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{//由设置联系人启动
                        Intent in = new Intent(Intent.ACTION_CALL);
                        in.setData(Uri.parse("tel:" + mDatas.get(position).getPhones().get(0)));
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        }else{
                            startActivity(in);
                        }
                    }
                } else {//如果该联系人没有号码，则提示用户重新选择一个联系人
                    Toast.makeText(mContext, "该联系人无号码",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //                mAdapter.removeItem(position);
                Log.d("LHRTAG", "长按删除了第" + position + "项");
            }
        });
        recyclerView.setAdapter(mAdapter);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(mAdapter));
//        itemTouchHelper.attachToRecyclerView(recyclerView);
        //初始化PopupWindow
        initPopupWindow();
    }

    //初始化PopupWindow
    private void initPopupWindow() {
        View popupWindow = LayoutInflater.from(mContext).inflate(R.layout.select_contacts_popupwindow_layout, null);
        recyclerViewSelectNum = (RecyclerView) popupWindow.findViewById(R.id.recycleview_selectNum);
        mSelectContactsNumberAdapter = new SelectContactsNumberAdapter(mContext, mNumbers);
        //item的点击事件(PS: 在这里执行拨打电话的逻辑，到时候，替换为把该号码保存到本地，具体是保存到数据库还是sharePreference还待研究)
        mSelectContactsNumberAdapter.setmOnItemClickListener(new SelectContactsNumberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, "你选择了" + mNumbers.get(position), Toast.LENGTH_SHORT).show();

                if (isSetSOSInfo){//由设置SOS启动
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    bundle.putString("number", mNumbers.get(position));
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{//由设置联系人启动
                    Intent in = new Intent(Intent.ACTION_CALL);
                    in.setData(Uri.parse("tel:" + mNumbers.get(position)));
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }else{
                        startActivity(in);
                    }
                }

                mPopupWindow.dismiss();
            }
        });
        recyclerViewSelectNum.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewSelectNum.addItemDecoration(new SelectContactsItemDecoration(mContext, SelectContactsItemDecoration.VERTICAL_LIST));
        recyclerViewSelectNum.setAdapter(mSelectContactsNumberAdapter);
        mPopupWindow = new PopupWindow(popupWindow, ViewGroup.LayoutParams.MATCH_PARENT, Utils.dip2px(mContext, 240));
        mPopupWindow.setFocusable(true);//设置popupWindow可以获取焦点，则里面的edittext可以获取到焦点，响应输入事件。
                                          // 但是activity上的其他控件不能再获取焦点（不能响应事件），除非popupWindow消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.setWindowBackgroundAlpha(mContext, 1f);//当popupWindow关闭时，把window设置回完全透明（没有变灰）
            }
        });
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        ContactsUtils.getContacts(mContext, mDatas);
        for (int i=0; i<mDatas.size(); i++){
            Log.d("LHRTAG", "name "+mDatas.get(i).getName());
            ArrayList<String> mPhones = mDatas.get(i).getPhones();
            for (int j=0; j<mPhones.size(); j++){
                Log.d("LHRTAG", "phone "+mPhones.get(j));
            }
        }
        mAdapter.setDatas(mDatas);
        Log.d("LHRTAG", "mDatas.size()" + mDatas.size()  );
    }


    /**
     * 展示popupWindow
     */
    private void showPopupWindow(){
        if (mPopupWindow!=null && !mPopupWindow.isShowing()){
            mPopupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
            Utils.setWindowBackgroundAlpha(mContext, 0.4f);
        }
    }

    //申请权限是否成功的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == 1)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else
            {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
