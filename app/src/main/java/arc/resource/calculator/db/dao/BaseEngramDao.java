package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseEngram;
import java.util.List;

@Dao
public interface BaseEngramDao {

  //  get object by id
  @Query("select * from engram "
      + "where id = :id "
      + "limit 1")
  BaseEngram get(String id);

  //  get id by its matching parameters
  @Query("select id from engram " +
      "where imageLocationId = :imageFolder " +
      "and imageFile = :imageFile " +
      "and requiredLevel = :requiredLevel")
  String getId(String imageFolder, String imageFile, Integer requiredLevel);

  //  get all objects
  @Query("select * from engram")
  List<BaseEngram> getAll();

  //  get all objects
  @Query("select * from engram "
      + "where id in (:engramIds)")
  List<BaseEngram> getAll(List<String> engramIds);

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(BaseEngram engram);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(BaseEngram... engrams);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<BaseEngram> engrams);

  //  delete all records from table
  @Query("delete from engram")
  void deleteAll();
}
