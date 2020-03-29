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

package arc.resource.calculator.ui.explorer;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.ExplorerDao;
import arc.resource.calculator.db.entity.StationEntity;
import arc.resource.calculator.db.repository.EngramRepository;
import arc.resource.calculator.db.repository.FolderRepository;
import arc.resource.calculator.db.repository.StationRepository;

class ExplorerRepository {
    private ExplorerDao mDao;

    private StationRepository mStationRepository;
    private FolderRepository mFolderRepository;
    private EngramRepository mEngramRepository;

    //  list of explorable items sent to recyclerview adapter
    private LiveData<List<ExplorerItem>> mItemList;

    //  history of explored items
    private Stack<ExplorerItem> mItemStack = new Stack<>();

    //  placeholder for current level of exploration
    private ExplorerItem mCurrentItem;

    ExplorerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mDao = db.explorerDao();
        mItemList = buildExplorerItems();

        mStationRepository = new StationRepository(application);
        mFolderRepository = new FolderRepository(application);
        mEngramRepository = new EngramRepository(application);

    }

    LiveData<List<ExplorerItem>> getItemList() {
        return mItemList;
    }

    private ExplorerItem getCurrentItem() {
        return mCurrentItem;
    }

    private ExplorerItemType getCurrentItemType() {
        return mCurrentItem.getItemType();
    }

    private boolean isCurrentItemNull() {
        return getCurrentItem() == null;
    }

    private boolean isCraftingStation() {
        return getCurrentItemType() == ExplorerItemType.CraftingStation;
    }

    private void updateItemList() {
        mItemList = buildExplorerItems();
    }

    private LiveData<List<ExplorerItem>> buildExplorerItems() {
        if (isCurrentItemNull()) {
            return Transformations.map(mDao.getStations(), input -> {
                List<ExplorerItem> output = new ArrayList<>();
                for (StationEntity stationEntity : input) {
                    output.add(ExplorerItem.fromStation(stationEntity));
                }
                return output;
            });
        } else {
            if (isCraftingStation()) {
                return Transformations.map(mDao.getChildrenItemsFromStation(getCurrentItem().getId()), input -> {
                    List<ExplorerItem> output = new ArrayList<>();
                    output.add(buildBackFolderExplorerItem());
                    output.addAll(ExplorerItem.fromFolders(input.folderEntities));
                    output.addAll(ExplorerItem.fromEngrams(input.engramEntities));
                    return output;
                });
            } else {
                return Transformations.map(mDao.getChildrenItemsFromFolder(getCurrentItem().getId()), input -> {
                    List<ExplorerItem> output = new ArrayList<>();
                    output.add(buildBackFolderExplorerItem());
                    output.addAll(ExplorerItem.fromFolders(input.folderEntities));
                    output.addAll(ExplorerItem.fromEngrams(input.engramEntities));
                    return output;
                });
            }
        }
    }

    private ExplorerItem buildBackFolderExplorerItem() {
        return ExplorerItem.backFolder(mCurrentItem);
    }

    private void navigateForwardToStation(ExplorerItem explorerItem) {
        //  update current item
        mCurrentItem = explorerItem;

        //  alert system of changes
        updateItemList();
    }

    private void navigateForwardToFolder(ExplorerItem explorerItem) {
        //  add previous explorer item to stack for traversing in reverse
        mItemStack.push(explorerItem);

        //  update current item
        mCurrentItem = explorerItem;

        //  alert system of changes
        updateItemList();
    }

    private void navigateBackFromFolder() {
        //  pop last explored item off list and update current item
        mCurrentItem = mItemStack.pop();

        //  alert system of changes
        updateItemList();
    }

    void onExplorerItemClick(int position, ExplorerItem explorerItem) {
        switch (explorerItem.getItemType()) {
            case CraftingStation:
                navigateForwardToStation(explorerItem);
                break;
            case Folder:
                //  if position is 0 and item type is folder, this signifies back folder
                if (position == 0) {
                    navigateBackFromFolder();
                } else {
                    navigateForwardToFolder(explorerItem);
                }
                break;
            case Engram:
                break;
        }
    }
}
