package com.cs.day;

import android.content.Intent;
import android.os.Bundle;

import com.cs.daytest.Mymenutest;
import com.cs.daytest.Waitsigntest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Mymenu extends AppCompatActivity {
    ImageView bulletin,dayoff,logout,dayofftest,oknai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymenu);
        bulletin=(ImageView)findViewById(R.id.bulletin);
        dayoff=(ImageView)findViewById(R.id.dayoff);
        logout=(ImageView)findViewById(R.id.logout);
        dayofftest=(ImageView)findViewById(R.id.dayofftest);
        oknai=(ImageView)findViewById(R.id.oknai);
        bulletin.setOnTouchListener(bulletinbtn);
        dayoff.setOnTouchListener(dayoffbtn);
        logout.setOnTouchListener(logoutbtn);
        dayofftest.setOnTouchListener(dayofftestbtn);
        oknai.setOnTouchListener(oknaibtn);

    }
    private ImageView.OnTouchListener oknaibtn=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//test
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    oknai.setImageResource(R.drawable.cs_okna2);

                    break;
                case MotionEvent.ACTION_UP:
                    oknai.setImageResource(R.drawable.cs_okna);
                    Intent intent=new Intent();
                    intent.setClass(Mymenu.this, Waitsigntest.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener dayofftestbtn=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//test
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    dayofftest.setImageResource(R.drawable.cs_test2);

                    break;
                case MotionEvent.ACTION_UP:
                    dayofftest.setImageResource(R.drawable.cs_test);
                    Intent intent=new Intent();
                    intent.setClass(Mymenu.this, Mymenutest.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener bulletinbtn=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//test
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    bulletin.setImageResource(R.drawable.cs_bulletin2h);

                    break;
                case MotionEvent.ACTION_UP:
                    bulletin.setImageResource(R.drawable.cs_bulletin3);
                    Intent intent=new Intent();
                    intent.setClass(Mymenu.this,Bulletin.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener dayoffbtn=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//test
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    dayoff.setImageResource(R.drawable.cs_dayoffh);

                    break;
                case MotionEvent.ACTION_UP:
                    dayoff.setImageResource(R.drawable.cs_dayoff2);
                    Intent intent=new Intent();
                    intent.setClass(Mymenu.this,Empmenu.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }

    };
    private ImageView.OnTouchListener logoutbtn=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//test
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    logout.setImageResource(R.drawable.cs_logout2h);

                    break;
                case MotionEvent.ACTION_UP:
                    logout.setImageResource(R.drawable.cs_logout3);
                    Intent intent=new Intent();
                    intent.setClass(Mymenu.this,MainActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }

    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();  // Always call the superclass method first
        Intent intent =new Intent();
        intent.setClass(Mymenu.this,MainActivity.class);
        startActivity(intent);
    }
}
