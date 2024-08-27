package com.goskydive.gear;

public class Canopy {
    private String manufacturer;
    private String model;
    private int size;

    public Canopy(String manufacturer, String model, int size) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.size = size;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
