package com.example.administrator.my_eventbus;

/**
 * Created by Administrator on 2017/3/31 0031.
 */

public class MessageEvent  {


    private String mMsg;

    public MessageEvent(String msg) {
        mMsg=msg;

    }
    public  String getMsg(){
        return mMsg;
    }
}
