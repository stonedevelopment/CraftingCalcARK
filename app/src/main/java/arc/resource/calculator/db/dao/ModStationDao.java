package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import arc.resource.calculator.db.entity.ModStation;

@Dao
public interface ModStationDao {

  @Insert
  void insert(ModStation station);
}
