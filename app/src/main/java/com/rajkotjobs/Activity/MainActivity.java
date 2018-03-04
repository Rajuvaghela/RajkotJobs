package com.rajkotjobs.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.rajkotjobs.R;

public class MainActivity extends AppCompatActivity {

    ImageView ivback;
    Button btn_job, btn_cmp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ivback = (ImageView) findViewById(R.id.ivback);
        btn_job = (Button) findViewById(R.id.btn_job);
        btn_cmp = (Button) findViewById(R.id.btn_cmp);
        getSupportActionBar().hide();

        Typeface f1 = Typeface.createFromAsset(getAssets(), "font/opensans-regular.ttf");
        btn_cmp.setTypeface(f1);
        btn_job.setTypeface(f1);

        btn_cmp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class).putExtra("with", "Componey"));

            }
        });
        btn_job.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class).putExtra("with", "Job seeker"));

            }
        });

    }
}
