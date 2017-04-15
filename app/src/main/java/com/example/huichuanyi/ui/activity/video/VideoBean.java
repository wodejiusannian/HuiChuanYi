package com.example.huichuanyi.ui.activity.video;

import java.io.Serializable;

public class VideoBean implements Serializable {

    private String pic_get;
    private String video_url;
    private String introduce;
    private int type;
    private String cover_id;
    private String total_price;

    public String getPic_get() {
        return pic_get;
    }

    public void setPic_get(String pic_get) {
        this.pic_get = pic_get;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCover_id() {
        return cover_id;
    }

    public void setCover_id(String cover_id) {
        this.cover_id = cover_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
