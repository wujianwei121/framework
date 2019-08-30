package com.example.framwork.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by super南仔 on 07/28/16.
 * blog: http://supercwn.github.io/
 * GitHub: https://github.com/supercwn
 */
public abstract class SuperBaseAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    protected int curPos = -1;

    public SuperBaseAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
        } else {
            mData = new ArrayList<>();
        }
    }

    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int curPos) {
        this.curPos = curPos;
        notifyDataSetChanged();
    }
}
