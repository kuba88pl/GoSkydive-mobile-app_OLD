package com.goskydive.introduction;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.goskydive.R;

import java.util.HashMap;
import java.util.Map;

public class Step2 extends AppCompatActivity {

    public static final String TAG1 = "TAG";
    EditText howManyJumps;
    Button nextButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String howManyJumpsToInteger;
    int zeroJumps = 0;
    Integer howManyUserJumps = zeroJumps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step2);

        howManyJumps = findViewById(R.id.how_many_jumps_edit_text);
        nextButton = findViewById(R.id.next_button_step2);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        howManyJumps.setText(String.valueOf(0));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (howManyJumps.getText().toString().isEmpty()) {
                    Toast.makeText(Step2.this, "Type number of jumps", Toast.LENGTH_SHORT).show();
                } else {

                    howManyJumpsToInteger = howManyJumps.getText().toString();
                    howManyUserJumps = Integer.parseInt(howManyJumpsToInteger);

                    //storing data to firestore
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("allJumps").document(userID);

                    Map<String, Object> allJumps = new HashMap<>();
                    allJumps.put("allJumps", howManyUserJumps);


                    documentReference.set(allJumps).addOnSuccessListener(new OnSuccessListener<Void>() {
                        public static final String TAG = "TAG";

                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "Jumps added");
                        }
                    });

                }
                //next activity link
                startActivity(new Intent(getApplicationContext(), Step3.class));
            }

        });
    }
}

