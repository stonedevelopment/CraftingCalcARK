package arc.resource.calculator.util;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.model.Category;

class DbUtil {
    private static final String TAG = DbUtil.class.getSimpleName();

    public static Category QueryForCategoryDetails( Context context, long _id ) {
        long dlc_id = new PrefsUtil( context ).getDLCPreference();

        Cursor cursor = context.getContentResolver().query(
                DatabaseContract.CategoryEntry.buildUriWithId( dlc_id, _id ),
                null, null, null, null
        );

        if ( cursor != null && cursor.moveToFirst() ) {
            String name = cursor.getString( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_NAME ) );
            long parent_id = cursor.getLong( cursor.getColumnIndex( DatabaseContract.CategoryEntry.COLUMN_PARENT_KEY ) );

            cursor.close();

            return new Category(
                    _id,
                    name,
                    parent_id
            );
        } else {
            // Category does not exist!?
            Log.e( TAG, "QueryForCategoryDetails(" + _id + ") returned null?" );
            return null;
        }
    }
}
