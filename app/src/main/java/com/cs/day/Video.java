package com.cs.day;

import android.os.Bundle;

import android.content.Intent;
import android.graphics.Color;

import android.os.StrictMode;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Video extends AppCompatActivity {

    String account="",passwd="",vender="";
    Spinner spinner;
    Button ok;

    int x=0;
    WebView ch1,ch2;
    String sel="";
    Timer timer = new Timer();
    String[] Balls= new String[] {"請選擇"};
    private int secondLeft = 180;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        account=Account.getAccount();
        passwd=Account.getPasswd();
        vender=Account.getVendor_id();
        spinner=(Spinner)findViewById(R.id.spinner);
        // ok=(Button)findViewById(R.id.ok);
        ch1=(WebView)findViewById(R.id.ch1);
        ch2=(WebView)findViewById(R.id.ch2);
        String result = dbpermission.executeQuery(vender);
        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;
            // bt.setText("更多資訊");

            Balls= new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                String license_plate=jsonData.getString("license_plate");
//revise
                Balls[i]=license_plate;

            }


        }

        catch(Exception e){}
        ArrayAdapter<String> adapterBalls=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,Balls);

        // 設定Spinner顯示的格式
        adapterBalls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 設定Spinner的資料來源
        spinner.setAdapter(adapterBalls);
        spinner.setOnItemSelectedListener(spnPreferListener);
        begin();
    }

    private Spinner.OnItemSelectedListener spnPreferListener=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                    TextView textView = (TextView) v;
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    x=0;
                    sel=parent.getSelectedItem().toString();
                    show(sel);

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            };

    public void show(String sel){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
        String dts=sdf.format(date);

        SimpleDateFormat t=new SimpleDateFormat("HH");
        Date date2=new Date();
        String t2=t.format(date2);

        String a=dbcarname.executeQuery(sel);
        String b[]=a.split(":");
        String c[]=b[0].split(",");
        String d[]=b[1].split(",");
        if(x<c.length){
            ch1.loadUrl("https://chansing.com.tw/car/video/"+sel+"/"+dts+"/"+t2+"/CH1/"+c[x]);

        }
        if(x<d.length){
            ch2.loadUrl("https://chansing.com.tw/car/video/"+sel+"/"+dts+"/"+t2+"/CH2/"+d[x]);
        }

    }
    public void begin() {
        timer.schedule(task, 1000, 1000) ;       }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    secondLeft--;

                    if (secondLeft < 1) {
                        secondLeft=180;
                        x++;
                        show(sel);
                    }
                }
            });
        }
    };
    /* */
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.video) {
            Intent intent= new Intent();
            intent.setClass(Video.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_settings) {
            Intent intent= new Intent();
            intent.setClass(Video.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
