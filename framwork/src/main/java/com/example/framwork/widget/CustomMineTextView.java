package com.example.framwork.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.R;


/**
 * Created by lenovo on 2018/5/11.
 */

public class CustomMineTextView extends LinearLayout {
    private TextView tvLeft;
    private TextView tvRight;
    private ImageView ivIcon;
    private ImageView ivNext;
    private String sLeft, sRightHint;
    private boolean isNext;
    private int icon;
    private int leftColor, rightColor;


    public CustomMineTextView(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public CustomMineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CustomMineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMineTextView);
        sLeft = typedArray.getString(R.styleable.CustomMineTextView_ctv_left);
        sRightHint = typedArray.getString(R.styleable.CustomMineTextView_ctv_right_hint);
        isNext = typedArray.getBoolean(R.styleable.CustomMineTextView_ctv_next, true);
        icon = typedArray.getResourceId(R.styleable.CustomMineTextView_ctv_icon, 0);
        leftColor = typedArray.getResourceId(R.styleable.CustomMineTextView_ctv_left_color, R.color.black);
        rightColor = typedArray.getResourceId(R.styleable.CustomMineTextView_ctv_left_color, R.color.gary_82);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_mine_text_view, this, true);
        tvLeft = findViewById(R.id.tv_left);
        tvRight = findViewById(R.id.tv_right);
        ivNext = findViewById(R.id.iv_next);
        ivIcon = findViewById(R.id.iv_icon);
        tvLeft.setText(sLeft);
        tvRight.setHint(sRightHint);
        tvLeft.setTextColor(ContextCompat.getColor(context, leftColor));
        tvRight.setTextColor(ContextCompat.getColor(context, rightColor));
        ivNext.setVisibility(isNext ? VISIBLE : GONE);
        ivIcon.setVisibility(icon != 0 ? VISIBLE : GONE);
        ivIcon.setImageResource(icon);
    }


    public void setTvRight(CharSequence s) {
        this.tvRight.setText(s);
    }

    public void setsRightHint(CharSequence s) {
        this.tvRight.setHint(s);
    }
}
