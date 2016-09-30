package arc.resource.calculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.helpers.DisplayHelper;
import arc.resource.calculator.helpers.Helper;
import arc.resource.calculator.model.CraftingQueue;
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
    private static final String TAG = CraftableEngramListAdapter.class.getSimpleName();

    private DisplayHelper displayHelper;

    private CraftingQueue craftingQueue;

    private Context mContext;

    public CraftableEngramListAdapter(Context context) {
        this.displayHelper = DisplayHelper.getInstance();
        this.craftingQueue = CraftingQueue.getInstance(context);
        this.mContext = context;

        Refresh();
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_engram, parent, false);

        return new EngramViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EngramViewHolder viewHolder = (EngramViewHolder) holder;

        viewHolder.itemView.getLayoutParams().height = (int) displayHelper.getEngramDimensionsWithDensity();
        viewHolder.itemView.getLayoutParams().width = (int) displayHelper.getEngramDimensionsWithDensity();

        try {
            int imageId = mContext.getResources().getIdentifier( craftingQueue.getEngramDrawable( position ), "drawable", mContext.getPackageName() );
            String name = craftingQueue.getEngramName( position );
            int quantity = craftingQueue.getEngramQuantity( position );


            viewHolder.getImage().setImageResource( imageId );
            viewHolder.getNameText().setText( name );
            viewHolder.getQuantityText().setText( String.format( Locale.US, "x%1$d", quantity ) );
        } catch ( ArrayIndexOutOfBoundsException e ) {
            Helper.Log( TAG, e.getMessage() );
        }
    }

    @Override
    public int getItemCount() {
        return craftingQueue.getEngramItemCount();
    }

    public void Refresh() {
        notifyDataSetChanged();
    }

    public void increaseQuantity(int position, int amount) {
        craftingQueue.increaseQuantity(position, amount);
    }

    public void increaseQuantity(long engramId, int amount) {
        craftingQueue.increaseQuantity(engramId, amount);
    }

    public void decreaseQuantity(int position, int amount) {
        craftingQueue.decreaseQuantity(position, amount);
    }

    public void ClearQueue() {
        craftingQueue.Clear();
    }

    public void Remove(long engramId) {
        craftingQueue.Remove(engramId);
    }

    public void setQuantity(long engramId, int quantity) {
        craftingQueue.setQuantity(engramId, quantity);
    }
}
