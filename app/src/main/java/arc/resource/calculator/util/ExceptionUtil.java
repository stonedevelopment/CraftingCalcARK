package arc.resource.calculator.util;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import java.util.Arrays;

public class ExceptionUtil {

    public static void SendErrorReportWithAlertDialog( Context context, String tag, final Exception e ) {
        SendErrorReport( tag, e );

        DialogUtil.Error( context, new DialogUtil.Callback() {
            @Override
            public void onOk() {
                // call reset to defaults!
                PrefsUtil.getInstance().saveToDefaultFullReset();
            }

            @Override
            public void onCancel() {
                // forcily close app
                throw new RuntimeException( "Force closing." );
            }
        } ).show();
    }

    public static void SendErrorReport( String tag, Exception e ) {
        if ( e.getCause() != null )
            FirebaseCrash.logcat( Log.ERROR, tag, e.getCause().toString() );

        FirebaseCrash.logcat( Log.ERROR, tag, BuildErrorReportPreferencesBundle().toString() );
        FirebaseCrash.logcat( Log.ERROR, tag, e.getMessage() );
        FirebaseCrash.logcat( Log.ERROR, tag, Arrays.toString( e.getStackTrace() ) );

        // TODO: 4/19/2017 Uncomment for production!!!!
//        FirebaseCrash.report( e );
    }

    // if position of requested list is out of bounds, then throw this exception
    public static class PositionOutOfBoundsException extends Exception {
        public PositionOutOfBoundsException( int index, int size, String contents ) {
            super( BuildExceptionMessageBundle( index, size, contents ).toString() );
        }
    }

    // if list<object> element = null, then throw this exception
    public static class ArrayElementNullException extends Exception {
        public ArrayElementNullException( int index, String contents ) {
            super( BuildExceptionMessageBundle( index, contents ).toString() );
        }
    }

    // Uri given to ContentProvider does not match uri list
    public static class URIUnknownException extends Exception {
        public URIUnknownException( Uri uri ) {
            super( uri.toString() );
        }
    }

    // Cursor returned null, resume requested _id
    public static class CursorNullException extends Exception {

        public CursorNullException( Uri uri ) {
            super( uri.toString() );
        }

        public CursorNullException( Uri uri, String contents ) {
            super( uri.toString() + " array:" + contents );
        }
    }

    // Cursor returned null, resume requested _id
    public static class CursorEmptyException extends Exception {
        public CursorEmptyException( Uri uri ) {
            super( uri.toString() );
        }
    }

    // Bundle used to express an array's contents and the requested index
    private static Bundle BuildExceptionMessageBundle( int index, String contents ) {
        Bundle bundle = new Bundle();

        bundle.putInt( "array_index", index );
        bundle.putString( "array_contents", contents );

        return bundle;
    }

    private static Bundle BuildExceptionMessageBundle( int index, int size, String contents ) {
        Bundle bundle = new Bundle();

        bundle.putInt( "array_index", index );
        bundle.putInt( "array_size", size );
        bundle.putString( "array_contents", contents );

        return bundle;
    }

    private static Bundle BuildErrorReportPreferencesBundle() {
        PrefsUtil prefs = PrefsUtil.getInstance();

        Bundle bundle = new Bundle();
        bundle.putBoolean( "filter_by_category", prefs.getCategoryFilterPreference() );
        bundle.putBoolean( "filter_by_station", prefs.getStationFilterPreference() );
        bundle.putBoolean( "filter_by_level", prefs.getLevelFilterPreference() );
        bundle.putInt( "current_level", prefs.getRequiredLevel() );
        bundle.putBoolean( "breakdown_resources", prefs.getRefinedFilterPreference() );

        return bundle;
    }
}
