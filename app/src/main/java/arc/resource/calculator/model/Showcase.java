package arc.resource.calculator.model;

import android.content.Context;
import android.util.SparseArray;

import arc.resource.calculator.db.DataSource;
import arc.resource.calculator.model.engram.DetailEngram;
import arc.resource.calculator.model.resource.CompositeResource;
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
public class Showcase {
    private static final String LOGTAG = "SHOWCASE";

    private DetailEngram engram;
    private DataSource dataSource;

    public Showcase( Context context, long engramId ) {

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

    public String getDrawable() {
        return engram.getDrawable();
    }

    public String getDescription() {
        return engram.getDescription();
    }

    public int getQuantity() {
//        return engram.getQuantity();
        return 0;
    }

    public SparseArray<QueueResource> getComposition() {
        SparseArray<QueueResource> returnableComposition = new SparseArray<>();

        SparseArray<CompositeResource> baseComposition = engram.getComposition();

//        int quantity = engram.getQuantity();

        for (int i = 0; i < baseComposition.size(); i++) {
            CompositeResource resource = baseComposition.valueAt( i );
//            resource.setQuantity( quantity );

//            returnableComposition.append(i, resource );
        }
        return returnableComposition;
    }

    public void setQuantity(int quantity) {
//        engram.setQuantity(quantity);
    }

    public String getCategoryDescription() {
//        Category category = dataSource.findSingleCategory(engram.getCategoryId());
//        long parent = category.getParent();
//
//        StringBuilder builder = new StringBuilder(category.getName());
//        while (parent > 0) {
//            category = dataSource.findSingleCategory(parent);
//            parent = category.getParent();
//
//            builder.insert(0, category.getName() + "/");
//        }
//
//        return builder.toString();
        return null;
    }
}
