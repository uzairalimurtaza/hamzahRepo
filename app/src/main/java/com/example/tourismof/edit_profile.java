package com.example.tourismof;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tourismof.Fragments.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class edit_profile extends AppCompatActivity {
    String email;
    private EditText UserName,Phone1, Bio;
    private Spinner CountryName,gender;
    private Button SaveInformationbuttion;
    private CircleImageView ProfileImage,pic;
    private ProgressDialog loadingBar;
    String gender_edit,user_name_edit,country_edit,phone_edit,bio_edit,ProfileImage_edit;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private StorageReference UserProfileImageRef;
    private DatabaseReference mRef;


    String currentUserID;
    final static int Gallery_Pick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

        mRef = FirebaseDatabase.getInstance().getReference().child("Users");

        ProfileImage=findViewById(R.id.profile_image);
        Bio=findViewById(R.id.bioo);
        UserName = (EditText) findViewById(R.id.name_edit_profile);
        Phone1 = (EditText) findViewById(R.id.phone_edit_profile);
        CountryName =findViewById(R.id.sp_country_edit_profile);
        gender=findViewById(R.id.gender);
        SaveInformationbuttion = (Button) findViewById(R.id.btn_edit_profile);
        pic = (CircleImageView) findViewById(R.id.edit_image_edit_profile);
         getvaluesofedit();
         setvaluesofedit();
        loadingBar = new ProgressDialog(this);

        pic.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent galleryIntent = new Intent();
              galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
              galleryIntent.setType("image/*");
              startActivityForResult(galleryIntent, Gallery_Pick);
          }
      });

        SaveInformationbuttion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAccountSetupInformation();
            }
        });


//        UsersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//
//                    if (dataSnapshot.hasChild("Profile")) {
//                        String img = dataSnapshot.child("Profile").getValue().toString();
//                        Picasso.get().load(img).placeholder(R.drawable.profile).into(ProfileImage);
//                    } else {
//                        Toast.makeText(edit_profile.this, "Please Add Profile Image", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    private void setvaluesofedit() {
        if(bio_edit !=null && country_edit !=null && ProfileImage_edit !=null && user_name_edit !=null && gender_edit !=null && phone_edit !=null)
        {
            Picasso.get().load(ProfileImage_edit).into(ProfileImage);
            UserName.setText(user_name_edit);
            Bio.setText(bio_edit);
            Phone1.setText(phone_edit);

        }
    }

    private void getvaluesofedit() {

        mRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("gender")){
                    gender_edit= dataSnapshot.child("gender").getValue().toString();
                }

                if(dataSnapshot.hasChild("username")){

                    user_name_edit = dataSnapshot.child("username").getValue().toString();
                }

                if(dataSnapshot.hasChild("country")){

                    country_edit = dataSnapshot.child("country").getValue().toString();
                }

                if(dataSnapshot.hasChild("ph_no")){
                    phone_edit = dataSnapshot.child("ph_no").getValue().toString();
                }
                if(dataSnapshot.hasChild("bio")){
                    bio_edit = dataSnapshot.child("bio").getValue().toString();
                }

                if(dataSnapshot.hasChild("Profile")){

                    ProfileImage_edit = dataSnapshot.child("Profile").getValue().toString();
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void SaveAccountSetupInformation() {
        String username = UserName.getText().toString();
        String Phone = Phone1.getText().toString();
        String country = CountryName.getSelectedItem().toString();
        String GGender=gender.getSelectedItem().toString();
        String bio_profile= Bio.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please write your username...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Phone)) {
            Toast.makeText(this, "Please write your full name...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(country)) {
            Toast.makeText(this, "Please write your country...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Saving Information");
            loadingBar.setMessage("Please wait, while we are creating your new Account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            getemail();
            HashMap userMap = new HashMap();
            userMap.put("username", username);
            userMap.put("ph_no", Phone);
            userMap.put("country", country);
            userMap.put("email", email);
            userMap.put("bio",bio_profile );
            userMap.put("gender",GGender );

            UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        SendUserToMainActivity();
                        Toast.makeText(edit_profile.this, "your Account is created Successfully.", Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(edit_profile.this, "Error Occured: " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }

        Intent intent=new Intent(edit_profile.this, FirstActivity.class);
        intent.putExtra("hostt",20);
        startActivity(intent);
    }

    private void getemail() {


        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("fullname")){
                    email = dataSnapshot.child("email").getValue().toString();

                }

       }
            @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(edit_profile.this, FirstActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Pick) {
            if (resultCode == RESULT_OK) {
                Uri imageData = data.getData();
                ProfileImage.setImageURI(imageData);

                
                final StorageReference ImageName = UserProfileImageRef.child(currentUserID + ".jpg");
                ImageName.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                UsersRef.child("Profile").setValue(String.valueOf(uri)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(edit_profile.this, "Error Resolved ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                        Toast.makeText(edit_profile.this, "Error Occured: ", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }
    }


}

