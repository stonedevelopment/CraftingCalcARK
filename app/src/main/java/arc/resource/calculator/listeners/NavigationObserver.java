package arc.resource.calculator.listeners;

import java.util.ArrayList;
import java.util.List;

/**
 * Observing class helper for NavigationTextView
 * <p>
 * Alerts listeners of changes to navigational changes - change station/category and search
 */
public class NavigationObserver {
    private static NavigationObserver sInstance;

    private List<Listener> mListeners;

    public static abstract class Listener {
        public void onUpdate( String text ) {
            // do nothing
        }
    }

    public static NavigationObserver getInstance() {
        if ( sInstance == null )
            sInstance = new NavigationObserver();

        return sInstance;
    }

    private NavigationObserver() {
        mListeners = new ArrayList<>();
    }

    public void registerListener( Listener listener ) {
        mListeners.add( listener );
    }

    public void unregisterListener( Listener listener ) {
        mListeners.remove( listener );
    }

    public void update( String text ) {
        for ( Listener listener : mListeners ) {
            listener.onUpdate( text );
        }
    }
}