package com.example.huichuanyi.modle;

import java.util.List;

/**
 * Created by 小五 on 2017/1/15.
 */
public class Pic {
    public String picPath;
    public String occ;
    public String sort;
    public String style;
    public String season;
    public String material;
    public String time;
    public String price;
    public String size;
    public String brand;
    public String location;
    public String clothes_styleId;
    public boolean isCheckOcc;
    public int tagOcc;
    public boolean isCheckSort;
    public int tagSort;
    public boolean isCheckStyle;
    public int tagStyle;
    public List<String> mListStyle;
    public List<String> mListStyleId;

    public List<String> getmListStyleId() {
        return mListStyleId;
    }

    public void setmListStyleId(List<String> mListStyleId) {
        this.mListStyleId = mListStyleId;
    }

    public String getClothes_styleId() {
        return clothes_styleId;
    }

    public void setClothes_styleId(String clothes_styleId) {
        this.clothes_styleId = clothes_styleId;
    }

    public boolean isCheckStyle() {
        return isCheckStyle;
    }

    public void setCheckStyle(boolean checkStyle) {
        isCheckStyle = checkStyle;
    }

    public int getTagStyle() {
        return tagStyle;
    }

    public void setTagStyle(int tagStyle) {
        this.tagStyle = tagStyle;
    }

    public boolean isCheckSort() {
        return isCheckSort;
    }

    public void setCheckSort(boolean checkSort) {
        isCheckSort = checkSort;
    }

    public int getTagSort() {
        return tagSort;
    }

    public void setTagSort(int tagSort) {
        this.tagSort = tagSort;
    }

    public boolean isCheckOcc() {
        return isCheckOcc;
    }

    public void setCheckOcc(boolean checkOcc) {
        isCheckOcc = checkOcc;
    }

    public int getTagOcc() {
        return tagOcc;
    }

    public void setTagOcc(int tagOcc) {
        this.tagOcc = tagOcc;
    }


    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getOcc() {
        return occ;
    }

    public void setOcc(String occ) {
        this.occ = occ;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getmListStyle() {
        return mListStyle;
    }

    public void setmListStyle(List<String> mListStyle) {
        this.mListStyle = mListStyle;
    }
}
