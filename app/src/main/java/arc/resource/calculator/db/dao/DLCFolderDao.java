package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseFolder;
import arc.resource.calculator.db.entity.DLCFolder;
import java.util.List;

@Dao
public interface DLCFolderDao {

  @Query("select * from dlcfolder "
      + "where parentId = :parentId "
      + "and dlcId = :dlcId")
  List<BaseFolder> getAll(String parentId, String dlcId);

  //  insert one object
  @Insert
  void insert(DLCFolder dlcFolder);

  //  delete all records from table
  @Query("delete from dlcfolder")
  void deleteAll();
}
