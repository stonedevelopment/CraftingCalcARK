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
    private long id;

    private int quantity;

    @ColumnInfo( name = "engram_id" )
    private long engramId;

    public Queue( int quantity, long engramId ) {
        this.quantity = quantity;
        this.engramId = engramId;
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
}
