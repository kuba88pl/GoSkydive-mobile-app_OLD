package com.goskydive.introduction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.goskydive.introduction.*;

import com.goskydive.R;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class Step3 extends AppCompatActivity {

    Button nextButton;
    EditText licenceTypeInput;
    Switch isStudentSwitch;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    boolean isStudent = false;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step3);

        nextButton = findViewById(R.id.next_button);
        licenceTypeInput = findViewById(R.id.licence_type_input_text);
        isStudentSwitch = findViewById(R.id.is_student_switch);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (licenceTypeInput.getText().toString().isEmpty()) {
                    Toast.makeText(Step3.this, "Specify licence type", Toast.LENGTH_SHORT).show();
                } else {
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("licenceType").document(userID);
                    Map<String, Object> licenceType = new HashMap<>();

                    licenceType.put("Licence Type", licenceTypeInput.getText().toString());
                    documentReference.set(licenceType).addOnSuccessListener(new OnSuccessListener<Void>() {

                        public static final String TAG = "TAG";

                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "Licence was stored!");
                        }
                    });
//TODO
//                    userID = fAuth.getCurrentUser().getUid();
//                    DocumentReference documentReference1 = fStore.collection("isStudent").document(userID);
//                    Map<String, Boolean> isStudentState = new HashMap<>();
//                    isStudent = isStudentSwitch.isChecked();
//                    isStudentState.put("isStudent", isStudent);
//                    documentReference1.set(isStudent).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Log.d("TAG", "Student update");
//                            }
//                        });



                }

                startActivity(new Intent(getApplicationContext(), Step4.class));

            }
        });

    }
}