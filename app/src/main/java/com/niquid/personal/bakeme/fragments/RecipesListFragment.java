package com.niquid.personal.bakeme.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
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

import timber.log.Timber;

import static com.niquid.personal.bakeme.utils.RecipeUtils.IS_TWO_PANED_KEY;
import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPES_KEY;
import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;


public class RecipesListFragment extends Fragment implements RecipeAdapter.RecipeOnClick{

    private List<Recipe> recipes;
    private RecipeAdapter adapter;
    private boolean isTwoPane;
    private CommunicateToActivity communicateToActivity;

    public interface CommunicateToActivity{
        void showDetailList();
    }

    public RecipesListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        if(savedInstanceState != null){
            recipes = Parcels.unwrap(savedInstanceState.getParcelable(RECIPES_KEY));
            isTwoPane = savedInstanceState.getBoolean(IS_TWO_PANED_KEY);
        }

        adapter = new RecipeAdapter(recipes, this);
        RecyclerView recipeList = rootView.findViewById(R.id.recipe_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recipeList.setAdapter(adapter);
        recipeList.setLayoutManager(layoutManager);

        return rootView;
    }

    public void setData(List<Recipe> recipes, boolean isTwoPane, CommunicateToActivity communicateToActivity){
        this.recipes = recipes;
        this.isTwoPane = isTwoPane;
        this.communicateToActivity = communicateToActivity;
        updateUI();
    }

    private void updateUI(){
        adapter.updateAdaptor(recipes);
    }

    @Override
    public void onClick(Recipe recipe) {
        if (!isTwoPane) {
            Intent changeToRecipeActivity = new Intent(getContext(), RecipeDetailActivity.class);
            changeToRecipeActivity.putExtra(RECIPE_KEY, Parcels.wrap(recipe));
            startActivity(changeToRecipeActivity);
        } else {
            communicateToActivity.showDetailList();
            FragmentManager manager = getFragmentManager();
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setTwoPaned(isTwoPane);
            recipeDetailFragment.setRecipe(recipe);
            manager.beginTransaction()
                    .replace(R.id.fragment_recipe_detail, recipeDetailFragment)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPES_KEY, Parcels.wrap(recipes));
        outState.putBoolean(IS_TWO_PANED_KEY, isTwoPane);
    }
}
