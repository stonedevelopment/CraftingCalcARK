package arc.resource.calculator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;

public class MainSwitcher extends ViewSwitcher {
    private static final String TAG = MainSwitcher.class.getSimpleName();

    LinearLayout mCraftableLayout;
    LinearLayout mInventoryLayout;

    CraftableRecyclerView mCraftableRecyclerView;
    InventorySwitcher mInventorySwitcher;

    public MainSwitcher( Context context ) {
        super( context );
    }

    public MainSwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public void onCreate() {
        Log.d( TAG, "onCreate()" );

        mCraftableLayout = ( LinearLayout ) findViewById( R.id.layout_craftables );

        mCraftableRecyclerView = ( CraftableRecyclerView ) findViewById( R.id.gridview_craftables );
        mCraftableRecyclerView.create();

        Button viewCraftables = ( Button ) findViewById( R.id.button_view_craftables );
        viewCraftables.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if ( !isCraftableCurrentView() ) {
                    showNext();

                    mInventorySwitcher.pause();
                    resumeCraftableRecyclerView();
                }
            }
        } );

        mInventoryLayout = ( LinearLayout ) findViewById( R.id.layout_inventory );

        mInventorySwitcher = ( InventorySwitcher ) findViewById( R.id.switcher_inventory );
        mInventorySwitcher.init();

        Button viewInventory = ( Button ) findViewById( R.id.button_view_inventory );
        viewInventory.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                if ( !isInventoryCurrentView() ) {
                    showNext();

                    pauseCraftableRecyclerView();
                    mInventorySwitcher.resume();
                }
            }
        } );
    }

    public void onPause() {
        if ( isCraftableCurrentView() ) {
            pauseCraftableRecyclerView();
        } else {
            mInventorySwitcher.pause();
        }
    }

    public void onResume() {
        if ( isCraftableCurrentView() ) {
            resumeCraftableRecyclerView();
        } else {
            mInventorySwitcher.resume();
        }
    }

    public void onSearch( String searchQuery ) {
        // toggle craftable recyclerView back if not current view, then execute search
        if ( !isCraftableCurrentView() ) {
            showNext();

            mInventorySwitcher.pause();
            resumeCraftableRecyclerView();
        }

        mCraftableRecyclerView.getAdapter().searchData( searchQuery );
    }

    private void resumeCraftableRecyclerView() {
        NavigationTextView navigationTextView = ( NavigationTextView ) findViewById( R.id.textview_navigation_hierarchy );
        navigationTextView.resume();

        mCraftableRecyclerView.resume();
    }

    private void pauseCraftableRecyclerView() {
        NavigationTextView navigationTextView = ( NavigationTextView ) findViewById( R.id.textview_navigation_hierarchy );
        navigationTextView.pause();

        mCraftableRecyclerView.pause();
    }

    private boolean isCraftableCurrentView() {
        return getCurrentView().getId() == mCraftableLayout.getId();
    }

    private boolean isInventoryCurrentView() {
        return getCurrentView().getId() == mInventoryLayout.getId();
    }
}
