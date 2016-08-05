package arc.resource.calculator.helpers;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

import arc.resource.calculator.R;

public class DisplayHelper {
    private static final String LOGTAG = "DisplayHelper";

    private static DisplayHelper sInstance;
    private static Context sContext;

    private int orientation;

    private float density;
    private float height;
    private float width;

    public static DisplayHelper createInstance(Context context, Display display) {
        sContext = context;
        sInstance = new DisplayHelper(display);

        return sInstance;
    }

    public static DisplayHelper getInstance() {
        return sInstance;
    }

    private DisplayHelper(Display display) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        this.density = displayMetrics.density;

        this.width = displayMetrics.widthPixels;
        this.height = displayMetrics.heightPixels;

        this.orientation = display.getRotation();
    }

    public float getEngramDimensions() {
        float padding = sContext.getResources().getDimension(R.dimen.engram_thumbnail_padding);
        float dpPadding = padding / density;

        float dpHeight = height / density;
        float dpWidth = width / density;

        float dimensions;

        switch (orientation) {
            case 0:
            case 2:
                dimensions = (dpWidth / 5) - (dpPadding);
                break;

            case 1:
            case 3:
            default:
                dimensions = (dpHeight / 5) - (dpPadding);
                break;
        }

//      Helper.Log(LOGTAG, "density:" + density + " dpWidth:" + dpWidth + " dimensions:" + dimensions);
        return dimensions;
    }

    public float getEngramDimensionsWithDensity() {
        return getEngramDimensions() * density;
    }
}
