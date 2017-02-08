package com.itel.smartkey.ui.phone;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.itel.smartkey.R;


public class CallActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_REQUEST_CALLPHONE = 1;
    private static final int REQUEST_CODE_OPEN_PERMISSION_SETTING = 1;
    private String number = null;
    private Intent intent;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("LHRTAG", "-----------call");
        intent= getIntent();
        //获取传递进来的"call:// + _xxxx"
        uri = intent.getData();
        String numberStr = uri.toString();

        Log.d("LHRTAG", "uri " + numberStr);
        //把 "call:// + _xxxx" 拆分成 "call://" 和 "xxxx"" 并把xxxx赋值给number
        String[] strings = TextUtils.split(numberStr, "_");
        if (strings.length > 1){
            number = strings[1];
        }
        Log.d("LHRTAG", "call:" + number);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_REQUEST_CALLPHONE);
        }else{
            callPhone();
        }
    }
    //拨打电话
    private void callPhone() {
        Intent in = new Intent(Intent.ACTION_CALL);
        in.setData(Uri.parse("tel:" + number));
        startActivity(in);
        finish();
    }

    //申请权限是否成功的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_REQUEST_CALLPHONE) {
            if (permissions[0].equals(Manifest.permission.CALL_PHONE) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {//如果申请权限成功，则申请联系人数据并且更新rv的数据源
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                callPhone();
            } else {//如果用户选择不授予权限
                boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE);
                if (!showRationale) {//如果用户勾选了不再询问或者在系统设置中设置了关闭权限，则弹出对话框并指引用户到系统设置中进行权限设置，
                    // 如果用户还是不允许权限，则关闭该界面
                    showRationalePermissionDialog();

                    // user also CHECKED "never ask again"
                    // you can either enable some fall back,
                    // disable features of your app
                    // or open another dialog explaining
                    // again the permission and directing to
                    // the app setting
                } else {//如果拒绝授予权限，并且未勾选“不再询问按钮”和未在系统设置中禁止权限，
                    // 则弹出对话框并说明为什么需要权限，并给用户选择是否重新申请权限，
                    //如果用户不重新申请，则关闭该界面
                    showRejectPermissionDialog();
                }
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 展示用户点击不再询问后弹出的对话框
     */
    private void showRationalePermissionDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(CallActivity.this.getString(R.string.access_the_dialog_permission_content))
                .setNegativeButton(CallActivity.this.getString(R.string.access_the_dialog_permission_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setPositiveButton(CallActivity.this.getString(R.string.access_the_dialog_permission_settings), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_CODE_OPEN_PERMISSION_SETTING);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("LHRTAG", "dialog onCancel ");
                CallActivity.this.finish();
            }
        });
    }

    /**
     * 展示用户拒绝授予权限时弹出的对话框
     */
    private void showRejectPermissionDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(CallActivity.this.getString(R.string.access_the_dialog_permission_content))
                .setPositiveButton(CallActivity.this.getString(R.string.access_the_dialog_permission_settings), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(CallActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_REQUEST_CALLPHONE);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(CallActivity.this.getString(R.string.access_the_dialog_permission_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("LHRTAG", "dialog onCancel ");
                CallActivity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_PERMISSION_SETTING) {
            Log.d("LHRTAG", "SelectContactsActivity set perssions result");
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE_REQUEST_CALLPHONE);
            } else {
                Log.d("LHRTAG", "已经授予权限");
                callPhone();
            }
        }
    }

}
