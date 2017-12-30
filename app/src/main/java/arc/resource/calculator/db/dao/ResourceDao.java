package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.Resource;

@Dao
public interface ResourceDao {
    @Query( "select * from resource where _id = :id limit 1" )
    Resource get( int id );

    @Query( "select * from resource" )
    List<Resource> getAll();

    @Query( "select * from resource " +
            "where dlc_id = :dlc_id" )
    List<Resource> getAll( int dlc_id );

    @Insert
    int insertAll( Resource... resources );
}
