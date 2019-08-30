package com.example.framwork.behavior;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.framwork.BaseApplictaion;
import com.example.framwork.R;
import com.yanzhenjie.nohttp.Logger;

import java.util.List;

/**
 * 可滚动的 Content Behavior
 * <p/>
 * Created by xujun
 */
public class WeiboContentBehavior extends HeaderScrollingViewBehavior {
    private static final String TAG = "WeiboContentBehavior";

    public WeiboContentBehavior() {
    }

    public WeiboContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetChildAsNeeded(parent, child, dependency);
        return false;
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {
        float dependencyTranslationY = dependency.getTranslationY();
        int translationY = (int) (-dependencyTranslationY / (getHeaderOffsetRange() * 1.0f) *
                getScrollRange(dependency));
        Log.i(TAG, "offsetChildAsNeeded: translationY=" + translationY);
        child.setTranslationY(translationY);

    }

    @Override
    protected View findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (isDependOn(view)) return view;
        }
        return null;
    }

    @Override
    protected int getScrollRange(View v) {
        if (isDependOn(v)) {
            return Math.max(0, v.getMeasuredHeight() - getFinalHeight());
        } else {
            return super.getScrollRange(v);
        }
    }

    private int getHeaderOffsetRange() {
        int d = BaseApplictaion.get().getResources().getDimensionPixelOffset(R.dimen
                .weibo_header_offset);
        Logger.d("-------------c------------" + d);
        return d;
    }

    private int getFinalHeight() {
        Resources resources = BaseApplictaion.get().getResources();

        return 0;
    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.id_weibo_header;
    }
}
