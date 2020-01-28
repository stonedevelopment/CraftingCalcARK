/*
 * Copyright (c) 2019 Jared Stone
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
package arc.resource.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import arc.resource.calculator.listeners.ExceptionObservable;
import arc.resource.calculator.model.engram.QueueEngram;
import arc.resource.calculator.repository.queue.QueueObserver;
import arc.resource.calculator.repository.queue.QueueRepository;
import arc.resource.calculator.tasks.InitializationTask;
import arc.resource.calculator.tasks.ParseConvertTask;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;

import static arc.resource.calculator.LoadScreenActivity.EVENT.INIT;

public class LoadScreenActivity extends AppCompatActivity implements ExceptionObservable.Observer {
    private static final String TAG = LoadScreenActivity.class.getSimpleName();

    private final String BUNDLE_EVENT = "EVENT";
    private final String BUNDLE_TEXT = "TEXT";
    private final String BUNDLE_TIME = "TIME";

    /**
     * Events in order of execution.
     */
    enum EVENT {
        INIT,
        JSON,
        DATABASE,
        PREFERENCES,
        QUEUE,
        PREPARATION
    }

    private static final long DELAY_MILLIS = 1500;

    private JSONObject mJSONObject;
    private Listener mListener;
    private String mNewVersion;
    private long mStartElapsedTime;
    private TextView mTextView;

    private boolean mHasUpdate = false;
    private boolean mDidUpdate = false;

    private EVENT mCurrentEvent = INIT;

    private interface Listener {
        // triggers upon any error found, alerts user via status screen, sends report, closes app
        void onError(Exception e);

        // sets current event id, triggers first event
        void onInit();

        // onResume current event, triggers end event
        void onStartEvent();

        // triggers next event
        void onEndEvent();

        // triggers onResume event
        void onNextEvent();

        // triggers app to onResume main activity
        void onFinish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        ImageView imageView = findViewById(R.id.content_init_image_view);

        final String imagePath = "file:///android_asset/splash.png";
        Picasso.with(this)
                .load(imagePath)
                .error(R.drawable.placeholder_empty)
                .placeholder(R.drawable.placeholder_empty)
                .into(imageView);

        mTextView = findViewById(R.id.content_init_status_text);

        if (savedInstanceState != null) {
            int eventOrdinal = (int) savedInstanceState.get(BUNDLE_EVENT);
            mCurrentEvent = EVENT.values()[eventOrdinal];

            mStartElapsedTime = (long) savedInstanceState.get(BUNDLE_TIME);

            String text = (String) savedInstanceState.get(BUNDLE_TEXT);
            mTextView.setText(text);
        } else {
            mCurrentEvent = INIT;
            mStartElapsedTime = SystemClock.elapsedRealtime();
        }

        mListener = new Listener() {
            @Override
            public void onError(Exception e) {
                // send error report
                sendErrorReport(e);

                // alert user of error
                updateStatusMessages(getString(R.string.load_activity_status_message_error_with_message));
            }

            @Override
            public void onInit() {
                if (mCurrentEvent == INIT) {
                    mListener.onStartEvent();
                }
            }

            @Override
            public void onStartEvent() {
                Log.d(TAG, "onStartEvent(): " + mCurrentEvent);

                switch (mCurrentEvent) {
                    case INIT:
                        updateStatusMessages(getString(R.string.initialization_init_event));

                        PrefsUtil.getInstance(getApplicationContext());

                        mListener.onEndEvent();
                        break;

                    case JSON:
                        new ParseConvertTask(getApplicationContext(), new ParseConvertTask.Listener() {
                            @Override
                            public void onError(Exception e) {
                                // alert status window of error
                                updateStatusMessages(getString(R.string.initialization_error_event));

                                // trigger activity error event handler
                                mListener.onError(e);
                            }

                            @Override
                            public void onInit() {
                                // alert status window that database initialization is initializing
                                updateStatusMessages(getString(R.string.initialization_json_event_init));
                            }

                            @Override
                            public void onNewVersion(String oldVersion, String newVersion) {
                                // alert status window of new version
                                if (oldVersion == null)
                                    // first install
                                    updateStatusMessages(String.format(getString(R.string.initialization_json_event_new_version_first_install), newVersion));
                                else
                                    // updated install
                                    updateStatusMessages(String.format(getString(R.string.initialization_json_event_new_version), oldVersion, newVersion));

                                mNewVersion = newVersion;
                            }

                            @Override
                            public void onStart() {
                                // alert status window that database initialization has begun
                                updateStatusMessages(getString(R.string.initialization_json_event_started));
                            }

                            @Override
                            public void onUpdate(String message) {
                                // alert status window with a new message
                                updateStatusMessages(message);
                            }

                            @Override
                            public void onFinish() {
                                // alert status window that database initialization has finished, no new update
                                updateStatusMessages(getString(R.string.initialization_json_event_finished_without_update));

                                // trigger next event (database initialization)
                                mListener.onEndEvent();
                            }

                            @Override
                            public void onFinish(JSONObject newObject) {
                                // alert status window that database initialization has finished, with update
                                updateStatusMessages(formatMessageWithElapsedTime(getString(R.string.initialization_json_event_finished_with_update)));

                                // set global boolean to notify initialization task
                                mHasUpdate = true;

                                // set global json object for use in initialization task
                                mJSONObject = newObject;

                                // trigger next event (database initialization)
                                mListener.onEndEvent();
                            }
                        }).execute();
                        break;

                    case DATABASE:
                        if (mHasUpdate) {
                            new InitializationTask(getApplicationContext(), mJSONObject, new InitializationTask.Listener() {
                                @Override
                                public void onError(Exception e) {
                                    // alert status window of error
                                    updateStatusMessages(getString(R.string.initialization_error_event));

                                    // trigger activity error event handler
                                    mListener.onError(e);
                                }

                                @Override
                                public void onInit() {
                                    // alert status window that database initialization is initializing
//                                updateStatusMessages( getString( R.string.initialization_db_event_init ) );
                                }

                                @Override
                                public void onStart() {
                                    // alert status window that database initialization has begun
                                    updateStatusMessages(getString(R.string.initialization_db_event_started));
                                }

                                @Override
                                public void onUpdate(String message) {
                                    // alert status window with a new message
                                    updateStatusMessages(message);
                                }

                                @Override
                                public void onFinish(boolean didUpdate) {
                                    // alert status window that database initialization has finished, with or without an error
                                    if (didUpdate) {
                                        updateStatusMessages(formatMessageWithElapsedTime(getString(R.string.initialization_db_event_finished)));

                                        // set global boolean used to notify main activity when called
                                        mDidUpdate = true;
                                    }

                                    // trigger next event (in-app purchases?)
                                    mListener.onEndEvent();
                                }
                            }).execute();
                        } else {
                            // trigger next event (in-app purchases?)
                            mListener.onEndEvent();
                        }
                        break;

                    case PREFERENCES:
                        // if database updated, save new version to preferences, reset categories back to default
                        if (mHasUpdate) {
                            updateStatusMessages(getString(R.string.initialization_pref_event_started));

                            PrefsUtil.getInstance(getApplicationContext()).updateJSONVersion(mNewVersion);
                            PrefsUtil.getInstance(getApplicationContext()).saveCraftingQueueJSONString(null);
                            PrefsUtil.getInstance(getApplicationContext()).saveToDefault();

                            updateStatusMessages(getString(R.string.initialization_pref_event_finished));
                        }

                        // trigger next event (end of events?)
                        mListener.onEndEvent();
                        break;

                    case QUEUE:
                        // create instance of QueueRepository
                        updateStatusMessages(getString(R.string.initialization_queue_event_started));

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
                                updateStatusMessages(getString(R.string.initialization_queue_event_fetching));
                            }

                            @Override
                            public void onQueueDataPopulated() {
                                updateStatusMessages(getString(R.string.initialization_queue_event_finished));
                                queueRepository.removeObserver(TAG);
                                mListener.onEndEvent();
                            }

                            @Override
                            public void onQueueDataEmpty() {
                                updateStatusMessages(getString(R.string.initialization_queue_event_finished));
                                queueRepository.removeObserver(TAG);
                                mListener.onEndEvent();
                            }
                        });
                        queueRepository.init(getApplicationContext());
                        break;

                    case PREPARATION:
                        updateStatusMessages(getString(R.string.initialization_app_init_event_started));

                        int width = getResources().getDisplayMetrics().widthPixels;
                        int numCols = getResources().getInteger(R.integer.gridview_column_count);

//                        DisplayUtil.getInstance().setImageSize( width / numCols );
                        PrefsUtil.getInstance(getApplicationContext()).saveCraftableViewSize(width / numCols);

                        // trigger next event (end of events?)
                        mListener.onEndEvent();
                        break;

                    default:
                        Log.e(TAG, "Incorrect Event Type: " + mCurrentEvent);
                        break;
                }
            }

            @Override
            public void onEndEvent() {
                mListener.onNextEvent();
            }

            @Override
            public void onNextEvent() {
                int index = mCurrentEvent.ordinal();

                index++;

                EVENT[] events = EVENT.values();

                if (index < events.length) {
                    mCurrentEvent = events[index];

                    Log.d(TAG, "onNextEvent(): " + mCurrentEvent + ", " + index);
                    mListener.onStartEvent();
                } else {
                    mListener.onFinish();
                }
            }

            @Override
            public void onFinish() {
                // say goodbye to user, onResume app
                updateStatusMessages(formatMessageWithElapsedTime(getString(R.string.initialization_finish_event)));

                finishActivity();
            }
        };

        mListener.onInit();
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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_EVENT, mCurrentEvent.ordinal());
        outState.putString(BUNDLE_TEXT, mTextView.getText().toString());
        outState.putLong(BUNDLE_TIME, mStartElapsedTime);

        super.onSaveInstanceState(outState);
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

    private void displayStatusMessage(String message) {
        Log.d(TAG, "displayStatusMessage(): " + message);
        mTextView.append(message);
    }

    private void updateStatusMessages(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                displayStatusMessage(message);
            }
        });
    }

    // http://stackoverflow.com/questions/15248891/how-to-measure-elapsed-time
    private String formatMessageWithElapsedTime(String message) {
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliseconds = endTime - mStartElapsedTime;
        double elapsedSeconds = elapsedMilliseconds / 1000.0;

        String elapsedMessage = String.format(getString(R.string.load_activity_status_message_elapsed_format), Double.toString(elapsedSeconds));

        return String.format(getString(R.string.load_activity_status_message_format_with_elapsed_time), message, elapsedMessage);
    }

    private void finishWithDelay(Runnable runnable) {
        new Handler().postDelayed(runnable, DELAY_MILLIS);
    }

    private void finishActivity() {
        finishWithDelay(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean isFirst = prefs.getBoolean(PrefsUtil.FirstUseKey, PrefsUtil.FirstUseDefaultValue);
//                boolean isFirst = true;

                if (isFirst)
                    startFirstUseActivity();
                else
                    startMainActivity();
            }
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

    @Override
    public void onException(String tag, Exception e) {
        mListener.onError(e);
    }

    @Override
    public void onFatalException(String tag, Exception e) {
        mListener.onError(e);
    }
}