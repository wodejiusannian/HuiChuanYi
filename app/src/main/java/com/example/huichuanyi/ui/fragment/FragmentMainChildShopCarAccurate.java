package com.example.huichuanyi.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.OverLayCardLayoutManager;
import com.example.huichuanyi.utils.RenRenCallback;
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

    @Override
    protected void setData() {
        super.setData();
        RequestParams pa = new RequestParams(NetConfig.GET_RECOMMEND_NEW);
        pa.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(getContext(), 1));
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray body = object.getJSONArray("body");
                    for (int i = 0; i < body.length(); i++) {
                        JSONObject obj = body.getJSONObject(i);
                        PrivateRecommendModel privateRecommendModel = new PrivateRecommendModel();
                        privateRecommendModel.setSize_name(obj.getString("size_name"));
                        privateRecommendModel.setReason(obj.getString("reason"));
                        privateRecommendModel.setRecommend_time(obj.getString("recommend_time"));
                        privateRecommendModel.setPrice_dj(obj.getString("price_dj"));
                        privateRecommendModel.setSize_get(obj.getString("size_get"));
                        privateRecommendModel.setClothes_get(obj.getString("clothes_get"));
                        privateRecommendModel.setColor_name(obj.getString("color_name"));
                        privateRecommendModel.setIntroduction(obj.getString("introduction"));
                        privateRecommendModel.setId(obj.getString("id"));
                        privateRecommendModel.setClothes_name(obj.getString("clothes_name"));
                        privateRecommendModel.setRecommend_id(obj.getString("recommend_id"));
                        mData.add(i, privateRecommendModel);
                    }
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
