package com.example.framwork.banner;

import java.io.Serializable;

public abstract class BannerItem implements Serializable {
    public abstract String getImgUrl();

    public abstract String getTitle();

}
