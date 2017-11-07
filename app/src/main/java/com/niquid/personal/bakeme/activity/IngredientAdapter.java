package com.niquid.personal.bakeme.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.models.Ingredient;

import java.util.List;

/**
 * Created by marti on 07/11/2017.
 */

public class IngredientAdapter extends BaseExpandableListAdapter {

    private List<Ingredient> ingredients;
    private Context context;

    public IngredientAdapter(List<Ingredient> ingredients, Context context){
        this.ingredients = ingredients;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int i) {
        return ingredients.size();
    }

    @Override
    public Object getGroup(int i) {
        return ingredients;
    }

    @Override
    public Object getChild(int i, int ingredientPosition) {
        return ingredients.get(ingredientPosition);
    }

    @Override
    public long getGroupId(int i) {
        return 1;
    }

    @Override
    public long getChildId(int i, int ingredientPosition) {
        return ingredientPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null)
             view = inflater.inflate(R.layout.ingredient_header, null);
        return view;
    }

    @Override
    public View getChildView(int i, int ingredientPosition, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null)
            view = inflater.inflate(R.layout.ingredient_item, null);

        Ingredient ingredient = ingredients.get(ingredientPosition);

        TextView ingredientName = view.findViewById(R.id.ingredient_name);
        TextView ingredientMeasurement = view.findViewById(R.id.ingredient_measurement);
        TextView ingredientAmount = view.findViewById(R.id.ingredient_amount);

        ingredientName.setText(ingredient.getIngredient());
        ingredientMeasurement.setText(ingredient.getMeasure());
        ingredientAmount.setText(String.valueOf(ingredient.getQuantity()));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
