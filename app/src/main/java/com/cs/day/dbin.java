package com.cs.day;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;


public class dbin {

public static String executeQuery(String account,String name,String department,String c
		,String start_d,String start_t,String end_d,String end_t,String rea,
		String substitute,String manager,String remain)
    { 
    	String result ="";
    	 
		
    	    try {
    	HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("https://demo.chansing.com.tw/off/insertapp.php");
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
         
       
       params.add(new BasicNameValuePair("emp_id", account));
       
      
       params.add(new BasicNameValuePair("name", name));
       params.add(new BasicNameValuePair("department", department));
       params.add(new BasicNameValuePair("type", c));
       params.add(new BasicNameValuePair("start_d", start_d));
       params.add(new BasicNameValuePair("start_t", start_t));
       params.add(new BasicNameValuePair("end_d", end_d));
       params.add(new BasicNameValuePair("end_t", end_t));
       params.add(new BasicNameValuePair("reason", rea));
       params.add(new BasicNameValuePair("substitute", substitute));
       params.add(new BasicNameValuePair("manager", manager));
       params.add(new BasicNameValuePair("hruse", remain));
      /**/ 
       httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        //view_account.setText(httpResponse.getStatusLine().toString());
        HttpEntity httpEntity = httpResponse.getEntity();
        InputStream inputStream = httpEntity.getContent();
        
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
        StringBuilder builder = new StringBuilder();
        String line = null;
        while((line = bufReader.readLine()) != null) {
            builder.append(line + "\n");
        }
        inputStream.close();
        result = builder.toString();
    } 
    catch(Exception e) {}
    
    return result;
}
    
}

