package com.example.huichuanyi.share;

import android.content.Context;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class Share {

    public static void showShare(final Context context, String title, String titleUrl,
                                 String text, String imageUrl, String content) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(title);
        oks.setTitleUrl("http://hmyc365.net:8080/html/share/share.html");
        oks.setText(text);
        oks.setUrl("http://hmyc365.net:8080/html/share/share.html");
        oks.setImageUrl(imageUrl);
        oks.setComment(content);
        oks.show(context);
    }

    public static void inviteFriend(Context mContext) {
        ShareSDK.initSDK(mContext);
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle("慧美优惠你大吃一惊");
        oks.setTitleUrl("http://hmyc365.net:8080/html/share/share.html");
        oks.setText("交个朋友吧");
        oks.setUrl("http://hmyc365.net:8080/html/share/share.html");
        oks.setComment("交个朋友吧");
        oks.setImageUrl("http://101.201.36.18:8080/images/syspic/1.jpg");
        oks.show(mContext);
    }
}
