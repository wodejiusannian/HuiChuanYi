package com.example.huichuanyi.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.bean.Banner;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ShopCarButtonModel;
import com.example.huichuanyi.common_view.model.ShopCarNoBodyModel;
import com.example.huichuanyi.common_view.model.ShopCarTopModel;
import com.example.huichuanyi.common_view.model.ShopCarType0Model;
import com.example.huichuanyi.common_view.model.ShopCarType1Model;
import com.example.huichuanyi.common_view.model.ShopCarType2Model;
import com.example.huichuanyi.common_view.model.ShopCarType3Model;
import com.example.huichuanyi.common_view.model.ShopCarType4Model;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.newpage.ShopCarOrderDetailsActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.AsyncHttpUtils;
import com.example.huichuanyi.utils.HttpCallBack;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
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
public class FragmentMainChildShopCarSelf extends BaseFragment {

    @BindView(R.id.refresh_view)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycle_view)
    RecyclerView content;

    private MultiTypeAdapter adapter;

    private List<Visitable> mData = new ArrayList<>();

    /*@BindView(R.id.iv_shopcar_allselct)
    ImageView ivSelect;*/

    private MUtilsInternet net = MUtilsInternet.getInstance();


    @Override
    protected void initData() {
        super.initData();
        adapter = new MultiTypeAdapter(mData);
        content.setLayoutManager(new LinearLayoutManager(getContext()));
        content.setAdapter(adapter);
        List<Fragment> fragments = getFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            String fragmentName = fragment.getClass().getSimpleName();
            if ("FragmentMainShopCar".equals(fragmentName)) {
                shopCar = (FragmentMainShopCar) fragment;
            }
        }
    }

    private void initBanner() {
        refreshLayout.setRefreshing(true);
        Map<String, String> map = new HashMap<>();
        map.put("bannerType", "3");
        net.post2(NetConfig.BANNER_TYPE, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    List<Banner> mBanner = new ArrayList<Banner>();
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
                    mData.clear();
                    refreshLayout.setRefreshing(false);
                    rlDelete.setVisibility(View.GONE);
                    mData.add(new ShopCarTopModel(mBanner));
                    initNet();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private FragmentMainShopCar shopCar;

    @BindView(R.id.ll_mainchildshopcar_button)
    LinearLayout mButton;


    @Override
    protected void initEvent() {
        super.initEvent();

        content.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                y += dy;
                if (y > topY) {
                    mButton.setVisibility(View.VISIBLE);
                } else {
                    mButton.setVisibility(View.GONE);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initBanner();
                refreshHandler.sendEmptyMessageDelayed(1, 5000);
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                List<Boolean> list = new ArrayList<Boolean>();
                double money = 0;
                for (Visitable visitable : mData) {
                    if (visitable instanceof ShopCarType2Model) {
                        ShopCarType2Model shop2 = ((ShopCarType2Model) visitable);
                        boolean check2 = shop2.isCheck;
                        list.add(check2);
                        if (check2) {
                            String strGoodsPrice2 = shop2.goodsPrice;
                            int intOrderNumber2 = shop2.orderNumber;
                            double douGoodsPrice2 = Double.parseDouble(strGoodsPrice2);
                            money += douGoodsPrice2 * intOrderNumber2;
                        }
                    } else if (visitable instanceof ShopCarType3Model) {
                        ShopCarType3Model shop3 = ((ShopCarType3Model) visitable);
                        boolean check3 = shop3.isCheck;
                        list.add(check3);
                        if (check3) {
                            String strGoodsPrice3 = shop3.goodsPrice;
                            int intOrderNumber3 = shop3.orderNumber;
                            double douGoodsPrice3 = Double.parseDouble(strGoodsPrice3);
                            money += douGoodsPrice3 * intOrderNumber3;
                        }
                    }
                }
                DecimalFormat df = new DecimalFormat("0.##");
                moneys[0].setText(df.format(money) + "");
                moneys[1].setText(df.format(money) + "");
                ShopCarType0Model shopCarType0Model = (ShopCarType0Model) mData.get(2);
                if (list.contains(false)) {
                    shopCarType0Model.isCheck = false;
                } else {
                    shopCarType0Model.isCheck = true;
                }
            }
        });
    }

    @BindViews({R.id.tv_mainchildshopcar_money_one, R.id.tv_mainchildshopcar_money_one2})
    TextView[] moneys;


    private void initNet() {
        Map<String, String> map = new HashMap<>();
        map.put("token", NetConfig.TOKEN);
        map.put("buyUserId", SharedPreferenceUtils.getUserData(getContext(), 1));
        net.post2(NetConfig.SHOPCAR_LIST, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    mData.add(new ShopCarButtonModel());
                    mData.add(new ShopCarType0Model("管理", false));
                    JSONObject ret = new JSONObject(result);
                    JSONArray bodyArray = ret.getJSONArray("body");
                    if (0 == bodyArray.length()) {
                        rlAll.setVisibility(View.GONE);
                        mData.add(new ShopCarNoBodyModel());
                    } else {
                        rlAll.setVisibility(View.VISIBLE);
                        for (int i = 0; i < bodyArray.length(); i++) {
                            JSONObject goodInfoObj = bodyArray.getJSONObject(i);
                            ShopCarType1Model shop = new ShopCarType1Model(goodInfoObj.getString("sellerPicture"), goodInfoObj.getString("sellerUserId"), goodInfoObj.getString("sellerUserName"), i);
                            mData.add(shop);
                            JSONArray goodInfoArray = goodInfoObj.getJSONArray("goodsInfo");
                            for (int j = 0; j < goodInfoArray.length(); j++) {
                                JSONObject o = goodInfoArray.getJSONObject(j);
                                if (j == goodInfoArray.length() - 1) {
                                    mData.add(new ShopCarType3Model(o.getString("goodsColor"), o.getString("goodsId"), o.getString("goodsName"),
                                            o.getString("goodsPicture"), o.getString("goodsPrice"), o.getString("goodsSize"), o.getString("id"),
                                            Integer.parseInt(o.getString("orderNumber")), o.getString("orderType"), i, false));
                                } else {
                                    mData.add(new ShopCarType2Model(o.getString("goodsColor"), o.getString("goodsId"), o.getString("goodsName"),
                                            o.getString("goodsPicture"), o.getString("goodsPrice"), o.getString("goodsSize"), o.getString("id"),
                                            Integer.parseInt(o.getString("orderNumber")), o.getString("orderType"), i, false));
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*recycleview 滑动的高度*/
    private int y;

    /*顶部高度*/
    private int topY;

    @Override
    protected void setData() {
        super.setData();
        topY = ActivityUtils.getStatusBarHeight(getContext()) + 580;
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_mainshopcar_accurate:
                        shopCar.setChange();
                        break;
                    case R.id.iv_shopcartype1_select:
                        int pos1 = (int) v.getTag();
                        ShopCarType1Model shopCarType1Model = (ShopCarType1Model) mData.get(pos1);
                        int tag1 = shopCarType1Model.Tag;
                        shopCarType1Model.isCheck = !shopCarType1Model.isCheck;
                        for (Visitable visitable : mData) {
                            if (visitable instanceof ShopCarType2Model) {
                                ShopCarType2Model shop2 = (ShopCarType2Model) visitable;
                                if (tag1 == shop2.Tag) {
                                    shop2.isCheck = shopCarType1Model.isCheck;
                                }
                            } else if (visitable instanceof ShopCarType3Model) {
                                ShopCarType3Model shop3 = (ShopCarType3Model) visitable;
                                if (tag1 == shop3.Tag) {
                                    shop3.isCheck = shopCarType1Model.isCheck;
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.iv_shopcartype2_select:
                        int pos2 = (int) v.getTag();
                        ShopCarType2Model shopCarType2Model = (ShopCarType2Model) mData.get(pos2);
                        shopCarType2Model.isCheck = !shopCarType2Model.isCheck;
                        int tag2 = shopCarType2Model.Tag;
                        ShopCarType1Model type2Model = null;
                        List<Boolean> isClode2 = new ArrayList<Boolean>();
                        for (Visitable visitable : mData) {
                            if (visitable instanceof ShopCarType1Model) {
                                ShopCarType1Model shop1 = ((ShopCarType1Model) visitable);
                                if (tag2 == shop1.Tag) {
                                    type2Model = ((ShopCarType1Model) visitable);
                                }
                            } else if (visitable instanceof ShopCarType2Model) {
                                ShopCarType2Model shop2 = ((ShopCarType2Model) visitable);
                                if (tag2 == shop2.Tag) {
                                    isClode2.add(shop2.isCheck);
                                }
                            } else if (visitable instanceof ShopCarType3Model) {
                                ShopCarType3Model shop3 = ((ShopCarType3Model) visitable);
                                if (tag2 == shop3.Tag) {
                                    isClode2.add(shop3.isCheck);
                                }
                            }
                        }
                        if (isClode2.contains(false)) {
                            type2Model.isCheck = false;
                        } else {
                            type2Model.isCheck = true;
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.iv_shopcartype3_select:
                        int pos3 = (int) v.getTag();
                        ShopCarType3Model shopCarType3Model = (ShopCarType3Model) mData.get(pos3);
                        shopCarType3Model.isCheck = !shopCarType3Model.isCheck;
                        int tag3 = shopCarType3Model.Tag;
                        ShopCarType1Model type1Model = null;
                        List<Boolean> isClode = new ArrayList<Boolean>();
                        for (Visitable visitable : mData) {
                            if (visitable instanceof ShopCarType1Model) {
                                ShopCarType1Model shop1 = ((ShopCarType1Model) visitable);
                                if (tag3 == shop1.Tag) {
                                    type1Model = ((ShopCarType1Model) visitable);
                                }
                            } else if (visitable instanceof ShopCarType2Model) {
                                ShopCarType2Model shop2 = ((ShopCarType2Model) visitable);
                                if (tag3 == shop2.Tag) {
                                    isClode.add(shop2.isCheck);
                                }
                            } else if (visitable instanceof ShopCarType3Model) {
                                ShopCarType3Model shop3 = ((ShopCarType3Model) visitable);
                                if (tag3 == shop3.Tag) {
                                    isClode.add(shop3.isCheck);
                                }
                            }
                        }
                        if (isClode.contains(false)) {
                            type1Model.isCheck = false;
                        } else {
                            type1Model.isCheck = true;
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.iv_shopcar_allselct:
                        for (Visitable visitable : mData) {
                            if (visitable instanceof ShopCarType1Model) {
                                ((ShopCarType1Model) visitable).isCheck = all;
                            } else if (visitable instanceof ShopCarType2Model) {
                                ((ShopCarType2Model) visitable).isCheck = all;
                            } else if (visitable instanceof ShopCarType3Model) {
                                ((ShopCarType3Model) visitable).isCheck = all;
                            }
                        }
                        all = !all;
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.tv_shocarself_manager:
                        final ShopCarType0Model shop0 = (ShopCarType0Model) mData.get(2);
                        if (!shop0.isCheckManger) {
                            shop0.strManager = "完成";
                            for (Visitable visitable : mData) {
                                if (visitable instanceof ShopCarType2Model) {
                                    ((ShopCarType2Model) visitable).isShow = !((ShopCarType2Model) visitable).isShow;
                                } else if (visitable instanceof ShopCarType3Model) {
                                    ((ShopCarType3Model) visitable).isShow = !((ShopCarType3Model) visitable).isShow;
                                }
                            }
                            rlDelete.setVisibility(View.VISIBLE);
                        } else {
                            try {
                                JSONObject o = new JSONObject();
                                o.put("buyUserId", SharedPreferenceUtils.getUserData(getContext(), 1));
                                o.put("token", NetConfig.TOKEN);
                                JSONArray array = new JSONArray();
                                for (Visitable visitable : mData) {
                                    if (visitable instanceof ShopCarType2Model) {
                                        JSONObject o2 = new JSONObject();
                                        ShopCarType2Model m2 = ((ShopCarType2Model) visitable);
                                        o2.put("id", m2.id);
                                        o2.put("orderNumber", m2.orderNumber + "");
                                        array.put(o2);
                                    } else if (visitable instanceof ShopCarType3Model) {
                                        JSONObject o2 = new JSONObject();
                                        ShopCarType3Model m3 = ((ShopCarType3Model) visitable);
                                        o2.put("id", m3.id);
                                        o2.put("orderNumber", m3.orderNumber + "");
                                        array.put(o2);
                                    }
                                }
                                o.put("orderInfo", array);
                                new AsyncHttpUtils(new HttpCallBack() {
                                    @Override
                                    public void onResponse(String result) {
                                        String ret = JsonUtils.getRet(result);
                                        if ("0".equals(ret)) {
                                            shop0.strManager = "管理";
                                            /*for (Visitable visitable : mData) {
                                                if (visitable instanceof ShopCarType2Model) {
                                                    ((ShopCarType2Model) visitable).isShow = !((ShopCarType2Model) visitable).isShow;
                                                } else if (visitable instanceof ShopCarType3Model) {
                                                    ((ShopCarType3Model) visitable).isShow = !((ShopCarType3Model) visitable).isShow;
                                                }
                                            }*/
                                            rlDelete.setVisibility(View.GONE);
                                            initBanner();
                                        }
                                    }
                                }, getActivity()).execute(NetConfig.SHOPCAR_REFRESH_SHOP, o.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        shop0.isCheckManger = !shop0.isCheckManger;
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.tv_shocarself_delete:
                        int t1 = (int) v.getTag();
                        ShopCarType2Model shopCarType2Model1 = (ShopCarType2Model) mData.get(t1);
                        int n1 = shopCarType2Model1.orderNumber;
                        if (n1 < 2) {
                            Toast.makeText(getContext(), "数量最少为1", Toast.LENGTH_SHORT).show();
                        } else {
                            shopCarType2Model1.orderNumber--;
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.tv_shocarself_add:
                        int t2 = (int) v.getTag();
                        ShopCarType2Model shopCarType2Model2 = (ShopCarType2Model) mData.get(t2);
                        shopCarType2Model2.orderNumber++;
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.tv_shocarselftype3_delete:
                        int t3 = (int) v.getTag();
                        ShopCarType3Model shopCarType2Model3 = (ShopCarType3Model) mData.get(t3);
                        int n3 = shopCarType2Model3.orderNumber;
                        if (n3 < 2) {
                            Toast.makeText(getContext(), "数量最少为1", Toast.LENGTH_SHORT).show();
                        } else {
                            shopCarType2Model3.orderNumber--;
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.tv_shocarselftype3_add:
                        int t4 = (int) v.getTag();
                        ShopCarType3Model shopCarType3Model4 = (ShopCarType3Model) mData.get(t4);
                        shopCarType3Model4.orderNumber++;
                        adapter.notifyDataSetChanged();
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @BindView(R.id.rl_mainchildshopcar_delete)
    RelativeLayout rlDelete;

    @Override
    protected int layoutInflaterId() {
        return R.layout.fragment_mainchildshopcar_self;
    }


    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            refreshLayout.setRefreshing(false);
        }
    };

    private boolean all = true;

    @OnClick({R.id.tv_mainshopcar_accurate, R.id.iv_mainchildshopcar_delete, R.id.ll_mainchildshopcar_money})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_mainshopcar_accurate:
                shopCar.setChange();
                break;
            case R.id.iv_mainchildshopcar_delete:
                Map<String, String> map = new HashMap<>();
                map.put("buyUserId", SharedPreferenceUtils.getUserData(getContext(), 1));
                map.put("token", NetConfig.TOKEN);
                String idPj = "";
                for (Visitable visitable : mData) {
                    if (visitable instanceof ShopCarType2Model) {
                        ShopCarType2Model shopCarType2Model = ((ShopCarType2Model) visitable);
                        if (shopCarType2Model.isCheck) {
                            idPj = idPj + "," + shopCarType2Model.id;
                        }
                    } else if (visitable instanceof ShopCarType3Model) {
                        ShopCarType3Model shopCarType3Model = ((ShopCarType3Model) visitable);
                        if (shopCarType3Model.isCheck) {
                            idPj = idPj + "," + shopCarType3Model.id;
                        }
                    }
                }
                map.put("idPj", idPj);
                net.post(NetConfig.SHOPCAR_DELETE_SHOP, getContext(), map, new MUtilsInternet.XCallBack() {
                    @Override
                    public void onResponse(String result) {
                        String ret = JsonUtils.getRet(result);
                        if ("0".equals(ret)) {
                            initBanner();
                        } else {
                            Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.ll_mainchildshopcar_money:
                ArrayList<ShopCarType4Model> array = new ArrayList<>();
                for (Visitable visitable : mData) {
                    if (visitable instanceof ShopCarType2Model) {
                        ShopCarType2Model shop2 = ((ShopCarType2Model) visitable);
                        boolean check2 = shop2.isCheck;
                        if (check2) {
                            array.add(new ShopCarType4Model(shop2.goodsColor, shop2.goodsId, shop2.goodsName, shop2.goodsPicture,
                                    shop2.goodsPrice, shop2.goodsSize, shop2.id, shop2.orderNumber, shop2.orderType));
                        }
                    } else if (visitable instanceof ShopCarType3Model) {
                        ShopCarType3Model shop3 = ((ShopCarType3Model) visitable);
                        boolean check3 = shop3.isCheck;
                        if (check3) {
                            array.add(new ShopCarType4Model(shop3.goodsColor, shop3.goodsId, shop3.goodsName, shop3.goodsPicture,
                                    shop3.goodsPrice, shop3.goodsSize, shop3.id, shop3.orderNumber, shop3.orderType));
                        }
                    }
                }
                if (array.size() > 0) {
                    Intent intent = new Intent(getActivity(), ShopCarOrderDetailsActivity.class);
                    intent.putParcelableArrayListExtra("shoplist", array);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "请选择商品", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @BindView(R.id.relative_all)
    RelativeLayout rlAll;

    @Override
    public void onResume() {
        super.onResume();
        initBanner();
    }
}
