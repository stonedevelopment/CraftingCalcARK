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
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.util.ExceptionUtil;
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

    public CraftableEngramListAdapter( Context context ) throws Exception {
        this.mContext = context;
        this.mCraftingQueue = CraftingQueue.getInstance( context );

        NotifyDataChanged();
    }

    public EngramGridViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_craftable_engram_thumbnail, parent, false );

        return new EngramGridViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( EngramGridViewHolder holder, int position ) {
        holder.getView().getLayoutParams().height = ( int ) MainActivity.mEngramDimensions;
        holder.getView().getLayoutParams().width = ( int ) MainActivity.mEngramDimensions;

        try {
            int imageId = getContext().getResources().getIdentifier(
                    mCraftingQueue.getEngram( position ).getDrawable(),
                    "drawable",
                    getContext().getPackageName() );

            // FIXME: 12/22/2016 Create crafting queue viewholder, why set background on every bind?
            holder.getThumbnail().setBackground( ContextCompat.getDrawable( getContext(), R.drawable.frame_engram_crafting_queue ) );
            holder.getThumbnail().setImageResource( imageId );

            String name = mCraftingQueue.getEngram( position ).getName();
            holder.getName().setText( name );
            holder.getName().setSingleLine( true );

            int quantity = mCraftingQueue.getEngramQuantityWithYield( position );
            holder.getQuantity().setText( String.format( Locale.US, "x%d", quantity ) );
        } catch ( Exception e ) {
            ExceptionUtil.SendErrorReportWithAlertDialog( getContext(), TAG, e );
        }
    }

    @Override
    public int getItemCount() {
        return mCraftingQueue.getEngramItemCount();
    }

    public void NotifyDataChanged() {
        notifyDataSetChanged();
    }

    public QueueEngram getEngram( int position ) throws Exception {
        return mCraftingQueue.getEngram( position );
    }

    public void increaseQuantity( Context context, int position ) throws Exception {
        mCraftingQueue.increaseQuantity( context, position );
    }

    public void increaseQuantity( Context context, long engramId ) throws Exception {
        mCraftingQueue.increaseQuantity( context, engramId );
    }

    public void ClearQueue( Context context ) throws Exception {
        mCraftingQueue.ClearContents( context );
    }

    public void Delete( Context context, long engramId ) throws Exception {
        mCraftingQueue.Delete( context, engramId );
    }

    public void setQuantity( Context context, long engramId, int quantity ) throws Exception {
        mCraftingQueue.setQuantity( context, engramId, quantity );
    }

    private Context getContext() {
        return mContext;
    }
}
