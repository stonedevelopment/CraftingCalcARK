package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.Station;

@Dao
public interface StationDao {
    //  get one Station object by its rowId
    @Query( "select * from station " +
            "where _id = :id" )
    Station get( long id );

    //  get all Station objects from all game versions
    @Query( "select * from station" )
    List<Station> getAll();

    //  get all Station objects found in dlc_id
    @Query( "select * from station " +
            "where dlc_id = :dlcId" )
    List<Station> getAll( long dlcId );

    //  insert one Station object
    @Insert
    long insert( Station station );

    //  insert multiple Station objects
    @Insert
    List<Long> insert( Station... stations );

    //  insert a list of Station objects
    @Insert
    List<Long> insert( List<Station> stations );

    //  delete all records from table
    @Query( "delete from station" )
    void deleteAll();
}
