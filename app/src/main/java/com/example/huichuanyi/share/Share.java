package com.example.huichuanyi.share;

import android.content.Context;
import android.widget.Toast;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class Share {

    public static void showShare(Context context,String title,String titleUrl,
                                 String text,String imageUrl,String content){
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(title);
        oks.setTitleUrl(titleUrl);
        oks.setText(text);
        oks.setUrl("http://hmyc365.net:8080/html/share/share.html");
        oks.setImageUrl(imageUrl);
        oks.setComment(content);
        oks.show(context);
        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
    }

}
