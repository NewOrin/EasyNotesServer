package com.neworin.easynotes.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.neworin.easynotes.model.NoteBook;

import java.util.ArrayList;
import java.util.List;

public class GsonUtil<T> {

    public static Gson getGson() {
        return new Gson();
    }

    /**
     * json数组字符串转换成List对象数组
     *
     * @param parmas
     * @param clazz
     * @return
     */
    public List<T> parseArrayStr(String parmas, Class<T> clazz) {
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(parmas).getAsJsonArray();
        Gson gson = getGson();
        List<T> list = new ArrayList<T>();
        for (JsonElement je : jsonArray) {
            list.add(gson.fromJson(je, clazz));
        }
        return list;
    }
}
