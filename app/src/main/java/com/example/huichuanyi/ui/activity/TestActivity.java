package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.OverLayCardLayoutManager;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

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
public class TestActivity extends BaseActivity {

    @BindView(R.id.rv_private_recommend_content)
    RecyclerView content;

    private MultiTypeAdapter mAdapter;

    private List<Visitable> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_slw_private_recommend);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        mAdapter = new MultiTypeAdapter(mData);
        content.setLayoutManager(new OverLayCardLayoutManager(this));
        content.setAdapter(mAdapter);
        setdata();
    }

    @Override
    protected void setData() {

    }

    private void setdata() {
        RequestParams pa = new RequestParams(NetConfig.GET_RECOMMEND_NEW);
        pa.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(this, 1));
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray body = object.getJSONArray("body");
                    for (int i = 0; i < body.length(); i++) {
                        JSONObject obj = body.getJSONObject(i);
                        String clothes_name = obj.getString("clothes_name");
                        String clothes_get = obj.getString("clothes_get");
                        PrivateRecommendModel privateRecommendModel = new PrivateRecommendModel();
                        /*item.setSize_name(obj.getString("size_name"));
                        item.setReason(obj.getString("reason"));
                        item.setRecommend_time(obj.getString("recommend_time"));
                        item.setPrice_dj(obj.getString("price_dj"));
                        item.setSize_get(obj.getString("size_get"));
                        item.setClothes_get(obj.getString("clothes_get"));
                        item.setColor_name(obj.getString("color_name"));
                        item.setIntroduction(obj.getString("introduction"));
                        item.setId(obj.getString("id"));
                        item.setClothes_name(obj.getString("clothes_name"));
                        item.setRecommend_id(obj.getString("recommend_id"));*/
                        mData.add(privateRecommendModel);
                    }
                    //mData.add(new PrivateRecommendModel("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", ""));
                    // mData.add(new PrivateRecommendModel("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", ""));
                    //mData.add(new PrivateRecommendModel("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", ""));
                    //mData.add(new PrivateRecommendModel("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", ""));
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


}
