package arc.resource.calculator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import arc.resource.calculator.R;
import arc.resource.calculator.listeners.ExceptionObserver;

public class CraftableSwitcher extends ViewSwitcher implements CraftableRecyclerView.Listener {
    private static final String TAG = CraftableSwitcher.class.getSimpleName();

    private TextView mTextView;
    private CraftableRecyclerView mRecyclerView;

    @Override
    public void onError( Exception e ) {
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( String.format(
                getContext().getString( R.string.content_main_switcher_error_format ),
                getContext().getString( R.string.content_main_switcher_craftable ) ) );

        ExceptionObserver.getInstance().notifyExceptionCaught( TAG, e );
    }

    @Override
    public void onInit() {
        if ( !isTextViewShown() )
            showNext();

        onStatusUpdate( String.format(
                getContext().getString( R.string.content_main_switcher_fetch_format ),
                getContext().getString( R.string.content_main_switcher_craftable ) ) );
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

        onStatusUpdate( String.format(
                getContext().getString( R.string.content_main_switcher_empty_format ),
                getContext().getString( R.string.content_main_switcher_craftable ) ) );
    }

    public CraftableSwitcher( Context context ) {
        super( context );
    }

    public CraftableSwitcher( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public void onCreate() {
        // instantiate our textview for status updates
        mTextView = ( TextView ) findViewById( R.id.textview_craftables );

        // instantiate recyclerView
        mRecyclerView = ( CraftableRecyclerView ) findViewById( R.id.gridview_craftables );
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

    public void onSearch( String query ) {
        mRecyclerView.search( query );
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
