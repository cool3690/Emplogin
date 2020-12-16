package com.cs.control;

/**
 * Created by kstanoev on 1/14/2015.
 */
public class Phonekeka {


    private String name,tel,extension ;

    public Phonekeka(String name, String tel, String extension)
    {
        this.setName(name);
        this.setTel(tel);
        this.setExtension(extension);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
