package com.gmail.jaredstone1982.craftingcalcark.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
    private Context context;
    private SparseArray<CraftableResource> resourceList;
    private Resources resources;


    public ResourceListAdapter(Context context, SparseArray<CraftableResource> resources) {
        this.resourceList = resources;
        this.context = context.getApplicationContext();
        this.resources = context.getResources();
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

        CraftableResource resource = resourceList.valueAt(position);

        int imageId = resource.getImageId();
        Drawable drawable = resources.getDrawable(imageId, null);

        viewHolder.getImage().setImageDrawable(drawable);
        viewHolder.getNameText().setText(resource.getName());
        viewHolder.getQuantityText().setText(String.format(Locale.US, "%1$d", resource.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    public void setResources(SparseArray<CraftableResource> resources) {
        this.resourceList = resources;
    }

    public void Refresh() {
        this.notifyDataSetChanged();
    }
}
