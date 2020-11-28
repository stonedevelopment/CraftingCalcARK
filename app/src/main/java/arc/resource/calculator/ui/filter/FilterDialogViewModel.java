package arc.resource.calculator.ui.filter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import arc.resource.calculator.db.entity.DlcEntity;
import arc.resource.calculator.model.ui.InteractiveGameViewModel;

public class FilterDialogViewModel extends InteractiveGameViewModel {
    private final FilterDialogRepository repository;

    public FilterDialogViewModel(@NonNull Application application) {
        super(application);
        repository = new FilterDialogRepository(application);
    }

    public LiveData<List<DlcEntity>> getDlcListLiveData() {
        return repository.getDlcList(getGameEntityId());
    }

    public LiveData<List<DlcEntity>> getDlcTotalConversionListLiveData() {
        return repository.getDlcTotalConversionList(getGameEntityId());
    }
}