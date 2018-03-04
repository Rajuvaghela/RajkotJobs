package com.rajkotjobs.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rajkotjobs.Activity.JobDescriptionActivity;
import com.rajkotjobs.Adapter.JobListAdapter;
import com.rajkotjobs.Global;
import com.rajkotjobs.Model.ListOfJobModel;
import com.rajkotjobs.R;
import com.rajkotjobs.util.EndlessRecyclerOnScrollListener;
import com.rajkotjobs.util.LinearLayoutManagerWithSmoothScroller;
import com.rajkotjobs.util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    RecyclerView recyclerView_job_list;
    RecyclerView.Adapter listAdapter;
    LinearLayoutManagerWithSmoothScroller layoutManager;
    RecyclerView.LayoutManager layoutManager2;
    ProgressDialog dialog;
    EndlessRecyclerOnScrollListener listener;
    boolean flagrefreshchk = false;

    SwipeRefreshLayout swipe_refresh_home;
    public static int offset = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView_job_list = (RecyclerView) view.findViewById(R.id.recyclerView_job_list);

        swipe_refresh_home = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_home);
        swipe_refresh_home.setOnRefreshListener(this);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        layoutManager = new LinearLayoutManagerWithSmoothScroller(getContext());
        listener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                if (Global.arrayList_list_of_job.size() >= 1000) {
                    dialog.show();
                    LoadAllJobList(current_page);
                    offset = current_page;
                }

            }
        };
        recyclerView_job_list.setLayoutManager(layoutManager);
        recyclerView_job_list.setHasFixedSize(true);
        recyclerView_job_list.addOnScrollListener(listener);

        LoadAllJobList(1);


        return view;
    }



    /*@Override
    public void onResume() {
        super.onResume();
        if (!Global.checkeventclcik) {
            if (Global.arrayList_list_of_job == null || Global.arrayList_list_of_job.size() == 0) {
                dialog.show();
                LoadAllJobList(1);

            } else {
                Global.checkeventclcik = true;
                listAdapter.notifyDataSetChanged();
            }

        } else {

            LoadAllJobList(1);

        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public void LoadAllJobList(final int page) {
        if (Global.isNetworkAvailable(getContext())) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("action", "fetch_all_jobs_pagging");
            params.add("pageno", String.valueOf(page));

            client.post(Global.appurl, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {
                        try {
                            Log.e("rv_response", responseString);

                            JSONObject jsonObj = new JSONObject(responseString);
                            String success = jsonObj.getString("success");
                            if (success.equals("false")) {
                                if (page == 1) {
                                    if (!flagrefreshchk) {
                                        Global.arrayList_list_of_job.clear();
                                    }
                                }
                                dialog.dismiss();
                            }
                            if (success.equals("true")) {
                                if (page == 1) {
                                    if (!flagrefreshchk) {

                                        Global.arrayList_list_of_job.clear();
                                    }

                                }
                                JSONArray resultsarray = jsonObj.getJSONArray("data");
                                for (int i = 0; i < resultsarray.length(); i++) {
                                    Log.e("i", "" + i);

                                    ListOfJobModel temp = new ListOfJobModel();
                                    temp.setPkjobid(resultsarray.getJSONObject(i).getString("pkjobid"));
                                    temp.setJob_title(resultsarray.getJSONObject(i).getString("title"));
                                    temp.setJob_des(resultsarray.getJSONObject(i).getString("companyname"));
                                    temp.setJob_sellery(resultsarray.getJSONObject(i).getString("salary"));
                                    temp.setJob_post_days(resultsarray.getJSONObject(i).getString("createdat"));

                                    Global.arrayList_list_of_job.add(temp);
                                    dialog.dismiss();
                                }
                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        if (Global.arrayList_list_of_job.size() > 1000) {

                            listAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {

                            //listAdapter = new HomeAdapter(getActivity(),listhome);
                            listAdapter = new JobListAdapter(getActivity(), Global.arrayList_list_of_job);
                            recyclerView_job_list.setAdapter(listAdapter);
                            recyclerView_job_list.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Global.pkjobid = Global.arrayList_list_of_job.get(position).getPkjobid().toString();
                                    startActivity(new Intent(getContext(), JobDescriptionActivity.class));
                                }
                            }));
                            dialog.dismiss();
                            swipe_refresh_home.setRefreshing(false);
                        }

                    }

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        if (Global.isNetworkAvailable(getContext())) {
            listener.reset(0, true);
            LoadAllJobList(1);
        } else {
            swipe_refresh_home.setRefreshing(false);
        }
    }
}
