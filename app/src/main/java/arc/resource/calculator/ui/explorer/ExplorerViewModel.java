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
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.Stack;

import arc.resource.calculator.db.entity.primary.DirectoryItemEntity;
import arc.resource.calculator.model.InteractiveViewModel;
import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;

public class ExplorerViewModel extends InteractiveViewModel {
    public static final String TAG = ExplorerViewModel.class.getSimpleName();

    private final ExplorerRepository repository;
    private final Stack<ExplorerItem> historyStack = new Stack<>();

    private final LiveData<DirectorySnapshot> directorySnapshot;
    private final MutableLiveData<String> parentId = new MutableLiveData<>();

    public ExplorerViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "ExplorerViewModel: constructor");

        setIsLoading(true);
        repository = new ExplorerRepository(getApplication());
        directorySnapshot = transformDirectoryListToSnapshot();
    }

    public void fetch() {
        fetchDirectory();
    }

    LiveData<DirectorySnapshot> getDirectorySnapshot() {
        return directorySnapshot;
    }

    @Nullable
    private ExplorerItem getCurrentExplorerItem() {
        return peekAtStack();
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

    void handleOnClickEvent(ExplorerItem explorerItem) {
        Log.d(TAG, "handleOnClickEvent: " + explorerItem.getTitle());
        if (explorerItem.getViewType() == -1) {
            goBack();
        } else if (explorerItem.getViewType() == 0 ||
                explorerItem.getViewType() == 1) {
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
        this.parentId.setValue(parentId);
    }

    private void viewDetails(ExplorerItem explorerItem) {
        //  request detail pop up
        setSnackBarMessage(explorerItem.getTitle());
        Log.d(TAG, explorerItem.toString());
    }

    private LiveData<List<DirectoryItemEntity>> transformParentIdToDirectoryList() {
        return Transformations.switchMap(parentId, parentId -> {
            Log.d(TAG, "ExplorerViewModel: transforming parentId: " + parentId);
            return repository.fetchDirectory(parentId);
        });
    }

    private LiveData<DirectorySnapshot> transformDirectoryListToSnapshot() {
        return Transformations.map(transformParentIdToDirectoryList(), directory -> {
            setIsLoading(false);
            Log.d(TAG, "ExplorerViewModel: transforming directory entity list: " + directory.size());
            ExplorerItem current = getCurrentExplorerItem();
            return new DirectorySnapshot(current, directory);
        });
    }
}