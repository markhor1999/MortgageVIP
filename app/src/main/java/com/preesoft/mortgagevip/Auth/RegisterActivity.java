package com.preesoft.mortgagevip.Auth;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.preesoft.mortgagevip.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name, email, password, confirm_password;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private AwesomeValidation awesomeValidation;
    private Button registerBtn;
    private String userName, userId, userEmail,
            userPassword, confirmPassword, userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // firebase Reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();

        // initialized field
        name = findViewById(R.id.userName);
        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        confirm_password = findViewById(R.id.confirm_password);
        registerBtn = findViewById(R.id.registerBtn);

        // click listener
        registerBtn.setOnClickListener(this);

        // Validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(name, RegexTemplate.NOT_EMPTY, "Invalid Name");
        awesomeValidation.addValidation(email, Patterns.EMAIL_ADDRESS, "Invalid Email");
        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(password, regexPassword, "Please Write 8 digit Password");
        /*String regexNumber = ".{11,}";
        awesomeValidation.addValidation(phone, regexNumber, "Please Write 11 digit Number");*/
    }

    @Override
    public void onClick(View v) {
        if (registerBtn==v)
        {
            if (awesomeValidation.validate()) {
                if (password.getText().toString().equals(confirm_password.getText().toString())) {
                    registerUser();
                } else {
                    Toast.makeText(this, "Password & Confirm Password not match", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void registerUser() {

        //getting data to string
        userName = name.getText().toString();
        userEmail = email.getText().toString();
        userPassword = password.getText().toString();

        //progressDialog
        final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this, "Register....",
                "Please Wait...", false, false);

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       // userId = mAuth.getCurrentUser().getUid();
                        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                        if(mFirebaseUser != null) {
                            userId = mFirebaseUser.getUid(); //Do what you need to do with the id
                        }
                        if (task.isSuccessful()) {
                            Map register = new HashMap();
                            register.put("userID", userId);
                            register.put("name", userName);
                            register.put("email", userEmail);
                            register.put("type", "free");
                            register.put("image", "");
                            register.put("address", "");

                            databaseReference.child(userId).setValue(register)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            mAuth.signOut();
                                            Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                            Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(registerIntent);
                                        }
                                    });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
