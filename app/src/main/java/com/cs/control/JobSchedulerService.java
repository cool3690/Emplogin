package com.cs.control;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Gravity;
import android.widget.Toast;


import com.cs.day.MainActivity;
import com.cs.day.R;
import com.cs.mydb.dbleanoti;
import com.cs.mydb.dbleanoti2;

import org.json.JSONArray;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    private static final String TAG="JobSchedulerService";
    private Handler handler = new Handler();
    String account="q";
    int i=0;
    private boolean jobcancel=false;
    private static final String TEST_NOTIFY_ID = "test_notyfy_id";
    private static final int NOTYFI_REQUEST_ID = 300;
    private int testNotifyId = 11;
    @Override
    public boolean onStartJob(JobParameters jobParameters ) {
     //   Log.d(TAG, "JobSchedulerService onStartJob");
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

        account= jobParameters.getExtras().getString("INPUT");


        SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);
        String h = sharedPreferences.getString("input" , " ");

        handler.postDelayed(showTime,5000);

        return true;
    }


    public void showNotification(String s) {

        Intent intent =new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                NOTYFI_REQUEST_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("待簽表單")
                .setContentText("待簽表單有"+s+"張")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);
        NotificationChannel channel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(TEST_NOTIFY_ID
                    , "全興請假APP"
                    , NotificationManager.IMPORTANCE_HIGH);
            builder.setChannelId(TEST_NOTIFY_ID);
            manager.createNotificationChannel(channel);
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }
        manager.notify(testNotifyId,
                builder.build());
    }
    private void testNotification(String s) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .setOngoing(false);

      //  builder.setVibrate(new long[] { 1000, 1000 });
        //LED
        builder.setLights(Color.RED, 3000, 3000);
        builder.setContentText("待簽表單有"+s+"張");
         builder.setContentTitle("待簽表單");
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
    //@RequiresApi(api = Build.VERSION_CODES.O)
    private Runnable showTime = new Runnable() {

        public void run() {


            try {
                String result = dbleanoti.executeQuery(account);
                JSONArray jsonArray = new JSONArray(result);
                if(jsonArray.length()>0) {
                    // JSONObject jsonData = jsonArray.getJSONObject(i);
                    testNotification(jsonArray.length()+"");
                    showNotification(jsonArray.length()+"");
                    i++;
                    if(i==2)jobcancel=true;
                    if(jobcancel){return;}
                    handler.postDelayed(this, 1000);

                }

            } catch(Exception e) {}
            ////////////////////////
            try {
                String result = dbleanoti2.executeQuery(account);
                JSONArray jsonArray = new JSONArray(result);
                if(jsonArray.length()>0) {
                    // JSONObject jsonData = jsonArray.getJSONObject(i);
                    testNotification(jsonArray.length()+"");
                    showNotification(jsonArray.length()+"");
                    i++;
                    if(i==2)jobcancel=true;
                    if(jobcancel){return;}
                    handler.postDelayed(this, 1000);

                }

            } catch(Exception e) {}



                handler.postDelayed(this,3000000);

        }
    };
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
jobcancel=true;
       return true;
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}