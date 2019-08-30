/*
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.framwork.noHttp;

import android.content.Context;

import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONObject;

/**
 * Created in Mar 6, 2016 9:01:42 PM.
 *
 * @author wuajiwnei;
 */
public class ResponseListener<T> implements OnResponseListener<String> {


    private HttpCallBack<T> callBack;

    public ResponseListener(HttpCallBack<T> callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onStart(int what) {
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        if (callBack != null) {
            callBack.onFinish();
            if (response.getHeaders().getResponseCode() == 200) {
                callBack.onSucceed(what, response.get());
            } else {
                callBack.onFailed(response.getHeaders().getResponseCode(), new UnKnownHostError(), "服务器出现异常，请稍后重试");
            }
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        Exception exception = response.getException();
        String message;
        if (exception instanceof NetworkError) {// 网络不好
            message = "当前网络不稳定";
        } else if (exception instanceof TimeoutError) {// 请求超时
            message = "请求超时，请稍后重试";
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            message = "服务器出现异常，请稍后重试";
        } else if (exception instanceof URLError) {// URL是错的
            message = "URL错误";
        } else if (exception instanceof NotFoundCacheError) {
            message = "没有发现缓存";
        } else {
            message = "数据请求失败";
        }
        if (callBack != null) {
            callBack.onFinish();
            callBack.onFailed(what, exception, message);
        }
    }


    @Override
    public void onFinish(int what) {
    }

}
