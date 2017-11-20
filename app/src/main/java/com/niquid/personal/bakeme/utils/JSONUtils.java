package com.niquid.personal.bakeme.utils;

import com.niquid.personal.bakeme.models.Ingredient;
import com.niquid.personal.bakeme.models.Recipe;
import com.niquid.personal.bakeme.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class JSONUtils {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String INGREDIENTS = "ingredients";
    private static final String QUANTITY = "quantity";
    private static final String MEASURE = "measure";
    private static final String INGREDIENT = "ingredient";
    private static final String STEPS = "steps";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String DESCRIPTION = "description";
    private static final String VIDEO = "videoURL";
    private static final String THUMBNAIL = "thumbnailURL";
    private static final String SERVINGS = "servings";
    private static final String IMAGE = "";

    public static List<Recipe> getRecipesFromJSON(String json) {


        if (json == null || json.length() == 0) {
            Timber.e("Json file is null");
            return null;
        }

        List<Recipe> recipes = new ArrayList<>();
        JSONArray recipesArray;
        try {
            recipesArray = new JSONArray(json);

            int recipesNum = recipesArray.length();

            for (int i=0; i<recipesNum; i++) {
                JSONObject rec = recipesArray.getJSONObject(i);

                int id = rec.getInt(ID);
                String name = rec.getString(NAME);
                List<Ingredient> ingredients = getIngredients(rec);
                List<Step> steps = getSteps(rec);
                int servings = rec.getInt(SERVINGS);
                String image;
                try {
                    image = rec.getString(IMAGE);
                }catch (Exception e){
                    image = "";
                }

                Recipe recipe = new Recipe(id, name, ingredients, steps, servings, image);
                recipes.add(recipe);
            }
        } catch (JSONException e) {
            Timber.e("Could't read json");
            e.printStackTrace();
        }
        return recipes;
    }

    private static List<Ingredient> getIngredients(JSONObject rec) throws JSONException {
        JSONArray ingredientsJSON = rec.getJSONArray(INGREDIENTS);
        List<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0; i < ingredientsJSON.length(); i++) {
            JSONObject ingred = ingredientsJSON.getJSONObject(i);

            int quantity = ingred.getInt(QUANTITY);
            String measure = ingred.getString(MEASURE);
            String ingredientName = ingred.getString(INGREDIENT);

            Ingredient ingredient = new Ingredient(quantity, measure, ingredientName);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private static List<Step> getSteps(JSONObject rec) throws JSONException {
        JSONArray stepsJSON = rec.getJSONArray(STEPS);
        List<Step> steps = new ArrayList<>();

        for(int i=0; i<stepsJSON.length(); i++){
            JSONObject stepJSON = stepsJSON.getJSONObject(i);

            int id = stepJSON.getInt(ID);
            String shortDesc = stepJSON.getString(SHORT_DESCRIPTION);
            String desc = stepJSON.getString(DESCRIPTION);
            String video = stepJSON.getString(VIDEO);
            String thumbnail = stepJSON.getString(THUMBNAIL);

            Step step = new Step(id, shortDesc, desc, video, thumbnail);
            steps.add(step);
        }

        return steps;
    }

}
