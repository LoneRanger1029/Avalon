package com.lyh.avalontools.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * JSON 格式化工具类
 */
public class JsonFormatter {
    /**
     * 格式化JSON字符串
     * @param json 输入的JSON字符串
     * @return 格式化后的JSON字符串
     */
    public static String formatJson(String json) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Object jsonObj = gson.fromJson(json, Object.class);
            return gson.toJson(jsonObj);
        } catch (JsonSyntaxException e) {
            return "Invalid JSON: " + e.getMessage();
        }
    }
}
