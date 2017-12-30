package arc.resource.calculator.views;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import arc.resource.calculator.listeners.QueueObservable;

public class CalculateQueueButton extends AppCompatButton {
    private static final String TAG = CalculateQueueButton.class.getSimpleName();

    public CalculateQueueButton( Context context ) {
        super( context );
    }

    public CalculateQueueButton( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public CalculateQueueButton( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    public void onCreate() {
//        setOnClickListener( new OnClickListener() {
//            @Override
//            public void onClick( View v ) {
//                CraftingQueue.getInstance().clearQueue();
//            }
//        } );

        QueueObservable.getInstance().registerListener( TAG, new QueueObservable.Listener() {
            @Override
            public void onItemChanged( long craftableId, int quantity ) {
                if ( !isEnabled() )
                    setEnabled( true );
            }

            @Override
            public void onItemRemoved( long craftableId ) {
                // do nothing
            }

            @Override
            public void onDataSetPopulated() {
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
        QueueObservable.getInstance().unregisterListener( TAG );
    }
}
