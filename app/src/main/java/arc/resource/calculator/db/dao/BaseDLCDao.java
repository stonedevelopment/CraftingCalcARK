package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseDLC;
import java.util.List;

@Dao
public interface BaseDLCDao {

  //  get object by id
  @Query("select * from dlc "
      + "where id = :id "
      + "limit 1")
  BaseDLC get(String id);

  //  get all records
  @Query("select * from dlc")
  List<BaseDLC> getAll();

  //  insert one object
  @Insert
  void insert(BaseDLC dlc);

  //  delete all records from table
  @Query("delete from dlc")
  void deleteAll();
}
