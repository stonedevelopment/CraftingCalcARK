package arc.resource.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.listeners.PrefsObserver;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.RecyclerContextMenuInfo;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.DialogUtil;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.views.ClearQueueButton;
import arc.resource.calculator.views.CraftableRecyclerView;
import arc.resource.calculator.views.MainSwitcher;
import arc.resource.calculator.views.QueueRecyclerView;
import arc.resource.calculator.views.QueueSwitcher;

import static arc.resource.calculator.DetailActivity.ADD;
import static arc.resource.calculator.DetailActivity.ERROR;
import static arc.resource.calculator.DetailActivity.REMOVE;
import static arc.resource.calculator.DetailActivity.RESULT_CODE;
import static arc.resource.calculator.DetailActivity.RESULT_EXTRA;
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

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Log.d( TAG, "onCreate: " );

        // Check to see if database was updated
        boolean didUpdate = getIntent().getBooleanExtra( INTENT_KEY_DID_UPDATE, false );

        ExceptionObserver.getInstance().registerListener( this );

        MainSwitcher mainSwitcher = ( MainSwitcher ) findViewById( R.id.switcher_main );
        mainSwitcher.onCreate();

        QueueSwitcher queueSwitcher = ( QueueSwitcher ) findViewById( R.id.switcher_queue );
        queueSwitcher.onCreate();

        CraftableRecyclerView craftableRecyclerView = ( CraftableRecyclerView ) findViewById( R.id.gridview_craftables );
        registerForContextMenu( craftableRecyclerView );

        QueueRecyclerView queueRecyclerView = ( QueueRecyclerView ) findViewById( R.id.gridview_queue );
        registerForContextMenu( queueRecyclerView );

        // TODO: 4/16/2017 What if phone is without internet?
        mAdUtil = new AdUtil( this, R.id.content_main );
        mAdUtil.init();

        showChangeLog();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d( TAG, "onResume: " );

        ExceptionObserver.getInstance().registerListener( this );

        MainSwitcher mainSwitcher = ( MainSwitcher ) findViewById( R.id.switcher_main );
        mainSwitcher.onResume();

        QueueSwitcher queueSwitcher = ( QueueSwitcher ) findViewById( R.id.switcher_queue );
        queueSwitcher.onResume();

        ClearQueueButton button = ( ClearQueueButton ) findViewById( R.id.button_clear_queue );
        button.onResume();

        mAdUtil.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d( TAG, "onPause: " );

        PrefsObserver.getInstance().requestPreferencesSave();

        MainSwitcher mainSwitcher = ( MainSwitcher ) findViewById( R.id.switcher_main );
        mainSwitcher.onPause();

        QueueSwitcher queueSwitcher = ( QueueSwitcher ) findViewById( R.id.switcher_queue );
        queueSwitcher.onPause();

        ClearQueueButton button = ( ClearQueueButton ) findViewById( R.id.button_clear_queue );
        button.onPause();

        mAdUtil.pause();

        ExceptionObserver.getInstance().unregisterListener( this );
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
//        menu.findItem( R.id.action_remove_ads ).setEnabled(
//                !new PrefsUtil( getApplicationContext() ).getPurchasePref( PurchaseUtil.SKU_FEATURE_DISABLE_ADS ) );

        return show;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch ( item.getItemId() ) {
            case R.id.action_search:
                DialogUtil.Search( MainActivity.this, new DialogUtil.Callback() {
                    @Override
                    public void onResult( @Nullable Object result ) {
                        String searchQuery = ( String ) result;

                        if ( searchQuery != null ) {
                            MainSwitcher mainSwitcher = ( MainSwitcher ) findViewById( R.id.switcher_main );
                            mainSwitcher.onSearch( searchQuery );
                        }
                    }
                } ).show();
                break;

            case R.id.action_settings:
                startActivityForResult( new Intent( this, SettingsActivity.class ), SettingsActivity.REQUEST_CODE );
                return true;

            case R.id.action_show_changelog:
                ChangeLog changeLog = new ChangeLog( this );
                changeLog.getFullLogDialog().show();
                break;

            case R.id.action_show_helpdialog:
                HelpDialog helpDialog = new HelpDialog( this );
                helpDialog.getDialog().show();
                break;

            case R.id.action_about:
                DialogUtil.About( MainActivity.this ).show();
                break;

            case R.id.action_remove_ads:
                // purchase
                // refund

//                PrefsUtil prefs = new PrefsUtil( this );
//                boolean removeAds = prefs.getPurchasePref( PurchaseUtil.SKU_FEATURE_DISABLE_ADS );
//
//                if ( removeAds )
//                    mAdUtil.reloadAdView();
//                else
//                    mAdUtil.unloadAdView();
//
//                prefs.setPurchasePref( PurchaseUtil.SKU_FEATURE_DISABLE_ADS, !removeAds );

//                mPurchaseUtil.resume();
//                mPurchaseUtil.purchaseSku( PurchaseUtil.SKU_FEATURE_DISABLE_ADS, new EmptyRequestListener<Purchase>() {
//                    @Override
//                    public void onSuccess( @Nonnull Purchase result ) {
//                        Toast.makeText( MainActivity.this, "Purchase successful.", Toast.LENGTH_SHORT ).resume();
//
//                        switch ( result.sku ) {
//                            case PurchaseUtil.SKU_FEATURE_DISABLE_ADS:
//                                Toast.makeText( MainActivity.this, "Consider those ads tamed!!", Toast.LENGTH_LONG ).resume();
//
//                                mAdUtil.unloadAdView();
//
//                                new PrefsUtil( getApplicationContext() )
//                                        .setPurchasePref( PurchaseUtil.SKU_FEATURE_DISABLE_ADS, true );
//                        }
//
//                        mPurchaseUtil.pause();
//                    }
//
//                    @Override
//                    public void onError( int response, @Nonnull Exception e ) {
//                        Toast.makeText( MainActivity.this, "Purchase failed.", Toast.LENGTH_SHORT ).resume();
//
//                        Log.e( TAG, "Purchase failed.", e );
//
//                        new PrefsUtil( getApplicationContext() )
//                                .setPurchasePref( PurchaseUtil.SKU_FEATURE_DISABLE_ADS, false );
//
//                        mPurchaseUtil.pause();
//                    }
//                } );
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

                Toast.makeText( this, String.format( getString( R.string.toast_remove_from_queue_success_format ), name ), Toast.LENGTH_SHORT ).show();
                break;

            case R.id.floating_action_edit_quantity:
                name = CraftingQueue.getInstance().getCraftable( id ).getName();

                DialogUtil.EditQuantity( MainActivity.this, name, new DialogUtil.Callback() {
                    @Override
                    public void onResult( Object result ) {
                        int quantity = ( int ) result;
                        CraftingQueue.getInstance().setQuantity( getApplicationContext(), id, quantity );

                        Toast.makeText( MainActivity.this,
                                String.format( getString( R.string.toast_edit_quantity_success_format ), name ), Toast.LENGTH_SHORT ).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText( MainActivity.this,
                                String.format( getString( R.string.toast_edit_quantity_fail_format ), name ), Toast.LENGTH_SHORT ).show();
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

        if ( data != null ) {
            Bundle extras = data.getExtras();
            switch ( requestCode ) {
                case DetailActivity.REQUEST_CODE:
                    if ( resultCode == RESULT_OK ) {
                        switch ( extras.getInt( RESULT_CODE ) ) {
                            case REMOVE:
                                Toast.makeText( this, getString( R.string.toast_details_removed ), Toast.LENGTH_SHORT ).show();
                                break;

                            case UPDATE:
                                Toast.makeText( this, getString( R.string.toast_details_updated ), Toast.LENGTH_SHORT ).show();
                                break;

                            case ADD:
                                Toast.makeText( this, getString( R.string.toast_details_added ), Toast.LENGTH_SHORT ).show();
                                break;
                        }
                    } else {
                        if ( extras.getInt( RESULT_CODE ) == ERROR ) {
                            Exception e = ( Exception ) extras.get( RESULT_EXTRA );

                            if ( e != null ) {
                                Toast.makeText( this, getString( R.string.toast_details_error ), Toast.LENGTH_SHORT ).show();

                                ExceptionObserver.getInstance().notifyExceptionCaught( TAG, e );
                            }
                        } else {
                            Toast.makeText( this, getString( R.string.toast_details_no_change ), Toast.LENGTH_SHORT ).show();
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

                        Toast.makeText( this, getString( R.string.toast_settings_success ), Toast.LENGTH_SHORT ).show();

                        PrefsObserver.getInstance().notifyPreferencesChanged(
                                dlcValueChange, categoryPrefChange, stationPrefChange, levelPrefChange, levelValueChange, refinedPrefChange );
                    } else {
                        Toast.makeText( this, getString( R.string.toast_settings_fail ), Toast.LENGTH_SHORT ).show();
                    }
                    break;
            }
        } else {
            Toast.makeText( this, getString( R.string.toast_settings_fail ), Toast.LENGTH_SHORT ).show();
        }
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
}