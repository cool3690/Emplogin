package com.cs.day;

import android.widget.ImageView;

/**
 * Created by kstanoev on 1/14/2015.
 */
public class btKeka {

    private String title,content,depart,date ;
    int btn;

    public btKeka(String title,String content,String depart,String date,int btn )
    {
        this.setTitle(title);

        this.setContent(content);
        this.setDepart(depart);
        this.setDate(date);
        this.setBtn(btn);




    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBtn() {
        return btn;
    }

    public void setBtn(int btn) {
        this.btn = btn;
    }


}
