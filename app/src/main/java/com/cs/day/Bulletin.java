package com.cs.day;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.cs.mydb.dbbtsel;
import com.cs.mydb.dbleasel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.day.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Bulletin extends AppCompatActivity {
    ArrayList<btKeka> kekas = new ArrayList<btKeka>();

TextView title,content,depart,date;
ListView mylist;
String mytitle="",mycontent="",mydepart="",mydate="",myauthority="";
ArrayList myid=new ArrayList<String>();

ImageView btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        title=(TextView)findViewById(R.id.title);
        content=(TextView)findViewById(R.id.content);
        depart=(TextView)findViewById(R.id.depart);
        date=(TextView)findViewById(R.id.date);
        btn=(ImageView)findViewById(R.id.btn);
        mylist=(ListView)findViewById(R.id.list);
        mydb();
        myid.add(0);
    }

    public void mydb(){

        try{
            String result = dbbtsel.executeQuery();
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);

                mytitle=jsonData.getString("title");
                mycontent=jsonData.getString("content");
                mydepart=jsonData.getString("depart");
                mydate=jsonData.getString("date");
                 myauthority=jsonData.getString("authority");

                if(myauthority.equals("0")){
                    title.setText(mytitle);
                    content.setText(mycontent);
                    depart.setText("       "+mydepart);
                    date.setText(mydate);


                    }
                else if(myauthority.equals("1")){
                    myid.add(jsonData.getString("id"));
                    btKeka  keka = new btKeka(mytitle,mycontent,mydepart,mydate,R.drawable.cs_btbt);
                    kekas.add(keka);


                    }



            }
            final btKekasAdapter adapter = new btKekasAdapter(this, R.layout.btkeka, kekas);
            mylist.setAdapter(adapter);
            mylist.setTextFilterEnabled(true);

           mylist.setOnItemClickListener(lstPreferListener);

        }

        catch(Exception e){}
    }

    private ListView.OnItemClickListener lstPreferListener=
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    ImageView btn = (ImageView)v.findViewById(R.id.btn);
                    btn.setImageResource(R.drawable.cs_btbth);
                    Intent intent=new Intent();
                    intent.setClass(Bulletin.this,Bulletinmore.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("POSITION",position+"");
                    bundle.putStringArrayList("ID", myid);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            };
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();  // Always call the superclass method first

        Intent intent =new Intent();
        intent.setClass(Bulletin.this,Mymenu.class);
        startActivity(intent);
    }
}