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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.model.ui.InteractiveViewModel;

/**
 * ViewModel for MainActivity
 */
public class MainViewModel extends InteractiveViewModel {
    private final MainRepository repository;
    private MutableLiveData<Intent> mStartActivityForResultTrigger = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = new MainRepository(application);
    }

    MutableLiveData<Intent> getStartActivityForResultTrigger() {
        return mStartActivityForResultTrigger;
    }

    public void startActivityForResult(Intent intent) {
        mStartActivityForResultTrigger.setValue(intent);
    }

    public LiveData<GameEntity> getGameEntityEvent() {
        return repository.getGameEntity();
    }
}