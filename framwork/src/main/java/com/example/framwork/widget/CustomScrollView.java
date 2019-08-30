package com.example.framwork.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 解决ScrollView嵌套Viewpager导致Viewpager不能滑动的问题
 */
public class CustomScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFadingEdgeLength(0);
    }

    public void setScrollViewListener(ScrollViewListener listener) {
        scrollViewListener = listener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        // x为当前滑动条的横坐标，y表示当前滑动条的纵坐标，oldx为前一次滑动的横坐标，oldy表示前一次滑动的纵坐标
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            // 在这里将方法暴露出去
            scrollViewListener.onScrollChanged(y);
        }
    }


    public interface ScrollViewListener {
        void onScrollChanged(int y);
    }

}