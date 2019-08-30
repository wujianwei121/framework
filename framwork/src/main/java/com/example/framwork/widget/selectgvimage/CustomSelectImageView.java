package com.example.framwork.widget.selectgvimage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.framwork.R;
import com.example.framwork.base.BaseGoto;
import com.example.framwork.utils.FileUtil;
import com.example.framwork.widget.CustomerGridView;
import com.example.framwork.widget.ninegrid.ImageInfo;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


/**
 * 自定义选择布局
 * Created by huangminzheng on 2017/8/26 下午7:44.
 * Email:ahtchmz@gmail.com
 */
public class CustomSelectImageView extends LinearLayout implements UpdateImageAdapter.OnDelPic {
    public static final int SELECT_IMAGE_CODE = 0x122;
    private Context mContext;
    //gridview 间距
    private float gv_horizontalSpacing, gv_verticalSpacing;
    //添加图片  和   删除图片  图标
    private int iv_del_src, iv_add_src, maxPhoto, numColumns;
    //是否圆角
    private boolean isCircular;
    private UpdateImageAdapter imageAdapter;
    private boolean isClickEnable = true;//是否可点击(默认可点击)
    private List<UpdatePhotoInfo> selectImageList;
    private GridView gv_image;
    private Activity mActivity;
    private int selectMode, cropMode;//剪裁正方形还是长方形
    private boolean isCrop;//剪裁
    private int aspect_ratio_x = 1, aspect_ratio_y = 1;
    private int requestCode = SELECT_IMAGE_CODE;

    /**
     * 设置是否可点击
     *
     * @param clickEnable
     */
    public void setClickEnable(boolean clickEnable) {
        isClickEnable = clickEnable;
        imageAdapter.setClick(clickEnable);
        imageAdapter.notifyDataSetChanged();
    }


    public CustomSelectImageView(Context context) {
        super(context);
        initAttrs(context, null);
        init();
    }

