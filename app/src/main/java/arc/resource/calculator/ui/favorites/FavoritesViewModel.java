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

package arc.resource.calculator.ui.favorites;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.db.entity.primary.FavoriteEntity;
import arc.resource.calculator.model.ui.InteractiveGameViewModel;
import arc.resource.calculator.ui.favorites.model.FavoritesItem;

public class FavoritesViewModel extends InteractiveGameViewModel {

    private LiveData<List<FavoritesItem>> favoritesList = new MutableLiveData<>();

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void setupRepository(Application application) {
        setRepository(new FavoritesRepository(application));
    }

    @Override
    public void start() {
        super.start();
        transformFavorites();
    }

    @Override
    protected FavoritesRepository getRepository() {
        return (FavoritesRepository) super.getRepository();
    }

    public LiveData<List<FavoritesItem>> getFavoritesList() {
        return favoritesList;
    }

    private void transformFavorites() {
        favoritesList = Transformations.map(getRepository().getFavoritesList(getGameEntityId()), favoriteEntityList -> {
            List<FavoritesItem> outList = new ArrayList<>();
            for (FavoriteEntity favoriteEntity : favoriteEntityList) {
                outList.add(new FavoritesItem(favoriteEntity.getUuid(),
                        favoriteEntity.getName(),
                        favoriteEntity.getViewType(),
                        favoriteEntity.getGameId()));
            }
            return outList;
        });
    }
}