package com.cs.day;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.control.GlobalVariable;
import com.cs.control.JobSchedulerService;
import com.cs.mydb.db;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ImageView login,img;//,signature,del;
    private EditText acc;
    private EditText pwd;
    private TextView show;
    private ProgressDialog dialog;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         */
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
        findViews();
        setListeners();

        //startDownload();
        /* */



        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
        {
            mytoast("請開啟網路!");
        }
        else{
            // schedulejob(h);
        }
        schedulejob();
    }




    public void schedulejob(){
        ComponentName componentName=new ComponentName(this, JobSchedulerService.class);
        PersistableBundle bundle = new PersistableBundle();
        SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
        String name_str=remdname.getString("emp_id", "");
            bundle.putString("INPUT",name_str);

     JobInfo jobInfo= new JobInfo.Builder(123,componentName)
             .setPersisted(true) // 重開機後是否執行
             .setMinimumLatency(10000) // 延遲多久執行
             .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) //網路條件
             .setExtras(bundle)
             .build();

     JobScheduler scheduler=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
   int result=scheduler.schedule(jobInfo);
   if(result==JobScheduler.RESULT_SUCCESS){}
   else {}
/* */
    }
    private void setListeners() {
        login.setOnTouchListener(getDBRecord);
        //  signature.setOnClickListener(getsign);
        //  del.setOnClickListener(getdel);
    }

    private void startDownload() {

        new MainActivity.DownloadFileAsync().execute();
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            /*
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading...請稍後");
            dialog.show();
            */
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... aurl) {
           // schedulejob();
            return null;
        }

        protected void onProgressUpdate(String... progress) {
        }

        @Override
        protected void onPostExecute(String unused)
        {
            //mydb(b);
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
    private void findViews() {

        login = (ImageView)findViewById(R.id.login);
        // signature = (ImageView)findViewById(R.id.signature);
        //  del = (ImageView)findViewById(R.id.del);
        acc=(EditText)findViewById(R.id.acc);
        pwd=(EditText)findViewById(R.id.pwd);

        show=(TextView)findViewById(R.id.show);
        SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
        String name_str=remdname.getString("emp_id", "");
        String pass_str=remdname.getString("passwd", "");

            acc.setText(name_str);
            pwd.setText(pass_str);


        acc.setOnFocusChangeListener(accbt);
        pwd.setOnFocusChangeListener(pwdbt);
    }
    public EditText.OnFocusChangeListener accbt=new EditText.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if(hasFocus){
                acc.setHint("");
                pwd.setHint("密碼");
            }
        }

    };
    public EditText.OnFocusChangeListener pwdbt=new EditText.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if(hasFocus){
                pwd.setHint("");
                acc.setHint("帳號");
            }
        }

    };
    public void mydb(){
        String emp_id=acc.getText().toString();
        String passwd=pwd.getText().toString();
        SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit=remdname.edit();
        edit.putString("emp_id", acc.getText().toString());
        edit.putString("passwd", pwd.getText().toString());
        edit.commit();
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
        {
            mytoast("請開啟網路!");
        }
        else{



        try {


            String result = db.executeQuery(emp_id,passwd);
            // mytoast(result);
            if(result==""||result.contains("null")){
                mytoast("帳號或密碼錯誤!");
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
            JSONArray jsonArray = new JSONArray(result);
            Runnable progressRunnable = new Runnable() {

                @Override
                public void run() {
                    dialog.cancel();
                }
            };

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 3000);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                String str=jsonData.getString("emp_id");
                String account=acc.getText().toString();
                String department=jsonData.getString("department");
                String name=jsonData.getString("name");
                String hr=jsonData.getString("hrremain");
                String pwd=jsonData.getString("pwd");
                show.setText("歡迎 "+name);


                //Class.forName(b)
                GlobalVariable gv = (GlobalVariable)getApplicationContext();
                gv.setEmpacc(account);
                gv.setEmppwd(pwd);
                gv.setEmpdepartment(department);
                gv.setEmpname(name);
                gv.setEmphr(hr);
                /*
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
*/
                //timer.cancel();



            }
        } catch(Exception e) {}
    }

    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    private ImageView.OnTouchListener getDBRecord = new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    login.setImageResource(R.drawable.cs_loginh);



                    break;
                case MotionEvent.ACTION_UP:

                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setMessage("Loading...請稍後");
                    dialog.show();
                    GlobalVariable gv=(GlobalVariable)getApplicationContext();

                    if(gv.getEmpacc()==null || gv.getEmpdepartment()==null ||  gv.getEmpname()==null ||  gv.getEmphr()==null )
                    {
                        mydb();
                    }
                    if(gv.getEmpacc()!=null && gv.getEmpdepartment()!=null && gv.getEmpname()!=null && gv.getEmphr()!=null){
                        Intent intent=new Intent();
                        intent.setClass(MainActivity.this,Mymenu.class);
                        startActivity(intent);

                    }

                    login.setImageResource(R.drawable.cs_login);



                    break;
            }
            /**/
            return true;
        }
    };
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
            intent.setClass(MainActivity.this, MainActivity.class);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
