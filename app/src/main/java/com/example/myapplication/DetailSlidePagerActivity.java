package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.Fragment.Eletricfragment;
import com.example.myapplication.Fragment.Waterfragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class DetailSlidePagerActivity extends FragmentActivity {
    private ViewPager viewpager;
    private PagerAdapter mPagerAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailslideactivity);
        Intent intent = getIntent();
        Log.d("Tag",""+intent.getIntExtra("pageNum",0));

        viewpager = findViewById(R.id.viewpager);
        mPagerAdapter = new DetailSlidePagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mPagerAdapter);
        if(intent.getIntExtra("pageNum",0)==0){
            viewpager.setCurrentItem(0);
        }else{
            viewpager.setCurrentItem(1);
        }








    }



    private class DetailSlidePagerAdapter extends FragmentStatePagerAdapter{

        public DetailSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new Eletricfragment();

                case 1:
                    return new Waterfragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

//        @Override
//        public int getItemPosition(@NonNull Object object) {
//
//            return POSITION_NONE;
//        }


    }

}
