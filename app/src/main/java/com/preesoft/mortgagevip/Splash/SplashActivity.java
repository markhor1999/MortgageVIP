package com.preesoft.mortgagevip.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.preesoft.mortgagevip.R;
import com.wang.avi.AVLoadingIndicatorView;

public class SplashActivity extends AppCompatActivity {

    /*private Animation leftoright,righttoleft,layoutanim,fromTop,fromBottom;
   *//* private AVLoadingIndicatorView avLoadingIndicatorView;
    private ImageView logo;
    VideoView videoview;*//*
    private ImageView logoImageView;
    private TextView sloganTV, logoTV;
*/
    private static final int SPLASH_DISPLAY_LENGTH = 3000;
    private LottieAnimationView lottieAnimationView;
    private ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*logoImageView = findViewById(R.id.splash_logo_iv);
        sloganTV = findViewById(R.id.slogan_tv);
        logoTV = findViewById(R.id.logo_name_tv);

//        avLoadingIndicatorView = findViewById(R.id.avi);
        leftoright  = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        righttoleft = AnimationUtils.loadAnimation(this,R.anim.righttoleft);
        fromTop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        logoImageView.setAnimation(fromTop);
        sloganTV.setAnimation(fromBottom);
        logoTV.setAnimation(leftoright);*/

        lottieAnimationView = findViewById(R.id.splash_gate_anim);
        logoImageView = findViewById(R.id.splash_logo_iv);

        lottieAnimationView.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
                    }
                }
        );

        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                logoImageView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /* Create an Intent that will start the Login-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, AppVersionActivity.class);
                startActivity(mainIntent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
//        //videoView Background
//        videoview =  findViewById(R.id.videoView);
//        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.videeo);
//        videoview.setVideoURI(uri);
//        videoview.start();
//        videoview
//                .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        // not playVideo
//                        // playVideo();
//
//                        mp.start();
//                    }
//                });
//


//        logo = findViewById(R.id.splashLogo);
//
//        logo.setAnimation(fromTop);
//        avLoadingIndicatorView.setAnimation(fromBottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        videoview.start();
//    }

}
