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

package arc.resource.calculator.ui.load;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import arc.resource.calculator.model.SingleLiveEvent;

public class LoadScreenViewModel extends AndroidViewModel {
    public static final String TAG = LoadScreenViewModel.class.getCanonicalName();

    private SingleLiveEvent<LoadSceenEvent> loadScreenEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Long> startTimeEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<String> statusMessageEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Integer> progressEvent = new SingleLiveEvent<>();

    public LoadScreenViewModel(@NonNull Application application) {
        super(application);

        initialize();
    }

    private void initialize() {
        setLoadScreenEvent(LoadSceenEvent.Initialize);
        setStartTime();
    }

    SingleLiveEvent<LoadSceenEvent> getLoadScreenEvent() {
        return loadScreenEvent;
    }

    private void setLoadScreenEvent(LoadSceenEvent event) {
        loadScreenEvent.setValue(event);
    }

    void nextLoadScreenEvent() {
        int index = loadScreenEvent.getValue().ordinal();
        LoadSceenEvent[] events = LoadSceenEvent.values();
        Log.d(TAG, "nextLoadScreenEvent: " + events[index] + ", " + index);
        setLoadScreenEvent(events[++index]);
    }

    public SingleLiveEvent<Long> getStartTimeEvent() {
        return startTimeEvent;
    }

    private void setStartTime() {
        long currentTime = System.currentTimeMillis();
        startTimeEvent.setValue(currentTime);
    }

    SingleLiveEvent<String> getStatusMessageEvent() {
        return statusMessageEvent;
    }

    void setStatusMessage(String message) {
        statusMessageEvent.setValue(message);
    }

    SingleLiveEvent<Integer> getProgressEvent() {
        return progressEvent;
    }

    public void setProgress(int progressEvent) {
        this.progressEvent.setValue(progressEvent);
    }
}
