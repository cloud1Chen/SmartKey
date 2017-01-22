package com.itel.smartkey.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.itel.smartkey.bean.AppsBean;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huorong.liang on 2017/1/20.
 */

public class AppsUtils {


    /**
     * 获取手机上已经安装的app的信息，包括包名，启动的activity的名字，app名字
     * @param mContext
     * @return
     */
    public static ArrayList<AppsBean> loadApps(Context mContext){
        PackageManager mPm = mContext.getPackageManager();
        ArrayList<AppsBean> mApps = new ArrayList<>();
        List<ResolveInfo> appsInfo = loadAppsInfo(mContext);

        for (int i = 0; i < appsInfo.size(); i++ ){
            AppsBean bean = new AppsBean();
            bean.setPackageName(appsInfo.get(i).activityInfo.packageName);
            bean.setClassName(appsInfo.get(i).activityInfo.name);
            bean.setName((String) appsInfo.get(i).activityInfo.loadLabel(mPm));

            Drawable drawable = appsInfo.get(i).loadIcon(mPm);
            bean.setIconDraw(drawable);

            Bitmap bit = drawableToBitmap(drawable);
            bean.setIconBitmap(bit);

            byte[] bytes = getBytes(bit);
            bean.setIconBytes(bytes);

            mApps.add(bean);
        }
        return mApps;
    }

    /**
     * drawable转换为bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * bitmap转换为bytes
     * @param bitmap
     * @return
     */
    public static byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byte[] 装换为bitmap
     * @param b
     * @return
     */
    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


    public static List<ResolveInfo> loadAppsInfo(Context mContext) {
        PackageManager mPm = mContext.getPackageManager();
        ArrayList<ResolveInfo> apps;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        apps = (ArrayList<ResolveInfo>) mPm.queryIntentActivities(intent,0);
        for (int i=0; i<apps.size(); i++){
            String str = apps.get(i).activityInfo.packageName;
            Log.d("LHRTAG",str);
        }
        return apps;
    }
}
