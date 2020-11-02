package arc.resource.calculator.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class InteractiveViewModel extends AndroidViewModel {
    private final SingleLiveEvent<Boolean> isLoadingEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> snackBarMessageEvent = new SingleLiveEvent<>();

    public InteractiveViewModel(@NonNull Application application) {
        super(application);
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
