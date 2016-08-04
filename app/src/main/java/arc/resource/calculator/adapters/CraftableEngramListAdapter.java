package arc.resource.calculator.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.helpers.DisplayHelper;
import arc.resource.calculator.model.CraftableEngram;
import arc.resource.calculator.viewholders.EngramViewHolder;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: A:RC, a resource calculator for ARK:Survival Evolved
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class CraftableEngramListAdapter extends RecyclerView.Adapter {
    private static final String LOGTAG = "CraftableList";

    private DisplayHelper displayHelper;
    private SparseArray<CraftableEngram> engrams;

    public CraftableEngramListAdapter(SparseArray<CraftableEngram> engrams) {
        this.engrams = engrams;

        this.displayHelper = DisplayHelper.getInstance();
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_engram, parent, false);

        return new EngramViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EngramViewHolder viewHolder = (EngramViewHolder) holder;

        final CraftableEngram engram = engrams.valueAt(position);

        viewHolder.itemView.getLayoutParams().height = (int) displayHelper.getEngramDimensionsWithDensity();
        viewHolder.itemView.getLayoutParams().width = (int) displayHelper.getEngramDimensionsWithDensity();

        int imageId = engram.getImageId();

        viewHolder.getImage().setImageResource(imageId);
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
