package arc.resource.calculator.db.dao;

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

  //  get object by id
  @Query("select * from folder "
      + "where nameId = :nameId "
      + "and parentId = :parentId "
      + "limit 1")
  BaseFolder get(String nameId, String parentId);

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
  void insert(BaseFolder folder);

  //  delete all records from table
  @Query("delete from folder")
  void deleteAll();
}
