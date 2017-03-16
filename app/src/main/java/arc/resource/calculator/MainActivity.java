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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.instabug.library.Feature;
import com.instabug.library.Instabug;
import com.instabug.library.InstabugColorTheme;
import com.instabug.library.invocation.InstabugInvocationEvent;

import arc.resource.calculator.listeners.MainActivityListener;
import arc.resource.calculator.listeners.SendErrorReportListener;
import arc.resource.calculator.model.RecyclerContextMenuInfo;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.DialogUtil;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.ListenerUtil;
import arc.resource.calculator.util.Util;
import arc.resource.calculator.views.DisplayCaseRecyclerView;
import arc.resource.calculator.views.QueueCompositionRecyclerView;
import arc.resource.calculator.views.QueueEngramRecyclerView;

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
        implements MainActivityListener, SendErrorReportListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String INTENT_KEY_DID_UPDATE = "DID_UPDATE";

    DisplayCaseRecyclerView mDisplayCaseRecyclerView;
    QueueEngramRecyclerView mQueueEngramRecyclerView;
    QueueCompositionRecyclerView mQueueCompositionRecyclerView;
    Button mClearCraftingQueue;
    TextView mCategoryHierarchyTextView;

    //    private PurchaseUtil mPurchaseUtil;
    private AdUtil mAdUtil;

    private ListenerUtil mCallback;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mCallback = ListenerUtil.getInstance();
        mCallback.setMainActivityListener( this );
        mCallback.setSendErrorReportListener( this );

        // Check to see if database was updated
        boolean didUpdate = getIntent().getBooleanExtra( INTENT_KEY_DID_UPDATE, false );

        mDisplayCaseRecyclerView = ( DisplayCaseRecyclerView ) findViewById( R.id.display_case_engram_list );
        registerForContextMenu( mDisplayCaseRecyclerView );

        mQueueEngramRecyclerView = ( QueueEngramRecyclerView ) findViewById( R.id.crafting_queue_engram_list );
        registerForContextMenu( mQueueEngramRecyclerView );

        mQueueCompositionRecyclerView = ( QueueCompositionRecyclerView ) findViewById( R.id.crafting_queue_resource_list );
        registerForContextMenu( mQueueCompositionRecyclerView );

        mClearCraftingQueue = ( Button ) findViewById( R.id.clear_queue_button );
        mClearCraftingQueue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                mCallback.requestRemoveAllFromQueue( getApplicationContext() );
            }
        } );

        mCategoryHierarchyTextView = ( TextView ) findViewById( R.id.hierarchy_text );
        mCallback.requestCategoryHierarchy( this );

        updateContainerLayoutParams();

        new Instabug.Builder( getApplication(), BuildConfig.INSTABUG_API_KEY )
                .setInvocationEvent( InstabugInvocationEvent.SHAKE )
                .setTheme( InstabugColorTheme.InstabugColorThemeDark )
                .setEmailFieldRequired( false )
                .setCommentFieldRequired( true )
                .setCrashReportingState( Feature.State.DISABLED )
                .setSurveysState( Feature.State.DISABLED )
                .setInAppMessagingState( Feature.State.DISABLED )
                .build();

//        mPurchaseUtil = new PurchaseUtil( this );

        mAdUtil = new AdUtil( this, R.id.content_main );
        mAdUtil.init();

        showChangeLog();

//        Toast.makeText( this, getString( R.string.dimens ), Toast.LENGTH_SHORT ).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAdUtil.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mAdUtil.pause();
        mCallback.requestSaveCategoryLevels( getApplicationContext() );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        mPurchaseUtil.stop();
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
                DialogUtil.Search( MainActivity.this ).show();
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

