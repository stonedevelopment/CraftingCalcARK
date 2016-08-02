package arc.resource.calculator.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import arc.resource.calculator.R;
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
    private Context context;
    private SparseArray<CraftableEngram> engrams;
    private Resources resources;

    public CraftableEngramListAdapter(Context context, SparseArray<CraftableEngram> engrams) {
        this.engrams = engrams;
        this.context = context.getApplicationContext();
        this.resources = context.getResources();
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

        int imageId = engram.getImageId();
        Drawable drawable = resources.getDrawable(imageId, null);

        viewHolder.getImage().setImageDrawable(drawable);
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
