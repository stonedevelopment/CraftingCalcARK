package arc.resource.calculator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.util.PrefsUtil;

public class MainSwitcher extends ViewSwitcher {
    private static final String TAG = MainSwitcher.class.getSimpleName();

    public static final int CRAFTABLE_SCREEN = 0;
    public static final int INVENTORY_SCREEN = 1;
    public static final int DEFAULT_SCREEN = CRAFTABLE_SCREEN;

    CraftableLayout mCraftableLayout;
    InventoryLayout mInventoryLayout;

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
                showCraftableScreen();
            }
        } );

        mInventoryLayout = ( InventoryLayout ) findViewById( R.id.layout_inventory );
        mInventoryLayout.onCreate();

        Button viewInventory = ( Button ) findViewById( R.id.button_view_inventory );
        viewInventory.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                showInventoryScreen();
            }
        } );

        int screenId = PrefsUtil.getInstance( getContext() ).getMainSwitcherScreenId();

        if ( screenId == INVENTORY_SCREEN ) {
            showInventoryScreen();
        }
    }

    public void onPause() {
        if ( isCurrentView( mCraftableLayout.getId() ) ) {
            mCraftableLayout.onPause();
        } else {
            mInventoryLayout.onPause();
        }
    }

    public void onResume() {
        if ( isCurrentView( mCraftableLayout.getId() ) ) {
            mCraftableLayout.onResume();
        } else {
            mInventoryLayout.onResume();
        }
    }

    public void onDestroy() {
        mCraftableLayout.onDestroy();
        mInventoryLayout.onDestroy();
    }

    public void onSearch( String searchQuery ) {
        // toggle craftable recyclerView back if not current view, then execute search
        showCraftableScreen();

        mCraftableLayout.onSearch( searchQuery );
    }

    private boolean isCurrentView( int id ) {
        return getCurrentView().getId() == id;
    }

    public void showInventoryScreen() {
        // toggle view to inventory
        if ( !isCurrentView( mInventoryLayout.getId() ) )
            showNext();

        // onPause craftable view
        mCraftableLayout.onPause();

        // onResume inventory view
        mInventoryLayout.onResume();
    }

    public void showCraftableScreen() {
        // toggle view to craftables
        if ( !isCurrentView( mCraftableLayout.getId() ) )
            showNext();

        // onPause inventory view
        mInventoryLayout.onPause();

        // onResume craftable view
        mCraftableLayout.onResume();
    }
}
