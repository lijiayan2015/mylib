package com.ljy.okhttputils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * json解析类
 */
public class JsonUtils {
    private static Gson gson = new Gson();

    /**
     * 将对象转化成json数据
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String serialize(T obj) {
        return gson.toJson(obj);
    }


    /**
     * 将json字符串转化成对象
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T deSerilize(String json, Class<T> cls) throws JsonSyntaxException {
        return gson.fromJson(json, cls);
    }

    /**
     * 将json对象转化成实体类
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T deSerilizes(JsonObject json, Class<T> cls) throws JsonSyntaxException {
        return gson.fromJson(json, cls);
    }


    /**
     * 将json字符串转化成对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T deSerilize(String json, Type type) throws JsonSyntaxException {
        return gson.fromJson(json, type);
    }


    /**
     * 将json数组转化成对象集合
     *
     * @param jsonAray
     * @param cls
     * @param <T>
     * @return
     * @throws JSONException
     */
    public static <T> List<T> deSerilizes(String jsonAray, Class<T> cls) throws JSONException {
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(jsonAray).getAsJsonArray();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            list.add(deSerilizes(array.get(i).getAsJsonObject(), cls));
        }
        return list;
    }

    /**
     * 读取文本数据
     *
     * @param context  程序上下文
     * @param fileName 文件名
     * @return String, 读取到的文本内容，失败返回null
     */
    public static String readAssets(Context context, String fileName) {
        InputStream is = null;
        String content = null;
        try {
            is = context.getAssets().open(fileName);
            if (is != null) {

                byte[] buffer = new byte[1024];
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int readLength = is.read(buffer);
                    if (readLength == -1) break;
                    arrayOutputStream.write(buffer, 0, readLength);
                }
                is.close();
                arrayOutputStream.close();
                content = new String(arrayOutputStream.toByteArray());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            content = null;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return content;
    }
}
