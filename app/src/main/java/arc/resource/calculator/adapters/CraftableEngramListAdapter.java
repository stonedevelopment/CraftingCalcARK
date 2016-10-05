package arc.resource.calculator.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.helpers.Helper;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.viewholders.EngramGridViewHolder;

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

    private CraftingQueue mCraftingQueue;

    private Context mContext;

    private int mDimens;

    public CraftableEngramListAdapter( Context context ) {
        this.mContext = context;
        this.mCraftingQueue = CraftingQueue.getInstance( context );

        Refresh();
    }

    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_engram_thumbnail, parent, false );

        return new EngramGridViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position ) {
        EngramGridViewHolder viewHolder = ( EngramGridViewHolder ) holder;

        try {
            int imageId = getContext().getResources().getIdentifier( mCraftingQueue.getEngramDrawable( position ), "drawable", getContext().getPackageName() );
            String name = mCraftingQueue.getEngramName( position );
            int quantity = mCraftingQueue.getEngramQuantity( position );

            viewHolder.getImage().setImageResource( imageId );
            viewHolder.getNameText().setText( name );

            viewHolder.getImage().setBackgroundColor( ContextCompat.getColor( getContext(), R.color.crafting_queue_background ) );
            viewHolder.getQuantityText().setText( String.format( Locale.US, "x%d", quantity ) );
            viewHolder.getNameText().setSingleLine( true );
        } catch ( ArrayIndexOutOfBoundsException e ) {
            Helper.Log( TAG, e.getMessage() );
        }
    }

    @Override
    public int getItemCount() {
        return mCraftingQueue.getEngramItemCount();
    }

    public void Refresh() {
        notifyDataSetChanged();
    }

    public void increaseQuantity( int position, int amount ) {
        mCraftingQueue.increaseQuantity( position, amount );
    }

    public void increaseQuantity( long engramId, int amount ) {
        mCraftingQueue.increaseQuantity( engramId, amount );
    }

    public void decreaseQuantity( int position, int amount ) {
        mCraftingQueue.decreaseQuantity( position, amount );
    }

    public void ClearQueue() {
        mCraftingQueue.Clear();
    }

    public void Remove( long engramId ) {
        mCraftingQueue.Remove( engramId );
    }

    public void setQuantity( long engramId, int quantity ) {
        mCraftingQueue.setQuantity( engramId, quantity );
    }

    private Context getContext() {
        return mContext;
    }
}
