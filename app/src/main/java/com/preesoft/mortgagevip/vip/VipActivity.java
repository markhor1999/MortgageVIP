package com.preesoft.mortgagevip.vip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.preesoft.mortgagevip.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VipActivity extends AppCompatActivity implements View.OnClickListener {
    //views
    private EditText nameET, emailET, fullAddressET, phoneNumberET;
    private Button confirmButton;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;
    private DatabaseReference mUsersRef;
    private String userId;

    //Validation
    private AwesomeValidation awesomeValidation;

    //Progress
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        userId = Objects.requireNonNull(mAuth.getCurrentUser().getUid());
        mUsersRef = mRootRef.child("Users").child(userId);
        //Views
        nameET = findViewById(R.id.userName);
        emailET = findViewById(R.id.userEmail);
        phoneNumberET = findViewById(R.id.phone_number);
        fullAddressET = findViewById(R.id.full_address);
        confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(this);
        //
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(nameET, RegexTemplate.NOT_EMPTY, "Invalid Name");
        awesomeValidation.addValidation(emailET, Patterns.EMAIL_ADDRESS, "Invalid Email");
        awesomeValidation.addValidation(fullAddressET, RegexTemplate.NOT_EMPTY, "Address Cannot be Empty");
        String regexNumber = ".{11,}";
        awesomeValidation.addValidation(phoneNumberET, regexNumber, "Please Write 11 digit Number");

        //progressDialog
        progressDialog = new ProgressDialog(VipActivity.this);
        progressDialog.setTitle("Updating...");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);

        getAllData();
    }

    private void getAllData() {
        mUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String userName = Objects.requireNonNull(dataSnapshot.child("name").getValue().toString());
                    String userEmail = Objects.requireNonNull(dataSnapshot.child("email").getValue().toString());
                    if (dataSnapshot.hasChild("mobile")) {
                        String userMobile = Objects.requireNonNull(dataSnapshot.child("mobile").getValue().toString());
                        phoneNumberET.setText(userMobile);
                    }
                    if (dataSnapshot.hasChild("address")) {
                        String userAddress = Objects.requireNonNull(dataSnapshot.child("address").getValue().toString());
                        fullAddressET.setText(userAddress);
                    }

                    nameET.setText(userName);
                    emailET.setText(userEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == confirmButton) {
            if (awesomeValidation.validate()) {
                updateUserInfo();
            }
        }
    }

    private void updateUserInfo() {
        progressDialog.show();
        final String name = nameET.getText().toString();
        final String email = emailET.getText().toString();
        final String phone = phoneNumberET.getText().toString();
        final String type = "vip";
        final String address = fullAddressET.getText().toString();

        Map<String, Object> register = new HashMap<>();
        register.put("userID",userId);
        register.put("name",name);
        register.put("email",email);
        register.put("mobile",phone);
        register.put("type",type);
        register.put("address",address);

        mUsersRef.updateChildren(register).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(VipActivity.this, VipSubscriptionActivity.class));
                    finish();
                } else {
                    Toast.makeText(VipActivity.this, ""+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}