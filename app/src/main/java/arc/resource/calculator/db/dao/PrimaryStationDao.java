package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseStation;
import arc.resource.calculator.db.entity.PrimaryStation;
import java.util.List;

@Dao
public interface PrimaryStationDao {

  @Query("select * from station "
      + "inner join primarystation on stationId = station.id")
  List<BaseStation> getAll();

  @Insert
  void insert(PrimaryStation station);
}
