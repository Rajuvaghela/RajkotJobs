package com.rajkotjobs.Model;

/**
 * Created by raju on 11-10-2017.
 */

public class ListOfJobModel {
    String job_title;
    String job_des;

    public String getPkjobid() {
        return pkjobid;
    }

    public void setPkjobid(String pkjobid) {
        this.pkjobid = pkjobid;
    }

    String pkjobid;

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_des() {
        return job_des;
    }

    public void setJob_des(String job_des) {
        this.job_des = job_des;
    }

    public String getJob_sellery() {
        return job_sellery;
    }

    public void setJob_sellery(String job_sellery) {
        this.job_sellery = job_sellery;
    }

    public String getJob_post_days() {
        return job_post_days;
    }

    public void setJob_post_days(String job_post_days) {
        this.job_post_days = job_post_days;
    }

    String job_sellery;
    String job_post_days;

}
