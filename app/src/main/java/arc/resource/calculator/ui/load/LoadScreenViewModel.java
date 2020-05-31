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

import arc.resource.calculator.R;
import arc.resource.calculator.model.SingleLiveEvent;
import arc.resource.calculator.ui.load.check_version.CheckVersionListener;
import arc.resource.calculator.ui.load.check_version.CheckVersionTask;
import arc.resource.calculator.ui.load.check_version.versioning.Versioning;
import arc.resource.calculator.ui.load.update_database.UpdateDatabaseListener;
import arc.resource.calculator.ui.load.update_database.UpdateDatabaseTask;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;

public class LoadScreenViewModel extends AndroidViewModel {
    public static final String TAG = LoadScreenViewModel.class.getCanonicalName();

    private SingleLiveEvent<LoadScreenState> loadScreenStateEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<String> statusMessageEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Integer> progressEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Integer> progressTotalEvent = new SingleLiveEvent<>();

    private long startTimeInMillis;
    private List<Versioning> versioningList;

    public LoadScreenViewModel(@NonNull Application application) {
        super(application);

        startInitializeEvent();
    }

    SingleLiveEvent<LoadScreenState> getLoadScreenStateEvent() {
        return loadScreenStateEvent;
    }

    private void setLoadScreenStateEvent(LoadScreenState event) {
        loadScreenStateEvent.setValue(event);
    }

    private LoadScreenState getLoadScreenState() {
        return loadScreenStateEvent.getValue();
    }

    private void updateLoadScreenEvent(LoadScreenState event) {
        setLoadScreenStateEvent(event);
        handleLoadScreenState(event);
    }

    private long getStartTime() {
        return startTimeInMillis;
    }

    private void setStartTime() {
        startTimeInMillis = System.currentTimeMillis();
    }

    SingleLiveEvent<String> getStatusMessageEvent() {
        return statusMessageEvent;
    }

    private void updateStatusMessage(LoadScreenState event, String message) {
        Log.d(TAG, String.format("updateStatusMessage: %1$s/%2$s", event.toString(), message));

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

    private List<Versioning> getVersioningList() {
        return versioningList;
    }

    private void setVersioningList(List<Versioning> versioningList) {
        this.versioningList = versioningList;
    }

    private boolean hasVersions() {
        return versioningList.size() > 0;
    }

    private String getString(int stringResource) {
        return getApplication().getString(stringResource);
    }

    private void handleLoadScreenState(LoadScreenState state) {
        switch (state) {
            case Initialize:
                updateStatusMessage(state, getString(R.string.initialization_event_init));
                setStartTime();
                setProgress(0);
                setProgressTotal(LoadScreenState.values().length);
                startCheckVersionEvent();
                break;
            case CheckVersion:
                new CheckVersionTask(getApplication(), PrefsUtil.getInstance(getApplication()), new CheckVersionListener() {
                    @Override
                    public void onError(Exception e) {
                        String tag = CheckVersionTask.TAG;
                        String message = getString(R.string.initialization_event_check_version_error);
                        handleFatalException(tag, message, e);
                    }

                    @Override
                    public void onStart() {
                        updateStatusMessage(state, getString(R.string.initialization_event_check_version_start));
                    }

                    @Override
                    public void onFinish(List<Versioning> versioningList) {
                        setVersioningList(versioningList);

                        int total = versioningList.size();
                        if (total >= 1) {
                            if (total > 1) {
                                updateStatusMessage(state, String.format(getString(R.string.initialization_event_check_version_new_version_multiple), total));
                            } else {
                                updateStatusMessage(state, getString(R.string.initialization_event_check_version_new_version_single));
                            }
                            startUpdateDatabaseEvent();
                        } else {
                            updateStatusMessage(state, getString(R.string.initialization_event_check_version_finished_without_update));
                            startFinalizeEvent();
                        }
                    }
                }).execute();
                break;
            case UpdateDatabase:
                new UpdateDatabaseTask(getApplication(), versioningList, new UpdateDatabaseListener() {
                    @Override
                    public void onError(Exception e) {
                        String tag = UpdateDatabaseTask.TAG;
                        String message = getString(R.string.initialization_event_update_database_error);
                        handleFatalException(tag, message, e);
                    }

                    @Override
                    public void onStart() {
                        updateStatusMessage(state, getString(R.string.initialization_event_update_database_started));
                    }

                    @Override
                    public void onProgressUpdate(Versioning versioning, int progress, int progressTotal) {
                        updateStatusMessage(state, String.format(getString(R.string.initialization_event_update_database_progress_update), versioning.getName(), progress, progressTotal));
                    }

                    @Override
                    public void onFinish() {
                        updateStatusMessage(state, getString(R.string.initialization_event_update_database_finished));
                        startSavePrefsEvent();
                    }
                }).execute();
                break;
            case SavePrefs:
                PrefsUtil prefsUtil = PrefsUtil.getInstance(getApplication());
                for (Versioning versioning : versioningList) {
                    prefsUtil.setVersionByUUID(versioning.getUuid(), versioning.getVersion());
                }
                prefsUtil.setDidUpdate(true);

                startFinalizeEvent();
                break;
            case Finalize:
                updateStatusMessage(state, formatMessageWithElapsedTime(getString(R.string.initialization_finish_event)));
                break;
        }
    }

    private void startInitializeEvent() {
        updateLoadScreenEvent(LoadScreenState.Initialize);
    }

    private void startCheckVersionEvent() {
        updateLoadScreenEvent(LoadScreenState.CheckVersion);
    }

    private void startUpdateDatabaseEvent() {
        updateLoadScreenEvent(LoadScreenState.UpdateDatabase);
    }

    private void startSavePrefsEvent() {
        updateLoadScreenEvent(LoadScreenState.SavePrefs);
    }

    private void startFinalizeEvent() {
        updateLoadScreenEvent(LoadScreenState.Finalize);
    }

    private void handleFatalException(String tag, String message, Exception e) {
        ExceptionUtil.SendErrorReportWithMessage(tag, message, e, true);
    }

    private String formatMessageWithElapsedTime(String message) {
        long endTime = System.currentTimeMillis();
        long elapsedMilliseconds = endTime - getStartTime();
        double elapsedSeconds = elapsedMilliseconds / 1000.0;

        String elapsedMessage = String.format(getString(R.string.load_activity_status_message_elapsed_format), Double.toString(elapsedSeconds));

        return String.format(getString(R.string.load_activity_status_message_format_with_elapsed_time), message, elapsedMessage);
    }

}
