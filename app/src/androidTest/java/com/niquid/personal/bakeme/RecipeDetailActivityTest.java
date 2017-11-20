package com.niquid.personal.bakeme;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;

import com.niquid.personal.bakeme.activity.RecipeDetailActivity;
import com.niquid.personal.bakeme.activity.StepDetailActivity;
import com.niquid.personal.bakeme.models.Ingredient;
import com.niquid.personal.bakeme.models.Recipe;
import com.niquid.personal.bakeme.models.Step;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.niquid.personal.bakeme.utils.RecipeUtils.RECIPE_KEY;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public final IntentsTestRule<RecipeDetailActivity> mActivityTestRule
            = new IntentsTestRule<>(RecipeDetailActivity.class, true, false);

    private boolean isTablet;

    @Before
    public void init(){
        List<Step> steps = new ArrayList<>();
        steps.add(new Step(0, "a", "b", "", ""));
        steps.add(new Step(1, "b", "c", "", ""));

        Recipe recipe = new Recipe(0, "", new ArrayList<Ingredient>(), steps, 0, "");

        Intent fromPrevious = new Intent();
        fromPrevious.putExtra(RECIPE_KEY, Parcels.wrap(recipe));

        mActivityTestRule.launchActivity(fromPrevious);

        //Gets the size of the screen and sets the boolean isTablet
        DisplayMetrics dm = new DisplayMetrics();
        mActivityTestRule.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        int widthInDP = Math.round(dm.widthPixels / dm.density);
        isTablet = widthInDP >= 600;
    }


    //Tests w/e the mainActivity contains the fragments depending on the screen size
    @Test
    public void MainActivityContainsFragments(){

        if(isTablet) {
            onView(withId(R.id.recipe_details)).check(matches(isDisplayed()));
            onView(withId(R.id.step_detail)).check(matches(isDisplayed()));
        }
        else {
            onView(withId(R.id.recipe_details)).check(matches(isDisplayed()));
            onView(withId(R.id.step_detail)).check(doesNotExist());
        }
    }


    @Test
    public void RecipeClickStartsDetailFragment(){


        //starts fragment
        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

        //Click on a step
        onView(ViewMatchers.withId(R.id.step_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, doubleClick()));

        if(isTablet) {
            //if it is a tablet, it should split the screen and show the detail fragment
            onView(withId(R.id.step_detail)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        } else {
            //If it is a phone, it should start a new Activity to show the details of the recipe
            intended(hasComponent(StepDetailActivity.class.getName()));
        }
    }

}
