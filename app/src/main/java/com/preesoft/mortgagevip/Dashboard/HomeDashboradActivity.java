package com.preesoft.mortgagevip.Dashboard;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.preesoft.mortgagevip.AboutUS.AboutUsActivity;
import com.preesoft.mortgagevip.AboutUS.HelpActivity;
import com.preesoft.mortgagevip.AboutUS.TermConditionActivity;
import com.preesoft.mortgagevip.AboutUS.TipsActivity;
import com.preesoft.mortgagevip.Auth.LoginActivity;
import com.preesoft.mortgagevip.Mortgage_Rate.ViewRatesActivity;
import com.preesoft.mortgagevip.Profile.UserProfileActivity;
import com.preesoft.mortgagevip.R;
import com.preesoft.mortgagevip.Splash.AppVersionActivity;
import com.preesoft.mortgagevip.calculator.CalculatorActivity;
import com.preesoft.mortgagevip.calculator.ProjectCalculatorActivity;
import com.preesoft.mortgagevip.vip.VipActivity;
import com.preesoft.mortgagevip.vip.VipSubscriptionActivity;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeDashboradActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private CardView rate, calculator, commercial, mortgageCompany, vip, tips;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String userType, userName, userNumber, userImage, userId;

    //ImageSlider
    private ImageSlider imageSlider;
    private ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();

    private int[] image = {R.drawable.e, R.drawable.b, R.drawable.c};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dashborad);

        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser().getUid());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        // toolbar code
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Mortgage VIP");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        Button profileBTn;
        final TextView name, number;
        final CircleImageView profileImage;

        // initialization of navigationView
        profileBTn = hView.findViewById(R.id.profileBtn);
        name = hView.findViewById(R.id.text1);
        number = hView.findViewById(R.id.text2);
        profileImage = hView.findViewById(R.id.imageProfile);

        //ImageSlider
        imageSlider = findViewById(R.id.image_slider);
        slideModelArrayList.add(new SlideModel(R.drawable.e, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.b, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.c, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModelArrayList);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.exists()) {
                        if (dataSnapshot.hasChild("image")) {
                            userImage = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                            Glide.with(HomeDashboradActivity.this).load(userImage).into(profileImage);
                        }
                        if (dataSnapshot.hasChild("mobile")) {
                            userNumber = Objects.requireNonNull(dataSnapshot.child("mobile").getValue().toString());
                        }
                        userName = Objects.requireNonNull(dataSnapshot.child("name").getValue().toString());
                        userType = dataSnapshot.child("type").getValue().toString();

                        name.setText(userName);
                        number.setText(userNumber);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        profileBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeDashboradActivity.this, UserProfileActivity.class));
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimary));
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        rate            = findViewById(R.id.rateCardView);
        calculator      = findViewById(R.id.calculatorCardView);
        commercial      = findViewById(R.id.cammericalCardView);
        mortgageCompany = findViewById(R.id.card_mortgage);
        vip             = findViewById(R.id.card_vip);
        tips            = findViewById(R.id.tips);

        rate.setOnClickListener(this);
        calculator.setOnClickListener(this);
        commercial.setOnClickListener(this);
        vip.setOnClickListener(this);
        mortgageCompany.setOnClickListener(this);
        tips.setOnClickListener(this);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {

        }  else if (id == R.id.nav_termCondition) {
            startActivity(new Intent(HomeDashboradActivity.this, TermConditionActivity.class));
        } else if (id == R.id.nav_share) {
            share();
        }
        else if (id == R.id.nav_call) {
            callUS();
        }
        else if (id == R.id.nav_help) {
            startActivity(new Intent(HomeDashboradActivity.this, HelpActivity.class));
        }
        else if (id == R.id.nav_About) {
            startActivity(new Intent(HomeDashboradActivity.this, AboutUsActivity.class));
        } else if (id == R.id.nav_logout) {
            LogoutFunction();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callUS() {
          Intent intentCall=new Intent(Intent.ACTION_CALL);
          intentCall.setData(Uri.parse("tel:800"));

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"Please grant permission",Toast.LENGTH_SHORT).show();
            requestPermission();
        }else {
            startActivity(intentCall);
        }
    }
    private  void requestPermission(){
        ActivityCompat.requestPermissions(HomeDashboradActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }
    private void LogoutFunction() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.logoutdialog_layout, null);

        Button logoutButton, canncelButton;
        logoutButton = view.findViewById(R.id.logoutClick);
        canncelButton = view.findViewById(R.id.logout_CanncelButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeDashboradActivity.this, AppVersionActivity.class));
                finish();
            }
        });
        canncelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        builder.setView(view);
        builder.show();
    }

    private void share() {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "App description";
        String shareSub = "Apk url";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(shareIntent, "Share via"));

    }

    @Override
    public void onClick(View v) {
        if (v == rate){
            Intent intent = new Intent(HomeDashboradActivity.this, ViewRatesActivity.class);
            startActivity(intent);
        }if (v == calculator){
            Intent rateIntent = new Intent(HomeDashboradActivity.this, CalculatorActivity.class);
            startActivity(rateIntent);
        }if (v == commercial){
            Intent rateIntent = new Intent(HomeDashboradActivity.this, ProjectCalculatorActivity.class);
            startActivity(rateIntent);
        }
        if (v == mortgageCompany){
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?q=mortgage+companies"));
            startActivity(intent);
        }
        if (v == vip){
            if (userType.equals("vip")) {
                Intent intent = new Intent(HomeDashboradActivity.this, VipSubscriptionActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(HomeDashboradActivity.this, VipActivity.class);
                startActivity(intent);
            }
        }
        if (v == tips) {
            Intent intent = new Intent(HomeDashboradActivity.this, TipsActivity.class);
            startActivity(intent);
        }
    }

    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeDashboradActivity.this);
        builder.setMessage("Are you sure want to do this ?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
