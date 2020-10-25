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

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.model.SingleLiveEvent;

/**
 * ViewModel for MainActivity
 */
public class MainViewModel extends AndroidViewModel {
    private final MainRepository mRepository;
    private MutableLiveData<Intent> mStartActivityForResultTrigger = new MutableLiveData<>();
    private SingleLiveEvent<String> mSnackBarMessageEvent = new SingleLiveEvent<>();
    private LiveData<GameEntity> mGameEntityEvent;
    private SingleLiveEvent<Boolean> mIsLoadingEvent = new SingleLiveEvent<>();
    private int mNavigationPosition;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = new MainRepository(application);
        setIsLoading(true);
        mGameEntityEvent = mRepository.getGameEntity();
    }

    MutableLiveData<Intent> getStartActivityForResultTrigger() {
        return mStartActivityForResultTrigger;
    }

    public void startActivityForResult(Intent intent) {
        mStartActivityForResultTrigger.setValue(intent);
    }

    int getNavigationPosition() {
        return mNavigationPosition;
    }

    void setNavigationPosition(int position) {
        mNavigationPosition = position;
    }

    SingleLiveEvent<String> getSnackBarMessageEvent() {
        return mSnackBarMessageEvent;
    }

    void setSnackBarMessage(String message) {
        mSnackBarMessageEvent.postValue(message);
    }

    public SingleLiveEvent<Boolean> getLoadingEvent() {
        return mIsLoadingEvent;
    }

    void setIsLoading(boolean isLoading) {
        mIsLoadingEvent.setValue(isLoading);
    }

    LiveData<GameEntity> getGameEntityEvent() {
        return mGameEntityEvent;
    }
}