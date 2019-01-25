package com.example.myapplication.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.myapplication.Model.WaterData;
import com.example.myapplication.Model.WaterModel;
import com.example.myapplication.R;
import com.example.myapplication.Remote.APIClient;
import com.example.myapplication.Remote.APIInterface;

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


public class Waterfragment extends Fragment  {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.btn_setting) ImageView setting;
    @BindView(R.id.detail_yearmonth) TextView yearmonth;
    @BindView(R.id.usage) TextView usage;
    @BindView(R.id.charge) TextView charge;
    @BindView(R.id.usage2) TextView usage2;
    @BindView(R.id.charge3) TextView charge3;
    @BindView(R.id.usage3) TextView usage3;
    @BindView(R.id.charge2) TextView charge2;
    @BindView(R.id.btn_renew) ImageView renew;
    @BindView(R.id.predict) TextView predict;
    @BindView(R.id.nuzin) TextView nuzin;
    @BindView(R.id.dot) ImageView dot;
    @BindView(R.id.hidbutton) Button button;
    @BindView(R.id.hidden)
    Button hidden;
    @BindView(R.id.progress) RoundCornerProgressBar progressBar;
    APIInterface apiInterface;
    DecimalFormat myFormatter;
    FrameLayout.LayoutParams control;
    private Vibrator vibrator;

    private Integer status;
    WaterData waterData;
    String sibal;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.waterfragment,container,false);

        ButterKnife.bind(this,rootView);
        myFormatter = new DecimalFormat("###,###");
        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);


        request();
        setting.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           WaterDialogFragment wdialogFragment = new WaterDialogFragment();
                                           Bundle bundle = new Bundle();
                                           bundle.putInt("usage",waterData.predictionAmount);
                                           bundle.putInt("price",waterData.predictionPrice);
                                           wdialogFragment.setArguments(bundle);
                                           wdialogFragment.show(getChildFragmentManager(), "fragment_dialog_test");
                                           wdialogFragment.setDialogResult(new WaterDialogFragment.OnMyDialogResult() {
                                               @Override
                                               public void finish() {

                                                   wdialogFragment.dismiss();
                                                   Log.d("last","리슨");
                                                   try{
                                                       Thread.sleep(500);
                                                   }catch (Exception e){
                                                      Log.d("error",e.getMessage());
                                                   }
                                                   request();
                                               }
                                           });
                                       }
                                   });

//        setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CustomDialog2 customDialog = new CustomDialog2(getContext());
//                customDialog.callFuction(sibal);
//                customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        Log.d("tage","dismiss");
//                        request();
//                    }
//                });
//            }
//        });



