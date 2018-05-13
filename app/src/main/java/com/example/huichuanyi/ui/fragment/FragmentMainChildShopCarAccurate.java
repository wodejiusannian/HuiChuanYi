package com.example.huichuanyi.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.HomeAdapter;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.bean.Banner;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.activity.HMWebActivity;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.OverLayCardLayoutManager;
import com.example.huichuanyi.utils.RenRenCallback;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

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
public class FragmentMainChildShopCarAccurate extends BaseFragment {

    @BindView(R.id.rv_private_recommend_content)
    RecyclerView content;

    private MultiTypeAdapter mAdapter;

    private List<Visitable> mData = new ArrayList<>();

    private RenRenCallback callback = new RenRenCallback();

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = (int) v.getTag();
            }
        });

        banner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Banner banner = mBanner.get(position);
                String type = banner.getType();
                switch (type) {
                    case "1":
                        Intent intent = new Intent(getContext(), HMWebActivity.class);
                        String web_url = banner.getWeb_url();
                        String share_name = banner.getShare_name();
                        String share_url = banner.getShare_url();
                        intent.putExtra("hm_adpage_webview_url", web_url);
                        intent.putExtra("hm_activity_name", share_name);
                        intent.putExtra("hm_adpage_share_url", share_url);
                        getContext().startActivity(intent);
                        break;
                }
            }
        });
        callback.setSwipeListener(new RenRenCallback.OnSwipeListener() {
            @Override
            public void onSwiped(int adapterPosition, int direction) {

              /*  if (direction == ItemTouchHelper.DOWN || direction == ItemTouchHelper.UP) {
                    mData.add(0, mData.remove(adapterPosition));
                } else {

                    if (direction == ItemTouchHelper.RIGHT) {
                        mData.add(0, mData.remove(adapterPosition));
                    } else {
                        Toast.makeText(getContext(), "我不喜欢这个", Toast.LENGTH_SHORT).show();
                    }
                    // mData.remove(adapterPosition);
                }*/
                mData.add(0, mData.remove(adapterPosition));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSwipeTo(RecyclerView.ViewHolder viewHolder, float offset) {
                /*if (offset < -50) {
                    viewHolder.itemView.findViewById(R.id.like).setVisibility(View.VISIBLE);
                    viewHolder.itemView.findViewById(R.id.not_like).setVisibility(View.INVISIBLE);
                } else if (offset > 50) {
                    viewHolder.itemView.findViewById(R.id.like).setVisibility(View.INVISIBLE);
                    viewHolder.itemView.findViewById(R.id.not_like).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.itemView.findViewById(R.id.like).setVisibility(View.INVISIBLE);
                    viewHolder.itemView.findViewById(R.id.not_like).setVisibility(View.INVISIBLE);
                }*/
            }
        });
        new ItemTouchHelper(callback).attachToRecyclerView(content);
    }

    @Override
    protected void initData() {
        super.initData();
        List<Fragment> fragments = getFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            String fragmentName = fragment.getClass().getSimpleName();
            if ("FragmentMainShopCar".equals(fragmentName)) {
                shopCar = (FragmentMainShopCar) fragment;
            }
        }

        mAdapter = new MultiTypeAdapter(mData);
        content.setLayoutManager(new OverLayCardLayoutManager(getContext()));
        content.setAdapter(mAdapter);
    }

    private FragmentMainShopCar shopCar;

    private HomeAdapter ada;

    @Override
    protected void setData() {
        super.setData();
        ada = new HomeAdapter(banner, mBanner, getContext());
        banner.setAdapter(ada);
        RequestParams pa = new RequestParams(NetConfig.SHOPCAR_MANAGER_RECOMMEND);
        pa.addBodyParameter("buyUserId", SharedPreferenceUtils.getUserData(getContext(), 1));
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "onSuccess: -------" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray body = object.getJSONArray("body");
                    for (int i = 0; i < body.length(); i++) {
                        JSONObject obj = body.getJSONObject(i);
                        PrivateRecommendModel privateRecommendModel = new PrivateRecommendModel();
                        privateRecommendModel.setColor_name(obj.getString("goodsColor"));
                        privateRecommendModel.setClothes_name(obj.getString("goodsName"));
                        privateRecommendModel.setClothes_get(obj.getString("goodsPicture"));
                        privateRecommendModel.setPrice_dj(obj.getString("goodsPrice"));
                        privateRecommendModel.setSize_name(obj.getString("goodsSize"));
                        privateRecommendModel.setSize_get(obj.getString("goodsSize"));
                        privateRecommendModel.setId(obj.getString("id"));
                        privateRecommendModel.setRecommend_time(obj.getString("recommendDate"));
                        privateRecommendModel.setReason(obj.getString("recommendReason"));
                        privateRecommendModel.setRecommend_id(obj.getString("recommendUserName"));
                        mData.add(i, privateRecommendModel);
                    }
                    initBanner();
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @BindView(R.id.relative_all)
    RelativeLayout all;

    @BindView(R.id.rv_shopcar_advertisement)
    RollPagerView banner;
    private MUtilsInternet net = MUtilsInternet.getInstance();

    private void initBanner() {
        Map<String, String> map = new HashMap<>();
        map.put("bannerType", "3");
        net.post(NetConfig.BANNER_TYPE, getContext(), map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray banners = object.getJSONArray("body");
                    for (int i = 0; i < banners.length(); i++) {
                        JSONObject jsonObject = banners.getJSONObject(i);
                        Banner banner = new Banner();
                        banner.setShare_name(jsonObject.getString("bannerName"));
                        banner.setShare_url(jsonObject.getString("shareUrl"));
                        banner.setWeb_url(jsonObject.getString("clickUrl"));
                        banner.setType(jsonObject.getString("clickType"));
                        banner.setPic_url(NetConfig.BASE_NEW_URL + jsonObject.getString("pictureUrl"));
                        mBanner.add(banner);
                    }
                    if (mBanner.size() == 1) {
                        banner.setHintView(new ColorPointHintView(getActivity(), Color.parseColor("#00ac0000"), Color.parseColor("#00ac0000")));
                    } else {
                        banner.setHintView(new ColorPointHintView(getActivity(), Color.parseColor("#ac0000"), Color.WHITE));
                        banner.setPlayDelay(4000);
                    }
                    ada.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    List<Banner> mBanner = new ArrayList<Banner>();

    @OnClick({R.id.tv_mainshopcar_self})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_mainshopcar_self:
                shopCar.setChange();
                break;
            default:
                break;
        }
    }


    @Override
    protected int layoutInflaterId() {
        return R.layout.fragment_mainchildshopcar_accurate;
    }
}
