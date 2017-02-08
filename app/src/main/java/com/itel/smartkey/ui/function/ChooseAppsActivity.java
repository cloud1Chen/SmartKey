package com.itel.smartkey.ui.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.gjiazhe.wavesidebar.WaveSideBar;
import com.itel.smartkey.R;
import com.itel.smartkey.adapter.ChooseAppsAdapter;
import com.itel.smartkey.base.BaseActivity;
import com.itel.smartkey.bean.AppsBean;
import com.itel.smartkey.bean.Settings;
import com.itel.smartkey.sort.PinyinComparator;
import com.itel.smartkey.ui.phone.SelectContactsItemDecoration;
import com.itel.smartkey.ui.toolbox.FrontToolBoxActivity;
import com.itel.smartkey.utils.AppsUtils;
import com.itel.smartkey.utils.PaserNameToLetterUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.itel.smartkey.R.id.wave_side_bar;

/**
 * Created by huorong.liang on 2017/1/20.
 */

public class ChooseAppsActivity extends BaseActivity{

    public static String TAG = "ChooseAppsActivity";
    private Toolbar toolbar;

    //recvclerview
    private RecyclerView recyclerView;
    private ChooseAppsAdapter appsAdapter;
    LinearLayoutManager layoutManager;
    private List<AppsBean> mDatas = new ArrayList<>();
    private Context mContext;
    private PinyinComparator pinyinComparator;//大写字母比较器

    //slideview
    private WaveSideBar waveSideBar;

    @Override
    protected int getResultId() {
        return R.layout.activity_choose_apps;
    }

    @Override
    protected void initView() {
        mContext = this;
        pinyinComparator = new PinyinComparator();//初始化拼音比较器
        //toobar初始化
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        String toolbarTitle = mContext.getString(R.string.choose_apps);
        initToolbar(toolbar, R.mipmap.pic_back_bar_black, toolbarTitle, false, "505050", "f4f4f4");
        initRecycleView();
        initWaveSideBar();
    }

    private void initWaveSideBar() {
        waveSideBar = (WaveSideBar) findViewById(wave_side_bar);
        waveSideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                Log.d("LHRTAG", "index" + index);
                int selectPosition = 0;
                for (int i=0; i < mDatas.size(); i++){
                    if (mDatas.get(i).getSortLetter().equals(index)){
                        selectPosition = i;
                        break;
                    }
                }
                scrollPosition(selectPosition);
            }
        });
    }

    private void initRecycleView() {

        //recycleview相关的初始化
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_chooseApp);

        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);//设置垂直方向的线性布局

        recyclerView.addItemDecoration(new SelectContactsItemDecoration(mContext,//设置item的下划线
                SelectContactsItemDecoration.VERTICAL_LIST));

        appsAdapter = new ChooseAppsAdapter(mContext, mDatas);

        appsAdapter.setOnItemClickListener(new ChooseAppsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {//设置item的点击事件,AppsBean，并返回给ChooseFunctionActiity
                Intent mRQIntent = getIntent();
                AppsBean appsBean = mDatas.get(position);
                Settings settingsBean = (Settings) mRQIntent.getSerializableExtra(FrontToolBoxActivity.SETTINGS_BEAN);
                String packageName = appsBean.getPackageName();
                String activityClassName = appsBean.getClassName();
                String appsName = appsBean.getName();
                byte[] iconBytes = appsBean.getIconBytes();
                Log.d("LHRTAG", "ChooseAppsActivity appsName " + appsName);
                Log.d("LHRTAG", "ChooseAppsActivity packageName " + packageName);
                Log.d("LHRTAG", "ChooseAppsActivity activityClassName " + activityClassName);
                Log.d("LHRTAG", "ChooseAppsActivity iconBytes " + iconBytes);
                settingsBean.setData2(packageName);
                settingsBean.setData3(activityClassName);
                settingsBean.setFuncAcIconBytes(iconBytes);
                settingsBean.setFuncAcName(appsName);
                Bundle bundle = new Bundle();
                bundle.putSerializable(FrontToolBoxActivity.SETTINGS_BEAN, settingsBean);
                mRQIntent.putExtras(bundle);
                setResult(RESULT_OK, mRQIntent);
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(appsAdapter);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        initRvDatasAndSlideBarItems();
    }

    /**
     * 更新数据源
     */
    private void initRvDatasAndSlideBarItems() {
        List<AppsBean> mAppsBeanList = AppsUtils.loadApps(mContext);
        mDatas.clear();
        mDatas.addAll(mAppsBeanList);
        mDatas = (List<AppsBean>) PaserNameToLetterUtils.filledData(mDatas);
        Collections.sort(mDatas, pinyinComparator);
        Log.d("LHRTAG","mDatas.size()" + mDatas.size());
//        appsAdapter.upDates(mDatas);
        appsAdapter.notifyDataSetChanged();
        initWaveSlideBarData();
    }

    private void initWaveSlideBarData() {
        List<String> mLetters = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++){
            String temLetter = mDatas.get(i).getSortLetter();
            if (!mLetters.contains(temLetter)){
                mLetters.add(temLetter);
            }
        }
        String[] b = mLetters.toArray(new String[mLetters.size()]);
        waveSideBar.setIndexItems(b);//给侧滑菜单栏设置字符
    }

    /**
     * recyclerView根据index滑动到指定的item
     * @param index
     */
    private void scrollPosition(int index) {
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        int lastPosition = layoutManager.findLastVisibleItemPosition();
        if (index <= firstPosition) {
            recyclerView.scrollToPosition(index);
        } else if (index <= lastPosition) {
            int top = recyclerView.getChildAt(index - firstPosition).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            recyclerView.scrollToPosition(index);
        }
    }
}
