package com.example.huichuanyi.bean;

import com.example.huichuanyi.common_view.Type.TypeFactory;
import com.example.huichuanyi.common_view.model.Visitable;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class HistotyZhenDuan implements Visitable {


    /**
     * id : 1
     * time : 2017-06-30 17:46:41
     * sj_advice : 你是猪吗？
     * sw_advice : 你是猪吗？
     * xx_advice : 另类你是猪吗？
     * sj_zd : 你是猪吗？
     * sw_zd : 你是猪吗？
     * xx_zd : 校园你是猪吗？
     */

    private String id;
    private String time;
    private String sj_advice;
    private String sw_advice;
    private String xx_advice;
    private String sj_zd;
    private String sw_zd;
    private String xx_zd;
    private String picUrl;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSj_advice() {
        return sj_advice;
    }

    public void setSj_advice(String sj_advice) {
        this.sj_advice = sj_advice;
    }

    public String getSw_advice() {
        return sw_advice;
    }

    public void setSw_advice(String sw_advice) {
        this.sw_advice = sw_advice;
    }

    public String getXx_advice() {
        return xx_advice;
    }

    public void setXx_advice(String xx_advice) {
        this.xx_advice = xx_advice;
    }

    public String getSj_zd() {
        return sj_zd;
    }

    public void setSj_zd(String sj_zd) {
        this.sj_zd = sj_zd;
    }

    public String getSw_zd() {
        return sw_zd;
    }

    public void setSw_zd(String sw_zd) {
        this.sw_zd = sw_zd;
    }

    public String getXx_zd() {
        return xx_zd;
    }

    public void setXx_zd(String xx_zd) {
        this.xx_zd = xx_zd;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
