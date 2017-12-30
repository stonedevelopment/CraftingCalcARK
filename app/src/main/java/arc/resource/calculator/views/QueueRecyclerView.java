package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

import arc.resource.calculator.R;
import arc.resource.calculator.adapters.QueueAdapter;
import arc.resource.calculator.views.switchers.listeners.Listener;
import arc.resource.calculator.views.switchers.listeners.Observer;

public class QueueRecyclerView extends RecyclerViewWithContextMenu implements Observer {
    private static final String TAG = QueueRecyclerView.class.getSimpleName();

    private static Listener mListener;

    @Override
    public void notifyExceptionCaught( Exception e ) {
        getListener().onError( e );
    }

    @Override
    public void notifyInitializing() {
        getListener().onInit();
    }

    @Override
    public void notifyDataSetPopulated() {
        getListener().onPopulated();
    }

    @Override
    public void notifyDataSetEmpty() {
        getListener().onEmpty();
    }

    public QueueRecyclerView( Context context ) {
        super( context );
    }

    public QueueRecyclerView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public QueueRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
    }

    private static Listener getListener() {
        return mListener;
    }

    private void setListener( Listener listener ) {
        mListener = listener;
    }

    public void onCreate( Listener listener ) {
        int numCols = getResources().getInteger( R.integer.gridview_column_count );
        setLayoutManager( new GridLayoutManager( getContext(), numCols ) );
//        setLayoutManager( new GridLayoutManager( getContext(), 1, GridLayoutManager.HORIZONTAL, false ) );
        setListener( listener );
        setAdapter( new QueueAdapter( getContext(), this ) );
    }

    public void onResume() {
        getAdapter().resume();
    }

    public void pause() {
        getAdapter().pause();
    }

    public void destroy() {
        getAdapter().destroy();
    }

    @Override
    public QueueAdapter getAdapter() {
        return ( QueueAdapter ) super.getAdapter();
    }
}