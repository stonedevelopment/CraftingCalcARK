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

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import arc.resource.calculator.db.entity.EngramEntity;
import arc.resource.calculator.db.entity.FolderEntity;
import arc.resource.calculator.db.entity.StationEntity;
import arc.resource.calculator.ui.explorer.back.BackFolderExplorerItem;

public class ExplorerViewModel extends AndroidViewModel {
    // TODO: Maintain filters?
    public static final String TAG = ExplorerViewModel.class.getSimpleName();
    private final ExplorerItemRepository mRepository;

    private final MutableLiveData<BackFolderExplorerItem> mBackFolderExplorerItem = new MutableLiveData<>();
    private SingleLiveEvent<ExplorerViewModelState> mViewModelState = new SingleLiveEvent();

    private StationEntity mCurrentStation;

    public ExplorerViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ExplorerItemRepository(application);
    }

    MutableLiveData<ExplorerViewModelState> getViewModelState() {
        return mViewModelState;
    }

    private void setViewModelState(ExplorerViewModelState viewModelState) {
        mViewModelState.setValue(viewModelState);
    }

    LiveData<List<StationEntity>> getStations() {
        return mStationExplorerRepository.getStations();
    }

    LiveData<List<FolderEntity>> getFolders() {
        return mFolderExplorerRepository.getFolders();
    }

    LiveData<List<EngramEntity>> getEngrams() {
        return mEngramExplorerRepository.getEngrams();
    }

    MutableLiveData<BackFolderExplorerItem> getBackFolderExplorerItem() {
        return mBackFolderExplorerItem;
    }

    private void setBackFolderExplorerItem(BackFolderExplorerItem explorerItem) {
        mBackFolderExplorerItem.setValue(explorerItem);
    }

    private void setCurrentStation(StationEntity station) {
        mCurrentStation = station;
    }

    /**
     * User-controlled action to select a crafting station to view its contents
     * (change station as current destination container)
     *
     * @param station Station to select (change to)
     */
    public void selectStation(StationEntity station) {
        setBackFolderExplorerItem(BackFolderExplorerItem.fromStation(station));
        setCurrentStation(station);
        mStationExplorerRepository.selectStation();
        mFolderExplorerRepository.selectStation(station);
        mEngramExplorerRepository.selectStation(station);
    }

    private void deselectStation() {
        setBackFolderExplorerItem(null);
        setCurrentStation(null);
        mStationExplorerRepository.deselectStation();
        mFolderExplorerRepository.deselectStation();
        mEngramExplorerRepository.deselectStation();
    }

    /**
     * User-derived action to select a folder location to view its contents
     * (adds selected folder for a stack of folders to track history)
     *
     * @param folder Folder to select (change to)
     */
    public void selectFolder(FolderEntity folder) {
        setBackFolderExplorerItem(BackFolderExplorerItem.fromFolder(folder));
        mFolderExplorerRepository.selectFolder(folder);
        mEngramExplorerRepository.selectFolder(folder);
    }

    /**
     * User-derived action to traverse backwards from current folders into a list of folders
     * (back action for folders: back button or tapping a back folder)
     */
    public void deselectFolder() {
        mFolderExplorerRepository.deselectFolder();
    }

    /**
     * ViewHolder-derived action triggered when User clicks on an engram thumbnail
     *
     * @param engram Engram to select (view)
     */
    public void selectEngram(EngramEntity engram) {
        //  navigate to details screen for engram
    }

    public void goBack(BackFolderExplorerItem explorerItem) {
        switch (explorerItem.getItemType()) {
            case CraftingStation:
                deselectStation();
                break;
            case Folder:
                deselectFolder();
                break;
            case Engram:
                break;
        }
    }
}
