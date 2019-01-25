package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    LinearLayout dev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dev = findViewById(R.id.dev);
        try{
            Thread.sleep(1000);

       }catch (InterruptedException e){
            Log.d("Tag",e.getMessage());
        }
        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("dev",true);
                startActivity(intent);
                finish();
            }
        });
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}
