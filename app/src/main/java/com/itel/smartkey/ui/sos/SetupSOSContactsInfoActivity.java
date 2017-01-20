package com.itel.smartkey.ui.sos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.itel.smartkey.R;
import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.ui.phone.SelectContactsActivity;
import com.itel.smartkey.utils.PreferenceUtils;
import com.itel.smartkey.utils.Utils;

/**
 * Created by huorong.liang on 2017/1/9.
 */

public class SetupSOSContactsInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SetupSOSContactsInfoActivity";
    public static final int KEY_SOS_REQUEST_SELECTCONTACTS = 0b001;


    private PopupWindow mSOSPopupWindow;
    private Context mContext;
    private EditText etSosPhoneNumber;
    private EditText etSosMessage;
    private Button btSosCancel;
    private Button btSosOk;
    private ImageView ivGotoChooseContacts;
    private View rootView;
    private Toolbar toolbar;
    private Button openPop;

    @Override
    protected int getResultId() {
        return R.layout.activity_setupsoscontactsinfo;
    }

    @Override
    protected void initView() {
        mContext = this;
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar, R.mipmap.pic_back_bar_black, "设置SOS信息", false, "505050", "f3f4f5");
        //parent
        rootView = findViewById(R.id.activity_setupsoscontactsinfo);
        rootView.post(new Runnable() {
            @Override
            public void run() {
                if (mSOSPopupWindow != null){
                    showPopupWindow();
                }
            }
        });

        //button
        openPop = (Button) findViewById(R.id.openpop);

        //popupWindow
        initPopupWindow();
        String a = mSOSPopupWindow.isShowing() ? "true":"false";
        Utils.showToast(mContext, a ,0);
    }

    private void initPopupWindow(){
        //初始化popupWindow内的控件
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_sos_info_layout, null);
        etSosPhoneNumber = (EditText) view.findViewById(R.id.et_sos_phoneNumber);
        etSosMessage = (EditText) view.findViewById(R.id.et_sos_message);
        ivGotoChooseContacts = (ImageView) view.findViewById(R.id.iv_gotochoose_contacts);
        btSosCancel = (Button) view.findViewById(R.id.bt_sos_cancel);
        btSosOk = (Button) view.findViewById(R.id.bt_sos_ok);
        //popupWindow相关参数的初始化
        mSOSPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mSOSPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);//设置弹出窗体需要软键盘
        mSOSPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//设置popupwindow往上移以适应软键盘高度
        mSOSPopupWindow.setFocusable(true);//设置popupWindow可以获取焦点，则里面的edittext可以获取到焦点，响应输入事件。
                                             // 但是activity上的其他控件不能再获取焦点（不能响应事件），除非popupWindow消失
        mSOSPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.setWindowBackgroundAlpha(mContext, 1f);//当popupWindow关闭时，把window设置回完全透明（没有变灰）
            }
        });
        mSOSPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);//设置popupwindow弹入弹出时的动画
//        mSOSPopupWindow.setOutsideTouchable(true);//设置为true，则点击popupWindow之外的空白处，popupWindow会消失，但是，
//                                                    // popupWindow内的edittext不会获取焦点，不能响应输入事件，同时，activity上的控件依然可以响应点击事件，
//                                                    // 设置为false，点击popupWindow之外的空白处，popupWindow不会消失
    }

    @Override
    protected void initListener() {
        ivGotoChooseContacts.setOnClickListener(this);
        btSosCancel.setOnClickListener(this);
        btSosOk.setOnClickListener(this);
        openPop.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    //popupWindow各子view的点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_sos_cancel:
                mSOSPopupWindow.dismiss();
                break;
            case R.id.bt_sos_ok:
                Utils.showToast(mContext, "你保存了一个联系人号码", 0);
                String phoneNumber = etSosPhoneNumber.getText().toString();
                Log.d(TAG, phoneNumber);
                PreferenceUtils.putString(mContext, "phoneNumber", phoneNumber);
                SharedPreferences mSharePreference = PreferenceManager.getDefaultSharedPreferences(mContext);
                mSharePreference.edit().putString("phoneNumber", phoneNumber).apply();
                break;
            case R.id.iv_gotochoose_contacts:
                Intent intent = new Intent(this, SelectContactsActivity.class);
                intent.putExtra("isFromSetupSOS", true);
                startActivityForResult(intent, KEY_SOS_REQUEST_SELECTCONTACTS);
                break;
            case R.id.openpop:
                if (mSOSPopupWindow != null && !mSOSPopupWindow.isShowing()){
                    showPopupWindow();
                }else {
                    closePopupWindow();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {//把获取到的phoneNumber保存到sharepreference中
                case KEY_SOS_REQUEST_SELECTCONTACTS:
                    String number = data.getExtras().getString("number");
                    Log.d(TAG, "number " + number);
                    etSosPhoneNumber.setText(number);
                    PreferenceUtils.putString(mContext, "phoneNumber", number);
                    break;
            }
        }
    }

    /**
     * 展示popupWindow
     */
    private void showPopupWindow(){
        if (mSOSPopupWindow!=null && !mSOSPopupWindow.isShowing()){
            mSOSPopupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                    Utils.setWindowBackgroundAlpha(mContext, 0.4f);
        }
    }

    /**
     * 隐藏popupWindow
     */
    private void closePopupWindow(){
        if (mSOSPopupWindow.isShowing()){
            mSOSPopupWindow.dismiss();
        }
    }
}
