package com.example.framwork.widget.customtoolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.framwork.R;


public class AutoTitle extends LinearLayout {

    public AutoTitle(Context context) {
        super(context);
        init(context, null);
    }

    @SuppressLint("NewApi")
    public AutoTitle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public AutoTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflater = LayoutInflater.from(getContext());
        setOrientation(LinearLayout.HORIZONTAL);
        initAttrs(context, attrs);
        initView();
        setExtras();
    }

    private LayoutInflater inflater;
    private LinearLayout layout_left;
    private LinearLayout layout_center;
    private LinearLayout layout_right;

    private TextView left_tv;
    private TextView right_tv;
    private TextView center_tv;

    private View left_space;
    private View right_space;

    private void initView() {
        LayoutParams params_left = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layout_left = new LinearLayout(getContext());
        layout_left.setLayoutParams(params_left);
        layout_left.setPadding(titleLeftpadding, 0, titleLeftpadding, 0);
        layout_left.setGravity(Gravity.CENTER);

        LayoutParams params_right = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layout_right = new LinearLayout(getContext());
        layout_right.setLayoutParams(params_right);
        layout_right.setPadding(titleRightpadding, 0, titleRightpadding, 0);
        layout_right.setGravity(Gravity.CENTER);

        LayoutParams params_center = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        params_center.weight = 1;
        layout_center = new LinearLayout(getContext());
        layout_center.setLayoutParams(params_center);
        layout_center.setGravity(Gravity.CENTER);

        LayoutParams params_space_left = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        left_space = new View(getContext());
        left_space.setLayoutParams(params_space_left);

        LayoutParams params_space_right = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        right_space = new View(getContext());
        right_space.setLayoutParams(params_space_right);

        if (isInEditMode()) {
            layout_left.setBackgroundColor(0x7F000000);
            layout_right.setBackgroundColor(0x7F000000);
            layout_center.setBackgroundColor(0x7Fff0000);
        }

        addView(layout_left);
        addView(left_space);
        addView(layout_center);
        addView(right_space);
        addView(layout_right);
    }

    private int titleLeftpadding = 0;
    private int titleRightpadding = 0;

    private String lefttext_text;
    private int lefttext_size = -1;
    private int lefttext_color = -1;
    private Drawable lefttext_drawable;
    private int lefttext_appearance;

    private String righttext_text;
    private int righttext_size = -1;
    private int righttext_color = -1;
    private Drawable righttext_drawable;
    private int righttext_view;
    private int righttext_appearance;

    private String centertext_text;
    private int centertext_size = -1;
    private int centertext_color = -1;
    private Drawable centertext_drawable;
    private int centertext_appearance;

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AutoTitle);

        titleLeftpadding = array.getDimensionPixelSize(R.styleable.AutoTitle_titleLeftpadding, titleLeftpadding);
        titleRightpadding = array.getDimensionPixelSize(R.styleable.AutoTitle_titleRightpadding, titleRightpadding);

        lefttext_text = array.getString(R.styleable.AutoTitle_titleLeftText);
        lefttext_size = array.getDimensionPixelSize(R.styleable.AutoTitle_titleLeftTextSize, lefttext_size);
        lefttext_color = array.getColor(R.styleable.AutoTitle_titleLeftTextColor, lefttext_color);
        lefttext_drawable = array.getDrawable(R.styleable.AutoTitle_titleLeftDrawable);
        lefttext_appearance = array.getResourceId(R.styleable.AutoTitle_titleLeftAppearance, -1);

        righttext_text = array.getString(R.styleable.AutoTitle_titleRightText);
        righttext_size = array.getDimensionPixelSize(R.styleable.AutoTitle_titleRightTextSize, righttext_size);
        righttext_color = array.getColor(R.styleable.AutoTitle_titleRightTextColor, righttext_color);
        righttext_drawable = array.getDrawable(R.styleable.AutoTitle_titleRightDrawable);
        righttext_appearance = array.getResourceId(R.styleable.AutoTitle_titleRightAppearance, -1);

        centertext_text = array.getString(R.styleable.AutoTitle_titleCenterText);
        centertext_size = array.getDimensionPixelSize(R.styleable.AutoTitle_titleCenterTextSize, centertext_size);
        centertext_color = array.getColor(R.styleable.AutoTitle_titleCenterTextColor, centertext_color);
        centertext_drawable = array.getDrawable(R.styleable.AutoTitle_titleCenterDrawable);
        centertext_appearance = array.getResourceId(R.styleable.AutoTitle_titleCenterAppearance, -1);

        righttext_view = array.getResourceId(R.styleable.AutoTitle_titleRightView, -1);
        array.recycle();
    }

    private void setExtras() {
        if (!TextUtils.isEmpty(lefttext_text)) {
            initLeftText();
        } else if (lefttext_drawable != null) {
            initLeftText();
            left_tv.setBackground(lefttext_drawable);
        }

        if (righttext_view != -1) {
            layout_right.addView(inflater.inflate(righttext_view, layout_right, false));
        } else if (!TextUtils.isEmpty(righttext_text)) {
            initRightText();
        } else if (righttext_drawable != null) {
            initRightText();
            right_tv.setBackground(righttext_drawable);
        }

        if (!TextUtils.isEmpty(centertext_text)) {
            initCenterText();
        } else if (centertext_drawable != null) {
            initCenterText();
            center_tv.setBackground(centertext_drawable);
        }

        initCenterText();
    }

    private int count = 0;

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() >= 5) {
            if (child instanceof Space || child instanceof android.support.v4.widget.Space) {
                count++;
            } else if (count == 0) {
                layout_left.removeAllViews();
                layout_left.addView(child, params);
                count++;
            } else if (count == 1) {
                layout_center.removeAllViews();
                layout_center.addView(child, params);
                count++;
            } else if (count == 2) {
                layout_right.removeAllViews();
                layout_right.addView(child, params);
                count++;
            }
        } else {
            super.addView(child, index, params);
        }
    }

    private void initLeftText() {
        if (left_tv == null) {
            left_tv = getTextView();
            left_tv.setMaxEms(5);
            left_tv.setSingleLine();
            left_tv.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            layout_left.addView(left_tv);
            left_tv.setText(lefttext_text);

            if (lefttext_appearance != -1) {
                left_tv.setTextAppearance(getContext(), lefttext_appearance);
            }
            if (lefttext_size != -1) {
                left_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, lefttext_size);
            }
            left_tv.setTextColor(lefttext_color);
        }
    }

    private void initRightText() {
        if (right_tv == null) {
            right_tv = getTextView();
            right_tv.setMaxEms(5);
            right_tv.setSingleLine();
            right_tv.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            layout_right.addView(right_tv);
            right_tv.setText(righttext_text);

            if (righttext_appearance != -1) {
                right_tv.setTextAppearance(getContext(), righttext_appearance);
            }
            if (righttext_size != -1) {
                right_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, righttext_size);
            }
            right_tv.setTextColor(righttext_color);
        }
    }

    private void initCenterText() {
        if (center_tv == null) {
            center_tv = getTextView();
            layout_center.addView(center_tv);
            center_tv.setText(centertext_text);
            if (centertext_appearance != -1) {
                center_tv.setTextAppearance(getContext(), centertext_appearance);
            }
            if (centertext_size != -1) {
                center_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, centertext_size);
            }
            center_tv.setTextColor(centertext_color);
        }
    }

    private TextView getTextView() {
        TextView textview = new TextView(getContext());
        textview.setSingleLine();
        textview.setEllipsize(TextUtils.TruncateAt.END);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textview.setLayoutParams(params);
        return textview;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (layout_left != null && layout_right != null) {
            layout_left.measure(0, 0);
            layout_right.measure(0, 0);
            int w_left = layout_left.getVisibility() == VISIBLE ? layout_left.getMeasuredWidth() : 0;
            int w_right = layout_right.getVisibility() == VISIBLE ? layout_right.getMeasuredWidth() : 0;
            if (w_left > w_right) {
                left_space.getLayoutParams().width = 0;
                right_space.getLayoutParams().width = w_left - w_right;
            } else if (w_left < w_right) {
                left_space.getLayoutParams().width = w_right - w_left;
                right_space.getLayoutParams().width = 0;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public LinearLayout getLeftLayout() {
        return layout_left;
    }

    public LinearLayout getRightLayout() {
        return layout_right;
    }

    public LinearLayout getCenterLayout() {
        return layout_center;
    }

    public TextView getRightText() {
        return right_tv;
    }
}
