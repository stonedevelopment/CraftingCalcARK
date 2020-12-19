package arc.resource.calculator.model.ui.interactive;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.util.PrefsUtil;

import static arc.resource.calculator.model.ui.interactive.InteractiveLoadState.Error;
import static arc.resource.calculator.model.ui.interactive.InteractiveLoadState.Loaded;
import static arc.resource.calculator.model.ui.interactive.InteractiveLoadState.Loading;

public class InteractiveViewModel extends AndroidViewModel {
    public static final String TAG = InteractiveViewModel.class.getCanonicalName();

    private final MutableLiveData<InteractiveLoadState> isLoadingEvent = new MutableLiveData<>();
    private final SingleLiveEvent<String> snackBarMessageEvent = new SingleLiveEvent<>();

    private PrefsUtil prefs;

    public InteractiveViewModel(@NonNull Application application) {
        super(application);
    }

    public void setup(FragmentActivity activity) {
        setupPrefs(activity);
        setLoadState(Loading);
    }

    public void start() {
        setLoadState(Loaded);
    }

    public void error(String tag, String message) {
        setLoadState(Error);
        Log.e(tag, message);
    }

    private void setupPrefs(FragmentActivity activity) {
        prefs = PrefsUtil.getInstance(activity);
    }

    protected PrefsUtil getPrefs() {
        return prefs;
    }

    public MutableLiveData<InteractiveLoadState> getLoadingEvent() {
        return isLoadingEvent;
    }

    private void setLoadState(InteractiveLoadState loadState) {
        isLoadingEvent.setValue(loadState);
    }

    public SingleLiveEvent<String> getSnackBarMessageEvent() {
        return snackBarMessageEvent;
    }

    public void setSnackBarMessage(String message) {
        snackBarMessageEvent.setValue(message);
    }
}