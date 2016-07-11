package com.gmail.jaredstone1982.craftingcalcark;

import android.content.Context;
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
import com.gmail.jaredstone1982.craftingcalcark.adapters.EngramListAdapter;
import com.gmail.jaredstone1982.craftingcalcark.adapters.ResourceListAdapter;
import com.gmail.jaredstone1982.craftingcalcark.helpers.Helper;
import com.gmail.jaredstone1982.craftingcalcark.model.CraftingQueue;
import com.gmail.jaredstone1982.craftingcalcark.model.DisplayCase;
import com.gmail.jaredstone1982.craftingcalcark.model.listeners.RecyclerTouchListener;

public class MainActivity extends AppCompatActivity {
    private static final String LOGTAG = "MAINACTIVITY";

    private RecyclerView displayCaseEngramList;
    private RecyclerView craftingQueueEngramList;
    private RecyclerView craftingQueueResourceList;

    // TODO: 7/10/2016 Rework how the ListAdapters are named to better reflect their jobs
    private EngramListAdapter mEngramAdapter;
    private CraftableEngramListAdapter engramListAdapter;
    private ResourceListAdapter resourceListAdapter;

    private DisplayCase displayCase;
    private CraftingQueue craftingQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayCaseEngramList = (RecyclerView) findViewById(R.id.content_engrams);
        craftingQueueEngramList = (RecyclerView) findViewById(R.id.content_crafting_queue_engrams);
        craftingQueueResourceList = (RecyclerView) findViewById(R.id.content_crafting_queue_resources);

        displayCase = new DisplayCase(this);
        craftingQueue = new CraftingQueue(this);

        RecyclerView.LayoutManager mLayoutManager_EngramList =
                new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        if (displayCaseEngramList != null) {
            mEngramAdapter = new EngramListAdapter(displayCase.getEngrams());

            displayCaseEngramList.setLayoutManager(mLayoutManager_EngramList);
            displayCaseEngramList.addOnItemTouchListener(createRecyclerTouchListener(this, displayCaseEngramList));
            displayCaseEngramList.setAdapter(mEngramAdapter);
        }

        RecyclerView.LayoutManager mLayoutManager_CraftingQueueEngramList =
                new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        if (craftingQueueEngramList != null) {
            engramListAdapter = new CraftableEngramListAdapter(craftingQueue.getEngrams());

            craftingQueueEngramList.setLayoutManager(mLayoutManager_CraftingQueueEngramList);
            craftingQueueEngramList.addOnItemTouchListener(createRecyclerTouchListener(this, craftingQueueEngramList));
            craftingQueueEngramList.setAdapter(engramListAdapter);
        }

        RecyclerView.LayoutManager mLayoutManager_CraftingQueueResourceList =
                new LinearLayoutManager(this);
        if (craftingQueueResourceList != null) {
            resourceListAdapter = new ResourceListAdapter(craftingQueue.getResources());

            craftingQueueResourceList.setLayoutManager(mLayoutManager_CraftingQueueResourceList);
            craftingQueueResourceList.setAdapter(resourceListAdapter);
        }

        createExtraViews();
    }

    private RecyclerTouchListener createRecyclerTouchListener(final Context context, final RecyclerView recyclerView) {
        return new RecyclerTouchListener(this, recyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        if (recyclerView.equals(displayCaseEngramList)) {
                            craftingQueue.increaseQuantity(mEngramAdapter.getEngram(position).getId(), 1);
                        } else {
                            craftingQueue.increaseQuantity(engramListAdapter.getEngram(position).getId(), 1);
                        }

                        refreshDisplayForCraftingQueue();
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        // Call detail activity for [engram]
                        // Adjust quantities in new activity
                        // Save quantities to database
                        // When finished, a call to refreshlists should add them all back
                        Intent intent = new Intent(context, DetailActivity.class);
                        if (recyclerView.equals(displayCaseEngramList)) {
                            intent.putExtra(Helper.DETAIL_ID, mEngramAdapter.getEngram(position).getId());
                        } else {
                            intent.putExtra(Helper.DETAIL_ID, engramListAdapter.getEngram(position).getId());
                        }

                        startActivityForResult(intent, Helper.DETAIL_ID_CODE);
                    }
                });
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
                refreshDisplayForCraftingQueue(); // Refreshing an empty object?
                break;

            case R.id.action_ascending:
                break;

            case R.id.action_descending:
                break;

            case R.id.action_show_all:
                displayCase.getEngrams();
                refreshDisplayForDisplayCase();
                break;

            case R.id.action_show_filtered:
                // TODO: 6/15/2016 Filtered = Organized into folders
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
                refreshDisplayForCraftingQueue();
            }
        }
    }

    private void refreshDisplayForDisplayCase() {
        mEngramAdapter.setEngrams(displayCase.getEngrams());
        mEngramAdapter.Refresh();
    }

    private void refreshDisplayForCraftingQueue() {
        engramListAdapter.setEngrams(craftingQueue.getEngrams());
        resourceListAdapter.setResources(craftingQueue.getResources());

        engramListAdapter.Refresh();
        resourceListAdapter.Refresh();
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
