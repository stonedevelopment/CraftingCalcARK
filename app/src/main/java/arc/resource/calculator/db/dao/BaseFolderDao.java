package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseFolder;

@Dao
public interface BaseFolderDao {

  //  get id by its matching parameters
  @Query("select id from folder "
      + "where nameId = :nameId "
      + "limit 1")
  String getId(String nameId);

  @Insert(onConflict = IGNORE)
  long insert(BaseFolder folder);

  //  delete all records from table
  @Query("delete from folder")
  void deleteAll();
}
