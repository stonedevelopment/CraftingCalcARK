package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.DLCStation;
import java.util.List;

@Dao
public interface DLCStationDao {

  @Query("select * from dlcstation "
      + "where id = :id "
      + "limit 1")
  DLCStation get(String id);

  //  get by matching parameters
  @Query("select * from dlcstation "
      + "where dlcId = :dlcId "
      + "and stationId = :stationId "
      + "limit 1")
  DLCStation get(String dlcId, String stationId);

  //  get id by matching parameters
  @Query("select id from dlcstation "
      + "where dlcId = :dlcId "
      + "and stationId = :stationId "
      + "limit 1")
  String getId(String dlcId, String stationId);

  //  get dlc id by station id
  @Query("select dlcId from dlcstation "
      + "where stationId = :stationId")
  String getDlcId(String stationId);

  //  get list of station ids per dlc id
  @Query("select stationId from dlcstation "
      + "where dlcId = :dlcId")
  List<String> getStationIds(String dlcId);

  @Query("select * from dlcstation "
      + "where dlcId = :dlcId")
  List<DLCStation> getAll(String dlcId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(DLCStation dlcStation);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(DLCStation... dlcStations);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<DLCStation> dlcStations);

  //  delete all records from table
  @Query("delete from dlcstation")
  void deleteAll();

}
