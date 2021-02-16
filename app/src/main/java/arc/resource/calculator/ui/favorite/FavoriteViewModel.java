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

package arc.resource.calculator.ui.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.entity.primary.FavoriteEntity;
import arc.resource.calculator.model.ui.InteractiveGameViewModel;
import arc.resource.calculator.ui.explorer.model.ExplorerItem;
import arc.resource.calculator.ui.favorite.model.FavoriteItem;

public class FavoriteViewModel extends InteractiveGameViewModel {

    private LiveData<List<FavoriteItem>> favoritesListLiveData = new MutableLiveData<>();

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void setupRepository(Application application) {
        setRepository(new FavoriteRepository(application));
    }

    @Override
    public void start() {
        super.start();
        transformFavorites();
    }

    @Override
    protected FavoriteRepository getRepository() {
        return (FavoriteRepository) super.getRepository();
    }

    public LiveData<List<FavoriteItem>> getFavoritesListLiveData() {
        return favoritesListLiveData;
    }

    public LiveData<Boolean> checkIfFavorite(@NonNull String uuid) {
        //noinspection Convert2MethodRef
        return Transformations.map(getRepository().getFavoriteBySourceId(uuid),
                favoriteEntity -> favoriteEntity == null);
    }

    public void handleFavoriteButtonClick(ExplorerItem explorerItem) {
        getRepository().addFavorite(FavoriteEntity.fromExplorerItem(explorerItem));
    }

    private void transformFavorites() {
        favoritesListLiveData = Transformations.map(getRepository().getFavoritesList(getGameEntityId()), favoriteEntityList -> {
            List<FavoriteItem> outList = new ArrayList<>();
            for (FavoriteEntity favoriteEntity : favoriteEntityList) {
                outList.add(new FavoriteItem(favoriteEntity.getUuid(),
                        favoriteEntity.getName(),
                        favoriteEntity.getViewType(),
                        favoriteEntity.getGameId()));
            }
            return outList;
        });
    }
}