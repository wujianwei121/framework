package com.example.framwork.mvp;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.framwork.base.QuickActivity;
import com.example.framwork.baseapp.BaseAppConfig;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.CallServer;
import com.example.framwork.noHttp.FastJsonRequest;
import com.example.framwork.noHttp.HttpCallBack;
import com.example.framwork.noHttp.NetworkConfig;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.DLog;
import com.example.framwork.utils.EncryptUtil;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.StringRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


/**
 * Created by wanjingyu on 2016/10/8.
 */

public class CustomRequest<Entity> {
    public static final String TAG = "Network request";
    public static final int LIST = 0;
    public static final int ENTITY = 1;
    private static NetworkConfig sConfig;
    private String methodName;
    private Class<Entity> clazz;
    private int type;

    public CustomRequest(Class<Entity> clazz, int type) {
        this.clazz = clazz;
        this.type = type;
    }

    public static void setConfig(NetworkConfig config) {
        if (sConfig == null) {
            synchronized (NetworkConfig.class) {
                if (sConfig == null)
                    sConfig = config == null ? NetworkConfig.newBuilder().build() : config;
                else Log.w("Kalle", new IllegalStateException("Only allowed to configure once."));
            }
        }
    }

    public static NetworkConfig getConfig() {
        setConfig(null);
        return sConfig;
    }

    public void resultCustomurl(Context context, String url,HashMap info, boolean isLogin, boolean isShowLoading, boolean isShowToast, String loadingText, OnRequestListener requestListener) {
        this.methodName = info.get("methodName").toString();
        info.remove("methodName");
        Request<String> request;
        String jsonString = JSON.toJSONString(info);
        if (getConfig().isEncryption()) {
            request = new FastJsonRequest(url,
                    RequestMethod.POST, jsonString);

        } else {
            request = new StringRequest(url,
                    RequestMethod.POST);
            request.add(info);
        }
        DLog.d(TAG, new StringBuilder().append("post入参：").append(url).append(methodName).append(jsonString).toString());
        execute(context, request, isLogin, isShowLoading, isShowToast, loadingText, requestListener);
    }

    /*
     * 一般post请求是调用*/
    public void resultPostJsonModel(Context context, HashMap info, boolean isLogin, boolean isShowLoading, boolean isShowToast, String loadingText, OnRequestListener requestListener) {
        this.methodName = info.get("methodName").toString();
        info.remove("methodName");
        Request<String> request;
        String jsonString = JSON.toJSONString(info);
        if (getConfig().isEncryption()) {
            request = new FastJsonRequest(new StringBuilder().append(BaseAppConfig.SERVICE_PATH).append(methodName).toString(),
                    RequestMethod.POST, jsonString);

        } else {
            request = new StringRequest(new StringBuilder().append(BaseAppConfig.SERVICE_PATH).append(methodName).toString(),
                    RequestMethod.POST);
            request.add(info);
        }
        DLog.d(TAG, new StringBuilder().append("post入参：").append(BaseAppConfig.SERVICE_PATH).append(methodName).append(jsonString).toString());
        execute(context, request, isLogin, isShowLoading, isShowToast, loadingText, requestListener);
    }


    public void resultPostMoreImageModel(Context context, HashMap<String, String> imgInfo, HashMap info, boolean isLogin, boolean isShowLoading, boolean isShowToast, String loadingText, OnRequestListener requestListener) {
        this.methodName = info.get("methodName").toString();
        info.remove("methodName");
        Request<String> request;
        String jsonString = JSON.toJSONString(info);
        if (getConfig().isEncryption()) {
            request = new FastJsonRequest(new StringBuilder().append(BaseAppConfig.SERVICE_PATH).append(methodName).toString(),
                    RequestMethod.POST, jsonString);
        } else {
            request = new StringRequest(new StringBuilder().append(BaseAppConfig.SERVICE_PATH).append(methodName).toString(),
                    RequestMethod.POST);
            request.add(info);
        } 
		  if (imgInfo.size() != 0)
            for (Map.Entry<String, String> entry : imgInfo.entrySet()) {
                DLog.d(TAG, new StringBuilder().append("需要上传图片信息：").append(entry.getKey()).append(":").append(entry.getValue()).toString());
                if (entry.getValue() != null && !TextUtils.isEmpty(entry.getValue()) && !entry.getValue().startsWith("http")) {
                    request.add(entry.getKey(), new FileBinary(new File(entry.getValue())));
                }
            }
        DLog.d(TAG, new StringBuilder().append("post入参：").append(BaseAppConfig.SERVICE_PATH).append(methodName).append(jsonString).toString());
        execute(context, request, isLogin, isShowLoading, isShowToast, loadingText, requestListener);
    }

    public void execute(Context context, Request request, boolean isLogin, boolean isShowLoading, boolean isShowToast, String loadingText, OnRequestListener requestListener) {
        if (context != null && context instanceof QuickActivity) {
            if (isShowLoading) {
                QuickActivity b = (QuickActivity) context;
                if (TextUtils.isEmpty(loadingText))
                    b.showProgress();
                else b.showProgress(loadingText);
            }
        }
        addRequest(context, isLogin, isShowLoading, isShowToast, request, requestListener);
    }

    public void addRequest(final Context context, final boolean isLogin, final boolean isShowLoading, final boolean isShowToast, final Request request, final OnRequestListener requestListener) {
        CallServer.getRequestInstance().add(context, request, new HttpCallBack<String>() {
            @Override
            public void onSucceed(int what, String response) {
                DLog.d(TAG, new StringBuilder().append(methodName).append("原始出参：").append(response).toString());
                if (!TextUtils.isEmpty(response.trim())) {
                    BaseResponseBean bean;
                    if (getConfig().isEncryption()) {
                        String responseS = EncryptUtil.getInstance().decodeValue(response);
                        DLog.d(TAG, new StringBuilder().append(methodName).append("解密出参：").append(responseS).toString());
                    }
                    try {
                        bean = (BaseResponseBean) BaseResponseBean.parseObj(response, getConfig().getReponseC());
                        if (bean.isSuccess()) {
                            if (clazz == null) {
                                requestListener.requestSuccess(bean);
                            } else {
                                if (type == ENTITY) {
                                    requestListener.requestSuccess(bean.parseObject(clazz));
                                } else if (type == LIST) {
                                    requestListener.requestSuccess(bean.parseList(clazz));
                                }
                            }
                        } else {
                            //如果过滤错误信息   就不调用failed方法
                            if (getConfig().getFilter() != null && !getConfig().getFilter().filterOperation(context, isLogin, bean.getCode())) {
                                if (isShowToast) {
                                    Toasty.error(context, bean.getMessage() == null ? "获取数据失败" : bean.getMessage()).show();
                                }
                                requestListener.requestFailed(bean, bean.getCode(), null, bean.getMessage() == null ? "获取数据失败" : bean.getMessage());
                            }
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        onFailed(1, null, "解析数据异常");
                    }
                }
            }

            @Override
            public void onFailed(int what, Exception exception, String error) {
                if (isShowToast) {
                    Toasty.error(context, error).show();
                }
                requestListener.requestFailed(null, what, exception, error);

            }

            @Override
            public void onFinish() {
                if (context != null && context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (activity.isFinishing())
                        return;
                }
                if (context != null && context instanceof QuickActivity && isShowLoading) {
                    QuickActivity b = (QuickActivity) context;
                    b.hideProgress();
                }
                requestListener.requestFinish();
            }


        }, 0);
    }
}
