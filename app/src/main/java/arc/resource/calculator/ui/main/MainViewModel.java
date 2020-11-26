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
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import arc.resource.calculator.R;
import arc.resource.calculator.db.entity.DlcEntity;
import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.model.ui.LoadingViewModel;
import arc.resource.calculator.model.ui.SnackBarViewModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * ViewModel for MainActivity
 */
public class MainViewModel extends AndroidViewModel implements LoadingViewModel, SnackBarViewModel {
    private final MainRepository repository;
    private final SingleLiveEvent<Boolean> dataLoadedEvent = new SingleLiveEvent<>();

    private GameEntity gameEntity;
    private boolean gameEntityLoaded;
    private DlcEntity dlcEntity;
    private boolean dlcEntityLoaded;

    private MutableLiveData<Intent> mStartActivityForResultTrigger = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = new MainRepository(application);
    }

    public void injectDependencies(FragmentActivity fragmentActivity) {
        loadData(fragmentActivity);
    }

    MutableLiveData<Intent> getStartActivityForResultTrigger() {
        return mStartActivityForResultTrigger;
    }

    public void startActivityForResult(Intent intent) {
        mStartActivityForResultTrigger.setValue(intent);
    }

    public SingleLiveEvent<Boolean> getDataLoadedEvent() {
        return dataLoadedEvent;
    }

    private void setDataLoaded(boolean dataLoaded) {
        getDataLoadedEvent().setValue(dataLoaded);
    }

    public GameEntity getGameEntity() {
        return gameEntity;
    }

    private void setGameEntity(GameEntity gameEntity) {
        this.gameEntity = gameEntity;
    }

    private void setGameEntityLoaded(boolean gameEntityLoaded) {
        this.gameEntityLoaded = gameEntityLoaded;
    }

    private void handleGameEntity(GameEntity gameEntity) {
        setGameEntity(gameEntity);
        setGameEntityLoaded(true);
        checkDataLoaded();
    }

    public DlcEntity getDlcEntity() {
        return dlcEntity;
    }

    private void setDlcEntity(DlcEntity dlcEntity) {
        this.dlcEntity = dlcEntity;
    }

    private void setDlcEntityLoaded(boolean dlcEntityLoaded) {
        this.dlcEntityLoaded = dlcEntityLoaded;
    }

    private void handleDlcEntity(DlcEntity dlcEntity) {
        setDlcEntity(dlcEntity);
        setDlcEntityLoaded(true);
        checkDataLoaded();
    }

    private void loadData(FragmentActivity context) {
        setIsLoading(true);
        SharedPreferences prefs = context.getPreferences(MODE_PRIVATE);
        fetchGameEntity(prefs.getString(context.getString(R.string.pref_filter_game_id_key),
                context.getString(R.string.pref_filter_game_id_default_value)))
                .observe(context, this::handleGameEntity);
        fetchDlcEntity(prefs.getString(context.getString(R.string.pref_filter_dlc_id_key), null))
                .observe(context, this::handleDlcEntity);
    }

    private void checkDataLoaded() {
        if (gameEntityLoaded && dlcEntityLoaded) {
            setIsLoading(false);
        }
    }

    private LiveData<GameEntity> fetchGameEntity(String uuid) {
        setGameEntityLoaded(false);
        return repository.getGameEntity(uuid);
    }

    private LiveData<DlcEntity> fetchDlcEntity(String uuid) {
        setDlcEntityLoaded(false);
        return repository.getDlcEntity(uuid);
    }
}