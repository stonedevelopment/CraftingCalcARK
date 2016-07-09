package com.gmail.jaredstone1982.craftingcalcark.model;

import android.content.Context;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.db.QueueDataSource;

import java.util.HashMap;

/**
 * Proposed idea for concealing the crafting queue and its objects
 */
public class CraftingQueue {
    private QueueDataSource dataSource;
    private SparseArray<CraftableEngram> engrams;
    private HashMap<Long, Queue> queues = new HashMap<>();

    public CraftingQueue(Context context) {
        dataSource = new QueueDataSource(context, "CRAFTING");
        dataSource.Open();

        this.engrams = getEngrams();
    }

    public SparseArray<CraftableEngram> getEngrams() {
        return dataSource.findAllEngrams();
    }

    public SparseArray<CraftableResource> getResources() {
        return dataSource.findAllResources();
    }

    public void increaseQuantity(long engramId, int amount) {
        Queue queue = queues.get(engramId);

        // if queue is empty, add new queue into system
        // if queue exists, increase quantity by amount, update system with new object
        if (queue == null) {
            queue = dataSource.Insert(engramId, amount);
        } else {
            queue.increaseQuantity(amount);
            dataSource.Update(queue);
        }

        queues.put(engramId, queue);
    }

    public void setQuantity(long engramId, int quantity) {
        Queue queue = queues.get(engramId);

        // if queue is empty and quantity is above 0, add new queue into system
        if (queue == null) {
            if (quantity > 0) {
                queue = dataSource.Insert(engramId, quantity);
                queues.put(engramId, queue);
            }
            return;
        }

        // if quantity is 0, remove queue from system
        if (quantity == 0) {
            dataSource.Delete(engramId);
            return;
        }

        // if quantities are not equal, update queue object, array, and database TODO: simplify?
        if (queue.getQuantity() != quantity) {
            queue.setQuantity(quantity);
            dataSource.Update(queue);
            queues.put(engramId, queue);
        }
    }

    public void Clear() {
        dataSource.DeleteTableData();
    }

    public CraftableEngram getEngram(int position) {
        return engrams.valueAt(position);
    }

    public long getId(int position) {
        return getEngrams().valueAt(position).getId();
    }
}
