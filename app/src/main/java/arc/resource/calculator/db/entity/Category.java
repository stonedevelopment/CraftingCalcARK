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
    @PrimaryKey
    @ColumnInfo( name = "_id" )
    private int id;

    private String name;

    @ColumnInfo( name = "parent_id" )
    private int parentId;

    @ColumnInfo( name = "station_id" )
    private int stationId;

    @ColumnInfo( name = "dlc_id" )
    private int dlcId;

    public Category( String name, int parentId, int stationId, int dlcId ) {
        this.name = name;
        this.parentId = parentId;
        this.stationId = stationId;
        this.dlcId = dlcId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getParentId() {
        return parentId;
    }

    public int getStationId() {
        return stationId;
    }

    public int getDlcId() {
        return dlcId;
    }

}
