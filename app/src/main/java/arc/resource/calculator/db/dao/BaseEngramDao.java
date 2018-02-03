package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseEngram;
import java.util.List;

@Dao
public interface BaseEngramDao {

  //  get id by its matching parameters
  @Query("select id from engram "
      + "where nameId = :nameId "
      + "limit 1")
  String getId(String nameId);

  //  get id by its matching parameters
//  @Query("select id from engram "
//      + "where descriptionId = :descriptionId "
//      + "and imageLocationId = :imageLocationId "
//      + "and nameId = :nameId "
//      + "and requiredLevel = :requiredLevel")
//  String getId(String descriptionId, String imageLocationId, String nameId, Integer requiredLevel);

  @Query("select * from engram "
      + "where id in (:engramIds)")
  List<BaseEngram> getAll(List<String> engramIds);

  @Insert(onConflict = IGNORE)
  long insert(BaseEngram engram);

  //  delete all records from table
  @Query("delete from engram")
  void deleteAll();
}
