package arc.resource.calculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import arc.resource.calculator.adapters.CraftableEngramListAdapter;
import arc.resource.calculator.adapters.CraftableResourceListAdapter;
import arc.resource.calculator.adapters.DisplayCaseListAdapter;
import arc.resource.calculator.db.DatabaseHelper;
import arc.resource.calculator.helpers.AutoFitRecyclerView;
import arc.resource.calculator.helpers.DisplayHelper;
import arc.resource.calculator.helpers.Helper;
import arc.resource.calculator.helpers.PreferenceHelper;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: A:RC, a resource calculator for ARK:Survival Evolved
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DisplayCaseListAdapter displayCaseListAdapter;
    private CraftableEngramListAdapter craftableEngramListAdapter;
    private CraftableResourceListAdapter craftableResourceListAdapter;

    private AutoFitRecyclerView displayCaseEngramList;
    private RecyclerView craftingQueueEngramList;
    private RecyclerView craftingQueueResourceList;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        DisplayHelper.createInstance( this, getWindowManager().getDefaultDisplay() );

        displayCaseEngramList = ( AutoFitRecyclerView ) findViewById( R.id.content_display_case_engrams );
        craftingQueueEngramList = ( RecyclerView ) findViewById( R.id.content_crafting_queue_engrams );
        craftingQueueResourceList = ( RecyclerView ) findViewById( R.id.content_crafting_queue_resources );

        if ( craftingQueueEngramList != null ) {
            RecyclerTouchListener craftingQueueEngramTouchListener = new RecyclerTouchListener( this, craftingQueueEngramList,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick( View view, int position ) {
                            if ( position >= 0 ) {
                                craftableEngramListAdapter.increaseQuantity( position, 1 );
                                RefreshViews();
                            }
                        }

                        @Override
                        public void onLongClick( View view, int position ) {
                            if ( position >= 0 ) {
                                craftableEngramListAdapter.decreaseQuantity( position, 1 );
                                RefreshViews();
                            }
                        }
                    } );

            craftableEngramListAdapter = new CraftableEngramListAdapter( this );

            RecyclerView.LayoutManager craftableEngramLayoutManager =
                    new GridLayoutManager( this, 1, GridLayoutManager.HORIZONTAL, false );

            craftingQueueEngramList.setLayoutManager( craftableEngramLayoutManager );
            craftingQueueEngramList.addOnItemTouchListener( craftingQueueEngramTouchListener );
            craftingQueueEngramList.setAdapter( craftableEngramListAdapter );
        }

        if ( craftingQueueResourceList != null ) {
            RecyclerTouchListener craftingQueueResourceTouchListener = new RecyclerTouchListener( this, craftingQueueResourceList,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick( View view, int position ) {
                        }

                        @Override
                        public void onLongClick( View view, int position ) {
                            // Check database for matching resource id
                            // If found, add to queue (with matching quantities), if already not in queue
                        }
                    } );

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
                            if ( position >= 0 ) {
                                if ( displayCaseListAdapter.isEngram( position ) ) {
                                    craftableEngramListAdapter.increaseQuantity( displayCaseListAdapter.getEngramId( position ), 1 );
                                    RefreshViews();
                                } else {
                                    displayCaseListAdapter.changeCategory( position );
                                }
                            }
                        }

                        @Override
                        public void onLongClick( View view, int position ) {
                            if ( position >= 0 ) {
                                if ( displayCaseListAdapter.isEngram( position ) ) {
                                    Intent intent = new Intent( view.getContext(), DetailActivity.class );
                                    intent.putExtra( Helper.DETAIL_ID, displayCaseListAdapter.getEngramId( position ) );

                                    startActivityForResult( intent, Helper.DETAIL_ID_CODE );
                                } else {
                                    displayCaseListAdapter.changeCategory( position );
                                }
                            }
                        }
                    } );


            displayCaseListAdapter = new DisplayCaseListAdapter( this );
            displayCaseEngramList.addOnItemTouchListener( displayCaseTouchListener );
            displayCaseEngramList.setAdapter( displayCaseListAdapter );
        }

        createExtraViews();
        RefreshViews();
    }

    @Override
    protected void onStop() {
        super.onStop();

        PreferenceHelper preferenceHelper = new PreferenceHelper( this );

        preferenceHelper.setPreference( Helper.APP_LEVEL, displayCaseListAdapter.getLevel() );
        preferenceHelper.setPreference( Helper.APP_PARENT, displayCaseListAdapter.getParent() );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu_main, menu );

        if ( displayCaseListAdapter.isFiltered() ) {
            menu.findItem( R.id.action_show_all ).setTitle( R.string.action_show_filtered );
        } else {
            menu.findItem( R.id.action_show_all ).setTitle( R.string.action_show_all );
        }

        if ( craftableResourceListAdapter.getBreakdownResources() ) {
            menu.findItem( R.id.action_breakdownResources ).setTitle( R.string.action_breakdown_resources_raw );
        } else {
            menu.findItem( R.id.action_breakdownResources ).setTitle( R.string.action_breakdown_resources_refined );
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch ( item.getItemId() ) {
            case R.id.action_clearQueue:
                craftableEngramListAdapter.ClearQueue();
                RefreshViews();
                break;

            case R.id.action_breakdownResources:
                craftableResourceListAdapter.setBreakdownResources( !craftableResourceListAdapter.getBreakdownResources() );

                if ( craftableResourceListAdapter.getBreakdownResources() ) {
                    item.setTitle( R.string.action_breakdown_resources_raw );
                } else {
                    item.setTitle( R.string.action_breakdown_resources_refined );
                }

                RefreshViews();
                break;

            case R.id.action_show_all:
                displayCaseListAdapter.setFiltered( !displayCaseListAdapter.isFiltered() );

                if ( displayCaseListAdapter.isFiltered() ) {
                    item.setTitle( R.string.action_show_filtered );
                } else {
                    item.setTitle( R.string.action_show_all );
                }

                RefreshViews();
                break;

            case R.id.action_show_changelog:
                ChangeLog changeLog = new ChangeLog( this );
                changeLog.getFullLogDialog().show();
                break;

            case R.id.action_show_helpdialog:
                HelpDialog helpDialog = new HelpDialog( this );
                helpDialog.getDialog().show();
                break;

            case R.id.action_about:
                AlertDialog.Builder builder = new AlertDialog.Builder( this );

                builder.setTitle( getResources().getString( R.string.action_about ) + " " + getResources().getString( R.string.app_name_full ) )
                        .setIcon( R.drawable.wood_signs_wooden_sign )
                        .setNegativeButton( "Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialog, int which ) {
                            }
                        } )
                        .setMessage(
                                "Developed by: Shane Stone/Stone Development\n" +
                                        "Email: jaredstone1982@gmail.com/stonedevs@gmail.com\n" +
                                        "Twitter: @MasterxOfxNone/@StoneDevs\n" +
                                        "Steam/Xbox Live: MasterxOfxNone\n\n" +
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

        if ( requestCode == Helper.DETAIL_ID_CODE ) {
            if ( resultCode == RESULT_OK ) {
                Bundle extras = data.getExtras();
                long id = extras.getLong( Helper.DETAIL_ID );
                String result = extras.getString( Helper.DETAIL_RESULT_CODE );

                assert result != null;
                switch ( result ) {
                    case Helper.DETAIL_REMOVE:
                        boolean doRemove = extras.getBoolean( Helper.DETAIL_REMOVE );
                        if ( doRemove ) {
                            craftableEngramListAdapter.Remove( id );
                        }
                        break;
                    case Helper.DETAIL_SAVE:
                        int quantity = extras.getInt( Helper.DETAIL_QUANTITY );

                        if ( quantity > 0 ) {
                            craftableEngramListAdapter.setQuantity( id, quantity );
                        } else {
                            craftableEngramListAdapter.Remove( id );
                        }
                        break;
                }

                RefreshViews();
            }
        }
    }

    private void RefreshViews() {
        craftableEngramListAdapter.Refresh();
        craftableResourceListAdapter.Refresh();
        displayCaseListAdapter.Refresh();

        displayCaseEngramList.setLayoutParams( getDisplayCaseLayoutParams() );
    }

    private void createExtraViews() {
        Toolbar toolbar = ( Toolbar ) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        showChangeLog();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        if (fab != null) {
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//            });
//        }
    }

    private void showChangeLog() {
        ChangeLog changeLog = new ChangeLog( this );

        if ( changeLog.firstRun() ) {
            changeLog.getLogDialog().show();
        }
    }

    private LinearLayout.LayoutParams getDisplayCaseLayoutParams() {
        if ( craftableEngramListAdapter.getItemCount() > 0 ) {
            if ( getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ) {
                return new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.75f );
            } else {
                return new LinearLayout.LayoutParams( 0, LinearLayout.LayoutParams.MATCH_PARENT, 1f );
            }
        }

        return new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT );
    }

    private int getDisplayCaseHeightRecommendations() {
        if ( craftableEngramListAdapter.getItemCount() > 0 ) {
            if ( getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ) {
                int thumbnailSize = displayCaseEngramList.getColumnWidth();
                int rowCount = getResources().getInteger( R.integer.grid_layout_row_count );

                if ( rowCount > 0 && thumbnailSize > 0 ) {
                    return thumbnailSize * rowCount;
                }
            }
        }

        return GridLayoutManager.LayoutParams.MATCH_PARENT;
    }

    private int getDisplayCaseWidthRecommendations() {
        if ( craftableEngramListAdapter.getItemCount() > 0 ) {
            if ( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
                int thumbnailSize = displayCaseEngramList.getColumnWidth();
                int colCount = getResources().getInteger( R.integer.grid_layout_column_count );

                if ( colCount > 0 && thumbnailSize > 0 ) {
                    return thumbnailSize * colCount;
                }
            }
        }

        return GridLayoutManager.LayoutParams.MATCH_PARENT;
    }

    private float getGridViewThumbnailSizeRecommendations() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        float density = displayMetrics.density;

        float padding = getResources().getDimension( R.dimen.engram_grid_view_holder_padding );
        float dpPadding = padding / density;

        float dpHeight = displayMetrics.heightPixels / density;
        float dpWidth = displayMetrics.widthPixels / density;

        return dpWidth / getGridViewSpanCountRecommendations();
    }

    private int getGridViewSpanCountRecommendations() {
        if ( craftableEngramListAdapter.getItemCount() > 0 ) {
            return getResources().getInteger( R.integer.grid_layout_column_count_with_queue );
        }

        return getResources().getInteger( R.integer.grid_layout_column_count );
    }

    private String getAppVersions() {
        return "App Version: " + BuildConfig.VERSION_NAME + "/" + BuildConfig.VERSION_CODE + "\n" +
                "Database Version: " + DatabaseHelper.DATABASE_VERSION + "\n" +
                "JSON File Version: " + getString( R.string.json_version ) + "\n";
    }
}
