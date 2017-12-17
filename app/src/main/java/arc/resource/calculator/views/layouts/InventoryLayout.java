package arc.resource.calculator.views.layouts;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import arc.resource.calculator.R;
import arc.resource.calculator.views.switchers.InventorySwitcher;

public class InventoryLayout extends LinearLayout {

    //  Switcher view that will display if empty or error occurred
    InventorySwitcher mSwitcher;

    //  Text view that is used as a Title "Raw Materials" or "Gather These Materials"
    TextView mTextView;

    public InventoryLayout( Context context ) {
        super( context );
    }

    public InventoryLayout( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public InventoryLayout( Context context, @Nullable AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public InventoryLayout( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    public void onCreate() {
//        mSwitcher = ( InventorySwitcher ) findViewById( R.id.switcher_inventory );
//        mSwitcher.onCreate();
    }

    public void onResume() {
        mSwitcher.onResume();
    }

    public void onPause() {
        mSwitcher.onPause();
    }

    public void onDestroy() {
        mSwitcher.onDestroy();
    }

}
