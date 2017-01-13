package com.itel.smartkey.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by huorong.liang on 2017/1/9.
 */

public class Utils {
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
