package com.example.shoppershub.Ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.shoppershub.R;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final int SPLASH_DELAY = 2000; // 2000 = 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int user_id = sharedPreferences.getInt("user_id", 0);

                Intent iNext;
                if(user_id==0){ // for false (either First Time or user is logged out)
                    iNext = new Intent(SplashActivity.this, LoginActivity.class);
                }else { // for True (User is Logged in)
                    iNext = new Intent(SplashActivity.this, DashboardActivity.class);
                }
                startActivity(iNext);
                finish();
            }
        }, SPLASH_DELAY);
    }
}