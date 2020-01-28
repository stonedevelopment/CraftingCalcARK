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

package arc.resource.calculator.util;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.util.Arrays;

public class ExceptionUtil {

    public static void SendErrorReportWithAlertDialog(final Context context, String tag, final Exception e) {
        SendErrorReport(tag, e);

        DialogUtil.Error(context, new DialogUtil.Callback() {
            @Override
            public void onOk() {
                // call reset to defaults!
                PrefsUtil.getInstance(context).saveToDefaultFullReset();
            }

            @Override
            public void onCancel(Object o) {
                // forcibly close app
                Crashlytics.getInstance().crash();
            }
        }).show();
    }

    public static void SendErrorReport(String tag, Exception e) {
        if (e.getCause() != null)
            Crashlytics.log(Log.ERROR, tag, e.getCause().toString());

        Crashlytics.log(Log.ERROR, tag, e.toString());
        Crashlytics.log(Log.ERROR, tag, e.getMessage());
        Crashlytics.log(Log.ERROR, tag, Arrays.toString(e.getStackTrace()));
//        Crashlytics.log(Log.ERROR, tag, BuildErrorReportPreferencesBundle().toString());

        Crashlytics.logException(e);
    }

    // if position of requested list is out of bounds, then throw this exception
    public static class PositionOutOfBoundsException extends Exception {
        public PositionOutOfBoundsException(int index, int size, String contents) {
            super(BuildExceptionMessageBundle(index, size, contents).toString());
        }
    }

    // if list<object> element = null, then throw this exception
    public static class ArrayElementNullException extends Exception {
        public ArrayElementNullException(int index, String contents) {
            super(BuildExceptionMessageBundle(index, contents).toString());
        }
    }

    // Uri given to ContentProvider does not match uri list
    public static class URIUnknownException extends Exception {
        public URIUnknownException(Uri uri) {
            super(uri.toString());
        }
    }

    // Cursor returned null, onResume requested _id
    public static class CursorNullException extends Exception {

        public CursorNullException(Uri uri) {
            super(uri.toString());
        }

        public CursorNullException(Uri uri, String contents) {
            super(uri.toString() + " array:" + contents);
        }
    }

    // Cursor returned null, onResume requested _id
    public static class CursorEmptyException extends Exception {
        public CursorEmptyException(Uri uri) {
            super(uri.toString());
        }
    }

    // Bundle used to express an array's contents and the requested index
    private static Bundle BuildExceptionMessageBundle(int index, String contents) {
        Bundle bundle = new Bundle();

        bundle.putInt("array_index", index);
        bundle.putString("array_contents", contents);

        return bundle;
    }

    private static Bundle BuildExceptionMessageBundle(int index, int size, String contents) {
        Bundle bundle = new Bundle();

        bundle.putInt("array_index", index);
        bundle.putInt("array_size", size);
        bundle.putString("array_contents", contents);

        return bundle;
    }

    private static Bundle BuildErrorReportPreferencesBundle(Context context) {
        try {
            PrefsUtil prefs = PrefsUtil.getInstance(context);

            Bundle bundle = new Bundle();
            bundle.putBoolean("filter_by_category", prefs.getCategoryFilterPreference());
            bundle.putBoolean("filter_by_station", prefs.getStationFilterPreference());
            bundle.putBoolean("filter_by_level", prefs.getLevelFilterPreference());
            bundle.putInt("current_level", prefs.getRequiredLevel());
            bundle.putBoolean("breakdown_resources", prefs.getRefinedFilterPreference());

            return bundle;
        } catch (Exception e) {
            return new Bundle();
        }
    }
}
