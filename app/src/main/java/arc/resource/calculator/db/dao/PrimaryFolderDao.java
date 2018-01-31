package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseFolder;
import arc.resource.calculator.db.entity.PrimaryFolder;
import java.util.List;

@Dao
public interface PrimaryFolderDao {

  @Query("select * from folder "
      + "inner join primaryfolder on folderId = folder.id "
      + "where parentId = :parentId")
  List<BaseFolder> getAll(String parentId);

  @Insert(onConflict = IGNORE)
  void insert(PrimaryFolder folder);
}
