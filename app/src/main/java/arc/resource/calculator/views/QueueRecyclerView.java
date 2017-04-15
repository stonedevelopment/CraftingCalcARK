package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.ContextMenu;

import arc.resource.calculator.adapters.QueueAdapter;

public class QueueRecyclerView extends RecyclerViewWithContextMenu {
    private static final String TAG = QueueRecyclerView.class.getSimpleName();

    private ContextMenu.ContextMenuInfo mContextMenuInfo;

    private static Listener mListener;

    interface Listener {
        void onError( Exception e );

        void onInit();

        void onPopulated();

        void onEmpty();
    }

    public static class Observer {
        public void notifyExceptionCaught( Exception e ) {
            mListener.onError( e );
        }

        public void notifyInitializing() {
            mListener.onInit();
        }

        public void notifyDataSetPopulated() {
            mListener.onPopulated();
        }

        public void notifyDataSetEmpty() {
            mListener.onEmpty();
        }
    }

    public QueueRecyclerView( Context context ) {
        super( context );
        init();
    }

    public QueueRecyclerView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        init();
    }

    public QueueRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
        init();
    }

    private void init() {
        setLayoutManager( new GridLayoutManager( getContext(), 1, GridLayoutManager.HORIZONTAL, false ) );
        setAdapter( new QueueAdapter( getContext() ) );
    }

    /**
     * @param listener Listener of ViewSwitcher to understand when to switch TextView to RecyclerView and vice versa.
     */
    public void setup( Listener listener ) {
        // register listener from switcher
        mListener = listener;

        // setup up adapter and register this observer
        getAdapter().setup( new Observer() );
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }


    /**
     * Used to initialize before creating context menu and Bring up the context menu for this view.
     *
     * @param position for ContextMenuInfo
     */
    public void openContextMenu( int position ) {
        if ( position >= 0 ) {
            final long childId = getAdapter().getItemId( position );
            mContextMenuInfo = createContextMenuInfo( position, childId );
        }
        showContextMenu();
    }

    ContextMenu.ContextMenuInfo createContextMenuInfo( int position, long id ) {
        return new RecyclerContextMenuInfo( position, id );
    }

    /**
     * Extra menu information provided to the {@link android.view.View
     * .OnCreateContextMenuListener#onCreateContextMenu(android.view.ContextMenu, View,
     * ContextMenuInfo) } callback when a context menu is brought up for this RecycleView.
     */
    private static class RecyclerContextMenuInfo implements ContextMenu.ContextMenuInfo {

        /**
         * The position in the adapter for which the context menu is being displayed.
         */
        public int position;

        /**
         * The row id of the item for which the context menu is being displayed.
         */
        public long id;

        RecyclerContextMenuInfo( int position, long id ) {
            this.position = position;
            this.id = id;
        }
    }

    @Override
    public QueueAdapter getAdapter() {
        return ( QueueAdapter ) super.getAdapter();
    }
}