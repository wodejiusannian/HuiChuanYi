package com.example.huichuanyi.common_view.model;


import com.example.huichuanyi.common_view.Type.TypeFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yq05481 on 2016/12/30.
 */

public class LyBanner implements Visitable {

    private List<item_1> clothesInfo;

    public List<item_1> getBanner() {
        return clothesInfo;
    }

    public void setClothesInfo(List<item_1> clothesInfo) {
        this.clothesInfo = clothesInfo;
    }

    public static class item_1 implements Serializable {

        private String id;
        private String pic_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
