package com.example.huichuanyi.ui.newpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.bean.City;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderStudioFill;
import com.example.huichuanyi.common_view.model.OrderStudioOne;
import com.example.huichuanyi.common_view.model.OrderStudioThree;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.emum.ServiceType;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.ServiceSingleUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

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
public class OrderStudioListFragment extends BaseFragment {

    @Override
    protected int layoutInflaterId() {
        return R.layout.fragment_normal;
    }

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_view)
    SwipeRefreshLayout refreshLayout;

    private MultiTypeAdapter adapter;

    private List<Visitable> mData = new ArrayList<>();

    private OrderStudioListActivity activity;

    private UtilsInternet net = UtilsInternet.getInstance();

    private String city;

    private String mLng;

    private String mLat;

    private int type;

    private int windowsHeight;

    @Override
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
        adapter = new MultiTypeAdapter(mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        activity = (OrderStudioListActivity) getActivity();
    }


    @Override
    protected void initEvent() {
        super.initEvent();

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        windowsHeight = wm.getDefaultDisplay().getHeight();
        windowsHeight -= dip2px(getContext(), 80);
        if (15 == type) {
            //默认排序
            activity.setOnFresh(new OrderStudioListActivity.OnFresh() {
                @Override
                public void onFresh(String address, String lat, String lng) {
                    city = address;
                    mLng = lng;
                    mLat = lat;
                    loadingData();
                }

            });
        } else if (21 == type) {
            //评分最高
            activity.setOnFresh2(new OrderStudioListActivity.OnFresh() {
                @Override
                public void onFresh(String address, String lat, String lng) {
                    city = address;
                    mLng = lng;
                    mLat = lat;
                    loadingData();
                }

            });
        } else if (32 == type) {
            //销量最高
            activity.setOnFresh3(new OrderStudioListActivity.OnFresh() {
                @Override
                public void onFresh(String address, String lat, String lng) {
                    city = address;
                    mLng = lng;
                    mLat = lat;
                    loadingData();
                }
            });
        }
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                City.BodyBean bodyBean = (City.BodyBean) mData.get(position);
                String service = bodyBean.getService();
                if ("已开通".equals(service)) {
                    if (ServiceSingleUtils.getInstance().getServiceType() == ServiceType.SERVICE_ACARUS_KILLING) {
                        acausKilling(bodyBean.getCity(), position);
                    } else {
                        Intent intent = new Intent(getContext(), OrderStudioIntroduceActivity.class);
                        intent.putExtra("model", bodyBean);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void acausKilling(String city, final int position) {
        Map<String, String> map = new HashMap<>();
        map.put("token", "82D5FBD40259C743ADDEF14D0E22F347");
        map.put("priceType", "1");
        map.put("city", city);
        net.post(NetConfig.PRICE_ACARUS_KILLING, map, new UtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject body = obj.getJSONObject("body");
                    int defaultNum = body.getInt("defaultNum");
                    double defaultPrice = body.getDouble("defaultPrice");
                    City.BodyBean bodyBean = (City.BodyBean) mData.get(position);
                    String def = defaultNum + "";
                    String defPri = defaultPrice + "";
                    if (!CommonUtils.isEmpty(def) && !CommonUtils.isEmpty(defPri)) {
                        Intent intent = new Intent(getContext(), OrderStudioIntroduceActivity.class);
                        intent.putExtra("defaultNum", def);
                        intent.putExtra("defaultPrice", defPri);
                        intent.putExtra("model", bodyBean);
                        startActivity(intent);
                    } else {
                        Toast.makeText(activity, "请重试", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    protected void setData() {
        super.setData();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                loadingData();
            }
        });


    }

    public static int dip2px(Context con, float dpValue) {
        final float scale = con.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /*
   * 请求加载
   * */
    private void loadingData() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", SharedPreferenceUtils.getUserData(getContext(), 1));
        map.put("city", city);
        map.put("lng", mLng);
        map.put("lat", mLat);
        map.put("type", type + "");
        if (ServiceSingleUtils.getInstance().getServiceType() == ServiceType.SERVICE_ACARUS_KILLING) {
            map.put("cmfw", "1");
        } else {
            map.put("cmfw", "0");
        }
        net.post("http://hmyc365.net/admiral/old/wx/service/stuList.htm?token=82D5FBD40259C743ADDEF14D0E22F347", map, new UtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String ret = obj.getString("ret");
                    if ("0".equals(ret)) {
                        mData.clear();
                        JSONObject body = obj.getJSONObject("body");
                        boolean lately = body.getBoolean("lately");
                        String imgPath = body.getString("imgPath");
                        boolean vip = body.getBoolean("vip");
                        if (vip) {
                            mData.add(new OrderStudioOne("已购买365服务工作室"));
                            City.BodyBean bean = new City.BodyBean();
                            JSONArray latelyStudioLists = body.getJSONArray("vipStudioList");
                            JSONObject latelyStudioList = latelyStudioLists.getJSONObject(0);
                            String id = latelyStudioList.getString("id");
                            Double pf = latelyStudioList.getDouble("pf");
                            String phone = latelyStudioList.getString("phone");
                            String username = latelyStudioList.getString("username");
                            String address = latelyStudioList.getString("address");
                            int xl = latelyStudioList.getInt("xl");
                            String name = latelyStudioList.getString("name");
                            String service = latelyStudioList.getString("service");
                            String grade = latelyStudioList.getString("grade");
                            String jl = latelyStudioList.getString("jl");
                            String introduction = latelyStudioList.getString("introduction");
                            String photo_get = latelyStudioList.getString("photo_get");
                            String city = latelyStudioList.getString("city");
                            bean.setImgPath(imgPath);
                            bean.setId(id);
                            bean.setPf(pf + "");
                            bean.setPhone(phone);
                            bean.setUsername(username);
                            bean.setAddress(address);
                            bean.setXl(xl + "");
                            bean.setName(name);
                            bean.setService(service);
                            bean.setGrade(grade);
                            bean.setJl(jl);
                            bean.setIntroduction(introduction);
                            bean.setPhoto_get(photo_get);
                            bean.setCity(city);
                            bean.setArea(CommonUtils.area(address));
                            bean.setMonthStar(latelyStudioList.getString("monthStar"));
                            mData.add(bean);
                        }
                        if (lately) {
                            mData.add(new OrderStudioOne("最近选择的工作室"));
                            City.BodyBean bean = new City.BodyBean();
                            JSONArray latelyStudioLists = body.getJSONArray("latelyStudioList");
                            JSONObject latelyStudioList = latelyStudioLists.getJSONObject(0);
                            String id = latelyStudioList.getString("id");
                            Double pf = latelyStudioList.getDouble("pf");
                            String phone = latelyStudioList.getString("phone");
                            String username = latelyStudioList.getString("username");
                            String address = latelyStudioList.getString("address");
                            int xl = latelyStudioList.getInt("xl");
                            String name = latelyStudioList.getString("name");
                            String service = latelyStudioList.getString("service");
                            String grade = latelyStudioList.getString("grade");
                            String jl = latelyStudioList.getString("jl");
                            String introduction = latelyStudioList.getString("introduction");
                            String photo_get = latelyStudioList.getString("photo_get");
                            String city = latelyStudioList.getString("city");
                            bean.setId(id);
                            bean.setPf(pf + "");
                            bean.setPhone(phone);
                            bean.setUsername(username);
                            bean.setAddress(address);
                            bean.setXl(xl + "");
                            bean.setName(name);
                            bean.setService(service);
                            bean.setGrade(grade);
                            bean.setJl(jl);
                            bean.setIntroduction(introduction);
                            bean.setPhoto_get(photo_get);
                            bean.setCity(city);
                            bean.setArea(CommonUtils.area(address));
                            bean.setImgPath(imgPath);
                            bean.setMonthStar(latelyStudioList.getString("monthStar"));
                            mData.add(bean);
                        }
                        if (mData != null && mData.size() != 0)
                            mData.add(new OrderStudioOne("其它工作室"));
                        JSONArray studioList = body.getJSONArray("studioList");
                        for (int i = 0; i < studioList.length(); i++) {
                            City.BodyBean bean = new City.BodyBean();
                            JSONObject object = studioList.getJSONObject(i);
                            String id = object.getString("id");
                            Double pf = object.getDouble("pf");
                            String phone = object.getString("phone");
                            String username = object.getString("username");
                            String address = object.getString("address");
                            int xl = object.getInt("xl");
                            String name = object.getString("name");
                            String service = object.getString("service");
                            String grade = object.getString("grade");
                            String jl = object.getString("jl");
                            String introduction = object.getString("introduction");
                            String photo_get = object.getString("photo_get");
                            String city = object.getString("city");
                            bean.setCity(city);
                            bean.setId(id);
                            bean.setPf(pf + "");
                            bean.setPhone(phone);
                            bean.setUsername(username);
                            bean.setAddress(address);
                            bean.setXl(xl + "");
                            bean.setName(name);
                            bean.setService(service);
                            bean.setGrade(grade);
                            bean.setJl(jl);
                            bean.setImgPath(imgPath);
                            bean.setIntroduction(introduction);
                            bean.setPhoto_get(photo_get);
                            bean.setArea(CommonUtils.area(address));
                            bean.setMonthStar(object.getString("monthStar"));
                            mData.add(bean);
                        }
                        int mHeight = windowsHeight - (dip2px(getContext(), 110) * mData.size());
                        int count = mHeight / dip2px(getContext(), 10);
                        for (int i = 0; i < count; i++) {
                            mData.add(new OrderStudioFill());
                        }
                        mData.add(new OrderStudioThree());
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "请刷新重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
