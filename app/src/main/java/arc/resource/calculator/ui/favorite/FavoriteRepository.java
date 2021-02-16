package arc.resource.calculator.ui.favorite;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.dao.dlc.DlcFavoriteDao;
import arc.resource.calculator.db.dao.primary.FavoriteDao;
import arc.resource.calculator.db.entity.primary.FavoriteEntity;
import arc.resource.calculator.model.ui.interactive.InteractiveRepository;

public class FavoriteRepository extends InteractiveRepository {
    private final FavoriteDao favoriteDao;
    private final DlcFavoriteDao dlcFavoriteDao;

    public FavoriteRepository(Application application) {
        super(application);
        favoriteDao = getAppDatabase().favoriteDao();
        dlcFavoriteDao = getAppDatabase().dlcFavoriteDao();
    }

    protected FavoriteDao getFavoriteDao() {
        return favoriteDao;
    }

    protected DlcFavoriteDao getDlcFavoriteDao() {
        return dlcFavoriteDao;
    }

    public LiveData<List<FavoriteEntity>> getFavoritesList(String gameId) {
        return favoriteDao.getFavoriteList(gameId);
    }

    public LiveData<FavoriteEntity> getFavorite(String uuid) {
        return favoriteDao.getFavorite(uuid);
    }

    public LiveData<FavoriteEntity> getFavoriteBySourceId(String sourceId) {
        return favoriteDao.getFavoriteBySourceId(sourceId);
    }

    public void addFavorite(FavoriteEntity entity) {
        getFavoriteDao().insert(entity);
    }

    public void removeFavorite(FavoriteEntity entity) {
        getFavoriteDao().delete(entity);
    }
}
