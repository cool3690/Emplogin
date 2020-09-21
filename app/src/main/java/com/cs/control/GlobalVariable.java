package com.cs.control;
import android.app.Application;

public class GlobalVariable extends Application {
    private String Account;     //User 名稱
    private String Passwd;         //User 密碼
    private String names;
    private String vendor_id;
    private String Empacc,Emppwd,Empdepartment,Empname,Emphr;

    public String getEmppwd() {
        return Emppwd;
    }

    public void setEmppwd(String emppwd) {
        Emppwd = emppwd;
    }

    public String getEmpdepartment() {
        return Empdepartment;
    }

    public void setEmpdepartment(String empdepartment) {
        Empdepartment = empdepartment;
    }

    public String getEmpname() {
        return Empname;
    }

    public void setEmpname(String empname) {
        Empname = empname;
    }

    public String getEmphr() {
        return Emphr;
    }

    public void setEmphr(String emphr) {
        Emphr = emphr;
    }

    public String getEmpacc() {
        return Empacc;
    }

    public void setEmpacc(String empacc) {
        Empacc = empacc;
    }



    public String getVendor_id() {
        return vendor_id;
    }
    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }
    public String getAccount() {
        return Account;
    }
    public void setAccount(String account) {
        this.Account = account;
    }
    public String getPasswd() {
        return Passwd;
    }
    public void setPasswd(String passwd) {
        this.Passwd = passwd;
    }
    public String getNames() {
        return names;
    }
    public void setNames(String names) {
        this.names = names;
    }
}