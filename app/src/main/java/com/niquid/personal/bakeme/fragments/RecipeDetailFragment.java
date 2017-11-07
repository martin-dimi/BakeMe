package com.niquid.personal.bakeme.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.activity.IngredientAdapter;
import com.niquid.personal.bakeme.activity.StepAdapter;
import com.niquid.personal.bakeme.models.Recipe;

import org.parceler.Parcels;

import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;

public class RecipeDetailFragment extends Fragment {

    private View rootview;
    private Recipe recipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        return rootview;
    }

    private void setUI(){
        //Setting the toolbar
        Toolbar toolbar = rootview.findViewById(R.id.toolbar);
        toolbar.setTitle(recipe.getName());

        //Setting the ingredients
        IngredientAdapter ingredientAdapter = new IngredientAdapter(recipe.getIngredients(), getContext());
        ExpandableListView ingredientsList = rootview.findViewById(R.id.expandable_ingredients);
        ingredientsList.setAdapter(ingredientAdapter);

        //Setting the steps
        StepAdapter stepAdapter = new StepAdapter(recipe.getSteps(), getContext());
        ListView stepsList = rootview.findViewById(R.id.steps_list);
        stepsList.setAdapter(stepAdapter);

    }

    @Override
    public void setArguments(Bundle args) {
        recipe =  Parcels.unwrap(args.getParcelable(RECIPE_KEY));
        setUI();
    }

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }
}
