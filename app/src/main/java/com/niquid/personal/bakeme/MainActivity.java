package com.niquid.personal.bakeme;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.niquid.personal.bakeme.backgroud.FetchRecipes;
import com.niquid.personal.bakeme.models.Recipe;

import com.niquid.personal.bakeme.utils.JSONUtils;
import timber.log.Timber;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String RECIPES_JSON = "recipes";

    private String recipesJson;
    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());



        getRecipes();
    }



    private void getRecipes() {
        FetchRecipes fetchRecipes = new FetchRecipes(this, getSupportLoaderManager(), new OnRecipesTaskFinished());
        fetchRecipes.fetchRecipes();
        recipes = JSONUtils.getRecipesFromJSON(recipesJson);
    }

    public class OnRecipesTaskFinished implements FetchRecipes.OnTaskFinished{

        @Override
        public void onRecipesFetched(String json, Context context) {
            recipesJson = json;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            preferences.edit().putString(RECIPES_JSON, recipesJson).apply();
        }
    }
}
