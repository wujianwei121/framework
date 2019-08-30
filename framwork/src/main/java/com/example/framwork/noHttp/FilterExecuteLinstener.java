package com.example.framwork.noHttp;

import android.content.Context;

import java.util.List;

public interface FilterExecuteLinstener {
    //根据code分别处理
    boolean filterOperation(Context context, boolean isLogin, int code);

    //用于SuperRecyclerViewUtils或其他页面登录过期处理
    boolean logoutOfDate(int code);//用于SuperRecyclerViewUtils或其他页面登录过期处理
}