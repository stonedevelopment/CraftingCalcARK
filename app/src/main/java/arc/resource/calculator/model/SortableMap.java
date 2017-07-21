package arc.resource.calculator.model;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.listeners.ExceptionObserver;
import arc.resource.calculator.util.ExceptionUtil;

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

    public Object get( long key ) {
        return get( key, null );
    }

    public Object get( long key, Object valueIfNotFound ) {
        if ( contains( key ) ) {
            int position = mKeys.indexOf( key );
            return valueAt( position );
        } else {
            return valueIfNotFound;
        }
    }

    public boolean contains( long key ) {
        return mKeys.contains( key );
    }

    public int indexOfKey( long key ) {
        return mKeys.indexOf( key );
    }

    public long keyAt( int position ) {
        return mKeys.get( position );
    }

    public Object valueAt( int position ) {
        try {
            return mValues.get( position );
        } catch ( IndexOutOfBoundsException e ) {
            ExceptionObserver.getInstance().notifyFatalExceptionCaught( TAG, new
                    ExceptionUtil.PositionOutOfBoundsException( position, mValues.size(), mValues.toString() ) );
            return null;
        }
    }

    public void setValueAt( int position, Object value ) {
        set( position, keyAt( position ), value );
    }

    public void removeAt( int position ) {
        mKeys.remove( position );
        mValues.remove( position );
        mSize--;
    }

    public void clear() {
        mKeys.clear();
        mValues.clear();
        mSize = 0;
    }

    public int size() {
        return mSize;
    }

    /**
     * Public method that, depending upon if key exists, will add a new object to the lists.
     *
     * @param key   long value key taken from Object's _id (ROWID)
     * @param value value that will be added to lists
     */
    public void add( long key, Object value ) {
        if ( !contains( key ) ) {
            mKeys.add( key );
            mValues.add( value );
            mSize++;
        }
    }

    public void addAll( SortableMap map ) {
        mKeys.addAll( map.keySet() );
        mValues.addAll( map.valueSet() );
        mSize += map.size();
    }

    /**
     * Public method that, depending upon if key exists, will replace (or add) an object with a new object.
     *
     * @param key   long value key taken from Object's _id (ROWID)
     * @param value value that will be added to lists
     */
    public void put( long key, Object value ) {
        if ( contains( key ) ) {
            int position = indexOfKey( key );

            set( position, key, value );
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

    private void set( int position, long key, Object value ) {
        mKeys.set( position, key );
        mValues.set( position, value );
    }

    /**
     * Abstract method that is required to override, this will allow each extension a chance to chooose its own comparable.
     *
     * @param position position of Object in map
     * @return Comparable object, be it String or whatever, used to help sort().
     */
    public abstract Comparable getComparable( int position );

    private void swap( int a, int b ) {
        long tempKey = keyAt( a );
        Object tempValue = valueAt( a );
        set( a, keyAt( b ), valueAt( b ) );
        set( b, tempKey, tempValue );
    }

    public void sort() {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append( '{' ).append( ' ' );
        for ( int i = 0; i < size(); i++ ) {
            builder.append( valueAt( i ).toString() );
            if ( i < size() )
                builder.append( ',' );

            builder.append( ' ' );
        }
        builder.append( '}' );

        return builder.toString();
    }
}