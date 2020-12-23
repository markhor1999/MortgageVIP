package com.preesoft.mortgagevip.Auth;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.preesoft.mortgagevip.Dashboard.HomeDashboradActivity;
import com.preesoft.mortgagevip.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //fireBase Auth
        mAuth = FirebaseAuth.getInstance();
        mReferenece = FirebaseDatabase.getInstance().getReference().child("Users");

        register = findViewById(R.id.registerUser);
        login    = findViewById(R.id.loginUser);
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
            startActivity(new Intent(LoginActivity.this,HomeDashboradActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {


        if (v== login)
        {
            if (awesomeValidation.validate()) {
                userLogin();
            }
        }
        if (v== register) {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }
        if (v==forgotPassword) {
            startActivity(new Intent(LoginActivity.this,ForgotActivity.class));
        }
    }

    private void userLogin() {
        //getting data to string
        emails = email.getText().toString();
        userPassword = password.getText().toString();
        //progressDialog
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Authentication....",
                "Please Wait...", false, false);

        mAuth.signInWithEmailAndPassword(emails, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this,HomeDashboradActivity.class));
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }


}
