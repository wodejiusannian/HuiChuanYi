package com.example.huichuanyi.common_view.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeAdapter;
import com.example.huichuanyi.bean.Banner;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.SlwTwoModel;
import com.example.huichuanyi.secondui.AtMyAcitivty;
import com.example.huichuanyi.ui.activity.HMWebActivity;
import com.example.huichuanyi.ui.activity.HomeDaPeiRiJiActivity;
import com.example.huichuanyi.ui.activity.MyOrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class SlwTwoHolder extends BaseViewHolder<SlwTwoModel> {

    public SlwTwoHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(SlwTwoModel model, int position, MultiTypeAdapter adapter) {
        final List<Banner> data = model.data;
        RollPagerView pagerView = (RollPagerView) getView(R.id.roll_item_365_two_banner);
        final Context context = pagerView.getContext();
        HomeAdapter mAdapter = new HomeAdapter(pagerView, data, context);
        pagerView.setAdapter(mAdapter);
        pagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Banner banner = data.get(position);
                String type = banner.getType();
                switch (type) {
                    case "2":
                        Map<String, Object> map = new HashMap<>();
                        String web_url = banner.getWeb_url();
                        String share_name = banner.getShare_name();
                        String share_url = banner.getShare_url();
                        map.put("hm_adpage_webview_url", web_url);
                        map.put("hm_activity_name", share_name);
                        map.put("hm_adpage_share_url", share_url);
                        ActivityUtils.switchTo((Activity) context, HMWebActivity.class, map);
                        break;
                    case "5":
                        ActivityUtils.switchTo((Activity) context, MyOrderActivity.class);
                        break;
                    case "3":
                        ActivityUtils.switchTo((Activity) context, HomeDaPeiRiJiActivity.class);
                        break;
                    case "1":
                        break;
                    case "4":
                        ActivityUtils.switchTo((Activity) context, AtMyAcitivty.class);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
