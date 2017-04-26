package arc.resource.calculator.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.util.LongSparseArray;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.model.engram.DisplayEngram;
import arc.resource.calculator.model.resource.CompositeResource;
import arc.resource.calculator.model.resource.QueueResource;
import arc.resource.calculator.model.resource.Resource;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;
import arc.resource.calculator.util.Util;

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
public class Showcase {
    private static final String TAG = Showcase.class.getSimpleName();

    private Context mContext;

    private ShowcaseEntry mShowcaseEntry;

    public Showcase( Context context, long _id ) throws ExceptionUtil.CursorEmptyException, ExceptionUtil.CursorNullException {
        long dlc_id = PrefsUtil.getInstance().getDLCPreference();

        setContext( context );
        setShowcaseEntry( QueryForDetails( context, dlc_id, _id ) );
    }

    private void setShowcaseEntry( ShowcaseEntry showcaseEntry ) {
        if ( showcaseEntry == null )
            Log.d( TAG, "showcaseEntry is null?" );

        mShowcaseEntry = showcaseEntry;
    }

    public ShowcaseEntry getShowcaseEntry() {
        return mShowcaseEntry;
    }

    private void setContext( Context context ) {
        this.mContext = context.getApplicationContext();
    }

    private Context getContext() {
        return mContext;
    }

    public long getId() {
        return getShowcaseEntry().getCraftable().getId();
    }

    public String getImagePath() {
        return getShowcaseEntry().getCraftable().getImagePath();
    }

    public String getName() {
        return getShowcaseEntry().getCraftable().getName();
    }

    public QueueResource getResource( int position ) throws Exception {
        return getShowcaseEntry().getResource( position );
    }

    public int getQuantity() {
        return getShowcaseEntry().getCraftable().getQuantity();
    }

    public String getQuantityText() {
        return String.valueOf( getShowcaseEntry().getCraftable().getQuantityWithYield() );
    }

    public int getRequiredLevel() {
        return getShowcaseEntry().getRequiredLevel();
    }

    public String getDLCName() throws ExceptionUtil.CursorEmptyException, ExceptionUtil.CursorNullException {
        return QueryForDLCName( getContext(), getShowcaseEntry().getDLCId() );
    }

    public String getDescription() {
        return getShowcaseEntry().getDescription();
    }

    public String getStationNameArrayAsText() {
        Log.d( TAG, "getStationNameArrayAsText: " + getShowcaseEntry().getStations().toString() );

        int size = getShowcaseEntry().getStations().size();

        if ( size == 1 )
            return getShowcaseEntry().getStations().valueAt( 0 ).getName();

        if ( size == 2 )
            return String.format( getContext().getString( R.string.content_detail_crafted_in_double_format ),
                    getShowcaseEntry().getStations().valueAt( 0 ).getName(),
                    getShowcaseEntry().getStations().valueAt( 1 ).getName() );

        StringBuilder builder = new StringBuilder();
        for ( int i = 0; i < size; i++ ) {
            if ( i > 0 ) {
                if ( i == size - 1 )
                    builder.append( String.format( getContext().getString( R.string.content_detail_crafted_in_multiple_last_format ),
                            getShowcaseEntry().getStations().valueAt( i ).getName() ) );
                else
                    builder.append( String.format( getContext().getString( R.string.content_detail_crafted_in_multiple_format ),
                            getShowcaseEntry().getStations().valueAt( i ).getName() ) );
            } else
                builder.append( getShowcaseEntry().getStations().valueAt( i ).getName() );
        }

        return builder.toString();
    }

    public String getCategoryHierarchy( Context context )
            throws ExceptionUtil.CursorEmptyException, ExceptionUtil.CursorNullException {
        Category category = QueryForCategoryDetails( context, getShowcaseEntry().getDLCId(), getShowcaseEntry().getCraftable().getCategoryId() );

        if ( category == null ) return null;

        long parent_id = category.getParent();

        StringBuilder builder = new StringBuilder( category.getName() );
        while ( parent_id > 0 ) {
            category = QueryForCategoryDetails( context, getShowcaseEntry().getDLCId(), parent_id );
            if ( category == null ) break;

            parent_id = category.getParent();

            builder.insert( 0, category.getName() + "/" );
        }

        return builder.toString();
    }

