package com.goskydive.jump;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Objects;

import com.goskydive.gear.Canopy;
import com.goskydive.plane.Plane;

public class Jump {
    private long jumpNumber;
    private String date;
    private String jumpType;
    private String usedCanopy;
    private String usedPlane;
    private String dropzone;
    private long jumpHeight;
    public long freeFallDelay;

    public Jump(long jumpNumber, String date, String jumpType, String usedCanopy, String usedPlane, String dropzone, long jumpHeight, long freeFallDelay) {
        this.jumpNumber = jumpNumber;
        this.date = date;
        this.jumpType = jumpType;
        this.usedCanopy = usedCanopy;
        Plane plane = new Plane();
        //TODO type plane form database in addJumpActivity;
        this.usedPlane = usedPlane;
        this.dropzone = dropzone;
        this.jumpHeight = jumpHeight;
        this.freeFallDelay = freeFallDelay;
    }

    public long getJumpNumber() {
        return jumpNumber;
    }

    public void setJumpNumber(int jumpNumber) {
        this.jumpNumber = jumpNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getUsedCanopy() {
        return usedCanopy;
    }

    public void setUsedCanopy(String usedCanopy) {
        this.usedCanopy = usedCanopy;
    }

    public String getUsedPlane() {
        return usedPlane;
    }

    public void setUsedPlane(String usedPlane) {
        this.usedPlane = usedPlane;
    }

    public String getDropzone() {
        return dropzone;
    }

    public void setDropzone(String dropzone) {
        this.dropzone = dropzone;
    }

    public long getJumpHeight() {
        return jumpHeight;
    }

    public void setJumpHeight(long jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    public long getFreeFallDelay() {
        return freeFallDelay;
    }

    public void setFreeFallDelay(long freeFallDelay) {
        this.freeFallDelay = freeFallDelay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jump jump = (Jump) o;
        return jumpNumber == jump.jumpNumber && jumpHeight == jump.jumpHeight && freeFallDelay
                == jump.freeFallDelay && Objects.equals(date, jump.date) && Objects.equals(jumpType, jump.jumpType)
                && Objects.equals(usedCanopy, jump.usedCanopy) && Objects.equals(usedPlane, jump.usedPlane) && Objects.equals(dropzone, jump.dropzone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jumpNumber, date, jumpType, usedCanopy, usedPlane, dropzone, jumpHeight, freeFallDelay);
    }
}
