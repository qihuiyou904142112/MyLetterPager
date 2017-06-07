package com.qhy.letter.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class GsonUtils {


    private Gson mGson;

    public GsonUtils() {
        mGson = new Gson();
    }

    /**
     * 将json字符串转成对象
     *
     * @param json
     * @param cls
     * @return
     */
    public <T> T fromJSON(String json, Class<T> cls) {
        T t = null;
        try {
            t = mGson.fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static String createGsonString(Object object) {
        Gson gson = new Gson();
        String gsonString = gson.toJson(object);
        return gsonString;
    }

    public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        T t = gson.fromJson(gsonString, cls);
        return t;
    }

    public static <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

    public static <T> List<Map<String, T>> changeGsonToListMaps(
            String gsonString) {
        List<Map<String, T>> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
        }.getType());
        return list;
    }

    public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
        Map<String, T> map = null;
        Gson gson = new Gson();

        map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }


}
