package arc.resource.calculator.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;

import arc.resource.calculator.MainActivity;
import arc.resource.calculator.R;

public class DisplayUtil {
    private static final String TAG = DisplayUtil.class.getSimpleName();

    private Display mDisplay;
    private Context mContext;

    public DisplayUtil( Context context, Display display ) {
        mContext = context;
        mDisplay = display;
    }

    public float getDisplayCaseGridViewHolderDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getDisplay().getMetrics( displayMetrics );

        int colCount = getContext().getResources().getInteger( R.integer.display_case_column_count );
        int rowCount = getContext().getResources().getInteger( R.integer.display_case_row_count );

//        float textSize = getContext().getResources().getDimension( R.dimen.display_case_hierarchy_text_size );

        float padding = getContext().getResources().getDimension( R.dimen.display_case_view_holder_padding );

        // Padding * 2 for top and bottom or start and end
        float colPadding = ( padding * 2 ) * colCount;
//        float rowPadding = ( padding * 2 ) * rowCount;

//        float actionBarSize = getContext().getResources().getDimension( R.dimen.action_bar_height );

//        float adsPadding = getContext().getResources().getDimension( R.dimen.ads_height );

        if ( isPortrait() ) {
            return ( ( displayMetrics.widthPixels - colPadding ) / colCount );
        } else {
//            return ( ( displayMetrics.heightPixels - ( textSize + rowPadding + adsPadding + actionBarSize ) ) / rowCount );
            return ( ( displayMetrics.widthPixels - colPadding ) / colCount );
        }
    }

    public float getCraftingQueueGridViewHolderDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getDisplay().getMetrics( displayMetrics );

        float padding = getContext().getResources().getDimension( R.dimen.display_case_view_holder_padding );

        if ( isPortrait() ) {
            return MainActivity.mEngramDimensions - ( padding / 2 );
        } else {
            return MainActivity.mEngramDimensions - ( padding / 2 );
        }
    }

    public boolean isLandscape() {
        return getOrientation() == Configuration.ORIENTATION_LANDSCAPE;
    }

    public boolean isPortrait() {
        return getOrientation() == Configuration.ORIENTATION_PORTRAIT;
    }

    private int getOrientation() {
        return getContext().getResources().getConfiguration().orientation;
    }

    private Display getDisplay() {
        return mDisplay;
    }

    private Context getContext() {
        return mContext;
    }
}
