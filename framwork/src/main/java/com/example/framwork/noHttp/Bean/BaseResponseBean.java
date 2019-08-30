package com.example.framwork.noHttp.Bean;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseResponseBean {

    public abstract int getCode();

    public abstract String getData();

    public abstract String getMessage();

    public abstract boolean isSuccess();


    /**
     * 解析JsonObject，你的{@link E}必须提供默认无参构造
     *
     * @param clazz 要解析的实体类的class
     * @param <E>   实体类泛型
     * @return 实体类
     */
    public <E> E parseObject(Class<E> clazz) {
        E e = null;
        try {
            e = JSON.parseObject(getData(), clazz);
        } catch (Exception e1) {
            e1.printStackTrace();
            // 服务端数据格式错误时，返回data的空构造
            try {
                e = clazz.newInstance();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return e;
    }

    /**
     * 解析JsonArray，你的{@link E}必须提供默认无参构造
     *
     * @param clazz 要解析的实体类的class
     * @param <E>   实体类泛型
     * @return 实体类的List集合
     */
    public <E> List<E> parseList(Class<E> clazz) {
        List<E> e = new ArrayList<>();
        try {
            e = JSON.parseArray(getData(), clazz);
        } catch (Exception e1) {
        }
        return e;
    }

    /**
     * 解析JsonObject，你的{@link E}必须提供默认无参构造
     *
     * @param clazz 要解析的实体类的class
     * @param <E>   实体类泛型
     * @return 实体类
     */
    public static <E> E parseObj(String data, Class<E> clazz) {
        E e = null;
        try {
            e = JSON.parseObject(data, clazz);
        } catch (Exception e1) {
            e1.printStackTrace();
            // 服务端数据格式错误时，返回data的空构造
            try {
                e = clazz.newInstance();
            } catch (Exception e2) {
            }
        }
        return e;
    }

    /**
     * 解析JsonArray，封装为捕获异常
     *
     * @param clazz 要解析的实体类的class
     * @param <E>   实体类泛型
     * @return 实体类的List集合
     */
    public static <E> List<E> parseArray(String data, Class<E> clazz) {
        List<E> e = new ArrayList<>();
        try {
            e = JSON.parseArray(data, clazz);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return e;
    }
}
