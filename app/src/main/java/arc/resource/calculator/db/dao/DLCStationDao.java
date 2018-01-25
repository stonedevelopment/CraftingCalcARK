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

  @Query("select * from dlcstation "
      + "where dlcId = :dlcId")
  List<DLCStation> getAll(String dlcId);

  //  insert one object
  @Insert
  void insert(DLCStation dlcStation);

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
