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
import androidx.lifecycle.MutableLiveData;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.repository.explorer.ExplorerObserver;
import arc.resource.calculator.repository.explorer.ExplorerRepository;
import arc.resource.calculator.repository.queue.QueueObserver;
import arc.resource.calculator.repository.queue.QueueRepository;

enum ExplorerViewModelState {
    POPULATING, POPULATED, EMPTY
}

class ExplorerViewModel extends AndroidViewModel implements ExceptionObservable.Observer, QueueObserver, ExplorerObserver {
    // TODO: Maintain filters?
    public static final String TAG = ExplorerViewModel.class.getSimpleName();

    private MutableLiveData<String> mSnackBarMessage = new MutableLiveData<>();
    private MutableLiveData<ExplorerViewModelState> mViewModelState = new MutableLiveData<>();

    private ExplorerRepository mExplorerRepository = ExplorerRepository.getInstance();
    private QueueRepository mQueueRepository = QueueRepository.getInstance();

    public ExplorerViewModel(@NonNull Application application) {
        super(application);
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

    void registerListeners() {
        // TODO: 1/26/2020 Do we need to register/unregister listeners with each lifestyle change?
        ExceptionObservable.getInstance().registerObserver(this);
        QueueRepository.getInstance().addObserver(TAG, this);
        ExplorerRepository.getInstance().addObserver(TAG, this);
    }

    void unregisterListeners() {
        mQueueRepository.removeObserver(TAG);
        ExceptionObservable.getInstance().unregisterObserver();
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

    @Override
    public void onException(String tag, Exception e) {
        // TODO: 1/25/2020 handle exception
        setSnackBarMessage("An error occurred.");
    }

    @Override
    public void onFatalException(String tag, Exception e) {
        // TODO: 1/25/2020 handle fatal exception
        setSnackBarMessage("A fatal error occurred.");
    }

}
