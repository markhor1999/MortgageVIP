package com.preesoft.mortgagevip.Splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.preesoft.mortgagevip.Adapters.ViewPagerAdapter;
import com.preesoft.mortgagevip.Auth.LoginActivity;
import com.preesoft.mortgagevip.R;
import com.preesoft.mortgagevip.vip.auth.VipLoginActivity;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AppVersionActivity extends AppCompatActivity implements View.OnClickListener {


    private CardView freeText, paidText;

    /*ViewPager viewPager;
    private SpringDotsIndicator springDotsIndicator;
    ViewPagerAdapter viewPagerAdapter;
    Timer timer;*/

    //ImageSlider
    private ImageSlider imageSlider;
    private ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();

    //    private String[] name = {"johan", "JJ Thomson", "Animator", "Shells", "Bigbrow", "Cleners", "Ginners", "Nawal", "clipers"};
    private int[] image = {R.drawable.e, R.drawable.b,R.drawable.c };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_version);


        freeText = findViewById(R.id.freeCard);
        paidText = findViewById(R.id.paidCard);


        freeText.setOnClickListener(this);
        paidText.setOnClickListener(this);


       /* viewPager = findViewById(R.id.myViewPagerHome);
        springDotsIndicator = findViewById(R.id.spring_dots_indicator);


        // set ViewPagerAdapter
        viewPagerAdapter = new ViewPagerAdapter(AppVersionActivity.this, image);
        viewPager.setAdapter(viewPagerAdapter);
        springDotsIndicator.setVisibility(View.GONE);
        springDotsIndicator.setViewPager(viewPager);
        //viewPagerAdapter.setOnItemClickListener(HomeDashboradActivity.this);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable() {

                    @Override
                    public void run() {
                        viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % image.length);
                    }
                });
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 4000, 4000);*/
        //ImageSlider
        imageSlider= findViewById(R.id.image_slider);
        slideModelArrayList.add(new SlideModel(R.drawable.e, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.b, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.c, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModelArrayList);

    }

    @Override
    public void onClick(View v) {
        
        
        if (v == freeText){

            Intent intent = new Intent(AppVersionActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            
        }if (v == paidText){

            Intent intent = new Intent(AppVersionActivity.this, VipLoginActivity.class);
            startActivity(intent);
            finish();
            
            
        }

    }
}
