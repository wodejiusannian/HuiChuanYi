package com.example.huichuanyi.common_view.Type;

import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.bean.HistotyZhenDuan;
import com.example.huichuanyi.common_view.holder.BaseViewHolder;
import com.example.huichuanyi.common_view.holder.HistoryHolder;
import com.example.huichuanyi.common_view.holder.LyBanerHolder;
import com.example.huichuanyi.common_view.holder.LyCommendPeopleHolder;
import com.example.huichuanyi.common_view.holder.LyIndentOverHolder;
import com.example.huichuanyi.common_view.holder.LyIndentOverScrollHolder;
import com.example.huichuanyi.common_view.holder.LyItemShopHolder;
import com.example.huichuanyi.common_view.holder.LyItemSortHolder;
import com.example.huichuanyi.common_view.holder.LyItemTestHolder;
import com.example.huichuanyi.common_view.holder.LyListShopHolder;
import com.example.huichuanyi.common_view.holder.LyMainHolder;
import com.example.huichuanyi.common_view.holder.LyRTCReportHolder;
import com.example.huichuanyi.common_view.holder.LyShopCarHolder;
import com.example.huichuanyi.common_view.holder.OrderStudioFillHolder;
import com.example.huichuanyi.common_view.holder.OrderStudioOneHolder;
import com.example.huichuanyi.common_view.holder.OrderStudioThreeHolder;
import com.example.huichuanyi.common_view.holder.OrderStudioTwoHolder;
import com.example.huichuanyi.common_view.holder.PrivateRecommendHolder;
import com.example.huichuanyi.common_view.holder.SlwEightHolder;
import com.example.huichuanyi.common_view.holder.SlwFiveHolder;
import com.example.huichuanyi.common_view.holder.SlwFourHolder;
import com.example.huichuanyi.common_view.holder.SlwOneHolder;
import com.example.huichuanyi.common_view.holder.SlwSevenHolder;
import com.example.huichuanyi.common_view.holder.SlwSixHolder;
import com.example.huichuanyi.common_view.holder.SlwThreeHolder;
import com.example.huichuanyi.common_view.holder.SlwTwoHolder;
import com.example.huichuanyi.common_view.model.LyBanner;
import com.example.huichuanyi.common_view.model.LyCommendPeople;
import com.example.huichuanyi.common_view.model.LyItemShop;
import com.example.huichuanyi.common_view.model.LyItemSort;
import com.example.huichuanyi.common_view.model.LyListIndent;
import com.example.huichuanyi.common_view.model.LyListIndentScroll;
import com.example.huichuanyi.common_view.model.LyMain;
import com.example.huichuanyi.common_view.model.LyShopCar;
import com.example.huichuanyi.common_view.model.LyShopList;
import com.example.huichuanyi.common_view.model.LyTest;
import com.example.huichuanyi.common_view.model.OrderStudioFill;
import com.example.huichuanyi.common_view.model.OrderStudioOne;
import com.example.huichuanyi.common_view.model.OrderStudioThree;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel;
import com.example.huichuanyi.common_view.model.RTCReport;
import com.example.huichuanyi.common_view.model.SlwEightModel;
import com.example.huichuanyi.common_view.model.SlwFiveModel;
import com.example.huichuanyi.common_view.model.SlwFourModel;
import com.example.huichuanyi.common_view.model.SlwOneModel;
import com.example.huichuanyi.common_view.model.SlwSevenModle;
import com.example.huichuanyi.common_view.model.SlwSixModel;
import com.example.huichuanyi.common_view.model.SlwThreeModel;
import com.example.huichuanyi.common_view.model.SlwTwoModel;


/**
 * Created by yq05481 on 2016/12/30.
 */

public class TypeFactoryForList implements TypeFactory {

    private final int TYPE_RESOURCE_BANNER = R.layout.item_ly_banner;

    private final int TYPE_RESOURCE_ITEM_SHOP = R.layout.item_ly_shop;

    private final int TYPE_RESOURCE_ITEM_SORT = R.layout.item_ly_sort;

    private final int TYPE_RESOURCE_ITEM_LIST_SHOP = R.layout.item_ly_list_shop;

    private final int TYPE_RESOURCE_ITEM_SHOP_CAR = R.layout.item_ly_shop_car;

    private final int TYPE_RESOURCE_ITEM_COMMEND_PEOPLE = R.layout.item_ly_reommend_people;

    private final int TYPE_RESOURCE_ITEM_LIST_INDENT_OVER = R.layout.item_ly_list_indent;

    private final int TYPE_RESOURCE_ITEM_LIST_INDENT_OVER_SCROLL = R.layout.item_ly_list_indent_scroll;

    private final int TYPE_RESOURCE_ITEM_TEST = R.layout.item_ly_list_no_select;

    private final int TYPE_RESOURCE_LY_MAIN = R.layout.item_recycle_ly_main;

    private final int TYPE_RESOURCE_LY_RTC_REPORT = R.layout.item_recycle_rtc_report;

    private final int TYPE_RESOURCE_365_ITEM_1 = R.layout.item_365_one;

    private final int TYPE_RESOURCE_365_ITEM_2 = R.layout.item_365_two;

    private final int TYPE_RESOURCE_365_ITEM_3 = R.layout.item_365_three;

