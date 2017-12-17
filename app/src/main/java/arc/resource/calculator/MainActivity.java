package arc.resource.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.lapism.searchview.SearchView;
import com.squareup.picasso.Picasso;

import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.listeners.PrefsObserver;
import arc.resource.calculator.listeners.QueueObserver;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.RecyclerContextMenuInfo;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.DialogUtil;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.FeedbackUtil;
import arc.resource.calculator.views.layouts.CraftableLayout;
import arc.resource.calculator.views.layouts.QueueLayout;

import static arc.resource.calculator.DetailActivity.ADD;
import static arc.resource.calculator.DetailActivity.ERROR;
import static arc.resource.calculator.DetailActivity.REMOVE;
import static arc.resource.calculator.DetailActivity.RESULT_CODE;
import static arc.resource.calculator.DetailActivity.RESULT_EXTRA_NAME;
import static arc.resource.calculator.DetailActivity.UPDATE;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: A:RC, a resource calculator for ARK:Survival Evolved
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone/@ARKResourceCalc
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class MainActivity extends AppCompatActivity
        implements ExceptionObserver.Listener {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String INTENT_KEY_DID_UPDATE = "DID_UPDATE";

    private AdUtil mAdUtil;

    private FloatingActionButton mFabMenu;
    private FloatingActionButton mFabMenuOptionPositive;
    private FloatingActionButton mFabMenuOptionNegative;

    private boolean mIsFabMenuShown;

    private BottomSheetBehavior mBottomSheet;

    // Purchase flow -> disable menu option to disable ads
    // CreateOptionsMenu -> disable menu option to disable ads if purchased

    // TODO: 5/27/2017 Error popup to have BobOnTheCob image


    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ExceptionObserver.getInstance().registerListener( this );

        CraftableLayout craftableLayout = findViewById( R.id.layout_craftables );
        craftableLayout.onCreate();

        registerForContextMenu( craftableLayout.getSwitcher().getRecyclerView() );

        mBottomSheet = BottomSheetBehavior.from( findViewById( R.id.bottom_sheet ) );
        mBottomSheet.setSkipCollapsed( true );
        closeBottomSheet();

        QueueLayout queueLayout = findViewById( R.id.layout_queue );
        queueLayout.onCreate();

        registerForContextMenu( queueLayout.getSwitcher().getRecyclerView() );

        SearchView searchView = findViewById( R.id.searchView );
        searchView.setOnNavigationIconClickListener( new SearchView.OnNavigationIconClickListener() {
            @Override
            public void onNavigationIconClick( float state ) {
                NavigationView navigationView = findViewById( R.id.navigation_view );

                DrawerLayout drawerLayout = findViewById( R.id.drawer_layout );
                drawerLayout.openDrawer( navigationView );
            }
        } );

        NavigationView navigationView = findViewById( R.id.navigation_view );
        View view = navigationView.getHeaderView( 0 );
        ImageView imageView = view.findViewById( R.id.nav_header_image );
        if ( imageView != null ) {
            final String imagePath = "file:///android_asset/splash.png";
            Picasso.with( this )
                    .load( imagePath )
                    .error( R.drawable.placeholder_empty )
                    .placeholder( R.drawable.placeholder_empty )
                    .into( imageView );
        }

        mFabMenu = findViewById( R.id.fabMenu );
        mFabMenu.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                toggleBottomSheet();
            }
        } );

        mFabMenuOptionNegative = findViewById( R.id.fabMenuOptionClearQueue );
        mFabMenuOptionNegative.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                CraftingQueue.getInstance().clearQueue();
            }
        } );

        mFabMenuOptionPositive = findViewById( R.id.fabMenuOptionCalculate );
        mFabMenuOptionPositive.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                Toast.makeText( v.getContext(), "Craft it!", Toast.LENGTH_SHORT ).show();
            }
        } );

        if ( CraftingQueue.getInstance().isQueueEmpty() ) {
            hideFabMenuOptions();
        }

        mBottomSheet.setBottomSheetCallback( new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged( @NonNull View bottomSheet, int newState ) {
                switch ( newState ) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        mFabMenu.setImageResource( R.drawable.ic_hide_bottom_sheet_light );
                        showFabMenuOptions();
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        mFabMenu.setImageResource( R.drawable.ic_show_bottom_sheet_light );
                        hideFabMenuOptions();
                        break;
                }
            }

            @Override
            public void onSlide( @NonNull View bottomSheet, float slideOffset ) {
//                if ( slideOffset <= 0 ) {
//                    float fabOffset = 1 + slideOffset;
//                    if ( mFabMenuOptionNegative.isEnabled() )
//                        mFabMenuOptionNegative.animate().scaleX( fabOffset ).scaleY( fabOffset ).alpha( fabOffset ).setDuration( 0 ).start();
//                    if ( mFabMenuOptionPositive.isEnabled() )
//                        mFabMenuOptionPositive.animate().scaleX( fabOffset ).scaleY( fabOffset ).alpha( fabOffset ).setDuration( 0 ).start();
//                }
            }
        } );

        QueueObserver.getInstance().registerListener( TAG, new QueueObserver.Listener() {
            @Override
            public void onItemChanged( long craftableId, int quantity ) {
//                    enableFabMenuOptions();
                if ( isBottomSheetOpen() ) {
                    showFabMenuOptions();
                }
            }

            @Override
            public void onItemRemoved( long craftableId ) {

            }

            @Override
            public void onDataSetPopulated() {
//                enableFabMenuOptions();
                if ( isBottomSheetOpen() ) {
                    showFabMenuOptions();
                }
            }

            @Override
            public void onDataSetEmpty() {
                hideFabMenuOptions();
//                disableFabMenuOptions();
//                closeBottomSheet();
            }
        } );

        mAdUtil = new AdUtil( this, R.id.content_main );

        showChangeLog();
    }

    /**
     * User pressed back button:
     * Check if Navigation Drawer is open -> close it.
     * Check if Bottom Sheet is open -> close it.
     * Close app.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.navigation_view );

        if ( drawerLayout.isDrawerOpen( navigationView ) ) {
            drawerLayout.closeDrawer( navigationView );
        } else if ( isBottomSheetOpen() ) {
            closeBottomSheet();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ExceptionObserver.getInstance().registerListener( this );

        CraftableLayout craftableLayout = findViewById( R.id.layout_craftables );
        craftableLayout.onResume();

        QueueLayout queueLayout = findViewById( R.id.layout_queue );
        queueLayout.onResume();

        mAdUtil.resume();
    }

    @Override
    protected void onPause() {
        CraftableLayout craftableLayout = findViewById( R.id.layout_craftables );
        craftableLayout.onPause();

        QueueLayout queueLayout = findViewById( R.id.layout_queue );
        queueLayout.onPause();

        mAdUtil.pause();

        ExceptionObserver.getInstance().unregisterListener( this );

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        CraftableLayout craftableLayout = findViewById( R.id.layout_craftables );
        craftableLayout.onDestroy();

        QueueLayout queueLayout = findViewById( R.id.layout_queue );
        queueLayout.onDestroy();

        mAdUtil.destroy();

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu_main, menu );

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu( Menu menu ) {
        final boolean show = super.onPrepareOptionsMenu( menu );

        // set up menu to enable/disable remove ads button
        menu.findItem( R.id.action_remove_ads ).setEnabled( !mAdUtil.mRemoveAds );

        return show;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch ( item.getItemId() ) {
//            case R.id.action_search:
////                DialogUtil.Search( MainActivity.this, new DialogUtil.Callback() {
////                    @Override
////                    public void onResult( @Nullable Object result ) {
////                        String searchQuery = ( String ) result;
////
////                        CraftableLayout craftableLayout = ( CraftableLayout ) findViewById( R.id.layout_craftables );
////                        craftableLayout.onSearch( searchQuery );
////                    }
////
////                    @Override
////                    public void onCancel() {
////                        showSnackBar( getString( R.string.toast_search_fail ) );
////                    }
////                } ).show();
//                break;

//            case R.id.action_settings:
//                startActivityForResult( new Intent( this, SettingsActivity.class ), SettingsActivity.REQUEST_CODE );
//                return true;

            case R.id.action_show_changelog:
                ChangeLog changeLog = new ChangeLog( this );
                changeLog.getFullLogDialog().show();
                break;

            case R.id.action_show_tutorial:
                startActivity( new Intent( this, FirstUseActivity.class ) );
                break;

            case R.id.action_about:
                DialogUtil.About( MainActivity.this ).show();
                break;

            case R.id.action_feedback:
                FeedbackUtil.composeFeedbackEmail( this );
                break;

            case R.id.action_feedback_bug_report:
                FeedbackUtil.composeBugReportEmail( this );
                break;

            case R.id.action_remove_ads:
                mAdUtil.beginPurchase();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onCreateContextMenu( ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo ) {
        super.onCreateContextMenu( menu, v, menuInfo );
        MenuInflater inflater = getMenuInflater();

        switch ( v.getId() ) {
            case R.id.gridview_queue:
                inflater.inflate( R.menu.craftable_in_queue_menu, menu );
                break;

            case R.id.gridview_craftables:
                inflater.inflate( R.menu.displaycase_menu, menu );
                break;
        }
    }

    @Override
    public boolean onContextItemSelected( MenuItem item ) {
        RecyclerContextMenuInfo menuInfo = ( RecyclerContextMenuInfo ) item.getMenuInfo();

        final long id = menuInfo.getId();

        final String name;

        switch ( item.getItemId() ) {
            case R.id.floating_action_remove_from_queue:
                name = CraftingQueue.getInstance().getCraftable( id ).getName();

                CraftingQueue.getInstance().delete( id );

                showSnackBar( String.format( getString( R.string.toast_remove_from_queue_success_format ), name ) );
                break;

            case R.id.floating_action_edit_quantity:
                name = CraftingQueue.getInstance().getCraftable( id ).getName();

                DialogUtil.EditQuantity( MainActivity.this, name, new DialogUtil.Callback() {
                    @Override
                    public void onResult( Object result ) {
                        int quantity = ( int ) result;
                        CraftingQueue.getInstance().setQuantity( getApplicationContext(), id, quantity );

                        showSnackBar( String.format( getString( R.string.toast_edit_quantity_success_format ), name ) );
                    }

                    @Override
                    public void onCancel() {
                        showSnackBar( String.format( getString( R.string.toast_edit_quantity_fail_format ), name ) );
                    }
                } ).show();
                break;

            case R.id.floating_action_view_details:
                startDetailActivity( id );
                break;
        }

        return super.onContextItemSelected( item );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        if ( !mAdUtil.onActivityResult( requestCode, resultCode, data ) ) {
            if ( data != null ) {
                Bundle extras = data.getExtras();

                switch ( requestCode ) {
                    case DetailActivity.REQUEST_CODE:
                        int extraResultCode = extras.getInt( RESULT_CODE );

                        if ( resultCode == RESULT_OK ) {
                            String name = extras.getString( RESULT_EXTRA_NAME );
//                            long id = extras.getLong( RESULT_EXTRA_ID );
//                            int quantity = extras.getInt( RESULT_EXTRA_QUANTITY );

                            switch ( extraResultCode ) {
                                case REMOVE:
//                                    CraftingQueue.getInstance().delete( id );

                                    showSnackBar( String.format( getString( R.string.toast_details_removed_format ), name ) );
                                    break;

                                case UPDATE:
//                                    CraftingQueue.getInstance().setQuantity( this, id, quantity );

                                    showSnackBar( String.format( getString( R.string.toast_details_updated_format ), name ) );
                                    break;

                                case ADD:
//                                    CraftingQueue.getInstance().setQuantity( this, id, quantity );

                                    showSnackBar( String.format( getString( R.string.toast_details_added_format ), name ) );
                                    break;
                            }
                        } else {
                            if ( extraResultCode == ERROR ) {
                                Exception e = ( Exception ) extras.get( RESULT_EXTRA_NAME );

                                if ( e != null ) {
                                    showSnackBar( getString( R.string.toast_details_error ) );

                                    ExceptionObserver.getInstance().notifyExceptionCaught( TAG, e );
                                }
                            } else {
                                showSnackBar( getString( R.string.toast_details_no_change ) );
                            }
                        }
                        break;

                    case SettingsActivity.REQUEST_CODE:
                        if ( resultCode == RESULT_OK ) {
                            boolean dlcValueChange = extras.getBoolean( getString( R.string.pref_dlc_key ) );
                            boolean categoryPrefChange = extras.getBoolean( getString( R.string.pref_filter_category_key ) );
                            boolean stationPrefChange = extras.getBoolean( getString( R.string.pref_filter_station_key ) );
                            boolean levelPrefChange = extras.getBoolean( getString( R.string.pref_filter_level_key ) );
                            boolean levelValueChange = extras.getBoolean( getString( R.string.pref_edit_text_level_key ) );
                            boolean refinedPrefChange = extras.getBoolean( getString( R.string.pref_filter_refined_key ) );

                            showSnackBar( getString( R.string.toast_settings_success ) );

                            PrefsObserver.getInstance().notifyPreferencesChanged(
                                    dlcValueChange, categoryPrefChange, stationPrefChange, levelPrefChange, levelValueChange, refinedPrefChange );
                        } else {
                            showSnackBar( getString( R.string.toast_settings_fail ) );
                        }
                        break;
                }
            } else {
                showSnackBar( getString( R.string.toast_settings_fail ) );
            }
        }
    }

    @Override
    public void onException( String tag, Exception e ) {
        ExceptionUtil.SendErrorReport( tag, e );
    }

    @Override
    public void onFatalException( final String tag, final Exception e ) {
        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                ExceptionUtil.SendErrorReportWithAlertDialog( MainActivity.this, tag, e );
            }
        } );
    }

    private void startDetailActivity( long id ) {
        Intent intent = new Intent( this, DetailActivity.class );
        intent.putExtra( DetailActivity.REQUEST_ID, id );
        startActivityForResult( intent, DetailActivity.REQUEST_CODE );
    }

    private void showChangeLog() {
        ChangeLog changeLog = new ChangeLog( this );

        if ( changeLog.firstRun() ) {
            changeLog.getLogDialog().show();
        }
    }

    private void showSnackBar( String text ) {
        Snackbar.make( findViewById( R.id.content_main ), text, Snackbar.LENGTH_SHORT ).show();
    }

    private void toggleBottomSheet() {
        if ( isBottomSheetOpen() ) {
            closeBottomSheet();
        } else if ( isBottomSheetClosed() ) {
            openBottomSheet();
        }
    }

    private boolean isBottomSheetOpen() {
        return mBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED;
    }

    private boolean isBottomSheetClosed() {
        return mBottomSheet.getState() == BottomSheetBehavior.STATE_HIDDEN;
    }

    private void openBottomSheet() {
        mBottomSheet.setState( BottomSheetBehavior.STATE_EXPANDED );
    }

    private void closeBottomSheet() {
        mBottomSheet.setState( BottomSheetBehavior.STATE_HIDDEN );
    }

//    private void enableFabMenuOptions() {
//        enableFabMenuOption( mFabMenuOptionNegative );
//        enableFabMenuOption( mFabMenuOptionPositive );
//    }
//
//    private void enableFabMenuOption( FloatingActionButton fab ) {
//        fab.setEnabled( true );
//        if ( isBottomSheetOpen() ) {
//            fab.show();
//        }
//    }
//
//    private void disableFabMenuOptions() {
//        disableFabMenuOption( mFabMenuOptionNegative );
//        disableFabMenuOption( mFabMenuOptionPositive );
//    }
//
//    private void disableFabMenuOption( FloatingActionButton fab ) {
//        fab.setEnabled( false );
//        fab.hide();
//    }

    private void showFabMenuOptions() {
        if ( !CraftingQueue.getInstance().isQueueEmpty() ) {
            mFabMenuOptionPositive.show();
            mFabMenuOptionNegative.show();
        }
    }

    private void hideFabMenuOptions() {
        mFabMenuOptionPositive.hide();
        mFabMenuOptionNegative.hide();
    }

    public void rotateFabForward() {
        ViewCompat.animate( mFabMenu )
                .rotation( 135.0F )
                .withLayer()
                .setDuration( 300L )
                .setInterpolator( new OvershootInterpolator( 10.0F ) )
                .start();
    }

    public void rotateFabBackward() {
        ViewCompat.animate( mFabMenu )
                .rotation( 0.0F )
                .withLayer()
                .setDuration( 300L )
                .setInterpolator( new OvershootInterpolator( 10.0F ) )
                .start();
    }
}