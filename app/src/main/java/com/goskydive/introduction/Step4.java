package com.goskydive.introduction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.goskydive.R;
import com.goskydive.introduction.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Step4 extends AppCompatActivity {

    Button nextButton;
    Switch solo, rw, free, crw, tandem;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step4);

        nextButton = findViewById(R.id.next_button);

        solo = findViewById(R.id.solo_type_switch);
        rw = findViewById(R.id.relative_type_switch);
        free = findViewById(R.id.free_type_switch);
        crw = findViewById(R.id.crw_type_switch);
        tandem = findViewById(R.id.tandem_type_switch);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Map<String, Boolean> preferredTypeOfJumpMap = new HashMap<>();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                preferredTypeOfJumpMap.put("solo", solo.isChecked());
                preferredTypeOfJumpMap.put("rw", rw.isChecked());
                preferredTypeOfJumpMap.put("free", free.isChecked());
                preferredTypeOfJumpMap.put("crw", crw.isChecked());
                preferredTypeOfJumpMap.put("tandem", tandem.isChecked());


                userID = fAuth.getCurrentUser().getUid();
                DocumentReference prefReference = fStore.collection("preferredJumpType").document(userID);
                prefReference.set(preferredTypeOfJumpMap).addOnSuccessListener(new OnSuccessListener<Void>() {

                    public static final String TAG = "TAG";

                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "map added");
                    }
                });

                startActivity(new Intent(getApplicationContext(), Step5.class));

            }
        });

    }
}



