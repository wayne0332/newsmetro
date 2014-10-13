package com.newsmetro.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by lifeng.gao on 2014/10/13.
 */
public class GsonUtil {
    private static final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();

    private static final Gson gson = builder.create();

    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json,Class<T> clazz){
        return gson.fromJson(json,clazz);
    }
}
