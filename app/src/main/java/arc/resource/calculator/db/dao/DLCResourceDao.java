package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.DLCResource;

@Dao
public interface DLCResourceDao {

  @Query("select * from dlcresource "
      + "where id = :id "
      + "limit 1")
  DLCResource get(String id);

  //  get id by matching parameters
  @Query("select id from dlcresource " +
      "where nameId = :nameId " +
      "limit 1")
  String getId(String nameId);

  //  insert one object
  @Insert
  void insert(DLCResource dlcResource);

  //  delete all records from table
  @Query("delete from dlcresource")
  void deleteAll();
}
