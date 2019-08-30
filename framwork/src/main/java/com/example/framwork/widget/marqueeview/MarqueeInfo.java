package com.example.framwork.widget.marqueeview;

/**
 * Created by lenovo on 2017/2/23.
 * 跑马灯info
 */

public class MarqueeInfo {
    private String title ;//跑马灯内容
    private boolean isNews;//是否显示最新tag
    private String article_id;
    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isNews() {
        return isNews;
    }

    public void setNews(boolean news) {
        isNews = news;
    }
}
