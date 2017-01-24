package com.itel.smartkey.utils;

//**

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class Flashlight {
    @RequiresApi(api = Build.VERSION_CODES.M)

    static boolean isFlashLight = false;

    public static boolean isLighting(){
        return isFlashLight;
    }

    public static boolean openOrCloseFlashLight(Context context, boolean isOpen){
        String[] cameraIdLists;
        ArrayList<String> cameraIdListsArr = null;
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                cameraIdLists = cameraManager.getCameraIdList();
                cameraIdListsArr = new ArrayList<>(Arrays.asList(cameraIdLists));
                for(String id : cameraIdLists){
                    Log.d("LHRTAG", id);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        if(isFlashLight == true)
        {
            isFlashLight = false;
            isOpen = false;
        }
        else
        {
            isFlashLight = true;
            isOpen = true;
        }
        try {
            if (isMarshMallow() && cameraIdListsArr.contains("0")) {
                cameraManager.setTorchMode("0", isOpen);//后置闪光灯
//                cameraManager.setTorchMode("1", isOpen);//前置闪光灯
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isMarshMallow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        }
        return false;
    }



}
