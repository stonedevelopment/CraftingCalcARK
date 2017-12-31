package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.Engram;

@Dao
public interface EngramDao {
    //  get one Engram object by its rowId
    @Query( "select * from engram " +
            "where _id = :id limit 1" )
    Engram get( long id );

    //  get all Engram objects from all game versions
    @Query( "select * from engram" )
    List<Engram> getAll();

    //  get all Engram objects found in dlc_id
    @Query( "select * from engram " +
            "where dlc_id = :dlc_id" )
    List<Engram> getAll( long dlc_id );

    //  get all Engram objects found in dlc_id that have same category_id and station_id
    @Query( "select * from engram " +
            "where category_id = :category_id " +
            "and station_id = :station_id " +
            "and dlc_id = :dlc_id" )
    List<Engram> getAll( long category_id, long station_id, long dlc_id );

    //  get all Engram objects found in dlc_id that have same category_id and station_id
    //      with a required level of less than or equal to provided level
    @Query( "select * from engram " +
            "where level <= :level " +
            "and category_id = :category_id " +
            "and station_id = :station_id " +
            "and dlc_id = :dlc_id" )
    List<Engram> getAll( int level, long category_id, long station_id, long dlc_id );

    //  insert one Engram object
    @Insert
    long insert( Engram engram );

    //  insert multiple Engram objects
    @Insert
    List<Long> insert( Engram... engrams );

    //  insert a list of Engram objects
    @Insert
    List<Long> insert( List<Engram> engrams );

    //  delete all records from table
    @Query( "delete from engram" )
    void deleteAll();
}
