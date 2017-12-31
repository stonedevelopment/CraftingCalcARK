package arc.resource.calculator.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DLC {
    @PrimaryKey
    @ColumnInfo( name = "_id" )
    private long id;

    private String name;

    public DLC( long id, String name ) {
        this.id = id;
        this.name = name;
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
}
