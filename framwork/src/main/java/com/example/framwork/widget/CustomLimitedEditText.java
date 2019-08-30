package com.example.framwork.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.framwork.R;
import com.example.framwork.widget.limitededittext.LimitedEditText;

/**
 * Created by lenovo on 2018/4/12.
 */

public class CustomLimitedEditText extends RelativeLayout {
    private LimitedEditText etContent;
    private TextView countTextView;
    private int limitedCount;
    private int bgId;
    private int mineLine;
    private int textColor;
    private String hint;

    public CustomLimitedEditText(Context context) {
        super(context);
        initAttrs(context, null);

    }

    public CustomLimitedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CustomLimitedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLimitedEditText);
        limitedCount = typedArray.getInteger(R.styleable.CustomLimitedEditText_cle_limitCount, 200);
        mineLine = typedArray.getInteger(R.styleable.CustomLimitedEditText_cle_minLine, 5);
        bgId = typedArray.getResourceId(R.styleable.CustomLimitedEditText_cle_bg, R.color.white);
        textColor = typedArray.getResourceId(R.styleable.CustomLimitedEditText_cle_textColor, R.color.gray_98);
        hint = typedArray.getString(R.styleable.CustomLimitedEditText_cle_hint);
        typedArray.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_limited_edittext, this, true);
        setBackgroundResource(bgId);
        etContent = findViewById(R.id.et_content);
        countTextView = findViewById(R.id.countTextView);
        etContent.setHint(hint);
        etContent.setHintTextColor(ContextCompat.getColor(getContext(), textColor));
        etContent.setTextColor(ContextCompat.getColor(getContext(), textColor));
        etContent.setMinLines(mineLine);
        etContent.setLimitCount(limitedCount);
    }

    public String getEtContent() {
        return etContent.getText().toString();
    }

    public void setEtContent(String s) {
        etContent.setText(s);
    }

    public void setEidtable(boolean isEdit) {
        if (!isEdit) {
            countTextView.setVisibility(GONE);
            etContent.setFocusable(false);
            etContent.setFocusableInTouchMode(false);
        }
    }
}
