/*
    ShengDao Android Client, JsonMananger
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.konglianyuyin.mx.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.TypeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;


/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/4/21
 * 描述: [JSON解析管理类]
 * 修订历史:
 */
public class JsonMananger {

    static {
        TypeUtils.compatibleWithJavaBean = true;
    }

    private static final String tag = JsonMananger.class.getSimpleName();

    /**
     * 将json字符串转换成java对象
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将json字符串转换成java List对象
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        List<T> listT = null;
        try {
            listT = JSON.parseArray(json, cls);
        } catch (JSONException exception) {

        }
        return listT;
    }

    /**
     * 将bean对象转化成json字符串
     *
     * @param obj
     * @return
     */
    public static String beanToJson(Object obj)  {
        String result = JSON.toJSONString(obj);
        Log.e(tag, "beanToJson: " + result);
        return result;
    }

    /**
     * 将map对象转化成json字符串
     *
     * @param obj
     * @return
     */
    public static String mapToJson(Map obj) {
        String result = JSON.toJSONString(obj);
        Log.e(tag, "mapToJson: " + result);

        return result;
    }

    public static <T> T getReponseResult(String resultStr, Class<T> cls) {
        T t = null;
        try {
            t = JsonMananger.jsonToBean(resultStr, cls);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return t;
    }


    /**
     * 将json转化成map
     *
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> JsonStrToMap(String jsonStr) {
        Map<String, Object> map = JSON.parseObject(
                jsonStr, new TypeReference<Map<String, Object>>() {
                });

        return map;
    }

    public static String formatJson(final String json) {
        try {
            for (int i = 0, len = json.length(); i < len; i++) {
                char c = json.charAt(i);
                if (c == '{') {
                    return new JSONObject(json).toString(4);
                } else if (c == '[') {
                    return new JSONArray(json).toString(4);
                } else if (!Character.isWhitespace(c)) {
                    return json;
                }
            }
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
