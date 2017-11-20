package com.niquid.personal.bakeme.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.backgroud.FetchRecipeTask;
import com.niquid.personal.bakeme.fragments.RecipesListFragment;
import com.niquid.personal.bakeme.models.Recipe;
import com.niquid.personal.bakeme.utils.JSONUtils;

import java.util.List;

import timber.log.Timber;

import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());

        if(savedInstanceState == null) {
            getRecipes();
        }
    }


    private void getRecipes() {
        FetchRecipeTask fetchRecipeTask = new FetchRecipeTask(this, getLoaderManager(), new OnRecipesTaskFinished());
        fetchRecipeTask.fetchRecipes();
    }



    public class OnRecipesTaskFinished implements FetchRecipeTask.OnTaskFinished{

        @Override
        public void onRecipesFetched(String json, Context context) {
            RecipesListFragment recipesListFragment = (RecipesListFragment) getFragmentManager().findFragmentById(R.id.fragment_recipe_list);

            //Setting the recipe list in the fragment
            List<Recipe> recipes = JSONUtils.getRecipesFromJSON(json);
            recipesListFragment.setRecipes(recipes);

            //Store the data for later use
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putString(RECIPE_KEY, json);
            editor.apply();
        }
    }

}
