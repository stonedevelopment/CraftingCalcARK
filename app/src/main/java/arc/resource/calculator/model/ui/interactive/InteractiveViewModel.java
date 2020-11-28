package arc.resource.calculator.model.ui.interactive;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.util.PrefsUtil;

public class InteractiveViewModel extends AndroidViewModel {
    public static final String TAG = InteractiveViewModel.class.getCanonicalName();

    private final MutableLiveData<Boolean> isLoadingEvent = new MutableLiveData<>();
    private final SingleLiveEvent<String> snackBarMessageEvent = new SingleLiveEvent<>();

    private PrefsUtil prefs;

    public InteractiveViewModel(@NonNull Application application) {
        super(application);
    }

    public void setup(FragmentActivity activity) {
        setupPrefs(activity);
        setupEvents();
    }

    protected void setupEvents() {
        //  I do nothing.
    }

    protected void start() {
        //  I do nothing.
    }

    private void setupPrefs(FragmentActivity activity) {
        prefs = PrefsUtil.getInstance(activity);
    }

    protected PrefsUtil getPrefs() {
        return prefs;
    }

    public MutableLiveData<Boolean> getLoadingEvent() {
        return isLoadingEvent;
    }

    public void setIsLoading(boolean isLoading) {
        isLoadingEvent.setValue(isLoading);
    }

    public SingleLiveEvent<String> getSnackBarMessageEvent() {
        return snackBarMessageEvent;
    }

    public void setSnackBarMessage(String message) {
        snackBarMessageEvent.setValue(message);
    }
}