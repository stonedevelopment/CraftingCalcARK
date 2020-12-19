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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    private final SingleLiveEvent<Boolean> gameListDialogTrigger = new SingleLiveEvent<>();

    private final LiveData<List<GameEntity>> gameListLiveData;
    private final MutableLiveData<GameEntity> gameEntityLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new MainRepository(application);
        gameListLiveData = repository.getGameEntityList();
    }

    @Override
    public void setup(FragmentActivity activity) {
        super.setup(activity);
        observe(activity);
    }

    private void observe(FragmentActivity activity) {
        String gameId = getPrefs().getGameIdPreference();
        if (gameId == null) {
            Log.d(TAG, "observe: gameId is null.");
            //  trigger game list observations
            gameListLiveData.observe(activity, gameEntityList -> {
                if (gameEntityList.size() > 1) {
                    //  show dialog for user to choose
                } else {
                    //  default to only game
                    GameEntity gameEntity = gameEntityList.get(0);
                    saveGameEntity(gameEntity);
                    setGameEntityLiveData(gameEntity);
                }
            });
        } else {
            Log.d(TAG, "observe: gameId is saved! " + gameId);
            //  trigger game load event observations
            fetchGameEntity(gameId).observe(activity, this::setGameEntityLiveData);
        }
    }

    public MutableLiveData<GameEntity> getGameEntityLiveData() {
        return gameEntityLiveData;
    }

    private void setGameEntityLiveData(GameEntity gameEntity) {
        gameEntityLiveData.setValue(gameEntity);
    }

    public LiveData<List<GameEntity>> getGameEntityListLiveData() {
        return repository.getGameEntityList();
    }

    public SingleLiveEvent<Boolean> getGameListDialogTrigger() {
        return gameListDialogTrigger;
    }

    private LiveData<GameEntity> fetchGameEntity(String gameId) {
        return repository.getGameEntity(gameId);
    }

    public void saveGameEntity(GameEntity gameEntity) {
        getPrefs().saveGameIdPreference(gameEntity.getUuid());
    }

    private void showGameListDialog() {
        gameListDialogTrigger.setValue(true);
    }
}