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

import android.util.Log;
import android.widget.LinearLayout;

public class DisplayUtil {
    private static final String TAG = DisplayUtil.class.getSimpleName();

    public static final String BUNDLE_KEY = TAG;

    private static DisplayUtil sInstance;

    private int mViewSize = Util.NO_SIZE;

    public static DisplayUtil getInstance() {
        if (sInstance == null)
            sInstance = new DisplayUtil();

        return sInstance;
    }

    private DisplayUtil() {
    }

    public void setImageSize(int size) {
        Log.d(TAG, "setImageSize(): " + size);
        mViewSize = size;
    }

    public int getImageSize() {
        return mViewSize;
    }

    public LinearLayout.LayoutParams getParams() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mViewSize);
    }
}
