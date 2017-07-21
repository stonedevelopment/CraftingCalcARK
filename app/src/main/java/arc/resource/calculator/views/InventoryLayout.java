package arc.resource.calculator.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import arc.resource.calculator.R;
import arc.resource.calculator.util.PrefsUtil;

public class InventoryLayout extends LinearLayout {
    InventorySwitcher mSwitcher;
    RawMaterialCheckBox mCheckBox;

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
        mSwitcher = ( InventorySwitcher ) findViewById( R.id.switcher_inventory );
        mSwitcher.onCreate();

        mCheckBox = ( RawMaterialCheckBox ) findViewById( R.id.checkbox_inventory_raw_materials );
        mCheckBox.create();
    }

    public void onResume() {
//        mCheckBox.resume();
        mSwitcher.onResume();
    }

    public void onPause() {
//        mCheckBox.pause();
        mSwitcher.onPause();
    }

    public void onDestroy() {
//        mCheckBox.destroy();
        mSwitcher.onDestroy();
    }

}
