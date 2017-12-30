package arc.resource.calculator.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity( foreignKeys = {
        @ForeignKey( entity = DLC.class,
                parentColumns = "_id",
                childColumns = "dlc_id" ) } )

public class Resource {
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "_id" )
    private int id;

    private String name;

    @ColumnInfo( name = "image_folder" )
    private String imageFolder;

    @ColumnInfo( name = "image_file" )
    private String imageFile;

    @ColumnInfo( name = "dlc_id" )
    private int dlcId;

    public Resource( String name, String imageFolder, String imageFile, int dlcId ) {
        this.name = name;
        this.imageFolder = imageFolder;
        this.imageFile = imageFile;
        this.dlcId = dlcId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageFolder() {
        return imageFolder;
    }

    public String getImageFile() {
        return imageFile;
    }

    public int getDlcId() {
        return dlcId;
    }
}