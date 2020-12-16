package com.cs.day;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cs.control.Drawkeka;
import com.cs.control.DrawkekasAdapter;
import com.cs.control.GlobalVariable;
import com.cs.mydb.dbempsel;
import com.cs.mydb.dbleaselall;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
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

public class Offprograss extends AppCompatActivity {
    ArrayList<Drawkeka> kekas = new ArrayList<Drawkeka>();
    ImageView ret;
    ListView mylist;
    String account,department,name,hr,pwd;
    String substitute="",manager="",emp_id="",type="",time="",hruselea="",serial_num="";
    String myname="",reason="";
    String notes="";
    List<String> record=new ArrayList<>();
    List<Integer> state1=new ArrayList<>();
    List<Integer> state2=new ArrayList<>();
    List<Integer> state3=new ArrayList<>();
    List<String> sub=new ArrayList<>();
    List<String> man=new ArrayList<>();
    private ProgressDialog dialog;
    int count;
    Dialog dia;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offprograss);
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

        new Offprograss.DownloadFileAsync().execute();
    }
    class DownloadFileAsync extends AsyncTask<String, String, String> {
        //  ProgressDialog dialog = new ProgressDialog(Listening.this);


        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Offprograss.this);
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
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        try{
            String result = dbleaselall.executeQuery(account,fDate);
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);

                emp_id=jsonData.getString("emp_id");
                type=jsonData.getString("type");
                myname=jsonData.getString("name");
                reason=jsonData.getString("reason");
                String start_d=jsonData.getString("start_d");
                String start_t=jsonData.getString("start_t");
                String end_t=jsonData.getString("end_t");
                String end_d=jsonData.getString("end_d");
                String mylog=jsonData.getString("mylog");
                notes=jsonData.getString("notes");
                serial_num=jsonData.getString("serial_num");
                substitute=jsonData.getString("substitute");
                manager=jsonData.getString("manager");

                if(substitute.contains("0")){
                    String result2 = dbempsel.executeQuery(substitute);
                    JSONArray jsonArray2 = new JSONArray(result2);
                    JSONObject jsonData2 = jsonArray2.getJSONObject(0);
                    substitute=jsonData2.getString("name");
                }
                if(manager.contains("0")){
                    String result2 = dbempsel.executeQuery(manager);
                    JSONArray jsonArray2 = new JSONArray(result2);
                    JSONObject jsonData2 = jsonArray2.getJSONObject(0);
                    manager=jsonData2.getString("name");
                }
                if(notes.equals("1")){

                    Drawkeka  keka = new Drawkeka("流水號:"+serial_num+"\n[待銷假]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+" \n"
                            ,"請假人:"+name,"代理人:"+substitute,"主管:"+manager,R.drawable.doth,R.drawable.plineh,R.drawable.doth,R.drawable.pline,R.drawable.dot);
                    kekas.add(keka);
                    //,name,substitute,manager,R.drawable.doth,R.drawable.plineh,R.drawable.doth,R.drawable.pline,R.drawable.dot
                    record.add("\n流水號:"+serial_num+"\n[待銷假]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+"\n"+mylog);
                    state1.add(1);
                    state2.add(1);
                    state3.add(0);
                    man.add(manager);
                    sub.add(substitute);
                }
                /**/
                else if(notes.equals("2")){


                    record.add("\n"+serial_num+"\n[已銷假]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+"\n"+mylog);
                    if(substitute.contains("A") &&manager.contains("A")){
                        state1.add(1);
                        state2.add(0);
                        state3.add(0);
                        Drawkeka  keka = new Drawkeka("流水號:"+serial_num+"\n[作廢]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+" \n"
                                ,"請假人:"+name,"代理人:"+substitute,"主管:"+manager,R.drawable.doth,R.drawable.pline,R.drawable.dot,R.drawable.pline,R.drawable.dot);
                        kekas.add(keka);
                        man.add(manager);
                        sub.add(substitute);

                    }
                    else if(!substitute.contains("A") &&manager.contains("A")){
                        state1.add(1);
                        state2.add(1);
                        state3.add(0);
                        Drawkeka  keka = new Drawkeka("流水號:"+serial_num+"\n[待銷假]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+" \n"
                                ,"請假人:"+name,"代理人:"+substitute,"主管:"+manager,R.drawable.doth,R.drawable.plineh,R.drawable.doth,R.drawable.pline,R.drawable.dot);
                        kekas.add(keka);
                        man.add(manager);
                        sub.add(substitute);

                    }
                    else if(!substitute.contains("A") &&!manager.contains("A")){
                        state1.add(1);
                        state2.add(1);
                        state3.add(1);
                        Drawkeka  keka = new Drawkeka("流水號:"+serial_num+"\n[已銷假]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+" \n"
                                ,"請假人:"+name,"代理人:"+substitute,"主管:"+manager,R.drawable.doth,R.drawable.plineh,R.drawable.doth,R.drawable.plineh,R.drawable.doth);
                        kekas.add(keka);
                        man.add(manager);
                        sub.add(substitute);

                    }
                }


                else if(notes.equals("3")){

                    Drawkeka  keka = new Drawkeka("流水號:"+serial_num+"\n[完成請假]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+" \n"
                            ,"請假人:"+name,"代理人:"+substitute,"主管:"+manager,R.drawable.doth,R.drawable.plineh,R.drawable.doth,R.drawable.plineh,R.drawable.doth);
                    kekas.add(keka);
                    record.add("\n流水號:"+serial_num+"\n[完成請假]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+"\n"+mylog);
                    state1.add(1);
                    state2.add(1);
                    state3.add(1);
                    man.add(manager);
                    sub.add(substitute);
                }
                else if(notes.equals("")){

                    Drawkeka  keka = new Drawkeka("流水號:"+serial_num+"\n[簽核中]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+" \n"
                            ,"請假人:"+name,"代理人:"+substitute,"主管:"+manager,R.drawable.doth,R.drawable.pline,R.drawable.dot,R.drawable.pline,R.drawable.dot);
                    kekas.add(keka);
                    record.add("\n流水號:"+serial_num+"\n[簽核中]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+"\n"+mylog);
                    state1.add(1);
                    state2.add(0);
                    state3.add(0);
                    man.add(manager);
                    sub.add(substitute);
                }
                else if(notes.equals("0")){

                    Drawkeka  keka = new Drawkeka("流水號:"+serial_num+"\n[簽核中]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+" \n"
                            ,"請假人:"+name,"代理人:"+substitute,"主管:"+manager,R.drawable.doth,R.drawable.plineh,R.drawable.doth,R.drawable.pline,R.drawable.dot);
                    kekas.add(keka);
                    record.add("\n流水號:"+serial_num+"\n[簽核中]  "+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+"\n"+mylog);
                    state1.add(1);
                    state2.add(1);
                    state3.add(0);
                    man.add(manager);
                    sub.add(substitute);
                }


            }
            final DrawkekasAdapter adapter = new DrawkekasAdapter(this, R.layout.drawkeka, kekas);
            mylist.setAdapter(adapter);
            mylist.setTextFilterEnabled(true);
            // listview.setSelector(R.drawable.green);
           // mylist.setOnItemClickListener(lstPreferListener);
        }

        catch(Exception e){}
    }




    private ListView.OnItemClickListener lstPreferListener=
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    record.get(position);
                    context = Offprograss.this;


                    dia = new Dialog(context, R.style.edit_AlertDialog_style2);
                    dia.setContentView(R.layout.progresshow);
                    TextView person=(TextView)dia.findViewById(R.id.person);
                    TextView subs=(TextView)dia.findViewById(R.id.sub);
                    TextView managers=(TextView)dia.findViewById(R.id.manager);
                    ImageView statedot=(ImageView)dia.findViewById(R.id.statedot);
                    ImageView statedot2=(ImageView)dia.findViewById(R.id.statedot2);
                    ImageView statedot3=(ImageView)dia.findViewById(R.id.statedot3);
                    ImageView stateline=(ImageView)dia.findViewById(R.id.stateline);
                    ImageView stateline2=(ImageView)dia.findViewById(R.id.stateline2);
                    person.setText("請假:"+name);
                    subs.setText("代理人"+sub.get(position));
                    managers.setText("主管:"+man.get(position));
                    statedot.setImageResource(R.drawable.doth);
                    if(state2.get(position)==0){
                        statedot2.setImageResource(R.drawable.dot);
                        stateline.setImageResource(R.drawable.pline);
                    }
                    else{
                        statedot2.setImageResource(R.drawable.doth);
                        stateline.setImageResource(R.drawable.plineh);
                    }
                    if(state3.get(position)==0){
                        statedot3.setImageResource(R.drawable.dot);
                        stateline2.setImageResource(R.drawable.pline);
                    }
                    else{
                        statedot3.setImageResource(R.drawable.doth);
                        stateline2.setImageResource(R.drawable.plineh);
                    }
                    dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
                    Window w = dia.getWindow();
                    WindowManager.LayoutParams lp = w.getAttributes();
                    lp.x = 0;
                    lp.y = 20;
                    lp.width =mylist.getWidth();

                    dia.show();
                    dia.onWindowAttributesChanged(lp);
                    /*
                    new AlertDialog.Builder(Offprogresstest.this)
                            .setTitle("詳細資料")
                            .setIcon(R.drawable.ic_launcher)
                            .setMessage(record.get(position))
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i)
                                {
                                }
                            })
                            .show();
                     */


                }
            };


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
                    intent.setClass(Offprograss.this,Empmenu.class);
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
        intent.setClass(Offprograss.this,Empmenu.class);
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
        if (id == R.id.action_settings) {
            Intent intent= new Intent();
            intent.setClass(Offprograss.this, Mymenu.class);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}