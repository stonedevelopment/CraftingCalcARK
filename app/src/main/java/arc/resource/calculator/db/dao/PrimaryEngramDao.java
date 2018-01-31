package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseEngram;
import arc.resource.calculator.db.entity.PrimaryEngram;
import java.util.List;

@Dao
public interface PrimaryEngramDao {

  @Query("select * from engram "
      + "inner join primaryengram on engramId = engram.id "
      + "where parentId = :parentId")
  List<BaseEngram> getAll(String parentId);

  @Insert
  void insert(PrimaryEngram engram);
}
