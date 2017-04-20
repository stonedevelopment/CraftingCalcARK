package arc.resource.calculator.views;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import arc.resource.calculator.listeners.QueueObserver;
import arc.resource.calculator.model.CraftingQueue;

public class ClearQueueButton extends AppCompatButton {
    private static final String TAG = ClearQueueButton.class.getSimpleName();

    QueueObserver.Listener mListener = new QueueObserver.Listener() {
        @Override
        public void onItemChanged( long craftableId, int quantity ) {
            Log.d( TAG, "onItemChanged: " );
            if ( !isEnabled() )
                setEnabled( true );
        }

        @Override
        public void onDataSetPopulated() {
            Log.d( TAG, "onDataSetPopulated: " );
            if ( !isEnabled() )
                setEnabled( true );
        }

        @Override
        public void onDataSetEmpty() {
            setEnabled( false );
        }
    };

    public ClearQueueButton( Context context ) {
        super( context );
        init();
    }

    public ClearQueueButton( Context context, AttributeSet attrs ) {
        super( context, attrs );
        init();
    }

    public ClearQueueButton( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        init();
    }

    private void init() {
        setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                CraftingQueue.getInstance().clearQueue();
            }
        } );
    }

    public void onResume() {
        QueueObserver.getInstance().registerListener( mListener );
    }

    public void onPause() {
        QueueObserver.getInstance().unregisterListener( mListener );
    }
}
