package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseFolder;
import arc.resource.calculator.db.entity.DLCFolder;
import java.util.List;

@Dao
public interface DLCFolderDao {

  @Query("select * from folder "
      + "inner join dlcfolder on dlcfolder.folderId = folder.id "
      + "where dlcfolder.dlcId = :dlcId "
      + "and dlcfolder.parentId = :parentId")
  List<BaseFolder> getAll(String parentId, String dlcId);

  @Insert(onConflict = IGNORE)
  void insert(DLCFolder dlcFolder);

  //  delete all records from table
  @Query("delete from dlcfolder")
  void deleteAll();
}
