package com.example.framwork.widget.ninegrid.preview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.framwork.R;
import com.example.framwork.widget.ninegrid.ImageInfo;

import java.util.List;

import xyz.zpayh.hdimage.HDImageView;


/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ImagePreviewAdapter extends PagerAdapter {

    private List<ImageInfo> imageInfo;
    private Context context;
    private View currentView;

    public ImagePreviewAdapter(Context context, @NonNull List<ImageInfo> imageInfo) {
        super();
        this.imageInfo = imageInfo;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageInfo.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView = (View) object;
    }

    public View getPrimaryItem() {
        return currentView;
    }

    public HDImageView getPrimaryImageView() {
        return (HDImageView) currentView.findViewById(R.id.pv);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photoview, container, false);
//        final ProgressBar pb = (ProgressBar) view.findViewById(R.id.pb);
        final HDImageView imageView = (HDImageView) view.findViewById(R.id.pv);
        ImageInfo info = this.imageInfo.get(position);
//        showExcessPic(info, imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImagePreviewActivity) context).finishActivityAnim();
            }
        });
        if (info.bigImageUrl.startsWith("http"))
            imageView.setImageURI(info.bigImageUrl);
        else imageView.setImageURI("file://" + info.bigImageUrl);
        container.addView(view);
        return view;
    }

//    /**
//     * 展示过度图片
//     */
//    private void showExcessPic(ImageInfo imageInfo, HDImageView imageView) {
//        //先获取大图的缓存图片
//        Bitmap cacheImage = NineGridView.getImageLoader().getCacheImage(imageInfo.bigImageUrl);
//        //如果大图的缓存不存在,在获取小图的缓存
//        if (cacheImage == null)
//            cacheImage = NineGridView.getImageLoader().getCacheImage(imageInfo.thumbnailUrl);
//        //如果没有任何缓存,使用默认图片,否者使用缓存
//        if (cacheImage == null) {
//            imageView.setImageSource(R.drawable.ic_default_color);
//        } else {
//            imageView.setImageBitmap(cacheImage);
//        }
//    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}