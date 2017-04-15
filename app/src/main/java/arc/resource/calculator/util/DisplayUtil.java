package arc.resource.calculator.util;

import android.view.ViewGroup;

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
        mViewSize = size;
    }

    public ViewGroup.LayoutParams getParams() {
        return new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, mViewSize );
    }
}
