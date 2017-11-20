package com.niquid.personal.bakeme;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.niquid.personal.bakeme.activity.MainActivity;
import com.niquid.personal.bakeme.activity.RecipeDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public final IntentsTestRule<MainActivity> mActivityTestRule
            = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void RecipeClickStartsDetailFragment(){
        //starts fragment
        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

        //Click on a recipe
        onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, doubleClick()));

        //Start a new Activity to show the details of the recipe
        intended(hasComponent(RecipeDetailActivity.class.getName()));
        }
}


