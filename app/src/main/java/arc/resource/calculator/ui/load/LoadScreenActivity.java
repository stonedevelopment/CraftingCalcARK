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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import java.util.List;

import arc.resource.calculator.FirstUseActivity;
import arc.resource.calculator.R;
import arc.resource.calculator.ui.load.check_version.CheckVersionListener;
import arc.resource.calculator.ui.load.check_version.CheckVersionTask;
import arc.resource.calculator.ui.load.check_version.versioning.Versioning;
import arc.resource.calculator.ui.load.update_database.UpdateDatabaseListener;
import arc.resource.calculator.ui.load.update_database.UpdateDatabaseTask;
import arc.resource.calculator.ui.main.MainActivity;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;

public class LoadScreenActivity extends AppCompatActivity {
    private static final String TAG = LoadScreenActivity.class.getSimpleName();
    private static final long DELAY_MILLIS = 1500;
    private LoadScreenViewModel mViewModel;
    private PrefsUtil prefsUtil;
    private TextView mTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        setupViews();
        setupPrefs();
        setupViewModel();
    }

    private void setupViews() {
        ImageView imageView = findViewById(R.id.content_init_image_view);

        final String imagePath = "file:///android_asset/splash.png";
        Picasso.with(this)
                .load(imagePath)
                .error(R.drawable.placeholder_empty)
                .placeholder(R.drawable.placeholder_empty)
                .into(imageView);

        mTextView = findViewById(R.id.content_init_status_text);
        mProgressBar = findViewById(R.id.content_init_progress_bar);
    }

    private void setupPrefs() {
        prefsUtil = PrefsUtil.getInstance(getApplicationContext());
    }

    private void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(LoadScreenViewModel.class);
        mViewModel.getLoadScreenEvent().observe(this, this::eventUpdate);
        mViewModel.getStatusMessageEvent().observe(this, statusMessage -> mTextView.setText(statusMessage));
        mViewModel.getProgressEvent().observe(this, progress -> mProgressBar.setProgress(progress));
        mViewModel.getProgressTotalEvent().observe(this, total -> mProgressBar.setMax(total));
    }

    private void nextLoadScreenEvent() {
        mViewModel.nextLoadScreenEvent();
    }

    private void eventUpdate(LoadScreenEvent event) {
        switch (event) {
            case Initialize:
                updateStatusMessage(getString(R.string.initialization_event_init));
                nextLoadScreenEvent();
                break;
            case CheckVersion:
                new CheckVersionTask(getApplicationContext(), prefsUtil, new CheckVersionListener() {
                    @Override
                    public void onError(Exception e) {
                        handleExceptionWithMessage(getString(R.string.initialization_event_check_version_error), e);
                    }

                    @Override
                    public void onStart() {
                        updateStatusMessage(getString(R.string.initialization_event_check_version_start));
                    }

                    @Override
                    public void onFinish(List<Versioning> versioningList) {
                        int total = versioningList.size();
                        if (total >= 1) {
                            if (total > 1) {
                                updateStatusMessage(String.format(getString(R.string.initialization_event_check_version_new_version_multiple), total));
                            } else {
                                updateStatusMessage(getString(R.string.initialization_event_check_version_new_version_single));
                            }
                        } else {
                            updateStatusMessage(getString(R.string.initialization_event_check_version_finished_without_update));
                        }
                        mViewModel.setVersioningList(versioningList);
                        nextLoadScreenEvent();
                    }
                }).execute();
                break;
            case UpdateDatabase:
                new UpdateDatabaseTask(getApplicationContext(), prefsUtil, mViewModel.getVersioningList(), new UpdateDatabaseListener() {
                    @Override
                    public void onError(Exception e) {
                        handleExceptionWithMessage(getString(R.string.initialization_event_update_database_error), e);
                    }

                    @Override
                    public void onStart() {
                        updateStatusMessage(getString(R.string.initialization_event_update_database_started));
                    }

                    @Override
                    public void onProgressUpdate(Versioning versioning, int progress, int progressTotal) {
                        updateStatusMessage(String.format(getString(R.string.initialization_event_update_database_progress_update), versioning.getName(), progress, progressTotal));
                    }

                    @Override
                    public void onFinish() {
                        updateStatusMessage(getString(R.string.initialization_event_update_database_finished));
                        nextLoadScreenEvent();
                    }
                }).execute();
                break;
            case Finalize:
                updateStatusMessage(formatMessageWithElapsedTime(getString(R.string.initialization_finish_event)));
                finishActivity();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FirstUseActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                preferences.edit().putBoolean(PrefsUtil.FirstUseKey, false).apply();
            }
        }

        startMainActivity();
    }

    private void handleExceptionWithMessage(String message, Exception e) {
        updateStatusMessage(message);
        ExceptionUtil.SendErrorReport(TAG, e);
        Crashlytics.getInstance().crash();
    }

    private void handleFatalException(Exception e) {

    }

    @UiThread
    private void updateStatusMessage(final String message) {
        mViewModel.updateStatusMessage(message);
    }

    private String formatMessageWithElapsedTime(String message) {
        long endTime = System.currentTimeMillis();
        long elapsedMilliseconds = endTime - mViewModel.getStartTime();
        double elapsedSeconds = elapsedMilliseconds / 1000.0;

        String elapsedMessage = String.format(getString(R.string.load_activity_status_message_elapsed_format), Double.toString(elapsedSeconds));

        return String.format(getString(R.string.load_activity_status_message_format_with_elapsed_time), message, elapsedMessage);
    }

    private void finishWithDelay(Runnable runnable) {
        new Handler().postDelayed(runnable, DELAY_MILLIS);
    }

    private void finishActivity() {
        finishWithDelay(() -> {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            boolean isFirst = prefs.getBoolean(PrefsUtil.FirstUseKey, PrefsUtil.FirstUseDefaultValue);
//                boolean isFirst = true;

            if (isFirst) startFirstUseActivity();
            else startMainActivity();
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        finish();
    }

    private void startFirstUseActivity() {
        Intent intent = new Intent(getApplicationContext(), FirstUseActivity.class);
        startActivityForResult(intent, FirstUseActivity.REQUEST_CODE);
    }
}