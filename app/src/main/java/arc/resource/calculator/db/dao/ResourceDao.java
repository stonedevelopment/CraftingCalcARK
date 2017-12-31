package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.Resource;

@Dao
public interface ResourceDao {
    //  get one Resource object by its rowId
    @Query( "select * from resource " +
            "where _id = :id limit 1" )
    Resource get( long id );

    //  get one Resource object found in dlc_id by its name
    @Query( "select * from resource " +
            "where name = :name " +
            "and dlc_id = :dlcId limit 1" )
    Resource get( String name, long dlcId );

    //  get all Resource objects from all game versions
    @Query( "select * from resource" )
    List<Resource> getAll();

    //  get all Resource objects found in dlc_id
    @Query( "select * from resource " +
            "where dlc_id = :dlcId" )
    List<Resource> getAll( long dlcId );

    //  insert one Resource object
    @Insert
    long insert( Resource resource );

    //  insert multiple Resource objects
    @Insert
    List<Long> insert( Resource... resources );

    //  insert a list of Resource objects
    @Insert
    List<Long> insert( List<Resource> resources );

    //  delete all records from table
    @Query( "delete from resource" )
    void deleteAll();
}
