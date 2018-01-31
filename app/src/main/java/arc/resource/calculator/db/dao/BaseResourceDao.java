package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseResource;

@Dao
public interface BaseResourceDao {

  //  get id by matching parameters
  @Query("select id from resource " +
      "where nameId = :nameId " +
      "limit 1")
  String getId(String nameId);

  @Insert(onConflict = IGNORE)
  long insert(BaseResource resource);

  //  delete all records from table
  @Query("delete from resource")
  void deleteAll();
}
