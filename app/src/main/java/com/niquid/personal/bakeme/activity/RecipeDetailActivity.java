package com.niquid.personal.bakeme.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.fragments.RecipeDetailFragment;
import com.niquid.personal.bakeme.fragments.StepDetailFragment;
import com.niquid.personal.bakeme.models.Recipe;

import org.parceler.Parcels;
import timber.log.Timber;

import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;
import static com.niquid.personal.bakeme.utils.RecipeUtils.STEP_KEY;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnItemSelectedListener {

    private static final String STEP_DETAIL = "step_detail";

    private StepDetailFragment stepDetailFragment;
    private boolean mIsTwoPane = false;
    private Recipe mRecipe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //Setting up the appbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Getting and setting the mRecipe to display
        if(savedInstanceState != null) {
            mRecipe = Parcels.unwrap(savedInstanceState.getParcelable(RECIPE_KEY));
            stepDetailFragment = (StepDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, STEP_DETAIL);
            if(stepDetailFragment != null){
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.step_detail, stepDetailFragment);
                ft.commit();
            }
        }
        //Only create new fragments if there is no previous state
        else {
            mRecipe = Parcels.unwrap(getIntent().getParcelableExtra(RECIPE_KEY));
            RecipeDetailFragment fragment = (RecipeDetailFragment) getFragmentManager().findFragmentById(R.id.recipe_details);
            fragment.setRecipe(mRecipe);
        }



        mIsTwoPane = isTwoPane();
    }

    private boolean isTwoPane(){
        FrameLayout frameLayout = findViewById(R.id.step_detail);
        return frameLayout != null;
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

    @Override
    public void onItemSelected(int position) {
        Timber.d("Item clicked");
        Bundle bundle = new Bundle();

        if(mIsTwoPane){
            bundle.putParcelable(STEP_KEY, Parcels.wrap(mRecipe.getSteps().get(position)));
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(bundle);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.step_detail, stepDetailFragment);
            ft.commit();
        }else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(STEP_KEY, Parcels.wrap(mRecipe.getSteps()));
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_KEY, Parcels.wrap(mRecipe));
        if(stepDetailFragment != null)
            getSupportFragmentManager().putFragment(outState, STEP_DETAIL, stepDetailFragment);
    }
}
