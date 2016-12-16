package arc.resource.calculator;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

// http://stacktips.com/tutorials/android/creating-a-background-service-in-android
class UpdateReceiver extends ResultReceiver {
    private Receiver mReceiver;

    UpdateReceiver( Handler handler ) {
        super( handler );
    }

    void setReceiver( Receiver receiver ) {
        mReceiver = receiver;
    }

    interface Receiver {
        void onReceiveResult( int resultCode, Bundle resultData );
    }

    @Override
    protected void onReceiveResult( int resultCode, Bundle resultData ) {
        if ( mReceiver != null ) {
            mReceiver.onReceiveResult( resultCode, resultData );
        }
    }
}
