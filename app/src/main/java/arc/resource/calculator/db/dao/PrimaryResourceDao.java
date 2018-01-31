package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import arc.resource.calculator.db.entity.PrimaryResource;

@Dao
public interface PrimaryResourceDao {

  @Insert
  void insert(PrimaryResource resource);
}
