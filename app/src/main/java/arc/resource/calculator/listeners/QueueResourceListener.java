package arc.resource.calculator.listeners;

import android.content.Context;

public interface QueueResourceListener {
    void onResourceDataSetChangeRequest( Context context );

    void onResourceDataSetChanged();
}