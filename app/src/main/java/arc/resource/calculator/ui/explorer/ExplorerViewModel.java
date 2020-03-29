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

import arc.resource.calculator.R;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.repository.explorer.ExplorerObserver;
import arc.resource.calculator.repository.queue.QueueObserver;

public class ExplorerViewModel extends AndroidViewModel implements QueueObserver, ExplorerObserver {
    // TODO: Maintain filters?
    public static final String TAG = ExplorerViewModel.class.getSimpleName();

    private MutableLiveData<String> mSnackBarMessage = new MutableLiveData<>();
    private MutableLiveData<ExplorerViewModelState> mViewModelState = new MutableLiveData<>();

    private ExplorerRepository mRepository;

    private LiveData<List<ExplorerItem>> mItemList;

    public ExplorerViewModel(@NonNull Application application) {
        super(application);

        mRepository = new ExplorerRepository(application);
        mItemList = mRepository.getItemList();
    }

    public void showSnackBarMessage(String message) {
        setSnackBarMessage(message);
    }

    MutableLiveData<String> getSnackBarMessage() {
        return mSnackBarMessage;
    }

    private void setSnackBarMessage(String s) {
        mSnackBarMessage.setValue(s);
    }

    MutableLiveData<ExplorerViewModelState> getViewModelState() {
        return mViewModelState;
    }

    private void setViewModelState(ExplorerViewModelState viewModelState) {
        mViewModelState.setValue(viewModelState);
    }

    public LiveData<List<ExplorerItem>> getItemList() {
        return mItemList;
    }

    public void onExplorerItemClick(int position, ExplorerItem explorerItem) {
        mRepository.onExplorerItemClick(position, explorerItem);
    }

    @Override
    public void onItemAdded(@NonNull QueueEngram engram) {
        String message = String.format(getApplication().getString(R.string.snackbar_message_item_added_success_format), engram.getName());
        setSnackBarMessage(message);
    }

    @Override
    public void onItemRemoved(@NonNull QueueEngram engram) {
        String message = String.format(getApplication().getString(R.string.snackbar_message_item_removed_success_format), engram.getName());
        setSnackBarMessage(message);
    }

    @Override
    public void onItemChanged(@NonNull QueueEngram engram) {
        String message = String.format(getApplication().getString(R.string.snackbar_message_item_updated_success_format), engram.getName());
        setSnackBarMessage(message);
    }

    @Override
    public void onQueueDataPopulating() {
        //  do nothing
    }

    @Override
    public void onQueueDataPopulated() {
        //  do nothing
    }

    @Override
    public void onQueueDataEmpty() {
        // do nothing
    }

    @Override
    public void onEngramUpdated(int position) {
        //  do nothing
    }

    @Override
    public void onExplorerDataPopulating() {
        setViewModelState(ExplorerViewModelState.POPULATING);
    }

    @Override
    public void onExplorerDataPopulated() {
        setViewModelState(ExplorerViewModelState.POPULATED);
    }

    @Override
    public void onExplorerDataEmpty() {
        setViewModelState(ExplorerViewModelState.EMPTY);
    }

}
