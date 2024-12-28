package com.goskydive;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.goskydive.logbook.AddJumpActivity;
import com.goskydive.logbook.LogBook;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    ImageView addJumpButton;
    ImageView skydiveWithFriendsButton;
    ImageView profileIconButton;
    TextView jumpNumberText;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_drawer);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerLayout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
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

        addJumpButton = findViewById(R.id.addjumpbutton);
        profileIconButton = findViewById(R.id.profileIcon);
        jumpNumberText = findViewById(R.id.jumpsNumberText);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        addJumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddJumpActivity.class));
            }
        });

        profileIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
            }
        });


        DocumentReference totalJumpsReference = fStore.collection("allJumps").document(userId);
        totalJumpsReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot jumpNumberReferenceSnapshot, @Nullable FirebaseFirestoreException error) {
                try {
                    long jumpNo = jumpNumberReferenceSnapshot.getLong("allJumps");
                    jumpNumberText.setText(String.valueOf(jumpNo + " jumps."));
                } catch (NullPointerException e) {
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        moveTaskToBack(true);

    }

}