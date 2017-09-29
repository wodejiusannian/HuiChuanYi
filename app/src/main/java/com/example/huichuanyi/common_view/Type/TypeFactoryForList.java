package com.example.huichuanyi.common_view.Type;

import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.holder.BaseViewHolder;
import com.example.huichuanyi.common_view.holder.LyBanerHolder;
import com.example.huichuanyi.common_view.holder.LyCommendPeopleHolder;
import com.example.huichuanyi.common_view.holder.LyIndentOverHolder;
import com.example.huichuanyi.common_view.holder.LyIndentOverScrollHolder;
import com.example.huichuanyi.common_view.holder.LyItemShopHolder;
import com.example.huichuanyi.common_view.holder.LyItemSortHolder;
import com.example.huichuanyi.common_view.holder.LyItemTestHolder;
import com.example.huichuanyi.common_view.holder.LyListShopHolder;
import com.example.huichuanyi.common_view.holder.LyShopCarHolder;
import com.example.huichuanyi.common_view.model.LyBanner;
import com.example.huichuanyi.common_view.model.LyCommendPeople;
import com.example.huichuanyi.common_view.model.LyItemShop;
import com.example.huichuanyi.common_view.model.LyItemSort;
import com.example.huichuanyi.common_view.model.LyListIndent;
import com.example.huichuanyi.common_view.model.LyListIndentScroll;
import com.example.huichuanyi.common_view.model.LyShopCar;
import com.example.huichuanyi.common_view.model.LyShopList;
import com.example.huichuanyi.common_view.model.LyTest;


/**
 * Created by yq05481 on 2016/12/30.
 */

public class TypeFactoryForList implements TypeFactory {
    private static final String TAG = "TypeFactoryForList";

    private final int TYPE_RESOURCE_BANNER = R.layout.item_ly_banner;

    private final int TYPE_RESOURCE_ITEM_SHOP = R.layout.item_ly_shop;

    private final int TYPE_RESOURCE_ITEM_SORT = R.layout.item_ly_sort;

    private final int TYPE_RESOURCE_ITEM_LIST_SHOP = R.layout.item_ly_list_shop;

    private final int TYPE_RESOURCE_ITEM_SHOP_CAR = R.layout.item_ly_shop_car;

    private final int TYPE_RESOURCE_ITEM_COMMEND_PEOPLE = R.layout.item_ly_reommend_people;

    private final int TYPE_RESOURCE_ITEM_LIST_INDENT_OVER = R.layout.item_ly_list_indent;

    private final int TYPE_RESOURCE_ITEM_LIST_INDENT_OVER_SCROLL = R.layout.item_ly_list_indent_scroll;

    private final int TYPE_RESOURCE_ITEM_TEST = R.layout.item_ly_list_no_select;

    @Override
    public int type(LyBanner one) {
        return TYPE_RESOURCE_BANNER;
    }

    @Override
    public int type(LyItemShop lyItemShop) {
        return TYPE_RESOURCE_ITEM_SHOP;
    }

    @Override
    public int type(LyItemSort lyItemSort) {
        return TYPE_RESOURCE_ITEM_SORT;
    }

    @Override
    public int type(LyShopList.BodyBean lyShopList) {
        return TYPE_RESOURCE_ITEM_LIST_SHOP;
    }

    @Override
    public int type(LyListIndent bean) {
        return TYPE_RESOURCE_ITEM_LIST_INDENT_OVER;
    }

    @Override
    public int type(LyTest test) {
        return TYPE_RESOURCE_ITEM_TEST;
    }

    @Override
    public int type(LyListIndentScroll bean) {
        return TYPE_RESOURCE_ITEM_LIST_INDENT_OVER_SCROLL;
    }

    @Override
    public int type(LyShopCar.BodyBean lyShopCar) {
        return TYPE_RESOURCE_ITEM_SHOP_CAR;
    }

    @Override
    public int type(LyCommendPeople.BodyBean lyCommendPeople) {
        return TYPE_RESOURCE_ITEM_COMMEND_PEOPLE;
    }


    @Override
    public BaseViewHolder createViewHolder(int type, View itemView) {

        switch (type) {
            case TYPE_RESOURCE_BANNER:
                return new LyBanerHolder(itemView);
            case TYPE_RESOURCE_ITEM_SHOP:
                return new LyItemShopHolder(itemView);
            case TYPE_RESOURCE_ITEM_SORT:
                return new LyItemSortHolder(itemView);
            case TYPE_RESOURCE_ITEM_LIST_SHOP:
                return new LyListShopHolder(itemView);
            case TYPE_RESOURCE_ITEM_SHOP_CAR:
                return new LyShopCarHolder(itemView);
            case TYPE_RESOURCE_ITEM_COMMEND_PEOPLE:
                return new LyCommendPeopleHolder(itemView);
            case TYPE_RESOURCE_ITEM_LIST_INDENT_OVER:
                return new LyIndentOverHolder(itemView);
            case TYPE_RESOURCE_ITEM_LIST_INDENT_OVER_SCROLL:
                return new LyIndentOverScrollHolder(itemView);
            case TYPE_RESOURCE_ITEM_TEST:
                return new LyItemTestHolder(itemView);
            default:
                return null;
        }
    }
}
