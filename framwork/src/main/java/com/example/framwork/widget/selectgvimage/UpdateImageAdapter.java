package com.example.framwork.widget.selectgvimage;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.framwork.R;
import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.DensityUtil;

import java.util.List;


/**
 * Created by gao on 2017/3/7.
 */

public class UpdateImageAdapter extends BaseAdapter {

    private Context context = null;
    private List<UpdatePhotoInfo> list = null;
    private LayoutInflater inflater = null;
    private int width;
    private int white_px;
    private OnDelPic onDelPic = null;
    private int maxPhtoto = 1;
    private boolean isSquare = false;
    private boolean isClick;
    private int numColumns;
    private int iv_del_src, iv_add_src;

    public void setMaxPhtoto(int maxPhtoto) {
        this.maxPhtoto = maxPhtoto;
    }

    public UpdateImageAdapter(Context context, List<UpdatePhotoInfo> list, int maxPhoto, int numColumns, int iv_del_src, int iv_add_src, float horizontalSpacing, boolean isSquare, OnDelPic onDelPic, boolean isClick) {
        this.context = context;
        this.list = list;
        this.isSquare = isSquare;
        this.numColumns = numColumns;
        this.onDelPic = onDelPic;
        this.maxPhtoto = maxPhoto;
        setWhitePx(horizontalSpacing);
        this.isClick = isClick;
        this.iv_add_src = iv_add_src;
        this.iv_del_src = iv_del_src;
    }

    public void setClick(boolean click) {
        isClick = click;
        notifyDataSetChanged();
    }

    private void setWhitePx(float horizontalSpacing) {
        inflater = LayoutInflater.from(context);
        width = DensityUtil.getScreenWidth(context);
        white_px = (int) ((numColumns + 1) * horizontalSpacing);
    }

    @Override
    public int getCount() {
        if (list.size() == maxPhtoto)
            return list.size();
        else return list.size() + 1;
    }

    @Override
    public Object getItem(int i) {
        return list.size() == 0 ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_select_img, null);
            view.setTag(holder);
            holder.imageView = view.findViewById(R.id.pic);
            holder.delView = view.findViewById(R.id.del_pic);
            holder.delView.setImageResource(iv_del_src);
            holder.delView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer pos = (Integer) view.getTag();
                    onDelPic.delClick(pos);
                }
            });
            RelativeLayout.LayoutParams param = new RelativeLayout
                    .LayoutParams((width - white_px) / numColumns, (width - white_px) / numColumns);
            holder.imageView.setLayoutParams(param);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.delView.setTag(i);
        if (i == list.size()) {
            ImageLoaderUtils.display(context, holder.imageView, iv_add_src);
            holder.delView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            if (i == maxPhtoto || !isClick) {
                holder.imageView.setVisibility(View.GONE);
            }
        } else {
            if (isClick) {
                holder.delView.setVisibility(View.VISIBLE);
            } else {
                holder.delView.setVisibility(View.GONE);
            }
            if (isSquare) {
                ImageLoaderUtils.displayRound(context, holder.imageView, list.get(i).localPath);
            } else
                ImageLoaderUtils.display(context, holder.imageView, list.get(i).localPath);
        }
        return view;
    }

    public interface OnDelPic {
        void delClick(int position);
    }

    public class ViewHolder {
        public ImageView imageView = null;
        public ImageView delView = null;
    }
}
