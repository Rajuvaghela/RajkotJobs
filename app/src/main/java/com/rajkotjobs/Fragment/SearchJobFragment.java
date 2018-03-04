package com.rajkotjobs.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rajkotjobs.Activity.JobDescriptionActivity;
import com.rajkotjobs.Adapter.JobListAdapter;
import com.rajkotjobs.Global;
import com.rajkotjobs.Model.ListOfJobModel;
import com.rajkotjobs.R;
import com.rajkotjobs.util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class SearchJobFragment extends Fragment {


    RecyclerView recyclerView_job_list;
    RecyclerView.Adapter listAdapter;
    RecyclerView.LayoutManager layoutManager2;

    ProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_job, container, false);

        recyclerView_job_list = (RecyclerView) view.findViewById(R.id.recyclerView_job_list);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);

        Log.e("above", "fun");
        SearchJobList();
        Log.e("below", "fun");
        return view;
    }


    void SearchJobList() {
        Log.e("in", "fun");
        if (Global.isNetworkAvailable(getContext())) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("action", "searchJobs");
            params.add("search", Global.JobTitle);

            client.post(Global.appurl, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("serch_fail" +
                            "", responseString);
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.e("serch_list", responseString);
                    if (responseString != null) {
                        Global.arrayList_list_of_job.clear();
                        try {


                            JSONObject jsonObj = new JSONObject(responseString);
                            String success = jsonObj.getString("success");

                            if (success.equals("true")) {

                                JSONArray resultsarray = jsonObj.getJSONArray("data");
                                for (int i = 0; i < resultsarray.length(); i++) {
                                    Log.e("i", "" + i);

                                    ListOfJobModel temp = new ListOfJobModel();
                                    temp.setPkjobid(resultsarray.getJSONObject(i).getString("pkjobid"));
                                    temp.setJob_title(resultsarray.getJSONObject(i).getString("title"));
                                    temp.setJob_des(resultsarray.getJSONObject(i).getString("description"));
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

                        recyclerView_job_list.setHasFixedSize(true);
                        layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerView_job_list.setLayoutManager(layoutManager2);
                        listAdapter = new JobListAdapter(getActivity(), Global.arrayList_list_of_job);
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


                    }

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }



}
