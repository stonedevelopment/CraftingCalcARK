package arc.resource.calculator.views.switchers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.views.CraftableRecyclerView;
import arc.resource.calculator.views.InventoryRecyclerView;
import arc.resource.calculator.views.layouts.CalculateLayout;
import arc.resource.calculator.views.switchers.listeners.Listener;

public class CalculateSwitcher extends ViewSwitcher implements Listener {
    private static final String TAG = CalculateSwitcher.class.getSimpleName();

    private TextView mTextView;
    private CalculateLayout mLayout;

    @Override
    public void onError( Exception e ) {
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( getContext().getString( R.string.switcher_calculate_status_onerror ) );

        ExceptionObserver.getInstance().notifyExceptionCaught( TAG, e );
    }

    @Override
    public void onInit() {
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( getContext().getString( R.string.switcher_calculate_status_oninit ) );
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

        onStatusUpdate( getContext().getString( R.string.switcher_calculate_status_onempty ) );
    }

    public CalculateSwitcher( Context context ) {
        super( context );
    }

    public CalculateSwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public void onCreate() {
        // instantiate our textview for status updates
        mTextView = ( TextView ) findViewById( R.id.textview_calculate );

        // instantiate container layout
        mLayout = ( CalculateLayout ) findViewById( R.id.layout_calculate );
        mLayout.onCreate( this );
    }

    public void onResume() {
        mLayout.onResume();
    }

    public void onPause() {
        mLayout.onPause();
    }

    public void onDestroy() {
        mLayout.onDestroy();
    }

    private void onStatusUpdate( String text ) {
        mTextView.setText( text );
    }

    private boolean isTextViewShown() {
        return getCurrentView().getId() == mTextView.getId();
    }

    private boolean isRecyclerViewShown() {
        return getCurrentView().getId() == mLayout.getId();
    }
}
