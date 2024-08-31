package com.goskydive.logbook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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


import com.google.firebase.firestore.QueryDocumentSnapshot;
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
        setContentView(R.layout.activity_add_jump_test);

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

        //spinner planeType
        setPlane.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //TODO make loading data to spinner form firestore
//                String planeItem = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> temporaryPlanesList = new ArrayList<>();
        temporaryPlanesList.add("Cessna 182");
        temporaryPlanesList.add("Cessna 208 Grand Caravan");
        temporaryPlanesList.add("de Havilland Canada DHC-6 Twin Otter");
        temporaryPlanesList.add("Antonov AN-2");
        temporaryPlanesList.add("Technoavia SM92 Finist");
        temporaryPlanesList.add("Beechcraft King Air");
        temporaryPlanesList.add("Short SC.7 Skyvan");
        temporaryPlanesList.add("PAC P-750 XSTOL");
        temporaryPlanesList.add("CASA C-212");

        ArrayAdapter<String> planeModelsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, temporaryPlanesList);
        setPlane.setAdapter(planeModelsAdapter);


    }
}