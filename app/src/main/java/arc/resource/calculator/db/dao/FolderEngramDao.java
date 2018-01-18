package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.FolderEngram;
import java.util.List;

@Dao
public interface FolderEngramDao {

  @Query("select engramId from folderengram "
      + "where folderId = :dlcFolderId")
  List<String> getEngramIds(String dlcFolderId);

  @Query("select folderId from folderengram "
      + "where engramId = :dlcEngramId")
  List<String> getFolderIds(String dlcEngramId);

  @Query("select * from folderengram "
      + "where engramId = :dlcEngramId")
  List<FolderEngram> getAllByEngramId(String dlcEngramId);

  @Query("select * from folderengram "
      + "where folderId = :dlcFolderId")
  List<FolderEngram> getAllByFolderId(String dlcFolderId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(FolderEngram folderEngram);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(FolderEngram... folderEngrams);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<FolderEngram> folderEngrams);

  //  delete all records from table
  @Query("delete from folderengram")
  void deleteAll();
}
