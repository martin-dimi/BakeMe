package com.niquid.personal.bakeme.utils;


import com.niquid.personal.bakeme.models.Recipe;
import timber.log.Timber;

public class RecipeUtils {

    private static final int THRESHOLD_MEDIUM = 18;
    private static final int THRESHOLD_HARD = 22;

    enum Level {
        Easy,
        Medium,
        Hard
    }

    public static Level getDifficulty(Recipe recipe){
        if(recipe == null){
            Timber.w("Couldn't get difficulty. Recipe is null");
            return null;
        }
        int ingredients = recipe.getIngredients().size();
        int steps       = recipe.getSteps().size();
        int difficulty  = ingredients + steps;

        if(difficulty > THRESHOLD_HARD) return Level.Hard;
        else if(difficulty > THRESHOLD_MEDIUM) return Level.Medium;
        else return Level.Easy;
    }


}
