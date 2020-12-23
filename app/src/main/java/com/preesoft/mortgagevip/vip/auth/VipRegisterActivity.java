package com.preesoft.mortgagevip.vip.auth;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.preesoft.mortgagevip.Auth.LoginActivity;
import com.preesoft.mortgagevip.Auth.RegisterActivity;
import com.preesoft.mortgagevip.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VipRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    //views
    private EditText nameET, emailET, fullAddressET, phoneNumberET, passwordET;
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
        setContentView(R.layout.activity_vip_register);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mUsersRef = mRootRef.child("Users");
        //Views
        nameET = findViewById(R.id.userName);
        emailET = findViewById(R.id.userEmail);
        phoneNumberET = findViewById(R.id.phone_number);
        fullAddressET = findViewById(R.id.full_address);
        passwordET = findViewById(R.id.password_et);
        confirmButton = findViewById(R.id.confirm_button);


        confirmButton.setOnClickListener(this);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(nameET, RegexTemplate.NOT_EMPTY, "Invalid Name");
        awesomeValidation.addValidation(emailET, Patterns.EMAIL_ADDRESS, "Invalid Email");
        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(passwordET, regexPassword, "Please Write 8 digit Password");
        String regexNumber = ".{11,}";
        awesomeValidation.addValidation(phoneNumberET, regexNumber, "Please Write 11 digit Number");

        //
        //progressDialog
        progressDialog = new ProgressDialog(VipRegisterActivity.this);
        progressDialog.setTitle("Register...");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);

//        getAllData();
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
                registerNewVipUser();
            }
        }
    }

    private void registerNewVipUser() {
        progressDialog.show();
        final String name = nameET.getText().toString();
        final String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        final String phone = phoneNumberET.getText().toString();
        final String type = "vip";
        final String address = fullAddressET.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    saveUserInfo(name, email, phone, type, address);
                }
            }
        });
    }

    private void saveUserInfo(String name, String email, String phone, String type, String address) {
        Map register = new HashMap();
        userId = Objects.requireNonNull(mAuth.getCurrentUser().getUid());
        register.put("userID",userId);
        register.put("name",name);
        register.put("email",email);
        register.put("mobile",phone);
        register.put("type",type);
        register.put("address",address);

        mUsersRef.child(userId).setValue(register).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    mAuth.signOut();
                    Toast.makeText(VipRegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                    Intent registerIntent = new Intent(VipRegisterActivity.this, VipLoginActivity.class);
                    startActivity(registerIntent);
                } else {
                    progressDialog.dismiss();
                    mAuth.signOut();
                    Toast.makeText(VipRegisterActivity.this, ""+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}