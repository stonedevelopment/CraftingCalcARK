package arc.resource.calculator.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.model.Category;
import arc.resource.calculator.model.DisplayCase;
import arc.resource.calculator.model.engram.DisplayEngram;
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
public class DisplayCaseListAdapter extends RecyclerView.Adapter<EngramGridViewHolder> {
    private static final String TAG = DisplayCaseListAdapter.class.getSimpleName();

    private DisplayCase mDisplayCase;
    private Context mContext;

    public DisplayCaseListAdapter( Context context ) {
        this.mContext = context.getApplicationContext();
        this.mDisplayCase = new DisplayCase( context );
    }

    public EngramGridViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_engram_thumbnail, parent, false );

        return new EngramGridViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( final EngramGridViewHolder holder, int position ) {

        int imageId = getContext().getResources().getIdentifier( mDisplayCase.getDrawable( position ), "drawable", getContext().getPackageName() );
        holder.getThumbnail().setImageResource( imageId );

        String name = mDisplayCase.getNameByPosition( position );
        holder.getName().setText( name );

        if ( mDisplayCase.isEngram( position ) ) {
            int quantity = mDisplayCase.getQuantityWithYield( position );

            if ( quantity > 0 ) {
                holder.getThumbnail().setBackground( ContextCompat.getDrawable( getContext(), R.drawable.frame_engram_crafting_queue ) );
                holder.getQuantity().setText( String.format( Locale.US, "x%d", quantity ) );
                holder.getName().setSingleLine( true );
            } else {
                holder.getThumbnail().setBackground( ContextCompat.getDrawable( getContext(), R.drawable.frame_engram_display_case ) );
                holder.getQuantity().setText( null );
                holder.getName().setSingleLine( false );
            }
        } else {
            holder.getThumbnail().setBackgroundColor( 0 );
            holder.getQuantity().setText( null );
            holder.getName().setSingleLine( false );
        }
    }

    @Override
    public int getItemCount() {
        return mDisplayCase.getCount();
    }

    /**
     * -- PUBLIC UTILITY METHODS --
     */

    public DisplayEngram getEngram( int position ) {
        return mDisplayCase.getEngram( position );
    }

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

    public Category getCategoryDetails( long categoryId ) {
        return mDisplayCase.getCategory( categoryId );
    }

    public void changeCategory( int position ) {
        mDisplayCase.changeCategory( position );
    }

    public void setLevelToRoot() {
        mDisplayCase.setLevelToRoot();
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

    public void refreshData() {
        mDisplayCase.UpdateData();

        this.notifyDataSetChanged();
    }

    public void refreshItem( int position ) {
        notifyItemChanged( position );
    }

    public void refreshItemByEngramId( int position ) {
        if ( isEngram( position ) ) {
            notifyItemChanged( position );
        }
    }

    private Context getContext() {
        return mContext;
    }
}
