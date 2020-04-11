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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Stack;

import arc.resource.calculator.ui.explorer.back.BackFolderExplorerItem;
import arc.resource.calculator.ui.explorer.engram.EngramExplorerItem;
import arc.resource.calculator.ui.explorer.engram.EngramExplorerRepository;
import arc.resource.calculator.ui.explorer.folder.FolderExplorerItem;
import arc.resource.calculator.ui.explorer.folder.FolderExplorerRepository;
import arc.resource.calculator.ui.explorer.station.StationExplorerItem;
import arc.resource.calculator.ui.explorer.station.StationExplorerRepository;

import static arc.resource.calculator.db.AppDatabase.cParentId;

public class ExplorerViewModel extends AndroidViewModel {
    // TODO: Maintain filters?
    public static final String TAG = ExplorerViewModel.class.getSimpleName();
    private final StationExplorerRepository mStationExplorerRepository;
    private final FolderExplorerRepository mFolderExplorerRepository;
    private final EngramExplorerRepository mEngramExplorerRepository;

    private final LiveData<List<StationExplorerItem>> mStations;
    private final LiveData<List<FolderExplorerItem>> mFolders;
    private final LiveData<List<EngramExplorerItem>> mEngrams;

    private final MutableLiveData<BackFolderExplorerItem> mBackFolderExplorerItem = new MutableLiveData<>();

    private final Stack<ExplorerItem> mStack = new Stack<>();

    public ExplorerViewModel(@NonNull Application application) {
        super(application);
        mStationExplorerRepository = new StationExplorerRepository(application);
        mFolderExplorerRepository = new FolderExplorerRepository(application);
        mEngramExplorerRepository = new EngramExplorerRepository(application);

        mStations = mStationExplorerRepository.getStations();
        mFolders = mFolderExplorerRepository.getFolders();
        mEngrams = mEngramExplorerRepository.getEngrams();
    }

    LiveData<List<StationExplorerItem>> getStations() {
        return mStations;
    }

    LiveData<List<FolderExplorerItem>> getFolders() {
        return mFolders;
    }

    LiveData<List<EngramExplorerItem>> getEngrams() {
        return mEngrams;
    }

    MutableLiveData<BackFolderExplorerItem> getBackFolderExplorerItem() {
        return mBackFolderExplorerItem;
    }

    private void setBackFolderExplorerItem(@Nullable BackFolderExplorerItem explorerItem) {
        mBackFolderExplorerItem.setValue(explorerItem);
    }

    @Nullable
    private ExplorerItem getCurrentExplorerItem() {
        if (mStack.isEmpty()) return null;

        return mStack.peek();
    }

    @Nullable
    private BackFolderExplorerItem getParentExplorerItem() {
        if (mStack.size() == 1) return null;

        int parentPosition = mStack.size() - 2;
        ExplorerItem parentExplorerItem = mStack.get(parentPosition);
        return (BackFolderExplorerItem) parentExplorerItem;
    }

    private void setupBackFolderExplorerItem() {
        setBackFolderExplorerItem(getParentExplorerItem());
    }

    private void addToStack(ExplorerItem explorerItem) {
        mStack.push(explorerItem);
    }

    private void removeLastFromStack() {
        mStack.pop();
    }

    /**
     * User-derived action that "opens" a crafting station to expose a list of its contents
     * (change station as current destination container)
     *
     * @param stationExplorerItem Station to select (change to)
     */
    private void selectStation(StationExplorerItem stationExplorerItem) {
        mStationExplorerRepository.clearStations();
        mFolderExplorerRepository.fetchFolders(stationExplorerItem.getId(), cParentId);
        mEngramExplorerRepository.fetchEngrams(stationExplorerItem.getId(), cParentId);
    }

    /**
     * User-derived back action that "closes" a crafting station to expose the list of
     * available crafting stations.
     * (back action for folders: back button or tapping a back folder)
     */
    private void deselectStation() {
        mStationExplorerRepository.fetchStations();
        mFolderExplorerRepository.clearFolders();
        mEngramExplorerRepository.clearEngrams();
    }

    /**
     * User-derived action that "opens" a folder location to expose a list of its contents.
     * (adds selected folder for a stack of folders to track history)
     *
     * @param folderExplorerItem Folder to select (change to)
     */
    private void selectFolder(FolderExplorerItem folderExplorerItem) {
        mStationExplorerRepository.clearStations();
        mFolderExplorerRepository.fetchFolders(folderExplorerItem.getStationId(), folderExplorerItem.getId());
        mEngramExplorerRepository.fetchEngrams(folderExplorerItem.getStationId(), folderExplorerItem.getId());
    }

    /**
     * ViewHolder-derived action triggered when User clicks on an engram thumbnail
     *
     * @param engramExplorerItem Engram to select (view)
     */
    public void selectEngram(EngramExplorerItem engramExplorerItem) {
        //  navigate to details screen for engram
    }

    public void goForward(ExplorerItem explorerItem) {
        addToStack(explorerItem);
        setupBackFolderExplorerItem();

        if (getCurrentExplorerItem() instanceof StationExplorerItem) {
            selectStation((StationExplorerItem) getCurrentExplorerItem());
        } else if (getCurrentExplorerItem() instanceof FolderExplorerItem) {
            selectFolder((FolderExplorerItem) getCurrentExplorerItem());
        } else {
            //  throw exception
            Log.d(TAG, "goForward: unknown instanceof (" + explorerItem.getClass() + ")");
        }
    }

    /**
     * User-derived back action
     */
    public void goBack() {
        removeLastFromStack();
        setupBackFolderExplorerItem();

        if (getCurrentExplorerItem() == null) {
            deselectStation();
        } else if (getCurrentExplorerItem() instanceof StationExplorerItem) {
            selectStation((StationExplorerItem) getCurrentExplorerItem());
        } else if (getCurrentExplorerItem() instanceof FolderExplorerItem) {
            selectFolder((FolderExplorerItem) getCurrentExplorerItem());
        } else {
            //  throw exception
            Log.d(TAG, "goForward: unknown instanceof (" + getCurrentExplorerItem().getClass() + ")");
        }
    }
}