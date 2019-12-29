/*
 * Copyright (c) 2019 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */

package arc.resource.calculator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.listeners.PrefsObserver;
import arc.resource.calculator.listeners.QueueObserver;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.SortableMap;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.model.resource.CompositeResource;
import arc.resource.calculator.model.resource.Resource;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;
import arc.resource.calculator.views.InventoryRecyclerView;

import static androidx.recyclerview.widget.RecyclerView.NO_ID;
import static arc.resource.calculator.adapters.InventoryAdapter.Status.HIDDEN;
import static arc.resource.calculator.adapters.InventoryAdapter.Status.VISIBLE;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private static final String TAG = InventoryAdapter.class.getSimpleName();

    private Context mContext;

    private InventoryRecyclerView.Observer mViewObserver;

    private InventoryMap mInventory;

    private Status mViewStatus;

    private FetchInventoryTask mTask;

    private boolean mNeedsUpdate;

    private boolean mShowRawMaterials;

    enum Status {VISIBLE, HIDDEN}

    public InventoryAdapter(Context context, InventoryRecyclerView.Observer observer) {
        setContext(context);
        setObserver(observer);
        setInventory(new InventoryMap());

        mNeedsUpdate = true;

        mShowRawMaterials = PrefsUtil.getInstance(context).getRefinedFilterPreference();

        QueueObserver.getInstance().registerListener(TAG, new QueueObserver.Listener() {
            public void onDataSetPopulated() {
                if (mViewStatus == VISIBLE) {
                    fetchInventory();
                } else {
                    mNeedsUpdate = true;
                }
            }

            @Override
            public void onItemChanged(long craftableId, int quantity) {
                if (mViewStatus == VISIBLE) {
                    fetchInventory();
                } else {
                    mNeedsUpdate = true;
                }
            }

            @Override
            public void onItemRemoved(long craftableId) {
                if (mViewStatus == VISIBLE) {
                    fetchInventory();
                } else {
                    mNeedsUpdate = true;
                }
            }

            @Override
            public void onDataSetEmpty() {
                if (mViewStatus == VISIBLE) {
                    fetchInventory();
                } else {
                    mNeedsUpdate = true;
                }
            }
        });

        PrefsObserver.getInstance().registerListener(TAG, new PrefsObserver.Listener() {
            @Override
            public void onPreferencesChanged(boolean dlcValueChange, boolean categoryPrefChange, boolean stationPrefChange, boolean levelPrefChange, boolean levelValueChange, boolean refinedPrefChange) {
                if (refinedPrefChange) {
                    boolean showRawMaterials = PrefsUtil.getInstance(getContext()).getRefinedFilterPreference();
                    if (showRawMaterials != mShowRawMaterials) {
                        mShowRawMaterials = showRawMaterials;

                        if (mViewStatus == VISIBLE) {
                            fetchInventory();
                        } else {
                            mNeedsUpdate = true;
                        }
                    }
                }
            }
        });

        mTask = new FetchInventoryTask();
    }

    private Context getContext() {
        return mContext;
    }

    private void setContext(Context context) {
        this.mContext = context;
    }

    private InventoryMap getInventory() {
        return mInventory;
    }

    private void setInventory(InventoryMap inventory) {
        this.mInventory = inventory;
    }

    private InventoryRecyclerView.Observer getObserver() {
        return mViewObserver;
    }

    private void setObserver(InventoryRecyclerView.Observer observer) {
        mViewObserver = observer;
    }

    public void resume() {
        mViewStatus = VISIBLE;

        if (mNeedsUpdate)
            fetchInventory();
    }

    public void pause() {
        mViewStatus = HIDDEN;
    }

    public void destroy() {
        QueueObserver.getInstance().unregisterListener(TAG);
    }

    private CompositeResource getResource(int position) {
        return getInventory().valueAt(position);
    }

    private Cursor query(Uri uri) {
        return getContext().getContentResolver().query(uri, null, null, null, null);
    }

    private Resource QueryForResource(Uri uri) throws Exception {
        try (Cursor cursor = query(uri)) {
            if (cursor == null)
                throw new ExceptionUtil.CursorNullException(uri);

            if (!cursor.moveToFirst())
                throw new ExceptionUtil.CursorEmptyException(uri);

            long _id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.ResourceEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.ResourceEntry.COLUMN_NAME));
            String folder = cursor.getString(cursor.getColumnIndex(DatabaseContract.ResourceEntry.COLUMN_IMAGE_FOLDER));
            String file = cursor.getString(cursor.getColumnIndex(DatabaseContract.ResourceEntry.COLUMN_IMAGE_FILE));

            return new Resource(_id, name, folder, file);
        }
    }

    private long QueryForComplexResource(Uri uri) throws Exception {
        try (Cursor cursor = query(uri)) {
            if (cursor == null)
                throw new ExceptionUtil.CursorNullException(uri);

            if (!cursor.moveToFirst())
                return NO_ID;

//            long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry._ID ) );
            long engram_id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.ComplexResourceEntry.COLUMN_ENGRAM_KEY));
