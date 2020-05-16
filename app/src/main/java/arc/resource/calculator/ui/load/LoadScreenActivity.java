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
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import java.util.List;

import arc.resource.calculator.FirstUseActivity;
import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.repository.queue.QueueObserver;
import arc.resource.calculator.repository.queue.QueueRepository;
import arc.resource.calculator.ui.load.check_version.CheckVersionListener;
import arc.resource.calculator.ui.load.check_version.CheckVersionTask;
import arc.resource.calculator.ui.load.check_version.versioning.PrimaryVersioning;
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
    private Listener mListener;
    private String mNewVersion;
    private long startTime;
    private TextView mTextView;
    private ProgressBar mProgressBar;

    private PrimaryVersioning primaryVersioning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        setupViews();
        setupPrefs();
        setupViewModel();

        mListener = new Listener() {

            @Override
            public void onStartEvent() {
                Log.d(TAG, "onStartEvent(): " + mCurrentEvent);

                switch (mCurrentEvent) {

                    case UpdatePreferences:
                        // if database updated, save new version to preferences, reset categories back to default
                        if (mHasUpdate) {
                            updateStatusMessage(getString(R.string.initialization_pref_event_started));

                            prefsUtil.setVersionForPrimary(mNewVersion);
                            prefsUtil.saveCraftingQueueJSONString(null);
                            prefsUtil.saveToDefault();

                            updateStatusMessage(getString(R.string.initialization_pref_event_finished));
                        }

                        // trigger next event
                        mListener.onEndEvent();
                        break;

                    case CraftingQueue:
                        // create instance of QueueRepository
                        updateStatusMessage(getString(R.string.initialization_queue_event_started));

                        final QueueRepository queueRepository = QueueRepository.getInstance();
                        queueRepository.addObserver(TAG, new QueueObserver() {
                            @Override
                            public void onItemAdded(@NonNull QueueEngram engram) {
                                //  do nothing
                            }

                            @Override
                            public void onItemRemoved(@NonNull QueueEngram engram) {
                                //  do nothing
                            }

                            @Override
                            public void onItemChanged(@NonNull QueueEngram engram) {
                                //  do nothing
                            }

                            @Override
                            public void onQueueDataPopulating() {
                                updateStatusMessage(getString(R.string.initialization_queue_event_fetching));
                            }

                            @Override
                            public void onQueueDataPopulated() {
                                updateStatusMessage(getString(R.string.initialization_queue_event_finished));
                                queueRepository.removeObserver(TAG);
                                mListener.onEndEvent();
                            }

                            @Override
                            public void onQueueDataEmpty() {
                                updateStatusMessage(getString(R.string.initialization_queue_event_finished));
                                queueRepository.removeObserver(TAG);
                                mListener.onEndEvent();
                            }
                        });
                        queueRepository.init(getApplicationContext());
                        break;
                    case Finalize:
                        //  finalize any extra data for app

                        // trigger next event
                        mListener.onEndEvent();
                        break;
                }
            }

            @Override
            public void onFinish() {
                // say goodbye to user, onResume app
                updateStatusMessage(formatMessageWithElapsedTime(getString(R.string.initialization_finish_event)));

                finishActivity();
            }
        };

        mListener.onInit();
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

    private void nextLoadScreenEvent() {
        mViewModel.nextLoadScreenEvent();
    }

    private void eventUpdate(LoadSceenEvent event) {
        switch (event) {
            case Initialize:
                updateStatusMessage(getString(R.string.initialization_event_init));
                nextLoadScreenEvent();
                break;
            case CheckVersion:
                new CheckVersionTask(getApplicationContext(), prefsUtil, new CheckVersionListener() {
                    @Override
                    public void onError(Exception e) {
                        updateStatusMessage(getString(R.string.initialization_event_check_version_error));
                        ExceptionUtil.SendErrorReport(TAG, e);
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
                        updateStatusMessage(getString(R.string.initialization_event_update_database_error));
                        ExceptionUtil.SendErrorReport(TAG, e);
                    }

                    @Override
                    public void onStart() {
                        updateStatusMessage(getString(R.string.initialization_event_update_database_started));
                    }

                    @Override
                    public void onUpdate(Versioning versioning, int progress, int progressTotal) {
                        updateStatusMessage(String.format(getString(R.string.initialization_event_update_database_progress_update), versioning, progress, progressTotal));
                    }

                    @Override
                    public void onFinish() {
                        updateStatusMessage(getString(R.string.initialization_event_update_database_finished));
                        nextLoadScreenEvent();
                    }
                }).execute();
//                    new InitializationTask(getApplicationContext(), primaryVersioning, new InitializationTask.Listener() {
//                        @Override
//                        public void onError(Exception e) {
//                            // alert status window of error
//                            updateStatusMessage(getString(R.string.initialization_event_update_database_error));
//
//                            // trigger activity error event handler
//                            mListener.onError(e);
//                        }
//
//                        @Override
//                        public void onInit() {
//                            // alert status window that database initialization is initializing
////                                updateStatusMessages( getString( R.string.initialization_db_event_init ) );
//                        }
//
//                        @Override
//                        public void onStart() {
//                            // alert status window that database initialization has begun
//                            updateStatusMessage(getString(R.string.initialization_event_update_database_started));
//                        }
//
//                        @Override
//                        public void onUpdate(String message) {
//                            // alert status window with a new message
//                            updateStatusMessage(message);
//                        }
//
//                        @Override
//                        public void onFinish(boolean didUpdate) {
//                            // alert status window that database initialization has finished, with or without an error
//                            if (didUpdate) {
//                                updateStatusMessage(formatMessageWithElapsedTime(getString(R.string.initialization_event_update_database_finished)));
//
//                                // set global boolean used to notify main activity when called
//                                mDidUpdate = true;
//                            }
//
//                            // trigger next event (in-app purchases?)
//                            mListener.onEndEvent();
//                        }
//                    }).execute();
                break;
            case UpdatePreferences:
                break;
            case CraftingQueue:
                break;
            case Finalize:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ExceptionObservable.getInstance().registerObserver(this);
    }

    @Override
    protected void onPause() {
        ExceptionObservable.getInstance().unregisterObserver();

        super.onPause();
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

    private void sendErrorReport(Exception e) {
        ExceptionUtil.SendErrorReport(TAG, e);
    }

    private void updateStatusMessage(final String message) {
        mViewModel.updateStatusMessage(message);
    }

    private String formatMessageWithElapsedTime(String message) {
        long endTime = System.currentTimeMillis();
        long elapsedMilliseconds = endTime - startTime;
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
        intent.putExtra(MainActivity.INTENT_KEY_DID_UPDATE, mDidUpdate);

        startActivity(intent);

        finish();
    }

    private void startFirstUseActivity() {
        Intent intent = new Intent(getApplicationContext(), FirstUseActivity.class);
        startActivityForResult(intent, FirstUseActivity.REQUEST_CODE);
    }

    private interface Listener {
        // triggers upon any error found, alerts user via status screen, sends report, closes app
        void onError(Exception e, String message);

        // sets current event id, triggers first event
        void onInit();

        // triggers app to onResume main activity
        void onFinish();
    }
}