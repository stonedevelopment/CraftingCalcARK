package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.DLCEngram;
import java.util.List;

@Dao
public interface DLCEngramDao {

  //  get id by matching parameters
  @Query("select id from dlcengram "
      + "where dlcId = :dlcId "
      + "and engramId = :engramId "
      + "limit 1")
  String getId(String dlcId, String engramId);

  @Query("select engramId from dlcengram "
      + "where id =:id "
      + "limit 1")
  String getEngramId(String id);

  @Query("select * from DLCEngram")
  List<DLCEngram> getAll();

  //  insert one DLCResource object
  @Insert(onConflict = IGNORE)
  long insert(DLCEngram dlcEngram);

  //  insert multiple DLCResource objects
  @Insert(onConflict = IGNORE)
  void insert(DLCEngram... dlcEngrams);

  //  insert a list of DLCResource objects
  @Insert(onConflict = IGNORE)
  void insert(List<DLCEngram> dlcEngrams);

  //  delete all records from table
  @Query("delete from dlcengram")
  void deleteAll();
}
