package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseFolder;
import java.util.List;

@Dao
public interface BaseFolderDao {

  //  get object by id
  @Query("select * from folder "
      + "where id = :id "
      + "limit 1")
  BaseFolder get(String id);

  //  get id by its matching parameters
  @Query("select id from folder " +
      "where parentId = :parentId")
  String getId(String parentId);

  @Query("select parentId from folder "
      + "where id = :folderId "
      + "limit 1")
  String getParentId(String folderId);

  @Query("select * from folder "
      + "where id in (:folderIds)")
  List<BaseFolder> getAll(List<String> folderIds);

  @Query("select * from folder "
      + "where parentId = :parentId")
  List<BaseFolder> getAll(String parentId);

  //  insert one Folder object
  @Insert
  long insert(BaseFolder folder);

  //  insert multiple Folder objects
  @Insert(onConflict = IGNORE)
  void insert(BaseFolder... folders);

  //  insert a list of Folder objects
  @Insert(onConflict = IGNORE)
  void insert(List<BaseFolder> folders);

  //  delete all records from table
  @Query("delete from folder")
  void deleteAll();
}
