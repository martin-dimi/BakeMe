package com.niquid.personal.bakeme.models;

import java.util.List;


public class Recipe {

    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }
}
