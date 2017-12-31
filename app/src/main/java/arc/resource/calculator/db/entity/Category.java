package arc.resource.calculator.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity( foreignKeys = {
        @ForeignKey( entity = Station.class,
                parentColumns = "_id",
                childColumns = "station_id" ),
        @ForeignKey( entity = DLC.class,
                parentColumns = "_id",
                childColumns = "dlc_id" ) } )

public class Category {
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "_id" )
    private long id;

    private String name;

    @ColumnInfo( name = "parent_id" )
    private long parentId;

    @ColumnInfo( name = "station_id" )
    private long stationId;

    @ColumnInfo( name = "dlc_id" )
    private long dlcId;

    public Category( String name, long parentId, long stationId, long dlcId ) {
        this.name = name;
        this.parentId = parentId;
        this.stationId = stationId;
        this.dlcId = dlcId;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId( long parentId ) {
        this.parentId = parentId;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId( long stationId ) {
        this.stationId = stationId;
    }

    public long getDlcId() {
        return dlcId;
    }

    public void setDlcId( long dlcId ) {
        this.dlcId = dlcId;
    }
}
