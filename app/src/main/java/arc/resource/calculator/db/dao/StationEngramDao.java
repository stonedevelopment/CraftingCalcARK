package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.StationEngram;
import java.util.List;

@Dao
public interface StationEngramDao {

  @Query("select * from stationengram "
      + "where stationId = :stationId "
      + "and engramId = :engramId "
      + "limit 1")
  StationEngram get(String stationId, String engramId);

  //  get id by its matching parameters
  @Query("select id from stationengram "
      + "where stationId = :stationId "
      + "and engramId = :engramId "
      + "limit 1")
  String getId(String stationId, String engramId);

  //  get all engram ids by matching parameters
  @Query("select engramId from stationengram "
      + "where stationId = :stationId")
  List<String> getEngramIds(String stationId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(StationEngram stationEngram);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(StationEngram... stationEngrams);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<StationEngram> stationEngrams);

  //  delete all records from table
  @Query("delete from stationengram")
  void deleteAll();
}
