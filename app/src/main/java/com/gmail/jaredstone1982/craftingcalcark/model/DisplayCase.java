package com.gmail.jaredstone1982.craftingcalcark.model;

import android.content.Context;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.db.EngramDataSource;

/**
 * Proposed idea for instantiating, displaying, manipulating the main list of Engrams
 *
 * Thinking of this as a display cabinet
 *
 * There is a major difference between DisplayCase and CraftingQueue, they are essentially the same,
 *  except DisplayCase stores a static array of Engram objects whereas CraftingQueue is 100% dynamic.
 *  This makes sense now, but later on could result in problems when filters and hierarchical folders
 *  come into play.
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

        dataSource.Close();
    }

    public void Reset() {
        dataSource.Open();
        dataSource.Reset();
        dataSource.Close();
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
