package com.gmail.jaredstone1982.craftingcalcark.model;

import android.content.Context;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.db.DataSource;
import com.gmail.jaredstone1982.craftingcalcark.helpers.Helper;

/**
 * Proposed idea for concealing the crafting queue and its objects
 * Furthermore, CraftingQueue seems to be evolving into a database handler, be advised.
 */
public class CraftingQueue {
    private static final String LOGTAG = "CRAFTING";

    private static final int MAX = 100;

    private DataSource dataSource;

    public CraftingQueue(Context context) {
        dataSource = DataSource.getInstance(context, LOGTAG);
    }

    public SparseArray<CraftableEngram> getEngrams() {
        return dataSource.findAllCraftableEngrams();
    }

    public SparseArray<CraftableResource> getResources() {
        return dataSource.findAllCraftableResources();
    }

    public void Remove(long engramId) {
        Queue queue = dataSource.findSingleQueue(engramId);

        if (queue != null) {
            dataSource.Delete(queue);
        }
    }

    public void increaseQuantity(long engramId, int amount) {
        Queue queue = dataSource.findSingleQueue(engramId);

        // if queue is empty, add new queue into system
        // if queue exists, increase quantity by amount, update system with new object
        if (queue == null) {
            dataSource.Insert(engramId, amount);
        } else {
            if (queue.getQuantity() < MAX) {
                queue.increaseQuantity(amount);
                dataSource.Update(queue);
            }
        }
    }

    public void decreaseQuantity(long engramId, int amount) {
        Queue queue = dataSource.findSingleQueue(engramId);

        // if queue is empty, add new queue into system
        // if queue exists, increase quantity by amount, update system with new object
        if (queue != null) {
            if (amount > 0) {
                queue.decreaseQuantity(amount);
                if (queue.getQuantity() > 0) {
                    dataSource.Update(queue);
                } else {
                    dataSource.Delete(queue);
                }
            }
        }
    }

    public void setQuantity(long engramId, int quantity) {
        Queue queue = dataSource.findSingleQueue(engramId);

        // if queue is empty and quantity is above 0, add new queue into database
        if (queue == null) {
            if (quantity > 0) {
                dataSource.Insert(engramId, quantity);
            }
            return;
        }

        // if quantity is 0, remove queue from database
        if (quantity == 0) {
            Helper.Log(LOGTAG, "-- setQuantity() > Quantity is 0, deleting record of engramId: " + engramId);

            dataSource.Delete(queue);
            return;
        }

        // if quantities are not equal, update existing queue to database
        if (queue.getQuantity() != quantity && queue.getQuantity() <= MAX) {
            queue.setQuantity(quantity);
            dataSource.Update(queue);
        }
    }

    public void Clear() {
        dataSource.ClearQueue();
    }

    private void ResetConnection() {
        dataSource.ResetConnection();
    }
}
