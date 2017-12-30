package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.Engram;

@Dao
public interface EngramDao {
    @Query( "select * from engram where _id = :id limit 1" )
    Engram get( long id );

    @Query( "select * from engram" )
    List<Engram> getAll();

    @Query( "select * from engram " +
            "where dlc_id = :dlc_id" )
    List<Engram> getAll( long dlc_id );

    @Query( "select * from engram " +
            "where category_id = :category_id " +
            "and station_id = :station_id " +
            "and dlc_id = :dlc_id" )
    List<Engram> getAll( long category_id, long station_id, long dlc_id );

    @Query( "select * from engram " +
            "where level <= :level " +
            "and category_id = :category_id " +
            "and station_id = :station_id " +
            "and dlc_id = :dlc_id" )
    List<Engram> getAll( long level, long category_id, long station_id, long dlc_id );

    @Insert
    long insert( Engram engram );

    @Insert
    List<Long> insertAll( Engram... engrams );
}
