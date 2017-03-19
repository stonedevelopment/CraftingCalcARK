package arc.resource.calculator.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import arc.resource.calculator.listeners.CraftingQueueListener;
import arc.resource.calculator.listeners.QueueResourceListener;
import arc.resource.calculator.util.ListenerUtil;
import arc.resource.calculator.R;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.resource.CompositeResource;

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
public class CraftableResourceListAdapter extends RecyclerView.Adapter<CraftableResourceListAdapter.ViewHolder>
        implements CraftingQueueListener, QueueResourceListener {
    private static final String TAG = CraftableResourceListAdapter.class.getSimpleName();

    private static CraftableResourceListAdapter sInstance;

    private final CraftingQueue mCraftingQueue;

    private final ListenerUtil mCallback;

    public static CraftableResourceListAdapter getInstance( Context context ) {
        if ( sInstance == null ) {
            sInstance = new CraftableResourceListAdapter( context );
        }

        return sInstance;
    }

    private CraftableResourceListAdapter( Context context ) {
        mCraftingQueue = CraftingQueue.getInstance( context );
        mCallback = ListenerUtil.getInstance();

        mCallback.addCraftingQueueListener( this );
        mCallback.addCraftingQueueResourceListener( this );
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_resource, parent, false );

        return new ViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position ) {
        Context context = holder.getView().getContext();

        CompositeResource resource = getResource( position );

        String imagePath = "file:///android_asset/" + resource.getImagePath();
        Picasso.with( context ).load( imagePath ).into( holder.getThumbnail() );

        String name = resource.getName();
        holder.getName().setText( name );

        int quantity = resource.getQuantity();
        holder.getQuantity().setText( String.format( Locale.US, "%1$d", quantity ) );
    }

    @Override
    public int getItemCount() {
        return mCraftingQueue.getResourceItemCount();
    }

    private CompositeResource getResource( int position ) {
        return mCraftingQueue.getResourceByPosition( position );
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
    public void onRowInserted( Context context, long queue_id, long engram_id, int quantity, boolean wasQueueEmpty ) {
        mCallback.requestResourceDataSetChange( context );
    }

    @Override
    public void onRowUpdated( Context context, long queue_id, long engram_id, int quantity ) {
        mCallback.requestResourceDataSetChange( context );
    }

    @Override
    public void onRowDeleted( Context context, Uri uri, int positionStart, int itemCount, boolean isQueueEmpty ) {
        mCallback.requestResourceDataSetChange( context );
    }

    @Override
    public void onResourceDataSetChangeRequest( Context context ) {
        // do nothing
    }

    @Override
    public void onResourceDataSetChanged() {
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;

        private final ImageView mThumbnail;
        private final TextView mName;
        private final TextView mQuantity;

        public ViewHolder( View view ) {
            super( view );

            mView = view;

            mThumbnail = ( ImageView ) view.findViewById( R.id.resource_list_imageView );
            mName = ( TextView ) view.findViewById( R.id.resource_list_nameText );
            mQuantity = ( TextView ) view.findViewById( R.id.resource_list_quantityText );
        }

        public View getView() {
            return mView;
        }

        public ImageView getThumbnail() {
            return mThumbnail;
        }

        public TextView getName() {
            return mName;
        }

        public TextView getQuantity() {
            return mQuantity;
        }
    }
}
