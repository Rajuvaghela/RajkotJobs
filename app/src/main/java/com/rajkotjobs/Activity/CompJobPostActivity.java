package com.rajkotjobs.Activity;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.rajkotjobs.R;

import java.util.ArrayList;

public class CompJobPostActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout rl_first;
    Button btn_new;
    ArrayList<String> array_education_list = new ArrayList<>();
    ArrayList<String> array_experince_list = new ArrayList<>();
    Spinner spinner_education, spinner_experince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_job_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetObject();
    }

    void GetObject() {
        array_education_list.add("B.E.");
        array_education_list.add("Doctor");
        array_education_list.add("diploma");
        array_education_list.add("B.E.");
        array_education_list.add("Doctor");
        array_education_list.add("diploma");

        array_experince_list.add("0-6 month");
        array_experince_list.add("6-12 month");
        array_experince_list.add("1 Year");
        array_experince_list.add("2 Year");
        array_experince_list.add("3 Year");
        array_experince_list.add("4 Year");
        array_experince_list.add("More than 5 Year");


        spinner_education = (Spinner) findViewById(R.id.spinner_education);
        spinner_experince = (Spinner) findViewById(R.id.spinner_experince);

        spinner_education.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CompJobPostActivity.this,
                R.layout.custom_spiner, array_education_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_education.setAdapter(dataAdapter);
        spinner_education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("color", "" + array_education_list.get(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_experince.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> experinceAdapter = new ArrayAdapter<String>(CompJobPostActivity.this,
                R.layout.custom_spiner, array_experince_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_experince.setAdapter(experinceAdapter);
        spinner_experince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("color", "" + array_experince_list.get(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        rl_first = (RelativeLayout) findViewById(R.id.rl_first);
        btn_new = (Button) findViewById(R.id.btn_new);
        btn_new.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_new:
                rl_first.setVisibility(View.GONE);

                break;
        }
    }

}
