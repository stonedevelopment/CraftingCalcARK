package arc.resource.calculator.views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import arc.resource.calculator.listeners.NavigationObserver;

public class NavigationTextView extends AppCompatTextView {

    NavigationObserver.Listener mListener = new NavigationObserver.Listener() {
        @Override
        public void onUpdate( String text ) {
            setText( text );
        }
    };

    public NavigationTextView( Context context ) {
        super( context );
    }

    public NavigationTextView( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public NavigationTextView( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    public void resume() {
        NavigationObserver.getInstance().registerListener( mListener );
    }

    public void pause() {
        NavigationObserver.getInstance().unregisterListener( mListener );
    }
}
