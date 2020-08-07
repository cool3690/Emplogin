package com.cs.day;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Gravity;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    private static final String TAG="JobSchedulerService";
    private Handler handler = new Handler();

        double money=0;
    private boolean jobcancel=false;
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

       String g= jobParameters.getExtras().getString("INPUT");


        SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);
        String h = sharedPreferences.getString("input" , " ");

        handler.postDelayed(showTime,5000);
        return true;
    }



    private void testNotification(String s) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .setOngoing(false);

        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        //LED
       // builder.setLights(Color.RED, 3000, 3000);

        builder.setContentText(s);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    private Runnable showTime = new Runnable() {
        public void run() {

            try{

            }
            catch(Exception ex){} //  Log.d(TAG,""+tmp);

           String str="qwerty";
           if(jobcancel){return;}

             testNotification(str);


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