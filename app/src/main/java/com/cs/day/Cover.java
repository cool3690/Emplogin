package com.cs.day;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Cover extends AppCompatActivity {
    private ImageView login,img;//,signature,del;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cover);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        img=(ImageView)findViewById(R.id.img);
        timer = new Timer(true);
        timer.schedule(timerTask, 2500, 2000); //延時1000ms後執行，1000ms執行一次
    }
    TimerTask timerTask = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                Intent intent =new Intent();
                intent.setClass(Cover.this,MainActivity.class);
                timer.cancel();
                startActivity(intent);
                //回到主執行緒執行結束操作
                // Log.e("=====", "結束計時");
            }
        }
    };
}
