package arc.resource.calculator.views.layouts;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Layout class to better provide access to attached Floating Action Buttons and clean up
 * MainActivity.
 */

public class BottomSheetLayout extends RelativeLayout {
    public BottomSheetLayout( Context context ) {
        super( context );
    }

    public BottomSheetLayout( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public BottomSheetLayout( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public BottomSheetLayout( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
    }
}
