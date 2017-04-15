package com.example.huichuanyi.share;

import android.content.Context;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class Share {


    public static void showShare(final Context context, String titleUrl,
                                 String content) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(content);
        oks.setTitleUrl(titleUrl);
        oks.setText(content);
        oks.setUrl(titleUrl);
        oks.setComment(content);
        oks.setImageUrl("http://hmyc365.net:8081/file/logo.png");
        oks.show(context);
    }

    public static void inviteFriend(Context mContext) {
        ShareSDK.initSDK(mContext);
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle("慧美衣橱");
        oks.setTitleUrl("http://hmyc365.net:8080/html/share/share.html");
        oks.setText("轻松生活来自慧美，让衣橱管理走进千万家");
        oks.setUrl("http://hmyc365.net:8080/html/share/share.html");
        oks.setComment("轻松生活来自慧美，让衣橱管理走进千万家");
        oks.setImageUrl("http://hmyc365.net:8081/file/logo.png");
        oks.show(mContext);
    }
}
