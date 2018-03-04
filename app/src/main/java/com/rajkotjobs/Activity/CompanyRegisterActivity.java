package com.rajkotjobs.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rajkotjobs.Global;
import com.rajkotjobs.R;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CompanyRegisterActivity extends AppCompatActivity implements View.OnClickListener {


    TextView tv_login;
    Button btn_new;
    TextInputLayout input_cpwd, input_pwd, input_phone, input_email, input_name, input_address;
    EditText et_cpwd, et_pwd, et_phone, et_emial, et_name, et_address;
    RelativeLayout rl;
    LinearLayout ll_already_acount;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);
        getSupportActionBar().hide();


        tv_login = (TextView) findViewById(R.id.tv_login);
        btn_new = (Button) findViewById(R.id.btn_new);
        rl = (RelativeLayout) findViewById(R.id.rl);
        ll_already_acount = (LinearLayout) findViewById(R.id.ll_already_acount);
        ll_already_acount.setOnClickListener(this);
        input_cpwd = (TextInputLayout) findViewById(R.id.input_cpwd);
        input_pwd = (TextInputLayout) findViewById(R.id.input_pwd);
        input_phone = (TextInputLayout) findViewById(R.id.input_phone);
        input_email = (TextInputLayout) findViewById(R.id.input_email);
        input_name = (TextInputLayout) findViewById(R.id.input_name);
        input_address = (TextInputLayout) findViewById(R.id.input_address);

        btn_new.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_emial = (EditText) findViewById(R.id.et_emial);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_cpwd = (EditText) findViewById(R.id.et_cpwd);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_address = (EditText) findViewById(R.id.et_address);
        et_name.requestFocus();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_already_acount:
                finish();
                break;
            case R.id.btn_new:
                if (isvelid()) {
                    companyregister();
                }
                break;
        }

    }

    void companyregister() {
        if (Global.isNetworkAvailable(getApplicationContext())) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.add("action", "companyregister");
            params.add("name", et_name.getText().toString());
            params.add("email", et_emial.getText().toString());
            params.add("password", et_pwd.getText().toString());
            params.add("contact", et_phone.getText().toString());
            params.add("address", et_address.getText().toString());
            client.post(Global.appurl, params, new TextHttpResponseHandler() {
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    msg(responseString);
                    dialog.dismiss();
                }

                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    try {
                        JSONObject obj = new JSONObject(responseString);
                        String res = obj.getString("success");
                        String msg = obj.getString("message");
                        if (res.equals("true")) {
                            String pkuserid = obj.getString("pkuserid");
                            JSONObject info = obj.getJSONObject("userdata");
                            String uname = info.getString("name");
                            String contact = info.getString("contact");
                            String email = info.getString("email");

                            getSharedPreferences("data", MODE_PRIVATE).edit().putBoolean("islogin", true).apply();
                            getSharedPreferences("data", MODE_PRIVATE).edit().putString("pkuserid", pkuserid).apply();
                            getSharedPreferences("data", MODE_PRIVATE).edit().putString("uname", uname).apply();
                            getSharedPreferences("data", MODE_PRIVATE).edit().putString("mobile", contact).apply();
                            getSharedPreferences("data", MODE_PRIVATE).edit().putString("email", email).apply();
                            dialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), Login.class).putExtra("with", "Componey"));
                        } else {
                            msg(msg);
                            dialog.dismiss();
                        }

                    } catch (Exception e) {
                    }
                }
            });
        } else {
            msg("No Internet connection");
        }
    }

    boolean isvelid() {
        if (et_name.getText().toString().equals("")) {
            msg("Enter Company Name");
            input_name.requestFocus();
            return false;
        } else if (et_emial.getText().toString().isEmpty()) {
            msg("Enter Email");
            et_emial.requestFocus();
            return false;
        } else if (!isValidEmail(et_emial.getText().toString())) {
            msg("Invelid Email");
            et_emial.requestFocus();
            return false;
        } else if (et_phone.getText().toString().isEmpty()) {
            msg("Enter Mobile Number");
            et_phone.requestFocus();
            input_email.setError("");
            return false;
        } else if (et_phone.getText().toString().length() <= 9) {
            msg("Enter 10 digits");
            return false;
        } else if (et_pwd.getText().toString().isEmpty()) {
            msg("Enter password");
            et_pwd.requestFocus();
            input_phone.setError("");
            return false;
        } else if (et_cpwd.getText().toString().isEmpty()) {
            input_pwd.setError("");
            msg("Enter Confirm Password");
            et_cpwd.requestFocus();
        } else if (!et_pwd.getText().toString().equals(et_cpwd.getText().toString())) {
            msg("Both Password does not mathc");
            return false;
        } else if (et_address.getText().toString().isEmpty()) {
            msg("Enter Address");
            input_address.requestFocus();
            input_cpwd.setError("");
            return false;
        } else {
            input_address.setError("");
            btn_new.requestFocus();
            return true;
        }
        return true;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    void msg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
