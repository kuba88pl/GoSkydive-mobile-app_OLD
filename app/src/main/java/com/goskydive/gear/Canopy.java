package com.goskydive.gear;

import java.util.LinkedList;

public class Canopy extends SkydiveGear {

    private int canopySize;

    public Canopy(String producerName, String modelName, int productionYear, int canopySize) {
        super(producerName, modelName, productionYear);
        this.canopySize = canopySize;
    }

    public int getCanopySize() {
        return canopySize;
    }

    public void setCanopySize(int canopySize) {
        this.canopySize = canopySize;
    }

    public Canopy addCanopy(String producerName, String modelName, int canopySize, int productionYear) {
        super.setProducerName(producerName);
        super.setModelName(modelName);
        this.canopySize = canopySize;
        super.setProductionYear(productionYear);
        return this;
    }

}
