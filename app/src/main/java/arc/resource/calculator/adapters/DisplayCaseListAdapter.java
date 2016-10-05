package arc.resource.calculator.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.model.DisplayCase;
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
public class DisplayCaseListAdapter extends RecyclerView.Adapter {
    private static final String TAG = DisplayCaseListAdapter.class.getSimpleName();

    private DisplayCase mDisplayCase;
    private Context mContext;

    private int mDimens;

    public DisplayCaseListAdapter( Context context ) {
        this.mContext = context.getApplicationContext();
        this.mDisplayCase = DisplayCase.getInstance( context );
    }

    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_engram_thumbnail, parent, false );

        return new EngramGridViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position ) {
        EngramGridViewHolder viewHolder = ( EngramGridViewHolder ) holder;

        int imageId = getContext().getResources().getIdentifier( mDisplayCase.getDrawable( position ), "drawable", getContext().getPackageName() );
        String name = mDisplayCase.getName( position );

        viewHolder.getImage().setImageResource( imageId );
        viewHolder.getNameText().setText( name );

        if ( mDisplayCase.isEngram( position ) ) {
            int quantity = mDisplayCase.getQuantity( position );

            if ( quantity > 0 ) {
                viewHolder.getImage().setBackgroundColor( ContextCompat.getColor( getContext(), R.color.crafting_queue_background ) );
                viewHolder.getQuantityText().setText( String.format( Locale.US, "x%d", quantity ) );
                viewHolder.getNameText().setSingleLine( true );
            } else {
                viewHolder.getImage().setBackgroundColor( ContextCompat.getColor( getContext(), R.color.displaycase_engram_background ) );
                viewHolder.getQuantityText().setText( null );
                viewHolder.getNameText().setSingleLine( false );
            }
        } else {
            viewHolder.getImage().setBackgroundColor( 0 );
            viewHolder.getQuantityText().setText( null );
            viewHolder.getNameText().setSingleLine( false );
        }
    }

    @Override
    public int getItemCount() {
        return mDisplayCase.getCount();
    }

    /**
     * -- PUBLIC UTILITY METHODS --
     */

    public long getEngramId( int position ) {
        return mDisplayCase.getEngramId( position );
    }

    public boolean isEngram( int position ) {
        return mDisplayCase.isEngram( position );
    }

    public long getLevel() {
        return mDisplayCase.getLevel();
    }

    public long getParent() {
        return mDisplayCase.getParent();
    }

    public void changeCategory( int position ) {
        mDisplayCase.changeCategory( position );

        Refresh();
    }

    public boolean isFiltered() {
        return mDisplayCase.isFiltered();
    }

    public void setFiltered( boolean isFiltered ) {
        mDisplayCase.setIsFiltered( isFiltered );
    }

    /**
     * -- UTILITY METHODS --
     */

    public void Refresh() {
        mDisplayCase.UpdateQueues();

        this.notifyDataSetChanged();
    }

    private Context getContext() {
        return mContext;
    }
}
