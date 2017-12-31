package arc.resource.calculator.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity( tableName = "complex_resource",
        primaryKeys = { "engram_id", "resource_id" },
        foreignKeys = {
                @ForeignKey( entity = Engram.class,
                        parentColumns = "_id",
                        childColumns = "engram_id" ),
                @ForeignKey( entity = Resource.class,
                        parentColumns = "_id",
                        childColumns = "resource_id" ) } )

public class ComplexResource {
    @ColumnInfo( name = "engram_id" )
    private long engramId;
    @ColumnInfo( name = "resource_id" )
    private long resourceId;

    public ComplexResource( long engramId, long resourceId ) {
        this.engramId = engramId;
        this.resourceId = resourceId;
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
