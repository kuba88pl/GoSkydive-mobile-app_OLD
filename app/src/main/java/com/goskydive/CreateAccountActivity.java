package com.goskydive;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class CreateAccountActivity extends AppCompatActivity {

    String username;
    String email;
    String password;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        username = String.valueOf(findViewById(R.id.username));
        email = String.valueOf(findViewById(R.id.email));
    }
}