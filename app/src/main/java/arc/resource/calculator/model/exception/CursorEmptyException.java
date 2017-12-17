package arc.resource.calculator.model.exception;

import android.net.Uri;

/**
 * Created by jared on 11/10/2017.
 */

public class CursorEmptyException extends RuntimeException {
    public CursorEmptyException( Uri uri ) {
        super( uri.toString() );
    }
}
