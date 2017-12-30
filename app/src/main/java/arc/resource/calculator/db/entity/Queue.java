package arc.resource.calculator.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity( foreignKeys = {
        @ForeignKey( entity = Engram.class,
                parentColumns = "_id",
                childColumns = "engram_id" ) } )

public class Queue {
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "_id" )
    private int id;
    private int quantity;
    @ColumnInfo( name = "engram_id" )
    private int engramId;

    public Queue( int quantity, int engramId ) {
        this.quantity = quantity;
        this.engramId = engramId;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getEngramId() {
        return engramId;
    }
}
