package arc.resource.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import arc.resource.calculator.listeners.ErrorReporter;
import arc.resource.calculator.model.CraftingQueue;
import arc.resource.calculator.model.RecyclerContextMenuInfo;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.DialogUtil;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.Util;

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
        implements ErrorReporter.Listener {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String INTENT_KEY_DID_UPDATE = "DID_UPDATE";

    private AdUtil mAdUtil;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // Check to see if database was updated
        boolean didUpdate = getIntent().getBooleanExtra( INTENT_KEY_DID_UPDATE, false );

//        registerForContextMenu( findViewById( R.id.gridview_craftables ) );
//        registerForContextMenu( findViewById( R.id.gridview_inventory ) );
//        registerForContextMenu( findViewById( R.id.gridview_queue ) );

        // TODO: 4/13/2017 We need a notifier for the Clear Queue button, to enable and disable it
        Button clearQueueButton = ( Button ) findViewById( R.id.button_clear_queue );
//        clearQueueButton.setEnabled( !CraftingQueue.getInstance().isQueueEmpty() );
        clearQueueButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                CraftingQueue.getInstance().clearQueue();
            }
        } );

        mAdUtil = new AdUtil( this, R.id.content_main );
        mAdUtil.init();

        showChangeLog();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ErrorReporter.getInstance().registerListener( this );

        mAdUtil.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ErrorReporter.getInstance().unregisterListener( this );

        savePreferences();

        mAdUtil.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        mPurchaseUtil.shutDown();
        mAdUtil.destroy();
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
                    public void onResult( Object result ) {
                        // TODO: 4/10/2017 Search window while on inventory screen?
                        // TODO: 4/13/2017 Emit trigger to query for search data
                        String searchQuery = ( String ) result;
//                        DisplayCase.getInstance().searchData( getApplicationContext(), searchQuery );
                    }
                } ).show();
                break;

            case R.id.action_settings:
                startActivityForResult( new Intent( this, SettingsActivity.class ),
                        Util.REQUEST_CODE_SETTINGS_ACTIVITY );
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

//                mPurchaseUtil.setup();
//                mPurchaseUtil.purchaseSku( PurchaseUtil.SKU_FEATURE_DISABLE_ADS, new EmptyRequestListener<Purchase>() {
//                    @Override
//                    public void onSuccess( @Nonnull Purchase result ) {
//                        Toast.makeText( MainActivity.this, "Purchase successful.", Toast.LENGTH_SHORT ).setup();
//
//                        switch ( result.sku ) {
//                            case PurchaseUtil.SKU_FEATURE_DISABLE_ADS:
//                                Toast.makeText( MainActivity.this, "Consider those ads tamed!!", Toast.LENGTH_LONG ).setup();
//
//                                mAdUtil.unloadAdView();
//
//                                new PrefsUtil( getApplicationContext() )
//                                        .setPurchasePref( PurchaseUtil.SKU_FEATURE_DISABLE_ADS, true );
//                        }
//
//                        mPurchaseUtil.shutDown();
//                    }
//
//                    @Override
//                    public void onError( int response, @Nonnull Exception e ) {
//                        Toast.makeText( MainActivity.this, "Purchase failed.", Toast.LENGTH_SHORT ).setup();
//
//                        Log.e( TAG, "Purchase failed.", e );
//
//                        new PrefsUtil( getApplicationContext() )
//                                .setPurchasePref( PurchaseUtil.SKU_FEATURE_DISABLE_ADS, false );
//
//                        mPurchaseUtil.shutDown();
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

        switch ( item.getItemId() ) {
            case R.id.floating_action_remove_from_queue:
                CraftingQueue.getInstance().delete( id );
                break;

            case R.id.floating_action_edit_quantity:
                String name = CraftingQueue.getInstance().getCraftable( id ).getName();

                DialogUtil.EditQuantity( MainActivity.this, name, new DialogUtil.Callback() {
                    @Override
                    public void onResult( Object result ) {
                        int quantity = ( int ) result;
                        CraftingQueue.getInstance().setQuantity( getApplicationContext(), id, quantity );
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

        switch ( requestCode ) {
            case Util.REQUEST_CODE_DETAIL_ACTIVITY:
                if ( resultCode == RESULT_OK ) {
                    Bundle extras = data.getExtras();

                    long craftableId = extras.getLong( Util.DETAIL_ID );

                    int quantity = extras.getInt( Util.DETAIL_QUANTITY );
                    CraftingQueue.getInstance().setQuantity( this, craftableId, quantity );
                }
                break;
            case Util.REQUEST_CODE_SETTINGS_ACTIVITY:
                if ( resultCode == RESULT_OK ) {
                    Bundle extras = data.getExtras();

                    // TODO: 4/10/2017 create preferencelistener in listadapter?

                    boolean dlcValueChange = extras.getBoolean( getString( R.string.pref_dlc_key ) );
                    boolean categoryPrefChange = extras.getBoolean( getString( R.string.pref_filter_category_key ) );
                    boolean stationPrefChange = extras.getBoolean( getString( R.string.pref_filter_station_key ) );
                    boolean levelPrefChange = extras.getBoolean( getString( R.string.pref_filter_level_key ) );
                    boolean levelValueChange = extras.getBoolean( getString( R.string.pref_edit_text_level_key ) );
                    boolean refinedPrefChange = extras.getBoolean( getString( R.string.pref_filter_refined_key ) );

                    // if any pref that deals with displaying engrams was changed, reset levels to root.
                    if ( dlcValueChange || categoryPrefChange || stationPrefChange || levelPrefChange || levelValueChange ) {
//                        DisplayCase.getInstance().setCategoryLevelsToRoot();

                        // if dlc was changed, clear entire queue
                        if ( dlcValueChange )
                            CraftingQueue.getInstance().clearQueue();

                        // settings were changed, let's update DisplayCase with new data
//                        DisplayCase.getInstance().refreshData( getApplicationContext() );
                    }
                }
                break;
        }
    }

    private void startDetailActivity( long _id ) {
        savePreferences();

        Intent intent = new Intent( this, DetailActivity.class );
        intent.putExtra( Util.DETAIL_ID, _id );

        startActivityForResult( intent, Util.REQUEST_CODE_DETAIL_ACTIVITY );
    }

    private void savePreferences() {
        // save category levels
        // write queue to database, if need be
        // TODO: 4/13/2017 save category levels from outside of craftable adapter
//        DisplayCase.getInstance().saveCategoryLevelsToPref();
        CraftingQueue.getInstance().saveQueueToPrefs();
    }

    private void showChangeLog() {
        ChangeLog changeLog = new ChangeLog( this );

        if ( changeLog.firstRun() ) {
            changeLog.getLogDialog().show();
        }
    }

    @Override
    public void onSendErrorReport( String tag, Exception e ) {
        ExceptionUtil.SendErrorReport( tag, e );
    }

    @Override
    public void onSendErrorReportWithAlertDialog( String tag, Exception e ) {
        ExceptionUtil.SendErrorReportWithAlertDialog( MainActivity.this, tag, e );
    }
}