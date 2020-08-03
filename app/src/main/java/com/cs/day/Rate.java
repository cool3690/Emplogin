package com.cs.day;

/**
 * Created by kstanoev on 1/14/2015.
 */
public class Rate {
    private String dayoff, start_date, end_date;
    private boolean selected;
    public Rate(String dayoff, String start_date, String end_date)
    {
        this.setDayoff(dayoff);
        this.setEnd_date(end_date);
        this.setStart_date(start_date);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getDayoff() {
        return dayoff;
    }

    public void setDayoff(String dayoff) {
        this.dayoff = dayoff;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
