package com.example.huichuanyi.ui.newpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderStudioHoz;
import com.example.huichuanyi.common_view.model.OrderStudioIntroduceOpenModel;
import com.example.huichuanyi.common_view.model.OrderStudioIntroducePJBottomModel;
import com.example.huichuanyi.common_view.model.OrderStudioIntroducePJMiddleModel;
import com.example.huichuanyi.common_view.model.OrderStudioIntroducePJTopModel;
import com.example.huichuanyi.common_view.model.OrderStudioIntroduceSecondModel;
import com.example.huichuanyi.common_view.model.OrderStudioIntroduceThirdModel;
import com.example.huichuanyi.common_view.model.OrderStudioIntroduceTopModel;
import com.example.huichuanyi.common_view.model.OrderStudioOne;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.dialog.SelectClothesCountDialog;
import com.example.huichuanyi.emum.ServiceType;
import com.example.huichuanyi.ui.activity.LiJiYuYueWhatActivity;
import com.example.huichuanyi.ui.activity.SelectStudioTimeActivity;
import com.example.huichuanyi.ui.activity.lanyang.LyDetailsWebActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.ui.modle.OrderGoDoorPrice;
import com.example.huichuanyi.utils.ActivityCacheUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.ServiceSingleUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

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

public class OrderStudioIntroduceActivity extends BaseActivity {

    @BindView(R.id.recycle_view)
    RecyclerView recycle;

    @BindView(R.id.refresh_view)
    SwipeRefreshLayout refresh;

    @BindView(R.id.tv_order_studio_introduce_money)
    TextView money;

    private MultiTypeAdapter adapter;

    private List<Visitable> mData = new ArrayList<>();

    private City.BodyBean model;

    private MUtilsInternet net = MUtilsInternet.getInstance();

    private int pageNow = 1;

    private Map<String, String> map = new HashMap<>();

    private static final String BASE_URL = "http://hmyc365.net";

    private String addGrade = "0";

    @BindView(R.id.tv_alpha)
    TextView tvAlpha;

