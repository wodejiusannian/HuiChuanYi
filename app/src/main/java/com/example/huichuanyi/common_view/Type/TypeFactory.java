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
import com.example.huichuanyi.common_view.model.OrderFormOrder;
import com.example.huichuanyi.common_view.model.OrderFormSLW;
import com.example.huichuanyi.common_view.model.OrderFormVideo;
import com.example.huichuanyi.common_view.model.OrderStudioFill;
import com.example.huichuanyi.common_view.model.OrderStudioIntroduceOpenModel;
import com.example.huichuanyi.common_view.model.OrderStudioIntroducePJBottomModel;
import com.example.huichuanyi.common_view.model.OrderStudioIntroducePJMiddleModel;
import com.example.huichuanyi.common_view.model.OrderStudioIntroducePJTopModel;
import com.example.huichuanyi.common_view.model.OrderStudioIntroduceSecondModel;
import com.example.huichuanyi.common_view.model.OrderStudioIntroduceThirdModel;
import com.example.huichuanyi.common_view.model.OrderStudioIntroduceTopModel;
import com.example.huichuanyi.common_view.model.OrderStudioOne;
import com.example.huichuanyi.common_view.model.OrderStudioThree;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel;
import com.example.huichuanyi.common_view.model.RTCReport;
import com.example.huichuanyi.common_view.model.ShopCarButtonModel;
import com.example.huichuanyi.common_view.model.ShopCarTopModel;
import com.example.huichuanyi.common_view.model.ShopCarType0Model;
import com.example.huichuanyi.common_view.model.ShopCarType1Model;
import com.example.huichuanyi.common_view.model.ShopCarType2Model;
import com.example.huichuanyi.common_view.model.ShopCarType3Model;
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

    int type(ShopCarTopModel slwSixModel);

    int type(SlwSevenModle slwSevenModle);

    int type(OrderStudioOne slwSevenModle);

    int type(City.BodyBean slwSevenModle);

    int type(OrderStudioThree slwSevenModle);

    int type(PrivateRecommendModel privateRecommendModel);

    int type(SlwEightModel privateRecommendModel);

    int type(HistotyZhenDuan histotyZhenDuan);

    int type(OrderStudioIntroduceTopModel histotyZhenDuan);

    int type(OrderStudioIntroduceSecondModel histotyZhenDuan);

    int type(OrderStudioIntroduceThirdModel histotyZhenDuan);

    int type(OrderStudioIntroducePJTopModel histotyZhenDuan);

    int type(OrderStudioIntroducePJMiddleModel histotyZhenDuan);

    int type(OrderStudioIntroducePJBottomModel histotyZhenDuan);

    int type(OrderStudioIntroduceOpenModel histotyZhenDuan);

    int type(OrderStudioFill histotyZhenDuan);

    int type(ShopCarButtonModel histotyZhenDuan);

    int type(ShopCarType0Model histotyZhenDuan);

    int type(ShopCarType1Model histotyZhenDuan);

    int type(ShopCarType2Model histotyZhenDuan);

    int type(ShopCarType3Model histotyZhenDuan);

    int type(OrderFormOrder histotyZhenDuan);

    int type(OrderFormSLW histotyZhenDuan);

    int type(OrderFormVideo histotyZhenDuan);
    //int type(OrderStudioHoz histotyZhenDuan);

    BaseViewHolder createViewHolder(int type, View itemView);
}
