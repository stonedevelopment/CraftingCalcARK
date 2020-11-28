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

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.model.ui.interactive.InteractiveViewModel;

/**
 * ViewModel for MainActivity
 */
public class MainViewModel extends InteractiveViewModel {
    public static final String TAG = MainViewModel.class.getCanonicalName();

    private final MainRepository repository;
    private final SingleLiveEvent<String> gameEntityUuidEvent = new SingleLiveEvent<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new MainRepository(application);
    }

    @Override
    public void setup(FragmentActivity activity) {
        super.setup(activity);
        setIsLoading(true);
    }

    protected void setupComplete() {
        setIsLoading(false);
    }

    public LiveData<GameEntity> getGameEntityLiveData() {
        return Transformations.switchMap(gameEntityUuidEvent, repository::getGameEntity);
    }

    public LiveData<List<GameEntity>> getGameEntityListLiveData() {
        return repository.getGameEntityList();
    }

    public void fetchGameEntity(String gameId) {
        gameEntityUuidEvent.setValue(gameId);
    }

    public void saveGameEntity(GameEntity gameEntity) {
        getPrefs().saveGameIdPreference(gameEntity.getUuid());
    }
}