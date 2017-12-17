package arc.resource.calculator.model;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.model.exception.PositionOutOfBoundsException;

public abstract class SortableMap {
    private static final String TAG = SortableMap.class.getSimpleName();

    private List<Long> mKeys;
    private List<Object> mValues;
    private int mSize;

    public SortableMap() {
        mKeys = new ArrayList<>( 0 );
        mValues = new ArrayList<>( 0 );
        mSize = 0;
    }

    public Object get( long key ) throws PositionOutOfBoundsException {
        return valueAt( indexOfKey( key ) );
    }

    public Object getAt(int position) throws PositionOutOfBoundsException  {
        return valueAt( position );
    }

    public boolean contains( long key ) {
        return mKeys.contains( key );
    }

    public int indexOfKey( long key ) {
        return mKeys.indexOf( key );
    }

    private long keyAt( int position ) throws PositionOutOfBoundsException {
        try {
            return mKeys.get( position );
        } catch ( IndexOutOfBoundsException e ) {
            throw new PositionOutOfBoundsException( position, mKeys.size(), mKeys.toString() );
        }
    }

    private void setKeyAt( int position, long key ) throws PositionOutOfBoundsException {
        try {
            mKeys.set( position, key );
        } catch ( IndexOutOfBoundsException e ) {
            throw new PositionOutOfBoundsException( position, mKeys.size(), mKeys.toString() );
        }
    }

    private void removeKeyAt( int position ) throws PositionOutOfBoundsException {
        try {
            mKeys.remove( position );
        } catch ( IndexOutOfBoundsException e ) {
            throw new PositionOutOfBoundsException( position, mKeys.size(), mKeys.toString() );
        }
    }

    private boolean addKey( long key ) throws DuplicateKeyException {
        return !contains( key ) && mKeys.add( key );
    }

    private boolean addValue( Object value ) {
        return mValues.add( value );
    }

    private boolean appendKeys( List<Long> keys ) {
        return mKeys.addAll( keys );
    }

    private boolean appendValues( List<Object> values ) {
        return mValues.addAll( values );
    }

    public void update( int position, long key, Object value) throws PositionOutOfBoundsException {
        setKeyAt( position, key );
        setValueAt( position, value );
    }

    private Object setValueAt( int position, Object value ) throws PositionOutOfBoundsException {
        try {
            return mValues.set( position, value );
        } catch ( IndexOutOfBoundsException e ) {
            throw new PositionOutOfBoundsException( position, mKeys.size(), mKeys.toString() );
        }
    }

    private Object valueAt( int position ) throws PositionOutOfBoundsException {
        try {
            return mValues.get( position );
        } catch ( IndexOutOfBoundsException e ) {
            throw new PositionOutOfBoundsException( position, mValues.size(), mValues.toString() );
        }
    }

    private Object removeValueAt( int position ) throws PositionOutOfBoundsException {
        try {
            return mValues.remove( position );
        } catch ( IndexOutOfBoundsException e ) {
            throw new PositionOutOfBoundsException( position, mKeys.size(), mKeys.toString() );
        }
    }

    void removeAt( int position ) throws PositionOutOfBoundsException {
        removeKeyAt( position );
        removeValueAt( position );
        decreaseSize();
    }

    void clear() {
        mKeys.clear();
        mValues.clear();
        mSize = 0;
    }

    public int size() {
        return mSize;
    }

    private void decreaseSize() {
        if ( mSize > 0 ) mSize--;
    }

    private void decreaseSize( int amount ) {
        if ( mSize > amount ) mSize -= amount;
    }

    private void increaseSize() {
        mSize++;
    }

    private void increaseSize( int amount ) {
        mSize += amount;
    }

    public void add( long key, Object value ) {
        if ( !contains( key ) ) {
            mKeys.add( key );
            mValues.add( value );
            increaseSize();
        }
    }

    /**
     * Appends an unchecked Collection to the end of the map of keys and map of values.
     *
     * @param map Collection to append
     */
    public void append( SortableMap map ) {
        appendKeys( map.keySet() );
        appendValues( map.valueSet() );
        increaseSize( map.size() );
    }

    /**
     * Public method that, depending upon if key exists, will replace (or add) an object with a new object.
     *
     * @param key   long value key taken from Object's _id (ROWID)
     * @param value value that will be added to lists
     */
    public void put( long key, Object value ) throws PositionOutOfBoundsException {
        if ( contains( key ) ) {
            setAt( indexOfKey( key ), key, value );
        } else {
            add( key, value );
        }
    }

    private List<Long> keySet() {
        return mKeys;
    }

    private List<Object> valueSet() {
        return mValues;
    }

    private void setAt( int position, long key, Object value ) throws PositionOutOfBoundsException {
        setKeyAt( position, key );
        setValueAt( position, value );
    }

    /**
     * Abstract method that is required to override, this will allow each extension a chance to choose its own comparable.
     *
     * @param position position of Object in map
     * @return Comparable object, be it String or whatever, used to help sort().
     */
    public abstract Comparable getComparable( int position ) throws PositionOutOfBoundsException;

    /**
     * Swaps keys and values of one position to another
     *
     * @param a position index a
     * @param b position index b
     */
    private void swap( int a, int b ) throws PositionOutOfBoundsException {
        long tempKey = keyAt( a );
        Object tempValue = valueAt( a );
        setAt( a, keyAt( b ), valueAt( b ) );
        setAt( b, tempKey, tempValue );
    }

    public synchronized void sort() throws PositionOutOfBoundsException {
//        Log.d( TAG, "sort: before: " + toString() );
        boolean swapped = true;
        while ( swapped ) {
            swapped = false;
            for ( int i = 0; i < size() - 1; i++ ) {
                Comparable first = getComparable( i );
                Comparable second = getComparable( i + 1 );
                if ( first.compareTo( second ) > 0 ) {
                    swap( i, i + 1 );
                    swapped = true;
                }
            }
        }
//        Log.d( TAG, "sort: after: " + toString() );
    }

    private static class DuplicateKeyException extends Exception {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append( '{' ).append( ' ' );
        for ( int i = 0; i < size(); i++ ) {
            try {
                builder.append( valueAt( i ).toString() );
            } catch ( PositionOutOfBoundsException e ) {
                continue;
            }

            if ( i < size() )
                builder.append( ',' );

            builder.append( ' ' );
        }
        builder.append( '}' );

        return builder.toString();
    }
}