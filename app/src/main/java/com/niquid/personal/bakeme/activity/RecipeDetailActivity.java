package com.niquid.personal.bakeme.activity;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.fragments.RecipeDetailFragment;
import com.niquid.personal.bakeme.models.Recipe;

import org.parceler.Parcels;

import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //Setting up the appbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Getting and setting the recipe to display
        Recipe current = Parcels.unwrap(getIntent().getParcelableExtra(RECIPE_KEY));
        RecipeDetailFragment fragment = (RecipeDetailFragment) getFragmentManager().findFragmentById(R.id.fragment);
        fragment.setRecipe(current);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setTitle(String title){
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(title);
    }
}
