package com.qiuyixiao.bean;
public class ImgBean {
    private String title;
    private String thumburl;
    private String sourceurl;
    private String height;
    private String width;

    @Override
    public String toString() {
        return "ImgBean{" +
                "title='" + title + '\'' +
                ", thumburl='" + thumburl + '\'' +
                ", sourceurl='" + sourceurl + '\'' +
                ", height='" + height + '\'' +
                ", width='" + width + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumburl() {
        return thumburl;
    }

    public void setThumburl(String thumburl) {
        this.thumburl = thumburl;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
