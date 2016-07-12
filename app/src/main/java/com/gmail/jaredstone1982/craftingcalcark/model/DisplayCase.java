package com.gmail.jaredstone1982.craftingcalcark.model;

import android.content.Context;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.db.DataSource;

/**
 * Description: Proposed idea for instantiating, displaying, manipulating the main list of Engrams, thinking of this as a display cabinet.
 * Usage: Communicates with DataSource directly, returns data pulled from database
 * Used by: MainActivity
 * Variables: LOGTAG, dataSource
 * <p/>
 * Last Edit: Converted DisplayCase to strictly return data pulled from database, no longer holds values
 */
public class DisplayCase {
    private static final String LOGTAG = "DISPLAYCASE";

    private DataSource dataSource;

    public DisplayCase(Context context) {
        dataSource = new DataSource(context, LOGTAG);
        dataSource.Open();
    }

    public SparseArray<DisplayEngram> getEngrams() {
        SparseArray<DisplayEngram> engrams;

        engrams = dataSource.findAllDisplayEngrams();
        if (engrams.size() == 0) {
            dataSource.Reset(); // FIXME: Initializing both Resources and Engrams from inside DisplayCase class? Seems not quite right.
            engrams = dataSource.findAllDisplayEngrams();
        }
        return engrams;
    }

    public void Reset() {
        dataSource.Reset();
    }
}
