package com.rajkotjobs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.rajkotjobs.Model.ListOfJobModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

/**
 * Created by hp on 10/4/2017.
 */

public class Global
{
    public static String appurl = "http://192.168.0.103/rajkotjob/checkauth.php";
    public static ArrayList<String> month = new ArrayList<>();
    public static ArrayList<String> year = new ArrayList<>();
    public static List<ListOfJobModel> arrayList_list_of_job=new ArrayList<>();
    public static boolean checkeventclcik=false;

    public static String pkjobid;
    public static String saved_job;
    public static boolean search_result=false;
    public  static String JobTitle;


    public static void set()
    {
        Global.month.clear();
        Global.month.add("Month");
        Global.month.add("January");
        Global.month.add("February");
        Global.month.add("March");
        Global.month.add("April");
        Global.month.add("May");
        Global.month.add("June");
        Global.month.add("July");
        Global.month.add("August");
        Global.month.add("September");
        Global.month.add("October");
        Global.month.add("November");
        Global.month.add("December");

        int year = Calendar.getInstance().get((Calendar.YEAR));
        Global.year.clear();
        Global.year.add("Year");
        for(int i= year ;i > year-25;i--)
        {
            Global.year.add(String.valueOf(i));
            Log.w("yyyy",year+"");
        }
    }
    public static boolean isNetworkAvailable(Context ct)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) ct.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
