package arc.resource.calculator.helpers;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

import arc.resource.calculator.R;

public class DisplayHelper {
    private static final String TAG = DisplayHelper.class.getSimpleName();

    private static DisplayHelper sInstance;
    private Display mDisplay;
    private Context mContext;

    private float mDensity;
    private float mHeight;
    private float mWidth;

    public static DisplayHelper createInstance( Context context, Display display ) {
        sInstance = new DisplayHelper( context, display );

        return sInstance;
    }

    public static DisplayHelper getInstance() {
        return sInstance;
    }

    private DisplayHelper( Context context, Display display ) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics( displayMetrics );

        mContext = context;
        mDisplay = display;

        mDensity = displayMetrics.density;
        mWidth = displayMetrics.widthPixels;
        mHeight = displayMetrics.heightPixels;
    }

    public float getDensity() {
        return mDensity;
    }

    public float getSmallestWidthDensity() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mDisplay.getMetrics( displayMetrics );

        return displayMetrics.widthPixels / mDensity;
    }

    public float getEngramDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mDisplay.getMetrics( displayMetrics );

        float padding = getContext().getResources().getDimension( R.dimen.engram_grid_view_holder_padding );
        float dpPadding = padding / mDensity;

        float dpHeight = displayMetrics.heightPixels / mDensity;
        float dpWidth = displayMetrics.widthPixels / mDensity;

        float dimensions;

        switch ( mDisplay.getRotation() ) {
            case 0:
            case 2:
                dimensions = ( dpWidth / 5 ) - ( dpPadding );
                break;

            case 1:
            case 3:
            default:
                dimensions = ( dpHeight / 5 ) - ( dpPadding );
                break;
        }

        return dimensions;
    }

    public float getEngramDimensionsWithDensity() {
        return getEngramDimensions() * mDensity;
    }

    private Context getContext() {
        return mContext;
    }
}
