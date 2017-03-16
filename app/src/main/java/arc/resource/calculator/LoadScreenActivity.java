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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.tasks.InitializationTask;
import arc.resource.calculator.util.ExceptionUtil;
import arc.resource.calculator.util.PrefsUtil;

public class LoadScreenActivity extends AppCompatActivity {
    private static final String TAG = LoadScreenActivity.class.getSimpleName();

    private static final int TYPE_APP = 0;
    private static final int TYPE_DATABASE = 1;
    private static final int TYPE_PURCHASE = 2;
    private static final int TYPE_PREFERENCES = 3;

    private static final int EVENT_INIT = 0;
    private static final int EVENT_DATABASE = 1;
    private static final int EVENT_PURCHASE = 2;
    private static final int EVENT_PREFERENCES = 3;

    private static final int MAX_LINES = 10;

    private static final long DELAY_MILLIS = 3000;

    private List<String> mStatusMessages = new ArrayList<>();

    private TextView mTextView;

    private long mStartElapsedTime;

    private Listener mListener;

    private int mCurrentEvent;

    private boolean mDidUpdate = false;

//    private PurchaseUtil mPurchaseUtil;

    private interface Listener {
        // triggers upon any error found, alerts user via status screen, sends report, closes app
        void onError( Exception e );

        // sets current event id, triggers first event
        void onInit();

        // start current event, triggers end event
        void onStartEvent();

        // triggers next event
        void onEndEvent();

        // triggers start event
        void onNextEvent();

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
            public void onError( Exception e ) {
                // alert user of error, send error report, close app
                updateStatusMessages( formatMessageWithPrefix( TYPE_APP,
                        String.format( getString( R.string.load_activity_status_message_error_with_message ), e.getLocalizedMessage() ) ) );

                // send error report
                updateStatusMessages( formatMessageWithPrefix( TYPE_APP, getString( R.string.load_activity_status_message_send_error_report ) ) );
                sendErrorReport( e );

                // closing app
                updateStatusMessages( formatMessageWithPrefix( TYPE_APP, getString( R.string.load_activity_status_message_closing_app ) ) );
                finishActivityWithError();
            }

            @Override
            public void onInit() {
                mCurrentEvent = EVENT_INIT;
                mListener.onStartEvent();
            }

            @Override
            public void onStartEvent() {
                switch ( mCurrentEvent ) {
                    case EVENT_INIT:
                        updateStatusMessages( getString( R.string.initialization_init_event ) );
                        mListener.onEndEvent();
                        break;

                    case EVENT_DATABASE:
                        new InitializationTask( getApplicationContext(), new InitializationTask.Listener() {
                            @Override
                            public void onError( Exception e ) {
                                // alert status window of error
                                updateStatusMessages( formatMessageWithPrefix( TYPE_DATABASE, getString( R.string.initialization_db_event_error ) ) );

                                // trigger activity error event handler
                                mListener.onError( e );
                            }

                            @Override
                            public void onInit() {
                                // alert status window that database initialization is initializing
                                updateStatusMessages( formatMessageWithPrefix( TYPE_DATABASE, getString( R.string.initialization_db_event_init ) ) );
                            }

                            @Override
                            public void onStart() {
                                // alert status window that database initialization has begun
                                updateStatusMessages( formatMessageWithPrefix( TYPE_DATABASE, getString( R.string.initialization_db_event_started ) ) );
                            }

                            @Override
                            public void onUpdate( String message ) {
                                // alert status window with a new message
                                updateStatusMessages( formatMessageWithPrefix( TYPE_DATABASE, message ) );
                            }

                            @Override
                            public void onFinish( boolean didUpdate ) {
                                // alert status window that database initialization has finished, with or without an error
                                if ( didUpdate ) {
                                    updateStatusMessages( formatMessageWithPrefix( TYPE_DATABASE, formatMessageWithElapsedTime( getString( R.string.initialization_db_event_finished_with_update ) ) ) );
                                } else {
                                    updateStatusMessages( formatMessageWithPrefix( TYPE_DATABASE, getString( R.string.initialization_db_event_finished_without_update ) ) );
                                }

                                // set global boolean to notify mainactivity
                                mDidUpdate = didUpdate;

                                // trigger next event (in-app purchases?)
                                mListener.onEndEvent();
                            }
                        } ).execute();
                        break;

                    case EVENT_PURCHASE:
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
                        mListener.onEndEvent();
                        break;

                    case EVENT_PREFERENCES:
                        // if database updated, save new version to preferences, reset categories back to default
                        if ( mDidUpdate ) {
                            updateStatusMessages( formatMessageWithPrefix( TYPE_PREFERENCES, getString( R.string.initialization_pref_event_init ) ) );

                            PrefsUtil prefs = new PrefsUtil( getApplicationContext() );

                            prefs.updateJSONVersion( getString( R.string.json_version ) );
                            prefs.saveStationIdBackToDefault();
                            prefs.saveCategoryLevelsBackToDefault();

                            updateStatusMessages( formatMessageWithPrefix( TYPE_PREFERENCES, getString( R.string.initialization_pref_event_finished ) ) );
                        }

                        mListener.onEndEvent();
                        break;

                    default:
                        // say goodbye to user, trigger onFinish()
                        updateStatusMessages( formatMessageWithElapsedTime( "Starting app! Craft efficiently, my friend." ) );
                        mListener.onFinish();
                        break;
                }
            }

            @Override
            public void onEndEvent() {
                mListener.onNextEvent();
            }

            @Override
            public void onNextEvent() {
                mCurrentEvent++;

                mListener.onStartEvent();
            }

            @Override
            public void onFinish() {
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

    private void displayStatusMessages() {
        StringBuilder builder = new StringBuilder();
        for ( String statusMessage : mStatusMessages ) {
            builder.append( statusMessage ).append( '\n' );
        }

        mTextView.setText( builder.toString() );
    }

    private void updateStatusMessages( String message ) {
        if ( mStatusMessages.size() > MAX_LINES )
            mStatusMessages.remove( 0 );

        mStatusMessages.add( message );

        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                displayStatusMessages();
            }
        } );
    }

    private String formatMessageWithPrefix( int type, String message ) {
        String prefix;
        switch ( type ) {
            case TYPE_APP:
                prefix = getString( R.string.load_activity_status_message_prefix_app );
                break;

            case TYPE_DATABASE:
                prefix = getString( R.string.load_activity_status_message_prefix_db );
                break;

            case TYPE_PURCHASE:
                prefix = getString( R.string.load_activity_status_message_prefix_purchase );
                break;

            case TYPE_PREFERENCES:
                prefix = getString( R.string.load_activity_status_message_prefix_preferences );
                break;

            default:
                prefix = getString( R.string.load_activity_status_message_prefix_unknown );
        }

//        return String.format( getString( R.string.load_activity_status_message_format_with_prefix ), prefix, message );
        return message;
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