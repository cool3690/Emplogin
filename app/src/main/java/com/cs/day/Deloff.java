package com.cs.day;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.content.Intent;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import android.widget.CheckedTextView;
import android.widget.EditText;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cs.control.GlobalVariable;


import com.cs.mydb.dbcheck;
import com.cs.mydb.dbleadel;
import com.cs.mydb.dbleasel;
import com.cs.mydb.dbleaud;

public class Deloff extends AppCompatActivity {//取消請假
    private ImageView okbtn,fin;
    private TextView show;
    private ListView prefer;
    boolean check=false;
    List<String> list;
    List<Boolean> listShow;
    // 這個用來記錄哪幾個 item 是被打勾的
    String account,department,name,hr,pwd;
    String substitute="",manager="",emp_id="",type="",time="",hruselea="",serial_num="";
    String myname="",reason="";
    Dialog dia;
    Context context;
    Date cDate = new Date();
    String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
    EditText rea;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deloff);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        okbtn=(ImageView)findViewById(R.id.okbtndel);
        okbtn.setOnTouchListener(checkok);
        fin=(ImageView)findViewById(R.id.fin);
        fin.setOnTouchListener(finbtn);
        prefer=(ListView)findViewById(R.id.preferdel);
        show=(TextView)findViewById(R.id.show);
        rea=(EditText) findViewById(R.id.rea);
        // 以多選範本 建立  ArrayAdapter
        GlobalVariable gv=(GlobalVariable)getApplicationContext();
        account=gv.getEmpacc();
        department=gv.getEmpdepartment();
        name=gv.getEmpname();
        hr=gv.getEmphr();


        listShow = new ArrayList<Boolean>();
        list = new ArrayList<String>();
        try{   //dbleasel

            String result = com.cs.mydb.dbleasel.executeQuery(account,fDate);
            JSONArray jsonArray = new JSONArray(result);
            // show.setText("a"+":"+account);



            for(int i = 0; i < jsonArray.length(); i++)
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                serial_num=jsonData.getString("serial_num");
                String notes=jsonData.getString("notes");
                if(!notes.equals("2")){

                    emp_id=jsonData.getString("emp_id");
                    type=jsonData.getString("type");
                    myname=jsonData.getString("name");
                    reason=jsonData.getString("reason");
                    String start_d=jsonData.getString("start_d");
                    String start_t=jsonData.getString("start_t");
                    String end_t=jsonData.getString("end_t");
                    String end_d=jsonData.getString("end_d");

                    if(notes.equals("1")){
                        /*
                        list.add("\n[待銷假]  "+type+"/"+reason+"\n"+start_d+"/"+start_t+"\n"+end_d+"/"+end_t+"\n");
                        listShow.add(false);

                         */
                    }
                    else{
                        list.add("\n"+serial_num+"\n"+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+"\n");
                        listShow.add(false);
                    }

                }

            }

        }

        catch(Exception e){}
        RatesAdapter adapterItem = new RatesAdapter(this, list);
        prefer.setAdapter(adapterItem);

        prefer.setOnItemClickListener(lstPreferListener);

    }
    private ImageView.OnTouchListener checkok=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    okbtn.setImageResource(R.drawable.cs_cancelh);
                    break;
                case  MotionEvent.ACTION_UP:
                    okbtn.setImageResource(R.drawable.cs_cancel);
                    try{
                        String result = dbleasel.executeQuery(account,fDate);
                        JSONArray jsonArray = new JSONArray(result);


                        for(int i = 0; i < jsonArray.length(); i++)
                        {if (listShow.get(i)==true){
                            JSONObject jsonData = jsonArray.getJSONObject(i);
                            emp_id=jsonData.getString("emp_id");
                            substitute=jsonData.getString("substitute");
                            manager=jsonData.getString("manager");
                            type=jsonData.getString("type");
                            myname=jsonData.getString("name");
                            reason=jsonData.getString("reason");
                            String lea_id=jsonData.getString("lea_id");
                            String mylog=jsonData.getString("mylog");
                            hruselea=jsonData.getString("hruse");
                            if(!TextUtils.isEmpty( rea.getText().toString())){
                                reason= rea.getText().toString();
                            }
                            else{
                                mytoast("請輸入取消理由");
                                break;
                            }
                            if(!type.contains("特休"))
                            {
                                if(manager.contains("A") || manager.contains("B")
                                        || manager.contains("C"))
                                {
                                    String r2 = com.cs.mydb.dbcheck.executeQuery(emp_id);
                                    JSONArray jsonArr2 = new JSONArray(r2);
                                    for(int j = 0; j < jsonArr2.length(); j++)
                                    {
                                        JSONObject jsonData2 = jsonArr2.getJSONObject(j);
                                        String cmanager=jsonData2.getString("manager");
                                        //      show.setText(cmanager);
                                        mylog+=fDate+"請假作廢"+"\n";
                                        com.cs.mydb.dbleaud.executeQuery(emp_id,reason,lea_id,"2",cmanager,mylog+"取消理由:"+reason+"\n");
                                        check=true;
                                    }
                                    //
                                }
                                else if(!manager.contains("A") || !manager.contains("B")
                                        || !manager.contains("C"))
                                {
                                    String r2 = com.cs.mydb.dbcheck.executeQuery(emp_id);
                                    JSONArray jsonArr2 = new JSONArray(r2);
                                    for(int j = 0; j < jsonArr2.length(); j++)
                                    {
                                        JSONObject jsonData2 = jsonArr2.getJSONObject(j);
                                        String cmanager=jsonData2.getString("manager");
                                        //      show.setText(cmanager);
                                        mylog+=fDate+"取消請假"+"\n";
                                        com.cs.mydb.dbleaud.executeQuery(emp_id,reason,lea_id,"1",cmanager,mylog+"取消理由:"+reason+"\n");
                                        check=true;
                                    }
                                    //
                                }
                                else{ com.cs.mydb.dbleadel.executeQuery(emp_id,lea_id);}
                            }

                            else
                            {

                                if(!manager.contains("A")|| !manager.contains("B")
                                        || !manager.contains("C"))
                                {String r2 = dbcheck.executeQuery(emp_id);
                                    JSONArray jsonArr2 = new JSONArray(r2);
                                    for(int j = 0; j < jsonArr2.length(); j++)
                                    {
                                        JSONObject jsonData2 = jsonArr2.getJSONObject(j);
                                        String cmanager=jsonData2.getString("manager");
                                        //    show.setText(cmanager);
                                        mylog+=fDate+"取消請假"+"\n";
                                        dbleaud.executeQuery(emp_id,reason,lea_id,"1",cmanager,mylog+"取消理由:"+reason+"\n");
                                        check=true;
                                    }
                                }
                                else{
                                    dbleadel.executeQuery(emp_id,lea_id);
                                }


                            }

                        }
                            if(check && i == jsonArray.length()-1){
                                mydialog();
                            }

                        }

                        //   if(i ==jsonArray.length()-1){}
                        /**/
                        check=false;



                    }

                    catch(Exception e){}


                    break;
            }
            refresh();
            return true;
        }

    };
    private void mydialog(){
        /*
        new AlertDialog.Builder(Off.this)
                .setTitle("確認視窗")
                .setIcon(R.drawable.ic_launcher)
                .setMessage("請假完成!")
                .show();

         */
        context = Deloff.this;
        dia = new Dialog(context, R.style.edit_AlertDialog_style);
        dia.setContentView(R.layout.imgshow);
        ImageView imageView = (ImageView) dia.findViewById(R.id.start_img);
        //  imageView.setImageResource(R.drawable.cs_signshow);
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
    }

    private void refresh() {
        //finish();
        Intent intent = new Intent(Deloff.this, Deloff.class);
        startActivity(intent);
    }

    //  定義  onItemClick 方法
    private ListView.OnItemClickListener lstPreferListener=
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    CheckedTextView chkItem = (CheckedTextView) v.findViewById(R.id.check1);
                    chkItem.setChecked(!chkItem.isChecked());

                    listShow.set(position, chkItem.isChecked());

                }
            };
    private ImageView.OnTouchListener finbtn=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    fin.setImageResource(R.drawable.cs_reth);
                    break;
                case  MotionEvent.ACTION_UP:
                    fin.setImageResource(R.drawable.cs_ret);
                    Intent intent = new Intent(Deloff.this, Empmenu.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(Deloff.this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    /* */
    @Override
    public void onBackPressed() {
        super.onBackPressed();  // Always call the superclass method first

        Intent intent =new Intent();
        intent.setClass(Deloff.this,Empmenu.class);
        startActivity(intent);
    }


}