package com.rajkotjobs.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rajkotjobs.R;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class CompanyProfileFrgment extends Fragment implements View.OnClickListener {
    EditText et_company_name, et_company_emial, et_phone, et_address, et_area_code;
    Button btn_company_next;
    CircleImageView civ_company_profile;
    Bitmap photo;
    Spinner spinner_types_of_company;
    ArrayList<String> array_company_list = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_company_profile_frgment, container, false);
        et_company_name = (EditText) v.findViewById(R.id.et_company_name);
        et_company_emial = (EditText) v.findViewById(R.id.et_company_emial);
        et_phone = (EditText) v.findViewById(R.id.et_phone);
        et_address = (EditText) v.findViewById(R.id.et_address);
        et_area_code = (EditText) v.findViewById(R.id.et_area_code);
        btn_company_next = (Button) v.findViewById(R.id.btn_company_next);

        civ_company_profile = (CircleImageView) v.findViewById(R.id.civ_company_profile);

        civ_company_profile.setOnClickListener(this);
        btn_company_next.setOnClickListener(this);
        return v;
    }
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


        }
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new
                String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
     {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri uri1 = data.getData();
                    // imageview_photo1.setImageURI(uri1);
                    try {
                        photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        civ_company_profile.setImageBitmap(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("err",e.getMessage());
                    }
                    // saveImage(photo);
                    break;
            }
        }

    }

    // TODO: Rename method, update argument and hook method into UI event

}
