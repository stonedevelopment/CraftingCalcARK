package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.Composition;

@Dao
public interface CompositionDao {
    //  get one Composition object by rowId
    @Query( "select * from composition " +
            "where _id = :id limit 1" )
    Composition get( long id );

    //  get all Composition objects that match provided engram_id
    @Query( "select * from composition " +
            "where engram_id = :engramId" )
    List<Composition> getAll( long engramId );

    //  get all Composition objects that match provided resource_id
    @Query( "select * from composition " +
            "where resource_id = :resourceId" )
    List<Composition> getAllByResourceId( long resourceId );

    //  insert one Composition object
    @Insert
    long insert( Composition composition );

    //  insert multiple Composition objects
    @Insert
    List<Long> insert( Composition... compositions );

    //  insert a list of Composition objects
    @Insert
    List<Long> insert( List<Composition> compositions );

    //  delete all records from table
    @Query( "delete from composition" )
    void deleteAll();
}