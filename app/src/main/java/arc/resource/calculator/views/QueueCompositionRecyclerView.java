package arc.resource.calculator.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import arc.resource.calculator.adapters.CraftableResourceListAdapter;

public class QueueCompositionRecyclerView extends RecyclerViewWithContextMenu {
    private static final String TAG = QueueCompositionRecyclerView.class.getSimpleName();

    public QueueCompositionRecyclerView( Context context ) throws Exception {
        super( context );

        init( context );
    }

    public QueueCompositionRecyclerView( Context context, @Nullable AttributeSet attrs ) throws Exception {
        super( context, attrs );

        init( context );
    }

    public QueueCompositionRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) throws Exception {
        super( context, attrs, defStyle );

        init( context );
    }

    private void init( Context context ) {
        setLayoutManager( new LinearLayoutManager( context ) );
        setAdapter( CraftableResourceListAdapter.getInstance( context ) );
    }
}