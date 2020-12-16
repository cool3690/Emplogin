package com.cs.day;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cs.control.Phonekeka;
import com.cs.control.PhonekekasAdapter;
import com.cs.mydb.dbbtsel;
import com.cs.mydb.dbphone;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Phonebook extends AppCompatActivity {
    ArrayList<Phonekeka> kekas = new ArrayList<Phonekeka>();
    ListView mylist;
    String myname,myphone,myextension;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonebook);
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
        mylist=(ListView)findViewById(R.id.list);
        mydb();

    }

    public void mydb(){
        Phonekeka  keka = new Phonekeka("姓名","手機","分機");
        kekas.add(keka);
        try{
            String result = dbphone.executeQuery();
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);

                myname=jsonData.getString("name");
                myphone=jsonData.getString("phone");
                myextension=jsonData.getString("extension");


               keka = new Phonekeka(myname+"",myphone+"",myextension+"");
                kekas.add(keka);

            }

            final PhonekekasAdapter adapter = new PhonekekasAdapter(this, R.layout.phone, kekas);
            mylist.setAdapter(adapter);
          //  mylist.setTextFilterEnabled(true);

          //  mylist.setOnItemClickListener(lstPreferListener);

              /**/

        }

        catch(Exception e){}
    }
    private ListView.OnItemClickListener lstPreferListener=
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {


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
        intent.setClass(Phonebook.this,Mymenu.class);
        startActivity(intent);
    }
}