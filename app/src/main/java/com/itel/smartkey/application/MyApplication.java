package com.itel.smartkey.application;

import android.app.Application;
import android.util.Log;

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
        for (int i = 0; i < 9; i++){//设置单击对应的9项，默认为空
            Settings setBean = new Settings();
            setBean.setFuncAcId(i);
            setBean.setFuncId(-1);
            mDBService.addSetting(setBean);
        }
        Settings doubleClickDefaultBean = new Settings();//双击默认为拍照
        doubleClickDefaultBean.setFuncId(2);
        doubleClickDefaultBean.setFuncAcId(9);
        mDBService.addSetting(doubleClickDefaultBean);

        Settings longClickDefaultBean = new Settings();
        longClickDefaultBean.setFuncAcId(10);
        longClickDefaultBean.setFuncId(5);
        longClickDefaultBean.setData5("5");
        mDBService.addSetting(longClickDefaultBean);
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
