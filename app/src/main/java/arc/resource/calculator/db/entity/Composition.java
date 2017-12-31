package arc.resource.calculator.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity( foreignKeys = {
        @ForeignKey( entity = Engram.class,
                parentColumns = "_id",
                childColumns = "engram_id" ),
        @ForeignKey( entity = Resource.class,
                parentColumns = "_id",
                childColumns = "resource_id" ) } )

public class Composition {
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "_id" )
    private long id;

    private int quantity;

    @ColumnInfo( name = "engram_id" )
    private long engramId;

    @ColumnInfo( name = "resource_id" )
    private long resourceId;

    public Composition( long engramId, long resourceId, int quantity ) {
        this.engramId = engramId;
        this.resourceId = resourceId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }

    public long getEngramId() {
        return engramId;
    }

    public void setEngramId( long engramId ) {
        this.engramId = engramId;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId( long resourceId ) {
        this.resourceId = resourceId;
    }
}
