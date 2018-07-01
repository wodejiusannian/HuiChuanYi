package com.example.huichuanyi.ui.newpage;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.ItemHmShopCarBusiness;
import com.example.huichuanyi.common_view.model.ItemHmShopCarKind;
import com.example.huichuanyi.common_view.model.ItemHmShopCarRecommendShop;
import com.example.huichuanyi.common_view.model.ItemHmShopCarShops;
import com.example.huichuanyi.common_view.model.ItemHmShopCarShops2;
import com.example.huichuanyi.common_view.model.ShopCarType4Model;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.MUtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;


/**
 * Created by 小五 on 2018/6/26.
 */

public class HMShopCarFragment extends BaseFragment {

    @BindView(R.id.refresh_hmshopcar)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.rv_hmshopcar_content)
    RecyclerView content;

    private List<Visitable> mData = new ArrayList<>();

    private MultiTypeAdapter adapter;

    private boolean isAll = true;

    @BindView(R.id.iv_hmshopcar_all)
    ImageView all;

    private MUtilsInternet net = MUtilsInternet.getInstance();

    @BindView(R.id.tv_hmshopcar_edit)
    TextView edit;

    @OnClick({R.id.iv_hmshopcar_all, R.id.tv_hmshopcar_edit, R.id.tv_hmshopcar_count})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_hmshopcar_all:
                for (Visitable visitable : mData) {
                    if (visitable instanceof ItemHmShopCarBusiness) {
                        ItemHmShopCarBusiness itemHmShopCarBusiness = (ItemHmShopCarBusiness) visitable;
                        itemHmShopCarBusiness.setSelect(isAll);
                    } else if (visitable instanceof ItemHmShopCarShops) {
                        ItemHmShopCarShops itemHmShopCarShops = (ItemHmShopCarShops) visitable;
                        itemHmShopCarShops.setSelect(isAll);
                    } else if (visitable instanceof ItemHmShopCarShops2) {
                        ItemHmShopCarShops2 itemHmShopCarShops2 = (ItemHmShopCarShops2) visitable;
                        itemHmShopCarShops2.setSelect(isAll);
                    } else if (visitable instanceof ItemHmShopCarRecommendShop) {
                        ItemHmShopCarRecommendShop itemHmShopCarRecommendShop = (ItemHmShopCarRecommendShop) visitable;
                        itemHmShopCarRecommendShop.setBuy(isAll);
                    }
                }
                if (isAll) {
                    all.setImageResource(R.mipmap.hm_shopcar_select);
                } else {
                    all.setImageResource(R.mipmap.hm_shopcar_noselect);
                }
                isAll = !isAll;
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_hmshopcar_edit:
                if ("编辑".equals(edit.getText().toString())) {
                    edit.setText("完成");
                } else {
                    edit.setText("编辑");
                }
                for (int i = 0; i < mData.size(); i++) {
                    Visitable visitable = mData.get(i);
                    if (visitable instanceof ItemHmShopCarShops) {
                        ItemHmShopCarShops itemHmShopCarShops = (ItemHmShopCarShops) visitable;
                        itemHmShopCarShops.setEdit(!itemHmShopCarShops.isEdit());
                    } else if (visitable instanceof ItemHmShopCarShops2) {
                        ItemHmShopCarShops2 itemHmShopCarShops2 = (ItemHmShopCarShops2) visitable;
                        itemHmShopCarShops2.setEdit(!itemHmShopCarShops2.isEdit());
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_hmshopcar_count:
                ArrayList<ShopCarType4Model> array = new ArrayList<>();
                for (Visitable visitable : mData) {
                    if (visitable instanceof ItemHmShopCarShops) {
                        ItemHmShopCarShops shop2 = ((ItemHmShopCarShops) visitable);
                        boolean check2 = shop2.isSelect();
                        if (check2) {
                            array.add(new ShopCarType4Model(shop2.getGoodsColor(), shop2.getGoodsId(), shop2.getGoodsName(), shop2.getGoodsPicture(),
                                    shop2.getGoodsPrice() + "", shop2.getGoodsSize(), shop2.getId(), shop2.getOrderNumber(), ""));
                        }
                    } else if (visitable instanceof ItemHmShopCarShops2) {
                        ItemHmShopCarShops2 shop3 = ((ItemHmShopCarShops2) visitable);
                        boolean check3 = shop3.isSelect();
                        if (check3) {
                            array.add(new ShopCarType4Model(shop3.getGoodsColor(), shop3.getGoodsId(), shop3.getGoodsName(), shop3.getGoodsPicture(),
                                    shop3.getGoodsPrice() + "", shop3.getGoodsSize(), shop3.getId(), shop3.getOrderNumber(), ""));
                            ;
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


    @Override
    protected void initData() {
        super.initData();
        if (mData == null)
            mData = new ArrayList<>();
        if (adapter == null)
            adapter = new MultiTypeAdapter(mData);
        if (map == null)
            map = new HashMap<>();
        map.put("buyUserId", "81");
    }

    private Map<String, String> map = new HashMap<>();

    private void getData() {
        net.post(NetConfig.HM_SHOPCAR_DATA_MORE, getContext(), map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("TAG", "onResponse: ----" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject body = jsonObject.getJSONObject("body");
                    JSONArray orderMall = body.getJSONArray("orderMall");
                    JSONArray orderHkj = body.getJSONArray("orderHkj");
                    JSONArray orderVideo = body.getJSONArray("orderVideo");
                    if (orderMall.length() > 0) {
                        mData.add(new ItemHmShopCarKind("慧美商城"));
                        for (int i = 0; i < orderMall.length(); i++) {
                            JSONObject mallHkj = orderMall.getJSONObject(i);
                            String sellerUserName = mallHkj.getString("sellerUserName");
                            mData.add(new ItemHmShopCarBusiness(false, sellerUserName, mallHkj.getString("sellerUserId"),
                                    mallHkj.getString("sellerPicture"), mallHkj.getString("orderType")));
                            JSONArray appHmyc = mallHkj.getJSONArray("appHmycOrderInfo_ListGwc_ABCustoms");
                            for (int j = 0; j < appHmyc.length(); j++) {
                                JSONObject objAppHmyc = appHmyc.getJSONObject(j);
                                String goodsColor = objAppHmyc.getString("goodsColor");
                                String goodsId = objAppHmyc.getString("goodsId");
                                String goodsName = objAppHmyc.getString("goodsName");
                                String goodsPicture = objAppHmyc.getString("goodsPicture");
                                String goodsPrice = objAppHmyc.getString("goodsPrice");
                                String goodsSize = objAppHmyc.getString("goodsSize");
                                String id = objAppHmyc.getString("id");
                                String orderNumber = objAppHmyc.getString("orderNumber");
                                double price = Double.parseDouble(goodsPrice);
                                int number = Integer.parseInt(orderNumber);
                                if (i == orderHkj.length() - 1 && j == appHmyc.length() - 1) {
                                    mData.add(new ItemHmShopCarShops2(false, 1, false, goodsColor, goodsId, goodsName
                                            , goodsPicture, price, goodsSize, id, number, sellerUserName));
                                } else {
                                    mData.add(new ItemHmShopCarShops(false, 1, false, goodsColor, goodsId, goodsName
                                            , goodsPicture, price, goodsSize, id, number, sellerUserName));
                                }
                            }
                        }
                    } else if (orderHkj.length() > 0) {
                        mData.add(new ItemHmShopCarKind("慧美黑科技"));
                        for (int i = 0; i < orderHkj.length(); i++) {
                            JSONObject objOrderHkj = orderHkj.getJSONObject(i);
                            String sellerUserName = objOrderHkj.getString("sellerUserName");
                            mData.add(new ItemHmShopCarBusiness(false, sellerUserName, objOrderHkj.getString("sellerUserId"),
                                    objOrderHkj.getString("sellerPicture"), objOrderHkj.getString("orderType")));
                            JSONArray appHmyc = objOrderHkj.getJSONArray("appHmycOrderInfo_ListGwc_ABCustoms");
                            for (int j = 0; j < appHmyc.length(); j++) {
                                JSONObject objAppHmyc = appHmyc.getJSONObject(j);
                                String goodsColor = objAppHmyc.getString("goodsColor");
                                String goodsId = objAppHmyc.getString("goodsId");
                                String goodsName = objAppHmyc.getString("goodsName");
                                String goodsPicture = objAppHmyc.getString("goodsPicture");
                                String goodsPrice = objAppHmyc.getString("goodsPrice");
                                String goodsSize = objAppHmyc.getString("goodsSize");
                                String id = objAppHmyc.getString("id");
                                String orderNumber = objAppHmyc.getString("orderNumber");
                                double price = Double.parseDouble(goodsPrice);
                                int number = Integer.parseInt(orderNumber);
                                if (i == orderHkj.length() - 1 && j == appHmyc.length() - 1) {
                                    mData.add(new ItemHmShopCarShops2(false, 1, false, goodsColor, goodsId, goodsName
                                            , goodsPicture, price, goodsSize, id, number, sellerUserName));
                                } else {
                                    mData.add(new ItemHmShopCarShops(false, 1, false, goodsColor, goodsId, goodsName
                                            , goodsPicture, price, goodsSize, id, number, sellerUserName));
                                }
                            }
                        }
                    } else if (orderVideo.length() > 0) {
                        mData.add(new ItemHmShopCarKind("慧美视频"));
                        for (int i = 0; i < orderVideo.length(); i++) {
                            JSONObject videoHkj = orderVideo.getJSONObject(i);
                            String sellerUserName = videoHkj.getString("sellerUserName");
                            mData.add(new ItemHmShopCarBusiness(false, sellerUserName
                                    , videoHkj.getString("sellerUserId"),
                                    videoHkj.getString("sellerPicture"), videoHkj.getString("orderType")));
                            JSONArray appHmyc = videoHkj.getJSONArray("appHmycOrderInfo_ListGwc_ABCustoms");
                            for (int j = 0; j < appHmyc.length(); j++) {
                                JSONObject objAppHmyc = appHmyc.getJSONObject(j);
                                String goodsColor = objAppHmyc.getString("goodsColor");
                                String goodsId = objAppHmyc.getString("goodsId");
                                String goodsName = objAppHmyc.getString("goodsName");
                                String goodsPicture = objAppHmyc.getString("goodsPicture");
                                String goodsPrice = objAppHmyc.getString("goodsPrice");
                                String goodsSize = objAppHmyc.getString("goodsSize");
                                String id = objAppHmyc.getString("id");
                                String orderNumber = objAppHmyc.getString("orderNumber");
                                double price = Double.parseDouble(goodsPrice);
                                int number = Integer.parseInt(orderNumber);
                                if (i == orderHkj.length() - 1 && j == appHmyc.length() - 1) {
                                    mData.add(new ItemHmShopCarShops2(false, 1, false, goodsColor, goodsId, goodsName
                                            , goodsPicture, price, goodsSize, id, number, sellerUserName));
                                } else {
                                    mData.add(new ItemHmShopCarShops(false, 1, false, goodsColor, goodsId, goodsName
                                            , goodsPicture, price, goodsSize, id, number, sellerUserName));
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*mData.clear();
                mData.add(new ItemHmShopCarKind());
                mData.add(new ItemHmShopCarBusiness(false));
                mData.add(new ItemHmShopCarShops(false, 1, false));
                mData.add(new ItemHmShopCarShops(false, 1, false));
                mData.add(new ItemHmShopCarBusiness(false));
                mData.add(new ItemHmShopCarShops2(false, 1, false));
                mData.add(new ItemHmShopCarKind());
                mData.add(new ItemHmShopCarBusiness(false));
                mData.add(new ItemHmShopCarShops2(false, 1, false));
                mData.add(new ItemHmShopCarRecommend(false));
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject body = object.getJSONObject("body");
                    String recommendReason = body.getString("recommendReason");
                    JSONArray rec = body.getJSONArray("rec");
                    for (int i = 0; i < rec.length(); i++) {
                        JSONObject recItem = rec.getJSONObject(i);
                        ItemHmShopCarRecommendShop itemHmShopCarRecommendShop = new ItemHmShopCarRecommendShop();
                        itemHmShopCarRecommendShop.setDeleteStatus(recItem.getString("deleteStatus"));
                        itemHmShopCarRecommendShop.setGoodsId(recItem.getString("goodsId"));
                        itemHmShopCarRecommendShop.setGoodsName(recItem.getString("goodsName"));
                        itemHmShopCarRecommendShop.setGoodsPicture(recItem.getString("goodsPicture"));
                        itemHmShopCarRecommendShop.setGoodsPrice(recItem.getString("goodsPrice"));
                        itemHmShopCarRecommendShop.setId(recItem.getString("id"));
                        String recReason = recItem.getString("recommendReason");
                        itemHmShopCarRecommendShop.setRecommendReason(recReason);
                        if (recReason.equals(recommendReason))
                            itemHmShopCarRecommendShop.setBuy(true);
                        else itemHmShopCarRecommendShop.setBuy(false);
                        mData.add(itemHmShopCarRecommendShop);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Visitable visitable = mData.get(position);
                if (visitable instanceof ItemHmShopCarRecommendShop) return 1;
                else return 2;
            }
        });
        content.setLayoutManager(manager);
        content.setAdapter(adapter);
    }

    @Override
    protected void addData() {
        super.addData();
        getData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        adapter.setOnItemClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               int position = (int) v.getTag();
                                               Visitable visitable = mData.get(position);
                                               switch (v.getId()) {
                                                   case R.id.ll_recommendshop:
                                                       Toast.makeText(getContext(), "进入推荐服饰的详情页", Toast.LENGTH_SHORT).show();
                                                       break;
                                                   case R.id.iv_shopcarbusiness_select:
                                                       if (visitable instanceof ItemHmShopCarBusiness) {
                                                           ItemHmShopCarBusiness itemHmShopCarBusiness = (ItemHmShopCarBusiness) visitable;
                                                           String sellUserName = itemHmShopCarBusiness.getSellerUserName();
                                                           boolean isSelect = !itemHmShopCarBusiness.isSelect();
                                                           itemHmShopCarBusiness.setSelect(isSelect);
                                                           for (Visitable visi : mData) {
                                                               if (visi instanceof ItemHmShopCarShops) {
                                                                   ItemHmShopCarShops itemHmShopCarShops = (ItemHmShopCarShops) visi;
                                                                   String kindName = itemHmShopCarShops.getKindName();
                                                                   if (sellUserName.equals(kindName))
                                                                       itemHmShopCarShops.setSelect(isSelect);
                                                               } else if (visi instanceof ItemHmShopCarShops2) {
                                                                   ItemHmShopCarShops2 itemHmShopCarShops2 = (ItemHmShopCarShops2) visi;
                                                                   String kindName = itemHmShopCarShops2.getKindName();
                                                                   if (sellUserName.equals(kindName))
                                                                       itemHmShopCarShops2.setSelect(isSelect);
                                                               }
                                                           }
                                                       }
                                                       adapter.notifyDataSetChanged();
                                                       break;
                                                   case R.id.iv_shopcarrecommendshop_select:
                                                       if (visitable instanceof ItemHmShopCarRecommendShop) {
                                                           ItemHmShopCarRecommendShop itemHmShopCarRecommendShop = (ItemHmShopCarRecommendShop) visitable;
                                                           itemHmShopCarRecommendShop.setBuy(!itemHmShopCarRecommendShop.isBuy());
                                                       }
                                                       break;
                                                   case R.id.iv_shopcarshops2_select:
                                                       if (visitable instanceof ItemHmShopCarShops2) {
                                                           ItemHmShopCarShops2 itemHmShopCarShops2 = (ItemHmShopCarShops2) visitable;
                                                           itemHmShopCarShops2.setSelect(!itemHmShopCarShops2.isSelect());
                                                           List<Boolean> listBoolean = new ArrayList<Boolean>();
                                                           String str = itemHmShopCarShops2.getKindName();
                                                           for (Visitable visi : mData) {
                                                               if (visi instanceof ItemHmShopCarShops) {
                                                                   ItemHmShopCarShops itemHmShopCarShops = (ItemHmShopCarShops) visi;
                                                                   if (str.equals(itemHmShopCarShops.getKindName()))
                                                                       listBoolean.add(itemHmShopCarShops.isSelect());
                                                               } else if (visi instanceof ItemHmShopCarShops2) {
                                                                   ItemHmShopCarShops2 itemHmShopCarShops22 = (ItemHmShopCarShops2) visi;
                                                                   if (str.equals(itemHmShopCarShops22.getKindName()))
                                                                       listBoolean.add(itemHmShopCarShops22.isSelect());
                                                               }
                                                           }
                                                           for (Visitable visi : mData) {
                                                               if (visi instanceof ItemHmShopCarBusiness) {
                                                                   ItemHmShopCarBusiness itemHmShopCarBusiness = (ItemHmShopCarBusiness) visi;
                                                                   if (listBoolean.contains(false)) {
                                                                       if (str.equals(itemHmShopCarBusiness.getSellerUserName()))
                                                                           itemHmShopCarBusiness.setSelect(false);
                                                                   } else {
                                                                       if (str.equals(itemHmShopCarBusiness.getSellerUserName()))
                                                                           itemHmShopCarBusiness.setSelect(true);
                                                                   }
                                                               }
                                                           }
                                                           adapter.notifyDataSetChanged();
                                                       }
                                                       break;
                                                   case R.id.iv_shopcarshops_select:
                                                       if (visitable instanceof ItemHmShopCarShops) {
                                                           ItemHmShopCarShops itemHmShopCarShops = (ItemHmShopCarShops) visitable;
                                                           itemHmShopCarShops.setSelect(!itemHmShopCarShops.isSelect());
                                                           List<Boolean> listBoolean = new ArrayList<Boolean>();
                                                           String str = itemHmShopCarShops.getKindName();
                                                           for (Visitable visi : mData) {
                                                               if (visi instanceof ItemHmShopCarShops) {
                                                                   ItemHmShopCarShops itemHmShopCarShopss = (ItemHmShopCarShops) visi;
                                                                   if (str.equals(itemHmShopCarShopss.getKindName()))
                                                                       listBoolean.add(itemHmShopCarShopss.isSelect());
                                                               } else if (visi instanceof ItemHmShopCarShops2) {
                                                                   ItemHmShopCarShops2 itemHmShopCarShops22 = (ItemHmShopCarShops2) visi;
                                                                   if (str.equals(itemHmShopCarShops22.getKindName()))
                                                                       listBoolean.add(itemHmShopCarShops22.isSelect());
                                                               }
                                                           }

                                                           for (Visitable visi : mData) {
                                                               if (visi instanceof ItemHmShopCarBusiness) {
                                                                   ItemHmShopCarBusiness itemHmShopCarBusiness = (ItemHmShopCarBusiness) visi;
                                                                   if (listBoolean.contains(false)) {
                                                                       if (str.equals(itemHmShopCarBusiness.getSellerUserName()))
                                                                           itemHmShopCarBusiness.setSelect(false);
                                                                   } else {
                                                                       if (str.equals(itemHmShopCarBusiness.getSellerUserName()))
                                                                           itemHmShopCarBusiness.setSelect(true);
                                                                   }
                                                               }
                                                           }
                                                           adapter.notifyDataSetChanged();
                                                       }
                                                       break;
                                                   case R.id.tv_shocarself_delete:
                                                       if (visitable instanceof ItemHmShopCarShops) {
                                                           ItemHmShopCarShops itemHmShopCarShops = (ItemHmShopCarShops) visitable;
                                                           int count = itemHmShopCarShops.getCount();
                                                           if (count > 1) count--;
                                                           itemHmShopCarShops.setCount(count);
                                                       } else if (visitable instanceof ItemHmShopCarShops2) {
                                                           ItemHmShopCarShops2 itemHmShopCarShops2 = (ItemHmShopCarShops2) visitable;
                                                           int count = itemHmShopCarShops2.getCount();
                                                           if (count > 1) count--;
                                                           itemHmShopCarShops2.setCount(count);
                                                       }
                                                       break;
                                                   case R.id.tv_shocarself_add:
                                                       if (visitable instanceof ItemHmShopCarShops) {
                                                           ItemHmShopCarShops itemHmShopCarShops = (ItemHmShopCarShops) visitable;
                                                           int count = itemHmShopCarShops.getCount();
                                                           count++;
                                                           itemHmShopCarShops.setCount(count);
                                                       } else if (visitable instanceof ItemHmShopCarShops2) {
                                                           ItemHmShopCarShops2 itemHmShopCarShops2 = (ItemHmShopCarShops2) visitable;
                                                           int count = itemHmShopCarShops2.getCount();
                                                           count++;
                                                           itemHmShopCarShops2.setCount(count);
                                                       }
                                                       break;
                                               }

                                               adapter.notifyDataSetChanged();
                                           }
                                       }

        );
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()

                                           {
                                               @Override
                                               public void onRefresh() {
                                                   refreshLayout.setRefreshing(false);
                                                   getData();
                                               }
                                           }

        );
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver()

                                            {
                                                @Override
                                                public void onChanged() {
                                                    super.onChanged();

                                                    adapterOnChanger();

                                                }
                                            }

        );
    }

    @BindViews({R.id.tv_hmshopcar_money, R.id.tv_hmshopcar_count})
    TextView[] tvMC;

    private void adapterOnChanger() {
        double allMoney = 0.00;
        int allCount = 0;
        List<Boolean> listBoolean = new ArrayList<>();
        for (Visitable visitable : mData) {
            if (visitable instanceof ItemHmShopCarShops) {
                ItemHmShopCarShops itemHmShopCarShops = (ItemHmShopCarShops) visitable;
                listBoolean.add(itemHmShopCarShops.isSelect());
                if (itemHmShopCarShops.isSelect()) {
                    allCount += itemHmShopCarShops.getCount();
                    allMoney += itemHmShopCarShops.getCount() * itemHmShopCarShops.getGoodsPrice();
                }
            } else if (visitable instanceof ItemHmShopCarShops2) {
                ItemHmShopCarShops2 itemHmShopCarShops2 = (ItemHmShopCarShops2) visitable;
                listBoolean.add(itemHmShopCarShops2.isSelect());
                if (itemHmShopCarShops2.isSelect()) {
                    allCount += itemHmShopCarShops2.getCount();
                    allMoney += itemHmShopCarShops2.getCount() * itemHmShopCarShops2.getGoodsPrice();
                }
            } else if (visitable instanceof ItemHmShopCarRecommendShop) {
                ItemHmShopCarRecommendShop itemHmShopCarRecommendShop = (ItemHmShopCarRecommendShop) visitable;
                listBoolean.add(itemHmShopCarRecommendShop.isBuy());
                if (itemHmShopCarRecommendShop.isBuy()) {
                    allCount++;
                    allMoney += itemHmShopCarRecommendShop.getGoodsPrice();
                }
            }
        }
        if (listBoolean.contains(false)) {
            all.setImageResource(R.mipmap.hm_shopcar_noselect);
            isAll = true;
        } else {
            all.setImageResource(R.mipmap.hm_shopcar_select);
            isAll = false;
        }
        tvMC[0].setText("合计：¥" + CommonUtils.strDoubleTwo(allMoney));
        tvMC[1].setText("去结算(" + allCount + ")");
    }


    @Override
    protected int layoutInflaterId() {
        return R.layout.fragment_hmshopcar;
    }
}
