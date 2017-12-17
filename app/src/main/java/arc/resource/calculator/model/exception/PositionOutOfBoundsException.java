package arc.resource.calculator.model.exception;

import static arc.resource.calculator.util.ExceptionUtil.BuildExceptionMessageBundle;

public class PositionOutOfBoundsException extends Exception {
    public PositionOutOfBoundsException( int index, int size ) {
        super( BuildExceptionMessageBundle( index, size ).toString() );
    }

    public PositionOutOfBoundsException( int index, int size, String contents ) {
        super( BuildExceptionMessageBundle( index, size, contents ).toString() );
    }
}