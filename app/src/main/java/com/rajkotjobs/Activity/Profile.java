package com.rajkotjobs.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.rajkotjobs.Adapter.exp_Adapter;
import com.rajkotjobs.Global;
import com.rajkotjobs.Model.exp_info;
import com.rajkotjobs.R;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    TextView pic_Edit;
    TextInputLayout input_name, input_email, input_phone, input_address, input_clg, input_degree, input_field_std, input_std_city;
    EditText et_name, et_emial, et_phone, et_address, et_clg, et_degree, et_field_std, et_std_city;
    RadioButton rdo_fresher, rdo_experienced;
    LinearLayout.LayoutParams params;
    Button btn_Addmore;
    RecyclerView recy_exp;
    exp_Adapter adapter;
    ArrayList<exp_info> explist;
    LinearLayout ll_expextra;
    CircleImageView civ_user_profile;
    Bitmap photo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        civ_user_profile = (CircleImageView) findViewById(R.id.civ_user_profile);

        input_name = (TextInputLayout) findViewById(R.id.input_name);
        input_email = (TextInputLayout) findViewById(R.id.input_email);
        input_phone = (TextInputLayout) findViewById(R.id.input_phone);
        input_address = (TextInputLayout) findViewById(R.id.input_address);
        input_clg = (TextInputLayout) findViewById(R.id.input_clg);
        input_degree = (TextInputLayout) findViewById(R.id.input_degree);
        input_field_std = (TextInputLayout) findViewById(R.id.input_field_std);
        input_std_city = (TextInputLayout) findViewById(R.id.input_std_city);
        ll_expextra = (LinearLayout) findViewById(R.id.ll_expextra);
        et_name = (EditText) findViewById(R.id.et_name);
        et_emial = (EditText) findViewById(R.id.et_emial);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_address = (EditText) findViewById(R.id.et_address);
        et_clg = (EditText) findViewById(R.id.et_clg);
        et_degree = (EditText) findViewById(R.id.et_degree);
        et_field_std = (EditText) findViewById(R.id.et_field_std);
        et_std_city = (EditText) findViewById(R.id.et_std_city);
        rdo_fresher = (RadioButton) findViewById(R.id.rdo_fresher);
        rdo_experienced = (RadioButton) findViewById(R.id.rdo_experienced);
        recy_exp = (RecyclerView) findViewById(R.id.recy_exp);
        explist = new ArrayList<>();

        recy_exp.setNestedScrollingEnabled(false);
        adapter = new exp_Adapter(getApplicationContext(), explist);
        recy_exp.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recy_exp.setAdapter(adapter);
        //onclick
        civ_user_profile.setOnClickListener(this);
        btn_Addmore = (Button) findViewById(R.id.btn_Addmore);

        rdo_fresher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    ll_expextra.setVisibility(View.GONE);
                }
            }
        });
        rdo_experienced.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    ll_expextra.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_Addmore.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this, R.style.MyAlertDialogStyle);
                final View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.exp_item_dialog, null);
                builder.setView(v);
                final EditText et_title = (EditText) v.findViewById(R.id.et_title);
                final EditText et_cmp = (EditText) v.findViewById(R.id.et_cmp);
                final EditText et_city = (EditText) v.findViewById(R.id.et_city);
                //
                final Spinner sp_fmonth = (Spinner) v.findViewById(R.id.sp_fmonth);
                final Spinner sp_fyear = (Spinner) v.findViewById(R.id.sp_fyear);


                final Spinner sp_tmonth = (Spinner) v.findViewById(R.id.sp_tmonth);
                final Spinner sp_tyear = (Spinner) v.findViewById(R.id.sp_tyear);
                ///


                Global.set();
                ArrayAdapter<String> years = new ArrayAdapter<String>(Profile.this, R.layout.sp_single, Global.year);
                ArrayAdapter<String> month = new ArrayAdapter<String>(Profile.this, R.layout.sp_single, Global.month);
                Log.d("mon___",month.toString());

                sp_fmonth.setAdapter(month);
                sp_tmonth.setAdapter(month);
                sp_tyear.setAdapter(years);
                sp_fyear.setAdapter(years);

                final EditText et_jobdesc = (EditText) v.findViewById(R.id.et_jobdesc);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
                {
                    public void onClick(final DialogInterface dialogInterface, int i)
                    {
                        exp_info inf = new exp_info();
                        inf.setTitle(et_title.getText().toString());
                        inf.setCmp(et_cmp.getText().toString());
                        inf.setCity(et_city.getText().toString());
                        inf.setDesc(et_jobdesc.getText().toString());

                        inf.setFrom_month(sp_fmonth.getSelectedItemPosition());
                        inf.setTo_month(sp_tmonth.getSelectedItemPosition());
                        inf.setFrom_year(sp_fyear.getSelectedItemPosition());
                        inf.setTo_year(sp_tyear.getSelectedItemPosition());

                        explist.add(inf);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                });
                builder.setTitle("Add Work Experience");
                AlertDialog dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.dialoganim;
                dialog.show();
            }
        });
        //addv();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri uri1 = data.getData();
                    // imageview_photo1.setImageURI(uri1);
                    try {
                        photo = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        civ_user_profile.setImageBitmap(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // saveImage(photo);
                    break;
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.civ_user_profile:
                if (checkPermission())
                {
                    Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent1, 1);
                } else {
                    requestPermission();
                }
                break;
        }
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(Profile.this, new
                String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(Profile.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }
}