package com.example.framwork.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.R;
import com.example.framwork.bean.UpdatePhotoInfo;
import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.FileUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

/**
 * Created by lenovo on 2018/5/11.
 */

public class CustomHeaderView extends LinearLayout {
    public static final int SELECT_HEADER_CODE = 0x128;
    private CircleImageView ivHeader;
    private String leftHint;
    private int hintIcon;
    private Context mContext;
    private Activity mActivity;
    private ISelectHeaderListener selectHeaderListener;

    public void setSelectHeaderListener(ISelectHeaderListener selectHeaderListener) {
        this.selectHeaderListener = selectHeaderListener;
    }

    public CustomHeaderView(Context context) {
        super(context);
        this.mContext = context;
        initAttrs(context, null);
    }

    public CustomHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAttrs(context, attrs);
    }

    public CustomHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomHeaderView);
        leftHint = typedArray.getString(R.styleable.CustomHeaderView_chv_hint);
        hintIcon = typedArray.getResourceId(R.styleable.CustomHeaderView_chv_icon, R.drawable.ic_circle_header);
        typedArray.recycle();
        init();
    }

    private void init() {
        if (mContext instanceof Activity) {
            mActivity = (Activity) mContext;
        }
        LayoutInflater.from(getContext()).inflate(R.layout.view_header_view, this, true);
        ivHeader = findViewById(R.id.iv_cus_header);
        TextView tvHint = findViewById(R.id.tv_hint);
        ivHeader.setImageResource(hintIcon);
        tvHint.setText(leftHint);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(ivHeader);
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(mActivity)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                        .theme(com.example.framwork.R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                        .imageSpanCount(4)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .isGif(false)// 是否显示gif图片 true or false
                        .openClickSound(false)// 是否开启点击声音 true or false
                        .withAspectRatio(1, 1)
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                        .circleDimmedLayer(false)
                        .enableCrop(true)
                        .isDragFrame(true)
                        .scaleEnabled(true)
                        .rotateEnabled(false)
                        .compress(true)
                        .freeStyleCropEnabled(true)
                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                        .forResult(SELECT_HEADER_CODE);//结果回调onActivityResult code
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_HEADER_CODE) {
            List<LocalMedia> pathList = PictureSelector.obtainMultipleResult(data);
            if (pathList != null && pathList.size() != 0) {
                LocalMedia localMedia = pathList.get(0);
                UpdatePhotoInfo photoInfo = new UpdatePhotoInfo();
                if (localMedia.isCut())
                    photoInfo.localPath = localMedia.getCutPath();
                else
                    photoInfo.localPath = localMedia.getPath();
                photoInfo.photoSize = (int) (FileUtil.getInstance().getFileSizeL(mActivity, photoInfo.localPath) / 1024);
                if (photoInfo.photoSize == 0) {
                    Toasty.warning(mActivity, "所选图片已损坏");
                }
                if (selectHeaderListener != null) {
                    selectHeaderListener.selectHeaderImageSuccess(photoInfo.localPath);
                }
            }
        }
    }

    public interface ISelectHeaderListener {
        void selectHeaderImageSuccess(String url);
    }

    public void setUrlImage(String url) {
        if (!TextUtils.isEmpty(url))
            ImageLoaderUtils.displayCircleBorder(mContext, ivHeader, url, hintIcon);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput(View view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
