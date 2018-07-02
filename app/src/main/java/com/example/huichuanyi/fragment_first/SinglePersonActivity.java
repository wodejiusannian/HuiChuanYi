package com.example.huichuanyi.fragment_first;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.Fresh_365;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.SlwEightModel;
import com.example.huichuanyi.common_view.model.SlwFiveModel;
import com.example.huichuanyi.common_view.model.SlwFourModel;
import com.example.huichuanyi.common_view.model.SlwSevenModle;
import com.example.huichuanyi.common_view.model.SlwSixModel;
import com.example.huichuanyi.common_view.model.SlwTwoModel;
import com.example.huichuanyi.common_view.model.Visitable;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.MySelfDialog;
import com.example.huichuanyi.custom.SlwWebView;
import com.example.huichuanyi.ui.SlwGoActivity;
import com.example.huichuanyi.ui.activity.DatumActivity;
import com.example.huichuanyi.ui.activity.LiJiYuYueActivity;
import com.example.huichuanyi.ui.activity.PrivateManagerActivity;
import com.example.huichuanyi.ui.activity.video.HMWebSlwActivity;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.AsyncHttpUtils;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.HttpCallBack;
import com.example.huichuanyi.utils.HttpUtils;
import com.example.huichuanyi.utils.RxBus;
import com.example.huichuanyi.utils.SharedPreferenceUtils;

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
import rx.functions.Action1;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

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
public class SinglePersonActivity extends BaseActivity implements MySelfDialog.OnYesClickListener, Fresh_365 {


    @BindView(R.id.rv_fragment_365_content)
    RecyclerView rvContent;

    @BindView(R.id.sf_fragment_365_content)
    SwipeRefreshLayout sFresh;

    @BindView(R.id.web_fragment_365_no_pay)
    SlwWebView webView;

    @BindView(R.id.rl_fragment_365_no_pay)
    RelativeLayout noPay;

    private int count;


    private List<Visitable> mData = new ArrayList<>();

    private boolean isHappyReport = false;

    MultiTypeAdapter adapter;

    private String studio_name;
    private String studio_id;
    private String studio_pic;
    String vip_endDate;


