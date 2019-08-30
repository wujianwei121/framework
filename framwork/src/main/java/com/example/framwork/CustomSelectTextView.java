package com.example.framwork;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.framwork.bean.DialogListInfo;
import com.example.framwork.utils.DialogUtils;
import com.example.framwork.utils.PickerUtil;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by lenovo on 2018/5/11.
 */

public class CustomSelectTextView extends LinearLayout {
    private TextView tvLeft;
    private TextView tvRight;
    private String sLeft, sRightHint, sRight;
    private int leftColor, rightColor, themeColor;
    private float leftW;
    private int type;
    private int timeType;
    private int rightGravity;
    private OptionsPickerView cityPickerView;
    private TimePickerView timePicker;
    private Context context;
    private PickerUtil.OnCityClickListener onCityClickListener;
    private OnTimeSelectListener onTimeSelectListener;
    private DialogUtils.OnItemClickListener onItemClickListener;
    protected Dialog listDialog, gridDialog;
    private List<DialogListInfo> dialogListInfos;
    private boolean isClick;

    public List<DialogListInfo> getDialogListInfos() {
        return dialogListInfos;
    }

    public void setOnItemClickListener(DialogUtils.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void setOnTimeSelectListener(OnTimeSelectListener onTimeSelectListener) {
        this.onTimeSelectListener = onTimeSelectListener;
    }

    public void setOnCityClickListener(PickerUtil.OnCityClickListener onCityClickListener) {
        this.onCityClickListener = onCityClickListener;
    }

    public CustomSelectTextView(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public CustomSelectTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CustomSelectTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSelectTextView);
        isClick = typedArray.getBoolean(R.styleable.CustomSelectTextView_cstv_click, true);
        sLeft = typedArray.getString(R.styleable.CustomSelectTextView_cstv_left);
        sRight = typedArray.getString(R.styleable.CustomSelectTextView_cstv_right);
        themeColor = typedArray.getResourceId(R.styleable.CustomSelectTextView_cstv_theme_color, R.color.black);
        leftColor = typedArray.getResourceId(R.styleable.CustomSelectTextView_cstv_left_color, R.color.black);
        rightColor = typedArray.getResourceId(R.styleable.CustomSelectTextView_cstv_right_color, R.color.black);
        leftW = typedArray.getDimension(R.styleable.CustomSelectTextView_cstv_left_w, 0);
        sRightHint = typedArray.getString(R.styleable.CustomSelectTextView_cstv_right_hint);
        type = typedArray.getInt(R.styleable.CustomSelectTextView_cstv_type, 2);
        timeType = typedArray.getInt(R.styleable.CustomSelectTextView_cstv_time_type, 1);
        rightGravity = typedArray.getInt(R.styleable.CustomSelectTextView_cstv_right_gravity, 1);
        typedArray.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_select_text_view, this, true);
        tvLeft = findViewById(R.id.tv_left);
        tvRight = findViewById(R.id.tv_right);
        tvLeft.setTextColor(ContextCompat.getColor(context, leftColor));
        tvRight.setTextColor(ContextCompat.getColor(context, rightColor));
        if (leftW == 0) {
            tvLeft.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            tvLeft.setLayoutParams(new LayoutParams((int) leftW, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        tvRight.setGravity(rightGravity == 1 ? Gravity.LEFT : Gravity.RIGHT);
        tvLeft.setText(sLeft);
        if (!TextUtils.isEmpty(sRight))
            tvRight.setText(sRight);
        tvRight.setHint(sRightHint);
        setTextClickable(isClick);
        dialogListInfos = new ArrayList<>();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClick) {
                    return;
                }
                hideSoftInput(tvRight);
                if (type == 1) {
                    if (timePicker == null) {
                        if (timeType == 1) {
                            timePicker = PickerUtil.getInstance().initTimePicker(context, PickerUtil.YEAR_MONTH_DAY, themeColor, onTimeSelectListener);
                        } else {
                            timePicker = PickerUtil.getInstance().initTimePicker(context, PickerUtil.ALL, themeColor, onTimeSelectListener);
                        }
                    }
                    timePicker.show();
                } else if (type == 2) {
                    if (cityPickerView == null) {
                        cityPickerView = PickerUtil.getInstance().initCityPicker(context, onCityClickListener);
                    }
                    cityPickerView.show();
                }
            }
        });
    }

    public void setTextClickable(boolean isClick) {
        this.isClick = isClick;
        if (!isClick) {
            tvRight.setCompoundDrawables(null, null, null, null);// 设置到控件中
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput(TextView view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void dismissDialog() {
        if (timePicker != null && timePicker.isShowing()) {
            timePicker.dismiss();
        }
        if (cityPickerView != null && cityPickerView.isShowing()) {
            cityPickerView.dismiss();
        }
        if (listDialog != null && listDialog.isShowing()) {
            listDialog.dismiss();
        }
        if (gridDialog != null && gridDialog.isShowing()) {
            gridDialog.dismiss();
        }
    }

    public void clearDialog() {
        listDialog = null;
        gridDialog = null;
    }

    public void setListData(List<? extends DialogListInfo> dialogListInfos) {
        if (dialogListInfos.size() == 0) {
            Toasty.warning(context, "暂未获取到任何数据！").show();
            return;
        }
        this.dialogListInfos.clear();
        this.dialogListInfos.addAll(dialogListInfos);
        if (listDialog == null) {
            listDialog = DialogUtils.getInstance().getBottomListDialog(context, this.dialogListInfos, themeColor, onItemClickListener);
        }
        DialogUtils.getInstance().refreshAdapter();
    }

    public void setGrideData(List<? extends DialogListInfo> dialogListInfos) {
        if (dialogListInfos.size() == 0) {
            Toasty.warning(context, "暂未获取到任何数据！").show();
            return;
        }
        this.dialogListInfos.clear();
        this.dialogListInfos.addAll(dialogListInfos);
        if (gridDialog == null) {
            gridDialog = DialogUtils.getInstance().getBottomGrideDialog(context, this.dialogListInfos, themeColor, onItemClickListener);
        }
        DialogUtils.getInstance().refreshAdapter();
    }

    public void showListDialog() {
        if (!isClick) {
            return;
        }
        listDialog.show();
    }

    public void showGrideDialog() {
        if (!isClick) {
            return;
        }
        gridDialog.show();
    }

    public void setRightContent(String s) {
        this.tvRight.setText(s);
    }

    public void setRightHint(CharSequence s) {
        this.tvRight.setHint(s);
    }

    public void setLeftContent(String s) {
        this.tvLeft.setText(s);
    }

    public String getRightContent() {
        return tvRight.getText().toString();
    }

    public String getRightHint() {
        return tvRight.getHint() != null ? tvRight.getHint().toString() : "";
    }
}
