package com.itel.smartkey.application;

import android.app.Application;
import android.util.Log;

import com.itel.smartkey.MainActivity;
import com.itel.smartkey.bean.Function;
import com.itel.smartkey.bean.Settings;
import com.itel.smartkey.database.FunParser;
import com.itel.smartkey.database.SaxFunParser;
import com.itel.smartkey.service.DBService;

import java.io.InputStream;
import java.util.List;

/**
 * Created by huorong.liang on 2017/1/23.
 */

public class MyApplication extends Application {

    private DBService mDBService;
    private boolean isTableNoRecord;

    @Override
    public void onCreate() {
        super.onCreate();
        mDBService = new DBService(this);
        isTableNoRecord = mDBService.isTableNoRecord();
        initTable();
        Log.d("LHRTAG", "isTableNoRecord" + isTableNoRecord );
    }

    private void initTable() {//如果数据库中的function_table无任何记录，说明该表刚刚创建，需要从xml中导入功能列表
                               // （第一次安装应用时或者app升级（即db也升级，function_table没有任何记录））
        if (isTableNoRecord){
            initFunctionTable();//初始化function_table
            initSetTable();//初始化set_table
        }
    }

    /**
     * 初始化set_table
     */
    private void initSetTable() {//初始化set_table
        Settings doubleClickDefaultBean = new Settings();//双击默认为手电筒
        doubleClickDefaultBean.setFuncId(7);
        doubleClickDefaultBean.setFuncAcId(Integer.parseInt(MainActivity.POSITION_DOUBLECLICK));
        doubleClickDefaultBean.setData5(Function.METHOD_TYPE_OPEN_FLASHLIGHT + "");
        mDBService.addSetting(doubleClickDefaultBean);

        Settings longClickDefaultBean = new Settings();//长按默认为拍照
        longClickDefaultBean.setFuncAcId(Integer.parseInt(MainActivity.POSITION_LONGCLICK));
        longClickDefaultBean.setFuncId(2);
        mDBService.addSetting(longClickDefaultBean);

        for (int i = 3; i < 12; i++){//设置单击对应的9项，默认为空
            Settings setBean = new Settings();
            setBean.setFuncAcId(i);
            setBean.setFuncId(-1);
            mDBService.addSetting(setBean);
        }
    }

    /**
     * 初始化function_table
     */
    private void initFunctionTable() {
        try {
            FunParser parser;
            List<Function> funs;
            InputStream is = getAssets().open("fun.xml");
            parser = new SaxFunParser();  //创建SaxBookParser实例
            funs = parser.parse(is);  //解析输入流
            Log.d("jlog", "len:" + funs.size());
            for (Function f : funs) {
                Log.d("jlog", "-->>"  + f.getId() + f.getName() + " " + f.getFunction() + " " + f.getParameter() + " " + f.getFunction_type());
                mDBService.addFunction(f);
            }
        } catch (Exception e) {
            Log.e("jlog", e.getMessage());
        }
    }
}
