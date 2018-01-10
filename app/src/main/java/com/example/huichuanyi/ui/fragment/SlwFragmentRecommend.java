package com.example.huichuanyi.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.ui.activity.Item_DetailsActivity;
import com.example.huichuanyi.ui.activity.SLWRecordActivity;
import com.example.huichuanyi.utils.AsyncHttpUtils;
import com.example.huichuanyi.utils.HttpCallBack;
import com.example.huichuanyi.utils.HttpUtils;
import com.example.huichuanyi.utils.JsonUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

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
public class SlwFragmentRecommend extends BaseFragment {


    @Override
    protected int layoutInflaterId() {
        return R.layout.fragment_slw_private_recommend;
    }

    @BindView(R.id.rv_private_recommend_content)
    RecyclerView content;

    @BindView(R.id.rl_private_recommend_no_data)
    RelativeLayout rlNoData;

    private MultiTypeAdapter mAdapter;

    private List<Visitable> mData = new ArrayList<>();

    private RenRenCallback callback = new RenRenCallback();


    @BindView(R.id.tv_private_recommed_day)
    TextView days;

    private String studio_name, studio_id;


    @Override
    protected void initData() {
        super.initData();
        mAdapter = new MultiTypeAdapter(mData);
        content.setLayoutManager(new OverLayCardLayoutManager(getContext()));
        content.setAdapter(mAdapter);
        int day = getArguments().getInt("day");
        studio_name = getArguments().getString("studio_name");
        studio_id = getArguments().getString("studio_id");
        days.setText(Html.fromHtml("仍可以享受 <<font color=\"#ac0000\">" + day + "</font>天服饰推荐服务"));
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (0 == what)
                rlNoData.setVisibility(View.VISIBLE);
        }
    };

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
                    if (mData != null && mData.size() == 0) {
                        mHandler.sendEmptyMessage(0);
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

    @Override
    protected void initEvent() {
        super.initEvent();

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

    @OnClick({R.id.hm_365_clothes_dislike, R.id.hm_365_clothes_like, R.id.tv_slw_private_recommend_history, R.id.btn_365_seven_refuse_data})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.hm_365_clothes_dislike:
                final int i = content.getAdapter().getItemCount() - 1;
                PrivateRecommendModel privateRecommendModel = (PrivateRecommendModel) mData.get(i);
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_id", SharedPreferenceUtils.getUserData(getContext(), 1));
                map.put("rec_id", privateRecommendModel.getRecommend_id());
                String json = HttpUtils.toJson(map);
                new AsyncHttpUtils(new HttpCallBack() {
                    @Override
                    public void onResponse(String result) {
                        String ret = JsonUtils.getRet(result);
                        if ("0".equals(ret)) {
                            mData.remove(i);
                            mAdapter.notifyDataSetChanged();
                            if (mData != null && mData.size() == 0) {
                                mHandler.sendEmptyMessage(0);
                            }
                        }
                    }
                }, getActivity()).execute("http://hmyc365.net/HM/bg/hmyc/vip/info/deleteRecClo.do", json);
              /*  callback.toLeft(content, new RenRenCallback.SwipePosition() {
                    @Override
                    public void swipePosition(final int p) {

                    }
                });*/
                break;
            case R.id.btn_365_seven_refuse_data:
                showStudioDialog(4);
                break;
            case R.id.hm_365_clothes_like:
                int i11 = content.getAdapter().getItemCount() - 1;
                PrivateRecommendModel item = (PrivateRecommendModel) mData.get(i11);
                Intent in = new Intent(getContext(), Item_DetailsActivity.class);
                String clothes_get = item.getClothes_get();
                String color = item.getColor();
                String color_name = item.getColor_name();
                String id = item.getId();
                String introduction = item.getIntroduction();
                String price_dj = item.getPrice_dj();
                String reason = item.getReason();
                String size_name = item.getSize_name();
                String clothes_name = item.getClothes_name();
                String recommend_id = item.getRecommend_id();
                in.putExtra("clothes_get", clothes_get);
                in.putExtra("color", color);
                in.putExtra("color_name", color_name);
                in.putExtra("id", id);
                in.putExtra("type", "3");
                in.putExtra("introduction", introduction);
                in.putExtra("price_dj", price_dj);
                in.putExtra("name", clothes_name);
                in.putExtra("reason", reason);
                in.putExtra("size_name", size_name);
                in.putExtra("recommend_id", recommend_id);
                startActivity(in);
               /* callback.toRight(content, new RenRenCallback.SwipePosition() {
                    @Override
                    public void swipePosition(int p) {

                    }
                });*/
                break;
            case R.id.tv_slw_private_recommend_history:
                Intent intent = new Intent(getContext(), SLWRecordActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void showStudioDialog(int userEvent) {
        Map map = new HashMap();
        map.put("studio_id", studio_id);
        map.put("user_id", SharedPreferenceUtils.getUserData(getContext(), 1));
        map.put("demandType", userEvent + "");
        String json = HttpUtils.toJson(map);
        new AsyncHttpUtils(new HttpCallBack() {
            @Override
            public void onResponse(String result) {
                String msg = "提示：已向 %s 工作室发送短信通知，您还可以选择咨询 %s 工作室！";
                msg = String.format(msg, studio_name, studio_name);
                MySelfDialog mDialog = new MySelfDialog(getActivity());
                mDialog.setMessage(msg);
                mDialog.setOnNoListener("取消", null);
                mDialog.setOnYesListener("去联系", new MySelfDialog.OnYesClickListener() {
                    @Override
                    public void onClick() {
                        chat();
                    }
                });
                mDialog.show();
            }
        }, getActivity()).execute("http://hmyc365.net/HM/bg/hmyc/vip/info/noticeStudio.do", json);
    }

    private void chat() {
        RequestParams params = new RequestParams(NetConfig.IS_BUY_365);
        params.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(getContext(), 1));
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject body = object.getJSONObject("body");
                    String studio_name = body.getString("studio_name");
                    String studio_id = body.getString("studio_id");
                    RongIM im = RongIM.getInstance();
                    if (im != null && studio_id != null) {
                        im.startPrivateChat(getContext(), "hmgls_" + studio_id, studio_name);
                    }
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

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
}
