package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import arc.resource.calculator.db.entity.BaseMod;

@Dao
public interface BaseModDao {

  @Insert
  void insert(BaseMod mod);
}
