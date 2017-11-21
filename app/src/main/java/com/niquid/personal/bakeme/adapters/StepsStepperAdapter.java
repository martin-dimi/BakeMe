package com.niquid.personal.bakeme.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;


import com.niquid.personal.bakeme.fragments.StepDetailFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;

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
        return steps == null ? 0 : steps.size();
    }

}
