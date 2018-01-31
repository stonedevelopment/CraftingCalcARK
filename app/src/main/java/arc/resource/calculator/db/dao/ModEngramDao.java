package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import arc.resource.calculator.db.entity.ModEngram;
import arc.resource.calculator.db.entity.PrimaryEngram;

@Dao
public interface ModEngramDao {

  @Insert
  void insert(ModEngram engram);
}
