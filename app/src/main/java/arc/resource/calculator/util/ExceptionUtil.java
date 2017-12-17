package arc.resource.calculator.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import java.util.Arrays;

public class ExceptionUtil {

    public static void SendErrorReportWithAlertDialog( final Context context, String tag, final Exception e ) {
        SendErrorReport( tag, e );

        DialogUtil.Error( context, new DialogUtil.Callback() {
            @Override
            public void onOk() {
                // call reset to defaults!
                PrefsUtil.getInstance( context ).saveToDefaultFullReset();
            }

            @Override
            public void onCancel() {
                // force close app
                throw new RuntimeException( "Force closing." );
            }
        } ).show();
    }

    public static void SendErrorReport( String tag, Exception e ) {
        if ( e.getCause() != null )
            FirebaseCrash.logcat( Log.ERROR, tag, e.getCause().toString() );

        FirebaseCrash.logcat( Log.ERROR, tag, e.toString() );
        FirebaseCrash.logcat( Log.ERROR, tag, e.getMessage() );
        FirebaseCrash.logcat( Log.ERROR, tag, Arrays.toString( e.getStackTrace() ) );
        FirebaseCrash.logcat( Log.ERROR, tag, BuildErrorReportPreferencesBundle().toString() );

        FirebaseCrash.report( e );
    }

    public static Bundle BuildExceptionMessageBundle( int index, int size ) {
        Bundle bundle = new Bundle();

        bundle.putInt( "array_index", index );
        bundle.putInt( "array_size", size );

        return bundle;
    }

    // Bundle used to express an array's contents and the requested index
    public static Bundle BuildExceptionMessageBundle( int index, String contents ) {
        Bundle bundle = new Bundle();

        bundle.putInt( "array_index", index );
        bundle.putString( "array_contents", contents );

        return bundle;
    }

    public static Bundle BuildExceptionMessageBundle( int index, int size, String contents ) {
        Bundle bundle = new Bundle();

        bundle.putInt( "array_index", index );
        bundle.putInt( "array_size", size );
        bundle.putString( "array_contents", contents );

        return bundle;
    }

    public static Bundle BuildErrorReportPreferencesBundle() {
        try {
//            PrefsUtil prefs = PrefsUtil.getInstance();

            Bundle bundle = new Bundle();
//            bundle.putBoolean( "filter_by_category", prefs.getCategoryFilterPreference() );
//            bundle.putBoolean( "filter_by_station", prefs.getStationFilterPreference() );
//            bundle.putBoolean( "filter_by_level", prefs.getLevelFilterPreference() );
//            bundle.putInt( "current_level", prefs.getRequiredLevel() );
//            bundle.putBoolean( "breakdown_resources", prefs.getRefinedFilterPreference() );

            return bundle;
        } catch ( Exception e ) {
            return new Bundle();
        }
    }
}
