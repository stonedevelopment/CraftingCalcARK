package arc.resource.calculator.helpers;

import android.content.Context;
import android.util.DisplayMetrics;

import arc.resource.calculator.R;

public class DisplayHelper {
    private static final String LOGTAG = "DisplayHelper";

    private static DisplayHelper sInstance;
    private static Context sContext;

    private float density;
    private float width;

    public static DisplayHelper createInstance(Context context, DisplayMetrics displayMetrics) {
        sContext = context;
        sInstance = new DisplayHelper(displayMetrics);

        return sInstance;
    }

    public static DisplayHelper getInstance() {
        return sInstance;
    }

    private DisplayHelper(DisplayMetrics displayMetrics) {
        this.density = displayMetrics.density;

        this.width = displayMetrics.widthPixels;
    }

    public float getEngramDimensions() {
        float padding = sContext.getResources().getDimension(R.dimen.engram_thumbnail_padding);
        float dpPadding = padding / density;
        float dpWidth = width / density;
        float dimensions = (dpWidth / 5) - (dpPadding);

//      Helper.Log(LOGTAG, "density:" + density + " dpWidth:" + dpWidth + " dimensions:" + dimensions);
        return dimensions;
    }

    public float getEngramDimensionsWithDensity() {
        return getEngramDimensions() * density;
    }
}
