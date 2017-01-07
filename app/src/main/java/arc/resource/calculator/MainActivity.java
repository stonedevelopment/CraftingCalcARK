package arc.resource.calculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import arc.resource.calculator.adapters.CraftableEngramListAdapter;
import arc.resource.calculator.adapters.CraftableResourceListAdapter;
import arc.resource.calculator.adapters.DisplayCaseListAdapter;
import arc.resource.calculator.db.DatabaseHelper;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.DisplayUtil;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.Helper;
import arc.resource.calculator.util.PrefsUtil;
import arc.resource.calculator.views.RecyclerTouchListener;

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
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DisplayCaseListAdapter displayCaseListAdapter;
    private CraftableEngramListAdapter craftableEngramListAdapter;
    private CraftableResourceListAdapter craftableResourceListAdapter;

    private RecyclerView displayCaseEngramList;

    public static float mEngramDimensions;
    public static float mCraftableEngramDimensions;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        try {
            DisplayUtil displayUtil = new DisplayUtil( getApplicationContext(), getWindowManager().getDefaultDisplay() );
            mEngramDimensions = displayUtil.getDisplayCaseGridViewHolderDimensions();
            mCraftableEngramDimensions = displayUtil.getCraftingQueueGridViewHolderDimensions();

            displayCaseEngramList = ( RecyclerView ) findViewById( R.id.display_case_engram_list );
            RecyclerView craftingQueueEngramList = ( RecyclerView ) findViewById( R.id.crafting_queue_engram_list );
            RecyclerView craftingQueueResourceList = ( RecyclerView ) findViewById( R.id.crafting_queue_resource_list );

            if ( craftingQueueEngramList != null ) {
                RecyclerTouchListener craftingQueueEngramTouchListener = new RecyclerTouchListener( this, craftingQueueEngramList,
                        new RecyclerTouchListener.ClickListener() {
                            @Override
                            public void onClick( View view, int position ) {
                                try {
                                    if ( position >= 0 && position < craftableEngramListAdapter.getItemCount() ) {
                                        craftableEngramListAdapter.increaseQuantity( getApplicationContext(), position );

                                        RefreshViews( false );
                                    }
                                } catch ( Exception e ) {
                                    SendErrorReport( e );
                                }
                            }

                            @Override
                            public void onLongClick( View view, int position ) {
                                try {
                                    if ( position >= 0 && position < craftableEngramListAdapter.getItemCount() ) {
                                        Intent intent = new Intent( view.getContext(), DetailActivity.class );
                                        intent.putExtra(
                                                Helper.DETAIL_ID,
                                                craftableEngramListAdapter.getEngram( position ).getId() );

                                        startActivityForResult( intent, Helper.REQUEST_CODE_DETAIL_ACTIVITY );
                                    }
                                } catch ( Exception e ) {
                                    SendErrorReport( e );
                                }
                            }
                        }
                );

                craftableEngramListAdapter = new CraftableEngramListAdapter( MainActivity.this );

                craftingQueueEngramList.setLayoutManager(
                        new GridLayoutManager( this, 1, GridLayoutManager.HORIZONTAL, false ) );
                craftingQueueEngramList.addOnItemTouchListener( craftingQueueEngramTouchListener );
                craftingQueueEngramList.setAdapter( craftableEngramListAdapter );
            }

            if ( craftingQueueResourceList != null ) {
                craftableResourceListAdapter = new CraftableResourceListAdapter( this );

                RecyclerView.LayoutManager craftableResourceLayoutManager =
                        new LinearLayoutManager( this );

                craftingQueueResourceList.setLayoutManager( craftableResourceLayoutManager );
                craftingQueueResourceList.setAdapter( craftableResourceListAdapter );
            }

            if ( displayCaseEngramList != null ) {
                RecyclerTouchListener displayCaseTouchListener = new RecyclerTouchListener( this, displayCaseEngramList,
                        new RecyclerTouchListener.ClickListener() {
                            @Override
                            public void onClick( View view, int position ) {
                                try {
                                    if ( position > -1 && position < displayCaseListAdapter.getItemCount() ) {
                                        if ( displayCaseListAdapter.isEngram( position ) ) {
                                            boolean viewChanged =
                                                    craftableEngramListAdapter.getItemCount() == 0;

                                            craftableEngramListAdapter.increaseQuantity(
                                                    getApplicationContext(),
                                                    displayCaseListAdapter.getEngram( position ).getId() );

                                            RefreshViews( viewChanged );

                                            if ( viewChanged )
                                                displayCaseEngramList.scrollToPosition( position );

                                        } else if ( displayCaseListAdapter.isCategory( position ) ) {
                                            displayCaseListAdapter.changeCategory( position );
                                            RefreshDisplayCase();
                                        } else if ( displayCaseListAdapter.isStation( position ) ) {
                                            displayCaseListAdapter.changeStation( position );
                                            RefreshDisplayCase();
                                        } else {
                                            throw new ArrayIndexOutOfBoundsException( position );
                                        }
                                    }
                                } catch ( Exception e ) {
                                    SendErrorReport( e );
                                }
                            }

                            @Override
                            public void onLongClick( View view, int position ) {
                                try {
                                    if ( position > -1 && position < displayCaseListAdapter.getItemCount() ) {
                                        if ( displayCaseListAdapter.isEngram( position ) ) {
                                            Intent intent = new Intent( view.getContext(), DetailActivity.class );
                                            intent.putExtra(
                                                    Helper.DETAIL_ID,
                                                    displayCaseListAdapter.getEngram( position ).getId() );

                                            startActivityForResult( intent, Helper.REQUEST_CODE_DETAIL_ACTIVITY );
                                        } else if ( displayCaseListAdapter.isCategory( position ) ) {
                                            displayCaseListAdapter.changeCategory( position );
                                            RefreshDisplayCase();
                                        } else if ( displayCaseListAdapter.isStation( position ) ) {
                                            displayCaseListAdapter.changeStation( position );
                                            RefreshDisplayCase();
                                        } else {
                                            throw new ArrayIndexOutOfBoundsException( position );
                                        }
                                    }
                                } catch ( Exception e ) {
                                    SendErrorReport( e );
                                }
                            }
                        }
                );

                displayCaseEngramList.addOnItemTouchListener( displayCaseTouchListener );

                displayCaseListAdapter = new DisplayCaseListAdapter( MainActivity.this );
                displayCaseEngramList.setAdapter( displayCaseListAdapter );
            }

            Button buttonClearQueue = ( Button ) findViewById( R.id.clear_queue_button );
            if ( buttonClearQueue != null ) {
                buttonClearQueue.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick( View v ) {
                        // TODO: 12/24/2016 Don't like try catch inside try catch, custom onClickListener?
                        try {
                            ClearCraftingQueue();
                            RefreshViews( true );
                        } catch ( Exception e ) {
                            SendErrorReport( e );
                        }
                    }
                } );
            }

            AdUtil.loadAdView( this );

            showChangeLog();

            if ( getIntent().getBooleanExtra( getString( R.string.intent_key_did_update ), false ) ) {
                ClearCraftingQueue();

                getIntent().putExtra( getString( R.string.intent_key_did_update ), false );
            }

            RefreshViews( true );
        } catch ( Exception e ) {
            SendErrorReport( e );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();

        SaveSettings();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch ( item.getItemId() ) {
            case R.id.action_settings:
                startActivityForResult(
                        new Intent( this, SettingsActivity.class ),
                        Helper.REQUEST_CODE_SETTINGS_ACTIVITY );
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
                new AlertDialog.Builder( this )
                        .setTitle( getString( R.string.about_dialog_full_title ) )
                        .setIcon( R.drawable.wood_signs_wooden_sign )
                        .setNegativeButton( getString( R.string.about_dialog_close_button ), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialog, int which ) {
                            }
                        } )
                        .setMessage(
                                "Passionately developed by Shane Stone.\n\n" +
                                        "Email:\n  jaredstone1982@gmail.com\n  stonedevs@gmail.com\n\n" +
                                        "Twitter:\n  @MasterxOfxNone\n  @ARKResourceCalc\n\n" +
                                        "Steam:\n  MasterxOfxNone\n" +
                                        "Xbox Live:\n  MasterxOfxNone\n\n" +
                                        getAppVersions() )
                        .show();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        try {
            switch ( requestCode ) {
                case Helper.REQUEST_CODE_DETAIL_ACTIVITY:
                    if ( resultCode == RESULT_OK ) {
                        Bundle extras = data.getExtras();
                        long id = extras.getLong( Helper.DETAIL_ID );
                        String result = extras.getString( Helper.RESULT_CODE_DETAIL_ACTIVITY );

                        assert result != null;
                        switch ( result ) {
                            case Helper.DETAIL_REMOVE:
                                if ( extras.getBoolean( Helper.DETAIL_REMOVE ) )
                                    craftableEngramListAdapter.Delete(
                                            getApplicationContext(),
                                            id );
                                break;
                            case Helper.DETAIL_SAVE:
                                craftableEngramListAdapter.setQuantity(
                                        getApplicationContext(),
                                        id,
                                        extras.getInt( Helper.DETAIL_QUANTITY ) );
                                break;
                        }

                        RefreshViews( true );
                    }
                    break;
                case Helper.REQUEST_CODE_SETTINGS_ACTIVITY:
                    if ( resultCode == RESULT_OK ) {
                        Bundle extras = data.getExtras();

                        boolean dlcValueChange = extras.getBoolean( getString( R.string.pref_dlc_key ) );
                        boolean categoryPrefChange = extras.getBoolean( getString( R.string.pref_filter_category_key ) );
                        boolean stationPrefChange = extras.getBoolean( getString( R.string.pref_filter_station_key ) );
                        boolean levelPrefChange = extras.getBoolean( getString( R.string.pref_filter_level_key ) );
                        boolean levelValueChange = extras.getBoolean( getString( R.string.pref_edit_text_level_key ) );
                        boolean refinedPrefChange = extras.getBoolean( getString( R.string.pref_filter_refined_key ) );

                        if ( dlcValueChange || categoryPrefChange || stationPrefChange || levelPrefChange || levelValueChange ) {
                            ResetSettings();
                            SaveSettings();

                            if ( dlcValueChange ) {
                                ClearCraftingQueue();
                            }
                        }

                        if ( refinedPrefChange )
                            RefreshCraftingQueueResources();

                        RefreshViews( true );
                    }
                    break;
            }
        } catch ( Exception e ) {
            SendErrorReport( e );
        }
    }

    private void ResetSettings() {
        displayCaseListAdapter.resetLevels();
    }

    public void SaveSettings() {
        PrefsUtil prefs = new PrefsUtil( getApplicationContext() );

        if ( prefs.getCategoryFilterPreference() ) {
            prefs.saveCategoryLevels(
                    displayCaseListAdapter.getLevel(),
                    displayCaseListAdapter.getParent()
            );
        }

        if ( prefs.getStationFilterPreference() ) {
            prefs.saveStationId( displayCaseListAdapter.getStationId() );
        }

        Log.d( TAG, "SaveSettings(): level: " + displayCaseListAdapter.getLevel() +
                ", parent: " + displayCaseListAdapter.getParent() +
                ", station: " + displayCaseListAdapter.getStationId()
        );
    }

    private void RefreshViews( boolean viewChanged ) {
        try {
            RefreshCraftingQueue();
            RefreshDisplayCase();

            if ( viewChanged ) {
                displayCaseEngramList.setLayoutManager( getAdjustedLayoutManager() );
                displayCaseEngramList.setLayoutParams( getDisplayCaseLayoutParams() );

                FrameLayout topContainer = ( FrameLayout ) findViewById( R.id.content_main_top_container );
                topContainer.setLayoutParams( getDisplayCaseContainerLayoutParams() );

                FrameLayout bottomContainer = ( FrameLayout ) findViewById( R.id.content_main_bottom_container );
                bottomContainer.setLayoutParams( getCraftingQueueContainerLayoutParams() );
            }
        } catch ( Exception e ) {
            SendErrorReport( e );
        }
    }

    private void RefreshDisplayCase() throws Exception {
        displayCaseListAdapter.refreshData();

        TextView categoryHierarchy = ( TextView ) findViewById( R.id.hierarchy_text );
        categoryHierarchy.setText( displayCaseListAdapter.getHierarchicalText() );
    }

    private void RefreshCraftingQueue() {
        craftableEngramListAdapter.NotifyDataChanged();
        craftableResourceListAdapter.NotifyDataChanged();
    }

    private void RefreshCraftingQueueResources() throws Exception {
        craftableResourceListAdapter.RefreshData();
        craftableResourceListAdapter.NotifyDataChanged();
    }

    public void ClearCraftingQueue() throws Exception {
        craftableEngramListAdapter.ClearQueue( getApplicationContext() );
    }

    private void showChangeLog() {
        ChangeLog changeLog = new ChangeLog( this );

        if ( changeLog.firstRun() ) {
            changeLog.getLogDialog().show();
        }
    }

    private RecyclerView.LayoutManager getAdjustedLayoutManager() {
        DisplayUtil displayUtil = new DisplayUtil( getApplicationContext(), getWindowManager().getDefaultDisplay() );

        int numCols = getResources().getInteger( R.integer.display_case_column_count );
        if ( displayUtil.isLandscape() ) {
            if ( craftableEngramListAdapter.getItemCount() > 0 ) {
                numCols /= 2;
            }
        }

        return new GridLayoutManager( this, numCols );
    }

    private LinearLayout.LayoutParams getCraftingQueueContainerLayoutParams() {
        DisplayUtil displayUtil = new DisplayUtil( getApplicationContext(), getWindowManager().getDefaultDisplay() );

        mCraftableEngramDimensions = displayUtil.getCraftingQueueGridViewHolderDimensions();

        if ( displayUtil.isPortrait() ) {
            if ( craftableEngramListAdapter.getItemCount() > 0 ) {
                return new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f );
            } else {
                return new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0 );
            }
        } else {
            if ( craftableEngramListAdapter.getItemCount() > 0 ) {
                return new LinearLayout.LayoutParams( 0, LinearLayout.LayoutParams.MATCH_PARENT, 1f );
            } else {
                return new LinearLayout.LayoutParams( 0, LinearLayout.LayoutParams.MATCH_PARENT );
            }
        }
    }

    private LinearLayout.LayoutParams getDisplayCaseContainerLayoutParams() {
        DisplayUtil displayUtil = new DisplayUtil( getApplicationContext(), getWindowManager().getDefaultDisplay() );

        if ( displayUtil.isPortrait() ) {
            if ( craftableEngramListAdapter.getItemCount() == 0 ) {
                return new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f );
            } else {
                return new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
            }
        } else {
            if ( craftableEngramListAdapter.getItemCount() == 0 ) {
                return new LinearLayout.LayoutParams( 0, LinearLayout.LayoutParams.MATCH_PARENT, 1f );
            } else {
                return new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT );
            }
        }
    }

    private LinearLayout.LayoutParams getDisplayCaseLayoutParams() {
        DisplayUtil displayUtil = new DisplayUtil( getApplicationContext(), getWindowManager().getDefaultDisplay() );

        mEngramDimensions = displayUtil.getDisplayCaseGridViewHolderDimensions();
        mCraftableEngramDimensions = displayUtil.getCraftingQueueGridViewHolderDimensions();

        if ( displayUtil.isPortrait() ) {
            if ( craftableEngramListAdapter.getItemCount() > 0 ) {
                int numRows = getResources().getInteger( R.integer.display_case_row_count );
                float padding = getResources().getDimension( R.dimen.display_case_view_holder_padding );

                // Padding * 2 for top and bottom or start and end
                float containerPadding = ( padding * 2 );

                return new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        ( int ) ( mEngramDimensions * numRows + containerPadding )
                );
            } else {
                return new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f );
            }
        } else {
            // landscape
            if ( craftableEngramListAdapter.getItemCount() > 0 ) {
                int numCols = getResources().getInteger( R.integer.display_case_column_count );

                return new LinearLayout.LayoutParams(
                        ( int ) ( mEngramDimensions * ( numCols / 2 ) ),
                        0,
                        1f
                );
            } else {
                return new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1f );
            }
        }
    }

    private String getAppVersions() {
        return "App Version: " + BuildConfig.VERSION_NAME + "/" + BuildConfig.VERSION_CODE + "\n" +
                "Database Version: " + DatabaseHelper.DATABASE_VERSION + "\n" +
                "JSON File Version: " + getString( R.string.json_version ) + "\n";
    }

    void SendErrorReport( Exception e ) {
        ExceptionUtil.SendErrorReportWithAlertDialog( MainActivity.this, TAG, e );
    }
}
