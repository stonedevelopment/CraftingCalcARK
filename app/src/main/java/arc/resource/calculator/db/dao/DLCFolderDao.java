package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.DLCFolder;
import java.util.List;

@Dao
public interface DLCFolderDao {

  @Query("select id from dlcfolder "
      + "where dlcId = :dlcId "
      + "and folderId = :folderId "
      + "limit 1")
  String getId(String dlcId, String folderId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(DLCFolder dlcFolder);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(DLCFolder... dlcFolders);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<DLCFolder> dlcFolders);

  //  delete all records from table
  @Query("delete from dlcfolder")
  void deleteAll();
}
