package com.cs.day;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cs.control.GlobalVariable;
import com.cs.control.HorizonScrollTextView;
import com.cs.daytest.Mymenutest;
import com.cs.daytest.Waitsigntest;
import com.cs.mydb.dbbtsel;
import com.cs.mydbtest.dbleanoti2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class Mymenu extends AppCompatActivity {
    ImageView bulletin,dayoff,logout,dayofftest,oknai,phone;
    String account;
    private HorizonScrollTextView tv_2;
    String bult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymenu);
        bulletin=(ImageView)findViewById(R.id.bulletin);
        dayoff=(ImageView)findViewById(R.id.dayoff);
        logout=(ImageView)findViewById(R.id.logout);
        dayofftest=(ImageView)findViewById(R.id.dayofftest);
        oknai=(ImageView)findViewById(R.id.oknai);
        phone=(ImageView)findViewById(R.id.phone);
        bulletin.setOnTouchListener(bulletinbtn);
        dayoff.setOnTouchListener(dayoffbtn);
        logout.setOnTouchListener(logoutbtn);
        dayofftest.setOnTouchListener(dayofftestbtn);
        oknai.setOnTouchListener(oknaibtn);
        phone.setOnTouchListener(phonebtn);

        GlobalVariable gv = (GlobalVariable) getApplicationContext();
        account = gv.getEmpacc();
        new DownloadFileAsync().execute();
      //  init();

    }
    private void init(){
        tv_2 = (HorizonScrollTextView)findViewById(R.id.tv_2);
        tv_2.setText("test1  test2");
        tv_2.setTextSize(20);
        tv_2.setTextColor(Color.WHITE);
    }
    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {
            try {
                String result = dbleanoti2.executeQuery(account);
                JSONArray jsonArray = new JSONArray(result);
                if(jsonArray.length()>0) {
                    oknai.setImageResource(R.drawable.cs_okna3);
                }
                else{
                    oknai.setImageResource(R.drawable.cs_okna);
                }
            } catch(Exception e) {}
            try{
                String result = dbbtsel.executeQuery();
                JSONArray jsonArray = new JSONArray(result);

                for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
                {	 JSONObject jsonData = jsonArray.getJSONObject(i);

                    bult+=jsonData.getString("title")+"  ";
                    bult+=jsonData.getString("content")+"  ";
                    bult+=jsonData.getString("depart")+"  ";
                    bult+=jsonData.getString("date")+"  ";
                    bult+=jsonData.getString("authority");
                }

            }

            catch(Exception e){}
            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused) {
            tv_2 = (HorizonScrollTextView)findViewById(R.id.tv_2);
            tv_2.setText(bult);
            tv_2.setTextSize(20);
            tv_2.setTextColor(Color.WHITE);

        }
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
                    dayofftest.setImageResource(R.drawable.cs_test);

                    break;
                case MotionEvent.ACTION_UP:
                    dayofftest.setImageResource(R.drawable.cs_test2);
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
                    bulletin.setImageResource(R.drawable.cs_bulletin3);

                    break;
                case MotionEvent.ACTION_UP:
                    bulletin.setImageResource(R.drawable.cs_bulletin2);
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
    private ImageView.OnTouchListener phonebtn=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//test
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    phone.setImageResource(R.drawable.cs_phoneh);

                    break;
                case MotionEvent.ACTION_UP:
                    phone.setImageResource(R.drawable.cs_phone);
                    Intent intent=new Intent();
                    intent.setClass(Mymenu.this, Phonebook.class);
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
                    Bundle bundle=new Bundle();
                    bundle.putString("OUT","A");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
            return true;
        }

    };
    @Override
    public void onBackPressed()
    {

    }
   
}
