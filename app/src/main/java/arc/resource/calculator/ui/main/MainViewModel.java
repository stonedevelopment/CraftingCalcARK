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

package arc.resource.calculator.ui.main;

import android.content.Intent;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import arc.resource.calculator.model.engram.DisplayEngram;

/**
 * ViewModel for MainActivity
 */
public class MainViewModel extends ViewModel {
    private MutableLiveData<Intent> mStartActivityForResultTrigger = new MutableLiveData<>();
    private MutableLiveData<Integer> mNavigationPosition = new MutableLiveData<>();
    private MutableLiveData<String> mSnackBarMessage = new MutableLiveData<>();
    private MutableLiveData<DisplayEngram> mShowDialogFragment = new MutableLiveData<>();

    MutableLiveData<Intent> getStartActivityForResultTrigger() {
        return mStartActivityForResultTrigger;
    }

    public void startActivityForResult(Intent intent) {
        mStartActivityForResultTrigger.setValue(intent);
    }

    MutableLiveData<Integer> getNavigationPosition() {
        return mNavigationPosition;
    }

    void setNavigationPosition(int position) {
        mNavigationPosition.setValue(position);
    }

    MutableLiveData<String> getSnackBarMessage() {
        return mSnackBarMessage;
    }

    void setSnackBarMessage(String message) {
        mSnackBarMessage.postValue(message);
    }

    MutableLiveData<DisplayEngram> getShowDialogFragment() {
        return mShowDialogFragment;
    }

    public void setDialogFragment(DisplayEngram id) {
        mShowDialogFragment.setValue(id);
    }

}