package arc.resource.calculator.model.exception;

import android.net.Uri;

public class URIUnknownException extends Exception {
    public URIUnknownException( Uri uri ) {
        super( uri.toString() );
    }
}
