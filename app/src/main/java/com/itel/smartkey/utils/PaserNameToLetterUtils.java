package com.itel.smartkey.utils;

import android.util.Log;

import com.itel.smartkey.bean.ShortModel;
import com.itel.smartkey.sort.CharacterParser;

import java.util.List;

/**
 * 把 name 的拼音转换成大写字母 "A--Z" 的工具类，实体类bean需要继承于ShortModel
 * Created by huorong.liang on 2017/1/22.
 */

public class PaserNameToLetterUtils {
    /**
     * 把 name 的拼音转换成大写字母 "A--Z" 的方法，
     * 实体类bean需要继承于ShortModel
     * @param beanList 需转换的beanList
     * @return 完成转换的beanList（给bean的sortLetter赋值）
     */
    public static List<? extends ShortModel> filledData(List<? extends ShortModel> beanList) {
        CharacterParser characterParser = CharacterParser.getInstance();
        for (int i = 0; i < beanList.size(); i++) {
            ShortModel sortModel = beanList.get(i);
            Log.d("LHRTAG","beanList.size" + beanList.size());
            Log.d("LHRTAG","sortModel.getName()" + sortModel.getName());
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(sortModel.getName());//获取汉字对应的拼音
            String sortString = pinyin.substring(0, 1).toUpperCase();//将英文字符转换为大写字母
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetter(sortString.toUpperCase());
            } else {
                sortModel.setSortLetter("#");
            }
        }
        return beanList;
    }
}
