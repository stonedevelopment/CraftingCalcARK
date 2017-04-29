package arc.resource.calculator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;

public class MainSwitcher extends ViewSwitcher {
    private static final String TAG = MainSwitcher.class.getSimpleName();

    CraftableLayout mCraftableLayout;
    LinearLayout mInventoryLayout;

    InventorySwitcher mInventorySwitcher;

    public MainSwitcher( Context context ) {
        super( context );
    }

    public MainSwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public void onCreate() {
        mCraftableLayout = ( CraftableLayout ) findViewById( R.id.layout_craftables );
        mCraftableLayout.onCreate();

        Button viewCraftables = ( Button ) findViewById( R.id.button_view_craftables );
        viewCraftables.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                // toggle view to craftables
                showNext();

                // onPause inventory view
                mInventorySwitcher.onPause();

                // onResume craftable view
                mCraftableLayout.onResume();
            }
        } );

        mInventoryLayout = ( LinearLayout ) findViewById( R.id.layout_inventory );

        mInventorySwitcher = ( InventorySwitcher ) findViewById( R.id.switcher_inventory );
        mInventorySwitcher.onCreate();

        Button viewInventory = ( Button ) findViewById( R.id.button_view_inventory );
        viewInventory.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                // toggle view to inventory
                showNext();

                // onPause craftable view
                mCraftableLayout.onPause();

                // onResume inventory view
                mInventorySwitcher.onResume();
            }
        } );
    }

    public void onPause() {
        if ( isCurrentView( mCraftableLayout.getId() ) ) {
            mCraftableLayout.onPause();
        } else {
            mInventorySwitcher.onPause();
        }
    }

    public void onResume() {
        if ( isCurrentView( mCraftableLayout.getId() ) ) {
            mCraftableLayout.onResume();
        } else {
            mInventorySwitcher.onResume();
        }
    }

    public void onDestroy() {
        mCraftableLayout.onDestroy();
        mInventorySwitcher.onDestroy();
    }

    public void onSearch( String searchQuery ) {
        // toggle craftable recyclerView back if not current view, then execute search
        if ( !isCurrentView( mCraftableLayout.getId() ) ) {
            showNext();

            mInventorySwitcher.onPause();
            mCraftableLayout.onResume();
        }

        mCraftableLayout.onSearch( searchQuery );
    }

    private boolean isCurrentView( int id ) {
        return getCurrentView().getId() == id;
    }
}
