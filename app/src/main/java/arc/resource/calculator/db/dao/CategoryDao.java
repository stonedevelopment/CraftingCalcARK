package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.Category;

@Dao
public interface CategoryDao {
    @Query( "select * from category " +
            "where _id = :id " +
            "and dlc_id = :dlcId limit 1" )
    Category get( int id, int dlcId );

    @Query( "select * from category " +
            "where station_id = :stationId " +
            "and dlc_id = :dlcId" )
    List<Category> getAll( int stationId, int dlcId );

    @Query( "select * from category " +
            "where parent_id = :parentId " +
            "and dlc_id = :dlcId" )
    List<Category> getAllWithParentId( int parentId, int dlcId );

    @Insert
    List<Long> insertAll( Category... categories );
}
