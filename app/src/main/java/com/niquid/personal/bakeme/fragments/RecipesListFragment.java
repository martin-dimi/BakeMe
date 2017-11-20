package com.niquid.personal.bakeme.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.activity.RecipeDetailActivity;
import com.niquid.personal.bakeme.adapters.RecipeAdapter;
import com.niquid.personal.bakeme.models.Recipe;

import org.parceler.Parcels;

import java.util.List;

import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPES_KEY;
import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;


public class RecipesListFragment extends Fragment implements RecipeAdapter.RecipeOnClick{

    private List<Recipe> mRecipes;
    private RecipeAdapter mAdaptor;

    public RecipesListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        if(savedInstanceState != null){
            mRecipes = Parcels.unwrap(savedInstanceState.getParcelable(RECIPES_KEY));
        }

        mAdaptor = new RecipeAdapter(mRecipes, this);
        RecyclerView recipeList = rootView.findViewById(R.id.recipe_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recipeList.setAdapter(mAdaptor);
        recipeList.setLayoutManager(layoutManager);

        return rootView;
    }

    public void setRecipes(List<Recipe> mRecipes){
        this.mRecipes = mRecipes;
        mAdaptor.updateAdaptor(mRecipes);
    }

    @Override
    public void onClick(Recipe recipe) {
            Intent changeToRecipeActivity = new Intent(getContext(), RecipeDetailActivity.class);
            changeToRecipeActivity.putExtra(RECIPE_KEY, Parcels.wrap(recipe));
            startActivity(changeToRecipeActivity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPES_KEY, Parcels.wrap(mRecipes));
    }
}
