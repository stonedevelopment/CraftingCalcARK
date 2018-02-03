package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.DLCStation;
import java.util.List;

@Dao
public interface DLCStationDao {

  @Query("select stationId from dlcstation "
      + "where dlcId = :dlcId")
  List<String> getStationIds(String dlcId);

  @Insert
  void insert(DLCStation dlcStation);

  //  delete all records from table
  @Query("delete from dlcstation")
  void deleteAll();

}