    public void updateQuantifiableComposition() {
        getShowcaseEntry().setQuantifiableComposition();
    }

    public void setQuantity( int quantity ) {
        getShowcaseEntry().getCraftable().setQuantity( quantity );
    }

    public void increaseQuantity() {
        getShowcaseEntry().getCraftable().increaseQuantity();
    }

    public void increaseQuantity( int increment ) {
        getShowcaseEntry().getCraftable().increaseQuantity( increment );
    }

    public void decreaseQuantity() {
        getShowcaseEntry().getCraftable().decreaseQuantity();
    }

    public void decreaseQuantity( int decrement ) {
        getShowcaseEntry().getCraftable().decreaseQuantity( decrement );
    }

    private Cursor query( Context context, Uri uri ) {
        return context.getContentResolver().query( uri, null, null, null, null );
    }

    private ShowcaseEntry QueryForDetails( Context context, long dlc_id, long _id )
            throws ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {
        Uri uri = DatabaseContract.EngramEntry.buildUriWithId( dlc_id, _id );

        try ( Cursor cursor = query( context, uri ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_NAME ) );

            String folder = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER ) );
            String file = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE ) );

            String description = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_DESCRIPTION ) );
            int yield = cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_YIELD ) );
            long categoryId = cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_CATEGORY_KEY ) );
            int requiredLevel = cursor.getInt( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_LEVEL ) );

            List<Long> stationIds = new ArrayList<>( 0 );
            stationIds.add( cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_STATION_KEY ) ) );
            while ( cursor.moveToNext() )
                stationIds.add( cursor.getLong( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_STATION_KEY ) ) );

            // Next, let's grab station details from ids
            LongSparseArray<Station> stations = QueryForStations( context, dlc_id, stationIds );

            // Next, let's grab matching Queue details, if there are any.
            int quantity = 0;
            if ( CraftingQueue.getInstance().contains( _id ) )
                quantity = CraftingQueue.getInstance().getCraftable( _id ).getQuantity();

            // Finally, let's grab Composition details.
            LongSparseArray<CompositeResource> composition = QueryForComposition( context, dlc_id, _id );

            DisplayEngram craftable = new DisplayEngram( _id, name, folder, file, yield, categoryId, quantity );

            return new ShowcaseEntry( craftable, description, requiredLevel, dlc_id, composition, stations );
        }
    }

    private LongSparseArray<Station> QueryForStations( Context context, long dlc_id, List<Long> ids )
            throws ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {
        LongSparseArray<Station> stations = new LongSparseArray<>( 0 );

        for ( long _id : ids ) {
            Uri uri = DatabaseContract.StationEntry.buildUriWithId( dlc_id, _id );

            try ( Cursor cursor = query( context, uri ) ) {
                if ( cursor == null )
                    throw new ExceptionUtil.CursorNullException( uri );

                if ( !cursor.moveToFirst() )
                    throw new ExceptionUtil.CursorEmptyException( uri );

                String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_NAME ) );
                String file = cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_IMAGE_FILE ) );
                String folder = cursor.getString( cursor.getColumnIndex( DatabaseContract.StationEntry.COLUMN_IMAGE_FOLDER ) );

                stations.put( _id, new Station( _id, name, folder, file ) );
            }
        }

        return stations;
    }

    private LongSparseArray<CompositeResource> QueryForComposition( Context context, long dlc_id, long engram_id )
            throws ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {
        Uri uri = DatabaseContract.CompositionEntry.buildUriWithEngramId( dlc_id, engram_id );
        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            LongSparseArray<CompositeResource> composition = new LongSparseArray<>( 0 );
            while ( cursor.moveToNext() ) {
                long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY ) );
                int quantity = cursor.getInt( cursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_QUANTITY ) );

                composition.put( _id, new CompositeResource( QueryForResource( context, dlc_id, _id ), quantity ) );
            }

            return composition;
        }
    }

    private Resource QueryForResource( Context context, long dlc_id, long _id )
            throws ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {
        Uri uri = DatabaseContract.ResourceEntry.buildUriWithId( dlc_id, _id );

        try ( Cursor cursor = context.getContentResolver().query( uri, null, null, null, null ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.ResourceEntry.COLUMN_NAME ) );
            String folder = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER ) );
            String file = cursor.getString( cursor.getColumnIndex( DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE ) );

            return new Resource( _id, name, folder, file );
        }
    }

    private Category QueryForCategoryDetails( Context context, long dlc_id, long _id )
            throws ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {
        Uri uri = DatabaseContract.CategoryEntry.buildUriWithId( dlc_id, _id );

        try ( Cursor cursor = query( context, uri ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
            long parent_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );

            return new Category( _id, name, parent_id );
        }
    }

    private String QueryForDLCName( Context context, long dlc_id )
            throws ExceptionUtil.CursorNullException, ExceptionUtil.CursorEmptyException {
        Uri uri = DatabaseContract.buildUriWithId( DatabaseContract.DLCEntry.CONTENT_URI, dlc_id );

        try ( Cursor cursor = query( context, uri ) ) {
            if ( cursor == null )
                throw new ExceptionUtil.CursorNullException( uri );

            if ( !cursor.moveToFirst() )
                throw new ExceptionUtil.CursorEmptyException( uri );

            return cursor.getString( cursor.getColumnIndex( DatabaseContract.DLCEntry.COLUMN_NAME ) );
        }
    }

    public class ShowcaseEntry {
        private DisplayEngram mCraftable;

        private String mDescription;
        private int mRequiredLevel;
        private long mDLCId;

        private LongSparseArray<CompositeResource> mComposition;
        private LongSparseArray<QueueResource> mQuantifiableComposition;
        private LongSparseArray<Station> mStations;

        public ShowcaseEntry( DisplayEngram craftable, String description, int requiredLevel, long dlc_id,
                              LongSparseArray<CompositeResource> composition, LongSparseArray<Station> stations ) {
            setCraftable( craftable );

            setDescription( description );
            setRequiredLevel( requiredLevel );
            setDLCId( dlc_id );

            setComposition( composition );
            setQuantifiableComposition();

            setStations( stations );
        }

        void setCraftable( DisplayEngram craftable ) {
            this.mCraftable = craftable;
        }

        void setDescription( String description ) {
            this.mDescription = description;
        }

        void setRequiredLevel( int requiredLevel ) {
            this.mRequiredLevel = requiredLevel;
        }

        void setDLCId( long dlc_id ) {
            this.mDLCId = dlc_id;
        }

        public void setComposition( LongSparseArray<CompositeResource> composition ) {
            this.mComposition = composition;
        }

        private void setQuantifiableComposition() {
            LongSparseArray<CompositeResource> baseComposition = getComposition();
            int quantity = getCraftable().getQuantity();

            LongSparseArray<QueueResource> returnableComposition = new LongSparseArray<>();
            for ( int i = 0; i < baseComposition.size(); i++ ) {
                CompositeResource resource = baseComposition.valueAt( i );

                QueueResource queueResource = new QueueResource( resource, quantity );
                returnableComposition.append( i, queueResource );
            }

            mQuantifiableComposition = Util.sortResourcesByName( returnableComposition );
        }

        public void setStations( LongSparseArray<Station> stations ) {
            this.mStations = stations;
        }

        public DisplayEngram getCraftable() {
            return mCraftable;
        }

        public String getDescription() {
            return mDescription;
        }

        public int getRequiredLevel() {
            return mRequiredLevel;
        }

        public long getDLCId() {
            return mDLCId;
        }

        public LongSparseArray<CompositeResource> getComposition() {
            return mComposition;
        }

        public LongSparseArray<QueueResource> getQuantifiableComposition() {
            return mQuantifiableComposition;
        }

        public QueueResource getResource( int position ) throws Exception {
            if ( Util.isValidPosition( position, getQuantifiableComposition().size() ) ) {
                QueueResource resource = getQuantifiableComposition().valueAt( position );

                if ( resource == null )
                    throw new ExceptionUtil.ArrayElementNullException( position, getQuantifiableComposition().toString() );

                return resource;
            } else {
                throw new ExceptionUtil.PositionOutOfBoundsException( position, getQuantifiableComposition().size(), getQuantifiableComposition().toString() );
            }
        }

        public LongSparseArray<Station> getStations() {
            return mStations;
        }
    }
}