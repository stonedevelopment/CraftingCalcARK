package arc.resource.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

//    private DisplayCaseListAdapter displayCaseListAdapter;
//    private CraftableEngramListAdapter craftableEngramListAdapter;
//    private CraftableResourceListAdapter craftableResourceListAdapter;
//
//    private RecyclerView displayCaseEngramList;
//    private RecyclerView craftingQueueEngramList;
//    private RecyclerView craftingQueueResourceList;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
//        setContentView(R.layout.activity_main);

//        Display display = getWindowManager().getDefaultDisplay();
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        display.getMetrics(displayMetrics);
//
//        DisplayHelper.createInstance(this, display);
//
//        displayCaseEngramList = (RecyclerView) findViewById(R.id.content_displaycase);
//        craftingQueueEngramList = (RecyclerView) findViewById(R.id.content_crafting_queue_engrams);
//        craftingQueueResourceList = (RecyclerView) findViewById(R.id.content_crafting_queue_resources);
//
//        RecyclerView.LayoutManager craftableEngramLayoutManager =
//                new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
//        if (craftingQueueEngramList != null) {
//            RecyclerTouchListener craftingQueueEngramTouchListener = new RecyclerTouchListener(this, craftingQueueEngramList,
//                    new RecyclerTouchListener.ClickListener() {
//                        @Override
//                        public void onClick(View view, int position) {
//                            craftableEngramListAdapter.increaseQuantity(position, 1);
//                            RefreshViews();
//                        }
//
//                        @Override
//                        public void onLongClick(View view, int position) {
//                            craftableEngramListAdapter.decreaseQuantity(position, 1);
//                            RefreshViews();
//                        }
//                    });
//
//            craftableEngramListAdapter = new CraftableEngramListAdapter(this);
//
//            craftingQueueEngramList.setLayoutManager(craftableEngramLayoutManager);
//            craftingQueueEngramList.addOnItemTouchListener(craftingQueueEngramTouchListener);
//            craftingQueueEngramList.setAdapter(craftableEngramListAdapter);
//        }
//
//        RecyclerView.LayoutManager craftableResourceLayoutManager =
//                new LinearLayoutManager(this);
//        if (craftingQueueResourceList != null) {
//            RecyclerTouchListener craftingQueueResourceTouchListener = new RecyclerTouchListener(this, craftingQueueResourceList,
//                    new RecyclerTouchListener.ClickListener() {
//                        @Override
//                        public void onClick(View view, int position) {
//                            // Check database for matching resource id
//                            // If found, add to queue (with matching quantities), if already not in queue
//                        }
//
//                        @Override
//                        public void onLongClick(View view, int position) {
//                        }
//                    });
//
//            craftableResourceListAdapter = new CraftableResourceListAdapter(this);
//
//            craftingQueueResourceList.setLayoutManager(craftableResourceLayoutManager);
//            craftingQueueResourceList.setAdapter(craftableResourceListAdapter);
//        }
//
//        if (displayCaseEngramList != null) {
//            RecyclerTouchListener displayCaseTouchListener = new RecyclerTouchListener(this, displayCaseEngramList,
//                    new RecyclerTouchListener.ClickListener() {
//                        @Override
//                        public void onClick(View view, int position) {
//                            if (displayCaseListAdapter.isEngram(position)) {
//                                craftableEngramListAdapter.increaseQuantity(displayCaseListAdapter.getEngramId(position), 1);
//                                RefreshViews();
//                            } else {
//                                displayCaseListAdapter.changeCategory(position);
//                            }
//                        }
//
//                        @Override
//                        public void onLongClick(View view, int position) {
//                            if (displayCaseListAdapter.isEngram(position)) {
//                                Intent intent = new Intent(view.getContext(), DetailActivity.class);
//                                intent.putExtra(Helper.DETAIL_ID, displayCaseListAdapter.getEngramId(position));
//
//                                startActivityForResult(intent, Helper.DETAIL_ID_CODE);
//                            } else {
//                                displayCaseListAdapter.changeCategory(position);
//                            }
//                        }
//                    });
//
//            displayCaseListAdapter = new DisplayCaseListAdapter(this);
//            RecyclerView.LayoutManager displayCaseLayoutManager;
//
//            switch (display.getRotation()) {
//                case Surface.ROTATION_0:
//                case Surface.ROTATION_180:
//                    displayCaseLayoutManager = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
//                    break;
//                case Surface.ROTATION_270:
//                case Surface.ROTATION_90:
//                default:
//                    displayCaseLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
//                    break;
//            }
//
//            // Adjust the height of the Display Case to fit 3 rows of content in portrait view if crafting queue has items, if not, match parent's height
//            displayCaseEngramList.getLayoutParams().height = getDisplayCaseHeightRecommendations();
//            displayCaseEngramList.addOnItemTouchListener(displayCaseTouchListener);
//            displayCaseEngramList.setLayoutManager(displayCaseLayoutManager);
//            displayCaseEngramList.setAdapter(displayCaseListAdapter);
//        }