    @BindView(R.id.ll_alpha)
    LinearLayout llAlpha;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1000) {

        } else if (requestCode == 2 && resultCode == 2000) {
            String time = data.getStringExtra("time");
            OrderStudioIntroduceSecondModel visitable = (OrderStudioIntroduceSecondModel) mData.get(2);
            visitable.text = time;
            adapter.notifyItemChanged(2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_studio_introduce);
        ActivityCacheUtils.addActivity(this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void setListener() {
        initNet("1");
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoad = true;
                refresh.setRefreshing(false);
                pageNow = 1;
                initNet("1");
            }
        });
        adapter.OnScroll(new MultiTypeAdapter.Scroll() {
            @Override
            public void onScrollBottom() {
                if (isLoadHoz) {
                    isLoadHoz = false;
                    initHoz();
                }
            }
        });
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.rl_order_studio_introduce_second:
                        int position = (int) v.getTag();
                        OrderStudioIntroduceSecondModel visitable = (OrderStudioIntroduceSecondModel) mData.get(position);
                        if (1 == visitable.type) {
                            if (ServiceSingleUtils.getInstance().getServiceType() == ServiceType.SERVICE_ACARUS_KILLING) {
                                Toast.makeText(OrderStudioIntroduceActivity.this, "亲，此单位是标准哦", Toast.LENGTH_SHORT).show();
                            } else {
                                final List<OrderGoDoorPrice> mDatass = new ArrayList<>();
                                RequestParams params = new RequestParams(BASE_URL + "/admiral/app/hmyc/service/manager/listPrice.htm");
                                params.addBodyParameter("userIdManager", model.getId());
                                x.http().post(params, new Callback.CacheCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        try {
                                            JSONObject ob = new JSONObject(result);
                                            JSONObject body = ob.getJSONObject("body");
                                            addGrade = body.getString("addGrade");
                                            int i1 = Integer.parseInt(addGrade) + Integer.parseInt(model.getGrade());
                                            JSONArray records = body.getJSONArray("records");
                                            for (int i = 0; i < records.length(); i++) {
                                                JSONObject o = records.getJSONObject(i);
                                                mDatass.add(new OrderGoDoorPrice(o.getString("priceRange"), o.getString("gradeOne"),
                                                        o.getString("gradeTwo"), o.getString("gradeThree"), o.getString("gradeFour"),
                                                        o.getString("gradeFive"), o.getString("gradeSix")));
                                            }
                                            if (mDatass != null && mDatass.size() > 0) {
                                                SelectClothesCountDialog dialog = new SelectClothesCountDialog(OrderStudioIntroduceActivity.this, mDatass, i1 + "");
                                                dialog.setOnItemGetData(new SelectClothesCountDialog.ItemData() {
                                                    @Override
                                                    public void getItemData(String count, String price) {
                                                        OrderStudioIntroduceSecondModel visitable = (OrderStudioIntroduceSecondModel) mData.get(1);
                                                        visitable.text = count;
                                                        money.setText(price);
                                                        adapter.notifyItemChanged(1);
                                                    }
                                                });
                                                dialog.show();
                                            }
                                        } catch (Exception e) {
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

                                    @Override
                                    public boolean onCache(String result) {
                                        return false;
                                    }
                                });
                            }

                        } else {
                            Intent in = new Intent(OrderStudioIntroduceActivity.this, SelectStudioTimeActivity.class);
                            in.putExtra("studio_id", model.getId());
                            startActivityForResult(in, 2);
                        }
                        break;
                    case R.id.ll_item_hahhaha:
                        OrderStudioIntroduceOpenModel op = (OrderStudioIntroduceOpenModel) mData.get(5);
                        List<OrderStudioHoz> list = op.list;
                        int pos = (int) v.getTag();
                        Intent in = new Intent(OrderStudioIntroduceActivity.this, LyDetailsWebActivity.class);
                        in.putExtra("details_page", list.get(pos).piv);
                        startActivity(in);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private boolean isLoadHoz = true;

    private void initHoz() {
        RequestParams params = new RequestParams(BASE_URL + "/admiral/app/hmyc/service/manager/listPicture.htm");
        params.addBodyParameter("type", "2");
        params.addBodyParameter("userIdManager", model.getId());
        params.addBodyParameter("pageNow", pagsss + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject ob = new JSONObject(result);
                    JSONObject body = ob.getJSONObject("body");
                    JSONArray records = body.getJSONArray("records");
                    if (records.length() > 0) {
                        OrderStudioIntroduceOpenModel open = (OrderStudioIntroduceOpenModel) mData.get(5);
                        List<OrderStudioHoz> list = open.list;
                        for (int i = 0; i < records.length(); i++) {
                            JSONObject o = records.getJSONObject(i);
                            list.add(new OrderStudioHoz(BASE_URL + o.getString("pictureUrl"), 1));
                        }
                        String pageSize = body.getString("pageSize");
                        if (!pageSize.equals(pagsss + "")) {
                            mHandler.sendEmptyMessageDelayed(1, 2000);
                            pagsss++;
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isLoadHoz = true;
        }
    };

    private int pagsss = 2;
    /*
    * 我是保存上面banner数据的
    * */
    private List<String> picList = new ArrayList<String>();


    /*
    * 我是获取banner和横着滑的图片的  1 是banner上面的图片，2是横着滑的图片
    * */
    private void initNet(final String type) {
        loadingDialog();
        map.put("type", type);
        map.put("userIdManager", model.getId());
        map.put("pageNow", pageNow + "");
        net.post2(BASE_URL + "/admiral/app/hmyc/service/manager/listPicture.htm", map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                if ("1".equals(type)) {
                    try {
                        mData.clear();
                        picList.clear();
                        JSONObject ret = new JSONObject(result);
                        JSONObject body = ret.getJSONObject("body");
                        JSONArray records = body.getJSONArray("records");
                        for (int i = 0; i < records.length(); i++) {
                            JSONObject o = records.getJSONObject(i);
                            picList.add(BASE_URL + o.getString("pictureUrl"));
                        }
                        initNetOrderData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject ret = new JSONObject(result);
                        JSONObject body = ret.getJSONObject("body");
                        JSONArray records = body.getJSONArray("records");
                        List<OrderStudioHoz> piList = new ArrayList<OrderStudioHoz>();
                        for (int i = 0; i < records.length(); i++) {
                            JSONObject o = records.getJSONObject(i);
                            piList.add(new OrderStudioHoz(BASE_URL + o.getString("pictureUrl"), 1));
                        }
                        mData.add(4, new OrderStudioOne("服务展示"));
                        mData.add(5, new OrderStudioIntroduceOpenModel(piList));
                        initNetPJ();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    /*
    * 获取评价的接口
    * */
    private void initNetPJ() {
        map.put("pageNow", pageNow + "");
        net.post2(BASE_URL + "/admiral/app/hmyc/old/service/infoEvaluate.htm", map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    mData.add(6, new OrderStudioOne("用户评价"));
                    JSONObject ob = new JSONObject(result);
                    JSONObject body = ob.getJSONObject("body");
                    JSONArray records = body.getJSONArray("records");
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject jsonObject = records.getJSONObject(i);
                        if (i == 0) {
                            mData.add(new OrderStudioIntroducePJTopModel(jsonObject.getString("content"), jsonObject.getString("number"),
                                    jsonObject.getString("orderid"), jsonObject.getString("oth_num"), jsonObject.getString("oth_state"),
                                    jsonObject.getString("stars1"), jsonObject.getString("stars2"), jsonObject.getString("stars3"), jsonObject.getString("time"),
                                    jsonObject.getString("user_name"), jsonObject.getString("order_name")));
                        } else if (i == records.length() - 1) {
                            mData.add(new OrderStudioIntroducePJBottomModel(jsonObject.getString("content"), jsonObject.getString("number"),
                                    jsonObject.getString("orderid"), jsonObject.getString("oth_num"), jsonObject.getString("oth_state"),
                                    jsonObject.getString("stars1"), jsonObject.getString("stars2"), jsonObject.getString("stars3"), jsonObject.getString("time"),
                                    jsonObject.getString("user_name"), jsonObject.getString("order_name")));
                        } else {
                            mData.add(new OrderStudioIntroducePJMiddleModel(jsonObject.getString("content"), jsonObject.getString("number"),
                                    jsonObject.getString("orderid"), jsonObject.getString("oth_num"), jsonObject.getString("oth_state"),
                                    jsonObject.getString("stars1"), jsonObject.getString("stars2"), jsonObject.getString("stars3"), jsonObject.getString("time"),
                                    jsonObject.getString("user_name"), jsonObject.getString("order_name")));
                        }
                    }
                    pageNow++;
                    adapter.notifyDataSetChanged();
                    dismissDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    * 管理师详情-365客户数量+月度之星
    * */
    private void initNetOrderData() {
        RequestParams pa = new RequestParams(BASE_URL + "/admiral/app/hmyc/old/service/infoVipStar.htm");
        pa.addBodyParameter("userIdManager", model.getId());
        x.http().post(pa, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject ret = new JSONObject(result);
                    JSONObject body = ret.getJSONObject("body");
                    String vipNum = body.getString("vipNum");
                    JSONArray array = body.getJSONArray("nameTag");
                    List<String> textList = new ArrayList<String>();
                    for (int i = 0; i < array.length(); i++) {
                        textList.add(array.getString(i));
                    }
                    mData.add(0, new OrderStudioIntroduceTopModel(model.getName(), model.getIntroduction(), model.getPhoto_get(), picList, model.getUsername(), textList));
                    if (ServiceSingleUtils.getInstance().getServiceType() == ServiceType.SERVICE_ACARUS_KILLING)
                        mData.add(1, new OrderStudioIntroduceSecondModel(1, "标准" + defaultNum + "袋 " + defaultPrice + "元"));
                    else
                        mData.add(1, new OrderStudioIntroduceSecondModel(1, "请选择服饰量区间"));
                    mData.add(2, new OrderStudioIntroduceSecondModel(2, "请选择服务时间"));
                    double v = (Double.parseDouble(model.getPf()) / 5) * 100;
                    String jll = String.format("%.1f", v);
                    mData.add(3, new OrderStudioIntroduceThirdModel(model.getXl(), vipNum, jll + "%"));
                    initNet("2");
                } catch (Exception e) {
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

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }

    @Override
    protected void initData() {
        adapter = new MultiTypeAdapter(mData);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(adapter);
        Intent intent = getIntent();
        model = (City.BodyBean) intent.getSerializableExtra("model");
        defaultNum = intent.getStringExtra("defaultNum");
        defaultPrice = intent.getStringExtra("defaultPrice");
        if (!CommonUtils.isEmpty(defaultNum)) {
            money.setText(defaultPrice);
        }
        tvAlpha.setText(model.getName());
    }

    private String defaultPrice;
    private String defaultNum;

    @OnClick({R.id.tv_order_studio_introduce_go_order, R.id.ll_order_studio_introduce_what})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.tv_order_studio_introduce_go_order:
                if (ServiceSingleUtils.getInstance().getServiceType() == ServiceType.SERVICE_THE_DOOR) {
                    String count = null;
                    for (int i = 0; i < mData.size(); i++) {
                        Visitable visitable = mData.get(i);
                        if (visitable instanceof OrderStudioIntroduceSecondModel) {
                            count = ((OrderStudioIntroduceSecondModel) visitable).text;
                            break;
                        }
                    }
                    final String count1 = count;
                    String time = null;
                    for (int i = 0; i < mData.size(); i++) {
                        Visitable visitable = mData.get(i);
                        if (visitable instanceof OrderStudioIntroduceSecondModel) {
                            time = ((OrderStudioIntroduceSecondModel) visitable).text;
                        }
                    }
                    final String time1 = time;
                    if (CommonUtils.isEmpty(count) || count.contains("请")) {
                        Toast.makeText(this, "亲，请选择衣服数量", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (CommonUtils.isEmpty(time) || time.contains("请")) {
                        Toast.makeText(this, "亲，请选择预约时间", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    count = count.split("-")[1];
                    count = count.substring(0, count.length() - 3);
                    String buyUserId = SharedPreferenceUtils.getUserData(this, 1);
                    String buyUserName = SharedPreferenceUtils.getUserData(this, 2);
                    String sellerUserId = model.getId();
                    String sellerUserName = model.getUsername();
                    String sellerCityName = model.getCity();
                    String sellerPhone = model.getPhone();
                    String sellerPicture = model.getPhoto_get();
                    map.put("buyUserId", buyUserId);
                    map.put("buyUserName", buyUserName);
                    map.put("sellerUserId", sellerUserId);
                    map.put("sellerUserName", sellerUserName);
                    map.put("sellerCityName", sellerCityName);
                    map.put("sellerPhone", sellerPhone);
                    map.put("consigneeTime", time);
                    map.put("clothesNumber", count);
                    map.put("sellerPicture", sellerPicture);
                    net.post(NetConfig.SERVICE_GOORDER_GO_GO, this, map, new MUtilsInternet.XCallBack() {
                        @Override
                        public void onResponse(String result) {
                            try {
                                JSONObject obj = new JSONObject(result);
                                JSONObject body = obj.getJSONObject("body");
                                Intent intent = new Intent(OrderStudioIntroduceActivity.this, OrderDetailsActivity.class);
                                intent.putExtra("model", model);
                                intent.putExtra("money", money.getText().toString());
                                intent.putExtra("addGrade", addGrade);
                                intent.putExtra("count", count1);
                                intent.putExtra("time", time1);
                                intent.putExtra("defaultNum", defaultNum);
                                intent.putExtra("id", body.getString("id"));
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    String count = null;
                    for (int i = 0; i < mData.size(); i++) {
                        Visitable visitable = mData.get(i);
                        if (visitable instanceof OrderStudioIntroduceSecondModel) {
                            count = ((OrderStudioIntroduceSecondModel) visitable).text;
                            break;
                        }
                    }
                    String time = null;
                    for (int i = 0; i < mData.size(); i++) {
                        Visitable visitable = mData.get(i);
                        if (visitable instanceof OrderStudioIntroduceSecondModel) {
                            time = ((OrderStudioIntroduceSecondModel) visitable).text;
                        }
                    }
                    if (CommonUtils.isEmpty(count) || count.contains("请")) {
                        Toast.makeText(this, "亲，请选择衣服数量", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (CommonUtils.isEmpty(time) || time.contains("请")) {
                        Toast.makeText(this, "亲，请选择预约时间", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(this, OrderDetailsActivity.class);
                    intent.putExtra("model", model);
                    intent.putExtra("count", count);
                    intent.putExtra("time", time);
                    intent.putExtra("money", money.getText().toString());
                    intent.putExtra("addGrade", addGrade);
                    intent.putExtra("defaultNum", defaultNum);
                    startActivity(intent);
                }
                break;
            case R.id.ll_order_studio_introduce_what:
                Intent in = new Intent(this, LiJiYuYueWhatActivity.class);
                in.putExtra("gra", addGrade);
                startActivity(in);
                break;
            default:
                break;
        }
    }

    @Override
    protected void setData() {
        recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //super.onScrollStateChanged(recyclerView, newState);
                boolean slideToBottom = isSlideToBottom(recycle);
                if (slideToBottom && isLoad) {
                    isLoad = false;
                    initLoadMore();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                y += dy;
                float i = (float) (y / 200.0);
                llAlpha.setAlpha(i);
            }
        });
    }

    private int y = 0;

    private void initLoadMore() {
        map.put("pageNow", pageNow + "");
        net.post2(BASE_URL + "/admiral/app/hmyc/old/service/infoEvaluate.htm", map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject body = object.getJSONObject("body");
                    JSONArray records = body.getJSONArray("records");
                    if (records.length() > 0) {
                        pageNow++;
                        isLoad = true;
                        OrderStudioIntroducePJBottomModel pjBottomModel = (OrderStudioIntroducePJBottomModel) mData.get(mData.size() - 1);
                        mData.remove(mData.size() - 1);
                        OrderStudioIntroducePJMiddleModel middleModel = new OrderStudioIntroducePJMiddleModel(pjBottomModel.content, pjBottomModel.number, pjBottomModel.orderid,
                                pjBottomModel.oth_num, pjBottomModel.oth_state, pjBottomModel.stars1, pjBottomModel.stars2, pjBottomModel.stars3, pjBottomModel.time, pjBottomModel.user_name, pjBottomModel.order_name);
                        mData.add(middleModel);
                        for (int i = 0; i < records.length(); i++) {
                            JSONObject jsonObject = records.getJSONObject(i);
                            if (i == 0) {
                                mData.add(new OrderStudioIntroducePJTopModel(jsonObject.getString("content"), jsonObject.getString("number"),
                                        jsonObject.getString("orderid"), jsonObject.getString("oth_num"), jsonObject.getString("oth_state"),
                                        jsonObject.getString("stars1"), jsonObject.getString("stars2"), jsonObject.getString("stars3"), jsonObject.getString("time"),
                                        jsonObject.getString("user_name"), jsonObject.getString("order_name")));
                            } else if (i == records.length() - 1) {
                                mData.add(new OrderStudioIntroducePJBottomModel(jsonObject.getString("content"), jsonObject.getString("number"),
                                        jsonObject.getString("orderid"), jsonObject.getString("oth_num"), jsonObject.getString("oth_state"),
                                        jsonObject.getString("stars1"), jsonObject.getString("stars2"), jsonObject.getString("stars3"), jsonObject.getString("time"),
                                        jsonObject.getString("user_name"), jsonObject.getString("order_name")));
                            } else {
                                mData.add(new OrderStudioIntroducePJMiddleModel(jsonObject.getString("content"), jsonObject.getString("number"),
                                        jsonObject.getString("orderid"), jsonObject.getString("oth_num"), jsonObject.getString("oth_state"),
                                        jsonObject.getString("stars1"), jsonObject.getString("stars2"), jsonObject.getString("stars3"), jsonObject.getString("time"),
                                        jsonObject.getString("user_name"), jsonObject.getString("order_name")));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(OrderStudioIntroduceActivity.this, "没有更多的评价了", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /*
    * 加载完这一页才能再次加载
    * */
    private boolean isLoad = true;

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
