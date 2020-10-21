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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Empmenu extends AppCompatActivity {
    private TextView  dayo,signature,del,search,ret,subsign;
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
        dayo = (TextView)findViewById(R.id.dayo);
        signature = (TextView)findViewById(R.id.signature);
        del = (TextView)findViewById(R.id.del);
        ret = (TextView)findViewById(R.id.ret);
        search= (TextView)findViewById(R.id.search);
        subsign = (TextView)findViewById(R.id.subsign);
        dayo.setOnTouchListener(getdayo);
        signature.setOnTouchListener(getsign);
        del.setOnTouchListener(getdel);
        ret.setOnTouchListener(getret);
        search.setOnTouchListener(searchbtn);
        subsign.setOnTouchListener(subsignbtn);
    }
    private TextView.OnTouchListener subsignbtn = new TextView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    subsign.setBackgroundResource(R.drawable.cs_txtbtnh);
                    break;
                case  MotionEvent.ACTION_UP:
                    subsign.setBackgroundResource(R.drawable.cs_txtbtn);
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
    private TextView.OnTouchListener getdayo = new TextView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    dayo.setBackgroundResource(R.drawable.cs_txtbtnh);
                    break;
                case  MotionEvent.ACTION_UP:

                    dayo.setBackgroundResource(R.drawable.cs_txtbtn);
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

    private TextView.OnTouchListener getsign = new TextView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    signature.setBackgroundResource(R.drawable.cs_txtbtnh);
                    break;
                case  MotionEvent.ACTION_UP:
                    signature.setBackgroundResource(R.drawable.cs_txtbtn);
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
    private TextView.OnTouchListener getdel = new TextView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    del.setBackgroundResource(R.drawable.cs_txtbtnh);
                    break;
                case  MotionEvent.ACTION_UP:
                    del.setBackgroundResource(R.drawable.cs_txtbtn);
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
    private TextView.OnTouchListener searchbtn = new TextView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    search.setBackgroundResource(R.drawable.cs_txtbtnh);
                    break;
                case  MotionEvent.ACTION_UP:
                    search.setBackgroundResource(R.drawable.cs_txtbtn);
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
    private TextView.OnTouchListener getret = new TextView.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    ret.setBackgroundResource(R.drawable.cs_txtbtnh);
                    break;
                case  MotionEvent.ACTION_UP:
                    ret.setBackgroundResource(R.drawable.cs_txtbtn);
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