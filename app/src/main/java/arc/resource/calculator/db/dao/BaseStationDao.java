package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseStation;

@Dao
public interface BaseStationDao {

  //  get object by its matching parameters
  @Query("select * from station "
      + "where nameId = :nameId "
      + "limit 1")
  BaseStation get(String nameId);

  //  get id by its matching parameters
  @Query("select id from station "
      + "where nameId = :nameId "
      + "limit 1")
  String getId(String nameId);

  //  insert one object
  @Insert
  long insert(BaseStation station);

  //  delete all records from table
  @Query("delete from station")
  void deleteAll();
}