    @Override
    protected void setData() {
        sFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initNet();
                sFresh.setRefreshing(false);
            }
        });
        webView.setOnScrollChangedCallback(new SlwWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                if (t == 0) {//webView在顶部
                    sFresh.setEnabled(true);
                } else {//webView不是顶部
                    sFresh.setEnabled(false);
                }
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_365_2);
    }

    @Override
    protected void setListener() {
        RxBus.getDefault().toObservable(Integer.class).
                subscribe(new Action1<Integer>() {
                              @Override
                              public void call(Integer userEvent) {
                                  if (10081 == userEvent || 10082 == userEvent || 10087 == userEvent || 10088 == userEvent || 10000 == userEvent) {
                                      Intent intent = new Intent(SinglePersonActivity.this, PrivateManagerActivity.class);
                                      intent.putExtra("userEvent", userEvent);
                                      intent.putExtra("vip_endDate", vip_endDate);
                                      intent.putExtra("studio_name", studio_name);
                                      intent.putExtra("studio_id", studio_id);
                                      startActivity(intent);
                                  } else if (100891 == userEvent || 100892 == userEvent || 100893 == userEvent || 100894 == userEvent) {
                                      userEvent = userEvent - 10089;
                                      showStudioDialog(userEvent);
                                  } else if (10086111 == userEvent) {
                                      Intent intent = new Intent(SinglePersonActivity.this, SlwGoActivity.class);
                                      intent.putExtra("studio_name", studio_name);
                                      intent.putExtra("studio_pic", studio_pic);
                                      startActivity(intent);
                                  } else {
                                      chat();
                                  }
                              }
                          },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                // TODO: 处理异常
                            }
                        });
    }

    @Override
    protected void initData() {
        if (mData == null)
            mData = new ArrayList<>();

        adapter = new MultiTypeAdapter(mData);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(adapter);

        initNet();
    }

    /*
    * 数据请求处理
    * */
    private void initNet() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", SharedPreferenceUtils.getUserData(this, 1));
        String json = HttpUtils.toJson(map);
        new AsyncHttpUtils(new HttpCallBack() {
            @Override
            public void onResponse(String result) {
                showContent(result);
            }
        }, this).execute(NetConfig.SLW_DATA, json);
    }

    /*
    * 在这里进行数据展示
    * */
    private void showContent(String result) {
        try {
            mData.clear();
            JSONObject obj = new JSONObject(result);
            JSONObject body = obj.getJSONObject("body");
            String vip_state = body.getString("vip_state");
            /*
            * 这个是购买了365服务的
            * */
            if ("Y".equals(vip_state) || "y".equals(vip_state)) {
                noPay.setVisibility(View.GONE);
                int body_data = body.getInt("body_data_num");
                int style_report = body.getInt("style_report_num");
                int wardrobe_diagnosis = body.getInt("wardrobe_diagnosis_num");
                int rec_clothes_num = body.getInt("rec_clothes_num");
                JSONObject vip_info = body.getJSONObject("vip_info");
                studio_pic = vip_info.getString("studio_headPic");
                studio_name = vip_info.getString("studio_name");
                studio_id = vip_info.getString("studio_id");
                vip_endDate = vip_info.getString("vip_endDate");
                String rec_cloName = vip_info.getString("rec_cloName");
                String rec_cloPic = vip_info.getString("rec_cloPic");
                String rec_reason = vip_info.getString("rec_reason");
                String rec_totalNum = (body_data + style_report + wardrobe_diagnosis + rec_clothes_num) + "";
                SlwFiveModel slwFiveModel = new SlwFiveModel(studio_pic, studio_name, rec_totalNum);
                SlwSixModel slwSixModel = new SlwSixModel(rec_cloName, rec_cloPic, rec_reason, rec_clothes_num + "");
                List<SlwEightModel.Eight> data1 = new ArrayList<>();
                List<SlwFourModel.Four> data2 = new ArrayList<>();
                if (body_data > 0) {
                    data1.add(new SlwEightModel.Eight(R.mipmap.hm_365_guidance_measure, R.mipmap.hm_365_yet_pay_yet, 10081, "上门量体", 0));
                } else {
                    data1.add(new SlwEightModel.Eight(R.mipmap.hm_365_guidance_measure, R.mipmap.hmyc_no_order, 10081, "上门量体", 0));
                }

                if (style_report > 0) {
                    data1.add(new SlwEightModel.Eight(R.mipmap.hm_365_guidance_style, R.mipmap.hm_365_yet_pay_yet, 10082, "风格报告", 0));
                } else {
                    data1.add(new SlwEightModel.Eight(R.mipmap.hm_365_guidance_style, R.mipmap.hm_365_yet_pay_no_yet, 10082, "风格报告", 0));
                }

                data1.add(new SlwEightModel.Eight(R.mipmap.hm_365_guidance_chat, R.mipmap.hm_365_yet_pay_no_yet, 10086, "在线咨询", count));

                if (wardrobe_diagnosis > 0) {
                    data2.add(new SlwFourModel.Four(R.mipmap.hm_365_manager_diagnosis_report, R.mipmap.hm_365_yet_pay_yet, 10087, "衣橱诊断报告", 0));
                } else {
                    data2.add(new SlwFourModel.Four(R.mipmap.hm_365_manager_diagnosis_report, R.mipmap.hm_365_yet_pay_no_yet, 10087, "衣橱诊断报告", 0));
                }

                data2.add(new SlwFourModel.Four(R.mipmap.hm_365_manager_statisitic_report, R.mipmap.hm_365_yet_pay_yet, 10088, "衣橱数据统计报告", 0));

                SlwEightModel slwFourModel = new SlwEightModel(data1, "着装指导");
                SlwFourModel slwFourMode2 = new SlwFourModel(data2, "衣橱管理");
                mData.add(slwFiveModel);
                String urlTitle = body.getString("urlTitle");
                String problemPic = body.getString("problemPic");
                String picOnclickUrl = body.getString("picOnclickUrl");
                if (!CommonUtils.isEmpty(urlTitle)) {
                    SlwTwoModel slwTwoModel = new SlwTwoModel(picOnclickUrl, problemPic, urlTitle);
                    mData.add(slwTwoModel);
                    isHappyReport = true;
                }
                if (CommonUtils.isEmpty(rec_cloName)) {
                    mData.add(new SlwSevenModle());
                } else {
                    mData.add(slwSixModel);
                }
                mData.add(slwFourModel);
                mData.add(slwFourMode2);
            } else {
                noPay.setVisibility(View.VISIBLE);
                loadindUrl(webView, "http://hmyc365.net/file/html/app/vipIntroduce/index.html");
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "请检查网络连接", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick({R.id.btn_fragment_365_go_pay})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_fragment_365_go_pay:
                goOpen();
                break;
            default:
                break;
        }
    }


    private void chat() {
        RequestParams params = new RequestParams(NetConfig.IS_BUY_365);
        params.addBodyParameter("user_id", SharedPreferenceUtils.getUserData(this, 1));
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
                        im.startPrivateChat(SinglePersonActivity.this, "hmgls_" + studio_id, studio_name);
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

    public void goOpen() {
        RequestParams params = new RequestParams(NetConfig.GET_INFORMATION);
        params.addBodyParameter("userid", SharedPreferenceUtils.getUserData(this, 1));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.equals("0", result)) {
                    return;
                }
                try {
                    JSONObject object = new JSONObject(result);
                    String name = object.getString("name");
                    String sex = object.getString("sex");
                    String city = object.getString("city");
                    String phone = object.getString("phone");
                    if (CommonUtils.isEmpty(name) || CommonUtils.isEmpty(city) || CommonUtils.isEmpty(sex) || CommonUtils.isEmpty(phone)) {
                        showDialog();
                    } else {
                        goDredge();
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

    private void showDialog() {
        MySelfDialog mySelfDialog = new MySelfDialog(this);
        mySelfDialog.setTitle("温馨提示");
        mySelfDialog.setMessage("★  为了您更好的享受慧美衣橱各项服务，姓名，性别，城市，手机号四项为必填项，请您填写清楚");
        mySelfDialog.setOnNoListener("取消", null);
        mySelfDialog.setOnYesListener("去完善资料", this);
        mySelfDialog.show();
    }


   /* private void showDialogGo() {
        MySelfDialog mySelfDialog = new MySelfDialog(this);
        mySelfDialog.setTitle("温馨提示");
        mySelfDialog.setMessage("★  开通365服务后工作室将会看到您衣橱信息和个人资料信息");
        mySelfDialog.setOnNoListener("取消", null);
        mySelfDialog.setOnYesListener("确定", new MySelfDialog.OnYesClickListener() {
            @Override
            public void onClick() {
                goDredge();
            }
        });
        mySelfDialog.show();
    }*/

    private void goDredge() {
        String city = SharedPreferenceUtils.getBuyCity(this);
        Map<String, Object> map = new HashMap<>();
        map.put("location", city);
        map.put("order_365", "365");
        ActivityUtils.switchTo(this, LiJiYuYueActivity.class, map);
    }

    @Override
    public void onClick() {
        ActivityUtils.switchTo(this, DatumActivity.class);
    }

    private void loadindUrl(WebView web, String url) {
        WebSettings webSettings = web.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(LOAD_NO_CACHE);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if ("http://www.huimei.com/problem/normal".equals(url)) {
                    Intent in = new Intent(SinglePersonActivity.this, HMWebSlwActivity.class);
                    startActivity(in);
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
        WebChromeClient wvcc = new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {

                } else {

                }

            }

        };
        web.setWebChromeClient(wvcc);
        if (url != null) {
            web.loadUrl(url);
        }
    }


    public void showStudioDialog(int userEvent) {
        Map map = new HashMap();
        map.put("studio_id", studio_id);
        map.put("user_id", SharedPreferenceUtils.getUserData(this, 1));
        map.put("demandType", "衣服推荐");
        String json = HttpUtils.toJson(map);
        new AsyncHttpUtils(new HttpCallBack() {
            @Override
            public void onResponse(String result) {
                String msg = "提示：已向 %s 工作室发送短信通知，您还可以选择咨询 %s 工作室！";
                msg = String.format(msg, studio_name, studio_name);
                MySelfDialog mDialog = new MySelfDialog(SinglePersonActivity.this);
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
        }, this).execute("http://hmyc365.net/HM/bg/hmyc/vip/info/noticeStudio.do", json);
    }

    @Override
    public void reFresh365() {
        initNet();
    }

    private class HaveMsg extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                int read = intent.getIntExtra("isRead", 0);
                if (read > 0) {
                    count += read;
                } else {
                    count = 0;
                }
                List<Visitable> data = new ArrayList<>();
                data.clear();
                if (isHappyReport) {
                    for (int i = 0; i < mData.size(); i++) {
                        if (i != 3) {
                            data.add(mData.get(i));
                        } else {
                            SlwEightModel visitable = (SlwEightModel) mData.get(3);
                            visitable.getData().get(2).count = count;
                            data.add(visitable);
                        }
                    }
                } else {
                    for (int i = 0; i < mData.size(); i++) {
                        if (i != 2) {
                            data.add(mData.get(i));
                        } else {
                            SlwEightModel visitable = (SlwEightModel) mData.get(2);
                            visitable.getData().get(2).count = count;
                            data.add(visitable);
                        }
                    }
                }
                mData.clear();
                mData.addAll(data);
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
