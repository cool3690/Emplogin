package com.cs.day;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
            String result =dbleasel.executeQuery(account,fDate);
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                if(!notes.equals("2")){
                    emp_id=jsonData.getString("emp_id");
                    type=jsonData.getString("type");
                    myname=jsonData.getString("name");
                    reason=jsonData.getString("reason");
                    String start_d=jsonData.getString("start_d");
                    String start_t=jsonData.getString("start_t");
                    String end_t=jsonData.getString("end_t");
                    String end_d=jsonData.getString("end_d");
                    serial_num=jsonData.getString("serial_num");
                    if(notes.equals("1")){

                     Keka  keka = new Keka("\n"+serial_num+"\n[待銷假]  "+type+"/"+reason+"\n"+start_d+"/"+start_t+"\n"+end_d+"/"+end_t+"\n");
                        kekas.add(keka);
                    }
                    else{

                        Keka  keka = new Keka("\n"+serial_num+"\n"+type+"/"+reason+"\n"+start_d+"/"+start_t+"\n"+end_d+"/"+end_t+"\n");
                        kekas.add(keka);
                    }

                }

            }
            final KekasAdapter adapter = new KekasAdapter(this, R.layout.keka, kekas);
            mylist.setAdapter(adapter);
            mylist.setTextFilterEnabled(true);
          // listview.setSelector(R.drawable.green);
        }

        catch(Exception e){}
    }
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
        intent.setClass(Search.this,MainActivity.class);
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