package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import arc.resource.calculator.db.entity.DLC;

@Dao
public interface DLCDao {
    //  get one DLC object by its rowId
    @Query( "select * from dlc " +
            "where _id = :id" )
    DLC get( long id );

    //  get all DLC objects
    @Query( "select * from dlc" )
    List<DLC> getAll();

    //  insert one DLC object
    @Insert
    long insert( DLC dlc );

    //  insert multiple DLC objects
    @Insert
    List<Long> insert( DLC... dlcs );

    //  insert a list of DLC objects
    @Insert
    List<Long> insert( List<DLC> dlcs );

    //  delete all records from table
    @Query( "delete from dlc" )
    void deleteAll();
}
