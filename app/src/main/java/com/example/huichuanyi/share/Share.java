package com.example.huichuanyi.share;

import android.content.Context;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class Share {


    public static void showShare(final Context context, String titleUrl,
                                 String content) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(content);
        oks.setTitleUrl(titleUrl);
        oks.setText(content + titleUrl);
        oks.setUrl(titleUrl);
        oks.setComment(content);
        oks.setImageUrl("http://hmyc365.net:8081/file/hm_app_logopp_logo.png");
        oks.show(context);
    }


    public static void sdCardShare(final Context context, String sdUrl,
                                   String content) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(content);
        oks.setTitleUrl("http://hmyc365.net:8080/html/share/share.html");
        oks.setText(content + "http://hmyc365.net:8080/html/share/share.html");
        oks.setUrl("http://hmyc365.net:8080/html/share/share.html");
        oks.setComment(content);
        oks.setImagePath(sdUrl);
        oks.show(context);
    }

    public static void inviteFriend(Context mContext) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle("慧美衣橱");
        oks.setTitleUrl("http://hmyc365.net:8080/html/share/share.html");
        oks.setText("轻松生活来自慧美，让衣橱管理走进千万家");
        oks.setUrl("http://hmyc365.net:8080/html/share/share.html");
        oks.setComment("轻松生活来自慧美，让衣橱管理走进千万家");
        oks.setImageUrl("http://hmyc365.net:8081/file/hm_app_logopp_logo.png");
        oks.show(mContext);
    }
}
