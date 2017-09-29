package com.example.huichuanyi.ui.activity.lanyang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.LyShopCar;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class LyShopCarActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    private List<Visitable> mData = new ArrayList<>();

    private boolean isEdit = false;

    private MultiTypeAdapter adapter;

    @BindViews({R.id.tv_ly_shopcar_edit, R.id.tv_ly_shopcar_money, R.id.tv_ly_shopcar_gopay})
    TextView[] tvs;

    @BindView(R.id.cb_ly_shopcar_allselct)
    CheckBox cbAll;

    @BindView(R.id.ll_shopcar_money)
    LinearLayout llMoney;

    @BindView(R.id.sf_ly_shopcar_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.rv_ly_shopcar_content)
    RecyclerView rvContent;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ly_shop_car);
    }

    @Override
    protected void setListener() {
        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < mData.size(); i++) {
                    LyShopCar.BodyBean shopCar = (LyShopCar.BodyBean) mData.get(i);
                    shopCar.setSelect(isChecked);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {
        adapter = new MultiTypeAdapter(mData);
        dialog = new ProgressDialog(this);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int allMoney = 0;
                for (int i = 0; i < mData.size(); i++) {
                    LyShopCar.BodyBean car = (LyShopCar.BodyBean) mData.get(i);
                    if (car.isSelect()) {
                        allMoney += car.getPrice_one() * car.getNum();
                    }
                }
                tvs[1].setText(allMoney + "");
            }
        });

        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(adapter);
    }

    @Override
    protected void setData() {
        initShop();
        adapter.notifyDataSetChanged();
        refreshLayout.setOnRefreshListener(this);
    }

    @OnClick({R.id.tv_ly_shopcar_edit, R.id.tv_ly_shopcar_gopay})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.tv_ly_shopcar_edit:
                isEdit = !isEdit;
                if (isEdit) {
                    tvs[0].setText("完成");
                    tvs[2].setText("删除");
                    llMoney.setVisibility(View.GONE);
                } else {
                    updateShop();
                    tvs[0].setText("编辑");
                    tvs[2].setText("下单");
                    llMoney.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < mData.size(); i++) {
                    LyShopCar.BodyBean shopCar = (LyShopCar.BodyBean) mData.get(i);
                    shopCar.setEdit(isEdit);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_ly_shopcar_gopay:
                if (isEdit) {
                    deleteShop();
                } else {
                    ArrayList<LyShopCar.BodyBean> data = new ArrayList<>();
                    for (int i = 0; i < mData.size(); i++) {
                        LyShopCar.BodyBean visitable = (LyShopCar.BodyBean) mData.get(i);
                        if (visitable.isSelect())
                            data.add(visitable);
                    }
                    if (data.size() > 0) {
                        Intent intent = new Intent(this, LyBuyActivity.class);
                        intent.putExtra("tag", "1");
                        intent.putParcelableArrayListExtra("buying", data);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "亲，请选择您要购买的商品", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void deleteShop() {
        dialog.show();
        final JSONObject myData = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            myData.put("user_id", SharedPreferenceUtils.getUserData(this, 1));
            for (int i = 0; i < mData.size(); i++) {
                LyShopCar.BodyBean bean = (LyShopCar.BodyBean) mData.get(i);
                if (bean.isSelect()) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", bean.getId() + "");
                    array.put(obj);
                }
            }
            myData.put("ids", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams pa = new RequestParams(NetConfig.LY_SHOP_DELETE_SHOP);
        pa.addBodyParameter("mydata", myData.toString());
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String ret = obj.getString("ret");
                    if ("0".equals(ret)) {
                        for (int i = 0; i < mData.size(); i++) {
                            LyShopCar.BodyBean bean = (LyShopCar.BodyBean) mData.get(i);
                            if (bean.isSelect()) {
                                mData.remove(i);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(LyShopCarActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LyShopCarActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void updateShop() {
        dialog.show();
        JSONObject myData = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            myData.put("user_id", SharedPreferenceUtils.getUserData(this, 1));
            for (int i = 0; i < mData.size(); i++) {
                LyShopCar.BodyBean bean = (LyShopCar.BodyBean) mData.get(i);
                JSONObject obj = new JSONObject();
                obj.put("id", bean.getId() + "");
                obj.put("num", bean.getNum() + "");
                array.put(obj);
            }
            myData.put("info", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams pa = new RequestParams(NetConfig.LY_SHOP_UPDATE_COUNT);
        pa.addBodyParameter("mydata", myData.toString());
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initShop() {
        dialog.show();
        refreshLayout.setRefreshing(true);
        RequestParams params = new RequestParams(NetConfig.LY_SHOP_CAR_INIT_SHOP);
        params.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(this, 1));
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                mData.clear();
                Gson gson = new Gson();
                LyShopCar shopCar = gson.fromJson(result, LyShopCar.class);
                mData.addAll(shopCar.getBody());
                adapter.notifyDataSetChanged();
                if (refreshLayout.isRefreshing())
                    refreshLayout.setRefreshing(false);
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onRefresh() {
        initShop();
    }
}
