package arc.resource.calculator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ErrorReporter;

public class InventorySwitcher extends ViewSwitcher implements InventoryRecyclerView.Listener {
    private static final String TAG = InventorySwitcher.class.getSimpleName();

    private TextView mTextView;
    private InventoryRecyclerView mRecyclerView;

    @Override
    public void onError( Exception e ) {
        // switch view to textview, display error message
        if ( !isTextViewShown() )
            showNext();

        updateStatus( "An error occurred while fetching Inventory." );

        ErrorReporter.getInstance().emitSendErrorReport( TAG, e );
    }

    @Override
    public void onInit() {
        if ( !isTextViewShown() )
            showNext();

        updateStatus( "Fetching inventory.." );
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

        updateStatus( "Inventory is empty." );
    }

    public InventorySwitcher( Context context ) {
        super( context );
        init();
    }

    public InventorySwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
        init();
    }

    private void init() {
        Log.d( TAG, "init()" );

        // instantiate our textview for status updates
        mTextView = ( TextView ) findViewById( R.id.textview_inventory );

        // instantiate recyclerView
        mRecyclerView = ( InventoryRecyclerView ) findViewById( R.id.gridview_inventory );
    }

    private void updateStatus( String text ) {
        mTextView.setText( text );
    }

    private boolean isTextViewShown() {
        return getCurrentView().getId() == mTextView.getId();
    }

    private boolean isRecyclerViewShown() {
        return getCurrentView().getId() == mRecyclerView.getId();
    }

    @Override
    protected void onWindowVisibilityChanged( int visibility ) {
        Log.d( TAG, "onWindowVisitibilityChanged()" );

        // should trigger whenever this view is seen or hidden
        // if seen, check recyclerview for data changed, if none, return quantity to see if inventory is empty

        if ( visibility == VISIBLE ) {
            // toggle to textview, for status updates
            if ( !isTextViewShown() )
                showNext();

            mRecyclerView.startUp( this );
        } else {
            mRecyclerView.shutDown();
        }
    }
}
