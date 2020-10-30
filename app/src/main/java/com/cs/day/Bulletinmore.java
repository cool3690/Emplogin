package com.cs.day;

import android.content.Intent;
import android.os.Bundle;

import com.cs.mydb.dbbtsel;
import com.cs.mydb.dbbtsel2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Bulletinmore extends AppCompatActivity {
        TextView title,depart,date,content;
        String pos="";
        ArrayList myid=new ArrayList<String>();
    String mytitle="",mycontent="",mydepart="",mydate="",myauthority="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletinmore);
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
        depart=(TextView)findViewById(R.id.depart);
        content=(TextView)findViewById(R.id.content);
        date=(TextView)findViewById(R.id.date);
        // 取得  bundle
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        pos=bundle.getString("POSITION");
        myid=bundle.getStringArrayList("ID");
        int a=Integer.parseInt(pos);
        String tmp=myid.get(a)+"";
       mydb(tmp);


    }
    public void mydb(String id){

        try{
            String result = dbbtsel2.executeQuery(id);
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);

                mytitle=jsonData.getString("title");
                mycontent=jsonData.getString("content");
                mydepart=jsonData.getString("depart");
                mydate=jsonData.getString("date");
                myauthority=jsonData.getString("authority");
                /**/
                    title.setText(mytitle);
                    content.setText(mycontent);
                    depart.setText(mydepart);
                    date.setText(mydate);




            }


        }

        catch(Exception e){}
    }
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
        intent.setClass(Bulletinmore.this,Bulletin.class);
        startActivity(intent);
    }
}