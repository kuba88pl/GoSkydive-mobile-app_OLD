package com.goskydive.jump;

import com.goskydive.gear.Canopy;
import com.goskydive.gear.Plane;

import java.util.Date;

public class Jump {

    private Date jumpDate;
    private int jumpNumber;
    private String jumpType;
    private Canopy usedCanopy;
    private Plane planeUsedToJump;
    private String airPortLocalisation;
    private int high;
    private int freeFallTime;

    public Jump(Date jumpDate, int jumpNumber, String jumpType,
                Canopy usedCanopy, Plane planeUsedToJump,
                String airPortLocalisation, int high, int freeFallTime) {
        this.jumpDate = jumpDate;
        this.jumpNumber = jumpNumber;
        this.jumpType = jumpType;
        this.usedCanopy = usedCanopy;
        this.planeUsedToJump = planeUsedToJump;
        this.airPortLocalisation = airPortLocalisation;
        this.high = high;
        this.freeFallTime = freeFallTime;
    }

    public Date getJumpDate() {
        return jumpDate;
    }

    public void setJumpDate(Date jumpDate) {
        this.jumpDate = jumpDate;
    }

    public int getJumpNumber() {
        return jumpNumber;
    }

    public void setJumpNumber(int jumpNumber) {
        this.jumpNumber = jumpNumber;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public Canopy getUsedCanopy() {
        return usedCanopy;
    }

    public void setUsedCanopy(Canopy usedCanopy) {
        this.usedCanopy = usedCanopy;
    }

    public Plane getPlaneUsedToJump() {
        return planeUsedToJump;
    }

    public void setPlaneUsedToJump(Plane planeUsedToJump) {
        this.planeUsedToJump = planeUsedToJump;
    }

    public String getAirPortLocalisation() {
        return airPortLocalisation;
    }

    public void setAirPortLocalisation(String airPortLocalisation) {
        this.airPortLocalisation = airPortLocalisation;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getFreeFallTime() {
        return freeFallTime;
    }

    public void setFreeFallTime(int freeFallTime) {
        this.freeFallTime = freeFallTime;
    }
}
