package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.MainModel;
import com.example.myapplication.Remote.APIClient;
import com.example.myapplication.Remote.APIInterface;

import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.electric) RelativeLayout electricView;
    @BindView(R.id.water) RelativeLayout waterView;
    @BindView(R.id.gas) RelativeLayout gasView;
    @BindView(R.id.donate) ImageView donateView;
    @BindView(R.id.name) TextView userName;
    @BindView(R.id.waterDday) TextView waterDday;
    @BindView(R.id.electDday) TextView electDday;
    @BindView(R.id.electGoalPrice) TextView electGoalPrice;
    @BindView(R.id.waterGoalPrice) TextView waterGoalPrice;
    @BindView(R.id.waterPrice) TextView waterPrice;
    @BindView(R.id.electPrice) TextView electPrice;
    @BindView(R.id.savePrice) TextView savePrice;
    @BindView(R.id.electover) ImageView electover;
    @BindView(R.id.waterover) ImageView waterover;
    APIInterface apiInterface;
    DecimalFormat myFormatter;
//    String userName;
//    String waterDday;
//    String electDday;
//    String electGoalPrice;
//    String waterGoalPrice;
//    String waterPrice;
//    String electPrice;
//    String savePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        myFormatter = new DecimalFormat("###,###");
        ButterKnife.bind(this);
        request();

        electricView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DetailSlidePagerActivity.class);
                intent.putExtra("pageNum",0);
                startActivity(intent);
            }
        });
        waterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //화면전환
                Intent intent = new Intent(MainActivity.this,DetailSlidePagerActivity.class);
                intent.putExtra("pageNum",1);
                startActivity(intent);
            }
        });
        gasView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"서비스 준비중입니다.",Toast.LENGTH_SHORT).show();
            }
        });
        donateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //화면전환
                Intent intent = new Intent(MainActivity.this,NsaveActivity.class);
                startActivity(intent);
            }
        });





    }
    //메인 통신
    public void request(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<MainModel> call = apiInterface.getmaindata();
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                Log.d(TAG,response.code()+"");
                MainModel mainModel = response.body();
                Log.d(TAG,mainModel.message);
                userName.setText(mainModel.maindata.userName);
                waterDday.setText("D-"+mainModel.maindata.waterDday);
                electDday.setText("D-"+mainModel.maindata.electDday);
                electGoalPrice.setText(myFormatter.format(mainModel.maindata.electGoalPrice));
                waterGoalPrice.setText(myFormatter.format(mainModel.maindata.waterGoalPrice));
                waterPrice.setText(myFormatter.format(mainModel.maindata.waterPrice));
                electPrice.setText(myFormatter.format(mainModel.maindata.electPrice));
                savePrice.setText(myFormatter.format(mainModel.maindata.savePrice));
                if(mainModel.maindata.electGoalPrice<mainModel.maindata.electPrice){
                    electover.setVisibility(View.VISIBLE);
                }else{
                    electover.setVisibility(View.INVISIBLE);
                }
                if(mainModel.maindata.waterGoalPrice<mainModel.maindata.waterPrice){
                    waterover.setVisibility(View.VISIBLE);
                }else{
                    waterover.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {
                Log.d(TAG,t.getMessage()+"");
                call.cancel();


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        request();
    }

}
