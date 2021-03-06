package com.example.huichuanyi.ui.fragment.old;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huichuanyi.R;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.CustomToast;
import com.example.huichuanyi.custom.GlideCircleTransform;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.newui.activity.OrderFormActivity;
import com.example.huichuanyi.share.Share;
import com.example.huichuanyi.ui.activity.DatumActivity;
import com.example.huichuanyi.ui.activity.IndentActivity;
import com.example.huichuanyi.ui.activity.MainActivity;
import com.example.huichuanyi.ui.activity.MineSettingActivity;
import com.example.huichuanyi.ui.activity.MineStyleActivity;
import com.example.huichuanyi.ui.activity.My_365Activity;
import com.example.huichuanyi.ui.activity.ZhenDuanActivity;
import com.example.huichuanyi.ui.activity.login.LoginByAuthCodeActivity;
import com.example.huichuanyi.ui.base.BaseFragment;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.ImageUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.fragment_mainmine2)
public class FragmentMainMine2 extends BaseFragment {

    @ViewInject(R.id.iv_mine_photo)
    private ImageView mPhoto;

    @ViewInject(R.id.sv_mine_vip)
    private ImageView mVip;

    @ViewInject(R.id.tv_mine_name)
    private TextView mName;

    @ViewInject(R.id.tv_messager_is_have)
    private TextView tDian;

    private static final int REQUEST_CAMERA_CODE = 11;

    private Map<String, Object> map = new HashMap<>();

    private MainActivity activity;

    private HaveMsg haveMsg;

    @Event({R.id.iv_mine_photo, R.id.iv_mine_setting,
            R.id.ll_mine_datum, R.id.ll_mine_report, R.id.ll_mine_order, R.id.ll_mine_365,
            R.id.ll_mine_indent, R.id.ll_mine_invite, R.id.ll_mine_exit, R.id.ll_clo_zhenduan,
            R.id.ll_mine_rtc_report})
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.iv_mine_photo:
                upLoadingPhoto();
                break;
            case R.id.iv_mine_setting:
                ActivityUtils.switchTo(getActivity(), MineSettingActivity.class);
                break;
            case R.id.ll_mine_datum:
                ActivityUtils.switchTo(getActivity(), DatumActivity.class);
                break;
            case R.id.ll_mine_report:
                ActivityUtils.switchTo(getActivity(), MineStyleActivity.class);
                break;
            case R.id.ll_clo_zhenduan:
                ActivityUtils.switchTo(getActivity(), ZhenDuanActivity.class);
                break;
            case R.id.ll_mine_order:
                Intent orderIntent = new Intent(getActivity(), OrderFormActivity.class);
                orderIntent.putExtra("title", "预约订单");
                orderIntent.putExtra("orderTypePj", "1,2,3,4");
                startActivity(orderIntent);
                break;
            case R.id.ll_mine_365:
                ActivityUtils.switchTo(getActivity(), My_365Activity.class, map);
                break;
            case R.id.ll_mine_indent:
                ActivityUtils.switchTo(getActivity(), IndentActivity.class);
                break;
            case R.id.ll_mine_rtc_report:

                break;
            case R.id.ll_mine_invite:
                Share.inviteFriend(getContext());
                break;
            case R.id.ll_mine_exit:
                goExit();
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        activity = (MainActivity) getActivity();
        if (activity.isHave()) {
            tDian.setVisibility(View.VISIBLE);
        }
        haveMsg = new HaveMsg();
        activity.registerReceiver(haveMsg, new IntentFilter("action.have.msg"));
    }

    private void getUserPhoto() {
        String m365 = SharedPreferenceUtils.get365(getContext());
        if (!TextUtils.equals("365", m365)) {
            mVip.setVisibility(View.GONE);
        } else {
            mVip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();


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
                                Glide.with(getActivity()).load(SharedPreferenceUtils.getUserData(getContext(), 3)).transform(new GlideCircleTransform(getActivity())).into(mPhoto);
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
        mName.setText(SharedPreferenceUtils.getUserData(getContext(), 2));
        Glide.with(this).load(SharedPreferenceUtils.getUserData(getContext(), 3)).transform(new GlideCircleTransform(getActivity())).into(mPhoto);
        getUserPhoto();
    }

    private class HaveMsg extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int read = intent.getIntExtra("isRead", 0);
            if (read > 0) {
                tDian.setVisibility(View.VISIBLE);
            } else {
                tDian.setVisibility(View.GONE);
                activity.hideDian();
            }
        }
    }

}
