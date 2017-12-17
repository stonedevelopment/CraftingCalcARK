package arc.resource.calculator.listeners;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.util.ExceptionUtil;

public class ExceptionObserver {
    private static final String TAG = ExceptionObserver.class.getSimpleName();

    private static ExceptionObserver sInstance;

    private List<Listener> mListeners;

    public interface Listener {
        void onException( String tag, Exception e );

        void onFatalException( String tag, Exception e );
    }

    public static ExceptionObserver getInstance() {
        if ( sInstance == null )
            sInstance = new ExceptionObserver();

        return sInstance;
    }

    private ExceptionObserver() {
        mListeners = new ArrayList<>();
    }

    public synchronized void registerListener( Listener listener ) {
        if ( !mListeners.contains( listener ) )
            mListeners.add( listener );
    }

    public synchronized void unregisterListener( Listener listener ) {
        mListeners.remove( listener );
    }

    public void notifyExceptionCaught( String tag, Exception e ) {
        if ( mListeners.size() > 0 ) {
            for ( Listener listener : mListeners ) {
                listener.onException( tag, e );
            }
        } else {
            ExceptionUtil.SendErrorReport( tag, e );
        }
    }

    public void notifyFatalExceptionCaught( String tag, Exception e ) {
        if ( mListeners.size() > 0 ) {
            for ( Listener listener : mListeners ) {
                listener.onFatalException( tag, e );
            }
        } else {
            ExceptionUtil.SendErrorReport( tag, e );
        }
    }

    public void throwException() {
        notifyExceptionCaught( TAG, new Exception( "Manually thrown exception." ) );
    }

    public void throwFatalException() {
        notifyFatalExceptionCaught( TAG, new Exception( "Manually thrown exception." ) );
    }
}