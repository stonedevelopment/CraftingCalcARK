package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.FolderName;
import java.util.List;

@Dao
public interface FolderNameDao {

  @Query("select nameId from foldername "
      + "where folderId = :dlcFolderId "
      + "limit 1")
  String getNameId(String dlcFolderId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  void insert(FolderName folderName);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(FolderName... folderNames);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<FolderName> folderNames);

  //  delete all records from table
  @Query("delete from foldername")
  void deleteAll();

}
