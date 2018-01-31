package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseEngram;
import arc.resource.calculator.db.entity.DLCEngram;
import java.util.List;

@Dao
public interface DLCEngramDao {

  @Query("select * from engram "
      + "inner join dlcengram on dlcengram.engramId = engram.id "
      + "where dlcengram.dlcId = :dlcId "
      + "and dlcengram.parentId = :parentId")
  List<BaseEngram> getAll(String parentId, String dlcId);

  @Insert
  void insert(DLCEngram dlcEngram);

  //  delete all records from table
  @Query("delete from dlcengram")
  void deleteAll();
}
