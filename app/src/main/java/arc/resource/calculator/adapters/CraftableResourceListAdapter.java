package arc.resource.calculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.model.CraftingQueue;
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
public class CraftableResourceListAdapter extends RecyclerView.Adapter {
    private static final String LOGTAG = "ResourceList";

    private CraftingQueue craftingQueue;

    public CraftableResourceListAdapter(Context context) {
        this.craftingQueue = CraftingQueue.getInstance(context);
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

        int imageId = craftingQueue.getResourceImageId(position);
        String name = craftingQueue.getResourceName(position);
        int quantity = craftingQueue.getResourceQuantity(position);

        viewHolder.getImageView().setImageResource(imageId);
        viewHolder.getNameText().setText(name);
        viewHolder.getQuantityText().setText(String.format(Locale.US, "%1$d", quantity));
    }

    @Override
    public int getItemCount() {
        return craftingQueue.getResourceItemCount();
    }

    public void setBreakdownResources(boolean b) {
        if (craftingQueue.hasComplexResources() != b) {
            craftingQueue.setHasComplexResources(b);
        }
    }

    public boolean getBreakdownResources() {
        return craftingQueue.hasComplexResources();
    }

    public void Refresh() {
        this.notifyDataSetChanged();
    }
}
