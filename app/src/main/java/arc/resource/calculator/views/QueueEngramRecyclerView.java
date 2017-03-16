package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

import arc.resource.calculator.adapters.CraftableEngramListAdapter;

public class QueueEngramRecyclerView extends RecyclerViewWithContextMenu {
    private static final String TAG = QueueEngramRecyclerView.class.getSimpleName();

    public QueueEngramRecyclerView( Context context ) throws Exception {
        super( context );

        init( context );
    }

    public QueueEngramRecyclerView( Context context, @Nullable AttributeSet attrs ) throws Exception {
        super( context, attrs );

        init( context );
    }

    public QueueEngramRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) throws Exception {
        super( context, attrs, defStyle );

        init( context );
    }

    private void init( Context context ) {
        setAdapter( CraftableEngramListAdapter.getInstance( context ) );
        setLayoutManager( new GridLayoutManager( context, 1, GridLayoutManager.HORIZONTAL, false ) );
    }

    private class Manager extends GridLayoutManager {

        public Manager( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
            super( context, attrs, defStyleAttr, defStyleRes );
        }

        public Manager( Context context, int spanCount ) {
            super( context, spanCount );
        }

        public Manager( Context context, int spanCount, int orientation, boolean reverseLayout ) {
            super( context, spanCount, orientation, reverseLayout );
        }
    }
}