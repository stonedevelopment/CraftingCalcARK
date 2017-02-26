package arc.resource.calculator.listeners;

import android.os.Bundle;

/**
 * Created by jared on 2/20/2017.
 */

public interface ServiceListener {
    void onReceiveResult( int resultCode, Bundle resultData );
}
