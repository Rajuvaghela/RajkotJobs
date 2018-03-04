package com.rajkotjobs.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rajkotjobs.Adapter.JobListAdapter;
import com.rajkotjobs.Global;
import com.rajkotjobs.Model.ListOfJobModel;
import com.rajkotjobs.R;
import com.rajkotjobs.util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class JobDescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog dialog;
    Button btn_apply_for_job, btn_save_job, btn_report_job;
    TextView tv_job_title, tv_company_name, tv_sallery, tv_job_des, tv_require_education, tv_require_exp, tv_days_of_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_description);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_apply_for_job = (Button) findViewById(R.id.btn_apply_for_job);
        btn_save_job = (Button) findViewById(R.id.btn_save_job);
        btn_report_job = (Button) findViewById(R.id.btn_report_job);
        btn_save_job.setOnClickListener(this);
        btn_apply_for_job.setOnClickListener(this);
        btn_report_job.setOnClickListener(this);

        tv_job_title = (TextView) findViewById(R.id.tv_job_title);
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        tv_sallery = (TextView) findViewById(R.id.tv_sallery);
        tv_job_des = (TextView) findViewById(R.id.tv_job_des);
        tv_require_education = (TextView) findViewById(R.id.tv_require_education);
        tv_require_exp = (TextView) findViewById(R.id.tv_require_exp);
        tv_days_of_post = (TextView) findViewById(R.id.tv_days_of_post);


        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        LoadOneJob();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void LoadOneJob() {
        if (Global.isNetworkAvailable(getApplicationContext())) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("action", "fetch_single_jobs");
            params.add("jobid", Global.pkjobid);

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

                            if (success.equals("true")) {

                                JSONArray resultsarray = jsonObj.getJSONArray("data");
                                for (int i = 0; i < resultsarray.length(); i++) {
                                    Log.e("i", "" + i);
                                    tv_job_title.setText(resultsarray.getJSONObject(i).getString("title"));
                                    tv_job_des.setText(resultsarray.getJSONObject(i).getString("description"));
                                    tv_require_exp.setText(resultsarray.getJSONObject(i).getString("requiredexp"));
                                    tv_company_name.setText(resultsarray.getJSONObject(i).getString("companyname"));
                                    tv_sallery.setText(resultsarray.getJSONObject(i).getString("salary"));
                                    // tv_require_education.setText(resultsarray.getJSONObject(i).getString("title"));
                                    tv_days_of_post.setText(resultsarray.getJSONObject(i).getString("createdat"));
                                    dialog.dismiss();
                                }


                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }

                    }

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void SaveOneJob() {
        if (Global.isNetworkAvailable(getApplicationContext())) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(800000);
            RequestParams params = new RequestParams();
            params.add("action", "save_jobs");
            params.add("fkuserid", "1");
            params.add("fkjobid", Global.pkjobid);

            client.post(Global.appurl, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != null) {

                        try {
                            Log.e("save_job", responseString);

                            JSONObject jsonObj = new JSONObject(responseString);
                            String success = jsonObj.getString("success");

                            if (success.equals("true")) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), R.string.job_save_successfully, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }

                    }

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_job:
                SaveOneJob();
                break;
            case R.id.btn_apply_for_job:
                break;
            case R.id.btn_report_job:
                break;

        }
    }
}
