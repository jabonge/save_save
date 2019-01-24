package com.example.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Remote.APIClient;
import com.example.myapplication.Remote.APIInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomDialog2 extends Dialog{
    private Context context;
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.cancle)
    ImageView cancle;
    @BindView(R.id.save)
    ImageView save;
    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.open)
    RelativeLayout open;


    APIInterface apiInterface;


    public CustomDialog2(Context context) {
        super(context);
        this.context = context;
        apiInterface  = APIClient.getClient().create(APIInterface.class);
    }


//    public CustomDialog2(Context context){
//        this.context= context;
//
//
//    }
    public void callFuction(String sibal){
        CustomDialog2 dialog = new CustomDialog2(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.customdialog2);
        ButterKnife.bind(this,dialog);
        setting.setVisibility(View.VISIBLE);
        open.setVisibility(View.GONE);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //통신해서 설정값 넘겨주기
                //내부데이터저장
                Log.d("tag",edit.getText().toString());
                    if(edit.getText().toString().equals("")){
                        dialog.dismiss();
                    }else {
                        int target_water_usage = Integer.parseInt(edit.getText().toString());


                        if (target_water_usage > 0) {
                            Call<ResponseBody> call = apiInterface.watergoal(target_water_usage);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    ResponseBody message = response.body();
                                    Log.d("TAG","수도 목표사용량 전송");
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    call.cancel();
                                }
                          });

                            dialog.dismiss();

                            Toast toast = Toast.makeText(context, "저장에 성공하였습니다.", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,100);
                            toast.show();

                        } else {

                            Toast.makeText(context, "값을 확인해주세요", Toast.LENGTH_SHORT).show();
                        }

                    }





            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting.setVisibility(View.GONE);
                open.setVisibility(View.VISIBLE);

                //edittext로 전환
            }
        });

        dialog.show();
    }






}
