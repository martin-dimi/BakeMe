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

import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private List<Recipe> recipes;
    private RecipeOnClick recipeOnClick;

    public interface RecipeOnClick{
        void onClick(Recipe recipe);
    }

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

    public void updateAdaptor(List<Recipe> recipes, RecipeOnClick recipeOnClick){
        this.recipes = recipes;
        this.recipeOnClick = recipeOnClick;
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
            if(recipeOnClick != null)
                recipeOnClick.onClick(recipes.get(getAdapterPosition()));
        }
    }
}
