package com.goskydive.logbook;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.goskydive.Alerts;
import com.goskydive.Friends;
import com.goskydive.Login;
import com.goskydive.MainActivity;
import com.goskydive.MainActivityOld;
import com.goskydive.Messages;
import com.goskydive.R;
import com.goskydive.Settings;
import com.goskydive.Weather;
import com.goskydive.jump.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddJumpActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    public static final String TAG = "TAG";
    TextView jumpNumber, editTextDate, seekBarResult, freeFallTimeResult;
    Spinner chooseJumpType, setPlane, setCanopy, setDropzone;
    SeekBar heighSeekBar;
    ImageButton addJumpToFireStore;




    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    long nextJumpPlusOne;
    long userJumpValue;
    long freeFallTime;
    Date date = new Date();
    SimpleDateFormat todayDate = new SimpleDateFormat("dd.MM.yyyy");
    String todayDateToString = todayDate.format(date);

    int[] seekBarHighValues = {800, 1000, 1200, 1500, 2000, 2500, 3000, 3500, 4000, 4500};
    Jump nextJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_jump);



        jumpNumber = findViewById(R.id.jump_number_number);
        editTextDate = findViewById(R.id.editTextDate);
        heighSeekBar = findViewById(R.id.heigh_seekbar);
        chooseJumpType = findViewById(R.id.spinner_jump_type);
        setPlane = findViewById(R.id.set_plane_spinner);
        setCanopy = findViewById(R.id.set_canopy_spinner);
        setDropzone = findViewById(R.id.set_dropzone_spinner);
        seekBarResult = findViewById(R.id.seekbar_result);
        addJumpToFireStore = findViewById(R.id.add_jump);
        freeFallTimeResult = findViewById(R.id.freefall_time_result);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();


        editTextDate.setText(todayDateToString);


        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerLayout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(AddJumpActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
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


        DocumentReference totalJumpsReference = fStore.collection("allJumps").document(userId);
        totalJumpsReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot jumpNumberReferenceSnapshot, @Nullable FirebaseFirestoreException error) {
                try {
                    long jumpNo = jumpNumberReferenceSnapshot.getLong("allJumps") + 1; // because this is the next jump
                    nextJumpPlusOne = jumpNo;

                    jumpNumber.setText(String.valueOf(jumpNo));
                } catch (NullPointerException e) {
                }

            }
        });

        //spinner jumpType from firestore

        fStore.collection("jumpStyles").get().addOnSuccessListener(result -> {
            ArrayList<String> items = new ArrayList<>();
            for (DocumentSnapshot snapshot : result) {
                String item = snapshot.getString("jumpStyle");
                items.add(item);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chooseJumpType.setAdapter(adapter);

        });

        //spinner planeType

        fStore.collection("planes").get().addOnSuccessListener(result -> {
            ArrayList<String> items = new ArrayList<>();
            for (DocumentSnapshot snapshot : result) {
                String item = snapshot.getString("plane");
                items.add(item);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            setPlane.setAdapter(adapter);

        });

        //spinner canopy

        fStore.collection("canopies").get().addOnSuccessListener(result -> {
            ArrayList<String> items = new ArrayList<>();
            for (DocumentSnapshot snapshot : result) {
                String item = snapshot.getString("canopy");
                items.add(item);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            setCanopy.setAdapter(adapter);

        });

        //spinner dropzone

        fStore.collection("dropzones").get().addOnSuccessListener(result -> {
            ArrayList<String> items = new ArrayList<>();
            for (DocumentSnapshot snapshot : result) {
                String item = snapshot.getString("dropzone");
                items.add(item);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            setDropzone.setAdapter(adapter);

        });

        //seekbar high

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{R.color.red_seek_bar, R.color.light_red_seek_bar, R.color.yellow_seek_bar, R.color.light_yellow_seek_bar,
                        R.color.light_green_seek_bar, R.color.green_seek_bar,
                });
        gradientDrawable.setCornerRadius(10);
        heighSeekBar.setProgressDrawable(gradientDrawable);

        heighSeekBar.setMax(4000);
        heighSeekBar.setMin(800);
        heighSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                seekBarResult.setText(progress + " meters AGL");

                userJumpValue = Long.valueOf(progress);

                //calculating freefall time

                if (heighSeekBar.getProgress() >= 800) {
                    freeFallTime = 5;
                    freeFallTimeResult.setText(freeFallTime + " seconds");
                }
                if (heighSeekBar.getProgress() >= 1100) {
                    freeFallTime = 10;
                    freeFallTimeResult.setText(freeFallTime + " seconds");
                }
                if (heighSeekBar.getProgress() >= 1300) {
                    freeFallTime = 15;
                    freeFallTimeResult.setText(freeFallTime + " seconds");
                }
                if (heighSeekBar.getProgress() >= 1600) {
                    freeFallTime = 20;
                    freeFallTimeResult.setText(freeFallTime + " seconds");
                }
                if (heighSeekBar.getProgress() >= 2100) {
                    freeFallTime = 25;
                    freeFallTimeResult.setText(freeFallTime + " seconds");
                }
                if (heighSeekBar.getProgress() >= 2300) {
                    freeFallTime = 30;
                    freeFallTimeResult.setText(freeFallTime + " seconds");
                }
                if (heighSeekBar.getProgress() >= 2600) {
                    freeFallTime = 35;
                    freeFallTimeResult.setText(freeFallTime + " seconds");
                }
                if (heighSeekBar.getProgress() >= 2800) {
                    freeFallTime = 40;
                    freeFallTimeResult.setText(freeFallTime + " seconds");
                }
                if (heighSeekBar.getProgress() >= 3100) {
                    freeFallTime = 45;
                    freeFallTimeResult.setText(freeFallTime + " seconds");
                }
                if (heighSeekBar.getProgress() >= 3300) {
                    freeFallTime = 50;
                    freeFallTimeResult.setText(freeFallTime + " seconds");
                }
                if (heighSeekBar.getProgress() >= 3600) {
                    freeFallTime = 55;
                    freeFallTimeResult.setText(freeFallTime + " seconds");
                }
                if (heighSeekBar.getProgress() >= 4000) {
                    freeFallTime = 60;
                    freeFallTimeResult.setText(freeFallTime + " seconds");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        addJumpToFireStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // creating jump object and adding it to firestore to userJumpsLogBook -> jumps collection
                Jump jump = new Jump(
                        nextJumpPlusOne,
                        todayDateToString,
                        chooseJumpType.getSelectedItem().toString(),
                        setCanopy.getSelectedItem().toString(),
                        setPlane.getSelectedItem().toString(),
                        setDropzone.getSelectedItem().toString(),
                        userJumpValue,
                        freeFallTime);

                DocumentReference addNextJumpReference = fStore.collection("userJumpsLogBook")
                        .document(userId).collection("jumps").document();
                Map<String, Jump> addNextJump = new HashMap<>();
                addNextJump.put("nextJump", jump);
                addNextJumpReference.set(addNextJump).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "jump added");
                    }
                });

                // Updating allJumps number
                DocumentReference updateJumpNumber = fStore.collection("allJumps").document(userId);

                Map<String, Object> allJumps = new HashMap<>();
                allJumps.put("allJumps", nextJumpPlusOne);

                updateJumpNumber.set(allJumps).addOnSuccessListener(new OnSuccessListener<Void>() {
                    public static final String TAG = "TAG";

                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "All jumps updated");
                    }
                });
            }
        });

    }
}

