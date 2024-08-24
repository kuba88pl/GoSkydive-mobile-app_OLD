package com.goskydive;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText username, email, password;
    ImageButton signUpButton;
    TextView alreadyRegistered;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.googlesignupbutton), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUpButton = findViewById(R.id.googlesignupbutton);
        alreadyRegistered = findViewById(R.id.allready_registred_text);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Checking is user sign in

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtext = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                String usernameText = username.getText().toString();

                //email and password validation

                if (TextUtils.isEmpty(emailtext)) {
                    email.setError("Email is reguired.");
                    return;
                }

                if (TextUtils.isEmpty(passwordText) || passwordText.length() < 6) {
                    password.setError("Password should contains min. 6 or more characters long!");
                    return;
                }

                // user registration in firebase

                fAuth.createUserWithEmailAndPassword(emailtext, passwordText).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "User created!", Toast.LENGTH_SHORT).show();

                        //storing user data to firestore
                        userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("userName", username);
                        user.put("email", email);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {

                            public static final String TAG = "TAG";

                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSucces user profile is created for " + userID);

                            }
                        });
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(Register.this, "Error! Try again!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        alreadyRegistered.setOnClickListener(view ->
        {
            startActivity(new Intent(getApplicationContext(), Login.class));
        });
    }
}