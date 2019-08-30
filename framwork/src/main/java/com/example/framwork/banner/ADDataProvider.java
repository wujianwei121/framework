package com.example.framwork.banner;

import com.flyco.banner.anim.select.ZoomInEnter;

import java.util.List;

/**
 * 头部广告帮助类
 */
public class ADDataProvider {


    public interface OnAdItemClickListener {
        void onAdItemClick(int pos);
    }

    /**
     * 初始化广告栏
     */
    public static void initAdvertisement(SimpleImageBanner sib, final List<BannerItem> imageInfos) {
        if (imageInfos.size() == 0) {
            return;
        }
        //设置默认加载图片
        sib.setErrorResourceId(0);
        sib.setSelectAnimClass(ZoomInEnter.class)
                .setSource(imageInfos)
                .startScroll();
    }

    /**
     * 初始化广告栏
     */
    public static void initAdvertisementRound(SimpleImageBanner sib, List<BannerItem> imageInfos) {
        if (imageInfos.size() == 0) {
            return;
        }
        //设置默认加载图片
        sib.setErrorResourceId(0);
        sib.isRound(true);
        sib.setSelectAnimClass(ZoomInEnter.class)
                .setSource(imageInfos)
                .startScroll();
    }
}
