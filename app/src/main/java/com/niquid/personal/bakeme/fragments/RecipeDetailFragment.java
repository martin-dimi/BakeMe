package com.niquid.personal.bakeme.fragments;

import android.app.Fragment;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.activity.RecipeDetailActivity;
import com.niquid.personal.bakeme.adapters.IngredientAdapter;
import com.niquid.personal.bakeme.adapters.StepAdaptor;
import com.niquid.personal.bakeme.models.Recipe;
import com.niquid.personal.bakeme.widget.BakingShopWidget;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;

public class RecipeDetailFragment extends Fragment{

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    private OnItemSelectedListener onItemSelectedListener;

    private Recipe recipe;
    private StepAdaptor stepAdaptor;
    private IngredientAdapter ingredientAdapter;


    public interface OnItemSelectedListener{
        void onItemSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onItemSelectedListener = (OnItemSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);

        if(savedInstanceState != null){
            recipe = Parcels.unwrap(savedInstanceState.getParcelable(RECIPE_KEY));
        }

        //Setting up the step list
        RecyclerView stepList = rootView.findViewById(R.id.step_list);
        if(stepAdaptor == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            stepAdaptor = new StepAdaptor();
            stepAdaptor.setStepOnClick(new StepAdaptor.StepOnClick() {
                @Override
                public void onClick(int position) {
                    onItemSelectedListener.onItemSelected(position);
                }
            });
            stepList.setLayoutManager(linearLayoutManager);
            stepList.setAdapter(stepAdaptor);
        }else{
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            stepList.setLayoutManager(linearLayoutManager);
            stepList.setAdapter(stepAdaptor);
        }

        //Setting up the ingredient list
        ListView ingredientList = rootView.findViewById(R.id.ingredient_list);
        if(ingredientAdapter == null) ingredientAdapter = new IngredientAdapter();
        ingredientList.setAdapter(ingredientAdapter);

        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        if(recipe != null) {
            ((RecipeDetailActivity) getActivity()).setTitle(recipe.getName());
            stepAdaptor.setSteps(recipe.getSteps());
            ingredientAdapter.setIngredients(recipe.getIngredients());
        }
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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.recipe_detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        int addButton = R.id.menu_add;

        if(itemId == addButton && recipe != null){
            addToCart();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
