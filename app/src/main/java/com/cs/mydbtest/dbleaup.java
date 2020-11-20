package com.cs.mydbtest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class dbleaup {
public static String executeQuery(String account,String substitute,
		String manager,String lea_id,String notes,String mylog)
    { 
    	String result ="";
    	 
		
    	    try {
    	HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("https://demo.chansing.com.tw/off2/leaupapp.php");
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("emp_id", account));
        params.add(new BasicNameValuePair("substitute", substitute));
       params.add(new BasicNameValuePair("manager", manager));
       //params.add(new BasicNameValuePair("start_d", start_d));
       params.add(new BasicNameValuePair("lea_id", lea_id));
       params.add(new BasicNameValuePair("notes", notes));
       params.add(new BasicNameValuePair("mylog", mylog));
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

