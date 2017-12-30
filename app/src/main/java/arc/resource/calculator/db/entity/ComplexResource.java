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
    private int engramId;
    @ColumnInfo( name = "resource_id" )
    private int resourceId;

    public ComplexResource( int engramId, int resourceId ) {
        this.engramId = engramId;
        this.resourceId = resourceId;
    }

    public int getEngramId() {
        return engramId;
    }

    public int getResourceId() {
        return resourceId;
    }
}
