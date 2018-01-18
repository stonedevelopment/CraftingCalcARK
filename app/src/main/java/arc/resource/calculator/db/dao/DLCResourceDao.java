package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.DLCResource;
import java.util.List;

@Dao
public interface DLCResourceDao {

  @Query("select id from dlcresource "
      + "where dlcId = :dlcId "
      + "and resourceId = :resourceId "
      + "limit 1")
  String getId(String dlcId, String resourceId);

  //  get dlc id by resource id
  @Query("select dlcId from dlcresource "
      + "where resourceId = :resourceId")
  String getDlcId(String resourceId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(DLCResource dlcResource);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(DLCResource... dlcResources);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<DLCResource> dlcResources);

  //  delete all records from table
  @Query("delete from dlcresource")
  void deleteAll();
}
