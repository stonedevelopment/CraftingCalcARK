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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.Stack;

import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;
import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.db.entity.primary.FolderEntity;
import arc.resource.calculator.db.entity.primary.StationEntity;
import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.model.ui.InteractiveGameViewModel;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;

import static arc.resource.calculator.util.Constants.cBackFolderViewType;
import static arc.resource.calculator.util.Constants.cFolderViewType;
import static arc.resource.calculator.util.Constants.cStationViewType;

public class ExplorerViewModel extends InteractiveGameViewModel {
    public static final String TAG = ExplorerViewModel.class.getSimpleName();

    private final ExplorerRepository repository;
    private final Stack<ExplorerItem> historyStack = new Stack<>(); // TODO: 11/22/2020 What happens when app starts at bookmarked location?

    private final SingleLiveEvent<String> parentIdEvent = new SingleLiveEvent<>();

    public ExplorerViewModel(@NonNull Application application) {
        super(application);
        repository = new ExplorerRepository(application);
    }

    @Override
    public void start() {
        super.start();
        fetchDirectory();
    }

    public LiveData<DirectorySnapshot> getDirectorySnapshot() {
        return transformDirectoryListToSnapshot();
    }

    @Nullable
    public ExplorerItem getCurrentExplorerItem() {
        return peekAtStack();
    }

    public boolean hasParentExplorerItem() {
        return historyStack.size() > 1;
    }

    public boolean hasParentOfParentExplorerItem() {
        return historyStack.size() > 2;
    }

    @Nullable
    public ExplorerItem getParentExplorerItem() {
        int size = historyStack.size();
        return historyStack.get(size - 2);
    }

    @Nullable
    public ExplorerItem getParentOfParentExplorerItem() {
        int size = historyStack.size();
        return historyStack.get(size - 3);
    }

    private void updateParentId(String parentId) {
        parentIdEvent.setValue(parentId);
    }

    private void pushToStack(ExplorerItem explorerItem) {
        historyStack.push(explorerItem);
    }

    private ExplorerItem peekAtStack() {
        if (historyStack.isEmpty()) return null;
        return historyStack.peek();
    }

    private void popFromStack() {
        historyStack.pop();
    }

    public void handleOnClickEvent(ExplorerItem explorerItem) {
        if (explorerItem.getViewType() == cBackFolderViewType) {
            goBack();
        } else if (explorerItem.getViewType() == cStationViewType ||
                explorerItem.getViewType() == cFolderViewType) {
            goForward(explorerItem);
        } else {
            viewDetails(explorerItem);
        }
    }

    private void goForward(ExplorerItem explorerItem) {
        pushToStack(explorerItem);
        fetchDirectory();
    }

    private void goBack() {
        popFromStack();
        fetchDirectory();
    }

    private void fetchDirectory() {
        ExplorerItem explorerItem = peekAtStack();
        String parentId = explorerItem == null ? "" : explorerItem.getUuid();
        updateParentId(parentId);
    }

    public LiveData<EngramEntity> fetchEngram(@NonNull String uuid) {
        return repository.fetchEngram(uuid);
    }

    public LiveData<FolderEntity> fetchFolder(@NonNull String uuid) {
        return repository.fetchFolder(uuid);
    }

    public LiveData<StationEntity> fetchStation(@NonNull String uuid) {
        return repository.fetchStation(uuid);
    }

    private LiveData<List<DirectoryItemEntity>> transformParentIdToDirectoryList() {
        return Transformations.switchMap(parentIdEvent,
                parentId -> repository.fetchDirectory(getGameEntityId(), parentId));
    }

    private LiveData<DirectorySnapshot> transformDirectoryListToSnapshot() {
        return Transformations.map(transformParentIdToDirectoryList(), this::createDirectorySnapshot);
    }

    private DirectorySnapshot createDirectorySnapshot(List<DirectoryItemEntity> directory) {
        return new DirectorySnapshot(getCurrentExplorerItem(), directory);
    }

    private void viewDetails(ExplorerItem explorerItem) {
        //  request detail pop up
        setSnackBarMessage(explorerItem.getTitle());
        Log.d(TAG, explorerItem.toString());
    }
}