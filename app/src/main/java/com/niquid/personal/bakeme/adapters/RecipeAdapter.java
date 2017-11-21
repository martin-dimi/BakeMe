package com.niquid.personal.bakeme.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.models.Recipe;
import com.niquid.personal.bakeme.utils.RecipeUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private List<Recipe> recipes;
    private final RecipeOnClick recipeOnClick;

    public interface RecipeOnClick{
        void onClick(Recipe recipe);
    }

    public RecipeAdapter(List<Recipe> recipes, RecipeOnClick recipeOnClick){
        this.recipes = recipes;
        this.recipeOnClick = recipeOnClick;
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
        return recipes == null ? 0 : recipes.size();
    }

    public void updateAdaptor(List<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.cv_recipe_title)  TextView title;
        @BindView(R.id.cv_recipe_level)  ImageView level;
        @BindView(R.id.cv_recipe_servings)  TextView servings;
        @BindView(R.id.cv_recipe_ingredients)  TextView ingredients;
        @BindView(R.id.cv_image)  ImageView header;
        private final Context context;

        RecipeHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

            this.context    = context;
        }

        void setRecipe(Recipe recipe){
            String servingsNum  = String.valueOf(recipe.getServings());
            String ingredNum    = String.valueOf(recipe.getIngredients().size());

            title.setText(recipe.getName());
            level.setImageResource(RecipeUtils.getDifficulty(recipe));
            servings.setText(servingsNum);
            ingredients.setText(ingredNum);

            if(RecipeUtils.hasImage(recipe)){
                Uri uri = Uri.parse(recipe.getImage());
                Picasso.with(context).load(uri).placeholder(R.drawable.loading).into(header, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        header.setVisibility(View.GONE);
                    }
                });
            }
        }

        @Override
        public void onClick(View view) {
            if(recipeOnClick != null)
                recipeOnClick.onClick(recipes.get(getAdapterPosition()));
        }
    }
}
