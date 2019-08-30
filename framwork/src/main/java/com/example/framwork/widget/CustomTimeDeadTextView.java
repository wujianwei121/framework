package com.example.framwork.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by huangminzheng on 2017/9/11 下午3:50.
 * Email:ahtchmz@gmail.com
 */

@SuppressLint("AppCompatCustomView")
public class CustomTimeDeadTextView extends TextView {

    private Context mContext;

    public CustomTimeDeadTextView(Context context) {
        this(context, null);
    }

    public CustomTimeDeadTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTimeDeadTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    /**
     * 开始倒计时
     *
     * @param timeDead 倒计时时间(以秒为单位)
     */
    public void startTimeDead(int timeDead) {
        TimeDeadLine deadLine = new TimeDeadLine(timeDead * 1000, 1000);
        deadLine.start();
    }

    /**
     * 开始倒计时(默认60秒)
     */
    public void startTimeDead() {
        TimeDeadLine deadLine = new TimeDeadLine(60 * 1000, 1000);
        deadLine.start();
    }

    /**
     * 倒计时
     */
    private class TimeDeadLine extends CountDownTimer {

        public TimeDeadLine(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            setClickable(false);
            setEnabled(false);
            setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            setClickable(true);
            setEnabled(true);
            setText("获取验证码");
        }
    }

}
