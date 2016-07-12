package com.gmail.jaredstone1982.craftingcalcark.model;

import android.content.Context;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.db.DataSource;

/**
 * Description: Proposed idea to display the details of a single Engram from the DisplayCase object
 * Usage: Store detailed Engram data to easily display objects' details
 * Used by: DetailActivity
 * Variables: engram
 */
public class Showcase {
    private DetailEngram engram;

    public Showcase(Context context, Long id) {
        DataSource dataSource = new DataSource(context, "SHOWCASE");
        dataSource.Open();
        engram = dataSource.findSingleDetailEngram(id);
        dataSource.Close();
    }

    public DetailEngram getEngram() {
        return engram;
    }

    public long getId() {
        return engram.getId();
    }

    public String getName() {
        return engram.getName();
    }

    public int getImageId() {
        return engram.getImageId();
    }

    public String getDescription() {
        return engram.getDescription();
    }

    public int getQuantity() {
        return engram.getQuantity();
    }

    public SparseArray<CraftableResource> getComposition() {
        SparseArray<CraftableResource> baseComposition = engram.getComposition();
        SparseArray<CraftableResource> returnableComposition = new SparseArray<>();

        int quantity = engram.getQuantity();

        for (int i = 0; i < baseComposition.size(); i++) {
            CraftableResource resource = baseComposition.valueAt(i);

            CraftableResource craftableResource = new CraftableResource(resource);
            craftableResource.setQuantity(resource.getQuantity() * quantity);

            returnableComposition.append(i, craftableResource);
        }
        return returnableComposition;
    }

    public void setQuantity(int quantity) {
        engram.setQuantity(quantity);
    }
}
