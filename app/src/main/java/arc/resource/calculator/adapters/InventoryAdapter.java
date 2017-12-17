package arc.resource.calculator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
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
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.InventoryMap;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.model.exception.CursorEmptyException;
import arc.resource.calculator.model.exception.CursorNullException;
import arc.resource.calculator.model.exception.PositionOutOfBoundsException;
import arc.resource.calculator.model.resource.CompositeResource;
import arc.resource.calculator.model.resource.Resource;
import arc.resource.calculator.util.PrefsUtil;
import arc.resource.calculator.views.switchers.listeners.Observer;

import static android.support.v7.widget.RecyclerView.NO_ID;

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

    private Observer mViewObserver;

    private InventoryMap mMap;

    private FetchInventoryTask mTask;

    private boolean mNeedsUpdate;

    public InventoryAdapter( Context context, Observer observer ) {
        setContext( context );
        setObserver( observer );
        setMap( new InventoryMap() );

        executeTask();
    }

    private Context getContext() {
        return mContext;
    }

    private void setContext( Context context ) {
        this.mContext = context;
    }

    private InventoryMap getMap() {
        return mMap;
    }

    private void setMap( InventoryMap inventory ) {
        this.mMap = inventory;
    }

    private Observer getObserver() {
        return mViewObserver;
    }

    private void setObserver( Observer observer ) {
        mViewObserver = observer;
    }

    private void setTask( FetchInventoryTask task ) {
        mTask = task;
    }

    private FetchInventoryTask getTask() {
        return mTask;
    }

    private void cancelTask() {
        getTask().cancel( true );
    }

    private void executeTask() {
        cancelTask();

        setTask( new FetchInventoryTask() );
        getTask().execute();
    }

    /**
     * Out of service.
     * <p>
     * Used to be used in communications with a QueueObserver.
     */
    public void resume() {
    }

    /**
     * Out of service.
     * <p>
     * Used to be used in communications with a QueueObserver.
     */
    public void pause() {
    }

    /**
     * Out of service.
     * <p>
     * Used to be used in communications with a QueueObserver.
     */
    public void destroy() {
    }

    /**
     * Requests an object by position from the associated Map of this Adapter
     *
     * @param position Position in Map to which the object resides
     * @return CompositeResource object
     */
    private CompositeResource getResource( int position ) throws PositionOutOfBoundsException {
        return getMap().getAt( position );
    }

    private Cursor query( Uri uri ) {
        return getContext().getContentResolver().query( uri, null, null, null, null );
    }

    private Resource QueryForResource( Uri uri ) {
        try ( Cursor cursor = query( uri ) ) {
            if ( cursor == null )
                throw new CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new CursorEmptyException( uri );

            long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ResourceEntry._ID ) );
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_NAME ) );
            String folder = cursor.getString( cursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_IMAGE_FOLDER ) );
            String file = cursor.getString( cursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_IMAGE_FILE ) );

            return new Resource( _id, name, folder, file );
        }
    }

    private long QueryForComplexResource( Uri uri ) {
        try ( Cursor cursor = query( uri ) ) {
            if ( cursor == null )
                throw new CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                return NO_ID;

//            long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry._ID ) );
            long engram_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY ) );
//            long resource_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY ) );

            return engram_id;
        }
    }

    private long QueryForStationId( Uri uri ) {
        try ( Cursor cursor = query( uri ) ) {
            if ( cursor == null )
                throw new CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                return NO_ID;

//            long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry._ID ) );
            long engram_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY ) );