//                mPurchaseUtil.start();
//                mPurchaseUtil.purchaseSku( PurchaseUtil.SKU_FEATURE_DISABLE_ADS, new EmptyRequestListener<Purchase>() {
//                    @Override
//                    public void onSuccess( @Nonnull Purchase result ) {
//                        Toast.makeText( MainActivity.this, "Purchase successful.", Toast.LENGTH_SHORT ).show();
//
//                        switch ( result.sku ) {
//                            case PurchaseUtil.SKU_FEATURE_DISABLE_ADS:
//                                Toast.makeText( MainActivity.this, "Consider those ads tamed!!", Toast.LENGTH_LONG ).show();
//
//                                mAdUtil.unloadAdView();
//
//                                new PrefsUtil( getApplicationContext() )
//                                        .setPurchasePref( PurchaseUtil.SKU_FEATURE_DISABLE_ADS, true );
//                        }
//
//                        mPurchaseUtil.stop();
//                    }
//
//                    @Override
//                    public void onError( int response, @Nonnull Exception e ) {
//                        Toast.makeText( MainActivity.this, "Purchase failed.", Toast.LENGTH_SHORT ).show();
//
//                        Log.e( TAG, "Purchase failed.", e );
//
//                        new PrefsUtil( getApplicationContext() )
//                                .setPurchasePref( PurchaseUtil.SKU_FEATURE_DISABLE_ADS, false );
//
//                        mPurchaseUtil.stop();
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
            case R.id.crafting_queue_engram_list:
                inflater.inflate( R.menu.craftable_in_queue_menu, menu );
                break;

            case R.id.display_case_engram_list:
                inflater.inflate( R.menu.displaycase_menu, menu );
                break;
        }
    }

    @Override
    public boolean onContextItemSelected( MenuItem item ) {
        RecyclerContextMenuInfo menuInfo = ( RecyclerContextMenuInfo ) item.getMenuInfo();

        switch ( item.getItemId() ) {
            case R.id.floating_action_remove_from_queue:
                mCallback.requestRemoveOneFromQueue( getApplicationContext(), menuInfo.getId() );
                break;

            case R.id.floating_action_edit_quantity:
                DialogUtil.EditQuantity( MainActivity.this, menuInfo.getId() ).show();
                break;

            case R.id.floating_action_view_details:
                startDetailActivity( menuInfo.getId() );
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

                    long _id = extras.getLong( Util.DETAIL_ID );

                    if ( extras.getBoolean( Util.DETAIL_REMOVE ) )
                        mCallback.requestRemoveOneFromQueue( this, _id );

                    int quantity = extras.getInt( Util.DETAIL_QUANTITY );
                    if ( quantity > 0 )
                        mCallback.requestUpdateQuantity( this, _id, quantity );
                }
                break;
            case Util.REQUEST_CODE_SETTINGS_ACTIVITY:
                if ( resultCode == RESULT_OK ) {
                    Bundle extras = data.getExtras();

                    boolean dlcValueChange = extras.getBoolean( getString( R.string.pref_dlc_key ) );
                    boolean categoryPrefChange = extras.getBoolean( getString( R.string.pref_filter_category_key ) );
                    boolean stationPrefChange = extras.getBoolean( getString( R.string.pref_filter_station_key ) );
                    boolean levelPrefChange = extras.getBoolean( getString( R.string.pref_filter_level_key ) );
                    boolean levelValueChange = extras.getBoolean( getString( R.string.pref_edit_text_level_key ) );
                    boolean refinedPrefChange = extras.getBoolean( getString( R.string.pref_filter_refined_key ) );

                    // if any pref that deals with displaying engrams was changed, reset levels to root.
                    if ( dlcValueChange || categoryPrefChange || stationPrefChange || levelPrefChange || levelValueChange ) {
                        mCallback.requestResetCategoryLevels( this );

                        // if dlc was changed, clear entire queue
                        if ( dlcValueChange )
                            mCallback.requestRemoveAllFromQueue( this );

                        // settings were changed, let's update DisplayCase with new data
                        mCallback.requestDisplayCaseDataSetChange( this );
                    }

                    // if refined/raw was changed, let's update the composition data set to reflection such change
                    if ( refinedPrefChange )
                        mCallback.requestResourceDataSetChange( this );
                }
                break;
        }

    }

    private void startDetailActivity( long _id ) {
        mCallback.requestSaveCategoryLevels( this );

        Intent intent = new Intent( this, DetailActivity.class );
        intent.putExtra( Util.DETAIL_ID, _id );

        startActivityForResult( intent, Util.REQUEST_CODE_DETAIL_ACTIVITY );
    }

    private void showChangeLog() {
        ChangeLog changeLog = new ChangeLog( this );

        if ( changeLog.firstRun() ) {
            changeLog.getLogDialog().show();
        }
    }

    private void updateContainerLayoutParams() {
        boolean isQueueEmpty = mQueueEngramRecyclerView.getAdapter().getItemCount() == 0;

        LinearLayout.LayoutParams layoutParams;

        if ( isQueueEmpty ) {
            layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0 );
        } else {
            layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, getResources().getInteger( R.integer.weight_bottom_container ) );
        }

        View container = findViewById( R.id.content_main_bottom_container );
        container.setLayoutParams( layoutParams );
    }

    @Override
    public void onCategoryHierarchyResolved( String text ) {
        mCategoryHierarchyTextView.setText( text );
    }

    @Override
    public void onRequestLayoutUpdate() {
        updateContainerLayoutParams();
    }

    @Override
    public void onSendErrorReport( final String tag, final Exception e, boolean showAlertDialog ) {
        if ( showAlertDialog )
            runOnUiThread( new Runnable() {
                @Override
                public void run() {
                    ExceptionUtil.SendErrorReportWithAlertDialog( MainActivity.this, tag, e );
                }
            } );
        else
            ExceptionUtil.SendErrorReport( MainActivity.this, tag, e );
    }
}