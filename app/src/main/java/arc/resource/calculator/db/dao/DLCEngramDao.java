package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.DLCEngram;
import java.util.List;

@Dao
public interface DLCEngramDao {

  @Query("select * from DLCEngram")
  List<DLCEngram> getAll();

  @Query("select * from DLCEngram "
      + "where dlcId = :dlcId")
  List<DLCEngram> getAll(String dlcId);

  @Query("select * from DLCEngram "
      + "where parentId = :parentId "
      + "and dlcId = :dlcId")
  List<DLCEngram> getAll(String parentId, String dlcId);

  @Insert
  void insert(DLCEngram dlcEngram);

  //  delete all records from table
  @Query("delete from dlcengram")
  void deleteAll();
}
