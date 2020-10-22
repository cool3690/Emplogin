package com.cs.day;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cs.control.GlobalVariable;
import com.cs.mydb.dbleasel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Search extends AppCompatActivity {
    ArrayList<Keka> kekas = new ArrayList<Keka>();
    ImageView ret;
ListView mylist;
    String account,department,name,hr,pwd;
    String substitute="",manager="",emp_id="",type="",time="",hruselea="",serial_num="";
    String myname="",reason="";
    String notes="";
List<String>record=new ArrayList<>();
    private ProgressDialog dialog;
    int count;
    Dialog dia;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
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
        ret=(ImageView)findViewById(R.id.ret);
        mylist=(ListView)findViewById(R.id.mylist);
        startDownload();
        ret.setOnTouchListener(retbtn);
    }
    private void startDownload() {

        new Search.DownloadFileAsync().execute();
    }
    class DownloadFileAsync extends AsyncTask<String, String, String> {
        //  ProgressDialog dialog = new ProgressDialog(Listening.this);


        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Search.this);
            dialog.setMessage("Loading...請稍後");
            dialog.show();


            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {


            // new  DownloadFileAsync().cancel(true);
            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused)
        {
            GlobalVariable gv=(GlobalVariable)getApplicationContext();
            account=gv.getEmpacc();
            department=gv.getEmpdepartment();
            name=gv.getEmpname();
            hr=gv.getEmphr();
            mydb();

            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
    public void mydb(){
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy-01-01").format(cDate);
        try{
            String result = dbleasel.executeQuery(account,fDate);
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);

                    emp_id=jsonData.getString("emp_id");
                    type=jsonData.getString("type");
                    myname=jsonData.getString("name");
                    reason=jsonData.getString("reason");
                    String start_d=jsonData.getString("start_d");
                    String start_t=jsonData.getString("start_t");
                    String end_t=jsonData.getString("end_t");
                    String end_d=jsonData.getString("end_d");
                String mylog=jsonData.getString("mylog");
                notes=jsonData.getString("notes");
                    serial_num=jsonData.getString("serial_num");
                    if(notes.equals("1")){

                     Keka  keka = new Keka("      流水號:"+serial_num+"\n      [待銷假]  "+type+"/"+reason+"\n      開始:"+start_d+"/"+start_t+"\n      結束:"+end_d+"/"+end_t+" \n");
                        kekas.add(keka);
                        record.add("\n流水號:"+serial_num+"\n[待銷假]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+"\n"+mylog);
                    }
                    else if(notes.equals("2")){

                        Keka  keka = new Keka("      流水號:"+serial_num+"\n      [已銷假]  "+type+"/"+reason+"\n      開始:"+start_d+"/"+start_t+"\n      結束:"+end_d+"/"+end_t+" \n");
                        kekas.add(keka);
                        record.add("\n      "+serial_num+"\n[已銷假]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+"\n"+mylog);
                    }
                    else if(notes.equals("3")){

                        Keka  keka = new Keka("      流水號:"+serial_num+"\n      [完成請假]  "+type+"/"+reason+"\n      開始:"+start_d+"/"+start_t+"\n      結束:"+end_d+"/"+end_t+" \n");
                        kekas.add(keka);
                        record.add("\n流水號:"+serial_num+"\n[完成請假]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+"\n"+mylog);
                    }
                    else if(notes.equals("")){

                        Keka  keka = new Keka("      流水號:"+serial_num+"\n      [簽核中]  "+type+"/"+reason+"\n      開始:"+start_d+"/"+start_t+"\n      結束:"+end_d+"/"+end_t+" \n");
                        kekas.add(keka);
                        record.add("\n流水號:"+serial_num+"\n[簽核中]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+"\n"+mylog);
                    }



            }
            final KekasAdapter adapter = new KekasAdapter(this, R.layout.keka, kekas);
            mylist.setAdapter(adapter);
            mylist.setTextFilterEnabled(true);
          // listview.setSelector(R.drawable.green);
            mylist.setOnItemClickListener(lstPreferListener);
        }

        catch(Exception e){}
    }




    private ListView.OnItemClickListener lstPreferListener=
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    new AlertDialog.Builder(Search.this)
                            .setTitle("詳細資料")
                            .setIcon(R.drawable.ic_launcher)
                            .setMessage(record.get(position))
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i)
                                {
                                }
                            })
                            .show();



                }
            };
    private ImageView.OnTouchListener retbtn=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    ret.setImageResource(R.drawable.cs_reth);
                    break;
                case MotionEvent.ACTION_UP:
                    ret.setImageResource(R.drawable.cs_ret);
                    Intent intent =new Intent();
                    intent.setClass(Search.this,Empmenu.class);
                    startActivity(intent);
                    break;
            }

            return true;
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
        intent.setClass(Search.this,Empmenu.class);
        startActivity(intent);
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
/**/
        //noinspection SimplifiableIfStatement
        if (id == R.id.video) {
            Intent intent= new Intent();
            intent.setClass(Search.this, MainActivity.class);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}