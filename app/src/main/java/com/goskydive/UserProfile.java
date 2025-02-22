package com.goskydive;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.goskydive.logbook.LogBook;

public class UserProfile extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    Button logoutButton, changeProfileImageButton;
    TextView name, email, totalJumps, textview;
    ImageView profileImage;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    //TODO
    // StorageReference storageReference;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);

            toolbar = findViewById(R.id.toolbar);
            navigationView = findViewById(R.id.navigation_view);
            drawerLayout = findViewById(R.id.drawerLayout);


            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(UserProfile.this, drawerLayout, toolbar, R.string.open, R.string.close);
            drawerLayout.addDrawerListener(toggle);
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.nav_logbok) {
                        Intent intent = new Intent(getApplicationContext(), LogBook.class);
                        startActivity(intent);
                    }
                    if (item.getItemId() == R.id.nav_messages) {
                        Intent intent = new Intent(getApplicationContext(), Messages.class);
                        startActivity(intent);
                    }
                    if (item.getItemId() == R.id.nav_friends) {
                        Intent intent = new Intent(getApplicationContext(), Friends.class);
                        startActivity(intent);
                    }
                    if (item.getItemId() == R.id.nav_weather) {
                        Intent intent = new Intent(getApplicationContext(), Weather.class);
                        startActivity(intent);
                    }
                    if (item.getItemId() == R.id.nav_alerts) {
                        Intent intent = new Intent(getApplicationContext(), Alerts.class);
                        startActivity(intent);
                    }
                    if (item.getItemId() == R.id.nav_settings) {
                        Intent intent = new Intent(getApplicationContext(), Settings.class);
                        startActivity(intent);
                    }
                    if (item.getItemId() == R.id.nav_logbok) {
                        Intent intent = new Intent(getApplicationContext(), LogBook.class);
                        startActivity(intent);
                    }
                    if (item.getItemId() == R.id.nav_logout) {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        FirebaseAuth.getInstance().signOut();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }


                    return true;
                }
            });

        name = findViewById(R.id.your_name_text);
        email = findViewById(R.id.your_email_text);
        totalJumps = findViewById(R.id.total_jumps);
        profileImage = findViewById(R.id.profile_image);
        changeProfileImageButton = findViewById(R.id.change_profile_image_button);
        textview = findViewById(R.id.number_To_view);

        logoutButton = findViewById(R.id.logout_button);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //TODO
        // storageReference = FirebaseStorage.getInstance().getReference();

        userId = fAuth.getCurrentUser().getUid();

//ratriving data from firestore

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                name.setText(documentSnapshot.getString("userName"));
                email.setText(documentSnapshot.getString("email"));
            }
        });

        DocumentReference totalJumpsReference = fStore.collection("allJumps").document(userId);
        totalJumpsReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot jumpNumberReferenceSnapshot, @Nullable FirebaseFirestoreException error) {
                try {
                    long jumpNo = jumpNumberReferenceSnapshot.getLong("allJumps");
                    textview.setText(String.valueOf(jumpNo));
                } catch (NullPointerException e) {
                   //TODO
                    //add toast message, that user has not made any jumps yet
                }

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(), Login.class));
//                finish();

            }
        });

        //Button to changing profile image

        changeProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open device gallery code
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                profileImage.setImageURI(imageUri);
//TODO
//                uploadImageToFirebase(imageUri);

            }
        }
    }
//TODO
//    private void uploadImageToFirebase(Uri imageUri) {
//      upload profile image to firebase storage
//
//        StorageReference fileReference = storageReference.child("profile.jpg");
//        fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(UserProfile.this, "Profile image uploaded", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(UserProfile.this, "Error! Profile image not uploaded", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

}