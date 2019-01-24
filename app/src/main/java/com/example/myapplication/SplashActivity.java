package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try{
            Thread.sleep(1000);

       }catch (InterruptedException e){
            Log.d("Tag",e.getMessage());
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}
