package com.preesoft.mortgagevip.vip.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.preesoft.mortgagevip.Auth.ForgotActivity;
import com.preesoft.mortgagevip.Auth.RegisterActivity;
import com.preesoft.mortgagevip.Dashboard.HomeDashboradActivity;
import com.preesoft.mortgagevip.R;
import com.preesoft.mortgagevip.vip.VipSubscriptionActivity;

public class VipLoginActivity extends AppCompatActivity implements View.OnClickListener {


    private FloatingActionButton register;
    private Button login;
    private AwesomeValidation awesomeValidation;
    private TextView forgotPassword;
    private EditText email;
    private TextInputEditText password;

    private String emails, userPassword, userId, userType;
    private View view;
    private FirebaseAuth mAuth;
    private DatabaseReference mReferenece;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_login);

        //fireBase Auth
        mAuth = FirebaseAuth.getInstance();
        mReferenece = FirebaseDatabase.getInstance().getReference().child("Users");


        register = findViewById(R.id.registerUser);
        login = findViewById(R.id.loginUser);
        forgotPassword = findViewById(R.id.forgetPassword);

        password = findViewById(R.id.userPassword);
        email = findViewById(R.id.userEmail);


        register.setOnClickListener(this);
        login.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);


        //Validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(email, Patterns.EMAIL_ADDRESS, "Please Enter Valid Email");
        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(password, regexPassword, "");


    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            startActivity(new Intent(VipLoginActivity.this, HomeDashboradActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {


        if (v == login) {
            if (awesomeValidation.validate()) {
                userLogin();
            }
        }
        if (v == register) {
            startActivity(new Intent(VipLoginActivity.this, VipRegisterActivity.class));
        }
        if (v == forgotPassword) {
            startActivity(new Intent(VipLoginActivity.this, ForgotActivity.class));
        }
    }

    private void userLogin() {
        //getting data to string
        emails = email.getText().toString();
        userPassword = password.getText().toString();
        //progressDialog
        progressDialog = ProgressDialog.show(this, "Authentication....",
                "Please Wait...", false, false);

        mAuth.signInWithEmailAndPassword(emails, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkUserIsVip(task.getResult().getUser().getUid());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(VipLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUserIsVip(String uid) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("type")) {
                    if (dataSnapshot.child("type").getValue().equals("vip")) {
                        progressDialog.dismiss();
                        startActivity(new Intent(VipLoginActivity.this, HomeDashboradActivity.class));
                        Toast.makeText(VipLoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        mAuth.signOut();
                    }
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    mAuth.signOut();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                mAuth.signOut();
            }
        });
    }


}