//            long resource_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY ) );

            return engram_id;
        }
    }

    private InventoryMap QueryForComposition( Uri uri ) throws PositionOutOfBoundsException {
        return QueryForComposition( uri, true );
    }

    private InventoryMap QueryForComposition( Uri uri, boolean showRawMaterials ) throws PositionOutOfBoundsException {
        long dlc_id = DatabaseContract.CompositionEntry.getDLCIdFromUri( uri );

        try ( Cursor cursor = query( uri ) ) {
            if ( cursor == null )
                throw new CursorNullException( uri );

            InventoryMap inventoryMap = new InventoryMap();
            while ( cursor.moveToNext() ) {
                long resource_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY ) );
                int quantity = cursor.getInt( cursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_QUANTITY ) );

                // check if refined filter is enabled
                if ( showRawMaterials ) {
                    Log.d( TAG, "QueryForComposition: showRawMaterials" );
                    // check if resource_id is complex
                    long engram_id = QueryForComplexResource( DatabaseContract.ComplexResourceEntry.buildUriWithResourceId( resource_id ) );

                    // if complex, return engram_id
                    if ( engram_id > NO_ID ) {
                        // engram_id, station_id, composition
                        long station_id = QueryForStationId( DatabaseContract.EngramEntry.buildUriWithId( dlc_id, engram_id ) );

                        // query for composition with update uri built with engram_id
                        InventoryMap composition = QueryForComposition( DatabaseContract.CompositionEntry.buildUriWithEngramId( dlc_id, engram_id ), true );

                        // iterate through composition map
                        for ( int j = 0; j < composition.size(); j++ ) {
                            CompositeResource tempResource = composition.getAt( j );

                            CompositeResource resource = inventoryMap.get( tempResource.getId() );
                            if ( resource == null )
                                resource = new CompositeResource(
                                        tempResource.getId(),
                                        tempResource.getName(),
                                        tempResource.getFolder(),
                                        tempResource.getFile(),
                                        tempResource.getQuantity() * quantity );
                            else
                                resource.increaseQuantity( tempResource.getQuantity() * quantity );

                            inventoryMap.put( resource.getId(), resource );
                        }
                    } else {
                        inventoryMap.add( resource_id,
                                new CompositeResource( QueryForResource( DatabaseContract.ResourceEntry.buildUriWithId( dlc_id, resource_id ) ),
                                        quantity ) );
                    }
                } else {
                    inventoryMap.add( resource_id,
                            new CompositeResource( QueryForResource( DatabaseContract.ResourceEntry.buildUriWithId( dlc_id, resource_id ) ),
                                    quantity ) );
                }
            }

            return inventoryMap;
        }
    }

    private void fetchInventory() {
        mTask.cancel( true );

        new FetchInventoryTask().execute();
    }

    /**
     * AsyncTask used when initializing data set with new data accrued from the Crafting Queue.
     * <p>
     * Used strictly for initializing -- once data is initialized, all this class needs to do is work off its supplied data.
     */
    private class FetchInventoryTask extends AsyncTask<Void, Void, Boolean> {
        private final String TAG = FetchInventoryTask.class.getSimpleName();

        private InventoryMap mTempInventoryMap;

        private Exception mException;

        FetchInventoryTask() {
            this.mTempInventoryMap = new InventoryMap();
            this.mException = null;
        }

        @Override
        protected void onPreExecute() {
            getObserver().notifyInitializing();
        }

        @Override
        protected void onPostExecute( Boolean querySuccessful ) {
            if ( querySuccessful ) {
                setMap( mTempInventoryMap );

                notifyDataSetChanged();

                mNeedsUpdate = false;

                if ( mTempInventoryMap.size() > 0 ) {
                    getObserver().notifyDataSetPopulated();
                } else {
                    getObserver().notifyDataSetEmpty();
                }
            } else {
                if ( mException != null )
                    getObserver().notifyExceptionCaught( mException );
            }
        }

        @Override
        protected Boolean doInBackground( Void... params ) {

            try {
                long dlc_id = PrefsUtil.getInstance( mContext ).getDLCPreference();

                CraftingQueue craftingQueue = CraftingQueue.getInstance();

                for ( int i = 0; i < craftingQueue.getSize(); i++ ) {
                    if ( isCancelled() )
                        return false;

                    QueueEngram queueEngram = craftingQueue.getCraftable( i );

                    InventoryMap composition =
                            QueryForComposition( DatabaseContract.CompositionEntry.buildUriWithEngramId( dlc_id, queueEngram.getId() ) );

                    for ( int j = 0; j < composition.size(); j++ ) {
                        CompositeResource tempResource = composition.getAt( j );

                        CompositeResource resource = mTempInventoryMap.get( tempResource.getId() );
                        if ( resource == null )
                            resource = new CompositeResource(
                                    tempResource.getId(),
                                    tempResource.getName(),
                                    tempResource.getFolder(),
                                    tempResource.getFile(),
                                    tempResource.getQuantity() * queueEngram.getQuantity() );
                        else
                            resource.increaseQuantity( tempResource.getQuantity() * queueEngram.getQuantity() );

                        mTempInventoryMap.put( resource.getId(), resource );
                    }
                }

                mTempInventoryMap.sort();
                return true;
            } catch ( Exception e ) {
                mException = e;
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
        try {
            CompositeResource resource = getResource( position );

            String imagePath = "file:///android_asset/" + resource.getImagePath();
            Picasso.with( getContext() )
                    .load( imagePath )
                    .error( R.drawable.placeholder_empty )
                    .placeholder( R.drawable.placeholder_empty )
                    .into( holder.getImageView() );

            holder.getNameView().setText( resource.getName() );
            holder.getQuantityView().setText( String.format( Locale.getDefault(), "x%d", resource.getQuantity() ) );
        } catch ( PositionOutOfBoundsException e ) {
            getObserver().notifyExceptionCaught( e );
        }
    }

    @Override
    public int getItemCount() {
        return getMap().size();
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
}