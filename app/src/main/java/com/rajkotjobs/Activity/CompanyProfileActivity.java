package com.rajkotjobs.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rajkotjobs.R;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompanyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_company_name, et_company_emial, et_phone, et_address, et_area_code;
    Button btn_company_next, btn_post_job;
    CircleImageView civ_company_profile;
    Bitmap photo;
    Spinner spinner_types_of_company;
    ArrayList<String> array_company_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetObject();
    }

    void GetObject() {
        array_company_list.add("Accounting");
        array_company_list.add("Advertising");
        array_company_list.add("Automobile");
        array_company_list.add("Banking");
        array_company_list.add("BPO");
        array_company_list.add("Constitution");
        array_company_list.add("Export import");
        array_company_list.add("Education");
        array_company_list.add("IT hardware and networking");
        array_company_list.add("IT software");
        array_company_list.add("Medical");
        array_company_list.add("Security");


        spinner_types_of_company = (Spinner) findViewById(R.id.spinner_types_of_company);
        spinner_types_of_company.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CompanyProfileActivity.this,
                R.layout.custom_spiner, array_company_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_types_of_company.setAdapter(dataAdapter);
        spinner_types_of_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("array_company_list", "" + array_company_list.get(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        et_company_name = (EditText) findViewById(R.id.et_company_name);
        et_company_emial = (EditText) findViewById(R.id.et_company_emial);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_address = (EditText) findViewById(R.id.et_address);
        et_area_code = (EditText) findViewById(R.id.et_area_code);
        btn_company_next = (Button) findViewById(R.id.btn_company_next);
        btn_post_job = (Button) findViewById(R.id.btn_post_job);
        civ_company_profile = (CircleImageView) findViewById(R.id.civ_company_profile);
        btn_post_job.setOnClickListener(this);
        civ_company_profile.setOnClickListener(this);
        btn_company_next.setOnClickListener(this);

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
            case R.id.btn_company_next:
                break;
            case R.id.civ_company_profile:
                if (checkPermission()) {
                    Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent1, 1);
                } else {
                    requestPermission();
                }
                break;

            case R.id.btn_post_job:
                startActivity(new Intent(getApplicationContext(), CompJobPostActivity.class));
                break;
        }
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(CompanyProfileActivity.this, new
                String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(CompanyProfileActivity.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri uri1 = data.getData();
                    // imageview_photo1.setImageURI(uri1);
                    try {
                        photo = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        civ_company_profile.setImageBitmap(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // saveImage(photo);
                    break;
            }
        }

    }
}
