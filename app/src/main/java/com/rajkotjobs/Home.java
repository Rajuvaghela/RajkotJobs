package com.rajkotjobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rajkotjobs.Activity.CompJobPostActivity;
import com.rajkotjobs.Activity.CompanyProfileFrgment;
import com.rajkotjobs.Activity.Profile;
import com.rajkotjobs.Adapter.SearchAdapter;
import com.rajkotjobs.Fragment.HomeFragment;
import com.rajkotjobs.Fragment.SavedJobFragment;
import com.rajkotjobs.Fragment.SearchJobFragment;
import com.rajkotjobs.Model.SearchModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {

    DrawerLayout drawer;
    LinearLayout user_menu, ll_viewjob, ll_savejob, ll_recent, ll_cmp_menu, ll_cmp_profile, ll_cmp_postjob, ll_cmp_find_resume, ll_user_home, ll_cmp_home;
    AutoCompleteTextView editText_search;
    ProgressDialog dialog;
    List<SearchModel> sersch_model_list = new ArrayList<>();
    SearchAdapter adapter;

    Set<SearchModel> hs = new HashSet<>();
    int pos;

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        editText_search = (AutoCompleteTextView) findViewById(R.id.editText_search);
        editText_search.setOnItemClickListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        LinearLayout ll_profile = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.ll_profile);
        user_menu = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.user_menu);
        ll_viewjob = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.ll_viewjob);
        ll_savejob = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.ll_savejob);
        ll_recent = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.ll_recent);
        ll_cmp_menu = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.ll_cmp_menu);
        ll_cmp_home = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.ll_cmp_home);
        ll_user_home = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.ll_user_home);
        ll_cmp_profile = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.ll_cmp_profile);
        ll_cmp_postjob = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.ll_cmp_postjob);
        ll_cmp_find_resume = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.ll_cmp_find_resume);


        ll_cmp_menu.setVisibility(View.GONE);
        ll_viewjob.setOnClickListener(this);
        ll_savejob.setOnClickListener(this);
        ll_recent.setOnClickListener(this);
        ll_user_home.setOnClickListener(this);

        ll_cmp_profile.setOnClickListener(this);
        ll_cmp_postjob.setOnClickListener(this);
        ll_cmp_find_resume.setOnClickListener(this);
        ll_cmp_home.setOnClickListener(this);

        ll_profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        //ll_cmp_menu.setVisibility(View.GONE);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);

        Global.arrayList_list_of_job.clear();
        HomeFragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_home, fragment);
        fragmentTransaction.commit();
        editText_search.setThreshold(1);
        SearchJob();
    }

    void SearchJob() {
        if (Global.isNetworkAvailable(getApplicationContext())) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(50000);
            RequestParams params = new RequestParams();
            params.add("action", "fetch_all_job_cat");


            client.post(Global.appurl, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        sersch_model_list.clear();
                        try {
                            Log.e("serch", responseString);
                            JSONObject jsonObj = new JSONObject(responseString);
                            String success = jsonObj.getString("success");
                            JSONArray resultsarray = jsonObj.getJSONArray("data");
                            if (success.equals("true")) {
                                for (int i = 0; i < resultsarray.length(); i++) {
                                    SearchModel temp = new SearchModel();
                                  /*  temp.setL_id(resultsarray.getJSONObject(i).getString("l_id"));
                                    temp.setCat_id(resultsarray.getJSONObject(i).getString("cat_id"));*/
                                    temp.setSearch(resultsarray.getJSONObject(i).getString("job_cat"));
                                    sersch_model_list.add(temp);
                                    Log.e("title", "" + resultsarray.getJSONObject(i).getString("job_cat"));
                                    dialog.dismiss();
                                }


                            }
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }


                    }
                    adapter = new SearchAdapter(getApplicationContext(), sersch_model_list);
                    editText_search.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void Tmsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //menu user
            case R.id.ll_user_home:
                setmenu_color();
                ll_user_home.setBackgroundColor(getResources().getColor(R.color.graylight));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_viewjob:
                Tmsg("viewjob");
                setmenu_color();
                ll_viewjob.setBackgroundColor(getResources().getColor(R.color.graylight));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_savejob:
                Tmsg("savejob");
                Global.arrayList_list_of_job.clear();
                SavedJobFragment fragment2 = new SavedJobFragment();
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.replace(R.id.framelayout_home, fragment2);
                fragmentTransaction2.addToBackStack(null);
                fragmentTransaction2.commit();

                setmenu_color();
                ll_savejob.setBackgroundColor(getResources().getColor(R.color.graylight));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_recent:
                Tmsg("recent");
                setmenu_color();
                ll_recent.setBackgroundColor(getResources().getColor(R.color.graylight));
                drawer.closeDrawer(GravityCompat.START);
                break;
            //menu cmp
            case R.id.ll_cmp_home:
                Global.arrayList_list_of_job.clear();
                HomeFragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout_home, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                setmenu_color();
                ll_cmp_home.setBackgroundColor(getResources().getColor(R.color.graylight));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_cmp_profile:
                Tmsg("cmp profile");
                setmenu_color();
                ll_cmp_profile.setBackgroundColor(getResources().getColor(R.color.graylight));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_cmp_postjob:
                CompanyProfileFrgment profileFrgment = new CompanyProfileFrgment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.framelayout_home, profileFrgment);
                transaction.commit();
                startActivity(new Intent(getApplicationContext(), CompJobPostActivity.class));
                setmenu_color();
                ll_cmp_postjob.setBackgroundColor(getResources().getColor(R.color.graylight));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_cmp_find_resume:
                Tmsg("resume");
                setmenu_color();
                ll_cmp_find_resume.setBackgroundColor(getResources().getColor(R.color.graylight));
                drawer.closeDrawer(GravityCompat.START);
                break;
            //

        }
    }

    void setmenu_color() {
        ll_viewjob.setBackgroundColor(getResources().getColor(R.color.white));
        ll_savejob.setBackgroundColor(getResources().getColor(R.color.white));
        ll_recent.setBackgroundColor(getResources().getColor(R.color.white));
        ll_cmp_profile.setBackgroundColor(getResources().getColor(R.color.white));
        ll_cmp_postjob.setBackgroundColor(getResources().getColor(R.color.white));
        ll_cmp_find_resume.setBackgroundColor(getResources().getColor(R.color.white));
        ll_cmp_home.setBackgroundColor(getResources().getColor(R.color.white));
        ll_user_home.setBackgroundColor(getResources().getColor(R.color.white));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selection = (String) parent.getItemAtPosition(position);
        for (int i = 0; i < sersch_model_list.size(); i++) {

            if (sersch_model_list.get(i).getSearch().equals(selection)) {
                pos = i;
                break;
            }
        }
        Log.e("position", String.valueOf(pos));
        Log.e("listid", sersch_model_list.get(pos).getSearch());

        Global.JobTitle = sersch_model_list.get(pos).getSearch();
        SearchJobFragment fragment3 = new SearchJobFragment();
        FragmentManager fragmentManager3 = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
        fragmentTransaction3.replace(R.id.framelayout_home, fragment3);
        fragmentTransaction3.addToBackStack(null);
        fragmentTransaction3.commit();
        editText_search.setText("");

    }

}
