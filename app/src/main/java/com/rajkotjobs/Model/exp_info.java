package com.rajkotjobs.Model;

/**
 * Created by hp on 10/3/2017.
 */

public class exp_info {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    String title,cmp,city,desc;



    int from_month;
    int to_month;
    int from_year;

    public int getFrom_month() {
        return from_month;
    }

    public void setFrom_month(int from_month) {
        this.from_month = from_month;
    }

    public int getTo_month() {
        return to_month;
    }

    public void setTo_month(int to_month) {
        this.to_month = to_month;
    }

    public int getFrom_year() {
        return from_year;
    }

    public void setFrom_year(int from_year) {
        this.from_year = from_year;
    }

    public int getTo_year() {
        return to_year;
    }

    public void setTo_year(int to_year) {
        this.to_year = to_year;
    }

    int to_year;
}
