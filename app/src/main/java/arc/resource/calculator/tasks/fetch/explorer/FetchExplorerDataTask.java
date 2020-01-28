/*
 * Copyright (c) 2020 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */

package arc.resource.calculator.tasks.fetch.explorer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import arc.resource.calculator.model.category.BackCategory;
import arc.resource.calculator.model.map.CategoryMap;
import arc.resource.calculator.model.map.CraftableMap;
import arc.resource.calculator.model.map.StationMap;
import arc.resource.calculator.repository.explorer.ExplorerRepository;
import arc.resource.calculator.tasks.fetch.FetchDataTask;
import arc.resource.calculator.util.PrefsUtil;

public class FetchExplorerDataTask extends FetchDataTask {
    private final String TAG = FetchExplorerDataTask.class.getSimpleName();

    private StationMap mStationMap;
    private CategoryMap mCategoryMap;
    private CraftableMap mCraftableMap;

    public FetchExplorerDataTask(Context context, FetchExplorerDataTaskObservable observable) {
        super(context, observable);

        setupMaps();
    }

    @Override
    public FetchExplorerDataTaskObservable getObservable() {
        return (FetchExplorerDataTaskObservable) super.getObservable();
    }

    private void setupMaps() {
        mStationMap = new StationMap();
        mCategoryMap = new CategoryMap();
        mCraftableMap = new CraftableMap();
    }

    @Override
    protected void onPreExecute() {
        getObservable().notifyPreFetch();
        getObservable().notifyFetching();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        long dlc_id = PrefsUtil.getInstance(getContext()).getDLCPreference();

        Bundle bundle = ExplorerRepository.getInstance().gatherUrisForDataTask(dlc_id);
        Uri stationUri = bundle.getParcelable("station");
        Uri categoryUri = bundle.getParcelable("category");
        Uri craftableUri = bundle.getParcelable("craftable");

        BackCategory backCategory = null;
        if (bundle.containsKey("backCategoryId") && bundle.containsKey("backCategoryParent")) {
            long backCategoryId = bundle.getLong("backCategoryId");
            long backCategoryParent = bundle.getLong("backCategoryParent");

            backCategory = new BackCategory(backCategoryId, backCategoryParent);
        }

        try {
            mStationMap = ExplorerRepository.queryForStations(getContext(), stationUri);
            if (isCancelled()) return false;

            if (backCategory != null)
                mCategoryMap.add(backCategory.getId(), backCategory);
            mCategoryMap.addAll(ExplorerRepository.queryForCategories(getContext(), categoryUri));
            if (isCancelled()) return false;

            mCraftableMap = ExplorerRepository.queryForEngrams(getContext(), craftableUri);
            return !isCancelled();
        } catch (Exception e) {
            getObservable().notifyFetchExceptionCaught(e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean querySuccessful) {
        if (querySuccessful) {
            getObservable().notifyFetchSuccess(mStationMap, mCategoryMap, mCraftableMap);
        } else {
            getObservable().notifyFetchFail();
        }
    }
}