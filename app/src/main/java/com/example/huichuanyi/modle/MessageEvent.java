package com.example.huichuanyi.modle;

import android.util.Log;

/**
 * Created by 小五 on 2016/12/19.
 */
public class MessageEvent {
    public String message;

    public MessageEvent(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
