package arc.resource.calculator.util;

import android.content.Context;
import android.content.res.Configuration;

public class DisplayUtil {
    private static final String TAG = DisplayUtil.class.getSimpleName();

    private static DisplayUtil sInstance;

    private int mOrientation;

//    private float mImageSize;
//    private boolean mImageSizeResolved;

    public static DisplayUtil getInstance( Context context ) {
        if ( sInstance == null )
            sInstance = new DisplayUtil( context );

        return sInstance;
    }

    private DisplayUtil( Context context ) {
        mOrientation = context.getResources().getConfiguration().orientation;
//        mImageSize = -1;
//        mImageSizeResolved = false;
    }

//    public void setImageSize( float size ) {
//        mImageSize = size;
//        mImageSizeResolved = true;
//    }
//
//    public float getImageSize() {
//        return mImageSize;
//    }

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
