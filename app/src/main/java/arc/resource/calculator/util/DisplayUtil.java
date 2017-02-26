package arc.resource.calculator.util;

import android.content.Context;
import android.content.res.Configuration;

import arc.resource.calculator.listeners.MainActivityListener;

class DisplayUtil {
    private static final String TAG = DisplayUtil.class.getSimpleName();

    private static DisplayUtil sInstance;

    private int mOrientation;

    public static DisplayUtil getInstance( Context context ) {
        if ( sInstance == null )
            sInstance = new DisplayUtil( context );

        return sInstance;
    }

    private DisplayUtil( Context context ) {
        mOrientation = context.getResources().getConfiguration().orientation;
    }

    public boolean isLandscape() {
        return getOrientation() == Configuration.ORIENTATION_LANDSCAPE;
    }

    public boolean isPortrait() {
        return getOrientation() == Configuration.ORIENTATION_PORTRAIT;
    }

    public int getOrientation() {
        return mOrientation;
    }
}
