package com.example.framwork.adapter.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.framwork.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * ListView 通用的adapter
 *
 * @author hongyang
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int curPos = -1;
    private int selectPosition = -1;

    public CommonAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = new ArrayList<>();
    }

    public CommonAdapter(Context context, List<T> datas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        if (mDatas != null && mDatas.size() != 0) {
            return mDatas.size();
        } else {
            return 0;
        }
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int curPos) {
        this.curPos = curPos;
        notifyDataSetChanged();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = BaseViewHolder.get(mContext, convertView, parent,
                getItemViewLayoutId(position, getItem(position)), position);
        convert(holder, getItem(position), position);
        return holder.getConvertView();
    }

    public abstract void convert(BaseViewHolder holder, T t, int p);

    protected abstract int getItemViewLayoutId(int position, T item);

    public void addAll(List<T> objs) {
        if (objs != null && objs.size() != 0) {
            mDatas.addAll(objs);
            notifyDataSetChanged();
        }
    }

    public void remove(int index) {
        mDatas.remove(index);
        notifyDataSetChanged();
    }

    public void addNewAll(List<T> objs) {
        if (objs != null && objs.size() != 0) {
            mDatas.clear();
            mDatas.addAll(objs);
            notifyDataSetChanged();
        }
    }

    public void add(T objs) {
        mDatas.add(objs);
        notifyDataSetChanged();
    }

    public void clear() {
        if (mDatas != null) {
            mDatas.clear();
        } else {
            mDatas = new ArrayList<>();
        }
    }
}
