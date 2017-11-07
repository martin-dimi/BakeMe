package com.niquid.personal.bakeme.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.activity.RecipeAdapter;
import com.niquid.personal.bakeme.models.Recipe;

import java.util.List;

import timber.log.Timber;


public class RecipesListFragment extends Fragment {

    private List<Recipe> recipes;
    private RecipeAdapter adapter;
    private RecipeAdapter.RecipeOnClick recipeOnClick;

    public RecipesListFragment(){
        Timber.plant(new Timber.DebugTree());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        adapter = new RecipeAdapter(recipes);
        RecyclerView recipeList = rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);

        recipeList.setAdapter(adapter);
        recipeList.setLayoutManager(layoutManager);

        return rootView;
    }

    public void setData(List<Recipe> recipes, RecipeAdapter.RecipeOnClick recipeOnClick){
        this.recipes = recipes;
        this.recipeOnClick = recipeOnClick;
        updateUI();
    }

    private void updateUI(){
        adapter.updateAdaptor(recipes, recipeOnClick);
    }
}