//        createExtraViews();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
////        PreferenceHelper.getInstance(this).setPreference(Helper.APP_LEVEL, displayCaseListAdapter.getLevel());
////        PreferenceHelper.getInstance(this).setPreference(Helper.APP_PARENT, displayCaseListAdapter.getParent());
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
////        if (displayCaseListAdapter.isFiltered()) {
////            menu.findItem(R.id.action_show_all).setTitle(R.string.action_show_filtered);
////        }
////
////        if (craftableResourceListAdapter.getBreakdownResources()) {
////            menu.findItem(R.id.action_breakdownResources).setTitle(R.string.action_breakdown_resources_raw);
////        }
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
////            case R.id.action_clearQueue:
////                craftableEngramListAdapter.ClearQueue();
////                RefreshViews();
////                break;
////
////            case R.id.action_breakdownResources:
////                craftableResourceListAdapter.setBreakdownResources(!craftableResourceListAdapter.getBreakdownResources());
////
////                if (craftableResourceListAdapter.getBreakdownResources()) {
////                    item.setTitle(R.string.action_breakdown_resources_raw);
////                } else {
////                    item.setTitle(R.string.action_breakdown_resources_refined);
////                }
////
////                RefreshViews();
////                break;
////
////            case R.id.action_show_all:
////                displayCaseListAdapter.setFiltered(!displayCaseListAdapter.isFiltered());
////
////                if (displayCaseListAdapter.isFiltered()) {
////                    item.setTitle(R.string.action_show_filtered);
////                } else {
////                    item.setTitle(R.string.action_show_all);
////                }
////
////                RefreshViews();
////                break;
////
////            case R.id.action_show_changelog:
////                ChangeLog changeLog = new ChangeLog(this);
////                changeLog.getFullLogDialog().show();
////                break;
////
////            case R.id.action_show_helpdialog:
////                HelpDialog helpDialog = new HelpDialog(this);
////                helpDialog.getDialog().show();
////                break;
////
////            case R.id.action_about:
////                AlertDialog.Builder builder = new AlertDialog.Builder(this);
////
////                builder.setTitle(getResources().getString(R.string.action_about) + " " + getResources().getString(R.string.app_name_full))
////                        .setIcon(R.drawable.wood_signs_wooden_sign)
////                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                            }
////                        })
////                        .setMessage(
////                                "Developed by: Shane Stone/Stone Development\n" +
////                                        "Email: jaredstone1982@gmail.com/stonedevs@gmail.com\n" +
////                                        "Twitter: @MasterxOfxNone/@StoneDevs\n" +
////                                        "Steam/Xbox Live: MasterxOfxNone\n\n" +
////                                        getAppVersions())
////                        .show();
////                break;
//
//            default:
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////
////        if (requestCode == Helper.DETAIL_ID_CODE) {
////            if (resultCode == RESULT_OK) {
////                Bundle extras = data.getExtras();
////                long id = extras.getLong(Helper.DETAIL_ID);
////                String result = extras.getString(Helper.DETAIL_RESULT_CODE);
////
////                assert result != null;
////                switch (result) {
////                    case Helper.DETAIL_REMOVE:
////                        boolean doRemove = extras.getBoolean(Helper.DETAIL_REMOVE);
////                        if (doRemove) {
////                            craftableEngramListAdapter.Remove(id);
////                        }
////                        break;
////                    case Helper.DETAIL_SAVE:
////                        int quantity = extras.getInt(Helper.DETAIL_QUANTITY);
////
////                        if (quantity > 0) {
////                            craftableEngramListAdapter.setQuantity(id, quantity);
////                        } else {
////                            craftableEngramListAdapter.Remove(id);
////                        }
////                        break;
////                }
////
////                RefreshViews();
////            }
////        }
//    }
//
//    private void RefreshViews() {
////        craftableEngramListAdapter.Refresh();
////        craftableResourceListAdapter.Refresh();
////        displayCaseListAdapter.Refresh();
////
////        displayCaseEngramList.getLayoutParams().height = getDisplayCaseHeightRecommendations();
//    }
//
//    private void createExtraViews() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        showChangeLog();
//
////        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
////        if (fab != null) {
////            fab.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                            .setAction("Action", null).show();
////                }
////            });
////        }
//    }
//
//    private void showChangeLog() {
//        ChangeLog changeLog = new ChangeLog(this);
//
//        if (changeLog.firstRun()) {
//            changeLog.getLogDialog().show();
//        }
//    }
//
////    private int getDisplayCaseHeightRecommendations() {
//////        if (craftableEngramListAdapter.getItemCount() > 0) {
//////            return (int) (DisplayHelper.getInstance().getEngramDimensionsWithDensity() * 3);
//////        } else {
//////            return GridLayoutManager.LayoutParams.MATCH_PARENT;
//////        }
////    }
//////
////    private int getDisplayCaseWidthRecommendations() {
//////        if (craftableEngramListAdapter.getItemCount() > 0) {
//////            return (int) (DisplayHelper.getInstance().getEngramDimensionsWithDensity() * 3);
//////        } else {
//////            return GridLayoutManager.LayoutParams.MATCH_PARENT;
//////        }
////    }
//
//    private String getAppVersions() {
//        return "App Version: " + BuildConfig.VERSION_NAME + "/" + BuildConfig.VERSION_CODE + "\n" +
//                "Database Version: " + DatabaseHelper.DATABASE_VERSION + "\n" +
//                "Engram Version: " + EngramInitializer.VERSION + " (" + EngramInitializer.getCount() + " Engrams)\n" +
//                "Resource Version: " + ResourceInitializer.VERSION + " (" + ResourceInitializer.getCount() + " Resources)\n" +
//                "Category Version: " + CategoryInitializer.VERSION + " (" + CategoryInitializer.getCount() + " Categories)";
//    }
}
