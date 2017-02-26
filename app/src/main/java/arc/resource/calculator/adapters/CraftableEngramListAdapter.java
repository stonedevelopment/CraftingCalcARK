package arc.resource.calculator.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.CraftingQueueListener;
import arc.resource.calculator.listeners.QueueEngramListener;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.engram.QueueEngram;
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
public class CraftableEngramListAdapter extends RecyclerView.Adapter<CraftableEngramListAdapter.ViewHolder>
        implements CraftingQueueListener, QueueEngramListener {
    private static final String TAG = CraftableEngramListAdapter.class.getSimpleName();

    private static final long INVALID_ITEM_ID = -1;
    private static final long INVALID_ITEM_POSITION = -1;

    private static CraftableEngramListAdapter sInstance;

    private CraftingQueue mCraftingQueue;
    private ListenerUtil mCallback;

    public static CraftableEngramListAdapter getInstance( Context context ) {
        if ( sInstance == null ) {
            sInstance = new CraftableEngramListAdapter( context );
        }

        return sInstance;
    }

    private CraftableEngramListAdapter( Context context ) {
        mCraftingQueue = CraftingQueue.getInstance( context );
        mCallback = ListenerUtil.getInstance();

        mCallback.addCraftingQueueListener( this );
        mCallback.addCraftingQueueEngramListener( this );
    }

    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_craftable_engram_thumbnail, parent, false );

        return new ViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position ) {
        View view = holder.itemView;
        Context context = view.getContext();

        QueueEngram engram = getEngram( position );

        String imagePath = "file:///android_asset/" + engram.getImagePath();
        Picasso.with( context )
                .load( imagePath )
                .placeholder( R.drawable.placeholder_empty )
                .into( holder.getImageView() );

        holder.getName().setText( engram.getName() );
        holder.getQuantity().setText( String.format( Locale.US, "x%d", engram.getQuantityWithYield() ) );
    }

    @Override
    public long getItemId( int position ) {
        try {
            return getEngram( position ).getId();
        } catch ( Exception e ) {
            return INVALID_ITEM_ID;
        }
    }

    @Override
    public int getItemCount() {
        return mCraftingQueue.getEngramItemCount();
    }

    private QueueEngram getEngram( int position ) {
        return mCraftingQueue.getEngramByPosition( position );
    }

    @Override
    public void onRequestRemoveOneFromQueue( Context context, long engram_id ) {

    }

    @Override
    public void onRequestRemoveAllFromQueue( Context context ) {

    }

    @Override
    public void onRequestIncreaseQuantity( Context context, int position ) {

    }

    @Override
    public void onRequestIncreaseQuantity( Context context, long engram_id ) {

    }

    @Override
    public void onRequestUpdateQuantity( Context context, long engram_id, int quantity ) {

    }

    @Override
    public void onRowInserted( Context context, long queueId, long engramId, int quantity, boolean wasQueueEmpty ) {
        int position = mCraftingQueue.getPositionByEngramId( engramId );

        if ( position != INVALID_ITEM_POSITION )
            notifyItemInserted( position );
        else
            notifyDataSetChanged();

        if ( wasQueueEmpty )
            mCallback.requestLayoutUpdate();
    }

    @Override
    public void onRowUpdated( Context context, long queueId, long engramId, int quantity ) {
        int position = mCraftingQueue.getPositionByEngramId( engramId );

        if ( position != INVALID_ITEM_POSITION )
            notifyItemChanged( position );
        else
            notifyDataSetChanged();
    }

    @Override
    public void onRowDeleted( Context context, Uri uri, int positionStart, int itemCount, boolean isQueueEmpty ) {
        Log.d( TAG, "onRowDeleted(): " + uri + ", " + positionStart + ", " + itemCount + ", " + isQueueEmpty );
        notifyItemRangeRemoved( positionStart, itemCount );

        if ( isQueueEmpty )
            mCallback.requestLayoutUpdate();
    }

    @Override
    public void onEngramDataSetChangeRequest( Context context ) {
        // do nothing
    }

    @Override
    public void onEngramDataSetChanged( boolean isQueueEmpty ) {
        notifyDataSetChanged();

        if ( isQueueEmpty )
            mCallback.requestLayoutUpdate();
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
            mCallback.requestIncreaseQuantity( view.getContext(), getAdapterPosition() );
        }

        @Override
        public boolean onLongClick( View view ) {
            return false;
        }
    }
}