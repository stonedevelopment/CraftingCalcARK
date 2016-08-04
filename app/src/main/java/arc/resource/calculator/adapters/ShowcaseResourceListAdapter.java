package arc.resource.calculator.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.model.CraftableResource;
import arc.resource.calculator.viewholders.ResourceViewHolder;

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
public class ShowcaseResourceListAdapter extends RecyclerView.Adapter {
    private static final String LOGTAG = "ResourceList";

    private SparseArray<CraftableResource> resources = null;

    public ShowcaseResourceListAdapter(SparseArray<CraftableResource> resources) {
        this.resources = resources;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_resource, parent, false);

        return new ResourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ResourceViewHolder viewHolder = (ResourceViewHolder) holder;

        int imageId = resources.valueAt(position).getImageId();
        String name = resources.valueAt(position).getName();
        int quantity = resources.valueAt(position).getQuantity();

        viewHolder.getImageView().setImageResource(imageId);
        viewHolder.getNameText().setText(name);
        viewHolder.getQuantityText().setText(String.format(Locale.US, "%1$d", quantity));
    }

    @Override
    public int getItemCount() {
        return resources.size();
    }

    public void setResources(SparseArray<CraftableResource> resources) {
        this.resources = resources;
    }

    public void Refresh() {
        this.notifyDataSetChanged();
    }
}
