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
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel;
import com.example.huichuanyi.common_view.model.PrivateRecommendModel2;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.ui.activity.Item_DetailsActivity;
import com.example.huichuanyi.ui.activity.SLWRecordActivity;
import com.example.huichuanyi.utils.AsyncHttpUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.HttpCallBack;
import com.example.huichuanyi.utils.HttpUtils;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.MUtilsInternet;
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
        RequestParams pa = new RequestParams(NetConfig.SHOPCAR_MANAGER_RECOMMEND);
        pa.addBodyParameter("buyUserId", SharedPreferenceUtils.getUserData(getContext(), 1));
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray body = object.getJSONArray("body");
                    for (int i = 0; i < body.length(); i++) {
                        JSONObject obj = body.getJSONObject(i);
                        PrivateRecommendModel2 privateRecommendModel = new PrivateRecommendModel2();
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
        mAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = (int) v.getTag();
                goNext(p);
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

    @OnClick({R.id.hm_365_clothes_dislike, R.id.hm_365_clothes_like, R.id.tv_slw_private_recommend_history, R.id.btn_365_seven_refuse_data})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.hm_365_clothes_dislike:
                final int p = content.getAdapter().getItemCount() - 1;
                PrivateRecommendModel2 model1 = (PrivateRecommendModel2) mData.get(p);
                String id = model1.getId();
                if (!CommonUtils.isEmpty(id)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("buyUserId", SharedPreferenceUtils.getUserData(getContext(), 1));
                    map.put("token", NetConfig.TOKEN);
                    map.put("idPj", model1.getId());
                    net.post(NetConfig.SHOPCAR_DELETE_SHOP, getContext(), map, new MUtilsInternet.XCallBack() {
                        @Override
                        public void onResponse(String result) {
                            String ret = JsonUtils.getRet(result);
                            if ("0".equals(ret)) {
                                mData.remove(p);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.btn_365_seven_refuse_data:
                showStudioDialog(4);
                break;
            case R.id.hm_365_clothes_like:
                if (mData != null && mData.size() > 0) {
                    int i11 = content.getAdapter().getItemCount() - 1;
                    goNext(i11);
                }
                break;
            case R.id.tv_slw_private_recommend_history:
                Intent intent = new Intent(getContext(), SLWRecordActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void goNext(int i11) {
        PrivateRecommendModel2 item = (PrivateRecommendModel2) mData.get(i11);
        if (!CommonUtils.isEmpty(item.getId())) {
            PrivateRecommendModel bean = new PrivateRecommendModel();
            bean.setId(item.getId());
            bean.setClothes_get(item.getClothes_get());
            bean.setClothes_name(item.getColor_name());
            bean.setColor(item.getColor());
            bean.setColor_name(item.getColor_name());
            bean.setIntroduction(item.getIntroduction());
            bean.setPrice_dj(item.getPrice_dj());
            bean.setReason(item.getReason());
            bean.setRecommend_id(item.getRecommend_id());
            bean.setRecommend_time(item.getRecommend_time());
            bean.setSize_get(item.getSize_get());
            bean.setSize_name(item.getSize_name());
            Intent in = new Intent(getContext(), Item_DetailsActivity.class);
            in.putExtra("bean", bean);
            startActivity(in);
        }
    }

    public void showStudioDialog(int userEvent) {
        Map map = new HashMap();
        map.put("studio_id", studio_id);
        map.put("user_id", SharedPreferenceUtils.getUserData(getContext(), 1));
        map.put("demandType", "衣服推荐");
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

    private MUtilsInternet net = MUtilsInternet.getInstance();
}
