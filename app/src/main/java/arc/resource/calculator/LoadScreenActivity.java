/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: A:RC, a resource calculator for ARK:Survival Evolved
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
package arc.resource.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import arc.resource.calculator.listeners.ErrorReporter;
import arc.resource.calculator.tasks.InitializationTask;
import arc.resource.calculator.tasks.ParseConvertTask;
import arc.resource.calculator.util.DisplayUtil;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;

import static arc.resource.calculator.LoadScreenActivity.EVENT.INIT;

public class LoadScreenActivity extends AppCompatActivity implements ErrorReporter.Listener {
    private static final String TAG = LoadScreenActivity.class.getSimpleName();

    enum EVENT {
        INIT, JSON, DATABASE, PURCHASE, PREFERENCES, PREPARATION
    }

    private static final long DELAY_MILLIS = 1500;

    private JSONObject mJSONObject;
    private Listener mListener;
    private String mNewVersion;
    private long mStartElapsedTime;
    private TextView mTextView;

    private boolean mHasUpdate = false;
    private boolean mDidUpdate = false;

    private EVENT mCurrentEvent;

    private interface Listener {
        // triggers upon any error found, alerts user via status screen, sends report, closes app
        void onError( Exception e );

        // sets current event id, triggers first event
        void onInit();

        // setup current event, triggers end event
        void onStartEvent();

        // triggers next event
        void onEndEvent();

        // triggers setup event
        void onNextEvent();

        // triggers app to setup main activity
        void onFinish();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_load_screen );

        mStartElapsedTime = SystemClock.elapsedRealtime();

        mTextView = ( TextView ) findViewById( R.id.content_init_status_text );

        mListener = new Listener() {
            @Override
            public void onError( Exception e ) {
                // send error report
                sendErrorReport( e );

                // alert user of error
                updateStatusMessages( getString( R.string.load_activity_status_message_error_with_message ) );
            }

            @Override
            public void onInit() {
                mCurrentEvent = INIT;
                mListener.onStartEvent();
            }

            @Override
            public void onStartEvent() {
                Log.d( TAG, "onStartEvent(): " + mCurrentEvent );

                switch ( mCurrentEvent ) {
                    case INIT:
                        updateStatusMessages( getString( R.string.initialization_init_event ) );

                        PrefsUtil.createInstance( getApplicationContext() );

                        mListener.onEndEvent();
                        break;

                    case JSON:
                        new ParseConvertTask( getApplicationContext(), new ParseConvertTask.Listener() {
                            @Override
                            public void onError( Exception e ) {
                                // alert status window of error
                                updateStatusMessages( getString( R.string.initialization_error_event ) );

                                // trigger activity error event handler
                                mListener.onError( e );
                            }

                            @Override
                            public void onInit() {
                                // alert status window that database initialization is initializing
                                updateStatusMessages( getString( R.string.initialization_json_event_init ) );
                            }

                            @Override
                            public void onNewVersion( String oldVersion, String newVersion ) {
                                // alert status window of new version
                                if ( oldVersion == null )
                                    // first install
                                    updateStatusMessages( String.format( getString( R.string.initialization_json_event_new_version_first_install ), newVersion ) );
                                else
                                    // updated install
                                    updateStatusMessages( String.format( getString( R.string.initialization_json_event_new_version ), oldVersion, newVersion ) );

                                mNewVersion = newVersion;
                            }

                            @Override
                            public void onStart() {
                                // alert status window that database initialization has begun
                                updateStatusMessages( getString( R.string.initialization_json_event_started ) );
                            }

                            @Override
                            public void onUpdate( String message ) {
                                // alert status window with a new message
                                updateStatusMessages( message );
                            }

                            @Override
                            public void onFinish() {
                                // alert status window that database initialization has finished, no new update
                                updateStatusMessages( getString( R.string.initialization_json_event_finished_without_update ) );

                                // trigger next event (database initialization)
                                mListener.onEndEvent();
                            }

                            @Override
                            public void onFinish( JSONObject newObject ) {
                                // alert status window that database initialization has finished, with update
                                updateStatusMessages( formatMessageWithElapsedTime( getString( R.string.initialization_json_event_finished_with_update ) ) );

                                // set global boolean to notify initialization task
                                mHasUpdate = true;

                                // set global json object for use in initialization task
                                mJSONObject = newObject;

                                // trigger next event (database initialization)
                                mListener.onEndEvent();
                            }
                        } ).execute();
                        break;

                    case DATABASE:
                        if ( mHasUpdate ) {
                            new InitializationTask( getApplicationContext(), mJSONObject, new InitializationTask.Listener() {
                                @Override
                                public void onError( Exception e ) {
                                    // alert status window of error
                                    updateStatusMessages( getString( R.string.initialization_error_event ) );

                                    // trigger activity error event handler
                                    mListener.onError( e );
                                }

                                @Override
                                public void onInit() {
                                    // alert status window that database initialization is initializing
//                                updateStatusMessages( getString( R.string.initialization_db_event_init ) );
                                }

                                @Override
                                public void onStart() {
                                    // alert status window that database initialization has begun
                                    updateStatusMessages( getString( R.string.initialization_db_event_started ) );
                                }

                                @Override
                                public void onUpdate( String message ) {
                                    // alert status window with a new message
                                    updateStatusMessages( message );
                                }

                                @Override
                                public void onFinish( boolean didUpdate ) {
                                    // alert status window that database initialization has finished, with or without an error
                                    if ( didUpdate ) {
                                        updateStatusMessages( formatMessageWithElapsedTime( getString( R.string.initialization_db_event_finished ) ) );

                                        // set global boolean used to notify main activity when called
                                        mDidUpdate = true;
                                    }

                                    // trigger next event (in-app purchases?)
                                    mListener.onEndEvent();
                                }
                            } ).execute();
                        } else {
                            // trigger next event (in-app purchases?)
                            mListener.onEndEvent();
                        }
                        break;

                    case PURCHASE:
                        // alert status window that initializing has begun
//                        updateStatusMessages( formatMessageWithPrefix( TYPE_PURCHASE, getString( R.string.initialization_purchase_event_init ) ) );
//
//                        // begin in-app purchase communication
//                        mPurchaseUtil = new PurchaseUtil( LoadScreenActivity.this );
//                        mPurchaseUtil.setup();
//                        mPurchaseUtil.loadInventory( new Inventory.Callback() {
//                            @Override
//                            public void onLoaded( @Nonnull Inventory.Products products ) {
//                                Inventory.Product product = products.get( IN_APP );
//
//                                for ( Purchase purchase : product.getPurchases() ) {
//                                    updateStatusMessages( formatMessageWithPrefix( TYPE_PURCHASE, purchase.sku ) );
//                                }
//
//                                if ( product.supported ) {
//                                    updateStatusMessages( "Supported" );
//                                } else {
//                                    updateStatusMessages( "Unsupported" );
//                                }
//
//                                // set prefs per sku
//                                PrefsUtil prefs = new PrefsUtil( getApplicationContext() );
//                                for ( Sku sku : product.getSkus() ) {
//                                    updateStatusMessages( formatMessageWithPrefix( TYPE_PURCHASE, sku.toString() ) );
////                                    prefs.setPurchasePref( sku, product.isPurchased( sku ) );
//                                }
//
//                                int purchases = product.getPurchases().size();
//
//                                // If purchases are greater than 0, welcome and thank customer, otherwise just welcome
//                                String message = purchases > 0 ?
//                                        getString( R.string.initialization_purchase_event_finished_with_purchase ) :
//                                        getString( R.string.initialization_purchase_event_finished_without_purchase );
//
//                                updateStatusMessages( formatMessageWithPrefix( TYPE_PURCHASE, message ) );
//
//                            }
//                        } );

                        // trigger next event (preferences?)
                        mListener.onEndEvent();
                        break;

                    case PREFERENCES:
                        // if database updated, save new version to preferences, reset categories back to default
                        if ( mHasUpdate ) {
                            updateStatusMessages( getString( R.string.initialization_pref_event_started ) );

                            PrefsUtil.getInstance().updateJSONVersion( mNewVersion );
                            PrefsUtil.getInstance().saveToDefault();

                            updateStatusMessages( getString( R.string.initialization_pref_event_finished ) );
                        }

                        // trigger next event (end of events?)
                        mListener.onEndEvent();
                        break;

                    case PREPARATION:
                        // create instances of DisplayCase and CraftingQueue
                        updateStatusMessages( getString( R.string.initialization_app_init_event_started ) );

                        int width = getResources().getDisplayMetrics().widthPixels;
                        int numCols = getResources().getInteger( R.integer.gridview_column_count );

                        DisplayUtil.getInstance().setImageSize( width / numCols );

                        // trigger next event (end of events?)
                        mListener.onEndEvent();
                        break;

                    default:
                        Log.e( TAG, "Incorrect Event Type: " + mCurrentEvent );
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

                if ( index < events.length ) {
                    mCurrentEvent = events[index];

                    Log.d( TAG, "onNextEvent(): " + mCurrentEvent + ", " + index );
                    mListener.onStartEvent();
                } else
                    mListener.onFinish();
            }

            @Override
            public void onFinish() {
                // say goodbye to user, setup app
                updateStatusMessages( formatMessageWithElapsedTime( getString( R.string.initialization_finish_event ) ) );

                finishActivity();
            }
        };

        mListener.onInit();
    }

