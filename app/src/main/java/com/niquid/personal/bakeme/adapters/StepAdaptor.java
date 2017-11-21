package com.niquid.personal.bakeme.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepAdaptor extends RecyclerView.Adapter<StepAdaptor.StepHolder> {

    private static final int TOP = 0;
    private static final int BOTTOM = 1;
    private static final int MIDDLE = 2;

    private StepOnClick stepOnClick;
    private List<Step> steps;
    private int selected = 0;

    public interface StepOnClick{
        void onClick(int position);
    }

    public StepAdaptor(){
        this.steps = null;
    }

    public void setSteps(List<Step> steps){
        this.steps = steps;
        notifyDataSetChanged();
    }

    public void setStepOnClick(StepOnClick stepOnClick){
        this.stepOnClick = stepOnClick;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) return TOP;
        if(position == steps.size() - 1) return BOTTOM;
        return MIDDLE;
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        View holderView;

        switch (viewType){
            case TOP:
                holderView = LayoutInflater.from(parentContext).inflate(R.layout.step_item_top, parent, false);
                break;
            case BOTTOM:
                holderView = LayoutInflater.from(parentContext).inflate(R.layout.step_item_bottom, parent, false);
                break;
            default:
                holderView = LayoutInflater.from(parentContext).inflate(R.layout.step_item_middle, parent, false);
        }

        return new StepHolder(holderView);
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        Step current = steps.get(position);
        holder.setArgs(current);
    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size();
    }

    class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.step_name) TextView description;

        StepHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void setArgs(Step step){
            description.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            notifyItemChanged(selected);
            selected = position;
            notifyItemChanged(selected);
            stepOnClick.onClick(position);
        }
    }

}
