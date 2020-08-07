package com.cs.day;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class BootCompletedReceiver extends BroadcastReceiver {

      //  throw new UnsupportedOperationException("Not yet implemented");

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
        /* */
           // Toast.makeText(context, "OK ", Toast.LENGTH_LONG).show();
          Intent newIntent = new Intent(context, JobSchedulerService.class);
          newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //注意，必须添加这个标记，否则启动会失败
          context.startActivity(newIntent);
        }
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            Toast.makeText(context, "Connectivity changed", Toast.LENGTH_SHORT).show();
        }
    }
}
