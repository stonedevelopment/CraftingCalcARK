package ark.resource.calculator.model;

import android.content.Context;
import android.util.SparseArray;

import ark.resource.calculator.db.DataSource;

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
public class Showcase {
    private static final String LOGTAG = "SHOWCASE";

    private DetailEngram engram;
    private DataSource dataSource;

    public Showcase(Context context, Long id) {
        this.dataSource = DataSource.getInstance(context, LOGTAG);

        engram = dataSource.findSingleDetailEngram(id);
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

    public String getCategoryDescription() {
        Category category = dataSource.findSingleCategory(engram.getCategoryId());
        long parent = category.getParent();

        StringBuilder builder = new StringBuilder(category.getName());
        while (parent > 0) {
            category = dataSource.findSingleCategory(parent);
            parent = category.getParent();

            builder.insert(0, category.getName() + "/");
        }

        return builder.toString();
    }
}
