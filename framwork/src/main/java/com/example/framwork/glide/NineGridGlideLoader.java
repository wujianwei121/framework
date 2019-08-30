package com.example.framwork.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.framwork.R;
import com.example.framwork.widget.ninegrid.NineGridView;

/**
 * @author wanjingyu
 * @date 2016/6/17
 */
public class NineGridGlideLoader implements NineGridView.ImageLoader {
    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        ImageLoaderUtils.display(context, imageView, url, R.color.default_bg_color);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
