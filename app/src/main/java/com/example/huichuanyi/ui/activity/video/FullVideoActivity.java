package com.example.huichuanyi.ui.activity.video;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.huichuanyi.R;
import com.example.huichuanyi.ui.activity.video.widget.MediaHelp;
import com.example.huichuanyi.ui.activity.video.widget.VideoMediaController;
import com.example.huichuanyi.ui.activity.video.widget.VideoSuperPlayer;


public class FullVideoActivity extends Activity {
    private VideoSuperPlayer mVideo;
    private String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.video_activity_full);
        mVideo = (VideoSuperPlayer) findViewById(R.id.video);
        info = getIntent().getStringExtra("video");
        mVideo.loadAndPlay(MediaHelp.getInstance(), info, getIntent()
                .getExtras().getInt("position"), true);
        mVideo.setPageType(VideoMediaController.PageType.EXPAND);
        mVideo.setVideoPlayCallback(new VideoSuperPlayer.VideoPlayCallbackImpl() {

            @Override
            public void onSwitchPageType() {
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    finish();
                }
            }

            @Override
            public void onPlayFinish() {
                finish();
            }

            @Override
            public void onCloseVideo() {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("position", mVideo.getCurrentPosition());
        setResult(RESULT_OK, intent);
        super.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaHelp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaHelp.resume();
    }
}
