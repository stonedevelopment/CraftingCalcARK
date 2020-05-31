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

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.ui.load.check_version.versioning.Versioning;
import arc.resource.calculator.util.ExceptionUtil;

public class LoadScreenViewModel extends AndroidViewModel {
    public static final String TAG = LoadScreenViewModel.class.getCanonicalName();

    private SingleLiveEvent<LoadScreenEvent> loadScreenEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<String> statusMessageEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Integer> progressEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Integer> progressTotalEvent = new SingleLiveEvent<>();

    private long startTimeInMillis;
    private int loadScreenEventIndex;
    private List<Versioning> versioningList;
    private boolean didUpdate = false;
    private boolean hasUpdate = false;

    public LoadScreenViewModel(@NonNull Application application) throws Exception {
        super(application);

        initialize();
    }

    private void initialize() throws Exception {
        setStartTime();
        setProgressTotal(LoadScreenEvent.values().length);
        nextLoadScreenEvent();
    }

    private void setLoadScreenIndex(int index) {
        loadScreenEventIndex = index;
    }

    SingleLiveEvent<LoadScreenEvent> getLoadScreenEvent() {
        return loadScreenEvent;
    }

    private void setLoadScreenEvent(LoadScreenEvent event) {
        loadScreenEvent.setValue(event);
    }

    private void updateLoadScreenEvent(LoadScreenEvent event) {
        setLoadScreenIndex(event.ordinal());
        setLoadScreenEvent(event);
    }

    void nextLoadScreenEvent() {
        LoadScreenEvent event = getLoadScreenEvent().getValue();

        if (event != null) {
            try {
                switch (event) {
                    case Initialize:
                        updateLoadScreenEvent(LoadScreenEvent.CheckVersion);
                        break;
                    case CheckVersion:
                        if (hasUpdate) {
                            updateLoadScreenEvent(LoadScreenEvent.UpdateDatabase);
                        } else {
                            updateLoadScreenEvent(LoadScreenEvent.Finalize);
                        }
                        break;
                    case UpdateDatabase:
                        updateLoadScreenEvent(LoadScreenEvent.Finalize);
                        break;
                    case Finalize:
                        throw new Exception("There are no other events after Finalize.");
                }
            } catch (Exception e) {
                ExceptionUtil.SendErrorReport(TAG, e);
                e.printStackTrace();
            }
        } else {
            updateLoadScreenEvent(LoadScreenEvent.Initialize);
        }
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

    @UiThread
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