    @Override
    protected void onResume() {
        ErrorReporter.getInstance().registerListener( this );

        super.onResume();
    }

    @Override
    protected void onPause() {
        ErrorReporter.getInstance().unregisterListener( this );

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ErrorReporter.getInstance().unregisterListener( this );

        super.onDestroy();
    }

    private void sendErrorReport( Exception e ) {
        ExceptionUtil.SendErrorReport( TAG, e );
    }

    private void displayStatusMessage( String message ) {
        Log.d( TAG, "displayStatusMessage(): " + message );
        mTextView.append( message );
    }

    private void updateStatusMessages( final String message ) {
        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                displayStatusMessage( message );
            }
        } );
    }

    // http://stackoverflow.com/questions/15248891/how-to-measure-elapsed-time
    private String formatMessageWithElapsedTime( String message ) {
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliseconds = endTime - mStartElapsedTime;
        double elapsedSeconds = elapsedMilliseconds / 1000.0;

        String elapsedMessage = String.format( getString( R.string.load_activity_status_message_elapsed_format ), Double.toString( elapsedSeconds ) );

        return String.format( getString( R.string.load_activity_status_message_format_with_elapsed_time ), message, elapsedMessage );
    }

    private void finishActivity() {
        finishWithDelay( new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                intent.putExtra( MainActivity.INTENT_KEY_DID_UPDATE, mDidUpdate );

                startActivity( intent );

                finish();
            }
        } );
    }

    private void finishWithDelay( Runnable runnable ) {
        new Handler().postDelayed( runnable, DELAY_MILLIS );
    }

    @Override
    public void onSendErrorReport( String tag, Exception e ) {
        mListener.onError( e );
    }

    @Override
    public void onSendErrorReportWithAlertDialog( String tag, Exception e ) {
        mListener.onError( e );
    }
}