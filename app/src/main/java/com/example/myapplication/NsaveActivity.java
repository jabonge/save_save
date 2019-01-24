package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class NsaveActivity extends AppCompatActivity {
    private ImageView nsave1;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsave);
        nsave1 = findViewById(R.id.nsave_detail1);
        pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();
        if(pref.getBoolean("detailsave",false)==true){
            nsave1.setImageDrawable(getResources().getDrawable(R.drawable.img_detail1_participate, getApplicationContext().getTheme()));
        }else{
            nsave1.setImageDrawable(getResources().getDrawable(R.drawable.btn_nsave_detail1, getApplicationContext().getTheme()));
        }

        nsave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NsaveActivity.this,NsaveDetail.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(pref.getBoolean("detailsave",false)==true){
            nsave1.setImageDrawable(getResources().getDrawable(R.drawable.img_detail1_participate, getApplicationContext().getTheme()));
        }else{
            nsave1.setImageDrawable(getResources().getDrawable(R.drawable.btn_nsave_detail1, getApplicationContext().getTheme()));
        }

    }
}
