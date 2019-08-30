package com.example.framwork.noHttp;


import com.example.framwork.noHttp.Bean.BaseResponseBean;

/**
 * 返回base类型
 */
public interface OnRequestListener<T> {
    void requestSuccess(T bean);

    void requestFailed(T bean, int errorCode, Exception exception, String error);

    //用于hide loading
    void requestFinish();


}
