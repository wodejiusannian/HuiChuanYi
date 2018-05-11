package com.example.huichuanyi.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.baidumap.Location;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.Video;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.custom.EditDialog;
import com.example.huichuanyi.share.Share;
import com.example.huichuanyi.ui.activity.video.FullVideoActivity;
import com.example.huichuanyi.ui.activity.video.HomeVideoYouhuiquanActivity;
import com.example.huichuanyi.ui.activity.video.widget.MediaHelp;
import com.example.huichuanyi.ui.activity.video.widget.VideoSuperPlayer;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.JsonUtils;
import com.example.huichuanyi.utils.ReminderUtils;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeVideoActivity extends BaseActivity implements UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener, EditDialog.EditYes {

    @ViewInject(R.id.lv_video)
    private ListView videos;

    @ViewInject(R.id.sf_refresh)
    private SwipeRefreshLayout refreshLayout;

    private Map<String, String> map = new HashMap<>();
    private ArrayList<Video.BodyBean> mData = new ArrayList<>();

    private UtilsInternet net = UtilsInternet.getInstance();

    private String cover_id, user_id, promo_code;
    private boolean isPlaying;
    private int indexPosition = -1;
    private VideoAdapter adapter;
    private boolean is;
    private int isFlag = 1;

    private int f = 0;

    private Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int arg1 = msg.arg1;
            if (arg1 == 1) {
                for (Video.BodyBean bean : mData) {
                    if (CommonUtils.isEmpty(bean.getUser_id())) {
                        is = true;
                        break;
                    }
                }
                if (Location.isHaveActive && is) {
                    EditDialog editDialog = new EditDialog(HomeVideoActivity.this);
                    editDialog.setOnClickNo("取消");
                    editDialog.setOnClickYes("确定", HomeVideoActivity.this);
                    editDialog.show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_video);
    }

    @Override
    public void setListener() {
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void initView() {
        cover_id = getIntent().getStringExtra("id");
        user_id = SharedPreferenceUtils.getUserData(this, 1);
        loadData();
    }

    @Override
    public void initData() {
        refreshLayout.setRefreshing(true);

    }

    @Override
    public void setData() {
        adapter = new VideoAdapter(this);
        videos.setAdapter(adapter);
        videos.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if ((indexPosition < videos.getFirstVisiblePosition() || indexPosition > videos
                        .getLastVisiblePosition()) && isPlaying) {
                    indexPosition = -1;
                    isPlaying = false;
                    adapter.notifyDataSetChanged();
                    MediaHelp.release();
                }
            }
        });
    }

    public void back(View view) {
        if (view != null) {
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (f == 1) {
            isFlag = 1;
            loadData();
        }
    }

    @Override
    protected void onDestroy() {
        MediaHelp.release();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        MediaHelp.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        MediaHelp.pause();
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MediaHelp.getInstance().seekTo(data.getIntExtra("position", 0));
    }

    @Override
    public void onResponse(String result) {
        switch (isFlag) {
            case 1:
                mData.clear();
                Gson gson = new Gson();
                Video video = gson.fromJson(result, Video.class);
                mData.addAll(video.getBody());
                adapter.notifyDataSetChanged();
                Message msg = Message.obtain();
                msg.arg1 = 1;
                mHander.sendMessage(msg);
                refreshLayout.setRefreshing(false);
                break;
            case 2:
                isFlag = 1;
                try {
                    JSONObject object = new JSONObject(result);
                    String ret = object.getString("ret");
                    switch (ret) {
                        case "0":
                            if (mData != null && mData.size() > 0) {
                                f = 1;
                                JSONObject body = object.getJSONObject("body");
                                String video_price = body.getString("video_price");
                                Intent intent = new Intent(HomeVideoActivity.this, HomeVideoYouhuiquanActivity.class);
                                intent.putExtra("list", mData);
                                intent.putExtra("promo_code", promo_code);
                                intent.putExtra("video_price", video_price);
                                startActivity(intent);
                            }
                            break;
                        case "1013":
                            ReminderUtils.Toast(this, "优惠码错误");
                            break;
                        case "1014":
                            ReminderUtils.Toast(this, "活动已过期");
                            break;
                        case "1016":
                            ReminderUtils.Toast(this, "优惠码已被使用");
                            break;
                        default:
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:

                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    private void loadData() {
        map.put("cover_id", cover_id);
        map.put("user_id", user_id);
        net.post(NetConfig.GET_SHIPIN_LIST, map, this);
    }

    @Override
    public void getEdit(String edit) {
        if (!CommonUtils.isEmpty(edit)) {
            isFlag = 2;
            promo_code = edit;
            map.put("promo_code", edit);
            net.post(NetConfig.GET_YOUHUIQUAN, map, this);
        } else {
            Toast.makeText(this, "请输入优惠码", Toast.LENGTH_SHORT).show();
        }
    }


    class VideoAdapter extends BaseAdapter {
        private Context context;
        LayoutInflater inflater;

        public VideoAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return mData.size();
        }


        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            GameVideoViewHolder holder = null;
            if (v == null) {
                v = inflater.inflate(R.layout.video_item, parent, false);
                holder = new GameVideoViewHolder(v);
                v.setTag(holder);
            } else {
                holder = (GameVideoViewHolder) v.getTag();
            }
            Video.BodyBean listBean = mData.get(position);
            String user_id = listBean.getUser_id();
            if (CommonUtils.isEmpty(user_id)) {
                holder.isPay.setText("加入购物车");
            } else {
                holder.isPay.setText("已购买");
            }
            String icon = listBean.getVideo_pic();
            holder.pic.setImageURI(icon);
            holder.mPlayBtnView.setOnClickListener(new MyOnclick(
                    holder.mPlayBtnView, holder.mVideoViewLayout, position));
            holder.isPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Video.BodyBean bean = mData.get(position);
                    if (CommonUtils.isEmpty(bean.getUser_id())) {
                        addShopcar(bean.getVideo_id());
                    } else {

                    }
                }
            });
            if (indexPosition == position) {
                holder.mVideoViewLayout.setVisibility(View.VISIBLE);
            } else {
                holder.mVideoViewLayout.setVisibility(View.GONE);
                holder.mVideoViewLayout.close();
            }
            holder.studioPhoto.setImageURI(icon);
            holder.mTextViewName.setText(listBean.getVideo_author());
            holder.mTitle.setText(listBean.getVideo_name());
            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Share.inviteFriend(context);
                }
            });
            return v;
        }

        class MyOnclick implements View.OnClickListener {
            VideoSuperPlayer mSuperVideoPlayer;
            ImageView mPlayBtnView;
            int position;

            public MyOnclick(ImageView mPlayBtnView,
                             VideoSuperPlayer mSuperVideoPlayer, int position) {
                this.position = position;
                this.mSuperVideoPlayer = mSuperVideoPlayer;
                this.mPlayBtnView = mPlayBtnView;
            }

            @Override
            public void onClick(View v) {
                if (CommonUtils.isEmpty(mData.get(position).getUser_id())) {
                    /*indexPosition = -1;
                    isPlaying = false;
                    notifyDataSetChanged();
                    MediaHelp.release();
                    Intent intent = new Intent(HomeVideoActivity.this, VideoPayActivity.class);
                    intent.putExtra("list", mData);
                    intent.putExtra("pos", position);
                    f = 1;
                    startActivity(intent);*/
                    Toast.makeText(context, "请加入购物车购买哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                MediaHelp.release();
                indexPosition = position;
                isPlaying = true;
                mSuperVideoPlayer.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.loadAndPlay(MediaHelp.getInstance(), mData
                        .get(position).getVideo_url(), 0, false);
                mSuperVideoPlayer.setVideoPlayCallback(new MyVideoPlayCallback(
                        mPlayBtnView, mSuperVideoPlayer, mData.get(position).getVideo_url()));
                notifyDataSetChanged();
            }
        }

        class MyVideoPlayCallback implements VideoSuperPlayer.VideoPlayCallbackImpl {
            ImageView mPlayBtnView;
            VideoSuperPlayer mSuperVideoPlayer;
            String info;

            public MyVideoPlayCallback(ImageView mPlayBtnView,
                                       VideoSuperPlayer mSuperVideoPlayer, String info) {
                this.mPlayBtnView = mPlayBtnView;
                this.info = info;
                this.mSuperVideoPlayer = mSuperVideoPlayer;
            }

            @Override
            public void onCloseVideo() {
                closeVideo();
            }

            @Override
            public void onSwitchPageType() {
                if (((Activity) context).getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(new Intent(context,
                            FullVideoActivity.class));
                    intent.putExtra("video", info);
                    intent.putExtra("position",
                            mSuperVideoPlayer.getCurrentPosition());
                    ((Activity) context).startActivityForResult(intent, 1);
                }
            }

            @Override
            public void onPlayFinish() {
                closeVideo();
            }

            private void closeVideo() {
                isPlaying = false;
                indexPosition = -1;
                mSuperVideoPlayer.close();
                MediaHelp.release();
                mPlayBtnView.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.setVisibility(View.GONE);
            }

        }

        public class GameVideoViewHolder {

            private VideoSuperPlayer mVideoViewLayout;
            private ImageView mPlayBtnView;
            private SimpleDraweeView pic, studioPhoto;
            private TextView mTextViewName, mTitle, isPay;
            private ImageView share;

            public GameVideoViewHolder(View view) {
                mVideoViewLayout = (VideoSuperPlayer) view.findViewById(R.id.video);
                mPlayBtnView = (ImageView) view.findViewById(R.id.play_btn);
                pic = (SimpleDraweeView) view.findViewById(R.id.icon);
                mTextViewName = (TextView) view.findViewById(R.id.tv_video_item_name);
                mTitle = (TextView) view.findViewById(R.id.tv_video_item_title);
                studioPhoto = (SimpleDraweeView) view.findViewById(R.id.sv_video_item_studio);
                share = (ImageView) view.findViewById(R.id.iv_video_item_share);
                isPay = (TextView) view.findViewById(R.id.tv_video_isPay);
            }
        }
    }

    private void addShopcar(String goodsId) {
        map.put("buyUserId", SharedPreferenceUtils.getUserData(this, 1));
        map.put("goodsId", goodsId);
        map.put("buyUserCity", SharedPreferenceUtils.getCity(this));
        map.put("buyUserName", SharedPreferenceUtils.getUserData(this, 2));
        map.put("orderRemarkBuyer", "");
        net.post(NetConfig.SHOPCAR_ADDVIDEO, map, new UtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                String ret = JsonUtils.getRet(result);
                if ("0".equals(ret)) {
                    Toast.makeText(HomeVideoActivity.this, "加入成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeVideoActivity.this, "加入失败，亲，您是否已经购买或者加入购物车", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
