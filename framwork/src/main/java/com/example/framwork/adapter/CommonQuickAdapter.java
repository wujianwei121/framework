package com.example.framwork.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.framwork.utils.DLog;

import java.util.List;

public abstract class CommonQuickAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public int curPos = -1;
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public CommonQuickAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void addNewData(List<T> data) {
        getData().clear();
        addData(data);
    }

    public void setCurPos(int curPos) {
        this.curPos = curPos;
        notifyDataSetChanged();
    }

    @Override
    public void setOnItemClick(View v, int position) {
         if (position == curPos && isFastClick()) {
            return;
        }
        super.setOnItemClick(v, position);
    }
}
