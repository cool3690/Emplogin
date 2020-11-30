package com.cs.control;

/**
 * Created by kstanoev on 1/14/2015.
 */
public class Drawkeka {


    private String show,person,sub,manager ;
    int statedot,stateline,statedot2,stateline2,statedot3;

    public Drawkeka(String show, String person,String sub,String manager,int statedot,int stateline,int statedot2,int stateline2,int statedot3)
    {
        this.setShow(show);
        this.setPerson(person);
        this.setSub(sub);
        this.setManager(manager);
        this.setStatedot(statedot);
        this.setStateline(stateline);
        this.setStatedot2(statedot2);
        this.setStateline2(stateline2);
        this.setStatedot3(statedot3);
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public int getStatedot() {
        return statedot;
    }

    public void setStatedot(int statedot) {
        this.statedot = statedot;
    }

    public int getStateline() {
        return stateline;
    }

    public void setStateline(int stateline) {
        this.stateline = stateline;
    }

    public int getStatedot2() {
        return statedot2;
    }

    public void setStatedot2(int statedot2) {
        this.statedot2 = statedot2;
    }

    public int getStateline2() {
        return stateline2;
    }

    public void setStateline2(int stateline2) {
        this.stateline2 = stateline2;
    }

    public int getStatedot3() {
        return statedot3;
    }

    public void setStatedot3(int statedot3) {
        this.statedot3 = statedot3;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

}
