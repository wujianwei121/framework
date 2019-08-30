package com.example.framwork.mvp;

import android.app.Activity;
import android.content.Entity;

import com.example.framwork.utils.SuperRecyclerViewUtils;

import java.util.HashMap;

/**
 * Created by lenovo on 2018/4/13.
 */

public class BaseRecyclePresenter extends BasePresenter {
    public SuperRecyclerViewUtils recyclerViewUtils;

    public BaseRecyclePresenter(Activity activity, Class<Entity> clazz, int type) {
        super(activity, clazz, type);
    }

    public void setRecyclerViewUtils(SuperRecyclerViewUtils recyclerViewUtils) {
        this.recyclerViewUtils = recyclerViewUtils;
    }

    public void getDataList(HashMap map) {
        if (map != null) {
            requestInfo = map;
            post3NoLogin(new SuperRecyclerViewUtils.RecyclerViewHttpCallBack(recyclerViewUtils));
        }
    }
}
