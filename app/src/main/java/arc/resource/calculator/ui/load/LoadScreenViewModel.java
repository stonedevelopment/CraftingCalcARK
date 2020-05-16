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

import java.util.List;

import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.ui.load.check_version.versioning.Versioning;

public class LoadScreenViewModel extends AndroidViewModel {
    public static final String TAG = LoadScreenViewModel.class.getCanonicalName();

    private SingleLiveEvent<LoadSceenEvent> loadScreenEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<String> statusMessageEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Integer> progressEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Integer> progressTotalEvent = new SingleLiveEvent<>();

    private long startTimeInMillis;
    private int loadScreenEventIndex = 0;
    private List<Versioning> versioningList;

    public LoadScreenViewModel(@NonNull Application application) {
        super(application);

        initialize();
    }

    private void initialize() {
        setLoadScreenEvent(LoadSceenEvent.Initialize);
        setStartTime();
        setProgressTotal(LoadSceenEvent.values().length);
    }

    SingleLiveEvent<LoadSceenEvent> getLoadScreenEvent() {
        return loadScreenEvent;
    }

    private void setLoadScreenEvent(LoadSceenEvent event) {
        loadScreenEvent.setValue(event);
    }

    void nextLoadScreenEvent() {
        LoadSceenEvent[] events = LoadSceenEvent.values();
        Log.d(TAG, "nextLoadScreenEvent: " + events[loadScreenEventIndex] + ", " + loadScreenEventIndex);

        loadScreenEventIndex++;

        setProgress(loadScreenEventIndex);
        setLoadScreenEvent(events[loadScreenEventIndex]);
    }

    long getStartTime() {
        return startTimeInMillis;
    }

    private void setStartTime() {
        startTimeInMillis = System.currentTimeMillis();
    }

    SingleLiveEvent<String> getStatusMessageEvent() {
        return statusMessageEvent;
    }

    void updateStatusMessage(String message) {
        statusMessageEvent.setValue(message);
    }

    SingleLiveEvent<Integer> getProgressEvent() {
        return progressEvent;
    }

    private void setProgress(int progress) {
        progressEvent.setValue(progress);
    }

    SingleLiveEvent<Integer> getProgressTotalEvent() {
        return progressTotalEvent;
    }

    private void setProgressTotal(int progressTotal) {
        progressTotalEvent.setValue(progressTotal);
    }

    List<Versioning> getVersioningList() {
        return versioningList;
    }

    void setVersioningList(List<Versioning> versioningList) {
        this.versioningList = versioningList;
    }
}
