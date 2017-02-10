package com.example.huichuanyi.bean;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.modle.Video;
import com.example.huichuanyi.share.Share;
import com.example.huichuanyi.utils.ActivityUtils;
import com.squareup.picasso.Picasso;
import com.superplayer.library.utils.SuperPlayerUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SuperVideoAdapter extends RecyclerView.Adapter<SuperVideoAdapter.VideoViewHolder> {
    private final Context mContext;
    private List<Video.ListBean> dataList;

    public SuperVideoAdapter(Context context, List<Video.ListBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override
    public SuperVideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.adapter_listview_layout, null);
        VideoViewHolder holder = new VideoViewHolder(view);
        view.setTag(holder);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuperVideoAdapter.VideoViewHolder holder, int position) {
        holder.update(position);
        Video.ListBean listBean = dataList.get(position);
        final String geticon = listBean.getGeticon();
        final String title = dataList.get(position).getName();
        holder.mTextViewTitle.setText(title);
        if (!TextUtils.isEmpty(geticon)) {
            Picasso.with(mContext).load(geticon).into(holder.mImageViewCover);
        }
        String geticon1 = listBean.getGeticon();
        String makeuser = listBean.getMakeuser();
        holder.mTextViewName.setText(makeuser);
        String playnum = listBean.getPlaynum();
        holder.mTextViewCount.setText(playnum + "次播放");
        String length = listBean.getLength();
        final String introduce = listBean.getIntroduce();
        final String getpath = listBean.getGetpath();
        final String linkurl = listBean.getLinkurl();
        holder.mTextViewPlayTime.setText(length);
        if (!TextUtils.isEmpty(geticon1)) {
            Picasso.with(mContext).load(geticon1).into(holder.mImageViewManager);
        }
        holder.mImageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.showShare(mContext, title, linkurl, introduce, linkurl, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlayPlayerControl;
        private RelativeLayout rlayPlayer;
        private ImageView mImageViewCover, mImageViewManager, mImageViewShare;
        private TextView mTextViewTitle, mTextViewPlayTime;
        private TextView mTextViewName, mTextViewCount;

        public VideoViewHolder(View itemView) {
            super(itemView);
            rlayPlayerControl = (RelativeLayout) itemView.findViewById(R.id.adapter_player_control);
            rlayPlayer = (RelativeLayout) itemView.findViewById(R.id.adapter_super_video_layout);
            mImageViewCover = (ImageView) itemView.findViewById(R.id.adapter_super_video_iv_cover);
            mTextViewTitle = (TextView) itemView.findViewById(R.id.tv_super_video_title);
            mImageViewManager = (ImageView) itemView.findViewById(R.id.imageView);
            mTextViewName = (TextView) itemView.findViewById(R.id.tv_item_video_name);
            mTextViewCount = (TextView) itemView.findViewById(R.id.tv_item_pay_video_count);
            mTextViewPlayTime = (TextView) itemView.findViewById(R.id.tv_play_time);
            mImageViewShare = (ImageView) itemView.findViewById(R.id.tv_item_pay_video_share);
            if (rlayPlayer != null) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlayPlayer.getLayoutParams();
                layoutParams.height = (int) (SuperPlayerUtils.getScreenWidth((Activity) mContext) * 0.5652f);//这值是网上抄来的，我设置了这个之后就没有全屏回来拉伸的效果，具体为什么我也不太清楚
                rlayPlayer.setLayoutParams(layoutParams);
            }
        }

        public void update(final int position) {
            //点击回调 播放视频
            rlayPlayerControl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userid = dataList.get(position).getUserid();
                    String videoid = dataList.get(position).getId();
                    String price = dataList.get(position).getMoney();
                    String geticon = dataList.get(position).getGeticon();
                    String name = dataList.get(position).getName();
                    if (playclick != null)
                        if (TextUtils.equals("null", userid)) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("videoid", videoid);
                            map.put("price", price);
                            map.put("geticon", geticon);
                            map.put("name", name);
                            ActivityUtils.switchTo((Activity) mContext, PayVideoActivity.class, map);
                            ((Activity) mContext).finish();
                            return;
                        }
                    playclick.onPlayclick(position, rlayPlayerControl);
                }
            });
        }
    }

    private onPlayClick playclick;

    public void setPlayClick(onPlayClick playclick) {
        this.playclick = playclick;
    }

    public interface onPlayClick {
        void onPlayclick(int position, RelativeLayout image);
    }

}
