package arc.resource.calculator.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import arc.resource.calculator.db.entity.Queue;

@Dao
public interface QueueDao {
    @Query( "select * from queue where _id = :id" )
    Queue get( int id );

    @Query( "select * from queue " )
    LiveData<List<Queue>> getAll();

    @Insert( onConflict = OnConflictStrategy.FAIL )
    long insert( Queue queue );

    @Update
    int update( Queue queue );

    @Update
    int update( Queue... queues );

    @Delete
    int delete( Queue queue );

    @Delete
    int delete( Queue... queues );
}
