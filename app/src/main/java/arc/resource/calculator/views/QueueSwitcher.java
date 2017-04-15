package arc.resource.calculator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ErrorReporter;
import arc.resource.calculator.util.DisplayUtil;

public class QueueSwitcher extends ViewSwitcher implements QueueRecyclerView.Listener {
    private static final String TAG = QueueSwitcher.class.getSimpleName();

    private TextView mTextView;
    private QueueRecyclerView mRecyclerView;

    @Override
    public void onError( Exception e ) {
        // switch view to textview, display error message
        if ( !isTextViewShown() )
            showNext();

        updateStatus( "An error occurred while fetching crafting queue." );

        ErrorReporter.getInstance().emitSendErrorReport( TAG, e );
    }

    @Override
    public void onInit() {
        if ( !isTextViewShown() )
            showNext();

        updateStatus( "Fetching crafting queue.." );
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

        updateStatus( "Crafting queue is empty." );
    }

    public QueueSwitcher( Context context ) {
        super( context );
        init();
    }

    public QueueSwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
        init();
    }

    private void init() {
        Log.d( TAG, "init()" );

        // set size (height) to that of 1/5 screen's width
        setLayoutParams( DisplayUtil.getInstance().getParams() );

        // instantiate our textview for status updates
        mTextView = ( TextView ) findViewById( R.id.textview_queue );

        // instantiate recyclerView
        mRecyclerView = ( QueueRecyclerView ) findViewById( R.id.gridview_queue );

        // setup view with switcher observer
        // set this as observer, initialize adapter, create crafting queue instance, query for saved data, if any.
        if ( mRecyclerView != null )
            mRecyclerView.setup( this );
        else
            mTextView.setText( "RecyclerView is null?" );
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
}