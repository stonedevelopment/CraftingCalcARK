package arc.resource.calculator.model.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import arc.resource.calculator.db.entity.GameEntity;
import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.ui.main.MainViewModel;

public class InteractiveViewModel extends AndroidViewModel {
    private final SingleLiveEvent<Boolean> isLoadingEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> snackBarMessageEvent = new SingleLiveEvent<>();

    private LiveData<GameEntity> gameEntityLiveData;

    public InteractiveViewModel(@NonNull Application application) {
        super(application);
    }

    public void injectViewModels(FragmentActivity fragment) {
        MainViewModel mainViewModel = new ViewModelProvider(fragment).get(MainViewModel.class);
        gameEntityLiveData = mainViewModel.getGameEntityEvent();
        gameEntityLiveData.observe(fragment, gameEntity -> handleGameEntityLiveData());
    }

    public void handleGameEntityLiveData() {
        //  do nothing
    }

    public LiveData<GameEntity> getGameEntityLiveData() {
        return gameEntityLiveData;
    }

    public SingleLiveEvent<String> getSnackBarMessageEvent() {
        return snackBarMessageEvent;
    }

    protected void setSnackBarMessage(String message) {
        snackBarMessageEvent.setValue(message);
    }

    public void sendMessageToSnackBar(String message) {
        setSnackBarMessage(message);
    }

    public boolean isLoading() {
        return isLoadingEvent.getValue() == null ? false : isLoadingEvent.getValue();
    }

    public SingleLiveEvent<Boolean> getLoadingEvent() {
        return isLoadingEvent;
    }

    public void setIsLoading(boolean isLoading) {
        isLoadingEvent.setValue(isLoading);
    }
}
