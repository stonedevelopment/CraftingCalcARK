package arc.resource.calculator.util;

import android.util.Log;
import android.widget.LinearLayout;

public class DisplayUtil {
    private static final String TAG = DisplayUtil.class.getSimpleName();

    private static DisplayUtil sInstance;

    private int mViewSize = Util.NO_SIZE;

    public static DisplayUtil getInstance() {
        if ( sInstance == null )
            sInstance = new DisplayUtil();

        return sInstance;
    }

    private DisplayUtil() {
    }

    public void setImageSize( int size ) {
        Log.d( TAG, "setImageSize(): " + size );
        mViewSize = size;
    }

    public int getImageSize() {
        return mViewSize;
    }

    public LinearLayout.LayoutParams getParams() {
        return new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, mViewSize );
    }
}
