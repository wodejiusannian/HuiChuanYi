package com.example.huichuanyi.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.activity.video.FullVideoActivity;
import com.example.huichuanyi.ui.activity.video.VideoBean;
import com.example.huichuanyi.ui.activity.video.widget.MediaHelp;
import com.example.huichuanyi.ui.activity.video.widget.VideoSuperPlayer;
import com.example.huichuanyi.utils.UtilsInternet;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class HomeVideoCoverActivity extends BaseActivity implements UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.lv_video)
    private ListView videos;

    @ViewInject(R.id.sf_refresh)
    private SwipeRefreshLayout refreshLayout;

    private List<VideoBean> mData = new ArrayList<>();

    private boolean isPlaying;
    private int indexPostion = -1;
    private VideoAdapter adapter;
    private UtilsInternet net = UtilsInternet.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_video);
    }

    @Override
    public void setListener() {
        videos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoBean bean = mData.get(position);
                int type = bean.getType();
                if (type == 2) {
                    indexPostion = -1;
                    isPlaying = false;
                    adapter.notifyDataSetChanged();
                    MediaHelp.release();
                    Intent intent = new Intent(HomeVideoCoverActivity.this, HomeVideoActivity.class);
                    intent.putExtra("id", bean.getCover_id());
                    startActivity(intent);
                } else if (type == 3) {
                    Toast.makeText(HomeVideoCoverActivity.this, "敬请期待", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void initView() {
        loadData();
    }

    @Override
    public void initData() {
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(this);
    }


    private void loadData() {
        net.post(NetConfig.GET_SHIPIN_FENGMIAN, null, this);
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
                if ((indexPostion < videos.getFirstVisiblePosition() || indexPostion > videos
                        .getLastVisiblePosition()) && isPlaying) {
                    indexPostion = -1;
                    isPlaying = false;
                    adapter.notifyDataSetChanged();
                    MediaHelp.release();
                }
            }
        });
    }

    @Override
    public void onResponse(String result) {
        try {
            mData.clear();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray body = jsonObject.getJSONArray("body");
            for (int i = 0; i < body.length(); i++) {
                JSONObject obj = body.getJSONObject(i);
                VideoBean bean = new VideoBean();
                bean.setIntroduce(obj.getString("introduce"));
                bean.setCover_id(obj.getString("cover_id"));
                bean.setType(obj.getInt("type"));
                bean.setPic_get(obj.getString("pic_get"));
                bean.setVideo_url(obj.getString("video_url"));
                mData.add(bean);
            }
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }


    class VideoAdapter extends BaseAdapter {
        private Context context;
        LayoutInflater inflater;
        private static final int TYPE_ONE = 0;
        private static final int TYPE_TWO = 1;

        public VideoAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public VideoBean getItem(int position) {
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
        public int getItemViewType(int position) {
            VideoBean bean = mData.get(position);
            int type = bean.getType();
            if (type == 1) {
                return TYPE_ONE;
            } else {
                return TYPE_TWO;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int itemViewType = getItemViewType(position);
            if (itemViewType == TYPE_ONE) {
                //第一种item
                GameVideoViewHolder viewHolder1 = null;
                if (convertView == null) {
                    //没有缓存过
                    viewHolder1 = new GameVideoViewHolder();
                    convertView = inflater.inflate(R.layout.video_item_one, null, false);
                    viewHolder1.mVideoViewLayout = (VideoSuperPlayer) convertView
                            .findViewById(R.id.super_video_type_one);
                    viewHolder1.mPlayBtnView = (ImageView) convertView.findViewById(R.id.play_btn_type_one);
                    viewHolder1.pic = (SimpleDraweeView) convertView.findViewById(R.id.sv_type_one);
                    convertView.setTag(viewHolder1);
                } else {
                    viewHolder1 = (GameVideoViewHolder) convertView.getTag();
                }
                String pic_get = mData.get(position).getPic_get();
                viewHolder1.pic.setImageURI(pic_get);
                viewHolder1.mPlayBtnView.setOnClickListener(new MyOnclick(
                        viewHolder1.mPlayBtnView, viewHolder1.mVideoViewLayout, position));
                if (indexPostion == position) {
                    viewHolder1.mVideoViewLayout.setVisibility(View.VISIBLE);
                } else {
                    viewHolder1.mVideoViewLayout.setVisibility(View.GONE);
                    viewHolder1.mVideoViewLayout.close();
                }
            } else if (itemViewType == TYPE_TWO) {
                //第一种item
                ViewHolder2 viewHolder2 = null;
                if (convertView == null) {
                    //没有缓存过
                    viewHolder2 = new ViewHolder2();
                    convertView = inflater.inflate(R.layout.video_item_two, null, false);
                    viewHolder2.pic2 = (SimpleDraweeView) convertView.findViewById(R.id.sv_type_two);
                    convertView.setTag(viewHolder2);
                } else {
                    viewHolder2 = (ViewHolder2) convertView.getTag();
                }
                String pic_get = mData.get(position).getPic_get();
                viewHolder2.pic2.setImageURI(pic_get);
            }

            return convertView;
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
                MediaHelp.release();
                indexPostion = position;
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
                indexPostion = -1;
                mSuperVideoPlayer.close();
                MediaHelp.release();
                mPlayBtnView.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.setVisibility(View.GONE);
            }

        }

        class GameVideoViewHolder {

            private VideoSuperPlayer mVideoViewLayout;
            private ImageView mPlayBtnView;
            private SimpleDraweeView pic;

        }

        class ViewHolder2 {
            private SimpleDraweeView pic2;
        }

    }

    public void back(View view) {
        if (view != null) {
            finish();
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

}
