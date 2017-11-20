package com.niquid.personal.bakeme.utils;


import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.models.Recipe;
import timber.log.Timber;

public class RecipeUtils {

    public static final String RECIPE_KEY = "recipe";
    public static final String RECIPES_KEY = "recipes";
    public static final String STEP_KEY = "step";
    public static final String STEP_VIDEO_POSITION = "video_pos";


    private static final int THRESHOLD_MEDIUM = 18;
    private static final int THRESHOLD_HARD = 22;


    public static int getDifficulty(Recipe recipe){
        if(recipe == null){
            Timber.w("Couldn't get difficulty. Recipe is null");
            return R.drawable.easy;
        }
        int ingredients = recipe.getIngredients().size();
        int steps       = recipe.getSteps().size();
        int difficulty  = ingredients + steps;

        if(difficulty > THRESHOLD_HARD) return R.drawable.hard;
        else if(difficulty > THRESHOLD_MEDIUM) return R.drawable.medium;
        else return R.drawable.easy;
    }

    public static boolean hasImage(Recipe recipe){
        return !recipe.getImage().equals("");
    }


}
