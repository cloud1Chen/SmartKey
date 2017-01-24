package com.itel.smartkey.database;

import android.util.Log;

import com.itel.smartkey.bean.Function;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;


public class SaxFunParser implements FunParser {

    @Override
    public List<Function> parse(InputStream is) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();  //取得SAXParserFactory实例
        SAXParser parser = factory.newSAXParser();                  //从factory获取SAXParser实例
        MyHandler handler = new MyHandler();                        //实例化自定义Handler
        parser.parse(is, handler);                                  //根据自定义Handler规则解析输入流
        return handler.getFuns();
    }

    @Override
    public String serialize(List<Function> funs) throws Exception {
        SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();//取得SAXTransformerFactory实例
        TransformerHandler handler = factory.newTransformerHandler();           //从factory获取TransformerHandler实例
        Transformer transformer = handler.getTransformer();                     //从handler获取Transformer实例
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");            // 设置输出采用的编码方式
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");                // 是否自动添加额外的空白
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");   // 是否忽略XML声明

        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        handler.setResult(result);

        String uri = "";    //代表命名空间的URI 当URI无值时 须置为空字符串
        String localName = "";  //命名空间的本地名称(不包含前缀) 当没有进行命名空间处理时 须置为空字符串

        handler.startDocument();
        handler.startElement(uri, localName, "funs", null);

        AttributesImpl attrs = new AttributesImpl();    //负责存放元素的属性信息
        char[] ch = null;
        for (Function fun : funs) {
            attrs.clear();  //清空属性列表
            attrs.addAttribute(uri, localName, "id", "string", String.valueOf(fun.getId()));//添加一个名为id的属性(type影响不大,这里设为string)
            handler.startElement(uri, localName, "fun", attrs);    //开始一个book元素 关联上面设定的id属性

            handler.startElement(uri, localName, "name", null); //开始一个name元素 没有属性
            ch = String.valueOf(fun.getName()).toCharArray();
            handler.characters(ch, 0, ch.length);   //设置name元素的文本节点
            handler.endElement(uri, localName, "name");


            handler.endElement(uri, localName, "fun");
        }
        handler.endElement(uri, localName, "funs");
        handler.endDocument();

        return writer.toString();
    }

    //需要重写DefaultHandler的方法
    private class MyHandler extends DefaultHandler {

        private List<Function> funs;
        private Function fun;
        private StringBuilder builder;

        //返回解析后得到的对象集合
        public List<Function> getFuns() {
            return funs;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            funs = new ArrayList<Function>();
            builder = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (localName.equals("fun")) {
                fun = new Function();
            }
            builder.setLength(0);   //将字符长度设置为0 以便重新开始读取元素内的字符节点
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            builder.append(ch, start, length);  //将读取的字符数组追加到builder中
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            //id name icon function function_type parameter single_click_enable double_click_enable long_click_enable close_enable lock_enable
            if (localName.equals("id")) {
//                Log.d("jlog", "id:" + builder.toString());
                fun.setId(Integer.parseInt(builder.toString()));
            } else if (localName.equals("name")) {
//                Log.d("jlog", "name:" + builder.toString());
                fun.setName(builder.toString());
            } else if (localName.equals("icon")) {
//                Log.d("jlog", "icon:" + builder.toString());
                fun.setIcon(builder.toString());
            } else if (localName.equals("function")) {
//                Log.d("jlog", "function:" + builder.toString());
                fun.setFunction(builder.toString());
            } else if (localName.equals("function_type")) {
//                Log.d("jlog", "function_type:" + builder.toString());
                fun.setFunction_type(Integer.parseInt(builder.toString()));
            } else if (localName.equals("function_app_type")) {//add lhr 2017/1/23
                                Log.d("jlog", "function_app_type:" + builder.toString());
                fun.setFunction_app_type(Integer.parseInt(builder.toString()));
            } else if (localName.equals("parameter")) {
//                Log.d("jlog", "parameter:" + builder.toString());
                fun.setParameter(builder.toString());
            } else if (localName.equals("single_click_enable")) {
//                Log.d("jlog", "single_click_enable:" + builder.toString());
                fun.setSingleClick(Integer.parseInt(builder.toString()) == 1);
            } else if (localName.equals("double_click_enable")) {
//                Log.d("jlog", "double_click_enable:" + builder.toString());
                fun.setDoubleClick(Integer.parseInt(builder.toString()) == 1);
            } else if (localName.equals("long_click_enable")) {
//                Log.d("jlog", "long_click_enable:" + builder.toString());
                fun.setLongClick(Integer.parseInt(builder.toString()) == 1);
            } else if (localName.equals("close_enable")) {
//                Log.d("jlog", "close_enable:" + builder.toString());
                fun.setCloseEnable(Integer.parseInt(builder.toString()) == 1);
            } else if (localName.equals("lock_enable")) {
//                Log.d("jlog", "lock_enable:" + builder.toString());
                fun.setLockEnable(Integer.parseInt(builder.toString()) == 1);
            } else if (localName.equals("parameter_extra")){//add lhr 2017/1/23
                fun.setParameter_extra(builder.toString());
            }else if (localName.equals("fun")) {
                funs.add(fun);
            }
        }
    }
}