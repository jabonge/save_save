package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class NsaveDetail extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ImageView storysave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsave_detail);
        storysave = findViewById(R.id.storysave);
        pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();
        if(pref.getBoolean("detailsave",false)==true){
            storysave.setImageDrawable(getResources().getDrawable(R.drawable.btn_thisstoryunsave, getApplicationContext().getTheme()));
        }else{
            storysave.setImageDrawable(getResources().getDrawable(R.drawable.btn_thisstorysave, getApplicationContext().getTheme()));
        }
        storysave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getBoolean("detailsave",false)==false){
                    storysave.setImageDrawable(getResources().getDrawable(R.drawable.btn_thisstoryunsave, getApplicationContext().getTheme()));
                    editor.putBoolean("detailsave", true);
                    editor.commit();
                }else{
                    storysave.setImageDrawable(getResources().getDrawable(R.drawable.btn_thisstorysave, getApplicationContext().getTheme()));
                    editor.putBoolean("detailsave", false);
                    editor.commit();
                }
            }
        });

    }
}
