package com.niquid.personal.bakeme.models;


import org.parceler.Parcel;

import java.io.Serializable;

@Parcel
public class Ingredient implements Serializable {

    private int quantity;
    private String measure;
    private String ingredient;

    public Ingredient() {   //For parcel
    }

    public Ingredient(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