//        });
        renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
        hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Call<ResponseBody> call = apiInterface.reset();
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.d("TAGE", "초기화성공");
                            vibrator.vibrate(1000);
                            request();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            call.cancel();
                            Log.d("TAGE", "초기화실패");
                        }
                    });
                }catch (Exception e){
                    Log.d(TAG,""+e.getMessage());
                    Toast.makeText(getContext(), "서버 상태 이상", Toast.LENGTH_SHORT).show();

                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setMax(22);
                progressBar.setProgress((float)21);

                usage.setText("21");
                charge.setText("22,870");

            }
        });

        return rootView;

    }
    @Override
     public void onPause() {
        super.onPause();
        Log.d("TAG","onpause");
    }
    public void request(){
        Log.d("lastlast","마지막");
        apiInterface = APIClient.getClient().create(APIInterface.class);
        control = (FrameLayout.LayoutParams) dot.getLayoutParams();
        try {
            Call<WaterModel> call = apiInterface.getwaterdata(yearmonth.getText().toString());
            call.enqueue(new Callback<WaterModel>() {
                @Override
                public void onResponse(Call<WaterModel> call, Response<WaterModel> response) {
                    Log.d(TAG, response.code() + "");
                    WaterModel waterModel = response.body();
                    waterData = waterModel.waterdata;
                    Log.d(TAG, waterModel.message);
                    Log.d(TAG, "monthUsage" + myFormatter.format(waterData.monthUsage));
                    Log.d(TAG, "predictionAmount" + myFormatter.format(waterData.predictionAmount));

                    usage.setText(myFormatter.format(waterData.monthUsage));
                    charge.setText(myFormatter.format(waterData.monthUsagePrice));

                    usage3.setText(myFormatter.format(waterData.saveAmount));
                    charge3.setText(myFormatter.format(waterData.savePrice));


                    //dot.setVisibility(View.GONE);
                    status = waterData.stateGoal;
                    sibal = String.valueOf(status);
                    Log.d("sibal",sibal);
                    if(sibal.equals("1")){
                        predict.setText("목표 사용량");
                        usage2.setText(myFormatter.format(waterData.waterGoal));
                        charge2.setText(myFormatter.format(waterData.waterGoalPrice));
                    }else{
                        predict.setText("당월 예상수치");
                        usage2.setText(myFormatter.format(waterData.predictionAmount));
                        charge2.setText(myFormatter.format(waterData.predictionPrice));
                    }
                    setprogress(getbasu(waterData, sibal), sibal, waterData);
                    setnuzin(waterData);
//                if(sibal=="0") {
//                    usage2.setText(myFormatter.format(waterData.predictionAmount));
//                    charge2.setText(myFormatter.format(waterData.predictionPrice));
//                    if (waterData.predictionAmount < waterData.monthUsage) {
//                        progressBar.setMax(waterData.predictionAmount * 2);
//                        progressBar.setProgress(waterData.monthUsage - waterData.predictionAmount);
//                        progressBar.setProgressBackgroundColor(Color.parseColor("#62cea5"));
//                        progressBar.setProgressColor(Color.parseColor("#c16767"));
//                    } else{
//                        progressBar.setMax(waterData.predictionAmount);
//                        progressBar.setProgress(waterData.monthUsage);
//                        progressBar.setProgressBackgroundColor(Color.parseColor("#d8efe6"));
//                        progressBar.setProgressColor(Color.parseColor("#62cea5"));}
//
//
//
//                }else{
//                    usage2.setText(myFormatter.format(waterData.waterGoal));
//                    charge2.setText(myFormatter.format(waterData.waterGoalPrice));
//                    progressBar.setMax(waterData.waterGoal);
//                    progressBar.setProgress(waterData.monthUsage);
//                    predict.setText("목표 사용량");
//                    if(waterData.waterGoal<waterData.monthUsage){
//                        progressBar.setMax(waterData.waterGoal*2);
//                        progressBar.setProgress(waterData.monthUsage-waterData.waterGoal);
//                        progressBar.setProgressBackgroundColor(Color.parseColor("#62cea5"));
//                        progressBar.setProgressColor(Color.parseColor("#c16767"));
//                    }
//
//                }


                }

                @Override
                public void onFailure(Call<WaterModel> call, Throwable t) {
                    Log.d(TAG, t.getMessage() + "");
                    call.cancel();


                }
            });
        }catch (Exception e){
            Log.d(TAG, e.getMessage() + "");
            Toast.makeText(getContext(), "서버 상태 이상", Toast.LENGTH_SHORT).show();

        }


    }
    public void setnuzin(WaterData electricData){
        int stage;
        if(electricData.monthUsage<=30){
            stage =0;
        }else if(electricData.monthUsage<=50){
            stage =1;
        }else{
            stage =2;
        }
        nuzin.setText("누진세 "+stage+"단계 적용 중");
    }



    public int getPxFromDp(float dp) {
        int px = 0;
        Context appContext = getContext();
        px = Math.round (dp * appContext.getResources().getDisplayMetrics().density);
        return px;
    }
    public int getbasu(WaterData electricData,String code){
        Log.d("basu",code+"");
        if(code.equals("0")){
            int basu =(int) electricData.monthUsage/electricData.predictionAmount;
            return basu;
        }else{
            int basu =(int) electricData.monthUsage/electricData.waterGoal;
            return basu;
        }
    }
    public void setprogress(int basu,String code,WaterData electricData){
        float pading1;
//        usage.setText(myFormatter.format(electricData.monthUsage));
//        charge.setText(myFormatter.format(electricData.monthUsagePrice));
        if(code.equals("0")){
//            if(electricData.predictionAmount>30 ) {
//                pading1 = 30 / (float) electricData.predictionAmount;
//                Log.d("padding",pading1+"");
//                control.leftMargin = Math.round(getPxFromDp(309*pading1));
//                Log.d("leftmargin",Math.round(309*pading1)+"");
//                dot.setLayoutParams(control);
//            }

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
//            usage.setText(myFormatter.format(electricData.monthUsage));
//            charge.setText(myFormatter.format(electricData.monthUsagePrice));
//            usage2.setText(myFormatter.format(electricData.waterGoal));
//            charge2.setText(myFormatter.format(electricData.waterGoalPrice));
//            predict.setText("목표 사용량");
//            if(electricData.waterGoal>100 ) {
//                pading1 = 100 / (float) electricData.waterGoal;
//                Log.d("padding",pading1+"");
//                control.leftMargin = Math.round(getPxFromDp(309*pading1));
//                Log.d("leftmargin",Math.round(309*pading1)+"");
//                dot.setLayoutParams(control);
//            }
            if(basu==0) {

                Log.d("basu", "목표 case0");
                progressBar.setProgressBackgroundColor(Color.parseColor("#d8efe6"));
                progressBar.setProgressColor(Color.parseColor("#62cea5"));
                progressBar.setMax(electricData.waterGoal);
                progressBar.setProgress(electricData.monthUsage);
            }else if(basu%2!=0) {
                progressBar.setMax(electricData.waterGoal);
                progressBar.setProgress(electricData.monthUsage-(basu*electricData.waterGoal));
                progressBar.setProgressBackgroundColor(Color.parseColor("#62cea5"));
                progressBar.setProgressColor(Color.parseColor("#c16767"));
            }else {
                progressBar.setMax(electricData.waterGoal);
                progressBar.setProgress(electricData.monthUsage-(basu*electricData.waterGoal));
                progressBar.setProgressBackgroundColor(Color.parseColor("#c16767"));
                progressBar.setProgressColor(Color.parseColor("#62cea5"));
            }


        }

    }


}
