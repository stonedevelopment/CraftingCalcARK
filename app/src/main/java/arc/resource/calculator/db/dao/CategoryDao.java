package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.Category;

@Dao
public interface CategoryDao {
    //  get one Category object by its rowId
    @Query( "select * from category " +
            "where _id = :id limit 1" )
    Category get( long id );

    //  get all Category objects found in dlc_id
    @Query( "select * from category " +
            "where dlc_id = :dlcId" )
    List<Category> getAll( long dlcId );

    //  get all Category objects found in dlc_id with same parent_id
    @Query( "select * from category " +
            "where parent_id = :parentId " +
            "and dlc_id = :dlcId" )
    List<Category> getAll( long parentId, long dlcId );

    //  get all Category objects found in dlc_id with same parent_id
    //      that are also found in station_id
    @Query( "select * from category " +
            "where station_id = :stationId " +
            "and parent_id = :parentId " +
            "and dlc_id = :dlcId" )
    List<Category> getAll( long stationId, long parentId, long dlcId );

    //  insert one Category object
    @Insert
    long insert( Category category );

    //  insert multiple Category objects
    @Insert
    List<Long> insert( Category... categories );

    //  insert a list of Category objects
    @Insert
    List<Long> insert( List<Category> categories );

    //  delete all records from table
    @Query( "delete from category" )
    void deleteAll();
}
