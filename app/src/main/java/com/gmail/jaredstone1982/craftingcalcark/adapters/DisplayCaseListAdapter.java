package com.gmail.jaredstone1982.craftingcalcark.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.jaredstone1982.craftingcalcark.R;
import com.gmail.jaredstone1982.craftingcalcark.model.DisplayCase;
import com.gmail.jaredstone1982.craftingcalcark.viewholders.EngramViewHolder;

public class DisplayCaseListAdapter extends RecyclerView.Adapter {
    private static final String LOGTAG = "LISTADAPTER";

    private DisplayCase displayCase;

    public DisplayCaseListAdapter(Context context) {
        displayCase = new DisplayCase(context);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_engram_overlay, parent, false);

        return new EngramViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EngramViewHolder viewHolder = (EngramViewHolder) holder;

        int imageId = displayCase.getImageId(position);
        String name = displayCase.getName(position);

        viewHolder.getImage().setImageResource(imageId);
        viewHolder.getNameText().setText(name);
    }

    @Override
    public int getItemCount() {
        return displayCase.getCount();
    }

    /**
     * -- PUBLIC UTILITY METHODS --
     */

    public long getEngramId(int position) {
        return displayCase.getEngramId(position);
    }

    public boolean isEngram(int position) {
        return displayCase.isEngram(position);
    }

    public void changeCategory(int position) {
        displayCase.changeCategory(position);

        Refresh();
    }

    public void setFiltered(boolean isFiltered) {
        if (displayCase.setIsFiltered(isFiltered)) {
            Refresh();
        }
    }

    /**
     * -- PRIVATE UTILITY METHODS --
     */

    private void Refresh() {
        this.notifyDataSetChanged();
    }
}
