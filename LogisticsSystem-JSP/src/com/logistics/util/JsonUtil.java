package com.logistics.util;

import net.arnx.jsonic.JSON;

/**
 * JSON工具类
 * 使用Jsonic库进行JSON序列化和反序列化
 */
public class JsonUtil {
    
    /**
     * 将对象转换为JSON字符串
     * @param obj 要转换的对象
     * @return JSON字符串
     */
    public static String toJson(Object obj) {
        try {
            return JSON.encode(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
    
    /**
     * 将JSON字符串转换为指定类型的对象
     * @param json JSON字符串
     * @param clazz 目标类型
     * @return 转换后的对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return JSON.decode(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
