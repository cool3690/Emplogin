package com.cs.day;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Gravity;
import android.widget.Toast;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    private static final String TAG="JobSchedulerService";
    private Handler handler = new Handler();
    String account="q";
    int i=0;
    private boolean jobcancel=false;
    public static final  String CHANNEL_ID          = "default";
    private static final String CHANNEL_NAME        = "Default Channel";
    private static final String CHANNEL_DESCRIPTION = "this is default channel!";
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
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
                  //  createNotificationChannel(CHANNEL_ID,CHANNEL_NAME, 100);
                    handler.postDelayed(this, 1000);
                    i++;
                }
            } catch(Exception e) {}

          if(i==2)jobcancel=true;
           if(jobcancel){return;}

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "upgrade";
                String channelName = "待簽表單";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                createNotificationChannel(channelId, channelName, importance);

            }


                handler.postDelayed(this,3000);

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