    public CustomSelectImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public CustomSelectImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        mContext = context;
        if (mContext instanceof Activity) {
            mActivity = (Activity) mContext;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSelectImageView);
        gv_horizontalSpacing = typedArray.getDimension(R.styleable.CustomSelectImageView_csiv_horizontalSpacing, 0);
        gv_verticalSpacing = typedArray.getDimension(R.styleable.CustomSelectImageView_csiv_verticalSpacing, 0);
        isClickEnable = typedArray.getBoolean(R.styleable.CustomSelectImageView_csiv_is_click, true);
        iv_add_src = typedArray.getResourceId(R.styleable.CustomSelectImageView_csiv_add_src, R.drawable.ic_add_photo);
        iv_del_src = typedArray.getResourceId(R.styleable.CustomSelectImageView_csiv_del_src, R.drawable.ic_photo_del);
        maxPhoto = typedArray.getInt(R.styleable.CustomSelectImageView_csiv_max_photo, 9);
        numColumns = typedArray.getInt(R.styleable.CustomSelectImageView_csiv_columns_num, 3);
        isCircular = typedArray.getBoolean(R.styleable.CustomSelectImageView_csiv_is_circular, true);
        selectMode = typedArray.getInt(R.styleable.CustomSelectImageView_csiv_mode, 2);
        cropMode = typedArray.getInt(R.styleable.CustomSelectImageView_csiv_cropmode, 2);
        isCrop = typedArray.getBoolean(R.styleable.CustomSelectImageView_csiv_is_crop, false);
        typedArray.recycle();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_custom_select_image, this, true);
        selectImageList = new ArrayList<>();
        imageAdapter = new UpdateImageAdapter(mContext, selectImageList, maxPhoto, numColumns, iv_del_src, iv_add_src, gv_horizontalSpacing, isCircular, this, isClickEnable);
        gv_image = (CustomerGridView) findViewById(R.id.gv_img);
        gv_image.setNumColumns(numColumns);
        gv_image.setAdapter(imageAdapter);
        gv_image.setHorizontalSpacing((int) gv_horizontalSpacing);
        gv_image.setVerticalSpacing((int) gv_verticalSpacing);
        if (cropMode == 1) {
            aspect_ratio_x = 1;
            aspect_ratio_y = 1;
        } else {
            aspect_ratio_x = 6;
            aspect_ratio_y = 4;
        }
        setOnItemClick();
    }

    private void setOnItemClick() {
        gv_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == selectImageList.size()) {
                    if (isClickEnable) {
                        // 进入相册 以下是例子：用不到的api可以不写
                        PictureSelector.create(mActivity)
                                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                                .maxSelectNum(maxPhoto - selectImageList.size())// 最大图片选择数量 int
                                .imageSpanCount(4)// 每行显示个数 int
                                .selectionMode(selectMode)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                .previewImage(true)// 是否可预览图片 true or false
                                .isCamera(true)// 是否显示拍照按钮 true or false
                                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                .enableCrop(isCrop)
                                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)
                                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                .isGif(false)// 是否显示gif图片 true or false
                                .openClickSound(false)// 是否开启点击声音 true or false
                                .minimumCompressSize(100)// 小于100kb的图片不压缩
                                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                                .compress(true)// 是否压缩
                                .forResult(requestCode);//结果回调onActivityResult code
                    }
                } else {
                    List<ImageInfo> list = new ArrayList<>();
                    for (UpdatePhotoInfo info : selectImageList) {
                        ImageInfo imageInfo = new ImageInfo();
                        imageInfo.setBigImageUrl(info.localPath);
                        list.add(imageInfo);
                    }
                    BaseGoto.toImagePreviewActivity(mContext, list, position);
                }
            }
        });
    }


    @Override
    public void delClick(int position) {
        if (selectImageList.size() > 0 && selectImageList.size() >= position) {
            selectImageList.remove(position);
            imageAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置照片
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == this.requestCode) {
            List<LocalMedia> pathList = PictureSelector.obtainMultipleResult(data);
            if (pathList != null && pathList.size() != 0) {
                for (int i = 0; i < pathList.size(); i++) {
                    LocalMedia localMedia = pathList.get(i);
                    UpdatePhotoInfo photoInfo = new UpdatePhotoInfo();
                    if (localMedia.isCompressed())
                        photoInfo.localPath = localMedia.getCompressPath();
                    else
                        photoInfo.localPath = localMedia.getPath();
                    photoInfo.photoSize = (int) (FileUtil.getInstance().getFileSizeL(mActivity, photoInfo.localPath) / 1024);
                    if (photoInfo.photoSize == 0) {
                        Toasty.warning(mActivity, "所选图片已损坏").show();
                        break;
                    }
                    selectImageList.add(photoInfo);
                }
                imageAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 设置网络照片
     *
     * @param imgUrl
     */
    public void setSelectList(List<UpdatePhotoInfo> imgUrl) {
        if (imgUrl.size() > 0) {
            selectImageList.addAll(imgUrl);
            imageAdapter.notifyDataSetChanged();
        }
    }

    public List<UpdatePhotoInfo> getSelectImageList() {
        return selectImageList;
    }

    public void setStringList(List<String> imgUrls) {
        if (imgUrls.size() > 0) {
            for (String imgUrl : imgUrls)
                selectImageList.add(new UpdatePhotoInfo(imgUrl));
            imageAdapter.notifyDataSetChanged();
        }
    }
	public void setStringUrl(String imgUrl) {
        selectImageList.add(new UpdatePhotoInfo(imgUrl));
        imageAdapter.notifyDataSetChanged();
    }
   public void clearAll() {
        selectImageList.clear();
        imageAdapter.notifyDataSetChanged();
    }
    public List<String> getSelectsImageList() {
        List<String> sImages = new ArrayList<>();
        if (selectImageList.size() != 0)
            for (UpdatePhotoInfo info : selectImageList) {
                sImages.add(info.localPath);
            }
        return sImages;
    }
	  public String getSelectsImage() {
        if (selectImageList.size() != 0)
              return selectImageList.get(0).localPath;
        return "";
    }
}