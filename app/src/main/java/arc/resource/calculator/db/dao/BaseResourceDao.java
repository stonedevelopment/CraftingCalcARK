package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseResource;
import java.util.List;

@Dao
public interface BaseResourceDao {

  //  get object by id
  @Query("select * from resource "
      + "where id = :id "
      + "limit 1")
  BaseResource get(String id);

  //  get id by matching parameters
  @Query("select id from resource " +
      "where imageLocationId = :imageFolder " +
      "and imageFile = :imageFile")
  String getId(String imageFolder, String imageFile);

  //  insert one Resource object
  @Insert(onConflict = IGNORE)
  long insert(BaseResource resource);

  //  insert multiple Resource objects
  @Insert(onConflict = IGNORE)
  void insert(BaseResource... resources);

  //  insert a list of Resource objects
  @Insert(onConflict = IGNORE)
  void insert(List<BaseResource> resources);

  //  delete all records from table
  @Query("delete from resource")
  void deleteAll();
}
