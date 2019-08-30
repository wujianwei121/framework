package com.example.framwork.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.SparseLongArray;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.framwork.BaseApplictaion;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author wujianwei
 *         倒计时按钮
 */
public class TimeButton extends android.support.v7.widget.AppCompatButton implements OnClickListener {
    private long lenght = 60 * 1000;// 默认60秒
    private String textafter = "秒";
    private String textbefore = "获取验证码";
    private final int TIME = 1;
    private final int CTIME = 2;
    private OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    private long time;
    public static boolean isRun = false;
    private int clickColor = Color.WHITE;//点击颜色

    public TimeButton(Context context) {
        super(context);
        setOnClickListener(this);

    }

    public int getClickColor() {
        return clickColor;
    }

    public void setClickColor(int clickColor) {
        this.clickColor = clickColor;
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            setText(time / 1000 + textafter);
            time -= 1000;
            if (time < 0) {
                TimeButton.this.setEnabled(true);
                TimeButton.this.setText(textbefore);
                isRun = false;
                clearTimer();
            }
        }

        ;
    };

    private void initTimer() {
        time = lenght;
        t = new Timer();
        tt = new TimerTask() {

            @Override
            public void run() {
                han.sendEmptyMessage(0x01);
            }
        };
    }

    public void clearTimer() {
        isRun = false;
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null)
            t.cancel();
        t = null;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof TimeButton) {
            super.setOnClickListener(l);
        } else
            this.mOnclickListener = l;
    }

    @Override
    public void onClick(View v) {
        if (mOnclickListener != null)
            mOnclickListener.onClick(v);
        initTimer();
        this.setText(time / 1000 + textafter);
        this.setEnabled(false);
        isRun = true;
        t.schedule(tt, 0, 1000);
        // t.scheduleAtFixedRate(task, delay, period);
    }

    /**
     * 需要在activity里面同步处理
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void onDestroy() {
        if (BaseApplictaion.timeMap == null)
            BaseApplictaion.timeMap = new SparseLongArray();
        BaseApplictaion.timeMap.put(TIME, time);
        BaseApplictaion.timeMap.put(CTIME, System.currentTimeMillis());
        clearTimer();
    }

    /**
     * 需要在oncreate里面同步处理
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void onCreate(Bundle bundle) {
        if (BaseApplictaion.timeMap == null)
            return;
        if (BaseApplictaion.timeMap.size() <= 0)
            return;
        long time = System.currentTimeMillis() - BaseApplictaion.timeMap.get(CTIME)
                - BaseApplictaion.timeMap.get(TIME);
        BaseApplictaion.timeMap.clear();
        if (time > 0)
            return;
        else {
            initTimer();
            this.time = Math.abs(time);
            t.schedule(tt, 0, 1000);
            this.setText(time + textafter);
            this.setEnabled(false);
        }
    }

    /**
     * 设置倒计时结束后显示的文字
     */
    public TimeButton setTextAfter(String text1) {
        this.textafter = text1;
        return this;
    }

    /**
     * 设置在倒计时前的文本
     */
    public TimeButton setTextBefore(String text0) {
        this.textbefore = text0;
        this.setText(textbefore);
        return this;
    }

    /**
     * @param lenght 默认时长
     * @return
     */
    public TimeButton setLenght(long lenght) {
        this.lenght = lenght;
        return this;
    }
    /*

*
*/
}