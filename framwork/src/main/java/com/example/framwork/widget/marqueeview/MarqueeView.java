package com.example.framwork.widget.marqueeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.framwork.R;
import com.example.framwork.utils.DensityUtil;


import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by sunfusheng on 16/5/31.
 * 轮播文字效果
 */
public class MarqueeView extends ViewFlipper {

    private Context mContext;
    private List<MarqueeInfo> notices;
    private boolean isSetAnimDuration = false;
    private int height;
    private int interval = 3000;
    private int animDuration = 1000;
    private int textSize = 14;
    private int textColor = 0xffffffff;
    private MarqueeViewClickListener marqueeViewClickListener;

    public void setMarqueeViewClickListener(MarqueeViewClickListener marqueeViewClickListener) {
        this.marqueeViewClickListener = marqueeViewClickListener;
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        if (notices == null) {
            notices = new ArrayList<>();
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0);
        interval = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvInterval, interval);
        isSetAnimDuration = typedArray.hasValue(R.styleable.MarqueeViewStyle_mvAnimDuration);
        animDuration = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvAnimDuration, animDuration);
        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_mvTextSize)) {
            textSize = (int) typedArray.getDimension(R.styleable.MarqueeViewStyle_mvTextSize, textSize);
            textSize = DensityUtil.getInstance().px2sp(mContext, textSize);
        }
        textColor = typedArray.getColor(R.styleable.MarqueeViewStyle_mvTextColor, textColor);
        typedArray.recycle();

        setFlipInterval(interval);

        Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in);
        if (isSetAnimDuration) animIn.setDuration(animDuration);
        setInAnimation(animIn);
        Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out);
        if (isSetAnimDuration) animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }


    // 根据公告字符串列表启动轮播
    public void startWithList(List<MarqueeInfo> notices) {
        setNotices(notices);
        start();
    }


    // 启动轮播
    public boolean start() {
        if (notices == null || notices.size() == 0) return false;
        removeAllViews();
        int noticeSize = 2;
        if (notices.size() > 2) {
            noticeSize = notices.size() % 2 == 0 ? notices.size() / 2 : notices.size() / 2 + 1;
            for (int i = 0; i < noticeSize; i++) {
                List<MarqueeInfo> curNotices = new ArrayList<>();
                for (int j = i; j < i + 2; j++) {
                    int size = i + j;
                    if (size <= notices.size() - 1) {
                        curNotices.add(notices.get(i + j));
                    }
                }
                if (curNotices.size() != 0)
                    addView(createTextView(curNotices));
            }
        } else {
            for (int i = 0; i < noticeSize; i++) {
                List<MarqueeInfo> curNotices = new ArrayList<>();
                for (int j = 0; j < 2; j++) {
                    curNotices.add(notices.get(j));
                }
                if (curNotices.size() != 0)
                    addView(createTextView(curNotices));
            }
        }

        startFlipping();
        return true;
    }

    // 创建ViewFlipper下的TextView
    private View createTextView(final List<MarqueeInfo> texts) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_marquee_view, this, false);
        height = view.getLayoutParams().height;
        TextView tvOne = (TextView) view.findViewById(R.id.tv_new_one);
        TextView tvTwo = (TextView) view.findViewById(R.id.tv_new_two);
        TextView tagOne = (TextView) view.findViewById(R.id.tv_new_tag);
        TextView tagTwo = (TextView) view.findViewById(R.id.tv_new_tag_two);
        if (texts.size() >= 1) {
            tvOne.setText(texts.get(0).getTitle());
            //不做判断，不管什么状态都显示如果做判断请修改 (texts.get(0).isNews() ? VISIBLE : GONE)
            tagOne.setVisibility(texts.get(0).isNews() ? VISIBLE : VISIBLE);
            tvOne.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (marqueeViewClickListener != null) {
                        marqueeViewClickListener.marquessViewClick(texts.get(0));
                    }
                }
            });
        }
        if (texts.size() >= 2) {
            tvTwo.setText(texts.get(1).getTitle());
            //不做判断，不管什么状态都显示如果做判断请修改 (texts.get(1).isNews() ? VISIBLE : GONE)
            tagTwo.setVisibility(texts.get(1).isNews() ? VISIBLE : VISIBLE);
            tvTwo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (marqueeViewClickListener != null) {
                        marqueeViewClickListener.marquessViewClick(texts.get(1));
                    }
                }
            });
        }
        return view;
    }

    public List<MarqueeInfo> getNotices() {
        return notices;
    }

    public void setNotices(List<MarqueeInfo> notices) {
        this.notices.clear();
        this.notices.addAll(notices);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getLayoutParams() != null && getLayoutParams().height == LinearLayout.LayoutParams.WRAP_CONTENT) {
            getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface MarqueeViewClickListener {
        void marquessViewClick(MarqueeInfo s);
    }
}
