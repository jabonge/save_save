package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Remote.APIInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectricDialogFragment extends DialogFragment {

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
    OnMyDialogResult mDialogResult;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.customdialog,container,false);
        ButterKnife.bind(this,viewGroup);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setting.setVisibility(View.VISIBLE);
        open.setVisibility(View.GONE);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //통신해서 설정값 넘겨주기
                //내부데이터저장
                Log.d("tag",edit.getText().toString());
                if(edit.getText().toString().equals("")){
                    dismiss();
                }else {
                    int target_elect_usage = Integer.parseInt(edit.getText().toString());


                    if (target_elect_usage > 0) {

                        try{
                            Call<ResponseBody> call = apiInterface.electgoal(target_elect_usage);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    ResponseBody message = response.body();
                                    Log.d("TAG","전기 목표사용량 전송");
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    call.cancel();
                                }
                            });

                            mDialogResult.finish();

                            Toast toast = Toast.makeText(getContext(), "저장에 성공하였습니다.", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,100);
                            toast.show();

                        }catch (Exception e){
                            Log.d("TAG",e.getMessage());
                            Toast.makeText(getContext(), "서버 상태 이상", Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        Toast.makeText(getContext(), "값을 확인해주세요", Toast.LENGTH_SHORT).show();
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


        return viewGroup;

    }
    public void setDialogResult(OnMyDialogResult dialogResult){

        mDialogResult = dialogResult;

    }



    public interface OnMyDialogResult{

        void finish();

    }




}


