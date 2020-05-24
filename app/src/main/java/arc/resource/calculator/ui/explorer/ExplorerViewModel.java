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
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Stack;

import arc.resource.calculator.db.entity.primary.DirectoryEntity;
import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.ui.explorer.model.BackFolderExplorerItem;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;

public class ExplorerViewModel extends AndroidViewModel {
    // TODO: Maintain filters?
    public static final String TAG = ExplorerViewModel.class.getSimpleName();
    private final ExplorerRepository mRepository;
    private final SingleLiveEvent<String> mSnackBarMessageEvent = new SingleLiveEvent<>();
    private final Stack<ExplorerItem> mHistoryStack = new Stack<>();

    public ExplorerViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ExplorerRepository(application);
    }

    SingleLiveEvent<String> getSnackBarMessageEvent() {
        return mSnackBarMessageEvent;
    }

    void setSnackBarMessage(String message) {
        mSnackBarMessageEvent.setValue(message);
    }

    LiveData<List<DirectoryEntity>> getDirectorySnapshot() {
        return mRepository.getDirectorySnapshot();
    }

    @Nullable
    private ExplorerItem getCurrentExplorerItem() {
        if (mHistoryStack.isEmpty()) return null;
        return mHistoryStack.peek();
    }

    @Nullable
    private ExplorerItem getParentExplorerItem() {
        if (mHistoryStack.size() == 1) return null;
        int parentPosition = mHistoryStack.size() - 2;
        return mHistoryStack.get(parentPosition);
    }

    private void pushToStack(ExplorerItem explorerItem) {
        mHistoryStack.push(explorerItem);
    }

    private void popFromStack() {
        mHistoryStack.pop();
    }

    void handleOnClickEvent(ExplorerItem explorerItem) {
        if (explorerItem.getViewType() == 2 ||
                explorerItem.getViewType() == 1) {
            navigateTo(explorerItem);
        } else if (explorerItem instanceof BackFolderExplorerItem) {
            navigateFrom(explorerItem);
        } else {
            viewDetails(explorerItem);
        }
    }

    private void navigateTo(ExplorerItem explorerItem) {
        pushToStack(explorerItem);
        fetchDirectory(explorerItem.getUuid());
    }

    private void navigateFrom(ExplorerItem explorerItem) {
        popFromStack();
        fetchDirectory(explorerItem.getParentId());
    }

    private void viewDetails(ExplorerItem explorerItem) {
        //  request detail pop up
    }

    private void fetchDirectory(String parentId) {
        mRepository.fetchDirectory(parentId);
    }
}