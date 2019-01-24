package com.example.myapplication.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.myapplication.Model.ElectricData;
import com.example.myapplication.Model.EletcModel;
import com.example.myapplication.R;
import com.example.myapplication.Remote.APIClient;
import com.example.myapplication.Remote.APIInterface;
import com.example.myapplication.dialog.CustomDialog;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Eletricfragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.btn_setting) ImageView setting;
    @BindView(R.id.detail_yearmonth) TextView yearmonth;
    @BindView(R.id.detail_usage) TextView usage;
    @BindView(R.id.detail_charge) TextView charge;
    @BindView(R.id.usage2) TextView usage2;
    @BindView(R.id.charge2) TextView charge2;
    @BindView(R.id.charge3) TextView charge3;
    @BindView(R.id.usage3) TextView usage3;
    @BindView(R.id.btn_renew) ImageView renew;
    @BindView(R.id.predict) TextView predict;
    @BindView(R.id.nuzin) TextView nuzin;
    @BindView(R.id.dot) ImageView dot;
    @BindView(R.id.frame) FrameLayout frameLayout;
    @BindView(R.id.hidden)
    Button hidden;
    @BindView(R.id.progress) RoundCornerProgressBar progressBar;
    APIInterface apiInterface;
    DecimalFormat myFormatter;
    private Integer status;
    String sibal;
    FrameLayout.LayoutParams control;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.electricfragment,container,false);
        ButterKnife.bind(this,rootView);
        myFormatter = new DecimalFormat("###,###");
        request();


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog(getContext());
                customDialog.callFuction(sibal);
                customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Log.d("Tage","dismiss호출");
                        request();
                    }
                });
            }
        });
        renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
        hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = apiInterface.reset();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("TAGE","초기화성공");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        call.cancel();
                        Log.d("TAGE","초기화실패");
                    }
                });
            }
        });

        return rootView;

    }
    public void request(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
        control = (FrameLayout.LayoutParams) dot.getLayoutParams();
        Call<EletcModel> call = apiInterface.getelectricdata(yearmonth.getText().toString());
        call.enqueue(new Callback<EletcModel>() {
            @Override
            public void onResponse(Call<EletcModel> call, Response<EletcModel> response) {
                Log.d(TAG,response.code()+"");
                EletcModel eletcModel = response.body();
                ElectricData electricData = eletcModel.electdata;
                Log.d(TAG,eletcModel.message);


                usage.setText(myFormatter.format(electricData.monthUsage));
                charge.setText(myFormatter.format(electricData.monthUsagePrice));
                usage3.setText(myFormatter.format(electricData.saveAmount));
                charge3.setText(myFormatter.format(electricData.savePrice));

                status = electricData.statusGoal;
                sibal = String.valueOf(status);
                setprogress(getbasu(electricData,sibal),sibal,electricData);
                setnuzin(electricData);

//                if(sibal=="0"){
//                    if(electricData.predictionAmount<electricData.monthUsage){
//
//                        progressBar.setMax(electricData.predictionAmount*2);
//                        progressBar.setProgress(electricData.monthUsage-electricData.predictionAmount);
//                        progressBar.setProgressBackgroundColor(Color.parseColor("#62cea5"));
//                        progressBar.setProgressColor(Color.parseColor("#c16767"));}
//                        else{
//
//                        usage2.setText(myFormatter.format(electricData.predictionAmount));
//                        charge2.setText(myFormatter.format(electricData.predictionPrice));
//                        progressBar.setProgressBackgroundColor(Color.parseColor("#d8efe6"));
//                        progressBar.setProgressColor(Color.parseColor("#62cea5"));
//                        progressBar.setMax(electricData.predictionAmount);
//                        progressBar.setProgress(electricData.monthUsage);
//                    }
//
//
//
//                }else{
//                    usage2.setText(myFormatter.format(electricData.electGoal));
//                    charge2.setText(myFormatter.format(electricData.electGoalPrice));
//                    progressBar.setMax(electricData.electGoal);
//                    progressBar.setProgress(electricData.monthUsage);
//                    predict.setText("목표 사용량");
//                    if(electricData.electGoal<electricData.monthUsage){
//                        progressBar.setMax(electricData.electGoal*2);
//                        progressBar.setProgress(electricData.monthUsage-electricData.electGoal);
//                        progressBar.setProgressBackgroundColor(Color.parseColor("#62cea5"));
//                        progressBar.setProgressColor(Color.parseColor("#c16767"));
//                    }else{
//                        float pading1 = 100/(float)electricData.electGoal;
//                        Log.d("padding",pading1+"");
//                        control.leftMargin = Math.round(getPxFromDp(309*pading1));
//                        Log.d("leftmargin",Math.round(309*pading1)+"");
//                        dot.setLayoutParams(control);
//                        progressBar.setProgressBackgroundColor(Color.parseColor("#d8efe6"));
//                        progressBar.setProgressColor(Color.parseColor("#62cea5"));
//                        progressBar.setMax(electricData.predictionAmount);
//                        progressBar.setProgress(electricData.monthUsage);
//                    }
//
//                }





            }

            @Override
            public void onFailure(Call<EletcModel> call, Throwable t) {
                Log.d(TAG,t.getMessage()+"");
                call.cancel();


            }
        });


    }
    public void setnuzin(ElectricData electricData){
        int stage = electricData.monthUsage/100;
        nuzin.setText("누진세 "+stage+"단계 적용 중");
    }



    public int getPxFromDp(float dp) {
        int px = 0;
        Context appContext = getContext();
        px = Math.round (dp * appContext.getResources().getDisplayMetrics().density);
        return px;
    }
    public int getbasu(ElectricData electricData,String code){
        if(code=="0"){
            int basu =(int) electricData.monthUsage/electricData.predictionAmount;
            return basu;
        }else{
            int basu =(int) electricData.monthUsage/electricData.electGoal;
            return basu;
        }
    }
    public void setprogress(int basu, String code, ElectricData electricData){
        float pading1;
        if(code=="0"){
            if(electricData.predictionAmount>100 ) {
                pading1 = 100 / (float) electricData.predictionAmount;
                Log.d("padding",pading1+"");
                control.leftMargin = Math.round(getPxFromDp(309*pading1));
                Log.d("leftmargin",Math.round(309*pading1)+"");
                dot.setLayoutParams(control);
            }

            if(basu==0){
                progressBar.setProgressBackgroundColor(Color.parseColor("#d8efe6"));
                progressBar.setProgressColor(Color.parseColor("#62cea5"));
                progressBar.setMax(electricData.predictionAmount);
                progressBar.setProgress(electricData.monthUsage);
            }else if(basu%2!=0) {
                progressBar.setMax(electricData.predictionAmount);
                progressBar.setProgress(electricData.monthUsage-(basu*electricData.predictionAmount));
                progressBar.setProgressBackgroundColor(Color.parseColor("#62cea5"));
                progressBar.setProgressColor(Color.parseColor("#c16767"));
            }else {
                progressBar.setMax(electricData.predictionAmount);
                progressBar.setProgress(electricData.monthUsage-(basu*electricData.predictionAmount));
                progressBar.setProgressBackgroundColor(Color.parseColor("#c16767"));
                progressBar.setProgressColor(Color.parseColor("#62cea5"));
            }



        }else{
            usage2.setText(myFormatter.format(electricData.electGoal));
            charge2.setText(myFormatter.format(electricData.electGoalPrice));
            predict.setText("목표 사용량");
            if(electricData.electGoal>100 ) {
                pading1 = 100 / (float) electricData.electGoal;
                Log.d("padding",pading1+"");
                control.leftMargin = Math.round(getPxFromDp(309*pading1));
                Log.d("leftmargin",Math.round(309*pading1)+"");
                dot.setLayoutParams(control);
            }
            if(basu==0) {

                Log.d("basu", "목표 case0");
                progressBar.setProgressBackgroundColor(Color.parseColor("#d8efe6"));
                progressBar.setProgressColor(Color.parseColor("#62cea5"));
                progressBar.setMax(electricData.electGoal);
                progressBar.setProgress(electricData.monthUsage);
            }else if(basu%2!=0) {
                progressBar.setMax(electricData.electGoal);
                progressBar.setProgress(electricData.monthUsage-(basu*electricData.electGoal));
                progressBar.setProgressBackgroundColor(Color.parseColor("#62cea5"));
                progressBar.setProgressColor(Color.parseColor("#c16767"));
            }else {
                progressBar.setMax(electricData.electGoal);
                progressBar.setProgress(electricData.monthUsage-(basu*electricData.electGoal));
                progressBar.setProgressBackgroundColor(Color.parseColor("#c16767"));
                progressBar.setProgressColor(Color.parseColor("#62cea5"));
            }


        }

    }

    }











