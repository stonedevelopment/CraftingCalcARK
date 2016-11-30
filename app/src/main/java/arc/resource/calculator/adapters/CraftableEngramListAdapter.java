package arc.resource.calculator.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import arc.resource.calculator.MainActivity;
import arc.resource.calculator.R;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.util.Helper;
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
public class CraftableEngramListAdapter extends RecyclerView.Adapter<EngramGridViewHolder> {
    private static final String TAG = CraftableEngramListAdapter.class.getSimpleName();

    private CraftingQueue mCraftingQueue;

    private Context mContext;

    public CraftableEngramListAdapter( Context context ) {
        this.mContext = context;
        this.mCraftingQueue = CraftingQueue.getInstance( context );

        Refresh();
    }

    public EngramGridViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_craftable_engram_thumbnail, parent, false );

        return new EngramGridViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( EngramGridViewHolder holder, int position ) {
        try {
            int dimensions = ( int ) MainActivity.mCraftableEngramDimensions;

            holder.itemView.getLayoutParams().height = dimensions;
            holder.itemView.getLayoutParams().width = dimensions;

            int imageId = getContext().getResources().getIdentifier( mCraftingQueue.getEngramDrawable( position ), "drawable", getContext().getPackageName() );
            String name = mCraftingQueue.getEngramName( position );
            int quantity = mCraftingQueue.getEngramQuantityWithYield( position );

            holder.getThumbnail().setBackground( ContextCompat.getDrawable( getContext(), R.drawable.frame_engram_crafting_queue ) );
            holder.getThumbnail().setImageResource( imageId );

            holder.getName().setText( name );
            holder.getName().setSingleLine( true );

            holder.getQuantity().setText( String.format( Locale.US, "x%d", quantity ) );
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

    public long getEngramId( int position ) {
        return mCraftingQueue.getEngramId( position );
    }

    public void increaseQuantity( int position ) {
        mCraftingQueue.increaseQuantity( position );
    }

    public void increaseQuantity( long engramId ) {
        mCraftingQueue.increaseQuantity( engramId );
    }

    public void decreaseQuantity( int position ) {
        mCraftingQueue.decreaseQuantity( position );
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
