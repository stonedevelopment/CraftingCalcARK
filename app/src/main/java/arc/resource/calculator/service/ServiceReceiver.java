package arc.resource.calculator.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

// http://stacktips.com/tutorials/android/creating-a-background-service-in-android
public class ServiceReceiver extends ResultReceiver {
    private Receiver mReceiver;

    public ServiceReceiver( Handler handler ) {
        super( handler );
    }

    public void setReceiver( Receiver receiver ) {
        mReceiver = receiver;
    }

    public interface Receiver {
        void onReceiveResult( int resultCode, Bundle resultData );
    }

    @Override
    protected void onReceiveResult( int resultCode, Bundle resultData ) {
        if ( mReceiver != null ) {
            mReceiver.onReceiveResult( resultCode, resultData );
        }
    }
}
