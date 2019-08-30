package com.example.framwork.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.OverScroller;
import android.widget.TextView;

import com.example.framwork.R;
import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.ViewFindUtils;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;


public class SimpleImageBanner extends
        BaseIndicatorBanner<BannerItem, SimpleImageBanner> {
    private ColorDrawable colorDrawable;
    private Context mContext;
    private int errorResourceId;//加载错误显示的默认图
    private boolean isRound;//是否圆角

    public int getErrorResourceId() {
        return errorResourceId;
    }

    public void setErrorResourceId(int errorResourceId) {
        this.errorResourceId = errorResourceId;
    }

    public void isRound(boolean round) {
        this.isRound = round;
    }


    public SimpleImageBanner(Context context) {
        this(context, null, 0);
        this.mContext = context;
    }

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
        if (mDatas != null && mDatas.size() != 0) {
            final BannerItem item = mDatas.get(position);
            tv.setText(item.getTitle());
        }
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(mContext, R.layout.adapter_simple_image,
                null);
        RoundedImageView iv = ViewFindUtils.find(inflate, R.id.iv);
        if (isRound) {
            iv.setCornerRadius(10);
        } else {
            iv.setCornerRadius(0);
        }
        if (mDatas != null && mDatas.size() != 0) {
            final BannerItem item = mDatas.get(position);
            String imgUrl = item.getImgUrl();
            if (!TextUtils.isEmpty(imgUrl)) {
                if (getErrorResourceId() != 0) {
                    ImageLoaderUtils.display(mContext, iv, imgUrl, getErrorResourceId());
                } else {
                    ImageLoaderUtils.display(mContext, iv, imgUrl, colorDrawable);
                }
            } else {
                if (getErrorResourceId() != 0) {
                    iv.setImageResource(getErrorResourceId());
                } else {
                    iv.setImageDrawable(colorDrawable);
                }

            }

        }
        return inflate;
    }
}
