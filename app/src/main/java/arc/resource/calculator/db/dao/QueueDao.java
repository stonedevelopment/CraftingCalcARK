package arc.resource.calculator.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import arc.resource.calculator.db.entity.Queue;

@Dao
public interface QueueDao {
    //  get one Queue object by its rowId
    @Query( "select * from queue " +
            "where _id = :id" )
    Queue get( int id );

    //  get all Queue objects from database
    @Query( "select * from queue " )
    LiveData<List<Queue>> getAll();

    //  insert one Queue object
    @Insert
    long insert( Queue queue );

    //  insert multiple Queue objects
    @Insert
    List<Long> insert( Queue... queues );

    //  insert a list of Queue objects
    @Insert
    List<Long> insert( List<Queue> queues );

    //  update one Queue object
    @Update
    int update( Queue queue );

    //  update multiple Queue objects
    @Update
    int update( Queue... queues );

    //  update a list of Queue objects
    @Update
    int update( List<Queue> queues );

    //  delete one Queue object
    @Delete
    void delete( Queue queue );

    //  delete multiple Queue objects
    @Delete
    int delete( Queue... queues );

    //  delete a list of Queue objects
    @Delete
    int delete( List<Queue> queues );

    //  delete all records from table
    @Query( "delete from queue" )
    void deleteAll();
}
