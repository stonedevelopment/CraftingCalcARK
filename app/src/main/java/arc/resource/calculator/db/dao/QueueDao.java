package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.Queue;
import java.util.List;

@Dao
public interface QueueDao {

  //  insert one object
  @Insert
  long insert(Queue queue);

  //  insert multiple objects
  @Insert
  List<Long> insert(Queue... queues);

  //  insert a list of objects
  @Insert
  List<Long> insert(List<Queue> queues);

  //  delete all records from table
  @Query("delete from queue")
  void deleteAll();
}
