package com.goskydive.introduction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.goskydive.R;
import  com.goskydive.introduction.*;

public class Step5 extends AppCompatActivity {

    Button nextButton;
    EditText dropzone;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step5);

        nextButton = findViewById(R.id.next_button);
        dropzone = findViewById(R.id.choose_dropzone_edit_text);

        //TODO
        if(dropzone.getText().length() > 4){
            dropzone.setText("Only 4 characters!");
            Toast.makeText(this, "Type only 4 characters!", Toast.LENGTH_SHORT).show();
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Step6.class));

            }
        });
    }
}