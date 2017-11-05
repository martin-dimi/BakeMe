package com.niquid.personal.bakeme.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.activity.RecipeAdapter;
import com.niquid.personal.bakeme.backgroud.FetchRecipes;
import com.niquid.personal.bakeme.models.Recipe;
import com.niquid.personal.bakeme.utils.JSONUtils;

import java.util.List;

import timber.log.Timber;


public class RecipesListFragment extends Fragment {

    private static final String RECIPES_JSON = "recipes";

    private List<Recipe> recipes;
    private RecipeAdapter adapter;

    public RecipesListFragment(){
        Timber.plant(new Timber.DebugTree());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        adapter = new RecipeAdapter(recipes);
        getRecipes();
        RecyclerView recipeList = rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);

        recipeList.setAdapter(adapter);
        recipeList.setLayoutManager(layoutManager);

        return rootView;
    }


    private void getRecipes() {
        FetchRecipes fetchRecipes = new FetchRecipes(getContext(), getLoaderManager(), new OnRecipesTaskFinished());
        fetchRecipes.fetchRecipes();
    }

    public class OnRecipesTaskFinished implements FetchRecipes.OnTaskFinished{

        @Override
        public void onRecipesFetched(String json, Context context) {
            System.out.println("FETCHED RECIPES-----------");
            recipes = JSONUtils.getRecipesFromJSON(json);
            adapter.swapRecipes(recipes);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            preferences.edit().putString(RECIPES_JSON, json).apply();
        }
    }
}
