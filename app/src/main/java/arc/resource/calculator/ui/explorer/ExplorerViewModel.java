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

import arc.resource.calculator.db.entity.primary.DirectoryEntity;
import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;

public class ExplorerViewModel extends AndroidViewModel {
    // TODO: Maintain filters?
    public static final String TAG = ExplorerViewModel.class.getSimpleName();
    private final SingleLiveEvent<String> mSnackBarMessageEvent = new SingleLiveEvent<>();
    private final Stack<ExplorerItem> mHistoryStack = new Stack<>();
    private ExplorerRepository mRepository;
    private MutableLiveData<ExplorerItem> mParentItem = new MutableLiveData<>();
    private LiveData<String> mParentId;
    private LiveData<List<DirectoryEntity>> mDirectoryEntityList;
    private LiveData<DirectorySnapshot> mDirectorySnapshot;

    public ExplorerViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ExplorerRepository(getApplication());
        mParentId = Transformations.map(mParentItem, parentItem -> {
            Log.d(TAG, "ExplorerViewModel: transforming parentItem: " + parentItem);
            if (parentItem == null) return "fdc6a946-054d-41c3-80d4-a8868afabb24";
            return parentItem.getUuid();
        });
        mDirectoryEntityList = Transformations.switchMap(mParentId, parentId -> {
            Log.d(TAG, "ExplorerViewModel: transforming parentId: " + parentId);
            return mRepository.fetchDirectory(parentId);
        });
        mDirectorySnapshot = Transformations.map(mDirectoryEntityList, directory -> {
            Log.d(TAG, "ExplorerViewModel: transforming directorySnapshot: " + directory.size());
            ExplorerItem current = getCurrentExplorerItem();
            return new DirectorySnapshot(current, directory);
        });

        start();
    }

    private void start() {
        setParentExplorerItem(peekAtStack());
    }

    SingleLiveEvent<String> getSnackBarMessageEvent() {
        return mSnackBarMessageEvent;
    }

    void setSnackBarMessage(String message) {
        mSnackBarMessageEvent.setValue(message);
    }

    private void setParentExplorerItem(ExplorerItem explorerItem) {
        mParentItem.setValue(explorerItem);
    }

    LiveData<DirectorySnapshot> getDirectorySnapshot() {
        return mDirectorySnapshot;
    }

    @Nullable
    private ExplorerItem getCurrentExplorerItem() {
        return peekAtStack();
    }

    private void pushToStack(ExplorerItem explorerItem) {
        mHistoryStack.push(explorerItem);
    }

    private ExplorerItem peekAtStack() {
        if (mHistoryStack.isEmpty()) return null;
        return mHistoryStack.peek();
    }

    private void popFromStack() {
        mHistoryStack.pop();
    }

    void handleOnClickEvent(ExplorerItem explorerItem) {
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
        pushToStack(explorerItem);
        setParentExplorerItem(explorerItem);
    }

    private void goBack() {
        popFromStack();
        setParentExplorerItem(peekAtStack());
    }

    private void viewDetails(ExplorerItem explorerItem) {
        //  request detail pop up
        setSnackBarMessage(explorerItem.getTitle());
    }
}