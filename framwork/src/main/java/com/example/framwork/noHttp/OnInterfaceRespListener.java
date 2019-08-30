package com.example.framwork.noHttp;


/**
 * Created by lenovo on 2017/9/1.
 */

public abstract class OnInterfaceRespListener<T> implements OnRequestListener<T> {

    @Override
    public void requestFinish() {

    }

    @Override
    public void requestFailed(T bean, int errorCode, Exception exception, String error) {

    }
}
