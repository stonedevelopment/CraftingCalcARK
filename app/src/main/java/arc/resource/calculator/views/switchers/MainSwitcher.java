package arc.resource.calculator.views.switchers;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.util.PrefsUtil;
import arc.resource.calculator.views.layouts.CalculateLayout;
import arc.resource.calculator.views.layouts.CraftableLayout;

public class MainSwitcher extends ViewSwitcher {
    private static final String TAG = MainSwitcher.class.getSimpleName();

    public static final int CRAFTABLE_SCREEN = 0;
    public static final int CALCULATOR_SCREEN = 1;
    public static final int DEFAULT_SCREEN = CRAFTABLE_SCREEN;

    CraftableLayout mCraftableLayout;
    CalculateLayout mCalculatorLayout;

    public MainSwitcher( Context context ) {
        super( context );
    }

    public MainSwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public void onCreate() {
        Log.d( TAG, "onCreate: " );
        mCraftableLayout = ( CraftableLayout ) findViewById( R.id.layout_craftables );
        mCraftableLayout.onCreate();

//        findViewById( R.id.button_view_craftables )
//                .setOnClickListener( new OnClickListener() {
//                    @Override
//                    public void onClick( View v ) {
//                        showCraftableScreen();
//                    }
//                } );

//        mCalculatorLayout = ( CalculateLayout ) findViewById( R.id.layout_calculate );
//        mCalculatorLayout.onCreate( this );
//
//        findViewById( R.id.button_calculate )
//                .setOnClickListener( new OnClickListener() {
//                    @Override
//                    public void onClick( View v ) {
//                        showCalculatorScreen();
//                    }
//                } );

        int screenId = PrefsUtil.getInstance( getContext() ).getMainSwitcherScreenId();

        if ( screenId == CALCULATOR_SCREEN ) {
            showCalculatorScreen();
        }
    }

    public void onPause() {
        Log.d( TAG, "onPause: " );
        if ( isCurrentView( mCraftableLayout.getId() ) ) {
            mCraftableLayout.onPause();
        } else {
            mCalculatorLayout.onPause();
        }

        PrefsUtil.getInstance( getContext() ).saveMainSwitcherScreenId( getCurrentScreenId() );
    }

    public void onResume() {
        Log.d( TAG, "onResume: " );
        if ( isCurrentView( mCraftableLayout.getId() ) ) {
            mCraftableLayout.onResume();
        } else {
            mCalculatorLayout.onResume();
        }
    }

    public void onDestroy() {
        mCraftableLayout.onDestroy();
        mCalculatorLayout.onDestroy();
    }

    public void onSearch( String searchQuery ) {
        // toggle craftable recyclerView back if not current view, then execute search
        showCraftableScreen();

        mCraftableLayout.onSearch( searchQuery );
    }

    private boolean isCurrentView( int id ) {
        return getCurrentView().getId() == id;
    }

    public void showCalculatorScreen() {
        // toggle view to inventory
        if ( !isCurrentView( mCalculatorLayout.getId() ) ) {
            showNext();

            // onPause craftable view
            mCraftableLayout.onPause();

            // onResume inventory view
            mCalculatorLayout.onResume();
        }
    }

    public void showCraftableScreen() {
        // toggle view to craftables
        if ( !isCurrentView( mCraftableLayout.getId() ) ) {
            showNext();

            // onPause inventory view
            mCalculatorLayout.onPause();

            // onResume craftable view
            mCraftableLayout.onResume();
        }
    }

    private int getCurrentScreenId() {
        return isCurrentView( mCraftableLayout.getId() ) ? CRAFTABLE_SCREEN : CALCULATOR_SCREEN;
    }
}
