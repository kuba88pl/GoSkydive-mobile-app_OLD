package com.goskydive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText username, email, password;
    ImageButton signup;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        fAuth = FirebaseAuth.getInstance();



    }
}