    private final int TYPE_RESOURCE_365_ITEM_4 = R.layout.item_365_four;

    private final int TYPE_RESOURCE_365_ITEM_5 = R.layout.item_365_five;

    private final int TYPE_RESOURCE_365_ITEM_6 = R.layout.item_365_six;

    private final int TYPE_RESOURCE_365_ITEM_7 = R.layout.item_365_serven;

    private final int TYPE_RESOURCE_365_ITEM_8 = R.layout.item_365_eight;

    private final int TYPE_RESOURCE_PRIVATE_RECOMMEND = R.layout.item_test;

    private final int TYPE_RESOURCE_PRIVATE_HISTORY_ZHEN_DUAN = R.layout.item_history;

    private final int TYPE_RESOURCE_ORDER_STUDIO_ONE = R.layout.item_order_studio_one;

    private final int TYPE_RESOURCE_ORDER_STUDIO_TWO = R.layout.item_order_studio_normal;

    private final int TYPE_RESOURCE_ORDER_STUDIO_THREE = R.layout.item_bottom;

    private final int TYPE_RESOURCE_ORDER_STUDIO_FILL = R.layout.item_fill;

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
    public int type(LyMain.BodyBean bean) {
        return TYPE_RESOURCE_LY_MAIN;
    }

    @Override
    public int type(RTCReport bean) {
        return TYPE_RESOURCE_LY_RTC_REPORT;
    }

    @Override
    public int type(SlwOneModel slwOneModel) {
        return TYPE_RESOURCE_365_ITEM_1;
    }

    @Override
    public int type(SlwTwoModel slwTwoModel) {
        return TYPE_RESOURCE_365_ITEM_2;
    }

    @Override
    public int type(SlwThreeModel slwThreeModel) {
        return TYPE_RESOURCE_365_ITEM_3;
    }

    @Override
    public int type(SlwFourModel slwFourModel) {
        return TYPE_RESOURCE_365_ITEM_4;
    }

    @Override
    public int type(SlwFiveModel slwFiveModel) {
        return TYPE_RESOURCE_365_ITEM_5;
    }

    @Override
    public int type(SlwSixModel slwSixModel) {
        return TYPE_RESOURCE_365_ITEM_6;
    }

    @Override
    public int type(SlwSevenModle slwSevenModle) {
        return TYPE_RESOURCE_365_ITEM_7;
    }

    @Override
    public int type(OrderStudioOne slwSevenModle) {
        return TYPE_RESOURCE_ORDER_STUDIO_ONE;
    }

    @Override
    public int type(City.BodyBean slwSevenModle) {
        return TYPE_RESOURCE_ORDER_STUDIO_TWO;
    }

    @Override
    public int type(OrderStudioThree slwSevenModle) {
        return TYPE_RESOURCE_ORDER_STUDIO_THREE;
    }

    @Override
    public int type(PrivateRecommendModel privateRecommendModel) {
        return TYPE_RESOURCE_PRIVATE_RECOMMEND;
    }

    @Override
    public int type(SlwEightModel privateRecommendModel) {
        return TYPE_RESOURCE_365_ITEM_8;
    }

    @Override
    public int type(HistotyZhenDuan histotyZhenDuan) {
        return TYPE_RESOURCE_PRIVATE_HISTORY_ZHEN_DUAN;
    }

    @Override
    public int type(OrderStudioFill histotyZhenDuan) {
        return TYPE_RESOURCE_ORDER_STUDIO_FILL;
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
            case TYPE_RESOURCE_LY_MAIN:
                return new LyMainHolder(itemView);
            case TYPE_RESOURCE_LY_RTC_REPORT:
                return new LyRTCReportHolder(itemView);
            case TYPE_RESOURCE_365_ITEM_1:
                return new SlwOneHolder(itemView);
            case TYPE_RESOURCE_365_ITEM_2:
                return new SlwTwoHolder(itemView);
            case TYPE_RESOURCE_365_ITEM_3:
                return new SlwThreeHolder(itemView);
            case TYPE_RESOURCE_365_ITEM_4:
                return new SlwFourHolder(itemView);
            case TYPE_RESOURCE_365_ITEM_5:
                return new SlwFiveHolder(itemView);
            case TYPE_RESOURCE_365_ITEM_6:
                return new SlwSixHolder(itemView);
            case TYPE_RESOURCE_PRIVATE_RECOMMEND:
                return new PrivateRecommendHolder(itemView);
            case TYPE_RESOURCE_365_ITEM_7:
                return new SlwSevenHolder(itemView);
            case TYPE_RESOURCE_365_ITEM_8:
                return new SlwEightHolder(itemView);
            case TYPE_RESOURCE_PRIVATE_HISTORY_ZHEN_DUAN:
                return new HistoryHolder(itemView);
            case TYPE_RESOURCE_ORDER_STUDIO_ONE:
                return new OrderStudioOneHolder(itemView);
            case TYPE_RESOURCE_ORDER_STUDIO_TWO:
                return new OrderStudioTwoHolder(itemView);
            case TYPE_RESOURCE_ORDER_STUDIO_THREE:
                return new OrderStudioThreeHolder(itemView);
            case TYPE_RESOURCE_ORDER_STUDIO_FILL:
                return new OrderStudioFillHolder(itemView);
            default:
                return null;
        }
    }
}
