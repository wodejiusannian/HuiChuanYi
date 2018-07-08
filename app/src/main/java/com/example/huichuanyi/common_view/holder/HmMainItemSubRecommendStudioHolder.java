package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmMainRecommendStudio;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.GlideCircleTransform;
import com.example.huichuanyi.emum.ServiceType;
import com.example.huichuanyi.ui.newpage.OrderStudioIntroduceActivity;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.ServiceSingleUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 小五 on 2018/6/26.
 */

public class HmMainItemSubRecommendStudioHolder extends BaseViewHolder<ItemHmMainRecommendStudio.DataBean> {

    public HmMainItemSubRecommendStudioHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final ItemHmMainRecommendStudio.DataBean model, int position, MultiTypeAdapter adapter) {
        LinearLayout go = (LinearLayout) getView(R.id.ll_go);
        TextView level = (TextView) getView(R.id.tv_substudio_level);
        TextView name = (TextView) getView(R.id.iv_substudio_name);
        ImageView logo = (ImageView) getView(R.id.iv_substudio_logo);
        final Context context = level.getContext();
        level.setText(model.appExtensionIntro);
        name.setText(model.name);
        Glide.with(context).load(model.headPicture).bitmapTransform(new GlideCircleTransform(context)).into(logo);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test(context, model);
            }
        });


    }


    private void test(final Context context, final ItemHmMainRecommendStudio.DataBean model) {
        MUtilsInternet net = MUtilsInternet.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put("token", NetConfig.TOKEN);
        map.put("sellerUserId", model.userId);
        net.post2(NetConfig.HMSCROE_STUDIO, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject body = obj.getJSONObject("body");
                    City.BodyBean bodyBean = new City.BodyBean();
                    bodyBean.setUsername(model.userName);
                    bodyBean.setName(model.name);
                    bodyBean.setPf(body.getString("evaluateAverage"));
                    bodyBean.setXl(body.getString("orderNum"));
                    bodyBean.setMonthStar(model.monthStar);
                    bodyBean.setId(model.userId);
                    bodyBean.setGrade(model.level);
                    bodyBean.setIntroduction(model.introduction);
                    bodyBean.setPhoto_get(model.headPicture);
                    ServiceSingleUtils.getInstance().setServiceType(ServiceType.SERVICE_THE_DOOR);
                    Intent intent = new Intent(context, OrderStudioIntroduceActivity.class);
                    intent.putExtra("model", bodyBean);
                    context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
