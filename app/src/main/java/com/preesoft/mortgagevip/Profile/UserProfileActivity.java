package com.preesoft.mortgagevip.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.preesoft.mortgagevip.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {



    private EditText name, email, mobile;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private CircleImageView userProfile;
    private String userId, userName, userEmail, typeUser, userMobile, imageDb;
    private Button update;
    private Uri resultUri;
    static int PICK_REQUEST = 101;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);



        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser().getUid());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //initialized fields
        userProfile = findViewById(R.id.profileClick);
        name = findViewById(R.id.update_UserName);
        email = findViewById(R.id.update_UserEmail);
        mobile = findViewById(R.id.update_UserPhone);
        update = findViewById(R.id.update_Btn);





        //click listener
        userProfile.setOnClickListener(this);
        update.setOnClickListener(this);



        loadDataFromDb();





    }

    @Override
    public void onClick(View v) {


        if (v==userProfile)
        {
            if (Build.VERSION.SDK_INT >= 22) {

                checkAndroidVersion();

            } else {

                openGallery();
            }
        }


        if (v == update) {
            updateUser();
        }

    }










    private void checkAndroidVersion() {

        if (ContextCompat.checkSelfPermission(UserProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(UserProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {


            } else {

                ActivityCompat.requestPermissions(UserProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PICK_REQUEST);

            }

        } else {

            openGallery();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            resultUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                Glide.with(getApplication())
                        .load(bitmap) // Uri of the picture
                        .apply(RequestOptions.circleCropTransform())
                        .into(userProfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 1);

    }


    private void loadDataFromDb() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild("image")) {
                        imageDb = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                        Glide.with(getApplicationContext()).load(imageDb).into(userProfile);
                    }


                    userName = Objects.requireNonNull(dataSnapshot.child("name").getValue().toString());
                    userEmail = Objects.requireNonNull(dataSnapshot.child("email").getValue().toString());
                    userMobile = Objects.requireNonNull(dataSnapshot.child("mobile").getValue().toString());

                    name.setText(userName);
                    email.setText(userEmail);
                    mobile.setText(userMobile);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void updateUser() {

        String nameU = name.getText().toString();
        String emailU = email.getText().toString();
        String mobileU = mobile.getText().toString();


        //progressDialog
        final ProgressDialog progressDialog = ProgressDialog.show(UserProfileActivity.this, "Update....",
                "Please Wait...", false, false);



        Map<String, Object> updateInfo = new HashMap<String, Object>();
        updateInfo.put("name", nameU);
        updateInfo.put("mobile", mobileU);
        updateInfo.put("email", emailU);

        if (resultUri != null) {

            final StorageReference filePath = FirebaseStorage.getInstance().getReference().child("Users").child(resultUri.getLastPathSegment());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filePath.putBytes(data);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                    return;
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Map<String, Object> newImage = new HashMap<String, Object>();
                            newImage.put("image", uri.toString());
                            databaseReference.updateChildren(newImage);
                            progressDialog.dismiss();
                            finish();
                            return;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            finish();
                            return;
                        }
                    });
                }
            });
        } else {
            finish();
        }


        databaseReference.updateChildren(updateInfo);


    }




}
