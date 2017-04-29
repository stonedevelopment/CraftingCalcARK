package arc.resource.calculator.views;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import arc.resource.calculator.listeners.QueueObserver;
import arc.resource.calculator.model.CraftingQueue;

public class ClearQueueButton extends AppCompatButton {
    private static final String TAG = ClearQueueButton.class.getSimpleName();

    public ClearQueueButton( Context context ) {
        super( context );
    }

    public ClearQueueButton( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public ClearQueueButton( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    public void onCreate() {
        setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View v ) {
                CraftingQueue.getInstance().clearQueue();
            }
        } );

        QueueObserver.getInstance().registerListener( TAG, new QueueObserver.Listener() {
            @Override
            public void onItemChanged( long craftableId, int quantity ) {
                if ( !isEnabled() )
                    setEnabled( true );
            }

            @Override
            public void onDataSetPopulated() {
                if ( !isEnabled() )
                    setEnabled( true );
            }

            @Override
            public void onDataSetEmpty() {
                setEnabled( false );
            }
        } );
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onDestroy() {
        QueueObserver.getInstance().unregisterListener( TAG );
    }
}
