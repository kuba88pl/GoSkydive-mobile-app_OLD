package com.goskydive.logbook;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.goskydive.R;
import com.goskydive.jump.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddJumpActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView jumpNumber, editTextDate, seekBarResult;
    Spinner chooseJumpType, setPlane;
    SeekBar highSeekBar;
    ImageButton addJumpToFireStore;


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    long nextJumpPlusOne;
    long userJumpValue;

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
        highSeekBar = findViewById(R.id.heigh_seekbar);
        chooseJumpType = findViewById(R.id.spinner_jump_type);
        setPlane = findViewById(R.id.set_plane_spinner);
        seekBarResult = findViewById(R.id.seekbar_result);
        addJumpToFireStore = findViewById(R.id.add_jump);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        editTextDate.setText(todayDateToString);

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

        //seekbar high

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{R.color.red_seek_bar, R.color.light_red_seek_bar, R.color.yellow_seek_bar, R.color.light_yellow_seek_bar,
                        R.color.light_green_seek_bar, R.color.green_seek_bar,
                });
        gradientDrawable.setCornerRadius(10);
        highSeekBar.setProgressDrawable(gradientDrawable);


        highSeekBar.setMax(4500);
        highSeekBar.setMin(800);
        highSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                seekBarResult.setText(progress + " meters AGL");

                userJumpValue = Long.valueOf(progress);
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
                        "Crossfire 139",
                        setPlane.getSelectedItem().toString(),
                        "Gliwice",
                        userJumpValue,
                        50);


                DocumentReference addNextJumpReference = fStore.collection("userJumpsLogBook").document(userId).collection("jumps").document();
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

