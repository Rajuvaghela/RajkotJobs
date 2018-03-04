package com.rajkotjobs.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rajkotjobs.Global;
import com.rajkotjobs.Home;
import com.rajkotjobs.R;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class Login extends AppCompatActivity implements View.OnClickListener {
    ImageView ivback;
    LinearLayout ll_email, ll_pwd;
    Button btn_signin, btn_signup;
    TextView tv_forget, tv_skip, with;
    TextInputLayout input_pwd, input_email;
    LinearLayout ll_skip, ll_forgot_pass;
    String company_or_user;
    EditText et_email_id;
    EditText et_email;
    EditText et_pwd;
    ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        getSupportActionBar().hide();

        et_email = (EditText) findViewById(R.id.et_email);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_email.requestFocus();
        ivback = (ImageView) findViewById(R.id.ivback);
        ll_email = (LinearLayout) findViewById(R.id.ll_email);
        ll_pwd = (LinearLayout) findViewById(R.id.ll_pwd);
        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        tv_forget = (TextView) findViewById(R.id.tv_forget);
        ll_skip = (LinearLayout) findViewById(R.id.ll_skip);
        ll_forgot_pass = (LinearLayout) findViewById(R.id.ll_forgot_pass);
        ll_forgot_pass.setOnClickListener(this);
        ll_skip.setOnClickListener(this);
        tv_skip = (TextView) findViewById(R.id.tv_skip);
        String udata = "skip";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        tv_skip.setText(content);

        input_pwd = (TextInputLayout) findViewById(R.id.input_pwd);
        input_email = (TextInputLayout) findViewById(R.id.input_email);
        with = (TextView) findViewById(R.id.with);

        Glide.with(getApplicationContext())
                .load(Uri.parse("file:///android_asset/typing.gif"))
                .into(ivback);

        company_or_user = getIntent().getStringExtra("with");
        with.setText(with.getText() + " " + company_or_user);
        if (company_or_user.equals("Componey")) {
            ll_skip.setVisibility(View.GONE);
        } else {
            ll_skip.setVisibility(View.VISIBLE);
        }
        btn_signup.setOnClickListener(this);
        btn_signin.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_skip:
                startActivity(new Intent(getApplicationContext(), Home.class));
                break;
            case R.id.btn_signup:
                if (company_or_user.equals("Componey")) {
                    startActivity(new Intent(getApplicationContext(), CompanyRegisterActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), UserRegisterActivity.class));
                }
                break;
            case R.id.btn_signin:
                if (isvelid()) {
                    login();
                }
                break;
            case R.id.ll_forgot_pass:
                LayoutInflater li = LayoutInflater.from(Login.this);
                View vi = li.inflate(R.layout.forgot_pass_edittext, null);

                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(Login.this);
                alertDialogBuilder.setView(vi);

                et_email_id = (EditText) vi.findViewById(R.id.et_email_id);

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {

                        ForgotPass(et_email_id.getText().toString());
                        dialog.dismiss();

                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                Button theButton1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                theButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                break;

        }
    }

    void ForgotPass(String string) {
        if (Global.isNetworkAvailable(getApplicationContext())) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.add("action", "user_forgetpass");
            params.add("email", string);

            client.post(Global.appurl, params, new TextHttpResponseHandler() {
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    msg(responseString);
                }

                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    try {
                        JSONObject obj = new JSONObject(responseString);
                        String res = obj.getString("success");
                        String msg = obj.getString("message");
                        if (res.equals("true")) {
                            dialog.dismiss();
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

    void login() {

        if (Global.isNetworkAvailable(getApplicationContext())) {
            dialog.show();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(200000);
            RequestParams params = new RequestParams();
            params.add("action", "login");
            params.add("email", et_email.getText().toString());
            params.add("password", et_pwd.getText().toString());
            client.post(Global.appurl, params, new TextHttpResponseHandler() {
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    msg(responseString);
                    dialog.dismiss();
                }

                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.e("res", "" + responseString);
                    if (responseString != null) {
                        Log.e("res", "" + responseString);
                        try {
                            JSONObject obj = new JSONObject(responseString);
                            String res = obj.getString("success");
                            String msg = obj.getString("message");
                            if (res.equals("true")) {

                                JSONObject info = obj.getJSONObject("userdata");
                                String uname = info.getString("name");
                                String contact = info.getString("contact");
                                String email = info.getString("email");
                                String pkuserid = info.getString("pkuserid");

                                getSharedPreferences("data", MODE_PRIVATE).edit().putBoolean("islogin", true).apply();
                                getSharedPreferences("data", MODE_PRIVATE).edit().putString("pkuserid", pkuserid).apply();
                                getSharedPreferences("data", MODE_PRIVATE).edit().putString("uname", uname).apply();
                                getSharedPreferences("data", MODE_PRIVATE).edit().putString("mobile", contact).apply();
                                getSharedPreferences("data", MODE_PRIVATE).edit().putString("email", email).apply();
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), Login.class).putExtra("with", "Job seeker"));
                            } else {
                                msg(msg);
                                dialog.dismiss();
                            }

                        } catch (Exception e) {
                            dialog.dismiss();
                        }
                    } else {

                        dialog.dismiss();
                    }

                }
            });
        } else {
            msg("No Internet connection");
        }
    }

    boolean isvelid() {
        if (et_email.getText().toString().isEmpty()) {
            msg("Enter Email");
            et_email.requestFocus();
            return false;
        } else if (!isValidEmail(et_email.getText().toString())) {
            msg("Invelid Email");
            et_email.requestFocus();
            return false;
        } else if (et_pwd.getText().toString().isEmpty()) {
            msg("Enter password");
            et_pwd.requestFocus();
            return false;
        } else if (et_pwd.getText().toString().length() <= 5) {
            msg("Enter Velid Password");
            btn_signin.requestFocus();
            return false;
        } else {
            return true;
        }
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
