package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.DLCName;
import java.util.List;

@Dao
public interface DLCNameDao {

  @Query("select nameId from DLCName where dlcId = :dlcId")
  String getNameId(String dlcId);

  //  get dlc id by name id
  @Query("select dlcId from dlcname "
      + "where nameId = :nameId")
  String getDlcId(String nameId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(DLCName dlcName);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(DLCName... dlcNames);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<DLCName> dlcNames);

  //  delete all records from table
  @Query("delete from dlcname")
  void deleteAll();
}
