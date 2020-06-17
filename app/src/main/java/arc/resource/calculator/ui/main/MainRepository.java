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

import android.content.Context;

import androidx.lifecycle.LiveData;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.GameDao;
import arc.resource.calculator.db.entity.primary.GameEntity;

public class MainRepository {
    private final GameDao mDao;

    MainRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mDao = db.gameDao();
    }

    LiveData<GameEntity> getGameEntity() {
        return mDao.getGame("704d5a67-87fe-4d3c-8f9c-e0c66698a0eb");
    }
}
