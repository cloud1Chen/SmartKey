package com.itel.smartkey.ui.phone;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.itel.smartkey.R;
import com.itel.smartkey.adapter.OnRecycleViewItemClickListener;
import com.itel.smartkey.adapter.SelectCantactsAdapter;
import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.bean.ContactsBean;
import com.itel.smartkey.utils.ContactsUtils;

import java.util.ArrayList;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class SelectActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private Context mContext;
    private ArrayList<ContactsBean> mDatas = new ArrayList<>();
    private SelectCantactsAdapter mAdapter;
    private AdapterView.OnItemClickListener onItemClickListener;

    @Override
    protected int getResultId() {
        return R.layout.activity_select;
    }

    @Override
    protected void initView() {
        mContext = this;
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) mContext, new String[] {Manifest.permission.WRITE_CONTACTS}, 1);
        }else{
            Log.d("LHRTAG", "已经授予权限");
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar, true, "选择联系人");

        //recycleview相关
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_selectPhone);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, HORIZONTAL, false));
        mAdapter = new SelectCantactsAdapter(mContext, mDatas);
        mAdapter.setOnRecycleViewItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mDatas.get(position).getPhones().size() > 1){//号码多于两个

                    Toast.makeText(mContext,
                            "numbers" + mDatas.get(position).getPhones().get(0) + " "
                                    + mDatas.get(position).getPhones().get(1),Toast.LENGTH_SHORT).show();
                }else {//号码最多一个（或者没有）
                    Toast.makeText(mContext,
                            "numbers" + mDatas.get(position).getPhones().get(0), Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

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
