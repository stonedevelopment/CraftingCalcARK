package arc.resource.calculator.views.switchers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.views.InventoryRecyclerView;
import arc.resource.calculator.views.switchers.listeners.Listener;

public class InventorySwitcher extends ViewSwitcher implements Listener {
    private static final String TAG = InventorySwitcher.class.getSimpleName();

    private TextView mTextView;
    private InventoryRecyclerView mRecyclerView;

    @Override
    public void onError( Exception e ) {
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( getContext().getString( R.string.switcher_inventory_status_onerror) );

        ExceptionObserver.getInstance().notifyExceptionCaught( TAG, e );
    }

    @Override
    public void onInit() {
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( getContext().getString( R.string.switcher_inventory_status_oninit) );
    }

    @Override
    public void onPopulated() {
        // switch view to recycler
        if ( !isRecyclerViewShown() )
            showNext();
    }

    @Override
    public void onEmpty() {
        // switch view to textview, display empty message
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( getContext().getString( R.string.switcher_inventory_status_onempty) );
    }

    public InventorySwitcher( Context context ) {
        super( context );
    }

    public InventorySwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public void onCreate() {
        // instantiate our textview for status updates
//        mTextView = ( TextView ) findViewById( R.id.textview_inventory );

        // instantiate recyclerView
        mRecyclerView = ( InventoryRecyclerView ) findViewById( R.id.gridview_inventory );
        mRecyclerView.create( this );
    }

    public void onResume() {
        mRecyclerView.resume();
    }

    public void onPause() {
        mRecyclerView.pause();
    }

    public void onDestroy() {
        mRecyclerView.destroy();
    }

    private void onStatusUpdate( String text ) {
        mTextView.setText( text );
    }

    private boolean isTextViewShown() {
        return getCurrentView().getId() == mTextView.getId();
    }

    private boolean isRecyclerViewShown() {
        return getCurrentView().getId() == mRecyclerView.getId();
    }
}
