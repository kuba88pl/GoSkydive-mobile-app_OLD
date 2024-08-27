package com.goskydive.gear;

import java.util.ArrayList;
import java.util.Date;

public class Aad {

    private String model;
    private String subModel;
    private Date productionDate;

    public Aad(String model, String subModel, Date productionDate) {
        this.model = model;
        this.subModel = subModel;
        this.productionDate = productionDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSubModel() {
        return subModel;
    }

    public void setSubModel(String subModel) {
        this.subModel = subModel;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

}
