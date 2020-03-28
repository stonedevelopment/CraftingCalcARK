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
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.repository.explorer.ExplorerObserver;
import arc.resource.calculator.repository.explorer.ExplorerRepository;
import arc.resource.calculator.repository.queue.QueueObserver;
import arc.resource.calculator.repository.queue.QueueRepository;
import arc.resource.calculator.ui.main.MainViewModel;
import arc.resource.calculator.util.ExceptionUtil;

public class ExplorerViewModel extends AndroidViewModel implements QueueObserver, ExplorerObserver {
    // TODO: Maintain filters?
    public static final String TAG = ExplorerViewModel.class.getSimpleName();

    private MutableLiveData<String> mSnackBarMessage = new MutableLiveData<>();
    private MutableLiveData<ExplorerViewModelState> mViewModelState = new MutableLiveData<>();

    private ExceptionObservable mExceptionRepository = ExceptionObservable.getInstance();
    private QueueRepository mQueueRepository = QueueRepository.getInstance();
    private ExplorerRepository mExplorerRepository = ExplorerRepository.getInstance();

    private MutableLiveData<List<ExplorerItem>> explorerItemList = mExplorerRepository.getExplorerItemList();

    public ExplorerViewModel(@NonNull Application application) {
        super(application);

        registerListeners();
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        unregisterListeners();
    }

    public void showSnackBarMessage(String message) {
        setSnackBarMessage(message);
    }

    MutableLiveData<String> getSnackBarMessage() {
        return mSnackBarMessage;
    }

    private void setSnackBarMessage(String s) {
        mSnackBarMessage.postValue(s);
    }

    MutableLiveData<ExplorerViewModelState> getViewModelState() {
        return mViewModelState;
    }

    private void setViewModelState(ExplorerViewModelState viewModelState) {
        mViewModelState.setValue(viewModelState);
    }


    public MutableLiveData<List<ExplorerItem>> getExplorerItemList() {
        return explorerItemList;
    }

    public void setExplorerItemList(List<ExplorerItem> explorerItemList) {
        this.explorerItemList = explorerItemList;
    }

    private void registerListeners() {
        Log.d(TAG, "registerListeners: " + this);
        mQueueRepository.addObserver(TAG, this);
        mExplorerRepository.addObserver(TAG, this);
    }

    private void unregisterListeners() {
        Log.d(TAG, "unregisterListeners: " + this);
        mQueueRepository.removeObserver(TAG);
        mExplorerRepository.removeObserver(TAG);

    }

    long getItemId(int position) {
        if (mExplorerRepository.isStation(position)) {
            return mExplorerRepository.getStation(position).getId();
        }

        if (mExplorerRepository.isCategory(position)) {
            return mExplorerRepository.getCategory(position).getId();
        }

        return mExplorerRepository.getCraftableByGlobalPosition(position).getId();
    }

    void handleViewHolderClick(int position) {
        try {
            if (mExplorerRepository.isCraftable(position)) {
                // TODO: 3/22/2020 navigate to detail fragment using navigation host controller
                MainViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) getApplication()).get(MainViewModel.class);
                viewModel.setDialogFragment(mExplorerRepository.getCraftableByGlobalPosition(position));
            } else if (mExplorerRepository.isCategory(position)) {
                mExplorerRepository.changeCategory(position);
            } else if (mExplorerRepository.isStation(position)) {
                mExplorerRepository.changeStation(position);
            }
        } catch (ExceptionUtil.CursorEmptyException | ExceptionUtil.CursorNullException e) {
            mExceptionRepository.notifyFatalExceptionCaught(TAG, e);
        }
    }

    boolean handleViewHolderLongClick(int position) {
        return !mExplorerRepository.isCraftable(position);
    }

    void requestToUpdateEngramQuantity(long engramId, int quantity) {
        boolean updated = mQueueRepository.requestToUpdateQuantity(getApplication(), engramId, quantity);
        if (!updated) {
            String message = getApplication().getString(R.string.snackbar_message_edit_quantity_fail);
            setSnackBarMessage(message);
        }
    }

    void requestToRemoveEngram(long engramId) {
        boolean removed = mQueueRepository.requestToRemoveEngram(engramId);
        if (!removed) {
            String message = getApplication().getString(R.string.snackbar_message_item_removed_fail);
            setSnackBarMessage(message);
        }
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
