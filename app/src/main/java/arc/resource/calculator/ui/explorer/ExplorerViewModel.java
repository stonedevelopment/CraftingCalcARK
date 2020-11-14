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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Stack;

import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;
import arc.resource.calculator.db.entity.primary.EngramEntity;
import arc.resource.calculator.model.InteractiveViewModel;
import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;
import arc.resource.calculator.ui.main.MainViewModel;

import static arc.resource.calculator.util.Constants.cBackFolderViewType;
import static arc.resource.calculator.util.Constants.cFolderViewType;
import static arc.resource.calculator.util.Constants.cStationViewType;

public class ExplorerViewModel extends InteractiveViewModel {
    public static final String TAG = ExplorerViewModel.class.getSimpleName();

    private final ExplorerRepository repository;
    private final Stack<ExplorerItem> historyStack = new Stack<>();
    private final LiveData<DirectorySnapshot> directorySnapshot;

    private LiveData<GameEntity> gameEntityLiveData;
    private SingleLiveEvent<String> parentIdSingleLiveEvent = new SingleLiveEvent<>();

    public ExplorerViewModel(@NonNull Application application) {
        super(application);
        repository = new ExplorerRepository(getApplication());
        directorySnapshot = transformDirectoryListToSnapshot();
    }

    void injectViewModels(FragmentActivity fragment) {
        MainViewModel mainViewModel = new ViewModelProvider(fragment).get(MainViewModel.class);
        gameEntityLiveData = mainViewModel.getGameEntityEvent();
        gameEntityLiveData.observe(fragment, gameEntity -> fetchDirectory());
    }

    LiveData<DirectorySnapshot> getDirectorySnapshot() {
        return directorySnapshot;
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

    public LiveData<GameEntity> getGameEntityLiveData() {
        return gameEntityLiveData;
    }

    void updateParentId(String parentId) {
        parentIdSingleLiveEvent.setValue(parentId);
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
        Log.d(TAG, "handleOnClickEvent: " + explorerItem.getTitle());
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
        Log.d(TAG, "goForward: " + explorerItem.getTitle());
        pushToStack(explorerItem);
        fetchDirectory();
    }

    private void goBack() {
        Log.d(TAG, "goBack: ");
        popFromStack();
        fetchDirectory();
    }

    private void fetchDirectory() {
        setIsLoading(true);
        ExplorerItem explorerItem = peekAtStack();
        String parentId = explorerItem == null ? "" : explorerItem.getUuid();

        Log.d(TAG, "fetchDirectory: " + parentId);
        updateParentId(parentId);
    }

    public LiveData<EngramEntity> fetchEngram(@NonNull String uuid) {
        return repository.fetchEngram(uuid);
    }

    private LiveData<List<DirectoryItemEntity>> transformParentIdToDirectoryList() {
        return Transformations.switchMap(parentIdSingleLiveEvent, repository::fetchDirectory);
    }

    private LiveData<DirectorySnapshot> transformDirectoryListToSnapshot() {
        return Transformations.map(transformParentIdToDirectoryList(), directory -> {
            setIsLoading(false);
            Log.d(TAG, "ExplorerViewModel: transforming directory entity list: " + directory.size());
            ExplorerItem current = getCurrentExplorerItem();
            return new DirectorySnapshot(current, directory);
        });
    }

    private void viewDetails(ExplorerItem explorerItem) {
        //  request detail pop up
        setSnackBarMessage(explorerItem.getTitle());
        Log.d(TAG, explorerItem.toString());
    }
}