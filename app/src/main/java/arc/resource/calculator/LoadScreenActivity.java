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

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.tasks.InitializationTask;
import arc.resource.calculator.tasks.ParseConvertTask;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;

import static arc.resource.calculator.LoadScreenActivity.EVENT.INIT;

public class LoadScreenActivity extends AppCompatActivity {
    private static final String TAG = LoadScreenActivity.class.getSimpleName();

    private static final int TYPE_APP = 0;
    private static final int TYPE_DATABASE = 1;
    private static final int TYPE_PURCHASE = 2;
    private static final int TYPE_PREFERENCES = 3;

    private static final int EVENT_INIT = 0;
    private static final int EVENT_JSON = 1;
    private static final int EVENT_DATABASE = 2;
    private static final int EVENT_PURCHASE = 3;
    private static final int EVENT_PREFERENCES = 4;

    enum EVENT {
        INIT, JSON, DATABASE, PURCHASE, PREFERENCES
    }

//    private static final int MAX_LINES = 10;

    private static final long DELAY_MILLIS = 1500;

    private final List<String> mStatusMessages = new ArrayList<>();

    private TextView mTextView;

    private long mStartElapsedTime;

    private Listener mListener;

    private String mNewVersion;

    private boolean mHasUpdate = false;
    private boolean mDidUpdate = false;
    private JSONObject mJSONObject;

//    private PurchaseUtil mPurchaseUtil;

    private interface Listener {
        // triggers upon any error found, alerts user via status screen, sends report, closes app
        void onError( EVENT event, Exception e );

        // sets current event id, triggers first event
        void onInit();

        // start current event, triggers end event
        void onStartEvent( EVENT event );

        // triggers next event
        void onEndEvent( EVENT event );

        // triggers start event
        void onNextEvent( EVENT event );

        // triggers app to start main activity
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
            public void onError( EVENT event, Exception e ) {
                // alert user of error, send error report, close app
                updateStatusMessages( String.format( getString( R.string.load_activity_status_message_error_with_message ), e.getLocalizedMessage() ) );

                // send error report
                updateStatusMessages( getString( R.string.load_activity_status_message_send_error_report ) );
                sendErrorReport( e );

                // closing app
                updateStatusMessages( getString( R.string.load_activity_status_message_closing_app ) );
                finishActivityWithError();
            }

            @Override
            public void onInit() {
                mListener.onStartEvent( INIT );
            }

            @Override
            public void onStartEvent( final EVENT event ) {
                Log.d( TAG, "onStartEvent(): " + event );

                switch ( event ) {
                    case INIT:
                        updateStatusMessages( getString( R.string.initialization_init_event ) );
                        mListener.onEndEvent( event );
                        break;

                    case JSON:
                        new ParseConvertTask( getApplicationContext(), new ParseConvertTask.Listener() {
                            @Override
                            public void onError( Exception e ) {
                                // alert status window of error
                                updateStatusMessages( getString( R.string.initialization_error_event ) );

                                // trigger activity error event handler
                                mListener.onError( event, e );
                            }

                            @Override
                            public void onInit() {
                                // alert status window that database initialization is initializing
                                updateStatusMessages( getString( R.string.initialization_json_event_init ) );
                            }

                            @Override
                            public void onNewVersion( String oldVersion, String newVersion ) {
                                // alert status window of new version
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
                                mListener.onEndEvent( event );
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
                                mListener.onEndEvent( event );
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
                                    mListener.onError( event, e );
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
                                    mListener.onEndEvent( event );
                                }
                            } ).execute();
                        } else {
                            // trigger next event (in-app purchases?)
                            mListener.onEndEvent( event );
                        }
                        break;

                    case PURCHASE:
                        // alert status window that initializing has begun
//                        updateStatusMessages( formatMessageWithPrefix( TYPE_PURCHASE, getString( R.string.initialization_purchase_event_init ) ) );
//
//                        // begin in-app purchase communication
//                        mPurchaseUtil = new PurchaseUtil( LoadScreenActivity.this );
//                        mPurchaseUtil.start();
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
                        mListener.onEndEvent( event );
                        break;

                    case PREFERENCES:
                        // if database updated, save new version to preferences, reset categories back to default
                        if ( mHasUpdate ) {
                            updateStatusMessages( getString( R.string.initialization_pref_event_started ) );

                            PrefsUtil prefs = new PrefsUtil( getApplicationContext() );

                            prefs.updateJSONVersion( mNewVersion );
                            prefs.saveDefaults();

                            updateStatusMessages( getString( R.string.initialization_pref_event_finished ) );
                        }

                        // trigger next event (end of events?)
                        mListener.onEndEvent( event );
                        break;

                    default:
                        Log.e( TAG, "Incorrect Event Type: " + event );
                        break;
                }
            }

            @Override
            public void onEndEvent( EVENT event ) {
                mListener.onNextEvent( event );
            }

            @Override
            public void onNextEvent( EVENT event ) {
                int index = event.ordinal();

                index++;

                EVENT[] events = EVENT.values();

                if ( index < events.length ) {
                    EVENT nextEvent = events[index];

                    Log.d( TAG, "onNextEvent(): " + nextEvent + ", " + index );
                    mListener.onStartEvent( nextEvent );
                } else
                    mListener.onFinish();
            }

            @Override
            public void onFinish() {
                // say goodbye to user, start app
                updateStatusMessages( formatMessageWithElapsedTime( getString( R.string.initialization_finish_event ) ) );

                finishActivity();
            }
        };

        mListener.onInit();
    }

    @Override
    protected void onDestroy() {
//        mPurchaseUtil.stop();

        super.onDestroy();
    }

    private void sendErrorReport( Exception e ) {
        ExceptionUtil.SendErrorReport( this, TAG, e );
    }

    private void displayStatusMessage( String message ) {
        Log.d( TAG, "displayStatusMessage(): " + message );
        mTextView.append( message.concat( "\n" ) );
    }

    private void displayStatusMessages() {
//        Iterator<String> iterator = mStatusMessages.iterator();
//
//        StringBuilder builder = new StringBuilder();
//        for ( String statusMessage : mStatusMessages ) {
//            builder.append( statusMessage ).append( '\n' );
//        }
//
//        mTextView.setText( builder.toString() );
    }

    private void updateStatusMessages( final String message ) {
//        if ( mStatusMessages.size() > MAX_LINES )
//            mStatusMessages.remove( 0 );

//        mStatusMessages.add( message );

        runOnUiThread( new Runnable() {
            @Override
            public void run() {
//                displayStatusMessages( );
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
                intent.putExtra( MainActivity.INTENT_KEY_DID_UPDATE, mHasUpdate );

                startActivity( intent );

                finish();
            }
        } );
    }

    private void finishActivityWithError() {
        finishWithDelay( new Runnable() {
            @Override
            public void run() {
                finish();
            }
        } );
    }

    private void finishWithDelay( Runnable runnable ) {
        new Handler().postDelayed( runnable, DELAY_MILLIS );
    }
}