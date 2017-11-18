package com.niquid.personal.bakeme.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.adapters.StepsStepperAdapter;
import com.niquid.personal.bakeme.models.Step;
import com.stepstone.stepper.StepperLayout;

import org.parceler.Parcels;

import java.util.List;

import static com.niquid.personal.bakeme.utils.RecipeUtils.STEP_KEY;

public class StepDetailActivity extends AppCompatActivity {

    private static final String STEP_POSITION_KEY = "adapter";

    private StepperLayout stepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        List<Step> steps = Parcels.unwrap(getIntent().getParcelableExtra(STEP_KEY));
        int position = 0;
        if(savedInstanceState != null) {
            position = savedInstanceState.getInt(STEP_POSITION_KEY);
            System.out.println("SAVE STATE " + position);
        }
        stepperLayout = findViewById(R.id.stepperLayout);
        StepsStepperAdapter adapter = new StepsStepperAdapter(getSupportFragmentManager(), steps, this);
        stepperLayout.setAdapter(adapter, position);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int position = stepperLayout.getCurrentStepPosition();
        System.out.println("SAVING " + position);
        outState.putInt(STEP_POSITION_KEY, position);
    }
}
