package com.example.parthkaweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    int timeout=4000;

    LottieAnimationView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        icon=findViewById(R.id.launch_icon);
        icon.setAnimationFromUrl("https://assets2.lottiefiles.com/packages/lf20_cHA3rG.json");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,HomePage.class);
                startActivity(intent);
            }
        },timeout);
    }
}