package ark.resource.calculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import ark.resource.calculator.R;
import ark.resource.calculator.model.DisplayCase;
import ark.resource.calculator.viewholders.DisplayCaseViewHolder;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: ARK:Resource Calculator
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class DisplayCaseListAdapter extends RecyclerView.Adapter {
    private static final String LOGTAG = "LISTADAPTER";

    private DisplayCase displayCase;

    public DisplayCaseListAdapter(Context context) {
        displayCase = new DisplayCase(context);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_displaycase, parent, false);

        return new DisplayCaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DisplayCaseViewHolder viewHolder = (DisplayCaseViewHolder) holder;

        int imageId = displayCase.getImageId(position);
        String name = displayCase.getName(position);

        viewHolder.getImage().setImageResource(imageId);
        viewHolder.getNameText().setText(name);

        if (displayCase.isEngram(position)) {
            int quantity = displayCase.getQuantity(position);   // FIXME: tasks database to get quantity instead of saving it to object

            if (quantity > 0) {
                viewHolder.getImage().setBackgroundColor(displayCase.getContext().getColor(R.color.crafting_queue_background));
                viewHolder.getQuantityText().setText(String.format(Locale.US, "x%d", quantity));
                viewHolder.getNameText().setSingleLine(true);
            } else {
                viewHolder.getImage().setBackgroundColor(displayCase.getContext().getColor(R.color.displaycase_engram_background));
                viewHolder.getQuantityText().setText(null);
                viewHolder.getNameText().setSingleLine(false);
            }
        } else {
            viewHolder.getImage().setBackgroundColor(0);
            viewHolder.getQuantityText().setText(null);
            viewHolder.getNameText().setSingleLine(false);
        }
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
     * -- UTILITY METHODS --
     */

    public void Refresh() {
        this.notifyDataSetChanged();
    }
}
