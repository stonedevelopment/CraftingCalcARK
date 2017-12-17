package arc.resource.calculator.model;

import arc.resource.calculator.model.exception.PositionOutOfBoundsException;
import arc.resource.calculator.model.resource.CompositeResource;

/**
 * Created by jared on 11/4/2017.
 */

public class InventoryMap extends SortableMap {

    public InventoryMap() {
        super();
    }

//        @Override
//        public void add( long key, Object value ) {
//            // check if key/value pair was added or not (if new or stored value)
//            if ( contains( key ) ) {
//                // update key/value pair with converged value
//                CompositeResource tempResource = ( CompositeResource ) value;
//
//                CompositeResource resource = get( tempResource.getId() );
//                if ( resource == null )
//                    resource = new CompositeResource(
//                            tempResource.getId(),
//                            tempResource.getName(),
//                            tempResource.getFolder(),
//                            tempResource.getFile(),
//                            tempResource.getQuantity() * queueEngram.getQuantity() );
//                else
//                    resource.increaseQuantity( tempResource.getQuantity() * queueEngram.getQuantity() );
//            } else {
//                // add new key/value pair
//                addNew( key, value );
//            }
//        }

    @Override
    public CompositeResource get( long key ) throws PositionOutOfBoundsException {
        return ( CompositeResource ) super.get( key );
    }

    @Override
    public CompositeResource getAt( int position ) throws PositionOutOfBoundsException {
        return ( CompositeResource ) super.getAt( position );
    }

    @Override
    public String getComparable( int position ) throws PositionOutOfBoundsException {
        return getAt( position ).getName();
    }
}
