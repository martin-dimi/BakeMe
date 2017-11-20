package com.niquid.personal.bakeme.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.models.Ingredient;

import java.util.List;


public class IngredientAdapter extends BaseAdapter {

    private List<Ingredient> ingredients;

    public IngredientAdapter() {
        this.ingredients = null;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(ingredients == null) return  0;
        return ingredients.size();
    }

    @Override
    public Object getItem(int i) {
        return ingredients.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        Ingredient ingredient = ingredients.get(i);
        if(view == null)
            view = LayoutInflater.from(context)
                    .inflate(R.layout.ingredient_item, viewGroup, false);

        TextView name = view.findViewById(R.id.ingredient_name);
        TextView amount = view.findViewById(R.id.ingredient_amount);
        TextView measurement = view.findViewById(R.id.ingredient_measurement);

        name.setText(ingredient.getIngredient());
        amount.setText(String.valueOf(ingredient.getQuantity()));
        measurement.setText(ingredient.getMeasure());

        return view;
    }
}
