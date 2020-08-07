package com.cs.day;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.channels.Channel;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ImageView login,img;//,signature,del;
    private EditText acc;
    private EditText pwd;
    private TextView show;
    private ProgressDialog dialog;

    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        findViews();
        setListeners();
        schedulejob();

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

        /*
schedulejob();
        Intent intent2 = new Intent(MainActivity.this, NickyService.class);

        intent2.putExtra("ACCOUNT", name_str);
        startService(intent2);

         */
      //  find();
        Notify();

    }
    public void Notify() {
        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

// 建立通知物件，設定小圖示、大圖示、內容標題、內容訊息、時間
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("標題")
                .setContentText("訊息內容")
                .setWhen(System.currentTimeMillis())
                .build();

// 使用0為編號發出通知
        manager.notify(0, notification);
    }
    public void schedulejob(){

        ComponentName componentName=new ComponentName(this,JobSchedulerService.class);
        PersistableBundle bundle = new PersistableBundle();
      //  String tmp="0";
        SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
        String name_str=remdname.getString("emp_id", "");


            bundle.putString("INPUT",name_str);


     //   Intent intent2 = new Intent(MainActivity.this, NickyService.class);

      //  intent2.putExtra("ACCOUNT", name_str);
     //   startService(intent2);



/* */
     JobInfo jobInfo= new JobInfo.Builder(123,componentName)
             .setPersisted(true) // 重開機後是否執行
             .setMinimumLatency(3000) // 延遲多久執行
             .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) //網路條件
             .setExtras(bundle)
             .build();

     JobScheduler scheduler=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
   int result=scheduler.schedule(jobInfo);
   if(result==JobScheduler.RESULT_SUCCESS){}
   else {}

    }
    private void setListeners() {
        login.setOnTouchListener(getDBRecord);
        //  signature.setOnClickListener(getsign);
        //  del.setOnClickListener(getdel);
    }

    private ImageView.OnTouchListener getDBRecord = new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    login.setImageResource(R.drawable.cs_loginh);

                    break;
                case MotionEvent.ACTION_UP:

                    login.setImageResource(R.drawable.cs_login);
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setMessage("Loading...請稍後");
                    dialog.show();

                    mydb();
                    break;
            }
          /**/
            return true;
        }
    };
    /*
    private ImageView.OnClickListener getsign = new ImageView.OnClickListener() {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            String b="com.cs.monitor.Check";
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading...請稍後");
            dialog.show();
            mydb(b);
        }
    };
    private ImageView.OnClickListener getdel = new ImageView.OnClickListener() {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            String b="com.cs.monitor.Deloff";

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading...請稍後");
            dialog.show();
            mydb(b);
        }
    };

     */
    private void testNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .setOngoing(true);
        builder.setLights(Color.GREEN, 1000, 1000);

        builder.setContentText("通知,代理人情報");
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    public void mydb(){
        String emp_id=acc.getText().toString();
        String passwd=pwd.getText().toString();
        SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit=remdname.edit();
        edit.putString("emp_id", acc.getText().toString());
        edit.putString("passwd", pwd.getText().toString());
        edit.commit();

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

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                String str=jsonData.getString("emp_id");
                String account=acc.getText().toString();
                String department=jsonData.getString("department");
                String name=jsonData.getString("name");
                String hr=jsonData.getString("hrremain");
                String pwd=jsonData.getString("pwd");
                show.setText("歡迎 "+name);

                Intent intent=new Intent();
                intent.setClass(MainActivity.this,Empmenu.class);
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
                startActivity(intent);


            }
        } catch(Exception e) {}


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

        @Override
        protected String doInBackground(String... aurl) {
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

}
