package com.cs.day;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Empmenu extends AppCompatActivity {
    private ImageView dayo,signature,del,search,ret,subsign;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empmenu);
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
        dayo = (ImageView)findViewById(R.id.dayo);
        signature = (ImageView)findViewById(R.id.signature);
        del = (ImageView)findViewById(R.id.del);
        ret = (ImageView)findViewById(R.id.ret);
        search= (ImageView)findViewById(R.id.search);
        subsign = (ImageView)findViewById(R.id.subsign);
        dayo.setOnTouchListener(getdayo);
        signature.setOnTouchListener(getsign);
        del.setOnTouchListener(getdel);
        ret.setOnTouchListener(getret);
        search.setOnTouchListener(searchbtn);
        subsign.setOnTouchListener(subsignbtn);
    }
    private ImageView.OnTouchListener subsignbtn = new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    subsign.setImageResource(R.drawable.subsignh);
                    break;
                case  MotionEvent.ACTION_UP:
                    subsign.setImageResource(R.drawable.subsign);
                    dialog = new ProgressDialog(Empmenu.this);
                    dialog.setMessage("Loading...請稍後");
                    dialog.show();
                    Intent intent=new Intent();
                    intent.setClass(Empmenu.this,Subsign.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }

    };
    private ImageView.OnTouchListener getdayo = new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    dayo.setImageResource(R.drawable.cs_offh);
                    break;
                case  MotionEvent.ACTION_UP:
                    dayo.setImageResource(R.drawable.cs_off);
                    dialog = new ProgressDialog(Empmenu.this);
                    dialog.setMessage("Loading...請稍後");
                    dialog.show();
                    Intent intent=new Intent();
                    intent.setClass(Empmenu.this,Off.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }

    };

    private ImageView.OnTouchListener getsign = new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    signature.setImageResource(R.drawable.cs_signh);
                    break;
                case  MotionEvent.ACTION_UP:
                    signature.setImageResource(R.drawable.cs_sign);
                    dialog = new ProgressDialog(Empmenu.this);
                    dialog.setMessage("Loading...請稍後");
                    dialog.show();
                    Intent intent=new Intent();
                    intent.setClass(Empmenu.this,Check.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }

    };
    private ImageView.OnTouchListener getdel = new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    del.setImageResource(R.drawable.cs_cancelh);
                    break;
                case  MotionEvent.ACTION_UP:
                    del.setImageResource(R.drawable.cs_cancel);
                    dialog = new ProgressDialog(Empmenu.this);
                    dialog.setMessage("Loading...請稍後");
                    dialog.show();

                    Intent intent=new Intent();
                    intent.setClass(Empmenu.this,Deloff.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }



    };
    private ImageView.OnTouchListener searchbtn = new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    search.setImageResource(R.drawable.cs_recordh);
                    break;
                case  MotionEvent.ACTION_UP:
                    search.setImageResource(R.drawable.cs_record);
                    Intent intent=new Intent();
                    intent.setClass(Empmenu.this,Search.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
        public void onClick(View v) {
            // TODO Auto-generated method stub

            dialog = new ProgressDialog(Empmenu.this);
            dialog.setMessage("Loading...請稍後");
            dialog.show();

            Intent intent=new Intent();
            intent.setClass(Empmenu.this,Search.class);
            startActivity(intent);

            /* */
        }
    };
    private ImageView.OnTouchListener getret = new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    ret.setImageResource(R.drawable.cs_logouth);
                    break;
                case  MotionEvent.ACTION_UP:
                    ret.setImageResource(R.drawable.cs_logout);
                    Intent intent=new Intent();
                    intent.setClass(Empmenu.this,MainActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }


    };
    public void mydb(String b){


        Intent intent=new Intent();
        try {
            intent.setClass(Empmenu.this,Class.forName(b));
            startActivity(intent);

        } catch (ClassNotFoundException e) {

        }

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.video) {
            Intent intent= new Intent();
            intent.setClass(Empmenu.this, MainActivity.class);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*
    @Override
    public void onPause(){

        Intent intent =new Intent();
        intent.setClass(Empmenu.this,Emplogin.class);
        startActivity(intent);
        super.onPause();
    }
*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();  // Always call the superclass method first

        Intent intent =new Intent();
        intent.setClass(Empmenu.this,MainActivity.class);
        startActivity(intent);
    }
}