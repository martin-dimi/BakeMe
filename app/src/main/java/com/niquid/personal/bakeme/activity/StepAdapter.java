package com.niquid.personal.bakeme.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.models.Step;

import java.util.List;

/**
 * Created by marti on 07/11/2017.
 */

public class StepAdapter extends BaseAdapter {

    private List<Step> steps;
    private Context context;

    public StepAdapter(List<Step> steps, Context context){
        this.steps = steps;
        this.context = context;
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public Object getItem(int i) {
        return steps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        final int firstStep = 0;
        final int finalStep = steps.size()-1;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(pos == firstStep)
            view = inflater.inflate(R.layout.step_item_top, null);
        else if(pos == finalStep)
            view = inflater.inflate(R.layout.step_item_bottom, null);
        else
            view = inflater.inflate(R.layout.step_item_middle, null);

        TextView step = view.findViewById(R.id.step_name);
        step.setText(steps.get(pos).getShortDescription());

        return view;
    }
}
