package com.example.train;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class splashScreen extends AppCompatActivity {

    Handler H;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        H = new Handler();
        H.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent I = new Intent(splashScreen.this,MainActivity.class);
                startActivity(I);
                finish();
            }
        },2000);
    }
}