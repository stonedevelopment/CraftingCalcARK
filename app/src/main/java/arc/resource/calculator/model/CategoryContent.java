package arc.resource.calculator.model;

import android.content.Context;
import android.database.Cursor;
import android.util.LongSparseArray;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.util.PrefsUtil;

public class CategoryContent {

    private static final List<CategoryItem> ITEMS = new ArrayList<>();

    private static final LongSparseArray<CategoryItem> ITEM_MAP = new LongSparseArray<>();

    public List<CategoryItem> fetchItems( Context context ) {
        PrefsUtil prefs = new PrefsUtil( context );

        long dlc_id = prefs.getDLCPreference();
        long level = prefs.getLastCategoryLevel();
        long parent = prefs.getLastCategoryParent();
        long station_id = -1;

        Cursor cursor;

        if ( prefs.getStationFilterPreference() ) {
            cursor = context.getContentResolver().query(
                    DatabaseContract.CategoryEntry.buildUriWithStationId( dlc_id, station_id, level ),
                    null, null, null, null );
        } else {
            cursor = context.getContentResolver().query(
                    DatabaseContract.CategoryEntry.buildUriWithParentId( dlc_id, level ),
                    null, null, null, null );
        }

        if ( cursor == null ) {
            return new ArrayList<>();
        }

        while ( cursor.moveToNext() ) {
            long _id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry._ID ) );
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
            long parent_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );

            addItem( _id, name, parent_id );
        }

        cursor.close();

        return ITEMS;
    }

    private void addItem( long _id, String name, long parent_id ) {
        if ( ITEM_MAP.indexOfKey( _id ) < 0 ) {
            CategoryItem item = new CategoryItem( _id, name, parent_id );

            ITEMS.add( item );
            ITEM_MAP.put( _id, item );
        }
    }

    private void clearItems() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static List<CategoryItem> getItems() {
        return ITEMS;
    }

    public static CategoryItem getItem( Long key ) {
        return ITEM_MAP.get( key );
    }

    public class CategoryItem {

        private long mId;
        private String mName;
        private long mParent;
        private String mDrawable;

        public CategoryItem( long _id, String name, long parent ) {
            this.mId = _id;
            this.mName = name;
            this.mParent = parent;
            this.mDrawable = "folder";
        }

        public long getId() {
            return mId;
        }

        public String getName() {
            return mName;
        }

        public long getParent() {
            return mParent;
        }

        public String getDrawable() {
            return mDrawable;
        }

        @Override
        public String toString() {
            return "mEngramId=" + mId + ", mName=" + mName + ", mParent=" + mParent + ", mDrawable=" + mDrawable;
        }
    }
}
