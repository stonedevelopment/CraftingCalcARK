package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.DLCResource;

@Dao
public interface DLCResourceDao {

  @Insert
  void insert(DLCResource dlcResource);

  //  delete all records from table
  @Query("delete from dlcresource")
  void deleteAll();
}
