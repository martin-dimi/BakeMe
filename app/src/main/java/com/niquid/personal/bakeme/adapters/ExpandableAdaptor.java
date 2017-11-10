package com.niquid.personal.bakeme.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.models.Ingredient;
import com.niquid.personal.bakeme.models.Recipe;


public class ExpandableAdaptor extends BaseExpandableListAdapter {

    private static final int INGREDIENTS = 0;
    private static final int STEPS = 1;

    private Recipe recipe;
    private Context context;

    public ExpandableAdaptor(Recipe recipe, Context context){
        this.recipe = recipe;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int i) {

        if(i==INGREDIENTS) return recipe.getIngredients().size();
        else if(i==STEPS) return recipe.getSteps().size();
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        if(i==INGREDIENTS) return recipe.getIngredients();
        else if(i==STEPS) return recipe.getSteps();
        return null;
    }

    @Override
    public Object getChild(int parent, int child) {
        if(parent==INGREDIENTS) return recipe.getIngredients().get(child);
        else if(parent==STEPS) return recipe.getSteps().get(child);
        return null;
    }

    @Override
    public long getGroupId(int parent) {
        return parent;
    }

    @Override
    public long getChildId(int i, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int parent, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;

        if (view == null)
            view = inflater.inflate(R.layout.el_header, null);
        TextView ingredientAmount = view.findViewById(R.id.el_group_amount);

        if(parent == INGREDIENTS) {
                ((TextView) view.findViewById(R.id.el_group_header)).setText(R.string.ingredient_header);
                ingredientAmount.setText(String.valueOf(recipe.getIngredients().size()));
        }
        else if(parent == STEPS){
                ((TextView) view.findViewById(R.id.el_group_header)).setText(R.string.step_header);
                ingredientAmount.setText(String.valueOf(recipe.getIngredients().size()));
        }
        return view;
    }

    @Override
    public View getChildView(int parent, int child, boolean b, View view, ViewGroup viewGroup) {

        if(parent == INGREDIENTS)
            view = setIngredientChildView(child);
        else if(parent == STEPS)
            view = setStepChildView(child);


        return view;
    }

    @SuppressLint("InflateParams")
    private View setIngredientChildView(int ingredientPosition){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;

        View view = inflater.inflate(R.layout.ingredient_item, null);
        Ingredient ingredient = recipe.getIngredients().get(ingredientPosition);

        TextView ingredientName = view.findViewById(R.id.ingredient_name);
        TextView ingredientMeasurement = view.findViewById(R.id.ingredient_measurement);
        TextView ingredientAmount = view.findViewById(R.id.ingredient_amount);

        ingredientName.setText(ingredient.getIngredient());
        ingredientMeasurement.setText(ingredient.getMeasure());
        ingredientAmount.setText(String.valueOf(ingredient.getQuantity()));

        return view;
    }

    @SuppressLint("InflateParams")
    private View setStepChildView(int stepPosition){

        final int firstStep = 0;
        final int finalStep = recipe.getSteps().size()-1;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;

        View view;
        if(stepPosition == firstStep)
            view = inflater.inflate(R.layout.step_item_top, null);
        else if(stepPosition == finalStep)
            view = inflater.inflate(R.layout.step_item_bottom, null);
        else
            view = inflater.inflate(R.layout.step_item_middle, null);

        TextView step = view.findViewById(R.id.step_name);
        step.setText(recipe.getSteps().get(stepPosition).getShortDescription());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
