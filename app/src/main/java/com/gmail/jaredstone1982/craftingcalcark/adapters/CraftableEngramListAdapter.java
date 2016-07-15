package com.gmail.jaredstone1982.craftingcalcark.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.jaredstone1982.craftingcalcark.R;
import com.gmail.jaredstone1982.craftingcalcark.model.CraftableEngram;
import com.gmail.jaredstone1982.craftingcalcark.viewholders.EngramViewHolder;

import java.util.Locale;

public class CraftableEngramListAdapter extends RecyclerView.Adapter {
    private SparseArray<CraftableEngram> engrams;

    public CraftableEngramListAdapter(SparseArray<CraftableEngram> engrams) {
        this.engrams = engrams;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_engram, parent, false);

        return new EngramViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EngramViewHolder viewHolder = (EngramViewHolder) holder;

        CraftableEngram engram = engrams.valueAt(position);

        viewHolder.getImage().setImageResource(engram.getImageId());
        viewHolder.getNameText().setText(engram.getName());
        viewHolder.getQuantityText().setText(String.format(Locale.US, "x%1$d", engram.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return engrams.size();
    }

    public void setEngrams(SparseArray<CraftableEngram> engrams) {
        this.engrams = engrams;
    }

    public CraftableEngram getEngram(int position) {
        return engrams.valueAt(position);
    }

    public void Refresh() {
        this.notifyDataSetChanged();
    }
}
