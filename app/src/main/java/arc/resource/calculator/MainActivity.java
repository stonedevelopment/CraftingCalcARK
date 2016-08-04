package arc.resource.calculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import arc.resource.calculator.adapters.CraftableEngramListAdapter;
import arc.resource.calculator.adapters.CraftableResourceListAdapter;
import arc.resource.calculator.adapters.DisplayCaseListAdapter;
import arc.resource.calculator.db.DBOpenHelper;
import arc.resource.calculator.helpers.DisplayHelper;
import arc.resource.calculator.helpers.Helper;
import arc.resource.calculator.model.initializers.CategoryInitializer;
import arc.resource.calculator.model.initializers.EngramInitializer;
import arc.resource.calculator.model.initializers.ResourceInitializer;
import arc.resource.calculator.model.listeners.RecyclerTouchListener;

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
    private static final String LOGTAG = "MAINACTIVITY";

    private DisplayCaseListAdapter displayCaseListAdapter;
    private CraftableEngramListAdapter craftableEngramListAdapter;
    private CraftableResourceListAdapter craftableResourceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        DisplayHelper.createInstance(this, displayMetrics);

        final RecyclerView displayCaseEngramList = (RecyclerView) findViewById(R.id.content_displaycase);
        final RecyclerView craftingQueueEngramList = (RecyclerView) findViewById(R.id.content_crafting_queue_engrams);
        RecyclerView craftingQueueResourceList = (RecyclerView) findViewById(R.id.content_crafting_queue_resources);

        RecyclerView.LayoutManager displayCaseLayoutManager =
                new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);

        if (displayCaseEngramList != null) {
            RecyclerTouchListener displayCaseTouchListener = new RecyclerTouchListener(this, displayCaseEngramList,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            if (displayCaseListAdapter.isEngram(position)) {
                                craftableEngramListAdapter.increaseQuantity(displayCaseListAdapter.getEngramId(position), 1);

                                Refresh();
                            } else {
                                displayCaseListAdapter.changeCategory(position);
                            }
                        }

                        @Override
                        public void onLongClick(View view, int position) {
                            if (displayCaseListAdapter.isEngram(position)) {
                                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                                intent.putExtra(Helper.DETAIL_ID, displayCaseListAdapter.getEngramId(position));

                                startActivityForResult(intent, Helper.DETAIL_ID_CODE);
                            } else {
                                displayCaseListAdapter.changeCategory(position);
                            }
                        }
                    });

            displayCaseListAdapter = new DisplayCaseListAdapter(this);

            // Adjust the height of the Display Case to fit 3 rows of content in view.
            displayCaseEngramList.getLayoutParams().height = (int) (DisplayHelper.getInstance().getEngramDimensionsWithDensity() * 3);

            displayCaseEngramList.setLayoutManager(displayCaseLayoutManager);
            displayCaseEngramList.addOnItemTouchListener(displayCaseTouchListener);
            displayCaseEngramList.setAdapter(displayCaseListAdapter);
        }

        RecyclerView.LayoutManager craftableEngramLayoutManager =
                new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        if (craftingQueueEngramList != null) {
            RecyclerTouchListener craftingQueueTouchListener = new RecyclerTouchListener(this, craftingQueueEngramList,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            craftableEngramListAdapter.increaseQuantity(position, 1);

                            Refresh();
                        }

                        @Override
                        public void onLongClick(View view, int position) {
                            craftableEngramListAdapter.decreaseQuantity(position, 1);

                            Refresh();
                        }
                    });

            craftableEngramListAdapter = new CraftableEngramListAdapter(this);

            craftingQueueEngramList.setLayoutManager(craftableEngramLayoutManager);
            craftingQueueEngramList.addOnItemTouchListener(craftingQueueTouchListener);
            craftingQueueEngramList.setAdapter(craftableEngramListAdapter);
        }

        RecyclerView.LayoutManager craftableResourceLayoutManager =
                new LinearLayoutManager(this);
        if (craftingQueueResourceList != null) {
            craftableResourceListAdapter = new CraftableResourceListAdapter(this);

            craftingQueueResourceList.setLayoutManager(craftableResourceLayoutManager);
            craftingQueueResourceList.setAdapter(craftableResourceListAdapter);
        }

        createExtraViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clearQueue:
                craftableEngramListAdapter.ClearQueue();

                Refresh();
                break;

            case R.id.action_show_all:
                if (item.isChecked()) { //  Turning off Show All, filtering results.
                    item.setTitle(R.string.action_show_all);
                    item.setChecked(false);
                    displayCaseListAdapter.setFiltered(true);
                } else {
                    item.setTitle(R.string.action_show_filtered);
                    item.setChecked(true);
                    displayCaseListAdapter.setFiltered(false);
                }

                Refresh();
                break;

            case R.id.action_show_changelog:
                ChangeLog changeLog = new ChangeLog(this);
                changeLog.getFullLogDialog().show();
                break;

            case R.id.action_show_helpdialog:
                HelpDialog helpDialog = new HelpDialog(this);
                helpDialog.getDialog().show();
                break;

            case R.id.action_view_debug_info:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle(getResources().getString(R.string.view_debug_info))
                        .setIcon(R.drawable.wood_signs_wooden_sign)
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setMessage(
                                "App Version: " + BuildConfig.VERSION_NAME + "/" + BuildConfig.VERSION_CODE + "\n\n" +
                                        "Database Version: " + DBOpenHelper.getDatabaseVersion() + "\n\n" +
                                        "Engram Version: " + EngramInitializer.VERSION + "\n" +
                                        "Resource Version: " + ResourceInitializer.VERSION + "\n" +
                                        "Category Version: " + CategoryInitializer.VERSION)
                        .show();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Helper.DETAIL_ID_CODE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                long id = extras.getLong(Helper.DETAIL_ID);
                String result = extras.getString(Helper.DETAIL_RESULT_CODE);

                assert result != null;
                switch (result) {
                    case Helper.DETAIL_REMOVE:
                        boolean doRemove = extras.getBoolean(Helper.DETAIL_REMOVE);
                        if (doRemove) {
                            craftableEngramListAdapter.Remove(id);
                        }
                        break;
                    case Helper.DETAIL_SAVE:
                        int quantity = extras.getInt(Helper.DETAIL_QUANTITY);

                        if (quantity > 0) {
                            craftableEngramListAdapter.setQuantity(id, quantity);
                        }
                        break;
                }

                Refresh();
            }
        }
    }

    private void Refresh() {
        craftableEngramListAdapter.Refresh();
        craftableResourceListAdapter.Refresh();
        displayCaseListAdapter.Refresh();
    }

    private void createExtraViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        ChangeLog changeLog = new ChangeLog(this);

        if (changeLog.firstRun()) {
            changeLog.getLogDialog().show();
        }
    }
}
