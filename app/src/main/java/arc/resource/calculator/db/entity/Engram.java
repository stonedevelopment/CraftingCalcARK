package arc.resource.calculator.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity( indices = { @Index( "name" ) },
        foreignKeys = {
                @ForeignKey( entity = Station.class,
                        parentColumns = "_id",
                        childColumns = "station_id" ),
                @ForeignKey( entity = Category.class,
                        parentColumns = "_id",
                        childColumns = "category_id" ),
                @ForeignKey( entity = DLC.class,
                        parentColumns = "_id",
                        childColumns = "dlc_id" ) } )

public class Engram {
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "_id" )
    private long id;

    private String name;

    private String description;

    @ColumnInfo( name = "image_folder" )
    private String imageFolder;

    @ColumnInfo( name = "image_file" )
    private String imageFile;

    private int yield;

    private int level;

    @ColumnInfo( name = "station_id" )
    private long stationId;

    @ColumnInfo( name = "category_id" )
    private long categoryId;

    @ColumnInfo( name = "dlc_id" )
    private long dlcId;

    public Engram( String name, String description, String imageFolder, String imageFile, int yield, int level, long stationId, long categoryId, long dlcId ) {
        this.name = name;
        this.description = description;
        this.imageFolder = imageFolder;
        this.imageFile = imageFile;
        this.yield = yield;
        this.level = level;
        this.stationId = stationId;
        this.categoryId = categoryId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getImageFolder() {
        return imageFolder;
    }

    public void setImageFolder( String imageFolder ) {
        this.imageFolder = imageFolder;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile( String imageFile ) {
        this.imageFile = imageFile;
    }

    public int getYield() {
        return yield;
    }

    public void setYield( int yield ) {
        this.yield = yield;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel( int level ) {
        this.level = level;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId( long stationId ) {
        this.stationId = stationId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId( long categoryId ) {
        this.categoryId = categoryId;
    }

    public long getDlcId() {
        return dlcId;
    }

    public void setDlcId( long dlcId ) {
        this.dlcId = dlcId;
    }
}