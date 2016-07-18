package com.gmail.jaredstone1982.craftingcalcark.model;

import android.content.Context;
import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.R;
import com.gmail.jaredstone1982.craftingcalcark.db.DataSource;
import com.gmail.jaredstone1982.craftingcalcark.helpers.Helper;
import com.gmail.jaredstone1982.craftingcalcark.helpers.PreferenceHelper;

/**
 * Description: Proposed idea for instantiating, displaying, manipulating the main list of Engrams and Categories
 * Usage: Communicates with DataSource directly, returns data pulled from database
 * Used by: MainActivity
 * Variables: LOGTAG, dataSource
 * Last Edit: Added methods to receive and change Categories based upon its level
 */
public class DisplayCase {
    private static final String LOGTAG = "DISPLAYCASE";
    private static final long ROOT = 0;

    private boolean isFiltered;
    private long level;
    private long parent;

    private SparseArray<DisplayEngram> engrams;
    private SparseArray<Category> categories;

    private DataSource dataSource;

    public DisplayCase(Context context) {
        PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(context);
        isFiltered = preferenceHelper.getBooleanPreference(Helper.FILTERED);

        level = 0;
        parent = 0;

        dataSource = DataSource.getInstance(context, LOGTAG);

        UpdateData();
    }

    public boolean isFiltered() {
        return isFiltered;
    }

    public long getLevel() {
        return level;
    }

    public long getParent() {
        return parent;
    }

    public boolean setIsFiltered(boolean filtered) {
        if (isFiltered() != filtered) {
            PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(getContext());
            preferenceHelper.setPreference(Helper.FILTERED, filtered);

            this.isFiltered = filtered;

            UpdateData();

            return true;
        }
        return false;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    /**
     * -- METHODS THAT RETURN TO VIEWHOLDER --
     */

    public int getImageId(int position) {
        if (isFiltered) {
            if (position >= categories.size()) {
                position -= categories.size();

                DisplayEngram engram = engrams.valueAt(position);

                return engram.getImageId();
            } else {
                Category category = categories.valueAt(position);

                return category.getImageId();
            }
        } else {
            DisplayEngram engram = engrams.valueAt(position);

            return engram.getImageId();
        }
    }

    public String getName(int position) {
        if (isFiltered) {
            if (position >= categories.size()) {
                position -= categories.size();

                DisplayEngram engram = engrams.valueAt(position);

                return engram.getName();
            } else {
                Category category = categories.valueAt(position);

                return category.getName();
            }
        } else {
            DisplayEngram engram = engrams.valueAt(position);

            return engram.getName();
        }
    }

    public String getName(long categoryId) {
        if (categoryId > ROOT) {
            Category category = getCategory(categoryId);
            return "Go Back to " + category.getName();
        } else {
            return "Go Back";
        }
    }

    public Context getContext() {
        return dataSource.getContext();
    }

    /**
     * -- PUBLIC METHODS --
     */

    public boolean isEngram(int position) {
        return position >= categories.size();
    }

    public int getCount() {
        return engrams.size() + categories.size();
    }

    public long getEngramId(int position) {
        if (getLevel() > 0) {
            // subtract position by amount of categories shown
            position -= categories.size();
        }
        return engrams.valueAt(position).getId();
    }

    public void changeCategory(int position) {
        if (position < categories.size()) {
            // position within bounds
            Category category = categories.valueAt(position);

            Helper.Log(LOGTAG, "Changing category to [" + position + "] " + category.toString());

            if (getLevel() == ROOT) {
                // ROOT LEVEL
                setLevel(category.getId());   // Grabbing ID is the best way to track its location. TODO: Get rid of level variable in category object?
                setParent(category.getParent());
            } else {
                if (position == 0) {
                    // back button or FIRST CATEGORY, revert back to parent level or root level
                    if (category.getParent() > ROOT) {
                        Category parentCategory = getCategory(category.getParent());

                        setLevel(category.getParent());
                        setParent(parentCategory.getParent());
                    } else {
                        setLevel(ROOT);
                        setParent(ROOT);
                    }
                } else {
                    // position is > 0, making this a normal category object
                    setLevel(category.getId());   // Grabbing ID is the best way to track its location. TODO: Get rid of level variable in category object?
                    setParent(category.getParent());
                }
            }

            // Update lists with new data
            UpdateData();
        } else {
            // position out of bounds
        }
    }

    /**
     * -- PRIVATE UTILITY METHODS --
     */

    private Category getCategory(long categoryId) {
        return dataSource.findSingleCategory(categoryId);
    }

    private SparseArray<DisplayEngram> getEngrams() {
        SparseArray<DisplayEngram> engrams;

        if (isFiltered()) {
            engrams = dataSource.findAllDisplayEngrams(getLevel());
        } else {
            engrams = dataSource.findAllDisplayEngrams();
        }
        return engrams;
    }

    private SparseArray<Category> getCategories() {
        SparseArray<Category> categories = new SparseArray<>();

        if (isFiltered()) {
            categories = dataSource.findFilteredCategories(getLevel());

            if (getLevel() > 0) {
                Category category = new Category(0, getLevel(), getName(getParent()), getParent(), R.drawable.back);
                categories.put(0, category);
            }

            debugCategories(categories);
        }

        return categories;
    }

    private void debugCategories(SparseArray<Category> categories) {
//        SparseArray<Category> categories = dataSource.findAllCategories();

        Helper.Log(LOGTAG, "-- Displaying categories at level " + getLevel() + "..");
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.valueAt(i);
            Helper.Log(LOGTAG, "-> [" + i + "/" + categories.keyAt(i) + "] " + category.toString());
        }
        Helper.Log(LOGTAG, "-- Display completed.");
    }

    private void UpdateData() {
        engrams = getEngrams();
        categories = getCategories();
    }
}
