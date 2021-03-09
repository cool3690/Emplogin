package com.cs.day;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cs.mydb.dbbtsel;
import com.cs.mydb.dbbtsel2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Bulletinmore extends AppCompatActivity {
    Dialog dia;
    boolean tf=true;
    Context context;
    int num=1;
        TextView title,depart,date,content;
        String pos="";
        ArrayList myid=new ArrayList<String>();
        Button bt;
    String mytitle="",mycontent="",mydepart="",mydate="",myauthority="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletinmore);
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
        bt=(Button)findViewById(R.id.bt);
        title=(TextView)findViewById(R.id.title);
        depart=(TextView)findViewById(R.id.depart);
        content=(TextView)findViewById(R.id.content);
        date=(TextView)findViewById(R.id.date);
        // 取得  bundle
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        pos=bundle.getString("POSITION");
        myid=bundle.getStringArrayList("ID");
        int a=Integer.parseInt(pos);
        String tmp=myid.get(a)+"";
       mydb(tmp);
       bt.setOnClickListener(btn);


    }
    private  Button.OnClickListener btn=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            context = Bulletinmore.this;
            dia = new Dialog(context, R.style.edit_AlertDialog_style);
            dia.setContentView(R.layout.dailogshow);
            final ImageView imageView = (ImageView) dia.findViewById(R.id.start_img);
            TextView show=(TextView)dia.findViewById(R.id.show);
            Button btok=(Button)dia.findViewById(R.id.btok);

//////////////////////////////////////////
            String myurl="https://demo.chansing.com.tw/bt_pic/";
            if(Integer.parseInt(pos)==0){
                myurl+= "cs0.jpg";
            }
            else if(Integer.parseInt(pos)==1){
                myurl+= "cs0.jpg";
            }
            new AsyncTask<String, Void, Bitmap>()
            {
                @Override
                protected Bitmap doInBackground(String... params) //實作doInBackground
                {
                    String url = params[0];
                    return getBitmapFromURL(url);
                }

                @Override
                protected void onPostExecute(Bitmap result) //當doinbackground完成後
                {
                    imageView.setImageBitmap (result);
                    super.onPostExecute(result);
                }
            }.execute(myurl);

            dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
            Window w = dia.getWindow();
            WindowManager.LayoutParams lp = w.getAttributes();
            lp.x = 0;
            lp.y = 20;
            dia.show();
            dia.onWindowAttributesChanged(lp);
            imageView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dia.dismiss();
                        }
                    });
            btok.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dia.dismiss();
                        }
                    }
            );

        }
    };
    private static Bitmap getBitmapFromURL(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        }
        catch (IOException e){  return null;}
    }
    public void mydb(String id){

        try{
            String result = dbbtsel2.executeQuery(id);
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);

                mytitle=jsonData.getString("title");
                mycontent=jsonData.getString("content");
                mydepart=jsonData.getString("depart");
                mydate=jsonData.getString("date");
                myauthority=jsonData.getString("authority");
                /**/
                    title.setText(mytitle);
                    content.setText(mycontent);
                    depart.setText(mydepart);
                    date.setText(mydate);




            }


        }

        catch(Exception e){}
    }
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
        intent.setClass(Bulletinmore.this,Bulletin.class);
        startActivity(intent);
    }
}