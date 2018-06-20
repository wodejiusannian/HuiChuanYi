package com.example.huichuanyi.im;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.utils.SharedPreferenceUtils;
import com.gyf.barlibrary.ImmersionBar;

import java.util.Collection;
import java.util.Iterator;

import io.rong.imkit.RongIM;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

public class ConversationActivity extends FragmentActivity implements RongIM.UserInfoProvider {
    private static final String TAG = "ConversationActivity";
    private TextView tvTitle;

    private String title;
    private final String TextTypingTitle = "对方正在输入...";
    private final String VoiceTypingTitle = "对方正在讲话...";

    private String mTargetId;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    tvTitle.setText(TextTypingTitle);
                    break;
                case 1:
                    tvTitle.setText(VoiceTypingTitle);
                    break;
                case 2:
                    tvTitle.setText(title);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();
        tvTitle = (TextView) this.findViewById(R.id.tv_title);
        title = getIntent().getData().getQueryParameter("title");
        mTargetId = getIntent().getData().getQueryParameter("targetId");
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setText("私人衣橱管理师");
        }
        RongIM.setUserInfoProvider(this, true);

        Intent broadcast = new Intent("action.have.msg");
        broadcast.putExtra("isRead", "yes");
        sendOrderedBroadcast(broadcast, null);
        RongIMClient.setTypingStatusListener(new RongIMClient.TypingStatusListener() {
            @Override
            public void onTypingStatusChanged(Conversation.ConversationType type, String targetId, Collection<TypingStatus> typingStatusSet) {
                if (targetId.equals(mTargetId)) {
                    //当输入状态的会话类型和targetID与当前会话一致时，才需要显示
                    int count = typingStatusSet.size();
                    //count表示当前会话中正在输入的用户数量，目前只支持单聊，所以判断大于0就可以给予显示了
                    if (count > 0) {
                        Iterator iterator = typingStatusSet.iterator();
                        TypingStatus status = (TypingStatus) iterator.next();
                        String objectName = status.getTypingContentType();

                        MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
                        MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
                        //匹配对方正在输入的是文本消息还是语音消息
                        if (objectName.equals(textTag.value())) {
                            handler.sendEmptyMessage(0);
                        } else if (objectName.equals(voiceTag.value())) {
                            handler.sendEmptyMessage(1);
                        }
                    } else {//当前会话没有用户正在输入，标题栏仍显示原来标题
                        handler.sendEmptyMessage(2);
                    }
                }
            }
        });
    }

    public void back(View view) {
        if (view != null)
            finish();
    }

    @Override
    public UserInfo getUserInfo(String s) {
        if (s.contains("hmgls_")) {
            return new UserInfo(s, SharedPreferenceUtils.getUserData(ConversationActivity.this, 4), Uri.parse(SharedPreferenceUtils.getUserData(ConversationActivity.this, 5)));
        } else {
            return new UserInfo(s, SharedPreferenceUtils.getUserData(ConversationActivity.this, 2), Uri.parse(SharedPreferenceUtils.getUserData(ConversationActivity.this, 3)));
        }
    }
}