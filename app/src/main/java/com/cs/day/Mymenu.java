package com.cs.day;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

public class Mymenu extends AppCompatActivity {
    ImageView bulletin,dayoff,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymenu);
        bulletin=(ImageView)findViewById(R.id.bulletin);
        dayoff=(ImageView)findViewById(R.id.dayoff);
        logout=(ImageView)findViewById(R.id.logout);
        bulletin.setOnClickListener(bulletinbtn);
        dayoff.setOnClickListener(dayoffbtn);
        logout.setOnClickListener(logoutbtn);


    }
    private ImageView.OnClickListener bulletinbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View view) {

        }
    };
    private ImageView.OnClickListener dayoffbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            intent.setClass(Mymenu.this,Empmenu.class);
            startActivity(intent);
        }
    };
    private ImageView.OnClickListener logoutbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            intent.setClass(Mymenu.this,MainActivity.class);
            startActivity(intent);

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
