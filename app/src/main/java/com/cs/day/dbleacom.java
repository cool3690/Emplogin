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

public class dbleacom {
    public static String executeQuery(String emp_id,String start_d,String start_t) {
        String result = "";
       
        try { 
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("https://demo.chansing.com.tw/off/leacomapp.php");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("emp_id", emp_id));
            params.add(new BasicNameValuePair("start_d", start_d));
            params.add(new BasicNameValuePair("start_t", start_t));
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
        } catch(Exception e) {
            // Log.e("log_tag", e.toString());
        }
        
        return result;
    }
}
