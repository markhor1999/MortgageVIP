package com.preesoft.mortgagevip.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.preesoft.mortgagevip.R;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registerTv, loginTv,txt1,txt2;
    private Animation leftToRight,rightToLeft,fromRight,fromTop,fromBottom,fromLeft;
    private ImageView imageViewLogo;
    private CardView linearCardForgot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);



        //initializes fields
        registerTv = findViewById(R.id.registerText);
        loginTv = findViewById(R.id.loginText);
        txt1=findViewById(R.id.fg_txt1);
        txt2=findViewById(R.id.fg_txt2);
        linearCardForgot=findViewById(R.id.linear_cardForgot);



        //animation
        leftToRight  = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        rightToLeft = AnimationUtils.loadAnimation(this,R.anim.righttoleft);
        fromRight = AnimationUtils.loadAnimation(this,R.anim.fromright);
        fromLeft = AnimationUtils.loadAnimation(this,R.anim.fromleft);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);

        //apply animation
        imageViewLogo.setAnimation(fromRight);
        txt1.setAnimation(fromRight);
        txt2.setAnimation(fromRight);
        linearCardForgot.setAnimation(fromBottom);
        loginTv.setAnimation(fromLeft);
        registerTv.setAnimation(fromRight);







        //click listeners
        registerTv.setOnClickListener(this);
        loginTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == loginTv){

            startActivity(new Intent(ForgotActivity.this, LoginActivity.class));
            finish();

        }if (v == registerTv){

            startActivity(new Intent(ForgotActivity.this, RegisterActivity.class));
            finish();

        }
    }
}
