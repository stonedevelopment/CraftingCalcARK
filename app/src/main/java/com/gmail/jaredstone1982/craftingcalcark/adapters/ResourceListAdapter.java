package com.gmail.jaredstone1982.craftingcalcark.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.jaredstone1982.craftingcalcark.R;
import com.gmail.jaredstone1982.craftingcalcark.model.CraftableResource;
import com.gmail.jaredstone1982.craftingcalcark.viewholders.ResourceViewHolder;

import java.util.Locale;

public class ResourceListAdapter extends RecyclerView.Adapter {
    private SparseArray<CraftableResource> resources = null;

    public ResourceListAdapter(SparseArray<CraftableResource> resources) {
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

        CraftableResource resource = resources.valueAt(position);

        viewHolder.getImage().setImageResource(resource.getImageId());
        viewHolder.getNameText().setText(resource.getName());
        viewHolder.getQuantityText().setText(String.format(Locale.US, "%1$d", resource.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if (resources != null) {
            return resources.size();
        }
        return 0;
    }

    public void setResources(SparseArray<CraftableResource> resources) {
        this.resources = resources;
    }

    public void Refresh() {
        this.notifyDataSetChanged();
    }
}
