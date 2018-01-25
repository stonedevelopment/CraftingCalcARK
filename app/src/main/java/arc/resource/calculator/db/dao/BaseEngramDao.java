package arc.resource.calculator.db.dao;

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
      "where nameId = :nameId " +
      "and parentId = :parentId")
  String getId(String nameId, String parentId);

  //  get all objects
  @Query("select * from engram "
      + "where parentId = :parentId")
  List<BaseEngram> getAll(String parentId);

  //  get all objects
  @Query("select * from engram "
      + "where id in (:engramIds)")
  List<BaseEngram> getAll(List<String> engramIds);

  @Query("select nameId from engram "
      + "where nameId in (:nameIds)")
  List<String> getAllByNameIds(List<String> nameIds);

  //  insert one object
  @Insert
  void insert(BaseEngram engram);

  //  delete all records from table
  @Query("delete from engram")
  void deleteAll();
}
