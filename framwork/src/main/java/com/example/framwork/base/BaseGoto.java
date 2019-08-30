package com.example.framwork.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.framwork.WebViewActivity;
import com.example.framwork.widget.ninegrid.ImageInfo;
import com.example.framwork.widget.ninegrid.preview.ImagePreviewActivity;
import com.example.framwork.widget.selectgvimage.UpdatePhotoInfo;
import com.example.framwork.zxing.ui.CaptureActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/3/7.
 */

public class BaseGoto {
    /**
     * 去到拨号界面
     *
     * @param mobile
     */
    public static void toDialMobile(Context context, String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + mobile);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 跳转webview
     *
     * @param context
     * @param title
     * @param url
     * @param bgId    标题栏背景id 如果不需要传0
     */

    public static void toWebView(Context context, String title, String url, int bgId, int backRes, boolean isBar) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("title_bg", bgId);
        intent.putExtra("back_res", backRes);
        intent.putExtra("status_bar", isBar);
        context.startActivity(intent);
    }

    public static void toWebView(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("title_bg", 0);
        intent.putExtra("back_res", 0);
        intent.putExtra("status_bar", false);
        context.startActivity(intent);
    }

    public static void toQRCode(Context context, int bgId) {
        Intent intent = new Intent(context, CaptureActivity.class);
        intent.putExtra("title_bg", bgId);
        context.startActivity(intent);
    }

    /**
     * 图片预览
     *
     * @param context
     * @param list
     * @param position
     */
    public static void toImagePreviewActivity(Context context, List<ImageInfo> list, int position) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) list);
        bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, position);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 图片预览
     *
     * @param context
     */
    public static void toImagePreviewActivity(Context context, String imgUrl) {
        List<ImageInfo> list = new ArrayList<>();
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setBigImageUrl(imgUrl);
        list.add(imageInfo);
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) list);
        bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
