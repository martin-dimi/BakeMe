package com.niquid.personal.bakeme.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.models.Recipe;

import org.parceler.Parcels;

import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Recipe current = Parcels.unwrap(getIntent().getParcelableExtra(RECIPE_KEY));
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment);

        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_KEY, Parcels.wrap(current));
        fragment.setArguments(bundle);
    }
}
