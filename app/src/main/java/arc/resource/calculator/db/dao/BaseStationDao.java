package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseStation;
import java.util.List;

@Dao
public interface BaseStationDao {

  //  get object by id
  @Query("select * from station "
      + "where id = :id "
      + "limit 1")
  BaseStation get(String id);

  //  get object by id
  @Query("select * from station "
      + "where nameId = :nameId "
      + "limit 1")
  BaseStation getByNameId(String nameId);

  //  get id by matching parameters
  @Query("select id from station "
      + "where nameId = :nameId "
      + "limit 1")
  String getId(String nameId);

  @Query("select * from station")
  List<BaseStation> getAll();

  //  get all records matching supplied ids
  @Query("select * from station "
      + "where id in(:stationIds)")
  List<BaseStation> getAll(List<String> stationIds);

  //  insert one object
  @Insert
  long insert(BaseStation station);

  //  delete all records from table
  @Query("delete from station")
  void deleteAll();
}
