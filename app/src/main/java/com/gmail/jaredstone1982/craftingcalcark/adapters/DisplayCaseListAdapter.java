package com.gmail.jaredstone1982.craftingcalcark.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.jaredstone1982.craftingcalcark.R;
import com.gmail.jaredstone1982.craftingcalcark.model.DisplayCase;
import com.gmail.jaredstone1982.craftingcalcark.viewholders.DisplayCaseViewHolder;

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

        int textSize = 12;
        int textMax = 10;

        int imageId = displayCase.getImageId(position);
        String name = displayCase.getName(position);

        viewHolder.getImage().setImageResource(imageId);
        viewHolder.getNameText().setText(name);

        if (displayCase.isEngram(position)) {
            viewHolder.getImage().setBackgroundColor(displayCase.getContext().getColor(R.color.displaycase_engram_background));
            viewHolder.getImage().setImageTintList(displayCase.getContext().getColorStateList(R.color.displaycase_tint_half_opacity));
            viewHolder.getImage().setImageTintMode(PorterDuff.Mode.SRC_ATOP);
            viewHolder.getNameText().setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        } else {
            viewHolder.getImage().setBackgroundColor(0);
            viewHolder.getImage().setImageTintMode(null);

            int textLength = viewHolder.getNameText().length();
            if (textLength > textMax) {
                int textRemainder = textLength - textMax;
                if (textRemainder > 2) {
                    textRemainder = 2;
                }
                textSize -= textRemainder;
            }

            viewHolder.getNameText().setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
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
     * -- PRIVATE UTILITY METHODS --
     */

    private void Refresh() {
        this.notifyDataSetChanged();
    }
}
