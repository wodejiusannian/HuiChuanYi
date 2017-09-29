package com.example.huichuanyi.common_view.Type;

import android.view.View;

import com.example.huichuanyi.common_view.holder.BaseViewHolder;
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

    BaseViewHolder createViewHolder(int type, View itemView);
}
