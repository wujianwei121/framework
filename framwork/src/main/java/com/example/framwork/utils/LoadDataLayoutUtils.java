package com.example.framwork.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.framwork.R;
import com.example.framwork.widget.LoadDataLayout;


/**
 * Created by ${wjw} on 2016/4/15.
 */
public class LoadDataLayoutUtils {
    private LoadDataLayout loadDataLayout;
    private LoadDataLayout.OnReloadListener onReloadListener;

    public LoadDataLayoutUtils(LoadDataLayout loadDataLayout, LoadDataLayout.OnReloadListener onReloadListener) {
        this.loadDataLayout = loadDataLayout;
        this.onReloadListener = onReloadListener;
        if (onReloadListener != null) {
            loadDataLayout.setOnReloadListener(onReloadListener);
        }
    }

    /**
     * 显示内容
     */
    public void showContent() {
        if (loadDataLayout != null) {
            loadDataLayout.setStatus(LoadDataLayout.SUCCESS);
        }
    }

    public void showLoading(String s) {
        if (loadDataLayout != null) {
            loadDataLayout.setLoadingText(s);
            loadDataLayout.setStatus(LoadDataLayout.LOADING);
        }
    }


    public void showNoWifiError(String error) {
        if (loadDataLayout != null) {
            loadDataLayout.setNoNetWorkText(error);
            loadDataLayout.setReloadBtnText("");
            loadDataLayout.setStatus(LoadDataLayout.NO_NETWORK);
        }
    }


    public LoadDataLayout showEmpty(String error) {
        if (loadDataLayout != null) {
            loadDataLayout.setEmptyText(error);
            loadDataLayout.setReloadBtnText("");
            loadDataLayout.setStatus(LoadDataLayout.EMPTY);
        }
        return loadDataLayout;
    }

    public LoadDataLayout showEmpty(String error, int emptyRes) {
        if (loadDataLayout != null) {
            loadDataLayout.setEmptyText(error);
            loadDataLayout.setReloadBtnText("");
            loadDataLayout.setEmptyImage(emptyRes);
            loadDataLayout.setStatus(LoadDataLayout.EMPTY);
        }
        return loadDataLayout;
    }

    public void showError(String error) {
        if (loadDataLayout != null) {
            loadDataLayout.setErrorBtnTextColor(R.color.gray_98);
            loadDataLayout.setErrorBtnBg(R.color.transparent);
            loadDataLayout.setErrorText(error);
            loadDataLayout.setReloadBtnText("点击重试");
            loadDataLayout.setStatus(LoadDataLayout.ERROR);
        }
    }

    public void showNoLogin(String error, int btnTxtColor, int btnBg) {
        if (loadDataLayout != null) {
            loadDataLayout.setErrorText(error);
            loadDataLayout.setErrorBtnTextColor(btnTxtColor);
            loadDataLayout.setReloadBtnText("登录");
            loadDataLayout.setErrorBtnBg(btnBg);
            loadDataLayout.setStatus(LoadDataLayout.NO_Login);
        }
    }


    public LoadDataLayout getLoadDataLayout() {
        return loadDataLayout;
    }
}
