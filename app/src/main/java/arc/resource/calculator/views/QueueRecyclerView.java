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
    public void create( Listener listener ) {
        // register listener from switcher
        mListener = listener;

        // resume up adapter and register this observer
        getAdapter().create( new Observer() );
    }

    public void resume() {
        getAdapter().resume();
    }

    public void pause() {
        getAdapter().pause();
    }

    @Override
    public QueueAdapter getAdapter() {
        return ( QueueAdapter ) super.getAdapter();
    }
}