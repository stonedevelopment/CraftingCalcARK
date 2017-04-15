package arc.resource.calculator.listeners;

/**
 * Observing class helper for QueueAdapter
 * <p>
 * Alerts adapter of changes brought forth from within the CraftingQueue static backbone object.
 */
public class QueueNotifier {
    private static QueueNotifier sInstance;

    private Listener mListener;

    public interface Listener {

    }

    public static QueueNotifier getInstance() {
        if ( sInstance == null )
            sInstance = new QueueNotifier();

        return sInstance;
    }

    private QueueNotifier() {
        mListener = null;
    }

    public void registerListener( Listener listener ) {
        mListener = listener;
    }

    public void unregisterListener( Listener listener ) {
        mListener = null;
    }
}