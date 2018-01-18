package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.EngramComposite;
import java.util.List;

@Dao
public interface EngramCompositeDao {

  @Query("select * from engramcomposite "
      + "where id = :id "
      + "limit 1")
  EngramComposite get(String id);

  @Query("select id from engramcomposite "
      + "where engramId = :stationEngramId "
      + "and compositeId = :compositeId "
      + "limit 1")
  String getId(String stationEngramId, String compositeId);

  //  get list of ids by engram id
  @Query("select compositeId from engramcomposite "
      + "where engramId = :engramId")
  List<String> getCompositeIds(String engramId);

  //  get list of objects by engram id
  @Query("select * from engramcomposite "
      + "where engramId = :engramId")
  List<EngramComposite> getAll(String engramId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(EngramComposite engramComposite);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(EngramComposite... engramComposites);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<EngramComposite> engramComposites);

  //  delete all records from table
  @Query("delete from engramdescription")
  void deleteAll();
}
