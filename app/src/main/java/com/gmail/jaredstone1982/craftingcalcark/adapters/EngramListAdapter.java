package com.gmail.jaredstone1982.craftingcalcark.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.jaredstone1982.craftingcalcark.R;
import com.gmail.jaredstone1982.craftingcalcark.model.DisplayEngram;
import com.gmail.jaredstone1982.craftingcalcark.viewholders.EngramViewHolder;

public class EngramListAdapter extends RecyclerView.Adapter {
    private SparseArray<DisplayEngram> engrams;

    public EngramListAdapter(SparseArray<DisplayEngram> engrams) {
        this.engrams = engrams;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_engram_overlay, parent, false);

        return new EngramViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EngramViewHolder viewHolder = (EngramViewHolder) holder;

        DisplayEngram engram = engrams.valueAt(position);

        viewHolder.getImage().setImageResource(engram.getImageId());
        viewHolder.getNameText().setText(engram.getName());
    }

    @Override
    public int getItemCount() {
        return engrams.size();
    }

    public void setEngrams(SparseArray<DisplayEngram> engrams) {
        this.engrams = engrams;
    }

    public DisplayEngram getEngram(int position) {
        return engrams.valueAt(position);
    }

    public void Refresh() {
        this.notifyDataSetChanged();
    }
}
