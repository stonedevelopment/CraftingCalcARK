package arc.resource.calculator.listeners;

import android.content.Context;

public interface QueueEngramListener {
    void onEngramDataSetChangeRequest( Context context );

    void onEngramDataSetChanged( boolean isQueueEmpty );
}