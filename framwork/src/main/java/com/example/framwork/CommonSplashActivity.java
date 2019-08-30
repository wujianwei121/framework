package com.example.framwork;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.framwork.base.QuickActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author LackAi
 * @Date 2017/12/22 10:02
 */

public class CommonSplashActivity extends QuickActivity {

    protected ImageView welcomeImg;
    protected Button btnJump;
    private boolean isCanJump = false;
    private int recLen = 3;
    private Timer timer = new Timer();
    private ISplashActivityCallBack callBack;

    public void setCallBack(ISplashActivityCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void initViewsAndEvents() {
        welcomeImg = findViewById(R.id.welcome_img);
        btnJump = findViewById(R.id.btn_jump);
        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCanJump) {
                    task.cancel();
                    gotoActivity();
                }
            }
        });
        hideStatusBar();
    }

    public void hideStatusBar() {
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }


    private void initSplash() {
        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
        anima.setDuration(3000);// 设置动画显示时间
        welcomeImg.startAnimation(anima);
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    recLen--;
                    btnJump.setText(recLen + " 跳过");
                    if (recLen < 0) {
                        timer.cancel();
                        btnJump.setVisibility(View.GONE);
                        gotoActivity();
                    }
                }
            });
        }
    };

    public interface ISplashActivityCallBack {
        void gotoActivity();
    }

    ;

    private void gotoActivity() {
        if (callBack != null) {
            callBack.gotoActivity();
        }
        finish();
    }

    private void startJump() {
        initSplash();
        btnJump.setVisibility(View.VISIBLE);
        timer.schedule(task, 1000, 1000);
    }

    public void startSplash() {
        isCanJump = true;
        startJump();
    }
}
