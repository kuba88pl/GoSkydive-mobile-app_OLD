//package com.goskydive.jump;
//
//import androidx.annotation.Nullable;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.goskydive.gear.Canopy;
//import com.goskydive.plane.Plane;
//
//public class Jump {
//    private int jumpNumber;
//    private Date date;
//    private Enum jumpType;
//    private Canopy usedCanopy;
//    private Map<String, String> usedPlane = new HashMap<>();
//    private String city;
//    private int jumpHeight;
//    private int freeFallDelay;
//
//    public Jump(int jumpNumber, Date date, Enum jumpType, Canopy usedCanopy, Map<String, String> usedPlane, String city, int jumpHeight, int freeFallDelay) {
//        this.jumpNumber = jumpNumber;
//        this.date = date;
//        this.jumpType = jumpType;
//        this.usedCanopy = usedCanopy;
//        Plane plane = new Plane();
//        //TODO type plane form database in addJumpActivity;
//        this.usedPlane = plane.savePlanetoFirestore();
//        this.city = city;
//        this.jumpHeight = jumpHeight;
//        this.freeFallDelay = freeFallDelay;
//    }
//
//
//    //    public Jump() {
////    }
//
//    public int getJumpNumber() {
//        return jumpNumber;
//    }
//
//    public void setJumpNumber(int jumpNumber) {
//        this.jumpNumber = jumpNumber;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public Enum getJumpType() {
//        return jumpType;
//    }
//
//    public void setJumpType(Enum jumpType) {
//        this.jumpType = jumpType;
//    }
//
//    public Canopy getUsedCanopy() {
//        return usedCanopy;
//    }
//
//    public void setUsedCanopy(Canopy usedCanopy) {
//        this.usedCanopy = usedCanopy;
//    }
//
//    public Map<String, String> getUsedPlane() {
//        return usedPlane;
//    }
//
//    public void setUsedPlane(Map<String, String> usedPlane) {
//        this.usedPlane = usedPlane;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public int getJumpHeight() {
//        return jumpHeight;
//    }
//
//    public void setJumpHeight(int jumpHeight) {
//        this.jumpHeight = jumpHeight;
//    }
//
//    public int getFreeFallDelay() {
//        return freeFallDelay;
//    }
//
//    public void setFreeFallDelay(int freeFallDelay) {
//        this.freeFallDelay = freeFallDelay;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Jump jump = (Jump) o;
//        return jumpNumber == jump.jumpNumber && jumpHeight == jump.jumpHeight && freeFallDelay
//                == jump.freeFallDelay && Objects.equals(date, jump.date) && Objects.equals(jumpType, jump.jumpType)
//                && Objects.equals(usedCanopy, jump.usedCanopy) && Objects.equals(usedPlane, jump.usedPlane) && Objects.equals(city, jump.city);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(jumpNumber, date, jumpType, usedCanopy, usedPlane, city, jumpHeight, freeFallDelay);
//    }
//}
