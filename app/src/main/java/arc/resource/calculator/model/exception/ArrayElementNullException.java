package arc.resource.calculator.model.exception;

import static arc.resource.calculator.util.ExceptionUtil.BuildExceptionMessageBundle;

public class ArrayElementNullException extends RuntimeException {
    public ArrayElementNullException( int index, String contents ) {
        super( BuildExceptionMessageBundle( index, contents ).toString() );
    }
}
