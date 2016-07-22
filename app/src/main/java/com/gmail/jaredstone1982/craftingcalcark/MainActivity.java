package com.gmail.jaredstone1982.craftingcalcark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gmail.jaredstone1982.craftingcalcark.adapters.CraftableEngramListAdapter;
import com.gmail.jaredstone1982.craftingcalcark.adapters.DisplayCaseListAdapter;
import com.gmail.jaredstone1982.craftingcalcark.adapters.ResourceListAdapter;
import com.gmail.jaredstone1982.craftingcalcark.helpers.Helper;
import com.gmail.jaredstone1982.craftingcalcark.model.CraftingQueue;
import com.gmail.jaredstone1982.craftingcalcark.model.listeners.RecyclerTouchListener;

public class MainActivity extends AppCompatActivity {
    private static final String LOGTAG = "MAINACTIVITY";

    private DisplayCaseListAdapter displayCaseListAdapter;
    private CraftableEngramListAdapter craftableEngramListAdapter;
    private ResourceListAdapter craftableResourceListAdapter;

    private CraftingQueue craftingQueue;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create progress activity if database needs to be upgraded or initiated.
        // FIXME: Does not show anything after progressBar dismisses.
//        final DataSource dataSource = DataSource.getInstance(this, LOGTAG);
//        final List<String> tablesForContent = dataSource.TestTablesForContent();
//        if (tablesForContent.size() > 0) {
//            final ProgressDialog progressDialog = ProgressDialog.show(this, "Initializing data..", "Initializing data..", true);
//            progressDialog.setCancelable(false);
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    dataSource.InitializeTablesWithContent(tablesForContent);
//                    progressDialog.dismiss();
//                }
//            }).start();
//        }


        final RecyclerView displayCaseEngramList = (RecyclerView) findViewById(R.id.content_displaycase);
        RecyclerView craftingQueueEngramList = (RecyclerView) findViewById(R.id.content_crafting_queue_engrams);
        RecyclerView craftingQueueResourceList = (RecyclerView) findViewById(R.id.content_crafting_queue_resources);

        craftingQueue = new CraftingQueue(this); // TODO: Move CraftingQueue in its ListAdapter, same as DisplayCase

        RecyclerView.LayoutManager displayCaseLayoutManager =
                new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        if (displayCaseEngramList != null) {
            RecyclerTouchListener displayCaseTouchListener = new RecyclerTouchListener(this, displayCaseEngramList,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            if (displayCaseListAdapter.isEngram(position)) {
                                craftingQueue.increaseQuantity(displayCaseListAdapter.getEngramId(position), 1);

                                displayCaseListAdapter.Refresh();
                                refreshDisplayForCraftingQueue();
                            } else {
                                // this is a category
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
                                // this is a category TODO CategoryDetailActivity? Pulls a list of engrams dedicated to that category?
                                displayCaseListAdapter.changeCategory(position);
                            }
                        }
                    });

            displayCaseListAdapter = new DisplayCaseListAdapter(this);

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
                            craftingQueue.increaseQuantity(craftableEngramListAdapter.getEngram(position).getId(), 1);

                            refreshDisplayForCraftingQueue();
                        }

                        @Override
                        public void onLongClick(View view, int position) {
                            Intent intent = new Intent(view.getContext(), DetailActivity.class);
                            intent.putExtra(Helper.DETAIL_ID, craftableEngramListAdapter.getEngram(position).getId());

                            startActivityForResult(intent, Helper.DETAIL_ID_CODE);
                        }
                    });

            craftableEngramListAdapter = new CraftableEngramListAdapter(craftingQueue.getEngrams());

            craftingQueueEngramList.setLayoutManager(craftableEngramLayoutManager);
            craftingQueueEngramList.addOnItemTouchListener(craftingQueueTouchListener);
            craftingQueueEngramList.setAdapter(craftableEngramListAdapter);
        }

        RecyclerView.LayoutManager craftableResourceLayoutManager =
                new LinearLayoutManager(this);
        if (craftingQueueResourceList != null) {
            craftableResourceListAdapter = new ResourceListAdapter(craftingQueue.getResources());

            craftingQueueResourceList.setLayoutManager(craftableResourceLayoutManager);
            craftingQueueResourceList.setAdapter(craftableResourceListAdapter);
        }

        createExtraViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_clearQueue:
                craftingQueue.Clear();
                displayCaseListAdapter.Refresh();
                refreshDisplayForCraftingQueue(); // Refreshing an empty object?
                break;

            case R.id.action_show_all:
                displayCaseListAdapter.setFiltered(false);
                break;

            case R.id.action_show_filtered:
                displayCaseListAdapter.setFiltered(true);
                break;

            case R.id.action_settings:
                return true;

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
                int quantity = extras.getInt(Helper.DETAIL_QUANTITY);

                craftingQueue.setQuantity(id, quantity);

                displayCaseListAdapter.Refresh();
                refreshDisplayForCraftingQueue();
            }
        }
    }

    private void refreshDisplayForCraftingQueue() {
        craftableEngramListAdapter.setEngrams(craftingQueue.getEngrams());
        craftableResourceListAdapter.setResources(craftingQueue.getResources());

        craftableEngramListAdapter.Refresh();
        craftableResourceListAdapter.Refresh();
    }

    private void createExtraViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
}
