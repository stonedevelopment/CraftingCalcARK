package arc.resource.calculator.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.helpers.DisplayHelper;
import arc.resource.calculator.model.DisplayCase;
import arc.resource.calculator.viewholders.DisplayCaseViewHolder;

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
public class DisplayCaseListAdapter extends RecyclerView.Adapter {
    private static final String LOGTAG = "LISTADAPTER";

    //    private Context context;
    private DisplayHelper displayHelper;

    private DisplayCase displayCase;

    private Context context;

    public DisplayCaseListAdapter(Context context) {
        this.context = context.getApplicationContext();
        this.displayHelper = DisplayHelper.getInstance();
        this.displayCase = new DisplayCase(context);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_displaycase, parent, false);

        return new DisplayCaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DisplayCaseViewHolder viewHolder = (DisplayCaseViewHolder) holder;

        viewHolder.itemView.getLayoutParams().height = (int) displayHelper.getEngramDimensionsWithDensity();
        viewHolder.itemView.getLayoutParams().width = (int) displayHelper.getEngramDimensionsWithDensity();

        int imageId = displayCase.getImageId(position);
        String name = displayCase.getName(position);

        viewHolder.getImage().setImageResource(imageId);
        viewHolder.getNameText().setText(name);

        if (displayCase.isEngram(position)) {
            int quantity = displayCase.getQuantity(position);

            if (quantity > 0) {
                viewHolder.getImage().setBackgroundColor(ContextCompat.getColor(context, R.color.crafting_queue_background));
                viewHolder.getQuantityText().setText(String.format(Locale.US, "x%d", quantity));
                viewHolder.getNameText().setSingleLine(true);
            } else {
                viewHolder.getImage().setBackgroundColor(ContextCompat.getColor(context, R.color.displaycase_engram_background));
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

    public long getLevel() {
        return displayCase.getLevel();
    }

    public long getParent() {
        return displayCase.getParent();
    }

    public void changeCategory(int position) {
        displayCase.changeCategory(position);

        Refresh();
    }

    public boolean isFiltered() {
        return displayCase.isFiltered();
    }

    public void setFiltered(boolean isFiltered) {
        displayCase.setIsFiltered(isFiltered);
    }

    /**
     * -- UTILITY METHODS --
     */

    public void Refresh() {
        displayCase.UpdateQueues();

        this.notifyDataSetChanged();
    }
}
