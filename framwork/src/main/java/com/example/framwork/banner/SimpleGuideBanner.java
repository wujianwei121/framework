package com.example.framwork.banner;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framwork.R;
import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.ViewFindUtils;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;

public class SimpleGuideBanner extends BaseIndicatorBanner<BannerItem, SimpleGuideBanner> {
    private int btnBackground = R.color.white;
    private String btnText = "开启APP";
    private int btnTextColor = R.color.black;

    public int getBtnTextColor() {
        return btnTextColor;
    }

    public void setBtnTextColor(int btnTextColor) {
        this.btnTextColor = btnTextColor;
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    public int getBtnBackground() {
        return btnBackground;
    }

    public void setBtnBackground(int btnBackground) {
        this.btnBackground = btnBackground;
    }

    public SimpleGuideBanner(Context context) {
        this(context, null, 0);
    }

    public SimpleGuideBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleGuideBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBarShowWhenLast(false);
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(mContext, R.layout.adapter_simple_guide, null);
        ImageView iv = ViewFindUtils.find(inflate, R.id.iv);
        TextView tv_jump = ViewFindUtils.find(inflate, R.id.tv_jump);
        tv_jump.setBackgroundResource(btnBackground);
        tv_jump.setTextColor(ContextCompat.getColor(mContext, btnTextColor));
        tv_jump.setText(btnText);
        String imgUrl = mDatas.get(position).getImgUrl();
        tv_jump.setVisibility(position == mDatas.size() - 1 ? VISIBLE : GONE);
        ImageLoaderUtils.display(mContext, iv, imgUrl);

        tv_jump.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onJumpClickL != null)
                    onJumpClickL.onJumpClick();
            }
        });

        return inflate;
    }

    private OnJumpClickL onJumpClickL;

    public interface OnJumpClickL {
        void onJumpClick();
    }

    public void setOnJumpClickL(OnJumpClickL onJumpClickL) {
        this.onJumpClickL = onJumpClickL;
    }

}
