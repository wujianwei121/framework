package com.example.framwork.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.framwork.adapter.listview.CommonAdapter;
import com.example.framwork.glide.ImageLoaderUtils;

/**
 * Created by super南仔 on 07/28/16.
 * blog: http://supercwn.github.io/
 * GitHub: https://github.com/supercwn
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;
    private Context mContext;
    private int mLayoutId;
    private int mPosition;
    private View mConvertView;

    public BaseViewHolder(View itemView, Context context) {
        super(itemView);
        mViews = new SparseArray<>();
        mContext = context;
    }

    public BaseViewHolder(Context context, View itemView, ViewGroup parent, int position) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mPosition = position;
        mViews = new SparseArray<View>();
        mConvertView.setTag(this);
    }

    public static BaseViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            BaseViewHolder holder = new BaseViewHolder(context, itemView, parent, position);
            holder.mLayoutId = layoutId;
            return holder;
        } else {
            BaseViewHolder holder = (BaseViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public View getConvertView() {
        return mConvertView;
    }


    public <TView extends View> TView getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return (TView) view;
    }

    /**
     * 自定义错误照片
     */
    public BaseViewHolder setImageUrl(int viewId, String url, int errorSrc) {
        ImageView view = getView(viewId);
        ImageLoaderUtils.display(mContext, view, url, errorSrc);
        return this;
    }

    public BaseViewHolder setImageCircleUrl(int viewId, String url) {
        ImageView view = getView(viewId);
        ImageLoaderUtils.displayCircle(mContext, view, url);
        return this;
    }

    public BaseViewHolder setImageRouneUrl(int viewId, String url, int round) {
        ImageView view = getView(viewId);
        ImageLoaderUtils.displayRound(mContext, view, url, round);
        return this;
    }

    public BaseViewHolder setImageRouneUrl(int viewId, String url, int errror, int round) {
        ImageView view = getView(viewId);
        ImageLoaderUtils.displayRound(mContext, view, url, errror, round);
        return this;
    }

    public BaseViewHolder setImageUrl(int viewId, String url) {
        setImageUrl(viewId, url, 0);
        return this;
    }

    public BaseViewHolder setAdapter(int viewId, CommonAdapter adapter) {
        ListView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    public BaseViewHolder setMaxWith(int viewId, int with) {
        TextView view = getView(viewId);
        view.setMaxWidth(with);
        return this;
    }

    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public String getText(int viewId) {
        TextView view = getView(viewId);
        return view.getText().toString();
    }

    public BaseViewHolder setImageURI(int viewId, Uri uri) {
        ImageView view = getView(viewId);
        view.setImageURI(uri);
        return this;
    }

    public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public BaseViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(ContextCompat.getColor(mContext, textColorRes));
        return this;
    }

    public BaseViewHolder setImageResource(int viewId, int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public BaseViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(ContextCompat.getColor(mContext, color));
        return this;
    }

    public BaseViewHolder setBackgroundResource(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public BaseViewHolder setInVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public BaseViewHolder setSelected(int viewId, boolean isSelect) {
        View view = getView(viewId);
        view.setSelected(isSelect);
        return this;
    }

    public boolean isSelected(int viewId) {
        View view = getView(viewId);
        return view.isSelected();
    }

    public BaseViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        CheckBox view = getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    public BaseViewHolder setCheckBoxChecked(int viewId, boolean isCheck) {
        CheckBox view = getView(viewId);
        view.setChecked(isCheck);
        return this;
    }

    public BaseViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BaseViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }


    public BaseViewHolder setLeftCompoundDrawables(int viewId, @Nullable int left) {
        TextView view = getView(viewId);
        Drawable drawable = ContextCompat.getDrawable(mContext, left);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(drawable, null, null, null);
        return this;
    }

    public BaseViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }
}
