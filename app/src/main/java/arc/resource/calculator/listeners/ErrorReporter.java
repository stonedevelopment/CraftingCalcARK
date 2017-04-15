package arc.resource.calculator.listeners;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.util.ExceptionUtil;

public class ErrorReporter {
    private static ErrorReporter sInstance;

    private List<Listener> mListeners;

    public interface Listener {
        void onSendErrorReport( String tag, Exception e );

        void onSendErrorReportWithAlertDialog( String tag, Exception e );
    }

    public static ErrorReporter getInstance() {
        if ( sInstance == null )
            sInstance = new ErrorReporter();

        return sInstance;
    }

    private ErrorReporter() {
        mListeners = new ArrayList<>();
    }

    public void registerListener( Listener listener ) {
        mListeners.add( listener );
    }

    public void unregisterListener( Listener listener ) {
        mListeners.remove( listener );
    }

    public void emitSendErrorReport( String tag, Exception e ) {
        if ( mListeners.size() > 0 ) {
            for ( Listener listener : mListeners ) {
                listener.onSendErrorReport( tag, e );
            }
        } else {
            ExceptionUtil.SendErrorReport( tag, e );
        }
    }

    public void emitSendErrorReportWithAlertDialog( String tag, Exception e ) {
        for ( Listener listener : mListeners ) {
            listener.onSendErrorReportWithAlertDialog( tag, e );
        }
    }
}