package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

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

  //  get id by matching parameters
  @Query("select id from station " +
      "where imageLocationId = :imageFolder " +
      "and imageFile = :imageFile")
  String getId(String imageFolder, String imageFile);

  //  get all records matching supplied ids
  @Query("select * from station "
      + "where id in(:stationIds)")
  List<BaseStation> getAll(List<String> stationIds);

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(BaseStation station);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(BaseStation... stations);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<BaseStation> stations);

  //  delete all records from table
  @Query("delete from station")
  void deleteAll();
}
