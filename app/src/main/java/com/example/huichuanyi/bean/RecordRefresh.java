package com.example.huichuanyi.bean;

import java.util.List;

/**
 * Created by 小五 on 2017/2/21.
 */

public class RecordRefresh {
    private String time;
    private List<RefreshBean> list;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<RefreshBean> getList() {
        return list;
    }

    public void setList(List<RefreshBean> list) {
        this.list = list;
    }

    public static class RefreshBean {
        private String clothes_get;

        public String getClothes_get() {
            return clothes_get;
        }

        public void setClothes_get(String clothes_get) {
            this.clothes_get = clothes_get;
        }
    }
}
