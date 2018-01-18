package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.StationName;
import java.util.List;

@Dao
public interface StationNameDao {

  @Query("select nameId from stationname "
      + "where stationId = :stationId")
  String getNameId(String stationId);

  //  get station id by name id
  @Query("select stationId from stationname "
      + "where nameId = :nameId")
  String getStationId(String nameId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  void insert(StationName stationName);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(StationName... stationNames);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<StationName> stationNames);

  //  delete all records from table
  @Query("delete from stationname")
  void deleteAll();
}
