package com.niquid.personal.bakeme.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.models.Recipe;
import com.niquid.personal.bakeme.utils.RecipeUtils;

import org.parceler.Parcels;

import java.util.List;

import timber.log.Timber;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private static final String INTENT_RECIPE = "recipe";
    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes){
        this.recipes = recipes;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        int recipeCard = R.layout.recipe_card;
        View recipe = LayoutInflater.from(parentContext).inflate(recipeCard, parent, false);
        return new RecipeHolder(recipe, parentContext);
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        Recipe current = recipes.get(position);
        holder.setRecipe(current);
    }

    @Override
    public int getItemCount() {
        if(recipes == null) return 0;
        return recipes.size();
    }

    public void swapRecipes(List<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final Context parentContext;
        private final TextView title;
        private final ImageView level;
        private final TextView servings;
        private final TextView ingredients;

        RecipeHolder(View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);

            parentContext   = context;
            title           = itemView.findViewById(R.id.cv_recipe_title);
            level           = itemView.findViewById(R.id.cv_recipe_level);
            servings        = itemView.findViewById(R.id.cv_recipe_servings);
            ingredients     = itemView.findViewById(R.id.cv_recipe_ingredients);
        }

        void setRecipe(Recipe recipe){
            String servingsNum  = String.valueOf(recipe.getServings());
            String ingredNum    = String.valueOf(recipe.getIngredients().size());

            title.setText(recipe.getName());
            level.setImageResource(RecipeUtils.getDifficulty(recipe));
            servings.setText(servingsNum);
            ingredients.setText(ingredNum);
        }

        @Override
        public void onClick(View view) {
            Recipe current = recipes.get(getAdapterPosition());
            Intent changeToRecipeActivity = new Intent(parentContext, RecipeActivity.class);
            changeToRecipeActivity.putExtra(INTENT_RECIPE, Parcels.wrap(current));
            parentContext.startActivity(changeToRecipeActivity);
        }
    }
}
