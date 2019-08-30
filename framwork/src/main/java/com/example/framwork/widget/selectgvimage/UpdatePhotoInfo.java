package com.example.framwork.widget.selectgvimage;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/3/25.
 */

public class UpdatePhotoInfo implements Serializable {
    //网络地址
    public String photoPath;
    //本地地址
    public String localPath;
    //是否压缩过
    public boolean isCompress;
    //kb为单位
    public int photoSize;
    public boolean isOld;//是否是上一个页面带过来

    public UpdatePhotoInfo(String localPath) {
        this.localPath = localPath;
    }
    public UpdatePhotoInfo() {
    }
}
