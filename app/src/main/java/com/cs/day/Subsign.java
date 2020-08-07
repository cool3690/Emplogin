package com.cs.day;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Subsign extends AppCompatActivity {
    private ImageView okbtn,fin;
    private TextView show;
    private ListView prefer;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subsign);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 取得介面元件
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

        new Subsign.DownloadFileAsync().execute();
    }
    class DownloadFileAsync extends AsyncTask<String, String, String> {
        //  ProgressDialog dialog = new ProgressDialog(Listening.this);


        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Subsign.this);
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
            String result =dbleave2.executeQuery(account);
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                emp_id=jsonData.getString("emp_id");
                substitute=jsonData.getString("substitute");
                manager=jsonData.getString("manager");
                type=jsonData.getString("type");
                myname=jsonData.getString("name");
                reason=jsonData.getString("reason");
                serial_num=jsonData.getString("serial_num");
                hruselea=jsonData.getString("hruse");
                notes=jsonData.getString("notes");
                String start_d=jsonData.getString("start_d");
                String end_d=jsonData.getString("end_d");
                String start_t=jsonData.getString("start_t");
                String end_t=jsonData.getString("end_t");
                show.setText(notes);

                //jsonData.getString("start_t").substring(0, 5)+"~";
                if(substitute.contains("A")&&substitute.equals(account)
                        || !substitute.contains("A")&&manager.equals(account))
                {

                    if(notes.equals("1"))
                    {  list.add("\n"+serial_num+"\n"+"取消請假:"+myname+"/"+type+"/"+reason+"\n起"+start_d+"/"+start_t+"\n至"+end_d+"/"+end_t+"\n");
                        listShow.add(false);
                    }
                    else{
                        list.add("\n"+serial_num+"\n"+myname+"/"+type+"/"+reason+"\n起"+start_d+"/"+start_t+"\n至"+end_d+"/"+end_t+"\n");
                        listShow.add(false);
                    }
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
                        String result =dbleave2.executeQuery(account);//代理或主管有工號者顯示

                        JSONArray jsonArray = new JSONArray(result);
                        count=prefer.getCount();// for(int i=0;i<listShow.size();i++)


                        for(int i = 0; i < jsonArray.length(); i++)
                        {if (listShow.get(i)==true){//代理或主管有工號者顯示
                            JSONObject jsonData = jsonArray.getJSONObject(i);
                            emp_id=jsonData.getString("emp_id");
                            substitute=jsonData.getString("substitute");
                            manager=jsonData.getString("manager");
                            type=jsonData.getString("type");
                            myname=jsonData.getString("name");
                            reason=jsonData.getString("reason");
                            String start_d=jsonData.getString("start_d");
                            hruselea=jsonData.getString("hruse");
                            String lea_id=jsonData.getString("lea_id");
                            String notes=jsonData.getString("notes");
                            //show.setText(start_d);
                            if(substitute.contains("A")&&substitute.equals(account)
                                    ||substitute.contains("B")&&substitute.equals(account)
                                    ||substitute.contains("C")&&substitute.equals(account))
                            {
                                if(notes.equals("1"))
                                {
                                    dbleaup.executeQuery(emp_id,name,null,lea_id,"2");
                                }
                                else if(notes.equals("")){
                                    dbleaup.executeQuery(emp_id,name,null,lea_id,""); //update簽核
                                }
                            }
                            else if(!substitute.contains("A")&&manager.equals(account)
                                    ||!substitute.contains("B")&&manager.equals(account)
                                    ||!substitute.contains("C")&&manager.equals(account))
                            {
                                if(notes.equals("1"))
                                {
                                    dbleaup.executeQuery(emp_id,null,name,lea_id,"2");
                                }
                                else if(notes.equals("")){
                                    dbleaup.executeQuery(emp_id,null,name,lea_id,""); //update簽核
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
                                    show.setText(type+"");
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
                                        {dbempcom.executeQuery(emp_id,com+"");
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

                        }
                        mydialog();

                    }

                    catch(Exception e){}
                    refresh();
                    break;
            }
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
        context = Subsign.this;
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
        Intent intent = new Intent(Subsign.this, Check.class);
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
                    Intent intent = new Intent(Subsign.this, Empmenu.class);
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
        intent.setClass(Subsign.this,Empmenu.class);
        startActivity(intent);
    }

    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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
        if (id == R.id.video) {
            Intent intent= new Intent();
            intent.setClass(Subsign.this, MainActivity.class);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}