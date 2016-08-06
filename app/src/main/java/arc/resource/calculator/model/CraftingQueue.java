package arc.resource.calculator.model;

import android.content.Context;
import android.util.SparseArray;

import arc.resource.calculator.db.DataSource;

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
public class CraftingQueue {
    private static final String LOGTAG = "CRAFTING";
    public static final int MAX = 100;

    private static CraftingQueue sInstance;

    private SparseArray<CraftableResource> resources = null;
    private SparseArray<CraftableEngram> engrams = null;

    private DataSource dataSource;

    private CraftingQueue(Context context) {
        dataSource = DataSource.getInstance(context, LOGTAG);

        UpdateData();
    }

    public static CraftingQueue getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CraftingQueue(context);
        }
        return sInstance;
    }

    /**
     * -- PUBLIC ENGRAM METHODS --
     */

    public int getEngramItemCount() {
        return engrams.size();
    }

    public int getEngramImageId(int position) {
        return engrams.valueAt(position).getImageId();
    }

    public String getEngramName(int position) {
        return engrams.valueAt(position).getName();
    }

    public int getEngramQuantity(int position) {
        return engrams.valueAt(position).getQuantity();
    }

    /**
     * -- PUBLIC RESOURCE METHODS --
     */

    public int getResourceItemCount() {
        return resources.size();
    }

    public int getResourceImageId(int position) {
        return resources.valueAt(position).getImageId();
    }

    public String getResourceName(int position) {
        return resources.valueAt(position).getName();
    }

    public int getResourceQuantity(int position) {
        return resources.valueAt(position).getQuantity();
    }

    /**
     * -- PUBLIC QUANTITY METHODS --
     */

    public void increaseQuantity(int position, int amount) {
        try {
            increaseQuantity(engrams.valueAt(position).getId(), amount);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Position got out of control somehow, no need to stop app.
        }
    }

    public void increaseQuantity(long engramId, int amount) {
        Queue queue = dataSource.findSingleQueue(engramId);

        // if queue is empty, add new queue into system
        // if queue exists, increase quantity by amount, update system with new object
        if (queue == null) {
            Insert(engramId, amount);
        } else {
            if (queue.getQuantity() < MAX) {
                queue.increaseQuantity(amount);
                Update(queue);
            }
        }
    }

    public void decreaseQuantity(int position, int amount) {
        decreaseQuantity(engrams.valueAt(position).getId(), amount);
    }

    public void decreaseQuantity(long engramId, int amount) {
        Queue queue = dataSource.findSingleQueue(engramId);

        // if queue is empty, add new queue into system
        // if queue exists, increase quantity by amount, update system with new object
        if (queue != null) {
            if (amount > 0) {
                queue.decreaseQuantity(amount);
                if (queue.getQuantity() > 0) {
                    Update(queue);
                } else {
                    Remove(queue);
                }
            }
        }
    }

    public void setQuantity(long engramId, int quantity) {
        Queue queue = dataSource.findSingleQueue(engramId);

        // if queue is empty and quantity is above 0, add new queue into database
        if (queue == null) {
            if (quantity > 0) {
                Insert(engramId, quantity);
            }
            return;
        }

        // if quantity is 0, remove queue from database
        if (quantity == 0) {
            Remove(queue);
            return;
        }

        // if quantities are not equal, update existing queue to database
        if (queue.getQuantity() != quantity && queue.getQuantity() <= MAX) {
            queue.setQuantity(quantity);

            Update(queue);
        }
    }

    /**
     * -- PUBLIC DATABASE QUERY METHODS --
     */

    public void Remove(long engramId) {
        Remove(dataSource.findSingleQueue(engramId));
    }

    public void Remove(Queue queue) {
        dataSource.Delete(queue);

        UpdateData();
    }

    public void Update(Queue queue) {
        dataSource.Update(queue);

        UpdateData();
    }

    public void Insert(long engramId, int quantity) {
        dataSource.Insert(engramId, quantity);

        UpdateData();
    }

    public void Clear() {
        dataSource.ClearQueue();

        UpdateData();
    }

    /**
     * -- PRIVATE UTILITY METHODS --
     */

    private SparseArray<CraftableEngram> getEngrams() {
        return dataSource.findAllCraftableEngrams();
    }

    private SparseArray<CraftableResource> getResources() {
        return dataSource.findAllCraftableResources();
    }

    private void UpdateData() {
        engrams = getEngrams();
        resources = getResources();
    }
}
