package com.niquid.personal.bakeme;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.niquid.personal.bakeme.activity.StepDetailActivity;
import com.niquid.personal.bakeme.models.Step;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.niquid.personal.bakeme.utils.RecipeUtils.STEP_KEY;



@RunWith(AndroidJUnit4.class)
public class StepDetailActivityTest {

    @Rule
    public final ActivityTestRule<StepDetailActivity> mActivityTestRule
            = new ActivityTestRule<>(
                    StepDetailActivity.class,
                    true,
                    false);

    @Test
    public void StepActivityContainsFragment(){
        //checks whether the fragment is initialized successfully
        Intent fromPrevious = new Intent();
        List<Step> steps = new ArrayList<>();
        steps.add(new Step(0, "a", "b", null, null));
        steps.add(new Step(1, "b", "c", null, null));
        fromPrevious.putExtra(STEP_KEY, Parcels.wrap(steps));

        mActivityTestRule.launchActivity(fromPrevious);

        onView(withId(R.id.stepperLayout)).check(matches(isDisplayed()));
    }

}