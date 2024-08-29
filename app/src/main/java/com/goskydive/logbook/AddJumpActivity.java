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

    TextView jumpNumber, editTextDate;
    Spinner chooseJumpType;
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
        setContentView(R.layout.activity_add_jump_test);

        jumpNumber = findViewById(R.id.jump_number_number);
        editTextDate = findViewById(R.id.editTextDate);
        heigh = findViewById(R.id.heigh_seekbar);


        chooseJumpType = findViewById(R.id.spinner_jump_type);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        editTextDate.setText(todayDateToString);

        DocumentReference totalJumpsReference = fStore.collection("allJumps").document(userId);
        totalJumpsReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot jumpNumberReferenceSnapshot, @Nullable FirebaseFirestoreException error) {
                long jumpNo = jumpNumberReferenceSnapshot.getLong("allJumps") + 1; // because this is the next jump
                nextJumpPlusOne = jumpNo;

//                Jump jump = new Jump(numer, data, typ, spadochron, samolot, dropzone, wysokosc, czas spadania)
//                    Jump jump = new Jump(jumpNumber,
//                            todayDate,
//                            chooseJumpType.getBaseline(),
//                            Cano
//                            )

                jumpNumber.setText(String.valueOf(jumpNo));

            }
        });


        //spinner
        chooseJumpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String item = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> jumpStyles = new ArrayList<>();
        jumpStyles.add("SOLO");
        jumpStyles.add("RW");
        jumpStyles.add("FREE");
        jumpStyles.add("CRW");
        jumpStyles.add("TANDEM");

        ArrayAdapter<String> jumpStylesSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jumpStyles);
        chooseJumpType.setAdapter(jumpStylesSpinnerAdapter);
    }
}