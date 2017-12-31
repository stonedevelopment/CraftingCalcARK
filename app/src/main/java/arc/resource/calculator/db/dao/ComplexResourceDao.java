package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.ComplexResource;

@Dao
public interface ComplexResourceDao {
    //  get one ComplexResource object from matching resource_id
    @Query( "select * from complex_resource " +
            "where resource_id = :resourceId limit 1" )
    ComplexResource get( long resourceId );

    //  insert one ComplexResource object
    @Insert
    long insert( ComplexResource complexResource );

    //  insert multiple ComplexResource objects
    @Insert
    List<Long> insert( ComplexResource... complexResources );

    //  insert list of ComplexResource objects
    @Insert
    List<Long> insert( List<ComplexResource> complexResources );

    //  delete all records from table
    @Query( "delete from complex_resource" )
    void deleteAll();
}
