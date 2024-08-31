package com.goskydive.logbook;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.goskydive.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddJumpActivity extends AppCompatActivity {

    TextView jumpNumber, editTextDate, seekBarResult;
    Spinner chooseJumpType, setPlane;
    SeekBar highSeekBar;


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    long nextJumpPlusOne;
    long userJumpValue;

    Date date = new Date();
    SimpleDateFormat todayDate = new SimpleDateFormat("dd.MM.yyyy");
    String todayDateToString = todayDate.format(date);

    int[] seekBarHighValues = {800, 1000, 1200, 1500, 2000, 2500, 3000, 3500, 4000, 4500};

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
                //progress trzeba zapisac do drugiej zmiennej ktora bedzie przekazana do bazy
                userJumpValue = Long.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });


    }
}

