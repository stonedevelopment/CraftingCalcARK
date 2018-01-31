package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import arc.resource.calculator.db.entity.ModFolder;
import arc.resource.calculator.db.entity.PrimaryFolder;

@Dao
public interface ModFolderDao {

  @Insert
  void insert(ModFolder folder);
}
