package com.gmail.jaredstone1982.craftingcalcark.model;

import android.content.Context;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.db.EngramDataSource;

/**
 * Proposed idea for instantiating, displaying, manipulating the main list of Engrams
 *
 * Thinking of this as a display cabinet
 *
 */
public class DisplayCase {
    private SparseArray<DisplayEngram> engrams;
    private EngramDataSource dataSource;

    public DisplayCase(Context context) {
        dataSource = new EngramDataSource(context, "DISPLAYCASE");
        dataSource.Open();

        engrams = dataSource.findAll();
        if (engrams.size() == 0) {
            engrams = dataSource.Initialize();
        }
    }

    public void Reset() {
        dataSource.Reset();
    }

    public SparseArray<DisplayEngram> getEngrams() {
        return engrams;
    }

    public DisplayEngram getEngram(int position) {
        return engrams.valueAt(position);
    }

    public long getId(int position) {
        return engrams.valueAt(position).getId();
    }
}
