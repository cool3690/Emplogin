package com.cs.day;

import android.os.Bundle;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cs.control.GlobalVariable;
import com.cs.mydb.dbcheck;
import com.cs.mydb.dbin;
import com.cs.mydb.dbleacom;

import org.json.JSONArray;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Off extends AppCompatActivity {

    private Spinner mychoice,sub;
    private EditText date1;
    private EditText date2;
    private EditText time1;
    private EditText time2;
    private EditText reason;
    private TextView ans;
    private TextView tt;
    private ImageView confirm,ret;
    private ProgressDialog dialog;
    String sub_n="",manager="";
    String[] mysub= new String[1];
    Dialog dia;
    Context context;
    //private Button back;
    String account,department,name,hr,sub2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.off);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("www");
        /*
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        account=bundle.getString("ACCOUNT");
        department=bundle.getString("DEPARTMENT");
        name=bundle.getString("NAME");
        hr=bundle.getString("HR");

         */
        GlobalVariable gv=(GlobalVariable)getApplicationContext();
        account=gv.getEmpacc();
        department=gv.getEmpdepartment();
        name=gv.getEmpname();
        hr=gv.getEmphr();
        ans=(TextView)findViewById(R.id.ans);
        mysub[0]="請選擇";



        // 取得介面元件
        tt=(TextView)findViewById(R.id.tt);
        time1=(EditText)findViewById(R.id.time1);
        time2=(EditText)findViewById(R.id.time2);
        date1=(EditText)findViewById(R.id.date1);
        date2=(EditText)findViewById(R.id.date2);
        reason=(EditText)findViewById(R.id.reason);

        sub=(Spinner)findViewById(R.id.sub);
        mychoice=(Spinner) findViewById(R.id.choice);
        confirm=(ImageView)findViewById(R.id.confirm);
        ret=(ImageView)findViewById(R.id.ret);
        //reason.setInputType(InputType.TYPE_NULL);
        confirm.setOnTouchListener(btnTranListener);
        ret.setOnTouchListener(retbtn);
        // ans.setText(mysub[0]);
        String s=department+" ," +name+" ,特休剩餘:"+ hr + " 小時\n" ;
        tt.setText(s);

        sub=(Spinner) findViewById(R.id.sub);
        startDownload();



        ///day off type
        ArrayAdapter<CharSequence> adapterBalls = ArrayAdapter.createFromResource(
                this, R.array.absent,android.R.layout.simple_spinner_item);
        adapterBalls.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        mychoice.setAdapter(adapterBalls);
        mychoice.setOnItemSelectedListener(spnPreferListener);

        date1.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    showDatePickerDialog();
                }
            }
        });

        date1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePickerDialog();
            }
        });

        date1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                v.onTouchEvent(motionEvent);
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                // showDatePickerDialog();
                return true;
            }
        });


        date2.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    showDatePickerDialog2();
                }

            }
        });
        /**/
        date2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePickerDialog2();
            }
        });

        /**/
        date2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                v.onTouchEvent(motionEvent);
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                // showDatePickerDialog();
                return true;
            }
        });

        //////
        time1.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    showtime();
                }
            }
        });

        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showtime();
            }
        });

        time2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    showtime2();
                }
            }
        });

        time2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showtime2();
            }
        });


    }
    private void startDownload() {

        new DownloadFileAsync().execute();
    }
    class DownloadFileAsync extends AsyncTask<String, String, String> {
        //  ProgressDialog dialog = new ProgressDialog(Listening.this);


        @Override
        protected void onPreExecute() {
            /*
            dia = new Dialog(Off.this, R.style.edit_AlertDialog_style);
            dia.setContentView(R.layout.imgshow);

            GifImageView imageView = (GifImageView) dia.findViewById(R.id.start_img);
            try {

                GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.loading);
                imageView.setImageDrawable(gifDrawable);
                dia.setCanceledOnTouchOutside(false); // Sets whether this dialog is
                Window w = dia.getWindow();
                WindowManager.LayoutParams lp = w.getAttributes();
                lp.x = 0;
                lp.y = 20;
                dia.show();
                dia.onWindowAttributesChanged(lp);

            } catch (Exception e) {}



             */
            dialog = new ProgressDialog(Off.this);
            dialog.setMessage("Loading...請稍後");
            dialog.show();


            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {


            new Off.DownloadFileAsync().cancel(true);
            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused)
        { search();

            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
    public void search(){
        try{

            String result = dbcheck.executeQuery(account);

            JSONArray jsonArray = new JSONArray(result);

            String[] mysub= new String[jsonArray.length()+1];
            mysub[0]="請選擇";
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                mysub[i+1]=jsonData.getString("sub_n");
                //ans.setText(jsonData.getString("sub_n"));
            }
            ArrayAdapter<String> adaptersub=new ArrayAdapter<String>
                    (this,android.R.layout.simple_spinner_item,mysub);

            // 設定Spinner顯示的格式
            adaptersub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // 設定Spinner的資料來源
            sub.setAdapter(adaptersub);

            // 設定 sub 元件 ItemSelected 事件的 listener 為  subListener
            sub.setOnItemSelectedListener(subListener);


        }
        catch(Exception e){}
        // 建立ArrayAdapter
    }
    private ImageView.OnTouchListener btnTranListener=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    confirm.setImageResource(R.drawable.cs_okh);
                    break;
                case  MotionEvent.ACTION_UP:
                    confirm.setImageResource(R.drawable.cs_ok);
                    if(TextUtils.isEmpty(date1.getText().toString())||TextUtils.isEmpty(date2.getText().toString())){
                        mytoast("請填請假日期");
                    }
                    if(TextUtils.isEmpty(time1.getText().toString())||TextUtils.isEmpty(time2.getText().toString())){
                        mytoast("請填請假時間");
                    }
                    else{
                        sub2=sub.getSelectedItem().toString();
                        String c=mychoice.getSelectedItem().toString();
                        String[] d1=date1.getText().toString().split("-");
                        String[] d2=date2.getText().toString().split("-");
                        String[] t1=time1.getText().toString().split(":");
                        String[] t2=time2.getText().toString().split(":");
                        double totaltime=0;
                        int count=0;//分
                        int co=0;//時
                        int myday=0;
                        int mymon=0,myyear=0;
                        int tmp1=Integer.parseInt(t1[1]),tmp2=Integer.parseInt(t2[1]);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar cal = Calendar.getInstance();
                        Calendar cal2 = Calendar.getInstance();
                        String td=date1.getText().toString();
                        String td2=date2.getText().toString();
                        try {
                            cal.setTime(format.parse(td));
                            cal2.setTime(format.parse(td2));
                        }
                        catch (ParseException e1) {e1.printStackTrace();}
                        int dayOfWeek1 = cal.get(Calendar.DAY_OF_WEEK);
                        int dayOfWeek2 = cal2.get(Calendar.DAY_OF_WEEK);
                        for(int y=0;y<1;y++){
                            try{ String tim=time1.getText().toString();

                                String result = dbleacom.executeQuery(account,td,tim);

                                JSONArray jsonArray = new JSONArray(result);
                                int a=jsonArray.length();
                                // ans.setText(result);
                                if(jsonArray.length()>0){
                                    String str="重複請假";
                                    mytoast(str);
                                    date1.setText("");
                                    date2.setText("");
                                    break;
                                }

                            }
                            catch(Exception e){}
                            if(dayOfWeek1==7|| dayOfWeek1==1){
                                String str="起始時間非上班時間";
                                mytoast(str);
                                date1.setText("");
                                break;
                            }
                            else if(dayOfWeek2==7|| dayOfWeek2==1){
                                String str="結束時間非上班時間";
                                mytoast(str);
                                date2.setText("");
                                break;
                            }
                            // tt.setText(dayOfWeek+"");
                            else if(Integer.parseInt(t1[0])<8 ||Integer.parseInt(t1[0])>17){
                                String str="起始時間非上班時間";
                                mytoast(str);
                                time1.setText("");
                                break;
                            }
                            else if(Integer.parseInt(t2[0])<8 ||Integer.parseInt(t2[0])>18)
                            {String str="結束時間非上班時間";
                                mytoast(str);
                                time2.setText("");
                                break;
                            }
                            else if(c.equals("請選擇假別"))
                            {  String str="請選擇假別。";
                                mytoast(str);

                                break;
                            }
                            else if(sub2.equals("請選擇"))
                            {  String str="請選擇代理人。";
                                mytoast(str);

                                break;
                            }
                            else if(Integer.parseInt(t2[0])>=Integer.parseInt(t1[0]))
                            {count=(Integer.parseInt(t2[0])-Integer.parseInt(t1[0]))*60;}
                            else
                            {
                                count=(Integer.parseInt(t2[0])-8+17-Integer.parseInt(t1[0]))*60;
                            }

                            if(tmp2==0){tmp2=60;count=count-60;}
                            count=count+tmp2-tmp1;
                            if(Integer.parseInt(t1[0])<12 &&Integer.parseInt(t2[0])>13){count=count-60;}
                            if(count>480){count=480;}

                            co=count/60;
                            count=count%60;

                            if(d1[0].equals(d2[0]) &&d1[1].equals(d2[1])&&d1[2].equals(d2[2])){
                                if(co==0 && count<30){
                                    String str="至少請30分鐘";
                                    mytoast(str);
                                    time2.setText("");
                                    break;
                                }
                                else{
                                    if(count>0 &&count<30){count=30;}
                                    if(count>31 &&count<60){co++;count=0;}
                                    ans.setText(co +"小時"+count+"分" );
                                    // mydialog();
                                }


                            }

                            else{
                                if(count>0 &&count<30){count=30;}
                                if(count>31 &&count<60){co++;count=0;}
                                if(d1[0].equals(d2[0]) &&d1[1].equals(d2[1]))
                                {
                                    myday=Integer.parseInt(d2[2])-Integer.parseInt(d1[2]);
                                    ans.setText(myday+"天 "+co +"小時"+count+"分");
                                    //mydialog();
                                }
                                else if(d1[0].equals(d2[0]))
                                {

                                    mymon=((Integer.parseInt(d2[1])-Integer.parseInt(d1[1]))*30);
                                    mymon+=Integer.parseInt(d2[2])-Integer.parseInt(d1[2]);
                                    for(int i=Integer.parseInt(d1[1]);i<Integer.parseInt(d2[1]);i++)
                                    {
                                        if(i==1 || i==3||i==5 || i==7||i==8||i==10||i==12)
                                            mymon++;
                                        if(i==2)mymon-=2;
                                    }
                                    myday=mymon%30;
                                    mymon=mymon/30;

                                    ans.setText(mymon+"月"+myday+"天 "+co +"小時"+count+"分");
                                    // mydialog();
                                }
                                else
                                {
                                    myyear=Integer.parseInt(d2[0])-Integer.parseInt(d1[0]);
                                    if(Integer.parseInt(d2[1])<Integer.parseInt(d1[1]))
                                    {
                                        mymon=12-Integer.parseInt(d1[1])+Integer.parseInt(d2[1]);
                                        myyear--;
                                        mymon=mymon*30;
                                        mymon+=Integer.parseInt(d2[2])-Integer.parseInt(d1[2]);
                                        for(int i=Integer.parseInt(d1[1]);i<Integer.parseInt(d2[1]);i++)
                                        {
                                            if(i==1 || i==3||i==5 || i==7||i==8||i==10||i==12)
                                                mymon++;
                                            if(i==2)mymon-=2;
                                        }
                                        myday=mymon%30;
                                        mymon=mymon/30;
                                        ans.setText(myyear+"年"+mymon+"月"+myday+"天 "+co +"小時"+count+"分");
                                        // mydialog();
                                    }
                                    else
                                    {
                                        mymon=Integer.parseInt(d2[1])+Integer.parseInt(d1[1]);
                                        mymon=mymon*30;
                                        mymon+=Integer.parseInt(d2[2])-Integer.parseInt(d1[2]);
                                        for(int i=Integer.parseInt(d1[1]);i<Integer.parseInt(d2[1]);i++)
                                        {
                                            if(i==1 || i==3||i==5 || i==7||i==8||i==10||i==12)
                                                mymon++;
                                            if(i==2)mymon-=2;
                                        }
                                        myday=mymon%30;
                                        mymon=mymon/30;
                                        ans.setText(myyear+"年"+mymon+"月"+myday+"天 "+co +"小時"+count+"分");
                                        //mydialog();
                                    }

                                }
                                if(c.equals("特休")&& Integer.parseInt(hr)<co+count/60.0+myday*8+mymon*30*8+myyear*365*8)
                                {  String str1="特休時數不夠。";
                                    //mytoast(str1);
                                    date2.setText("");
                                    time2.setText("");
                                    break;
                                }

                            }
                            try {

                                String start_d=date1.getText().toString();
                                String end_d=date2.getText().toString();
                                String start_t=time1.getText().toString();
                                String end_t=time2.getText().toString();
                                String rea=reason.getText().toString();
                                String substitute="";
                                String manager="";

                                String result =dbcheck.executeQuery(account);
                                JSONArray jsonArray = new JSONArray(result);
                                String str=sub.getSelectedItem().toString();
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonData = jsonArray.getJSONObject(i);
                                    if(str.equals(jsonData.getString("sub_n")))
                                    {
                                        substitute=jsonData.getString("substitute");
                                        manager=jsonData.getString("manager");
                                    }

                                }

                                totaltime=co+count/60.0+myday*8+mymon*30*8+myyear*365*8;
                                String t=Double.toString(totaltime);
                                //mytoast(hr+":"+totaltime+"");

                                dbin.executeQuery(account,name,department,c,start_d,start_t,end_d,end_t,rea,substitute,manager,t);
                                mydialog();

                            }
                            catch(Exception e) {}
                        }


                    }
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
        context = Off.this;
        dia = new Dialog(context, R.style.edit_AlertDialog_style);
        dia.setContentView(R.layout.imgshow);
        ImageView imageView = (ImageView) dia.findViewById(R.id.start_img);

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
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(Off.this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    private void showtime() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(Off.this, R.style.DatePickBackgroundColor2, new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String tmp="",tmp2="";
                if(hourOfDay<10){tmp="0"+hourOfDay;}else tmp=""+hourOfDay;
                if(minute<10){tmp2="0"+minute;}else tmp2=""+minute;
                time1.setText(tmp + ":" + tmp2);
            }

        },  c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false).show();
    }
    private void showtime2() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(Off.this, R.style.DatePickBackgroundColor2, new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String tmp="",tmp2="";
                if(hourOfDay<10){tmp="0"+hourOfDay;}else tmp=""+hourOfDay;
                if(minute<10){tmp2="0"+minute;}else tmp2=""+minute;
                time2.setText(tmp + ":" + tmp2);
            }
        },  c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false).show();
    }

    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(Off.this, R.style.DatePickBackgroundColor,new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                monthOfYear++;
                String tmp="",tmp2="";
                if(monthOfYear<10){tmp="0"+monthOfYear;}else tmp=""+monthOfYear;
                if(dayOfMonth<10){tmp2="0"+dayOfMonth;}else tmp2=""+dayOfMonth;
                date1.setText(year+"-"+(tmp)+"-"+tmp2);

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }


    private void showDatePickerDialog2() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(Off.this, R.style.DatePickBackgroundColor, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                monthOfYear++;
                String tmp="",tmp2="";
                if(monthOfYear<10){tmp="0"+monthOfYear;}else tmp=""+monthOfYear;
                if(dayOfMonth<10){tmp2="0"+dayOfMonth;}else tmp2=""+dayOfMonth;
                date2.setText(year+"-"+(tmp)+"-"+tmp2);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }
    private Spinner.OnItemSelectedListener subListener=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                    TextView textView = (TextView) v;
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    //String sel=parent.getSelectedItem().toString();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            };
    private ImageView.OnTouchListener retbtn=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    ret.setImageResource(R.drawable.cs_reth);
                    break;
                case  MotionEvent.ACTION_UP:
                    ret.setImageResource(R.drawable.cs_ret);
                    Intent intent = new Intent(Off.this, Empmenu.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    //  定義  onItemSelected 方法
    private Spinner.OnItemSelectedListener spnPreferListener=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                    TextView textView = (TextView) v;
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    ((TextView) parent.getChildAt(0)).setTextSize(20);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            };

    @Override
    public void onBackPressed() {
        super.onBackPressed();  // Always call the superclass method first

        Intent intent =new Intent();
        intent.setClass(Off.this,Empmenu.class);
        startActivity(intent);
    }

}
