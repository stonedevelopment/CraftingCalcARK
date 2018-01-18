package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseDLC;
import java.util.List;

@Dao
public interface BaseDLCDao {

  //  get object by id
  @Query("select * from dlc "
      + "where id = :id "
      + "limit 1")
  BaseDLC get(String id);

  //  get id by its matching parameter
  @Query("select id from dlc " +
      "where imageLocationId = :imageFolder " +
      "and imageFile = :imageFile")
  String getId(String imageFolder, String imageFile);

  //  get all records
  @Query("select * from dlc")
  List<BaseDLC> getAll();

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(BaseDLC dlc);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(BaseDLC... dlcs);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<BaseDLC> dlcs);

  //  delete all records from table
  @Query("delete from dlc")
  void deleteAll();
}
