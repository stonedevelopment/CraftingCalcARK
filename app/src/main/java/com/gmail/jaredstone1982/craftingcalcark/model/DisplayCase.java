package com.gmail.jaredstone1982.craftingcalcark.model;

import android.content.Context;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.R;
import com.gmail.jaredstone1982.craftingcalcark.db.DataSource;
import com.gmail.jaredstone1982.craftingcalcark.helpers.Helper;

/**
 * Description: Proposed idea for instantiating, displaying, manipulating the main list of Engrams and Categories
 * Usage: Communicates with DataSource directly, returns data pulled from database
 * Used by: MainActivity
 * Variables: LOGTAG, dataSource
 * <p/>
 * TODO If level > 0, insert a BACK object containing a parent level variable
 * TODO If isFiltered, pull category objects from db, then pull engrams.. but how do we display them together in one listAdapter?
 * <p/>
 * <p/>
 * Last Edit: Added level variable to track where in the displayCase it is, if isFiltered is set to true
 */
public class DisplayCase {
    private static final String LOGTAG = "DISPLAYCASE";

    private boolean isFiltered;
    private long level;

//    private SparseArray<DisplayEngram> engrams;
//    private SparseArray<Category> categories;

    private DataSource dataSource;

    public DisplayCase(Context context) {
        dataSource = new DataSource(context, LOGTAG);
        dataSource.Open();

//        this.engrams = findAllDisplayEngrams();
//        this.categories = findAllCategories();

        this.isFiltered = false;
        this.level = 0;
    }

    public boolean isFiltered() {
        return isFiltered;
    }

    public void setIsFiltered(boolean filtered) {
        this.isFiltered = filtered;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public SparseArray<DisplayEngram> getEngrams() {
        return findAllDisplayEngrams();
    }

    private SparseArray<DisplayEngram> findAllDisplayEngrams() {
        SparseArray<DisplayEngram> engrams;

        if (isFiltered()) {
            engrams = dataSource.findAllDisplayEngrams(getLevel());
        } else {
            engrams = dataSource.findAllDisplayEngrams();
            if (engrams.size() == 0) {
                Reset(); // FIXME: Initializing both Resources and Engrams from inside DisplayCase class? Seems not quite right.
                engrams = dataSource.findAllDisplayEngrams();
            }
        }
        return engrams;
    }

    private SparseArray<Category> findAllCategories() {
        SparseArray<Category> categories = new SparseArray<>();

        if (level > 0) {
            Category category = new Category(0, 0, "Back", getLevel(), R.drawable.back);
            categories.put(categories.size(), category);
        }
        categories = dataSource.findFilteredCategories(getLevel());

        return categories;
    }

    private void Reset() {
        dataSource.Reset();
    }

    public void debugAllCategories() {
        SparseArray<Category> categories = dataSource.findAllCategories();

        Helper.setDebug(true);
        Helper.Log(LOGTAG, "-- Displaying all categories in database..");
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.valueAt(i);
            Helper.Log(LOGTAG, "-> " + category.toString());
        }
        Helper.Log(LOGTAG, "-- Display completed.");
        Helper.setDebug(false);
    }
}
