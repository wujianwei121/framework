package com.example.framwork.mvp;

import android.content.Context;

import com.example.framwork.noHttp.OnRequestListener;

import java.util.HashMap;
import java.util.List;


/**
 * Created by wanjingyu on 2016/10/8.
 */

public abstract class BasePresenter<Entity> {
    public static final int LIST = 0;
    public static final int ENTITY = 1;
    protected CustomRequest request;
    protected Context context;
    protected HashMap requestInfo;

    public BasePresenter(Context context, Class<Entity> clazz, int type) {
        request = new CustomRequest(clazz, type);
        this.context = context;
    }

    public BasePresenter(Context context) {
        request = new CustomRequest(null, 0);
        this.context = context;
    }

    public void post(OnRequestListener requestListener) {
        request.resultPostJsonModel(context, requestInfo, true, true, true, "", requestListener);
    }

    public void postNoToast(String loadtxt, OnRequestListener requestListener) {
        request.resultPostJsonModel(context, requestInfo, true, true, false, loadtxt, requestListener);
    }

    public void post(String loadtxt, OnRequestListener requestListener) {
        request.resultPostJsonModel(context, requestInfo, true, true, true, loadtxt, requestListener);
    }

    public void post(boolean isLoading, OnRequestListener requestListener) {
        request.resultPostJsonModel(context, requestInfo, true, isLoading, isLoading, "", requestListener);
    }

    public void post(boolean isLoading, String txt, OnRequestListener requestListener) {
        request.resultPostJsonModel(context, requestInfo, true, isLoading, isLoading, txt, requestListener);
    }

    public void postNoLoad(OnRequestListener requestListener) {
        request.resultPostJsonModel(context, requestInfo, true, false, true, "", requestListener);
    }

    public void postNoLoginNoLoading(OnRequestListener requestListener) {
        request.resultPostJsonModel(context, requestInfo, false, false, true, "", requestListener);
    }

    public void postNoLogin(String loadTxt, OnRequestListener requestListener) {
        request.resultPostJsonModel(context, requestInfo, false, true, true, loadTxt, requestListener);
    }

    public void post3NoLogin(OnRequestListener requestListener) {
        request.resultPostJsonModel(context, requestInfo, false, false, false, "", requestListener);
    }

    public void postLoadTxt(String loadingText, OnRequestListener requestListener) {
        request.resultPostJsonModel(context, requestInfo, true, true, true, loadingText, requestListener);
    }


    public void postImage(HashMap imgMap, boolean isLogin, boolean isShowLoading, boolean isShowToast, String loadTxt, OnRequestListener requestListener) {
        request.resultPostMoreImageModel(context, imgMap, requestInfo, isLogin, isShowLoading, isShowToast, loadTxt, requestListener);
    }

    public void postCutomUrl(String url, OnRequestListener requestListener) {
        request.resultCustomurl(context, url, requestInfo, true, true, true, "", requestListener);
    }
}
