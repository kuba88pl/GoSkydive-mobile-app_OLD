package com.goskydive.introduction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.goskydive.Register.*;

import com.goskydive.R;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Step3 extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button nextButton;
    Spinner chooseLicence;
    Switch isStudentSwitch;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step3);

        nextButton = findViewById(R.id.next_button);
        chooseLicence = findViewById(R.id.spinner_licence_type);
        isStudentSwitch = findViewById(R.id.is_student_switch);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fStore.collection("licenceType").get().addOnSuccessListener(result -> {
            ArrayList<String> items = new ArrayList<>();
            for (DocumentSnapshot snapshot : result) {
                String item = snapshot.getString("licence");
                items.add(item);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chooseLicence.setAdapter(adapter);

        });


        Map<String, Boolean> isStudentState = new HashMap<>();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chooseLicence.getSelectedItem().toString().isEmpty()) {
                    Toast.makeText(Step3.this, "Specify licence type", Toast.LENGTH_SHORT).show();
                } else {
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("userLicenceType").document(userID);
                    Map<String, Object> licenceType = new HashMap<>();

                    licenceType.put("licence", (chooseLicence.getSelectedItem().toString()));

                    documentReference.set(licenceType).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "Licence type updated!");
                        }
                    });

                    DocumentReference isStudentStateReference = fStore.collection("isStudent").document(userID);
                    isStudentState.put("isStudent", isStudentSwitch.isChecked());
                    isStudentStateReference.set(isStudentState).addOnSuccessListener(new OnSuccessListener<Void>() {
                        public static final String TAG = "TAG";

                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "Student state updated");
                        }
                    });

                }

                startActivity(new Intent(getApplicationContext(), Step4.class));

            }
        });

    }
}