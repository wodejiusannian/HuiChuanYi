package com.example.huichuanyi.fragment_first;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.FreshPhoto;
import com.example.huichuanyi.base.BaseFragment;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.CustomToast;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.share.Share;
import com.example.huichuanyi.ui_first.MainActivity;
import com.example.huichuanyi.ui_second.DatumActivity;
import com.example.huichuanyi.ui_second.IndentActivity;
import com.example.huichuanyi.ui_second.MyOrderActivity;
import com.example.huichuanyi.ui_second.RegisterActivity;
import com.example.huichuanyi.ui_second.ReportActivity;
import com.example.huichuanyi.ui_second.SettingActivity;
import com.example.huichuanyi.ui_third.My_365Activity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.ImageUtils;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.example.huichuanyi.utils.User;
import com.facebook.drawee.view.SimpleDraweeView;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Fragment_Mine extends BaseFragment implements View.OnClickListener, FreshPhoto {
    private View view;
    private TextView mTextViewRegisterAndLogin, mTextViewDaiTi, mImageViewSetting;
    private SimpleDraweeView mPhoto,mVip;
    private LinearLayout mLinearLayoutDatum, mLinearLayoutReport,
            mLinearLayoutOrder, mLinearLayoutIndent,
            mLinearLayoutInvite, mLinearLayoutExit, m365;
    private User mUser;
    private static final int REQUEST_CAMERA_CODE = 11;
    private Map<String, Object> map = new HashMap<>();
    private int useId = -1;
    private String userPhoto;

    @Override
    protected View initView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_mine, null);
        initChildView(view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mUser = new User(getActivity());
        useId = mUser.getUseId();
        getUserPhoto();
    }

    private void getUserPhoto() {
        String m365 = MySharedPreferences.get365(getContext());
        if (!TextUtils.equals("365",m365)){
            mVip.setVisibility(View.GONE);
        }else{
            mVip.setVisibility(View.VISIBLE);
        }
        if (useId == 0) {
            return;
        }
        RequestParams params = new RequestParams(NetConfig.GET_USER_PHOTO);
        params.addBodyParameter("userid", useId + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray list = object.getJSONArray("list");
                    JSONObject jsonObject = list.getJSONObject(0);
                    String name = jsonObject.getString("name");
                    if (!TextUtils.isEmpty(name)) {
                        mTextViewRegisterAndLogin.setVisibility(View.GONE);
                        mTextViewDaiTi.setVisibility(View.VISIBLE);
                        mTextViewDaiTi.setText(name);
                    }
                    userPhoto = jsonObject.getString("photopath");
                    if (!TextUtils.isEmpty(userPhoto)) {
                        mPhoto.setImageURI(userPhoto);
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
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTextViewRegisterAndLogin.setOnClickListener(this);
        mLinearLayoutExit.setOnClickListener(this);
        mPhoto.setOnClickListener(this);
        mImageViewSetting.setOnClickListener(this);
        mLinearLayoutDatum.setOnClickListener(this);
        mLinearLayoutReport.setOnClickListener(this);
        mLinearLayoutOrder.setOnClickListener(this);
        mLinearLayoutIndent.setOnClickListener(this);
        mLinearLayoutInvite.setOnClickListener(this);
        m365.setOnClickListener(this);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setCallBack(this);
    }

    @Override
    public void onClick(View v) {
        useId = mUser.getUseId();
        switch (v.getId()) {
            case R.id.tv_mine_registerandlogin:
                goLogin();
                break;
            case R.id.iv_mine_photo:
                if (useId > 0) {
                    upLoadingPhoto();
                } else {
                    CustomToast.showToast(getContext(),"亲，请先登陆哦");
                    ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
                }
                break;
            case R.id.iv_mine_setting:
                ActivityUtils.switchTo(getActivity(), SettingActivity.class);
                break;
            case R.id.ll_mine_datum:
                if (useId > 0) {
                    ActivityUtils.switchTo(getActivity(), DatumActivity.class);
                } else {
                    ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
                }
                break;
            case R.id.ll_mine_report:
                if (useId > 0) {
                    ActivityUtils.switchTo(getActivity(), ReportActivity.class);
                } else {
                    ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
                }
                break;
            case R.id.ll_mine_order:
                if (useId > 0) {
                    ActivityUtils.switchTo(getActivity(), MyOrderActivity.class);
                } else {
                    ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
                }
                break;
            case R.id.ll_mine_365:
                if (useId > 0) {
                    map.put("userPhoto", userPhoto);
                    ActivityUtils.switchTo(getActivity(), My_365Activity.class, map);
                } else {
                    ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
                }
                break;
            case R.id.ll_mine_indent:
                if (useId > 0) {
                    ActivityUtils.switchTo(getActivity(), IndentActivity.class);
                } else {
                    ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
                }
                break;
            case R.id.ll_mine_invite:
                if (useId > 0) {
                    Share.inviteFriend(getContext());
                } else {
                    ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
                }
                break;
            case R.id.ll_mine_exit:
                goExit(useId);
                break;
        }
    }


    /*
    * 去登陆
    * */
    private void goLogin() {
        ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
    }

    private void upLoadingPhoto() {
        PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
        intent.setSelectModel(SelectModel.MULTI);
        intent.setShowCarema(true);
        intent.setMaxTotal(1);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }


    private void initChildView(View view) {
        mTextViewRegisterAndLogin = (TextView) view.findViewById(R.id.tv_mine_registerandlogin);
        mLinearLayoutExit = (LinearLayout) view.findViewById(R.id.ll_mine_exit);
        mPhoto = (SimpleDraweeView) view.findViewById(R.id.iv_mine_photo);
        mImageViewSetting = (TextView) view.findViewById(R.id.iv_mine_setting);
        mLinearLayoutDatum = (LinearLayout) view.findViewById(R.id.ll_mine_datum);
        mLinearLayoutReport = (LinearLayout) view.findViewById(R.id.ll_mine_report);
        mLinearLayoutOrder = (LinearLayout) view.findViewById(R.id.ll_mine_order);
        mLinearLayoutIndent = (LinearLayout) view.findViewById(R.id.ll_mine_indent);
        mLinearLayoutInvite = (LinearLayout) view.findViewById(R.id.ll_mine_invite);
        mTextViewDaiTi = (TextView) view.findViewById(R.id.tv_mine_daiti);
        m365 = (LinearLayout) view.findViewById(R.id.ll_mine_365);
        mVip = (SimpleDraweeView) view.findViewById(R.id.sv_mine_vip);
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
                    Log.i("TAG", "--------------"+s);
                    String photo = ImageUtils.saveBitMapToFile(getActivity(), "myPhoto", bitmap, true);
                    RequestParams params = new RequestParams(NetConfig.USER_PHOTO);
                    params.addBodyParameter("userid", new User(getActivity()).getUseId() + "");
                    params.addBodyParameter("img", new File(photo));
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("TAG", "--------------"+result);
                            if ("0".equals(result)) {
                                CustomToast.showToast(getContext(),"修改失败");
                                return;
                            } else {
                                mPhoto.setImageURI(result);
                                CustomToast.showToast(getContext(),"修改成功");
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
    }


    @Override
    public void getPhoto() {
        useId = new User(getActivity()).getUseId();
        getUserPhoto();
    }

    /*
    * 去退出
    * */
    private void goExit(int useId) {
        if (useId > 0) {
            MySelfDialog mDialog = new MySelfDialog(getActivity());
            mDialog.setMessage("退出后看不到具体数据");
            mDialog.setOnNoListener("取消", null);
            mDialog.setOnYesListener("确定", new MySelfDialog.OnYesClickListener() {
                @Override
                public void onClick() {
                    User mUser = new User(getActivity());
                    mUser.writeUserId(0);
                    mPhoto.setImageURI(Uri.parse("res://com.example.huichuanyi/" + R.mipmap.managephoto));
                    mTextViewDaiTi.setVisibility(View.GONE);
                    mTextViewRegisterAndLogin.setVisibility(View.VISIBLE);
                    MySharedPreferences.save365(getContext(), null);
                    sendBroad();
                    CustomToast.showToast(getContext(),"退出登录成功");
                }
            });
            mDialog.show();
        } else {
            ActivityUtils.switchTo(getActivity(), RegisterActivity.class);
        }
    }


    public void sendBroad() {
        Intent intent = new Intent();
        intent.setAction("action.refreshFriend");
        getActivity().sendBroadcast(intent);
    }
}
