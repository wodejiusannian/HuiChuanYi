package com.example.huichuanyi.common_view.model;

import com.example.huichuanyi.common_view.Type.TypeFactory;

import java.io.Serializable;

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
public class PrivateRecommendModel2 implements Visitable, Serializable {


    public PrivateRecommendModel2() {
    }

    private String id;
    private String color;
    private String price_dj;
    private String introduction;
    private String clothes_get;
    private String size_name;
    private String reason;
    private String recommend_time;
    private String size_get;
    private String color_name;
    private String clothes_name;
    private String recommend_id;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getClothes_name() {
        return clothes_name;
    }

    public String getRecommend_id() {
        return recommend_id;
    }

    public void setRecommend_id(String recommend_id) {
        this.recommend_id = recommend_id;
    }

    public void setClothes_name(String clothes_name) {
        this.clothes_name = clothes_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice_dj() {
        return price_dj;
    }

    public void setPrice_dj(String price_dj) {
        this.price_dj = price_dj;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getClothes_get() {
        return clothes_get;
    }

    public void setClothes_get(String clothes_get) {
        this.clothes_get = clothes_get;
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRecommend_time() {
        return recommend_time;
    }

    public void setRecommend_time(String recommend_time) {
        this.recommend_time = recommend_time;
    }

    public String getSize_get() {
        return size_get;
    }

    public void setSize_get(String size_get) {
        this.size_get = size_get;
    }

    public String getColor_name() {
        return color_name;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
