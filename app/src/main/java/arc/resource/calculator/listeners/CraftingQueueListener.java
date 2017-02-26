package arc.resource.calculator.listeners;

import android.content.Context;
import android.net.Uri;

public interface CraftingQueueListener {
    void onRequestRemoveOneFromQueue( Context context, long engram_id );

    void onRequestRemoveAllFromQueue( Context context );

    void onRequestIncreaseQuantity( Context context, int position );

    void onRequestIncreaseQuantity( Context context, long engram_id );

    void onRequestUpdateQuantity( Context context, long engram_id, int quantity );

    void onRowInserted( Context context, long queue_id, long engram_id, int quantity, boolean wasQueueEmpty );

    void onRowUpdated( Context context, long queue_id, long engram_id, int quantity );

    void onRowDeleted( Context context, Uri uri, int positionStart, int itemCount, boolean isQueueEmpty );
}