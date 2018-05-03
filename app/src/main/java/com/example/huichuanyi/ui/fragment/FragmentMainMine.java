package com.example.huichuanyi.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.bean.UserInfo;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.CustomToast;
import com.example.huichuanyi.custom.GlideCircleTransform;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.newui.activity.OrderFormActivity;
import com.example.huichuanyi.newui.activity.OrderFormVideoActivity;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.ui.activity.MineSettingActivity;
import com.example.huichuanyi.ui.activity.login.LoginByAuthCodeActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.ImageUtils;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UpdateUtils;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class FragmentMainMine extends BaseFragment {

    @OnClick({R.id.iv_mainmine_photo, R.id.iv_mainmine_setting, R.id.tv_mainmine_orderform, R.id.tv_mainmine_clothesform,
            R.id.tv_mainmine_blackform, R.id.tv_mainmine_videoform, R.id.tv_mainmine_exit, R.id.tv_mainmine_refresh})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.iv_mine_photo:
                upLoadingPhoto();
                break;
            case R.id.iv_mainmine_setting:
                ActivityUtils.switchTo(getActivity(), MineSettingActivity.class);
                break;
            case R.id.tv_mainmine_orderform:
                Intent orderIntent = new Intent(getActivity(), OrderFormActivity.class);
                orderIntent.putExtra("title", "预约订单");
                startActivity(orderIntent);
                break;
            case R.id.tv_mainmine_clothesform:
                Intent clothesIntent = new Intent(getActivity(), OrderFormActivity.class);
                clothesIntent.putExtra("title", "服饰订单");
                startActivity(clothesIntent);
                break;
            case R.id.tv_mainmine_blackform:
                Intent blackIntent = new Intent(getActivity(), OrderFormActivity.class);
                blackIntent.putExtra("title", "黑科技订单");
                startActivity(blackIntent);
                break;
            case R.id.tv_mainmine_videoform:
                Intent videoIntent = new Intent(getActivity(), OrderFormVideoActivity.class);
                startActivity(videoIntent);
                break;
            case R.id.tv_mainmine_exit:
                goExit();
                break;
            case R.id.tv_mainmine_refresh:
                UpdateUtils up = UpdateUtils.getInstance(getContext());
                up.update(true);
                up.setOnUpListener(new UpdateUtils.Update() {
                    @Override
                    public void update() {
                        mHandler.sendEmptyMessage(1);
                    }
                });
                break;
        }
    }


    private static final int REQUEST_CAMERA_CODE = 11;

    private MainActivity activity;

    private HaveMsg haveMsg;

    @Override
    protected int layoutInflaterId() {
        return R.layout.fragment_mainmine;
    }

    @Override
    protected void initData() {
        super.initData();
        activity = (MainActivity) getActivity();
        /*if (activity.isHave()) {
            tDian.setVisibility(View.VISIBLE);
        }*/
        haveMsg = new HaveMsg();
        activity.registerReceiver(haveMsg, new IntentFilter("action.have.msg"));
    }

    /*private void getUserPhoto() {
        String m365 = SharedPreferenceUtils.get365(getContext());
        if (!TextUtils.equals("365", m365)) {
            mVip.setVisibility(View.GONE);
        } else {
            mVip.setVisibility(View.VISIBLE);
        }
    }*/

    @BindView(R.id.refresh_view)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void initEvent() {
        super.initEvent();
        getInfo();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInfo();
                mHandler.sendEmptyMessageDelayed(0, 3000);
            }
        });
    }


    private void upLoadingPhoto() {
        PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
        intent.setSelectModel(SelectModel.MULTI);
        intent.setShowCarema(true);
        intent.setMaxTotal(1);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    String s = list.get(0);
                    Bitmap bitmap = ImageUtils.ratio(s, 240f, 240f);
                    String photo = ImageUtils.saveBitMapToFile(getActivity(), "myPhoto", bitmap, true);
                    RequestParams params = new RequestParams(NetConfig.USER_PHOTO);
                    params.addBodyParameter("userid", SharedPreferenceUtils.getUserData(getContext(), 1));
                    params.addBodyParameter("img", new File(photo));
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            if ("0".equals(result)) {
                                CustomToast.showToast(getContext(), "修改失败");
                                return;
                            } else {
                                Glide.with(getActivity()).load(SharedPreferenceUtils.getUserData(getContext(), 3)).transform(new GlideCircleTransform(getActivity())).into(ivInfo[1]);
                                SharedPreferenceUtils.writeUserPhoto(getContext(), result);
                                CustomToast.showToast(getContext(), "修改成功");
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
                    break;

            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(haveMsg);
    }


    /*
    * 去退出
    * */
    private void goExit() {
        MySelfDialog mDialog = new MySelfDialog(getActivity());
        mDialog.setMessage("退出后看不到具体数据");
        mDialog.setOnNoListener("取消", null);
        mDialog.setOnYesListener("确定", new MySelfDialog.OnYesClickListener() {
            @Override
            public void onClick() {
                CustomToast.showToast(getContext(), "退出登录成功");
                SharedPreferenceUtils.writeUserId(getContext(), null);
                SharedPreferenceUtils.save365(getContext(), null);
                ActivityUtils.switchTo(getActivity(), LoginByAuthCodeActivity.class);
                getActivity().finish();
            }
        });
        mDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // mName.setText(SharedPreferenceUtils.getUserData(getContext(), 2));
        //Glide.with(this).load(SharedPreferenceUtils.getUserData(getContext(), 3)).transform(new GlideCircleTransform(getActivity())).into(mPhoto);
        //getUserPhoto();
    }

    private class HaveMsg extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            /*int read = intent.getIntExtra("isRead", 0);
            if (read > 0) {
                tDian.setVisibility(View.VISIBLE);
            } else {
                tDian.setVisibility(View.GONE);
                activity.hideDian();
            }*/
        }
    }

    private MUtilsInternet net = MUtilsInternet.getInstance();


    @BindViews({R.id.tv_mine_name, R.id.tv_mine_city, R.id.tv_mine_occupation, R.id.tv_mine_charactor})
    TextView[] tvInfo;

    @BindViews({R.id.tv_mainmine_couponid, R.id.tv_mainmine_couponstate})
    TextView[] couponInfo;

    @BindViews({R.id.tv_mainmine_managername, R.id.tv_mainmine_managerservicetime})
    TextView[] managerInfo;

    @BindViews({R.id.iv_mainmine_photobg, R.id.iv_mainmine_photo})
    ImageView[] ivInfo;

    @BindViews({R.id.rl_mainmine_openvip, R.id.tl_mainmine_noopenvip})
    RelativeLayout[] relativeLayout;

    @BindView(R.id.iv_mainmine_managerphoto)
    ImageView managerPhoto;

    private void getInfo() {
        final Context context = getContext();
        Map<String, String> map = new HashMap<>();
        map.put("userId", SharedPreferenceUtils.getUserData(context, 1));
        map.put("token", NetConfig.TOKEN);
        net.post(NetConfig.USER_INFO, context, map, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                refreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                UserInfo bean = gson.fromJson(result, UserInfo.class);
                UserInfo.BodyBean body = bean.getBody();
                tvInfo[0].setText(body.getUserName());
                tvInfo[1].setText(body.getUserCity());
                tvInfo[2].setText(body.getUserOccupation());
                tvInfo[3].setText(body.getUserCharactor());
                Glide.with(context).load(body.getUserPic()).centerCrop().bitmapTransform(new BlurTransformation(context)).into(ivInfo[0]);
                Glide.with(context).load(body.getManagerUrl()).transform(new GlideCircleTransform(context)).error(R.mipmap.stand).into(managerPhoto);
                String concessionCode = body.getConcessionCode();
                String vipEndDate = body.getVipEndDate();
                if (CommonUtils.isEmpty(vipEndDate)) {
                    relativeLayout[1].setVisibility(View.VISIBLE);
                    relativeLayout[0].setVisibility(View.GONE);
                } else {
                    relativeLayout[0].setVisibility(View.VISIBLE);
                    relativeLayout[1].setVisibility(View.GONE);
                    String managerName = body.getManagerName();
                    managerInfo[0].setText(managerName);
                    managerInfo[1].setText(vipEndDate + "到期");
                    Glide.with(context).load(body.getUserPic()).transform(new GlideCircleTransform(context)).error(R.mipmap.stand).into(ivInfo[1]);
                }
                if (CommonUtils.isEmpty(concessionCode)) {
                    //为空的时候
                    couponInfo[0].setText("无优惠券");
                    couponInfo[1].setText("");
                } else {
                    //非空的时候
                    couponInfo[0].setText(concessionCode);
                    String deleteStatus = body.getDeleteStatus();
                    if (deleteStatus.equals("1")) {
                        couponInfo[1].setText("已使用");
                    } else {
                        String concessionMoney = body.getConcessionMoney();
                        couponInfo[1].setText(concessionMoney);
                    }
                }

            }
        });
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (0 == what) {
                if (refreshLayout.isRefreshing())
                    refreshLayout.setRefreshing(false);
            } else if (1 == what) {
                Toast.makeText(activity, "已是最新版本了", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
