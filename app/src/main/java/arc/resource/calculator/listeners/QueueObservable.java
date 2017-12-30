package arc.resource.calculator.listeners;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Observing class helper for CraftableAdapter and InventoryAdapter
 * <p>
 * Alerts listeners of changes to the 'Queue' - adds, removes, etc.
 */

public class QueueObservable extends Observable {
    private static QueueObservable sInstance;

    private HashMap<String, Listener> mListeners;

    public static abstract class Listener {
        public abstract void onItemChanged( long craftableId, int quantity );

        public abstract void onItemRemoved( long craftableId );

        public abstract void onDataSetPopulated();

        public abstract void onDataSetEmpty();
    }

    public static QueueObservable getInstance() {
        if ( sInstance == null )
            sInstance = new QueueObservable();

        return sInstance;
    }

    public QueueObservable() {
        mListeners = new HashMap<>();
    }

    public void registerListener( String key, Listener listener ) {
        mListeners.put( key, listener );
    }

    public void unregisterListener( String key ) {
        mListeners.remove( key );
    }

    public void notifyItemChanged( long craftableId, int quantity ) {
        for ( Listener listener : mListeners.values() ) {
            listener.onItemChanged( craftableId, quantity );
        }
    }

    public void notifyItemRemoved( long craftableId ) {
        for ( Listener listener : mListeners.values() ) {
            listener.onItemRemoved( craftableId );
        }
    }

    public void notifyDataSetEmpty() {
        for ( Listener listener : mListeners.values() ) {
            listener.onDataSetEmpty();
        }
    }

    public void notifyDataSetPopulated() {
        for ( Listener listener : mListeners.values() ) {
            listener.onDataSetPopulated();
        }
    }
}