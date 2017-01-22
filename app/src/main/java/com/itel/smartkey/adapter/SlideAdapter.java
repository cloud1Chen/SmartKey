package com.itel.smartkey.adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import com.itel.smartkey.R;

import java.util.ArrayList;

import so.orion.slidebar.GBSlideBarAdapter;


/**
 * 项目名称：GBSlideBar
 * 类描述：
 * 创建人：Edanel
 * 创建时间：16/1/14 下午5:45
 * 修改人：huorong.liang
 * 修改时间：17/1/22 下午5:28
 * 修改备注：修改了ArrayList<String> mcontent;
 */
public class SlideAdapter implements GBSlideBarAdapter {


    protected StateListDrawable[] mItems;
    protected ArrayList<String> mcontent;
    protected int[] textColor;


    public SlideAdapter(Resources resources, int[] items) {
        mcontent = new ArrayList<>();
        mcontent.add(resources.getString(R.string.slow));
        mcontent.add(resources.getString(R.string.normal));
        mcontent.add(resources.getString(R.string.fast));

        int size = items.length;
        mItems = new StateListDrawable[size];
        Drawable drawable;
        for (int i = 0; i < size; i++) {
            drawable = resources.getDrawable(items[i]);
            if (drawable instanceof StateListDrawable) {
                mItems[i] = (StateListDrawable) drawable;
            } else {
                mItems[i] = new StateListDrawable();
                mItems[i].addState(new int[] {}, drawable);
            }
        }
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public String getText(int position) {
        return mcontent.get(position);
    }

    @Override
    public StateListDrawable getItem(int position) {
        return mItems[position];
    }

    @Override
    public int getTextColor(int position) {
        return textColor[position];
    }

    public void setTextColor(int[] color){
        textColor = color;
    }
}
