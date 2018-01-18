package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.EngramName;
import java.util.List;

@Dao
public interface EngramNameDao {

  //  get name id by engram id
  @Query("select nameId from engramname "
      + "where engramId = :engramId "
      + "limit 1")
  String getNameId(String engramId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  void insert(EngramName engramName);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(EngramName... engramNames);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<EngramName> engramNames);

  //  delete all records from table
  @Query("delete from engramname")
  void deleteAll();
}
