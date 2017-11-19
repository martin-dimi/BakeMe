package com.niquid.personal.bakeme.fragments;

import android.app.Fragment;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.activity.RecipeDetailActivity;
import com.niquid.personal.bakeme.activity.StepDetailActivity;
import com.niquid.personal.bakeme.adapters.ExpandableAdaptor;
import com.niquid.personal.bakeme.models.Recipe;
import com.niquid.personal.bakeme.widget.BakingShopWidget;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.niquid.personal.bakeme.utils.RecipeUtils.IS_TWO_PANED_KEY;
import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;
import static com.niquid.personal.bakeme.utils.RecipeUtils.STEP_KEY;

public class RecipeDetailFragment extends Fragment{

    @BindView(R.id.coordinator) CoordinatorLayout coordinatorLayout;

    private Recipe recipe;
    private boolean isTwoPaned;
    private ExpandableAdaptor adaptor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        View buttonsView = inflater.inflate(R.layout.step_cook_buttons, container, false);
        ButterKnife.bind(this, rootView);

        if(savedInstanceState != null){
            recipe = Parcels.unwrap(savedInstanceState.getParcelable(RECIPE_KEY));
            isTwoPaned = savedInstanceState.getBoolean(IS_TWO_PANED_KEY);
        }else isTwoPaned = false;

        ExpandableListView expandableList = rootView.findViewById(R.id.expandable_ingredients);

        //Setting the expandable list
        adaptor = new ExpandableAdaptor(recipe, getContext());
        expandableList.setAdapter(adaptor);

        //Setting the cooking button
        buttonsView.findViewById(R.id.start_cooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCooking();
            }
        });

        buttonsView.findViewById(R.id.add_shopping_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
        expandableList.addFooterView(buttonsView);

        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        if(recipe != null && !isTwoPaned) {
            ((RecipeDetailActivity) getActivity()).setTitle(recipe.getName());
        }
        if(adaptor != null){
            adaptor.changeRecipe(recipe);
        }
    }

    public void setTwoPaned(boolean twoPaned){
        this.isTwoPaned = twoPaned;
    }

    private void startCooking(){
        Intent intent = new Intent(getContext(), StepDetailActivity.class);
        intent.putExtra(STEP_KEY, Parcels.wrap(recipe.getSteps()));
        startActivity(intent);
    }

    private void addToCart(){
        AppWidgetManager manager = AppWidgetManager.getInstance(getContext());
        int[] appWidgetIds = manager.getAppWidgetIds(new ComponentName(getContext(), BakingShopWidget.class));
        BakingShopWidget.updateBakingWidgets(getContext(), manager, appWidgetIds, recipe.getIngredients());

        //Toast.makeText(getContext(), R.string.widget_toast, Toast.LENGTH_SHORT).show();
        Snackbar.make(coordinatorLayout, R.string.widget_toast, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_KEY, Parcels.wrap(recipe));
        outState.putBoolean(IS_TWO_PANED_KEY, isTwoPaned);
    }
}
