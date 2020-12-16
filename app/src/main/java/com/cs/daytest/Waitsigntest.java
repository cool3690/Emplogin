package com.cs.daytest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cs.control.GlobalVariable;
import com.cs.day.Check;
import com.cs.day.Empmenu;
import com.cs.day.Mymenu;
import com.cs.day.RatesAdapter;
import com.cs.mydbtest.dbemp;
import com.cs.mydbtest.dbempcom;
import com.cs.mydbtest.dbempup;
import com.cs.mydbtest.dbleaup;
import com.cs.mydb.dbleave2man;
import com.cs.mydb.dbwaitsign;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cs.day.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Waitsigntest extends AppCompatActivity {
    private ImageView okbtn,fin;
    private TextView show;
    private ListView prefer;
    Boolean check=false;
    String account,department,name,hr,pwd;
    String substitute="",manager="",emp_id="",type="",time="",hruselea="",serial_num="";
    String myname="",reason="";
    String notes="";
    List<String> list;
    List<Boolean> listShow;
    private ProgressDialog dialog;
    int count;
    Dialog dia;
    Context context;
    Date cDate = new Date();
    String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitsigntest);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        okbtn=(ImageView)findViewById(R.id.okbtn);
        okbtn.setOnTouchListener(checkok);
        fin=(ImageView)findViewById(R.id.fin);
        fin.setOnTouchListener(finbtn);
        prefer=(ListView)findViewById(R.id.prefer);
        show=(TextView)findViewById(R.id.show);

        /*
        // 取得  bundle
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        account=bundle.getString("ACCOUNT");

        GlobalVariable gv=(GlobalVariable)getApplicationContext();
        account=gv.getEmpacc();
        department=gv.getEmpdepartment();
        name=gv.getEmpname();
        hr=gv.getEmphr(); */
        startDownload();
        //代理或主管有工號者顯示
        //show.setText(account);
        listShow = new ArrayList<Boolean>();
        list = new ArrayList<String>();

        RatesAdapter adapterItem = new RatesAdapter(this, list);
        prefer.setAdapter(adapterItem);

        // 設定 lstPrefer 元件 ItemClick 事件的 listener 為 lstPreferListener
        prefer.setOnItemClickListener(lstPreferListener);

    }
    private void startDownload() {

        new Waitsigntest.DownloadFileAsync().execute();
    }
    class DownloadFileAsync extends AsyncTask<String, String, String> {
        //  ProgressDialog dialog = new ProgressDialog(Listening.this);


        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Waitsigntest.this);
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
        try{
            String result = dbwaitsign.executeQuery(account);
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                serial_num=jsonData.getString("serial_num");
                emp_id=jsonData.getString("emp_id");
                substitute=jsonData.getString("substitute");
                manager=jsonData.getString("manager");
                type=jsonData.getString("type");
                myname=jsonData.getString("name");
                reason=jsonData.getString("reason");

                hruselea=jsonData.getString("hruse");
                notes=jsonData.getString("notes");
                String start_d=jsonData.getString("start_d");
                String end_d=jsonData.getString("end_d");
                String start_t=jsonData.getString("start_t");
                String end_t=jsonData.getString("end_t");
                show.setText("");

                //jsonData.getString("start_t").substring(0, 5)+"~";

                // if(substitute.contains("A")&&substitute.equals(account) ||!substitute.contains("A")&&manager.equals(account))
                //                {}


                    if(notes.equals("1"))
                    {  list.add("流水號:"+serial_num+"\n"+"銷假:"+myname+"/"+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+"\n");
                        listShow.add(false);
                    }
                    else{
                        list.add("流水號:"+serial_num+"\n請假:"+myname+"/"+type+"/"+reason+"\n開始:"+start_d+"/"+start_t+"\n結束:"+end_d+"/"+end_t+"\n");
                        listShow.add(false);
                    }



            }

        }

        catch(Exception e){}
    }
    private ImageView.OnTouchListener checkok=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    okbtn.setImageResource(R.drawable.cs_signh);
                    break;
                case  MotionEvent.ACTION_UP:
                    okbtn.setImageResource(R.drawable.cs_sign);
                    try{
                        String result =dbwaitsign.executeQuery(account);//代理或主管有工號者顯示

                        JSONArray jsonArray = new JSONArray(result);
                        count=prefer.getCount();// for(int i=0;i<listShow.size();i++)


                        for(int i = 0; i < jsonArray.length(); i++)
                        {if (listShow.get(i)==true){//代理或主管有工號者顯示
                            JSONObject jsonData = jsonArray.getJSONObject(i);
                            serial_num=jsonData.getString("serial_num");
                            emp_id=jsonData.getString("emp_id");
                            substitute=jsonData.getString("substitute");
                            manager=jsonData.getString("manager");
                            type=jsonData.getString("type");
                            myname=jsonData.getString("name");
                            reason=jsonData.getString("reason");
                            String start_d=jsonData.getString("start_d");
                            hruselea=jsonData.getString("hruse");
                            String lea_id=jsonData.getString("lea_id");
                            String mylog=jsonData.getString("mylog");
                            String notes=jsonData.getString("notes");
                            //show.setText(start_d);
                            if(substitute.contains("0")&&substitute.equals(account))
                            {
                                if(notes.equals("1"))
                                {mylog+=fDate+name+"確認"+"\n";
                                    dbleaup.executeQuery(emp_id,name,null,lea_id,"2",mylog);
                                    check=true;
                                }
                                else if(notes.equals("")){
                                    mylog+=fDate+name+"確認"+"\n";
                                    dbleaup.executeQuery(emp_id,name,null,lea_id,"0",mylog); //update簽核
                                    check=true;
                                }
                                else if(notes.equals("1")){
                                    mylog+=fDate+name+"取消請假"+"\n";
                                    dbleaup.executeQuery(emp_id,name,null,lea_id,"",mylog); //update簽核
                                    check=true;
                                }
                            }
                            else if(!substitute.contains("0")&&manager.equals(account))
                            {
                                if(notes.equals("1"))
                                {mylog+=fDate+name+"簽核"+"\n";
                                    dbleaup.executeQuery(emp_id,null,name,lea_id,"2",mylog);
                                    check=true;
                                }
                                else if(notes.equals("0")){
                                    mylog+=fDate+name+"簽核"+"\n";
                                    dbleaup.executeQuery(emp_id,null,name,lea_id,"3",mylog); //update簽核
                                    check=true;
                                }

                                String ans= dbemp.executeQuery(emp_id);//db emp 員工資料表
                                JSONArray jsonArray2 = new JSONArray(ans);
                                for(int k = 0; k < jsonArray2.length(); k++)
                                {//db emp 員工資料表
                                    JSONObject jsonData2 = jsonArray2.getJSONObject(k);

                                    String hruse=jsonData2.getString("hruse");
                                    String myhr=jsonData2.getString("hr");
                                    //String hrremain=jsonData2.getString("hrremain");
                                    double a=Double.valueOf(hruselea).doubleValue()+Double.valueOf(hruse).doubleValue();
                                    String b=a+"";
                                    double c=Double.valueOf(hruse).doubleValue()-Double.valueOf(hruselea).doubleValue();
                                    String d=c+"";

                                    double e=Double.valueOf(myhr).doubleValue()-a;
                                    String f=e+"";
                                    double g=Double.valueOf(myhr).doubleValue()-c;
                                    String h=g+"";
                                    show.setText("");
                                    // show.setText(hruselea+"+"+hruse+"/"+b+" "+emp_id);
                                    if(type.equals("特休")){
                                        if(notes.equals("1"))
                                        {
                                            dbempup.executeQuery(emp_id,d,h);//db update
                                        }
                                        else{

                                            dbempup.executeQuery(emp_id,b,f);//db update
                                        }

                                    }
                                    //////////////
                                    String mycomp=jsonData2.getString("comp");
                                    double com=Double.valueOf(mycomp).doubleValue()-Double.valueOf(hruselea).doubleValue();
                                    // String comp=com+"";
                                    double com2=Double.valueOf(mycomp).doubleValue()+Double.valueOf(hruselea).doubleValue();
                                    //String f=com2+"";
                                    if(type.equals("補休")){
                                        if(notes.equals("1"))
                                        {
                                            dbempcom.executeQuery(emp_id,com+"");
                                            //dbempup.executeQuery(emp_id,f);//db update
                                        }
                                        else{dbempcom.executeQuery(emp_id,com2+"");
                                            // dbempup.executeQuery(emp_id,e);//db update
                                        }

                                    }

                                    ////////////

                                }
                            }
                        }
                            if(check && i == jsonArray.length()-1){
                                mydialog();
                                check=false;
                            }
                        }


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
        context = Waitsigntest.this;
        dia = new Dialog(context, R.style.edit_AlertDialog_style);
        dia.setContentView(R.layout.imgshow);
        ImageView imageView = (ImageView) dia.findViewById(R.id.start_img);
        imageView.setImageResource(R.drawable.cs_signshow);
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
        Intent intent = new Intent(Waitsigntest.this, Waitsigntest.class);
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
                    Intent intent = new Intent(Waitsigntest.this, Mymenu.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    /**/
    @Override
    public void onBackPressed() {
        super.onBackPressed();  // Always call the superclass method first
        Intent intent =new Intent();
        intent.setClass(Waitsigntest.this, Mymenu.class);
        startActivity(intent);
    }


}
