package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import arc.resource.calculator.db.entity.BaseQuality;

@Dao
public interface BaseQualityDao {

  @Insert
  void insert(BaseQuality quality);
}
