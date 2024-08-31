package com.goskydive.logbook;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    TextView jumpNumber, editTextDate;
    Spinner chooseJumpType, setPlane;
    ListView jumpStyleListView;
    SeekBar heigh;


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    long nextJumpPlusOne;

    Date date = new Date();
    SimpleDateFormat todayDate = new SimpleDateFormat("dd.MM.yyyy");
    String todayDateToString = todayDate.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_jump);

        jumpNumber = findViewById(R.id.jump_number_number);
        editTextDate = findViewById(R.id.editTextDate);
        heigh = findViewById(R.id.heigh_seekbar);
        chooseJumpType = findViewById(R.id.spinner_jump_type);
        setPlane = findViewById(R.id.set_plane_spinner);

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

        //spinner jumpType

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

    }
}