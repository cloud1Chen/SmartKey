package com.itel.smartkey.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by huorong.liang on 2017/1/9.
 */

public class Utils {

    /**
     * 根据string字符串获取在资源中的id
     * @param mContext
     * @param stringName
     * @return
     */
    public static int getStringIdByString(Context mContext, String stringName){
        int stringID = mContext.getResources().getIdentifier(stringName,// string.xml内配置的名字
                "string",
                "com.itel.smartkey");
        Log.d("LHRTAG", "stringID " + stringID);
        return stringID;
    }

    /**
     * 根据id获取资源中的字符串
     * @param mContext
     * @param stringName
     * @return
     */
    public static String getStringById(Context mContext, String stringName){
        String string;
        return string = mContext.getResources().getString(getStringIdByString(mContext, stringName));
    }


    /**
     * 根据drawable 的name获取在资源中的id
     * @param mContext
     * @param drawableName
     * @return
     */
    public static int getDrawableIdByString(Context mContext, String drawableName){
        int stringID = mContext.getResources().getIdentifier(drawableName,// string.xml内配置的名字
                "mipmap",
                "com.itel.smartkey");
        return stringID;
    }

    /**
     * 根据drawable 的id获取在资源中的图片
     * @param mContext
     * @param drawableName
     * @return
     */
    public static Drawable getDrawableById(Context mContext, String drawableName){
        Drawable image = mContext.getResources().getDrawable(getDrawableIdByString(mContext, drawableName));
        return image;
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
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 显示一个toast
     */
    public static void showToast(Context context, String message, int showTime){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    };

    /**
     * 获取当前activity的window并且设置它的背景透明度
     * @param alpha 0.0-1.0，0.0代表完全不透明，1.0代表完全透明
     */
    public static void setWindowBackgroundAlpha(Context context, float alpha){
        if (!(context instanceof Activity)) {
            return;
        }
        Window window = ((Activity)context).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }

}
