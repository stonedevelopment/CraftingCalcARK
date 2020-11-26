package arc.resource.calculator.model.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import arc.resource.calculator.db.entity.DlcEntity;
import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.ui.main.MainViewModel;

public class InteractiveViewModel extends AndroidViewModel implements LoadingViewModel, SnackBarViewModel {

    private MainViewModel mainViewModel;

    public InteractiveViewModel(@NonNull Application application) {
        super(application);
    }

    public void injectDependencies(FragmentActivity fragment) {
        setIsLoading(true);
        mainViewModel = new ViewModelProvider(fragment).get(MainViewModel.class);
        mainViewModel.getLoadingEvent().observe(fragment, mainViewModelLoaded -> start());
    }

    public GameEntity getGameEntity() {
        return mainViewModel.getGameEntity();
    }

    public DlcEntity getDlcEntity() {
        return mainViewModel.getDlcEntity();
    }

    protected void start() {
        setIsLoading(false);
    }
}