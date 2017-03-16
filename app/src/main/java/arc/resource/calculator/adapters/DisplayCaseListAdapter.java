package arc.resource.calculator.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.DisplayCaseListener;
import arc.resource.calculator.model.DisplayCase;
import arc.resource.calculator.model.engram.DisplayEngram;
import arc.resource.calculator.util.ListenerUtil;

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
public class DisplayCaseListAdapter extends RecyclerView.Adapter<DisplayCaseListAdapter.ViewHolder>
        implements DisplayCaseListener {
    private static final String TAG = DisplayCaseListAdapter.class.getSimpleName();

    private final int INVALID_ITEM_ID = -1;
    private final int INVALID_ITEM_POSITION = -1;

    private static DisplayCaseListAdapter sInstance;

    private DisplayCase mDisplayCase;

    private ListenerUtil mCallback;

//    private boolean mImageSizeResolved;

    public static DisplayCaseListAdapter getInstance( Context context, boolean didUpdate ) {
        if ( sInstance == null ) {
            sInstance = new DisplayCaseListAdapter( context, didUpdate );
        }

        return sInstance;
    }

    private DisplayCaseListAdapter( Context context, boolean didUpdate ) {
        mDisplayCase = DisplayCase.getInstance( context, didUpdate );
        mCallback = ListenerUtil.getInstance();

        mCallback.addDisplayCaseListener( this );
    }

    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_engram_thumbnail, parent, false );

        return new ViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, int position ) {
        View view = holder.itemView;
        Context context = view.getContext();

//        if ( !mImageSizeResolved ) {
//            view.measure( UNSPECIFIED, UNSPECIFIED );
//            int width = view.getMeasuredWidth();
//            float scale = context.getResources().getDisplayMetrics().density;
//            float scaledWidth = width * scale;
//
//            Log.d( TAG, "onBindViewHolder.imageSizeResolved: " + width + ", " + scale + ", " + scaledWidth );
//
//            DisplayUtil.getInstance( context ).setImageSize( scaledWidth );
//        }

        final String imagePath = "file:///android_asset/" + mDisplayCase.getImagePathByPosition( position );
        Picasso.with( context )
                .load( imagePath )
                .placeholder( R.drawable.placeholder_empty )
                .into( holder.getImageView() );

        holder.getName().setText( mDisplayCase.getNameByPosition( position ) );

        if ( isEngram( position ) ) {
            int quantity = mDisplayCase.getQuantityWithYieldByPosition( position );

            if ( quantity > 0 ) {
                holder.getImageView().setBackground( ContextCompat.getDrawable( context, R.drawable.frame_engram_crafting_queue ) );
                holder.getQuantity().setText( String.format( Locale.US, "x%d", quantity ) );
                holder.getName().setMaxLines( 1 );
            } else {
                holder.getImageView().setBackground( ContextCompat.getDrawable( context, R.drawable.frame_engram_display_case ) );
                holder.getQuantity().setText( null );
                holder.getName().setMaxLines( 3 );
            }
        } else {
            holder.getImageView().setBackgroundColor( 0 );
            holder.getQuantity().setText( null );
            holder.getName().setMaxLines( 3 );
        }
    }

    @Override
    public long getItemId( int position ) {
        long _id = mDisplayCase.getIdByPosition( position );

        return _id != INVALID_ITEM_ID ? _id : super.getItemId( position );
    }

    @Override
    public int getItemCount() {
        return mDisplayCase.getCount();
    }

    /**
     * -- PUBLIC UTILITY METHODS --
     */

    private DisplayEngram getEngram( int position ) {
        return mDisplayCase.getEngram( position );
    }

    private boolean isEngram( int position ) {
        return mDisplayCase.isEngram( position );
    }

    public long getLevel() {
        return mDisplayCase.getCurrentCategoryLevel();
    }

    public long getParent() {
        return mDisplayCase.getCurrentCategoryParent();
    }

    public long getStationId() {
        return mDisplayCase.getCurrentStationId();
    }

    private void changeCategory( Context context, int position ) {
        mDisplayCase.changeCategory( context, position );
    }

    private void changeStation( Context context, int position ) {
        mDisplayCase.changeStation( context, position );
    }

    /**
     * -- UTILITY METHODS --
     */

    public String getHierarchicalText( Context context ) {
        return mDisplayCase.buildHierarchicalText( context );
    }

    private boolean isCategory( int position ) {
        return mDisplayCase.isCategory( position );
    }

    private boolean isStation( int position ) {
        return mDisplayCase.isStation( position );
    }

    @Override
    public void onRequestResetCategoryLevels( Context context ) {
    }

    @Override
    public void onRequestSaveCategoryLevels( Context context ) {

    }

    @Override
    public void onRequestCategoryHierarchy( Context context ) {

    }

    @Override
    public void onRequestSearch( Context context, String searchText ) {

    }

    @Override
    public void onItemChanged( int position ) {
        notifyItemChanged( position );
    }

    @Override
    public void onRequestDisplayCaseDataSetChange( Context context ) {
        // do nothing
    }

    @Override
    public void onDisplayCaseDataSetChanged( Context context ) {
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private final String TAG = ViewHolder.class.getSimpleName();

        private ImageView mImageView;
        private TextView mName;
        private TextView mQuantity;

        ViewHolder( View view ) {
            super( view );

            mImageView = ( ImageView ) view.findViewById( R.id.list_item_engram_thumbnail_image_view );
            mName = ( TextView ) view.findViewById( R.id.list_item_engram_thumbnail_name_text_view );
            mQuantity = ( TextView ) view.findViewById( R.id.list_item_engram_thumbnail_quantity_text_view );

            view.setOnClickListener( this );
            view.setOnLongClickListener( this );
        }

        ImageView getImageView() {
            return mImageView;
        }

        public TextView getName() {
            return mName;
        }

        public TextView getQuantity() {
            return mQuantity;
        }

        @Override
        public void onClick( View view ) {
            int position = getAdapterPosition();
            Context context = view.getContext();

            if ( position < getItemCount() ) {
                if ( isEngram( position ) ) {
                    mCallback.requestIncreaseQuantity( context, mDisplayCase.getIdByPosition( position ) );
                } else if ( isCategory( position ) ) {
                    changeCategory( context, position );
                } else if ( isStation( position ) ) {
                    changeStation( context, position );
                } else {
                    throw new ArrayIndexOutOfBoundsException( position );
                }
            }
        }

        @Override
        public boolean onLongClick( View view ) {
            return !isEngram( getAdapterPosition() );
        }
    }
}