//            long resource_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.ComplexResourceEntry.COLUMN_RESOURCE_KEY ) );

            return engram_id;
        }
    }

    private InventoryMap QueryForComposition(Uri uri) throws Exception {
        long dlc_id = DatabaseContract.CompositionEntry.getDLCIdFromUri(uri);

        try (Cursor cursor = query(uri)) {
            if (cursor == null)
                throw new ExceptionUtil.CursorNullException(uri);

            InventoryMap inventoryMap = new InventoryMap();
            while (cursor.moveToNext()) {
                long resource_id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY));
                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseContract.CompositionEntry.COLUMN_QUANTITY));

                // check if refined filter is enabled
                if (mShowRawMaterials) {
                    Log.d(TAG, "QueryForComposition: showRawMaterials");
                    // check if resource_id is complex
                    long engram_id = QueryForComplexResource(DatabaseContract.ComplexResourceEntry.buildUriWithResourceId(resource_id));

                    // if complex, return engram_id
                    if (engram_id > NO_ID) {
                        Log.d(TAG, "QueryForComposition: engram_id is valid as complex resource");
                        // query for composition with update uri built with engram_id
                        InventoryMap composition = QueryForComposition(DatabaseContract.CompositionEntry.buildUriWithEngramId(dlc_id, engram_id));

                        // iterate through composition map
                        for (int j = 0; j < composition.size(); j++) {
                            CompositeResource tempResource = composition.valueAt(j);

                            CompositeResource resource = inventoryMap.get(tempResource.getId());
                            if (resource == null)
                                resource = new CompositeResource(
                                        tempResource.getId(),
                                        tempResource.getName(),
                                        tempResource.getFolder(),
                                        tempResource.getFile(),
                                        tempResource.getQuantity() * quantity);
                            else
                                resource.increaseQuantity(tempResource.getQuantity() * quantity);

                            inventoryMap.put(resource.getId(), resource);
                        }
                    } else {
                        inventoryMap.add(resource_id,
                                new CompositeResource(QueryForResource(DatabaseContract.ResourceEntry.buildUriWithId(dlc_id, resource_id)),
                                        quantity));
                    }
                } else {
                    inventoryMap.add(resource_id,
                            new CompositeResource(QueryForResource(DatabaseContract.ResourceEntry.buildUriWithId(dlc_id, resource_id)),
                                    quantity));
                }
            }

            return inventoryMap;
        }
    }

    private void fetchInventory() {
        mTask.cancel(true);

        mTask = new FetchInventoryTask();
        mTask.execute();
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
        protected void onPostExecute(Boolean querySuccessful) {
            if (querySuccessful) {
                setInventory(mTempInventoryMap);

                notifyDataSetChanged();

                mNeedsUpdate = false;

                if (mTempInventoryMap.size() > 0) {
                    getObserver().notifyDataSetPopulated();
                } else {
                    getObserver().notifyDataSetEmpty();
                }
            } else {
                if (mException != null)
                    getObserver().notifyExceptionCaught(mException);
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                long dlc_id = PrefsUtil.getInstance(mContext).getDLCPreference();

                CraftingQueue craftingQueue = CraftingQueue.getInstance();

                for (int i = 0; i < craftingQueue.getSize(); i++) {
                    if (isCancelled())
                        return false;

                    QueueEngram queueEngram = craftingQueue.getCraftable(i);

                    InventoryMap composition =
                            QueryForComposition(DatabaseContract.CompositionEntry.buildUriWithEngramId(dlc_id, queueEngram.getId()));

                    for (int j = 0; j < composition.size(); j++) {
                        CompositeResource tempResource = composition.valueAt(j);

                        CompositeResource resource = mTempInventoryMap.get(tempResource.getId());
                        if (resource == null)
                            resource = new CompositeResource(
                                    tempResource.getId(),
                                    tempResource.getName(),
                                    tempResource.getFolder(),
                                    tempResource.getFile(),
                                    tempResource.getQuantity() * queueEngram.getQuantity());
                        else
                            resource.increaseQuantity(tempResource.getQuantity() * queueEngram.getQuantity());

                        mTempInventoryMap.put(resource.getId(), resource);
                    }
                }

                mTempInventoryMap.sort();
                return true;
            } catch (Exception e) {
                mException = e;
                return false;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_inventory, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CompositeResource resource = getResource(position);

        String imagePath = "file:///android_asset/" + resource.getImagePath();
        Picasso.with(getContext())
                .load(imagePath)
                .error(R.drawable.placeholder_empty)
                .placeholder(R.drawable.placeholder_empty)
                .into(holder.getImageView());

        holder.getNameView().setText(resource.getName());
        holder.getQuantityView().setText(String.format(Locale.getDefault(), "x%d", resource.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return getInventory().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageView;
        private final TextView mNameView;
        private final TextView mQuantityView;

        ViewHolder(View view) {
            super(view);

            mImageView = view.findViewById(R.id.image_view);
            mNameView = view.findViewById(R.id.name_view);
            mQuantityView = view.findViewById(R.id.quantity_view);
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

    private class InventoryMap extends SortableMap {

        InventoryMap() {
            super();
        }

//        @Override
//        public void add( long key, Object value ) {
//            // check if key/value pair was added or not (if new or stored value)
//            if ( contains( key ) ) {
//                // update key/value pair with converged value
//                CompositeResource tempResource = ( CompositeResource ) value;
//
//                CompositeResource resource = get( tempResource.getId() );
//                if ( resource == null )
//                    resource = new CompositeResource(
//                            tempResource.getId(),
//                            tempResource.getName(),
//                            tempResource.getFolder(),
//                            tempResource.getFile(),
//                            tempResource.getQuantity() * queueEngram.getQuantity() );
//                else
//                    resource.increaseQuantity( tempResource.getQuantity() * queueEngram.getQuantity() );
//            } else {
//                // add new key/value pair
//                addNew( key, value );
//            }
//        }

        @Override
        public CompositeResource get(long key) {
            return (CompositeResource) super.get(key);
        }

        @Override
        public CompositeResource valueAt(int position) {
            return (CompositeResource) super.valueAt(position);
        }

        @Override
        public Comparable getComparable(int position) {
            return valueAt(position).getName();
        }
    }

    private class InventorySparseArray extends LongSparseArray<CompositeResource> {

        /**
         * Constructor that, by default, will set its element size to 0.
         */
        InventorySparseArray() {
            super(0);
        }

        /**
         * Constructor that sets its element size per the supplied amount.
         *
         * @param size Amount of elements the array requires to allocate.
         */
        InventorySparseArray(int size) {
            super(size);
        }

        /**
         * Sorts objects by Name
         */
        void sort() {
            boolean swapped = true;
            while (swapped) {
                swapped = false;
                for (int i = 0; i < size() - 1; i++) {
                    String first = valueAt(i).getName();
                    String second = valueAt(i + 1).getName();
                    if (first.compareTo(second) > 0) {
                        // swap
                        CompositeResource tempResource = valueAt(i + 1);
                        setValueAt(i + 1, valueAt(i));
                        setValueAt(i, tempResource);
                        swapped = true;
                    }
                }
            }
        }

        boolean contains(long key) {
            return !(indexOfKey(key) < 0);
        }
    }
}