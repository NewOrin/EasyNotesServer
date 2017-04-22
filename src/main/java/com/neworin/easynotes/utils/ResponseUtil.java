package com.neworin.easynotes.utils;

import com.google.gson.Gson;
import com.neworin.easynotes.http.Response;

/**
 * Created by NewOrin Zhang on 2017/4/22.
 * Project Name:EasyNotesServer
 */
public class ResponseUtil {

    private static Gson gson = new Gson();

    private static Response getResponse() {
        return new Response();
    }

    public static String parseSuccessRespJson(Object o) {
        return gson.toJson(getResponse().success(o));
    }

    public static String parseSuccessRespJson() {
        return gson.toJson(getResponse().success());
    }

    public static String parseFailedRespJson(String message) {
        return gson.toJson(getResponse().failure(message));
    }

    public static String parseFailedRespJson() {
        return gson.toJson(getResponse().failure());
    }
}
