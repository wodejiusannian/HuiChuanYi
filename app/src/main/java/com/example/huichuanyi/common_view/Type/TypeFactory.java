package com.example.huichuanyi.common_view.Type;

import android.view.View;

import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.bean.HistotyZhenDuan;
import com.example.huichuanyi.common_view.holder.BaseViewHolder;
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

public interface TypeFactory {

    int type(LyBanner one);

    int type(LyItemShop lyItemShop);

    int type(LyItemSort lyItemSort);

    int type(LyShopCar.BodyBean bodyBean);

    int type(LyCommendPeople.BodyBean lyCommendPeople);

    int type(LyShopList.BodyBean bodyBean);

    int type(LyListIndent bean);

    int type(LyTest test);

    int type(LyListIndentScroll bean);

    int type(LyMain.BodyBean bean);

    int type(RTCReport bean);

    int type(SlwOneModel slwOneModel);

    int type(SlwTwoModel slwTwoModel);

    int type(SlwThreeModel slwThreeModel);

    int type(SlwFourModel slwFourModel);

    int type(SlwFiveModel slwFiveModel);

    int type(SlwSixModel slwSixModel);

    int type(SlwSevenModle slwSevenModle);

    int type(OrderStudioOne slwSevenModle);

    int type(City.BodyBean slwSevenModle);

    int type(OrderStudioThree slwSevenModle);

    int type(PrivateRecommendModel privateRecommendModel);

    int type(SlwEightModel privateRecommendModel);

    int type(HistotyZhenDuan histotyZhenDuan);

    int type(OrderStudioFill histotyZhenDuan);

    BaseViewHolder createViewHolder(int type, View itemView);
}
