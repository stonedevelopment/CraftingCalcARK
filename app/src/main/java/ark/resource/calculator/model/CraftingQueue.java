package ark.resource.calculator.model;

import android.content.Context;
import android.util.SparseArray;

import ark.resource.calculator.db.DataSource;
import ark.resource.calculator.helpers.Helper;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: ARK:Resource Calculator
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
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