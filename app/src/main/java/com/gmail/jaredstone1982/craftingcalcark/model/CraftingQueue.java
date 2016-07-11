package com.gmail.jaredstone1982.craftingcalcark.model;

import android.content.Context;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.db.QueueDataSource;
import com.gmail.jaredstone1982.craftingcalcark.helpers.Helper;

import java.util.HashMap;

/**
 * Proposed idea for concealing the crafting queue and its objects
 * Furthermore, CraftingQueue seems to be evolving into a database handler, be advised.
 * TODO Will be organizing QueueDataSource and the other DataSource classes to inherit from a base class.
 */
public class CraftingQueue {
    private static final String LOGTAG = "CRAFTING";

    private QueueDataSource dataSource;

    public CraftingQueue(Context context) {
        dataSource = new QueueDataSource(context, LOGTAG);
        dataSource.Open();
    }

    private HashMap<Long, Queue> getQueues() {
        return dataSource.findAllQueues();
    }

    public SparseArray<CraftableEngram> getEngrams() {
        return dataSource.findAllEngrams();
    }

    public SparseArray<CraftableResource> getResources() {
        return dataSource.findAllResources();
    }

    public void increaseQuantity(long engramId, int amount) {
        HashMap<Long, Queue> queues = getQueues();
        Queue queue = queues.get(engramId);

        // if queue is empty, add new queue into system
        // if queue exists, increase quantity by amount, update system with new object
        if (queue == null) {
            dataSource.Insert(engramId, amount);
        } else {
            queue.increaseQuantity(amount);
            dataSource.Update(queue);
        }
    }

    public void setQuantity(long engramId, int quantity) {
        HashMap<Long, Queue> queues = getQueues();
        Queue queue = queues.get(engramId);

        // if queue is empty and quantity is above 0, add new queue into database
        if (queue == null) {
            if (quantity > 0) {
                dataSource.Insert(engramId, quantity);
            }
            return;
        }

        // if quantity is 0, remove queue from database
        if (quantity == 0) {
            dataSource.Delete(engramId);

            Helper.Log(LOGTAG, "-- setQuantity() > Quantity is 0, deleting record of engramId: " + engramId);
            return;
        }

        // if quantities are not equal, update existing queue to database
        if (queue.getQuantity() != quantity) {
            queue.setQuantity(quantity);
            dataSource.Update(queue);
        }
    }

    public void Clear() {
        Helper.Log(LOGTAG, "** Clearing Crafting Queue..");

        dataSource.DeleteTableData();
        dataSource.DropTable();
        dataSource.CreateTable();

        Helper.Log(LOGTAG, "** Crafting Queue cleared.");
    }
}
