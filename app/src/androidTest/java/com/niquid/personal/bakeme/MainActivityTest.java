package com.niquid.personal.bakeme;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;

import com.niquid.personal.bakeme.activity.MainActivity;
import com.niquid.personal.bakeme.activity.RecipeDetailActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public final IntentsTestRule <MainActivity> mActivityTestRule
            = new IntentsTestRule<>(MainActivity.class);

    private boolean isTablet;

    @Before
    public void init(){
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
            onView(withId(R.id.fragment_recipe_list)).check(matches(isDisplayed()));
            onView(withId(R.id.fragment_recipe_detail)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        }
        else {
            onView(withId(R.id.fragment_recipe_list)).check(matches(isDisplayed()));
            onView(withId(R.id.fragment_recipe_detail)).check(doesNotExist());
        }
    }

    @Test
    public void RecipeListFragmentContainsViews(){
        //starts fragment
        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

        onView(withId(R.id.toolbar_image)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_list)).check(matches(isDisplayed()));
    }

    @Test
    public void RecipeClickStartsDetailFragment(){

        //starts fragment
        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

        //Click on a recipe
        onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, doubleClick()));

        if(isTablet) {
            //if it is a tablet, it should split the screen and show the detail fragment
            onView(withId(R.id.fragment_recipe_detail)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        } else {
            //If it is a phone, it should start a new Activity to show the details of the recipe
            intended(hasComponent(RecipeDetailActivity.class.getName()));
        }
    }

}
