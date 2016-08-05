package arc.resource.calculator.model;

import android.content.Context;
import android.util.SparseArray;

import java.util.HashMap;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DataSource;
import arc.resource.calculator.helpers.Helper;
import arc.resource.calculator.helpers.PreferenceHelper;

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
public class DisplayCase {
    private static final String LOGTAG = "DISPLAYCASE";
    private static final long ROOT = 0;

    private boolean isFiltered;
    private long level;
    private long parent;

    private SparseArray<DisplayEngram> engrams = null;
    private SparseArray<Category> categories = null;
    private HashMap<Long, Queue> queues = null;

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

    public boolean inQueue(long engramId) {
        Queue queue = dataSource.findSingleQueue(engramId);

        return queue != null;
    }

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

    public int getQuantity(int position) {
        if (isFiltered) {
            if (position < categories.size()) {
                return 0;
            }

            position -= categories.size();
        }

        Queue queue = queues.get(engrams.valueAt(position).getId());

        if (queue != null) {
            return queue.getQuantity();
        }

        return 0;
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
        if (engrams != null && categories != null) {
            return engrams.size() + categories.size();
        }
        return 0;
    }

    public long getEngramId(int position) {
        if (isFiltered) {
            if (position >= categories.size()) {
                position -= categories.size();

                return engrams.valueAt(position).getId();
            }
        } else {
            return engrams.valueAt(position).getId();
        }

        return 0;
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

    private HashMap<Long, Queue> getQueues() {
        return dataSource.findAllQueues();
    }

    private void debugCategories(SparseArray<Category> categories) {
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
        queues = getQueues();
    }
}
