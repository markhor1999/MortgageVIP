package com.preesoft.mortgagevip.vip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.preesoft.mortgagevip.Dashboard.HomeDashboradActivity;
import com.preesoft.mortgagevip.R;

public class VipSubscriptionActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView threeMonthsCard, sixMonthsCard, oneYearCard;
    private ImageView backImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_subscription);

        backImageView = findViewById(R.id.back_iv);
        threeMonthsCard = findViewById(R.id.three_months_subs_card);
        sixMonthsCard = findViewById(R.id.six_months_subs_card);
        oneYearCard = findViewById(R.id.one_year_subs_card);

        backImageView.setOnClickListener(this);
        threeMonthsCard.setOnClickListener(this);
        sixMonthsCard.setOnClickListener(this);
        oneYearCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == backImageView) {
            startActivity(new Intent(this, HomeDashboradActivity.class));
        } else if (v == threeMonthsCard) {
            startActivity(new Intent(this, ShowSheetsActivity.class));
        } else if (v == sixMonthsCard) {
            startActivity(new Intent(this, ShowSheetsActivity.class));
        } else if (v == oneYearCard) {
            startActivity(new Intent(this, ShowSheetsActivity.class));
        }
    }
}