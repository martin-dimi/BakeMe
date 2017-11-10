package com.niquid.personal.bakeme.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.activity.StepDetailActivity;
import com.niquid.personal.bakeme.adapters.ExpandableAdaptor;
import com.niquid.personal.bakeme.models.Recipe;

import org.parceler.Parcels;

import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;
import static com.niquid.personal.bakeme.utils.RecipeUtils.STEP_KEY;

public class RecipeDetailFragment extends Fragment{

    private Recipe recipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        View cookView = inflater.inflate(R.layout.step_cook_button, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ExpandableListView expandableList = rootView.findViewById(R.id.expandable_ingredients);


        if(recipe!=null) {
            //Setting the toolbar
            toolbar.setTitle(recipe.getName());

            //Setting the expandable list
            ExpandableAdaptor expandableAdaptor = new ExpandableAdaptor(recipe, getContext());
            expandableList.setAdapter(expandableAdaptor);

            //Setting the cooking button
            cookView.findViewById(R.id.cook).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startCooking();
                }
            });
            expandableList.addFooterView(cookView);

        }

        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void startCooking(){
        Intent intent = new Intent(getContext(), StepDetailActivity.class);
        intent.putExtra(STEP_KEY, Parcels.wrap(recipe.getSteps()));
        startActivity(intent);
    }
}
