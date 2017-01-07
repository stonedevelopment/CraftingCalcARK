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
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import arc.resource.calculator.service.InitializationService;
import arc.resource.calculator.service.ServiceReceiver;
import arc.resource.calculator.util.AdUtil;
import arc.resource.calculator.util.Helper;
import arc.resource.calculator.util.PrefsUtil;

import static arc.resource.calculator.service.ServiceUtil.PARAM_MESSAGE;
import static arc.resource.calculator.service.ServiceUtil.PARAM_RECEIVER;
import static arc.resource.calculator.service.ServiceUtil.PARAM_REQUEST_ID;
import static arc.resource.calculator.service.ServiceUtil.STATUS_ERROR;
import static arc.resource.calculator.service.ServiceUtil.STATUS_FINISHED;
import static arc.resource.calculator.service.ServiceUtil.STATUS_STARTED;
import static arc.resource.calculator.service.ServiceUtil.STATUS_UPDATING;

public class LoadScreenActivity extends AppCompatActivity implements ServiceReceiver.Receiver {
    private static final String TAG = LoadScreenActivity.class.getSimpleName();

    private boolean mIsActive;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_load_screen );

        mIsActive = true;

        if ( isNewVersion() ) {
            ServiceReceiver receiver = new ServiceReceiver( new Handler() );
            receiver.setReceiver( this );

            Intent intent = new Intent( this, InitializationService.class );
            intent.putExtra( PARAM_REQUEST_ID, 100 );
            intent.putExtra( PARAM_RECEIVER, receiver );
            startService( intent );

            AdUtil.loadAdView( this );
        } else {
            startMainActivity( false );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsActive = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIsActive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsActive = false;
    }

    void startMainActivity( boolean didUpdate ) {
        if ( mIsActive ) {
            Intent intent = new Intent( getApplicationContext(), MainActivity.class );
            intent.putExtra( getString( R.string.intent_key_did_update ), didUpdate );

            startActivity( intent );

            finish();
        }
    }

    boolean isNewVersion() {
        String oldVersion = new PrefsUtil( this ).getJSONVersion();
        String newVersion = getString( R.string.json_version );

        Helper.Log( TAG, oldVersion + " == " + newVersion + "?" );

        return !Objects.equals( oldVersion, newVersion );
    }

    @Override
    public void onReceiveResult( int resultCode, Bundle resultData ) {
        String message;
        switch ( resultCode ) {
            case STATUS_STARTED:
                message = getString( R.string.load_activity_status_message_started );
                break;

            case STATUS_UPDATING:
                message = getString( R.string.load_activity_status_message_updating );
                break;

            case STATUS_FINISHED:
                message = getString( R.string.load_activity_status_message_finished );

                new Timer( TAG ).schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                postExecution();
                            }
                        },
                        1000
                );
                break;

            case STATUS_ERROR:
                message = String.format(
                        getString( R.string.load_activity_status_message_error_format ),
                        resultData.getString( PARAM_MESSAGE ) );
                break;

            default:
                message = "Unknown message code: " + resultCode + ", " + resultData.toString();
        }

        UpdateStatusText( message );
    }

    void UpdateStatusText( String message ) {
        TextView status = ( TextView ) findViewById( R.id.status );
        status.setText( message );
    }

    void postExecution() {
        // Save new version to preferences
        PrefsUtil prefs = new PrefsUtil( this );

        prefs.updateJSONVersion( getString( R.string.json_version ) );
        prefs.saveStationIdBackToDefault();
        prefs.saveCategoryLevelsBackToDefault();

        startMainActivity( true );
    }
}