package com.goskydive.plane;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Plane {

    public static final String TAG = "TAG";
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String userId =  fAuth.getCurrentUser().getUid();

    private String manufacturer;
    private String model;

    public Plane(String manufacturer, String model) {
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public Plane() {
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    //this method is responsible for storing plane map (manufacturer and model) to firestore.

    public void savePlanetoFirestore(String manufacturer, String model) {
        this.manufacturer = manufacturer;
        this.model = model;
        Map<String, String> planeToSave = new HashMap<>();
        planeToSave.put(manufacturer, model);

        DocumentReference documentReference = fStore.collection("planes").document(userId);
        documentReference.set(planeToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Success");
            }
        });



    }
}
