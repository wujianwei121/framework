package com.example.framwork.utils;

import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lenovo on 2017/1/17.
 */

public class SharedPreferencesCompat {
    public final Method sApplyMethod = findApplyMethod();
    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     * @author xuzhaohu
     */
    private static class SingletonHolder {
        private static SharedPreferencesCompat instance = new SharedPreferencesCompat();
    }

    /**
     * 私有的构造函数
     */
    private SharedPreferencesCompat() {

    }

    public static SharedPreferencesCompat getInstance() {
        return SharedPreferencesCompat.SingletonHolder.instance;
    }
    /**
     * 反射查找apply的方法
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Method findApplyMethod() {
        try {
            Class clz = SharedPreferences.Editor.class;
            return clz.getMethod("apply");
        } catch (NoSuchMethodException e) {
        }

        return null;
    }

    /**
     * 如果找到则使用apply执行，否则使用commit
     *
     * @param editor
     */
    public void apply(SharedPreferences.Editor editor) {
        try {
            if (sApplyMethod != null) {
                sApplyMethod.invoke(editor);
                return;
            }
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        editor.commit();
    }
}
