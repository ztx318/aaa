package com.example.administrator.my_eventbus;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/3/31 0031.
 */

public class SecondActivity extends Activity implements View.OnClickListener {

    private Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btn = (Button) findViewById(R.id.bt2_event);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        Intent intent=new Intent(SecondActivity.this,MainActivity.class);
//        startActivity(intent);
        EventBus.getDefault().post(new MessageEvent("bus来了"));
    }
}
