package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.ComplexResource;

@Dao
public interface ComplexResourceDao {
    @Query( "select * from complex_resource where resource_id = :resource_id limit 1" )
    ComplexResource get( int resource_id );

    @Query( "select * from complex_resource" )
    List<ComplexResource> getAll();
}
