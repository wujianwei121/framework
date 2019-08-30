package com.example.framwork.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.framwork.R;
import com.example.framwork.utils.ViewFindUtils;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;

/**
 * 加载本地
 */
public class SimpleGuideResBanner extends BaseIndicatorBanner<Integer, SimpleGuideResBanner> {
    private String jumpText;
    private int jumpBg;
    private int jumpTextColor;
    private Context context;


    public SimpleGuideResBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initAttrs(context, attrs);
    }

    public SimpleGuideResBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initAttrs(context, attrs);
        setBarShowWhenLast(false);
    }

    public void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GuildPageStyle);
        jumpText = ta.getString(R.styleable.GuildPageStyle_gp_btn_text);
        jumpBg = ta.getResourceId(R.styleable.GuildPageStyle_gp_btn_text_bg, 0);
        jumpTextColor = ta.getResourceId(R.styleable.GuildPageStyle_gp_btn_text_color, 0);
        ta.recycle();
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(mContext, R.layout.adapter_simple_guide, null);
        ImageView iv = ViewFindUtils.find(inflate, R.id.iv);
        TextView tv_jump = ViewFindUtils.find(inflate, R.id.tv_jump);
        tv_jump.setText(jumpText);
        if (jumpBg != 0) {
            tv_jump.setBackgroundResource(jumpBg);
        }
        if (jumpTextColor != 0) {
            tv_jump.setTextColor(ContextCompat.getColor(context, jumpTextColor));
        }
        final Integer resId = mDatas.get(position);
        tv_jump.setVisibility(position == mDatas.size() - 1 ? VISIBLE : GONE);

        Glide.with(mContext)
                .load(resId)
                .into(iv);

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
