package com.niquid.personal.bakeme.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;


import com.niquid.personal.bakeme.fragments.StepDetailFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import org.parceler.Parcels;

import java.util.List;

import static com.niquid.personal.bakeme.utils.RecipeUtils.STEP_KEY;


public class StepsStepperAdapter extends AbstractFragmentStepAdapter{

    private final List<com.niquid.personal.bakeme.models.Step> steps;

   public StepsStepperAdapter(FragmentManager manager, List<com.niquid.personal.bakeme.models.Step> steps, Context context){
       super(manager, context);
       this.steps = steps;
   }

    @Override
    public Step createStep(int position) {
        final StepDetailFragment step = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STEP_KEY, Parcels.wrap(steps.get(position)));
        step.setArguments(bundle);
        return step;
    }

    @Override
    public int getCount() {
        if(steps == null) return 0;
        return steps.size();
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return new StepViewModel.Builder(context)
                .setTitle("TITLE") //can be a CharSequence instead
                .create();
    }


}
