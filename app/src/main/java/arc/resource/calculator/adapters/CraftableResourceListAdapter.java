//package arc.resource.calculator.adapters;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import arc.resource.calculator.R;
//
///**
// * Copyright (C) 2016, Jared Stone
// * -
// * Author: Jared Stone
// * Title: A:RC, a resource calculator for ARK:Survival Evolved
// * -
// * Web: https://github.com/jaredstone1982/CraftingCalcARK
// * Email: jaredstone1982@gmail.com
// * Twitter: @MasterxOfxNone
// * -
// * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
// */
//public class CraftableResourceListAdapter extends RecyclerView.Adapter<CraftableResourceListAdapter.ViewHolder> {
//    private static final String TAG = CraftableResourceListAdapter.class.getSimpleName();
//
//    //    private final int INVALID_ITEM_POSITION = -1;
////    private final int INVALID_ITEM_ID = -1;
////
////    private LongSparseArray<CompositeResource> mResources;
////
////    private ListenerUtil mCallback;
////
////    public CraftableResourceListAdapter( Context context ) {
////        mCallback = ListenerUtil.getInstance();
////        setInventory( new LongSparseArray<CompositeResource>( 0 ) );
////
////        new QueryForResourcesTask( context ).execute();
////    }
////
////    @Override
////    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
////        View itemView = LayoutInflater.from( parent.getContext() ).
////                inflate( R.layout.list_item_resource, parent, false );
////
////        return new ViewHolder( itemView );
////    }
////
////    @Override
////    public void onBindViewHolder( ViewHolder holder, int position ) {
////        Context context = holder.getView().getContext();
////
////        CompositeResource resource = getResource( position );
////
////        String imagePath = "file:///android_asset/" + resource.getImagePath();
////        Picasso.with( context ).load( imagePath ).into( holder.getThumbnail() );
////
////        String name = resource.getName();
////        holder.getName().setText( name );
////
////        int quantity = resource.getQuantity();
////        holder.getQuantity().setText( String.format( Locale.US, "%1$d", quantity ) );
////    }
////
////    @Override
////    public int getItemCount() {
////        return getInventory().size();
////    }
////
////    private CompositeResource getResource( int position ) {
////        return getInventory().valueAt( position );
////    }
////
////    LongSparseArray<CompositeResource> getInventory() {
////        return mResources;
////    }
////
////    void setInventory( LongSparseArray<CompositeResource> resources ) {
////        mResources = resources;
////    }
////
////    // Query for compositions, calculate composition quantities with queue quantities
////    private LongSparseArray<CompositeResource> QueryForResources( Context context ) {
////        try {
////            long dlc_id = PrefsUtil.getInstance().getDLCPreference();
////
////            int level = 0;
////
////            LongSparseArray<QueueEngram> engrams = CraftingQueue.getInstance().getQueue();
////
////            Log.d( TAG, engrams.toString() );
////
////            LongSparseArray<CompositeResource> resourceMap = new LongSparseArray<>( 0 );
////            for ( int i = 0; i < engrams.size(); i++ ) {
////                QueueEngram engram = engrams.valueAt( i );
////
////                LongSparseArray<CompositeResource> tempResources =
////                        QueryForComposition(
////                                context,
////                                DatabaseContract.CompositionEntry.buildUriWithEngramId( dlc_id, engram.getId() ),
////                                level );
////
////                for ( int j = 0; j < tempResources.size(); j++ ) {
////                    CompositeResource tempResource = tempResources.valueAt( j );
////
////                    CompositeResource resource = resourceMap.get( tempResource.getId() );
////                    if ( resource == null )
////                        resource = new CompositeResource(
////                                tempResource.getId(),
////                                tempResource.getName(),
////                                tempResource.getFolder(),
////                                tempResource.getFile(),
////                                tempResource.getQuantity() * engram.getQuantity() );
////                    else
////                        resource.increaseQuantity( tempResource.getQuantity() * engram.getQuantity() );
////
////                    resourceMap.put( resource.getId(), resource );
////                }
////            }
////
////            return resourceMap;
////        } catch ( Exception e ) {
////            mCallback.notifyFatalExceptionCaught( TAG, e );
////            return null;
////        }
////    }
////
////    private LongSparseArray<CompositeResource> QueryForComposition( Context context, Uri uri, int level ) {
////        long dlc_id = DatabaseContract.CompositionEntry.getDLCIdFromUri( uri );
////
////        level++;
////
////        try ( Cursor cursor = context.getContentResolver().query(
////                uri, null, null, null, null ) ) {
////
////            if ( cursor == null )
////                throw new ExceptionUtil.CursorNullException( uri );
////
////            LongSparseArray<CompositeResource> resourceMap = new LongSparseArray<>();
////            while ( cursor.moveToNext() ) {
////                long resource_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY ) );
////                int quantity = cursor.getInt( cursor.getColumnIndex( DatabaseContract.CompositionEntry.COLUMN_QUANTITY ) );
////
////                long engram_id = INVALID_ITEM_ID;
////                if ( hasComplexResources() )
////                    engram_id = QueryForComplexResource(
////                            context,
////                            DatabaseContract.ComplexResourceEntry.buildUriWithResourceId( resource_id ) );
////
////                if ( engram_id != INVALID_ITEM_ID ) {
////                    LongSparseArray<CompositeResource> tempResources =
////                            QueryForComposition(
////                                    context,
////                                    DatabaseContract.CompositionEntry.buildUriWithEngramId( dlc_id, engram_id ), level );
////
////                    for ( int i = 0; i < tempResources.size(); i++ ) {
////                        CompositeResource tempResource = tempResources.get( i );
////
////                        CompositeResource resource = resourceMap.get( tempResource.getId() );
////                        if ( resource == null )
////                            resource = new CompositeResource(
////                                    tempResource.getId(),
////                                    tempResource.getName(),
////                                    tempResource.getFolder(),
////                                    tempResource.getFile(),
////                                    tempResource.getQuantity() * quantity );
////
////                        resourceMap.put( resource.getId(), resource );
////                    }
////                } else {
////                    Resource tempResource = QueryForResource(
////                            context,
////                            DatabaseContract.ResourceEntry.buildUriWithId( dlc_id, resource_id ) );
////
////                    if ( tempResource == null )
////                        continue;
////
////                    Log.d( TAG, tempResource.toString() );
////
////                    CompositeResource resource = resourceMap.get( resource_id );
////                    if ( resource == null )
////                        resource = new CompositeResource(
////                                tempResource.getId(),
////                                tempResource.getName(),
////                                tempResource.getFolder(),
////                                tempResource.getFile(),
////                                quantity );
////
////                    resourceMap.put( resource_id, resource );
////                }
////            }
////
////            return resourceMap;
////        } catch ( Exception e ) {
////            mCallback.notifyFatalExceptionCaught( TAG, e );
////
////            return new LongSparseArray<>( 0 );
////        }
////    }
////
////    private long QueryForComplexResource( Context context, Uri uri ) {
////        try ( Cursor cursor = context.getContentResolver().query(
////                uri, null, null, null, null ) ) {
////
////            if ( cursor == null )
////                throw new ExceptionUtil.CursorNullException( uri );
////
////            if ( !cursor.moveToFirst() )
////                throw new ExceptionUtil.CursorEmptyException( uri );
////
////            return cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY ) );
////        } catch ( Exception e ) {
////            return INVALID_ITEM_ID;
////        }
////    }
////
////    private LongSparseArray<CompositeResource> sortByName( LongSparseArray<CompositeResource> resources ) {
////        boolean swapped = true;
////        while ( swapped ) {
////
////            swapped = false;
////            for ( int i = 0; i < resources.size() - 1; i++ ) {
////                String first = resources.valueAt( i ).getName();
////                String second = resources.valueAt( i + 1 ).getName();
////                if ( first.compareTo( second ) > 0 ) {
////                    // swap
////                    CompositeResource tempResource = resources.valueAt( i + 1 );
////                    resources.setValueAt( i + 1, resources.valueAt( i ) );
////                    resources.setValueAt( i, tempResource );
////                    swapped = true;
////                }
////            }
////        }
////
////        return resources;
////    }
////
////    private boolean hasComplexResources() {
////        return PrefsUtil.getInstance().getRefinedFilterPreference();
////    }
////
////    private class QueryForResourcesTask extends AsyncTask<Void, Void, Void> {
////        private final String TAG = QueryForResourcesTask.class.getSimpleName();
////
////        final Context mContext;
////
////        QueryForResourcesTask( Context context ) {
////            mContext = context;
////        }
////
////        @Override
////        protected void onPostExecute( Void aVoid ) {
////            notifyDataSetPopulated();
////        }
////
////        @Override
////        protected Void doInBackground( Void... params ) {
////            setInventory( sortByName( QueryForResources( mContext ) ) );
////            return null;
////        }
////    }
////
//    class ViewHolder extends RecyclerView.ViewHolder {
//        private final View mView;
//
//        private final ImageView mThumbnail;
//        private final TextView mName;
//        private final TextView mQuantity;
//
//        public ViewHolder( View view ) {
//            super( view );
//
//            mView = view;
//
//            mThumbnail = ( ImageView ) view.findViewById( R.id.resource_list_imageView );
//            mName = ( TextView ) view.findViewById( R.id.resource_list_nameText );
//            mQuantity = ( TextView ) view.findViewById( R.id.resource_list_quantityText );
//        }
//
//        public View getView() {
//            return mView;
//        }
//
//        public ImageView getThumbnail() {
//            return mThumbnail;
//        }
//
//        public TextView getName() {
//            return mName;
//        }
//
//        public TextView getQuantity() {
//            return mQuantity;
//        }
//    }
//}
