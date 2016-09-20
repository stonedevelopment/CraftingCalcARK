package arc.resource.calculator.model;

import android.content.Context;
import android.util.SparseArray;

import arc.resource.calculator.db.DataSource;
import arc.resource.calculator.helpers.PreferenceHelper;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.model.resource.QueueResource;

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
    public static final String STRING_KEY_CRAFTING_QUEUE_HASCOMPLEXRESOURCES = "CRAFTING_QUEUE_HASCOMPLEXRESOURCES";

    private static CraftingQueue sInstance;

    private boolean hasComplexResources;

    private SparseArray<QueueResource> resources = null;
    private SparseArray<QueueEngram> engrams = null;

    private DataSource dataSource;

    private Context context;

    private CraftingQueue(Context context) {
        this.context = context;
//        this.dataSource = DataSource.getInstance(context, LOGTAG);
        this.hasComplexResources = PreferenceHelper.getInstance(context).getBooleanPreference(STRING_KEY_CRAFTING_QUEUE_HASCOMPLEXRESOURCES, false);

        UpdateData();
    }

    public static CraftingQueue getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CraftingQueue(context);
        }
        return sInstance;
    }

    // -- PUBLIC GETTER METHODS --

    public boolean hasComplexResources() {
        return hasComplexResources;
    }

    public Context getContext() {
        return context;
    }

    // -- PUBLIC SETTER METHODS --

    public void setHasComplexResources(boolean b) {
        if (hasComplexResources != b) {
            hasComplexResources = b;

            PreferenceHelper.getInstance(getContext()).setPreference(STRING_KEY_CRAFTING_QUEUE_HASCOMPLEXRESOURCES, b);

            UpdateData();
        }
    }

    // -- PUBLIC ENGRAM METHODS --

    public int getEngramItemCount() {
        return engrams.size();
    }

    public int getEngramImageId(int position) {
//        return engrams.valueAt(position).getImageId();
        return 0;
    }

    public String getEngramName(int position) {
        return engrams.valueAt(position).getName();
    }

    public int getEngramQuantity(int position) {
        return engrams.valueAt(position).getQuantity();
    }

    // -- PUBLIC RESOURCE METHODS --

    public int getResourceItemCount() {
        return resources.size();
    }

    public int getResourceImageId(int position) {
//        return resources.valueAt(position).getDrawable();
        return 0;
    }

    public String getResourceName(int position) {
        return resources.valueAt(position).getName();
    }

    public int getResourceQuantity(int position) {
        return resources.valueAt(position).getQuantity();
    }

    // -- PUBLIC QUANTITY METHODS --

    public void increaseQuantity(int position, int amount) {
        increaseQuantity( engrams.valueAt( position ).getId(), amount );
    }

    public void increaseQuantity(long engramId, int amount) {
//        Queue queue = dataSource.findSingleQueue(engramId);
//
//        // if queue is empty, add new queue into system
//        // if queue exists, increase quantity by amount, update system with new object
//        if (queue == null) {
//            Insert(engramId, amount);
//        } else {
//            if (queue.getQuantity() < MAX) {
//                queue.increaseQuantity(amount);
//                Update(queue);
//            }
//        }
    }

    public void decreaseQuantity(int position, int amount) {
        if ( position <= engrams.size() ) {
            decreaseQuantity( engrams.valueAt( position ).getId(), amount );
        }
    }

    public void decreaseQuantity(long engramId, int amount) {
//        Queue queue = dataSource.findSingleQueue(engramId);
//
//        // if queue is empty, add new queue into system
//        // if queue exists, increase quantity by amount, update system with new object
//        if (queue != null) {
//            if (amount > 0) {
//                queue.decreaseQuantity(amount);
//                if (queue.getQuantity() > 0) {
//                    Update(queue);
//                } else {
//                    Remove(queue);
//                }
//            }
//        }
    }

    public void setQuantity(long engramId, int quantity) {
//        Queue queue = dataSource.findSingleQueue(engramId);
//
//        // if queue is empty and quantity is above 0, add new queue into database
//        if (queue == null) {
//            if (quantity > 0) {
//                Insert(engramId, quantity);
//            }
//            return;
//        }
//
//        // if quantity is 0, remove queue from database
//        if (quantity == 0) {
//            Remove(queue);
//            return;
//        }
//
//        // if quantities are not equal, update existing queue to database
//        if (queue.getQuantity() != quantity && queue.getQuantity() <= MAX) {
//            queue.setQuantity(quantity);
//
//            Update(queue);
//        }
    }

    // -- PUBLIC DATABASE QUERY METHODS --

    public void Remove(long engramId) {
//        Remove(dataSource.findSingleQueue(engramId));
    }

    public void Remove(Queue queue) {
//        dataSource.DeleteFromQueue(queue);
//
//        UpdateData();
    }

    public void Update(Queue queue) {
//        dataSource.UpdateQueue(queue);
//
//        UpdateData();
    }

    public void Insert(long engramId, int quantity) {
//        dataSource.InsertToQueueWithEngramId(engramId, quantity);
//
//        UpdateData();
    }

    public void Clear() {
//        dataSource.ClearQueue();
//
//        UpdateData();
    }

    // -- PRIVATE UTILITY METHODS --

    private void setEngrams() {
//        this.engrams = dataSource.findAllCraftableEngrams();
    }

    private SparseArray<QueueEngram> getEngrams() {
        return engrams;
    }

    private SparseArray<QueueResource> getResources() {
//        if (!hasComplexResources()) return dataSource.findAllCraftableResourcesFromQueue();
//
//        SparseArray<QueueResource> resources = new SparseArray<>();
//        HashMap<Long, QueueResource> resourceMap = new HashMap<>();
//
//        SparseArray<QueueEngram> engrams = getEngrams();
//        HashMap<Long, QueueEngram> engramMap = new HashMap<>();
//
//        for (int i = 0; i < engrams.size(); i++) {
//            engramMap.put(engrams.valueAt(i).getId(), engrams.valueAt(i));
//        }
//
//        for (int i = 0; i < engrams.size(); i++) {
//            QueueEngram engram = engrams.valueAt(i);
//
//            Helper.Log(LOGTAG, "-- Checking engram: " + engram.toString());
//
//            SparseArray<QueueResource> composition = dataSource.findEngramResources(engram.getId());
//            for (int j = 0; j < composition.size(); j++) {
//                int imageId = composition.keyAt(j);
//                QueueResource resource = composition.valueAt(j);
//
//                Helper.Log(LOGTAG, " > Checking resource: " + resource.toString());
//
//                if (ComplexResourceInitializer.getResources().get(imageId) == null) {
//                    resource.setQuantity(resource.getQuantity() * engram.getQuantity());
//
//                    if (resourceMap.containsKey(resource.getId())) {
//                        resources.get(imageId).increaseQuantity(resource.getQuantity());
//                    } else {
//                        resources.put(imageId, resource);
//                        resourceMap.put(resource.getId(), resource);
//                    }
//                } else {
//                    Engram engramFromResources = dataSource.findSingleEngramByImageId(imageId);
//                    int combinedQuantity = resource.getQuantity() * engram.getQuantity();
//
//                    Helper.Log(LOGTAG, " >>>> resource:" + resource.toString());
//                    Helper.Log(LOGTAG, " >>>> engramFromResources:" + engramFromResources.toString());
//                    Helper.Log(LOGTAG, " >>>> engram:" + engram.toString());
//
//                    if (engramMap.containsKey(engramFromResources.getId())) {
//                        QueueEngram newEngram = engramMap.get(engramFromResources.getId());
//                        newEngram.increaseQuantity(combinedQuantity);
//                    } else {
//                        QueueEngram newEngram = new QueueEngram(engramFromResources, combinedQuantity);
//
//                        engrams.put(engrams.size(), newEngram);
//                        engramMap.put(newEngram.getId(), newEngram);
//                    }
//                }
//            }
//        }
//
        return resources;
    }

    private void UpdateData() {
        setEngrams();
        resources = getResources();
    }
}
