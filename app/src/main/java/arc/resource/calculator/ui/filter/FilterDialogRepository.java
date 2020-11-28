package arc.resource.calculator.ui.filter;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.AppDatabase;
import arc.resource.calculator.db.dao.DlcDao;
import arc.resource.calculator.db.entity.DlcEntity;

public class FilterDialogRepository {
    private final DlcDao dlcDao;

    FilterDialogRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        dlcDao = db.dlcDao();
    }

    LiveData<List<DlcEntity>> getDlcList(String gameId) {
        return dlcDao.getDlcList(gameId, false);
    }

    LiveData<List<DlcEntity>> getDlcTotalConversionList(String gameId) {
        return dlcDao.getDlcList(gameId, true);
    }
}
