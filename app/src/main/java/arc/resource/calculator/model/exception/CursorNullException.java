package arc.resource.calculator.model.exception;

import android.net.Uri;

/**
 * Created by jared on 11/10/2017.
 */

public class CursorNullException extends RuntimeException {
    public CursorNullException( Uri uri ) {
        super( uri.toString() );
    }

    public CursorNullException( Uri uri, String contents ) {
        super( uri.toString() + " array:" + contents );
    }
}
