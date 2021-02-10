package arc.resource.calculator.ui.favorites;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.dao.primary.FavoriteDao;
import arc.resource.calculator.db.entity.primary.FavoriteEntity;
import arc.resource.calculator.model.ui.interactive.InteractiveRepository;

public class FavoritesRepository extends InteractiveRepository {
    private final FavoriteDao favoriteDao;

    public FavoritesRepository(Application application) {
        super(application);
        favoriteDao = getAppDatabase().favoriteDao();
    }

    public LiveData<List<FavoriteEntity>> getFavoritesList(String gameId) {
        return favoriteDao.getFavoriteList(gameId);
    }
}
