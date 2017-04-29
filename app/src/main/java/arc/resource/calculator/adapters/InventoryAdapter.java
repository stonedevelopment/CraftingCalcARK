package arc.resource.calculator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.listeners.QueueObserver;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.model.resource.CompositeResource;
import arc.resource.calculator.model.resource.Resource;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;
import arc.resource.calculator.views.InventoryRecyclerView;

import static arc.resource.calculator.adapters.InventoryAdapter.Status.HIDDEN;
import static arc.resource.calculator.adapters.InventoryAdapter.Status.VISIBLE;

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
public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private static final String TAG = InventoryAdapter.class.getSimpleName();

    private Context mContext;

    private InventoryRecyclerView.Observer mViewObserver;

    private InventorySparseArray mInventory;

    private Status mViewStatus;

    private boolean mNeedsUpdate;

    enum Status {VISIBLE, HIDDEN}

    public InventoryAdapter( Context context, InventoryRecyclerView.Observer observer ) {
        setContext( context );
        setObserver( observer );
        setInventory( new InventorySparseArray() );

        QueueObserver.getInstance().registerListener( TAG, new QueueObserver.Listener() {
            public void onDataSetPopulated() {
                if ( mViewStatus == VISIBLE ) {
                    fetchInventory();
                } else {
                    mNeedsUpdate = true;
                }
            }

            @Override
            public void onItemChanged( long craftableId, int quantity ) {
                if ( mViewStatus == VISIBLE ) {
                    fetchInventory();
                } else {
                    mNeedsUpdate = true;
                }
            }

            @Override
            public void onItemRemoved( long craftableId ) {
                if ( mViewStatus == VISIBLE ) {
                    fetchInventory();
                } else {
                    mNeedsUpdate = true;
                }
            }

            @Override
            public void onDataSetEmpty() {
                if ( mViewStatus == VISIBLE ) {
                    fetchInventory();
                } else {
                    mNeedsUpdate = true;
                }
            }
        } );
    }

    private Context getContext() {
        return mContext;
    }

    private void setContext( Context context ) {
        this.mContext = context;
    }

    private InventorySparseArray getInventory() {
        return mInventory;
    }

    private void setInventory( InventorySparseArray inventory ) {
        this.mInventory = inventory;
    }

    private InventoryRecyclerView.Observer getObserver() {
        return mViewObserver;
    }

    private void setObserver( InventoryRecyclerView.Observer observer ) {
        mViewObserver = observer;
    }

    public void resume() {
        mViewStatus = VISIBLE;

        if ( mNeedsUpdate )
            fetchInventory();
    }

    public void pause() {
        mViewStatus = HIDDEN;
    }

    public void destroy() {
        QueueObserver.getInstance().unregisterListener( TAG );
    }

    private CompositeResource getResource( int position ) {
        return getInventory().valueAt( position );
    }

    private Cursor query( Uri uri ) {
        return getContext().getContentResolver().query( uri, null, null, null, null );
    }

    private Resource QueryForResource( Uri uri ) throws Exception {
        try ( Cursor cursor = query( uri ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ResourceEntry._ID ) );
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_NAME ) );
            String folder = cursor.getString( cursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_IMAGE_FOLDER ) );
            String file = cursor.getString( cursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_IMAGE_FILE ) );

            return new Resource( _id, name, folder, file );
        }
    }

    private LongSparseArray<CompositeResource> QueryForComposition( Uri uri ) throws Exception {
        long dlc_id = DatabaseContract.CompositionEntry.getDLCIdFromUri( uri );

        try ( Cursor cursor = query( uri ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            LongSparseArray<CompositeResource> resources = new LongSparseArray<>();
            while ( cursor.moveToNext() ) {
                long resource_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY ) );
                int quantity = cursor.getInt( cursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_QUANTITY ) );

                resources.put( resource_id, new CompositeResource(
                        QueryForResource( DatabaseContract.ResourceEntry.buildUriWithId( dlc_id, resource_id ) ),
                        quantity ) );
            }

            return resources;
        }
    }

    private void fetchInventory() {
        new FetchInventoryTask().execute();
    }

    /**
     * AsyncTask used when initializing data set with new data accrued from the Crafting Queue.
     * <p>
     * Used strictly for initializing -- once data is initialized, all this class needs to do is work off its supplied data.
     */
    private class FetchInventoryTask extends AsyncTask<Void, Void, Boolean> {
        private final String TAG = FetchInventoryTask.class.getSimpleName();

        private InventorySparseArray mArray;

        FetchInventoryTask() {
            this.mArray = new InventorySparseArray();
        }

        @Override
        protected void onPreExecute() {
            getObserver().notifyInitializing();
        }

        @Override
        protected void onPostExecute( Boolean querySuccessful ) {
            if ( querySuccessful ) {
                setInventory( mArray );

                notifyDataSetChanged();

                mNeedsUpdate = false;

                if ( mArray.size() > 0 ) {
                    getObserver().notifyDataSetPopulated();
                } else {
                    getObserver().notifyDataSetEmpty();
                }
            }
        }

        @Override
        protected Boolean doInBackground( Void... params ) {

            try {
                long dlc_id = PrefsUtil.getInstance().getDLCPreference();

                LongSparseArray<QueueEngram> craftables = CraftingQueue.getInstance().getQueue();

                InventorySparseArray inventory = new InventorySparseArray();
                for ( int i = 0; i < craftables.size(); i++ ) {
                    QueueEngram craftable = craftables.valueAt( i );

                    LongSparseArray<CompositeResource> composition =
                            QueryForComposition( DatabaseContract.CompositionEntry.buildUriWithEngramId( dlc_id, craftable.getId() ) );

                    for ( int j = 0; j < composition.size(); j++ ) {
                        CompositeResource tempResource = composition.valueAt( j );

                        CompositeResource resource = inventory.get( tempResource.getId() );
                        if ( resource == null )
                            resource = new CompositeResource(
                                    tempResource.getId(),
                                    tempResource.getName(),
                                    tempResource.getFolder(),
                                    tempResource.getFile(),
                                    tempResource.getQuantity() * craftable.getQuantity() );
                        else
                            resource.increaseQuantity( tempResource.getQuantity() * craftable.getQuantity() );

                        inventory.put( resource.getId(), resource );
                    }
                }

                inventory.sort();

                mArray = inventory;

                return true;
            } catch ( Exception e ) {
                getObserver().notifyExceptionCaught( e );

                return false;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from( parent.getContext() ).
                inflate( R.layout.list_item_inventory, parent, false );

        return new ViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position ) {
        CompositeResource resource = getResource( position );

        String imagePath = "file:///android_asset/" + resource.getImagePath();
        Picasso.with( getContext() )
                .load( imagePath )
                .error( R.drawable.placeholder_empty )
                .placeholder( R.drawable.placeholder_empty )
                .into( holder.getImageView() );

        holder.getNameView().setText( resource.getName() );
        holder.getQuantityView().setText( String.format( Locale.getDefault(), "x%d", resource.getQuantity() ) );
    }

    @Override
    public int getItemCount() {
        return getInventory().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageView;
        private final TextView mNameView;
        private final TextView mQuantityView;

        public ViewHolder( View view ) {
            super( view );

            mImageView = ( ImageView ) view.findViewById( R.id.image_view );
            mNameView = ( TextView ) view.findViewById( R.id.name_view );
            mQuantityView = ( TextView ) view.findViewById( R.id.quantity_view );
        }

        ImageView getImageView() {
            return mImageView;
        }

        TextView getNameView() {
            return mNameView;
        }

        TextView getQuantityView() {
            return mQuantityView;
        }
    }

    private class InventorySparseArray extends LongSparseArray<CompositeResource> {

        /**
         * Constructor that, by default, will set its element size to 0.
         */
        InventorySparseArray() {
            super( 0 );
        }

        /**
         * Constructor that sets its element size per the supplied amount.
         *
         * @param size Amount of elements the array requires to allocate.
         */
        InventorySparseArray( int size ) {
            super( size );
        }

        /**
         * Sorts objects by Name
         */
        void sort() {
            boolean swapped = true;
            while ( swapped ) {
                swapped = false;
                for ( int i = 0; i < size() - 1; i++ ) {
                    String first = valueAt( i ).getName();
                    String second = valueAt( i + 1 ).getName();
                    if ( first.compareTo( second ) > 0 ) {
                        // swap
                        CompositeResource tempResource = valueAt( i + 1 );
                        setValueAt( i + 1, valueAt( i ) );
                        setValueAt( i, tempResource );
                        swapped = true;
                    }
                }
            }
        }

        boolean contains( long key ) {
            return !( indexOfKey( key ) < 0 );
        }
    }
}