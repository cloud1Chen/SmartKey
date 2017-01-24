package com.itel.smartkey.database;

import com.itel.smartkey.bean.Function;

import java.io.InputStream;
import java.util.List;


public interface FunParser {
    /**
     * 解析输入流 得到Book对象集合
     * @param is
     * @return
     * @throws Exception
     */
    public List<Function> parse(InputStream is) throws Exception;

    /**
     * 序列化Book对象集合 得到XML形式的字符串
     * @param funs
     * @return
     * @throws Exception
     */
    public String serialize(List<Function> funs) throws Exception;
}