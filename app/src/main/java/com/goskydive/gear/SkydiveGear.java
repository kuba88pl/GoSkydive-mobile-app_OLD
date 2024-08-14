package com.goskydive.gear;

public class SkydiveGear {
    private String producerName;
    private String modelName;
//    private String size;
    private int productionYear;

    public SkydiveGear(String producerName, String modelName, int productionYear) {
        this.producerName = producerName;
        this.modelName = modelName;
//        this.size = size;
        this.productionYear = productionYear;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }


    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }
}
